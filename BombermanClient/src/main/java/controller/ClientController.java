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

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;

public class ClientController extends Observable implements Runnable {

    final static Logger log = (Logger) LogManager.getLogger(ClientController.class);

    Thread instance;
    boolean isRunning;
    long sleepTime = 100;

    ClientView clientView;
    public GameState gameState;
    Map map;
    int lifes;
    String layout;

    private String token;

    Socket so;
    DataInputStream entree;
    PrintWriter sortie;
    String s; // le serveur
    int p; // le port de connexion

    public ClientController() {

        Socket so;
        DataInputStream entree;
        PrintWriter sortie;
        s = "127.0.0.1";
        p = 80;

        log.debug("Server --> " + s);
        log.debug("Port --> " + p);
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
        isRunning = true;
        while (isRunning) {
            try {
                so = new Socket(s, p); // on connecte un socket
                sortie = new PrintWriter(so.getOutputStream(), true);
                entree = new DataInputStream(so.getInputStream());

                log.debug("init() --> " + layout);

                setInfo("Waiting for the server to find or create a new game.");

                log.debug("Server connected");

                so.close(); // on ferme la connexion
            } catch (UnknownHostException e) {
                log.debug(e.getMessage());
            } catch (IOException e) {
                log.debug(e.getMessage());
            }
            // 1 : update la map communication avec le serveur de jeu
            // 2 : update la vue via le pattern observateur
            setChanged();
            notifyObservers();
            try {
                if (!Thread.currentThread().isInterrupted())
                    Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                isRunning = false;
                e.printStackTrace();
            }
        }
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

    public void step() {
        // useless
        // méthode censée effectuer un tour de jeu
        /**
         * bombermanGame.step();
         */
    }

    public void setTime(int value) {
        // useless
        /**
         * Long sleepTime = (long) 1000 / turnPerSec;
         * bombermanGame.setSleepTime(sleepTime);
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

    public Map getMap() {
        return map;
    }

    public int getLifes() {
        return lifes;
    }

    /**
     * @param login
     * @param password
     * @return token de connexion
     */
    public String login(String login, String password) {
        String url = "http://localhost:8080/bomberman/login?login=" + login + "&password=" + password;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            token = response.body().string();
            log.debug("token --> " + token);
        } catch (IOException e) {
            log.debug("login --> " + e.getMessage());
        }

        if (!token.equals("")) {
            setInfo("Connection successful. Choose a map and press READY.");
            return "token";
        } else {
            setInfo("Connection failed. Try again.");
            return "";
        }

    }

    private void setInfo(String message) {
        clientView.setInfo(message);
    }

    public void setClientView(ClientView clientView) {
        this.clientView = clientView;
    }


}
