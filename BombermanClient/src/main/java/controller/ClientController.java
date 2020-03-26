package controller;

import client.ClientView;
import client.PanelBomberman;
import common.BombermanDTO;
import common.enums.AgentAction;
import common.enums.GameState;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientController implements Runnable {

    final static Logger log = (Logger) LogManager.getLogger(ClientController.class);

    public GameState gameState;
    ClientView clientView;
    BombermanDTO bombermanDTO;
    int lifes;
    String layout;

    Thread instance;
    long sleepTime = 50;

    Socket socket;
    ObjectInputStream input;
    ObjectOutputStream output;
    boolean isRunning = true;

    String server;
    int gamePort = 8090;
    int apiPort = 8080;
    AgentAction action;
    private String token;

    public ClientController() {
    }

    public void initConnection(String layout) {
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
            socket = new Socket(server, gamePort);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            output.writeObject(layout);

            bombermanDTO = (BombermanDTO) input.readObject();
            PanelBomberman panelBomberman = new PanelBomberman(bombermanDTO);
            clientView.addPanelBomberman(panelBomberman);
            log.debug(bombermanDTO.toString());
            clientView.repaint();

            while (isRunning) {
                bombermanDTO = (BombermanDTO) input.readObject();
                clientView.getPanelBomberman().setInfoGame(bombermanDTO.getBreakableWalls(), bombermanDTO.getInfoAgents(),
                        bombermanDTO.getInfoItems(), bombermanDTO.getInfoBombs());
                clientView.setInfo(bombermanDTO.toString());
                clientView.setVisible(true);
                clientView.repaint();
                log.debug(bombermanDTO.toString());
            }

            socket.close();
        } catch (UnknownHostException e) {
            log.debug(e);
        } catch (IOException e) {
            log.debug(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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

    public void updatePlayerAction(AgentAction action) throws IOException {
        this.action = action;
        output.writeObject(action);
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

    public BombermanDTO getBombermanDTO() {
        return bombermanDTO;
    }

    public int getLifes() {
        return lifes;
    }

}
