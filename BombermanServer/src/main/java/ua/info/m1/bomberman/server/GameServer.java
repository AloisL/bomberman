/*
package ua.info.m1.bomberman.server;

import ua.info.m1.bomberman.game.engine.BombermanGame;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

*/
/**
 * Server
 *//*

public class GameServer {

    BombermanGame bombermanGame;
    String serverIp;
    int port; // le port d’écoute
    ServerSocket serverSocket;
    ArrayList<GameServerInstance> gameServerInstances = new ArrayList<>();

    GameServer(BombermanGame bombermanGame, String serverIp, int port) {
        this.bombermanGame = bombermanGame;
        this.serverIp = serverIp;
        this.port = port;
    }

    void start() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server listenning on port: " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Connection accepted");

                GameServerInstance gameServerInstance = new GameServerInstance(this, socket);
                gameServerInstances.add(gameServerInstance);
                bombermanGame.addObserver(gameServerInstance);

                Thread instance = new Thread(gameServerInstance);
                instance.start();
            }
        } catch (IOException e) {
            System.out.println("GameServer ERROR" + e.getMessage());
        }

    }

}*/
