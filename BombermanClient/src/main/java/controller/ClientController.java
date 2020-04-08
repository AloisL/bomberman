package controller;

import client.ClientView;
import client.PanelBomberman;
import common.BombermanDTO;
import common.enums.AgentAction;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;

public class ClientController extends Observable implements Runnable {

    final static Logger log = (Logger) LogManager.getLogger(ClientController.class);
    String token;
    ClientView clientView;
    BombermanDTO bombermanDTO;
    String layout;
    AgentAction action;
    Thread instance;
    String server;
    int gamePort = 8090;
    int apiPort = 8080;
    volatile Socket socket;
    ObjectInputStream input;
    ObjectOutputStream output;
    volatile boolean isRunning;
    volatile boolean isWaiting;
    private String username;

    public ClientController() {
    }

    /**
     * Méthode de démarrage du thread de gestion d'une partie (le thread est stocké dans l'instance de la classe)
     *
     * @param layout
     */
    public void init(String layout) {
        this.layout = layout;
        instance = new Thread(this);
        instance.start();
    }

    /**
     * Méthode fermmant le socket et tuant le thread courant (appelé en fin de partie)
     */
    public void closeConnection() {
        try {
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        instance.stop();
        log.debug("Connection aborted");
    }

    /**
     * Méthode daemon d'initalisation et de gestion complète d'une partie de jeu.
     */
    @Override
    public void run() {
        try {
            // Initialisation de la connexion au jeu
            initConnection();

            // Boucle d'attente de lancement de la partie (tous les jouers doivent être prêts)
            waitBeforeStartLoop();

            // Passage de l'UI en mode "Ingame"
            clientView.panelInput.inGameMode();

            // Boucle de jeu (Gère toutes les mises à jour de l'ui)
            gameLoop();

            // Fermeture de la connexion une fois la boucle de jeu terminée
            socket.close();
            log.debug("Connection closed");
        } catch (Exception e) {
            log.error(e.getStackTrace(), e);
            // En cas d'exception, la méthode stop est appelée pour retourner sur le panel de recherche de partie
            stop(e.toString());
        }

    }

    /**
     * Initisalisation du socket et des i/o
     *
     * @throws IOException
     */
    private void initConnection() throws IOException {
        socket = new Socket(server, gamePort);
        log.debug("Connection initialized");

        // Si connection au serveur effectuée, débloquage du bouton prêt
        clientView.serverConnectedAllowReady();

        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());

        // Envoi du token de connection au server
        output.writeObject(username);

        // Envoi du layout choisi au server
        output.writeObject(layout);

        isRunning = false;
        isWaiting = true;
    }

    /**
     * Boucle d'attente pré-game
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void waitBeforeStartLoop() throws IOException, ClassNotFoundException {
        do {
            String startString = (String) input.readObject();
            setInfo(startString);
            if (startString.equals("$start")) {
                isWaiting = false;
                isRunning = true;
            }
        } while (isWaiting);
    }

    /**
     * boucle de jeu
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void gameLoop() throws IOException, ClassNotFoundException {
        log.debug("running=" + isRunning);
        while (isRunning) {
            Object receivedObject = input.readObject();
            log.debug("object received= " + receivedObject);
            if (receivedObject instanceof String) {
                String str = (String) receivedObject;
                if (str.startsWith("$end")) {
                    stop(str.substring(5));
                } else {
                    setInfo((String) receivedObject);
                }
            } else if (receivedObject instanceof BombermanDTO) {
                bombermanDTO = (BombermanDTO) receivedObject;
                if (clientView.getPanelBomberman() == null) {
                    PanelBomberman panelBomberman = new PanelBomberman(bombermanDTO);
                    clientView.addPanelBomberman(panelBomberman);
                    log.debug(bombermanDTO.toString());
                    clientView.repaint();
                }
                log.debug(bombermanDTO.toString());
                setChanged();
                notifyObservers();
            }
        }
    }

    public void stop(String info) {
        setInfo(info);
        isRunning = false;
        clientView.postGame();
        closeConnection();
    }

    /**
     * Méthode appelée pour définir la prochaine action utilisateur à effectuer par le serveur.
     * A noter que ce n'est qu'une mise à jour de la prochaine action
     * L'action est effectuée que lorsque le serveur l'autorise (au début d'un tour de jeu)
     * Seule eception: le placement d'une bombe qui est instantané pour les bienfaits de la fluidité du jeu
     */
    public void updatePlayerAction(AgentAction action) throws IOException {
        if (isRunning) {
            this.action = action;
            output.writeObject(action);
        }
    }

    /**
     * Permet de définir l'état prêt ou non du joueur
     *
     * @param ready
     */
    public void ready(boolean ready) {
        try {
            if (output != null) {
                if (ready) {
                    output.writeObject("$ready");
                    log.debug("ready");
                } else {
                    output.writeObject("$unready");
                    log.debug("unready");
                }
            } else stop("Server offline");
        } catch (IOException e) {
            log.error(e.getStackTrace().toString());
        }
    }

    /**
     * @param username
     * @param password
     * @return token de connexion
     */
    public String login(String server, String username, String password) {
        this.server = server;
        this.username = username;
        String url = "http://" + server + ":" + apiPort + "/bomberman/api/login?username=" + username + "&password=" + password;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            int responseCode = response.code();
            token = response.body().string();
            log.debug("token --> " + token);
            if (!token.equals("") && responseCode == 200) {
                setInfo("Connection successful \n Search for a server");
                return token;
            }
            setInfo("Connection failed, try again");
        } catch (IOException e) {
            setInfo(e.getMessage());
            log.error(e.getStackTrace().toString());
        }
        return null;
    }

    public void setClientView(ClientView clientView) {
        this.clientView = clientView;
        addObserver(clientView);
    }

    public void setInfo(String info) {
        clientView.setInfo(info);
    }

    public BombermanDTO getBombermanDTO() {
        return bombermanDTO;
    }

}
