package server;

import common.BombermanDTO;
import common.enums.AgentAction;
import engine.BombermanGame;
import engine.Map;
import engine.agents.AbstractAgent;
import engine.subsystems.ActionSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class GameServerInstance implements Runnable, Observer {

    final static Logger log = (Logger) LogManager.getLogger(GameServerInstance.class);

    GameServer gameServer;
    Socket socket;
    ObjectInputStream input;
    ObjectOutputStream output;
    User user;
    BombermanGame bombermanGame;
    boolean isRunning;
    boolean waiting;

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
            log.debug(e);
        } catch (NullPointerException npe) {
            bombermanGame.gameOver();
            log.debug(npe);
        } catch (ClassNotFoundException e) {
            bombermanGame.gameOver();
            log.debug(e);
        }
    }

    private void initGame() throws IOException, ClassNotFoundException {
        String layout = (String) input.readObject();
        log.debug(layout);
        bombermanGame = new BombermanGame(layout, 1);
        bombermanGame.addObserver(this);
        bombermanGame.gameServerInstances.add(this);
        bombermanGame.init();
        bombermanGame.launch();
    }

    private void gameLoop() throws IOException, ClassNotFoundException {
        isRunning = true;
        log.debug("Game started");
        output.writeObject("Game Started");
        while (isRunning) {
            AgentAction action = (AgentAction) input.readObject();
            ArrayList<AbstractAgent> players = bombermanGame.getPlayers();
            if (!players.isEmpty()) {
                AbstractAgent player = players.get(0);
                // Le placement de la bombe doit être instantané, on byepasse donc la cadence du jeu.
                if (action == AgentAction.PUT_BOMB) {
                    ActionSystem actionSystem = new ActionSystem(bombermanGame);
                    if (actionSystem.isLegalAction(player, action)) {
                        actionSystem.doAction(player, action);
                    }
                }
                if (player != null) player.setAgentAction(action);
            }
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
            log.debug(e);
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
            output.writeObject("$end-Game Won! You may now start a new game.");
        } catch (IOException e) {
            log.debug(e);
        }
        // TODO : méthode à appeler lorsque la partie est terminée
        // termine la partie et renvoie tout les joueurs restants vers le menu de sélection de partie
    }

}
