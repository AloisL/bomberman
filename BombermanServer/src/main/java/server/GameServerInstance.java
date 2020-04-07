package server;

import common.BombermanDTO;
import common.enums.AgentAction;
import engine.BombermanGame;
import engine.Map;
import engine.agents.BombermanAgent;
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
    public BombermanAgent bombermanAgent = null;
    GameServer gameServer;
    Socket socket;
    ObjectInputStream input;
    ObjectOutputStream output;
    User user;
    BombermanGame bombermanGame;
    volatile boolean isRunning;
    volatile boolean isReady;
    volatile boolean isWaiting;
    boolean hasLost;

    GameServerInstance(GameServer gameServer, Socket socket) {
        this.gameServer = gameServer;
        this.socket = socket;
    }

    @Override
    public void run() {
        hasLost = false;
        try {
            log.debug("Instance running");

            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            initGame();
            gameLoop();

            bombermanGame.removeInstanceLeftovers(this);
            socket.close();
            log.debug("Instance terminated");
        } catch (Exception e) {
            bombermanGame.removeInstanceLeftovers(this);
            log.error(e.getMessage(), e);
        }
    }

    private void initGame() throws IOException, ClassNotFoundException, InterruptedException {
        String layout = (String) input.readObject();
        sendText("Searching for " + layout + "game");
        log.debug(layout);
        isReady = false;
        isWaiting = true;

        bombermanGame = GameManager.findOrCreateGame(layout);
        sendText("Game found, press ready");
        bombermanGame.addObserver(this);
        bombermanGame.gameServerInstances.add(this);

        log.debug("iswaiting = " + isWaiting);
        do {
            Object objectInput = input.readObject();
            if (objectInput instanceof String) {
                String cmd = (String) objectInput;
                log.debug(cmd);
                if (cmd.equals("$ready"))
                    ready(true);
                if (cmd.equals("$unready"))
                    ready(false);
            }
        } while (isWaiting);
    }

    private void ready(boolean ready) throws IOException, ClassNotFoundException {
        log.debug("ready = " + ready);
        this.isReady = ready;
        GameManager.startIfPossible(bombermanGame);
    }

    private void gameLoop() throws IOException, ClassNotFoundException {
        isRunning = true;
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
            updateInfos();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void lost() {
        hasLost = true;
    }

    /**
     * Méthode appelées en fin de partie, change les texte des infos
     */
    public void terminate() {
        try {
            if (hasLost) sendText("$end-Game Lost! You may now start a new game.");
            else
                sendText("$end-Game Won! You may now start a new game.");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void sendText(String text) throws IOException {
        output.writeObject(text);
    }

    /**
     * Update des infos (vies bonus, portée bombe, etc...)
     */
    public void updateInfos() throws IOException {
        int nbVies = bombermanAgent.getNbLifes();
        if (nbVies > 0)
            output.writeObject("Life count: " + bombermanAgent.getNbLifes() + "/3");
        else output.writeObject("You are dead");
    }
}
