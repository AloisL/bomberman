package server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import engine.BombermanGame;
import engine.Map;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
            log.info("Instance running");
            input = new ObjectInputStream(socket.getInputStream());
            String layout = (String) input.readObject();
            input.close();
            bombermanGame = new BombermanGame(layout, 100, 1);
            bombermanGame.addObserver(this);
            bombermanGame.init();
            bombermanGame.launch();

            log.info("Connection closed");
        } catch (IOException e) {

        } catch (NullPointerException npe) {

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable arg0, Object arg1) {
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            Map updatedMap = bombermanGame.getMap();
            output.writeObject(updatedMap);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeSocket() throws IOException {
        socket.close();
    }

}
