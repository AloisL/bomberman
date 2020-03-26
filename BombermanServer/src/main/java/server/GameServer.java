package server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer {

    final static Logger log = (Logger) LogManager.getLogger(GameServer.class);

    int port;
    ServerSocket serverSocket;
    ArrayList<GameServerInstance> gameServerInstances = new ArrayList<>();

    public GameServer(int port) {
        this.port = port;
        start();
    }

    private void start() {
        try {
            serverSocket = new ServerSocket(port);
            log.info("Server listenning on port {}", port);
            while (true) {
                Socket socket = serverSocket.accept();
                log.info("Connection accepted");

                GameServerInstance gameServerInstance = new GameServerInstance(this, socket);
                gameServerInstances.add(gameServerInstance);

                Thread instance = new Thread(gameServerInstance);
                instance.start();
            }
        } catch (IOException e) {
            log.info("GameServer ERROR" + e.getMessage());
        }

    }

}
