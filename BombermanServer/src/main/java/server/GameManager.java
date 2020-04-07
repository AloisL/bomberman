package server;

import engine.BombermanGame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;

public class GameManager {

    final static Logger log = (Logger) LogManager.getLogger(GameManager.class);

    static ArrayList<BombermanGame> onlineGames = new ArrayList<>();

    public static BombermanGame findOrCreateGame(String layout) {
        log.debug("Online games count: " + onlineGames.size());
        for (BombermanGame onlineGame : onlineGames) {
            if (onlineGame.layout.equals(layout)) {
                if (onlineGame.isRunning == false &&
                        onlineGame.gameServerInstances.size() < onlineGame.maxPlayers) return onlineGame;
            }
        }
        BombermanGame newBombermanGame = new BombermanGame(layout, getMaxPlayers(layout));
        onlineGames.add(newBombermanGame);
        return newBombermanGame;
    }

    private static int getMaxPlayers(String layout) {
        if (layout.equals("alone.lay")) return 1;
        else if (layout.equals("arene.lay")) return 10;
        else if (layout.equals("exemple.lay")) return 3;
        else if (layout.equals("jeu1.lay")) return 4;
        else if (layout.equals("jeu_symetrique.lay")) return 4;
        else if (layout.equals("niveau1.lay")) return 1;
        else if (layout.equals("niveau2.lay")) return 1;
        else if (layout.equals("niveau3.lay")) return 1;
        else return 0;
    }

    public static void startIfPossible(BombermanGame bombermanGame) throws IOException {
        int maxPlayers = bombermanGame.maxPlayers;
        log.debug("Max player count: " + maxPlayers);

        int playersReady = 0;
        for (GameServerInstance gmi : bombermanGame.gameServerInstances) {
            if (gmi.isReady) playersReady++;
        }
        log.debug("Players ready: " + playersReady);

        if (maxPlayers == playersReady) {
            for (GameServerInstance gmi : bombermanGame.gameServerInstances) {
                gmi.sendText("$start");
                gmi.isWaiting = false;
                gmi.isRunning = true;
            }
            bombermanGame.init();
            bombermanGame.launch();
        } else {
            for (GameServerInstance gmi : bombermanGame.gameServerInstances) {
                gmi.sendText("Game found, players ready: " + playersReady + "/" + maxPlayers);
            }
        }
    }

    public static void closeGame(BombermanGame bombermanGame) {
        if (onlineGames.contains(bombermanGame))
            onlineGames.remove(bombermanGame);
    }
}
