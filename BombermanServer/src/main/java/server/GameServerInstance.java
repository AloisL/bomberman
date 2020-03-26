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
    boolean isRunning = true;

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

            String layout = (String) input.readObject();
            log.debug(layout);

            bombermanGame = new BombermanGame(layout, 10000, 1);
            bombermanGame.addObserver(this);
            bombermanGame.init();
            bombermanGame.launch();
            log.debug("Game started");

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

            log.debug("Game terminated");
            socket.close();
        } catch (IOException e) {
            log.debug(e);
        } catch (NullPointerException npe) {
            log.debug(npe);
        } catch (ClassNotFoundException e) {
            log.debug(e);
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
            log.debug(bombermanDTO.toString());
            output.writeObject(bombermanDTO);
            log.debug(bombermanDTO.toString() + " sent");
        } catch (IOException e) {
            log.debug(e);
        }
    }

}
