/*
package ua.info.m1.bomberman.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

*/
/**
 * ServerInstance
 *//*

public class GameServerInstance implements Runnable, Observer {

    GameServer gameServer;
    Socket socket;
    BufferedReader entree;
    DataOutputStream sortie;
    String str = "";
    String id;
    boolean playing = false;

    GameServerInstance(GameServer gameServer, Socket so) {
        this.gameServer = gameServer;
        this.socket = so;
    }

    @Override
    public void run() {
        try {
            System.out.println("Instance running");
            entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            sortie = new DataOutputStream(socket.getOutputStream());

            // SetPlayer
            sortie.writeChars("Choisissez votre id:\n");
            id = entree.readLine();
            playing = true;
            player = new Player(id);
            gameServer.game.addPlayer(player);
            while (!str.equals("exit")) {
                str = entree.readLine(); // on lit ce qui arrive
                if (playing == true && !str.equals("exit")) {
                    try {
                        int x = Integer.parseInt(str);
                        gameServer.game.propose(player, Integer.valueOf(str));
                    } catch (NumberFormatException e) {
                        sortie.writeChars("Please enter a valid integer\n");
                    }
                }

            }

            gameServer.game.removePlayer(player);
            gameServer.game.deleteObserver(this);
            socket.close();
            System.out.println("Connection closed");
        } catch (IOException e) {
            if (player != null) gameServer.game.removePlayer(player);
            gameServer.game.deleteObserver(this);
            System.out.println("GameServerInstance ERROR: " + e.getMessage());
        } catch (NullPointerException npe) {
            gameServer.game.deleteObserver(this);
            if (player != null) gameServer.game.removePlayer(player);

        }
    }

    @Override
    public void update(Observable arg0, Object arg1) {
        String message = (String) arg1;
        try {
            if (playing) sortie.writeChars(message);
        } catch (IOException e) {
            System.out.println("ERROR : " + e.getMessage());
        }

    }

}*/
