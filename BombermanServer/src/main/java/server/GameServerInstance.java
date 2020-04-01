package server;

import common.BombermanDTO;
import common.enums.AgentAction;
import engine.BombermanGame;
import engine.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class GameServerInstance implements Runnable, Observer {

    final static Logger log = (Logger) LogManager.getLogger(GameServerInstance.class);
    public int playerIndex;
    GameServer gameServer;
    Socket socket;
    ObjectInputStream input;
    ObjectOutputStream output;
    User user;
    BombermanGame bombermanGame;
    volatile boolean isRunning;
    volatile boolean ready;
    volatile boolean isWaiting;

    GameServerInstance(GameServer gameServer, Socket socket) {
        this.gameServer = gameServer;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            log.debug("Instance running");

            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            initGame();
            gameLoop();

            socket.close();

            log.debug("Instance terminated");
        } catch (IOException e) {
            bombermanGame.gameOver();
            log.error(e);
        } catch (NullPointerException npe) {
            bombermanGame.gameOver();
            log.error(npe);
        } catch (ClassNotFoundException e) {
            bombermanGame.gameOver();
            log.error(e);
        }
    }

    private void initGame() throws IOException, ClassNotFoundException {
        String layout = (String) input.readObject();
        log.debug(layout);
        ready = false;
        isWaiting = true;

        bombermanGame = GameManager.findOrCreateGame(layout);
        bombermanGame.addObserver(this);
        bombermanGame.gameServerInstances.add(this);
        playerIndex = bombermanGame.gameServerInstances.size() - 1;


        String cmd = (String) input.readObject();
        log.debug("cmd= " + cmd);
        if (cmd.equals("$ready")) {
            ready();
        }
        while (isWaiting) {
            // Attente le temps que la partie se lance
        }
    }

    private void ready() throws IOException, ClassNotFoundException {
        ready = true;
        GameManager.startIfPossible(bombermanGame);
    }

    private void gameLoop() throws IOException, ClassNotFoundException {
        isRunning = true;
        log.debug("Game started");
        sendText("Game Started");
        while (isRunning) {
            AgentAction action = (AgentAction) input.readObject();
            bombermanGame.setAction(this, action);
        }
    }

    @Override
    public void update(Observable arg0, Object arg1) {
        try {
            output.reset();
            Map updatedMap = bombermanGame.getMap();
            BombermanDTO bombermanDTO = new BombermanDTO(updatedMap.getFilename(), updatedMap.getSizeX(),
                    updatedMap.getSizeY(), updatedMap.get_walls(), updatedMap.getBreakableWalls(),
                    bombermanGame.getInfoAgents(), bombermanGame.getBombs(), bombermanGame.getItems());
            output.writeObject(bombermanDTO);
            log.debug(bombermanDTO.toString() + " sent");
        } catch (IOException e) {
            log.error(e);
        }
    }

    public void lost() {
        // TODO méthode à appeler lorsque le joueur a perdu
        // envoi l'instruction lui interdisant d'executer des actions
        // lui permet tout de même de continuer à regarder la partie
    }

    public void terminate() {
        try {
            // TODO : gestion du message game gagnée ou perdue
            sendText("$end-Game Won! You may now start a new game.");
        } catch (IOException e) {
            log.error(e);
        }
        // TODO : méthode à appeler lorsque la partie est terminée
        // termine la partie et renvoie tout les joueurs restants vers le menu de sélection de partie
    }

    public void sendText(String text) throws IOException {
        output.writeObject(text);
    }

}
