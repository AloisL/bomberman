package controller;

import client.ClientView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import res.Map;
import res.enums.AgentAction;
import res.enums.GameState;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;

public class ClientController extends Observable implements Runnable {

    final static Logger log = (Logger) LogManager.getLogger(ClientController.class);

    public GameState gameState;
    ClientView clientView;
    Map map;
    int lifes;
    String layout;

    Thread instance;
    long sleepTime = 100;

    Socket socket;
    ObjectInputStream input;
    ObjectOutputStream output;
    boolean isRunning = true;

    String server;
    int gamePort = 8090;
    int apiPort = 8080;
    String action;
    private String token;

    public ClientController() {
    }

    public void run(String layout) {
        this.layout = layout;
        instance = new Thread(this);
        instance.start();
    }

    /**
     * Méthode daemon de communcation avec le serveur permettant d'obtenir toutes les infos du jeu.
     */
    @Override
    public void run() {
        try {
            socket = new Socket(server, gamePort); // on connecte un socket
            output = new ObjectOutputStream(socket.getOutputStream());
            output.writeObject(layout);
            output.close();

            while (isRunning) {
                input = new ObjectInputStream(socket.getInputStream());
                Map updatedMap = (Map) input.readObject();
                log.debug(updatedMap.toString());
                input.close();
                wait(sleepTime);
            }

        } catch (UnknownHostException e) {
            log.debug(e);
        } catch (IOException e) {
            log.debug(e);
        } catch (ClassNotFoundException e) {
            log.debug(e);
        } catch (InterruptedException e) {
            log.debug(e);
        }
        // 1 : update la map communication avec le serveur de jeu
        // 2 : update la vue via le pattern observateur
        setChanged();
        notifyObservers();
        try {
            if (!Thread.currentThread().isInterrupted())
                Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void closeSocket(Socket socket) throws IOException {
        socket.close();
    }


    public void start() {
        // useless
        // méthode censée effectuer lancer le jeu;
        // Sera utilisée pour mettre le joueur dans l'état pret
        // Lorsque tous les joueurs sont prets, le serveur lance la partie
        /**
         * gameState = GameState.GAME_RUNNING;
         * bombermanGame.launch();
         */
    }

    public void pause() {
        // useless
        // méthode censée effectuer mettre en pause le jeu
        /**
         * gameState = GameState.GAME_PAUSED;
         * bombermanGame.stop();
         */
    }

    public void updatePlayerAction(AgentAction action) {
        // envoi de la prochaine action souhaitée au serveur.

        /**
         * ArrayList<AbstractAgent> players = bombermanGame.getPlayers();
         *         if (!players.isEmpty()) {
         *             AbstractAgent player = players.get(0);
         *             // Le placement de la bombe doit être instantané, on byepasse donc la cadence du jeu.
         *             if (action == AgentAction.PUT_BOMB) {
         *                 ActionSystem actionSystem = new ActionSystem(bombermanGame);
         *                 if (actionSystem.isLegalAction(player, action)) {
         *                     actionSystem.doAction(player, action);
         *                 }
         *             }
         *             if (player != null) player.setAgentAction(action);
         *         } else log.debug("updatePlayerAction: Liste des joueurs vide");
         */
    }

    /**
     * @param username
     * @param password
     * @return token de connexion
     */
    public String login(String server, String username, String password) {
        this.server = server;
        String url = "http://" + server + ":" + apiPort + "/bomberman/login?username=" + username + "&password=" + password;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url.toString()).build();
        try (Response response = client.newCall(request).execute()) {
            int responseCode = response.code();
            token = response.body().string();
            log.debug("token --> " + token);
            if (!token.equals("") && responseCode == 200) {
                clientView.setInfo("Connection successful. Choose a map and press READY.");
                return token;
            }
            clientView.setInfo("Connection failed. Try again.");
            clientView.repaint();
        } catch (IOException e) {
            clientView.setInfo(e.getMessage());
            log.debug(e);
        }
        return null;
    }

    public void setClientView(ClientView clientView) {
        this.clientView = clientView;
    }

    public Map getMap() {
        return map;
    }

    public int getLifes() {
        return lifes;
    }

}
