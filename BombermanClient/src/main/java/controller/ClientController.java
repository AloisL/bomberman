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
import java.net.UnknownHostException;
import java.util.Observable;

public class ClientController extends Observable implements Runnable {

    final static Logger log = (Logger) LogManager.getLogger(ClientController.class);

    ClientView clientView;
    BombermanDTO bombermanDTO;
    String layout;
    int lifes;
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
            log.debug("Connection initialized");
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            output.writeObject(layout);

            isRunning = false;
            isWaiting = true;
            // TODO : ready with ready button
            output.writeObject("$ready");
            do {
                String startString = (String) input.readObject();
                setInfo(startString);
                if (startString.equals("$start")) {
                    isWaiting = false;
                    isRunning = true;
                }
            } while (isWaiting);


            log.debug("running=" + isRunning);
            while (isRunning) {
                Object receivedObject = input.readObject();
                log.debug("object received= " + receivedObject);
                if (receivedObject instanceof String) {
                    String str = (String) receivedObject;
                    if (str.startsWith("$end")) {
                        stop();
                        setInfo(str.substring(5));
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
            log.debug("Connection closed");
            socket.close();
            clientView.postGame();
        } catch (UnknownHostException e) {
            log.debug(e);
            stop();
            clientView.postGame();
        } catch (IOException e) {
            log.debug(e);
            stop();
            clientView.postGame();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            stop();
            clientView.postGame();
        }
    }

    public void start() {
        isRunning = true;
    }

    public void stop() {
        setInfo("Game aborted");
        isRunning = false;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updatePlayerAction(AgentAction action) throws IOException {
        if (isRunning) {
            this.action = action;
            output.writeObject(action);
        }
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
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            int responseCode = response.code();
            token = response.body().string();
            log.debug("token --> " + token);
            if (!token.equals("") && responseCode == 200) {
                setInfo("Connection successful. Choose a map and press READY.");
                return token;
            }
            setInfo("Connection failed. Try again.");
        } catch (IOException e) {
            setInfo(e.getMessage());
            log.debug(e);
        }
        return null;
    }

    public void setClientView(ClientView clientView) {
        this.clientView = clientView;
        addObserver(clientView);
    }

    private void setInfo(String info) {
        clientView.setInfo(info);
    }

    public BombermanDTO getBombermanDTO() {
        return bombermanDTO;
    }

    public int getLifes() {
        return lifes;
    }

}
