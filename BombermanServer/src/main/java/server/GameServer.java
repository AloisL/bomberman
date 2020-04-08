package server;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer {

    final static Logger log = (Logger) LogManager.getLogger(GameServer.class);
    public static String serverToken;
    public static String server;
    public static int apiPort = 8080;
    int port = 8090;
    ServerSocket serverSocket;
    ArrayList<GameServerInstance> gameServerInstances = new ArrayList<>();
    String adminUser = "bomberman";

    public GameServer(String server, String adminPassword) {
        this.server = server;
        serverToken = login(adminUser, adminPassword);
        if (serverToken != null)
            start();
        else
            log.error("Server stopped, api connection failed");
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

    /**
     * @param username
     * @param password
     * @return token de connexion
     */
    public String login(String username, String password) {
        String token;
        String url = "http://" + server + ":" + apiPort + "/bomberman/api/login?username=" + username + "&password=" + password;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            int responseCode = response.code();
            token = response.body().string();
            log.debug("token --> " + token);
            if (!token.equals("") && responseCode == 200) {
                log.debug("Connection successful");
                return token;
            }
            log.error("Connection failed, try again");
        } catch (IOException e) {
            log.error(e.getStackTrace(), e);
        }
        return null;
    }

}
