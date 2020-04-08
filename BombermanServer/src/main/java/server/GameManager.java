package server;

import engine.BombermanGame;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class GameManager {

    final static Logger log = (Logger) LogManager.getLogger(GameManager.class);

    static ArrayList<BombermanGame> onlineGames = new ArrayList<>();

    /**
     * En fonction du layout choisi:
     * Cherche une partie en attente de démarrage non pleine ou créer une nouvelle partie
     *
     * @param layout
     * @return
     */
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

    /**
     * Retourne le nombre max de joueurx en fonction de la carte
     *
     * @param layout
     * @return
     */
    private static int getMaxPlayers(String layout) {
        // TODO: Compter le nombre de 'B' par layout pour obtenir le nombre de max de joueurs de manière automatique
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

    /**
     * Démarre la partie si et seulement si le nombre de joueurs max est atteint et les joueurs sont tous prêts
     *
     * @param bombermanGame
     * @throws IOException
     */
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

    /**
     * Supprimer de la liste des parties en cours la partie passée en paramètre
     *
     * @param bombermanGame
     */
    public static void closeGame(BombermanGame bombermanGame) {
        if (onlineGames.contains(bombermanGame))
            onlineGames.remove(bombermanGame);
    }

    public static void sendStats(BombermanGame bombermanGame) {
        String token = GameServer.serverToken;
        String dateDebut;
        String dateFin;
        String winner;

        if (bombermanGame.dateDebut != null)
            dateDebut = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(bombermanGame.dateDebut);
        else dateDebut = "--- error_datedebut ---";
        if (bombermanGame.dateFin != null)
            dateFin = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(bombermanGame.dateFin);
        else dateFin = "--- error_datefin ---";
        if (bombermanGame.winner != null)
            winner = bombermanGame.winner;
        else winner = "--- No winner ---";

        log.debug(dateDebut);
        log.debug(dateFin);
        log.debug(winner);

        sendStatForUser(token, winner, "oui", dateDebut, dateFin);

        ArrayList<String> loosers = bombermanGame.loosers;
        for (String user : loosers) {
            log.debug("looser = " + user);
            sendStatForUser(token, user, "non", dateDebut, dateFin);
        }

    }

    private static void sendStatForUser(String token, String user, String victoire, String dateDebut, String dateFin) {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("bombermanToken", token)
                .add("username", user)
                .add("victoire", victoire)
                .add("dateDebut", dateDebut)
                .add("dateFin", dateFin)
                .build();

        Request request = new Request.Builder()
                .url("http://" + GameServer.server + ":" + GameServer.apiPort + "/bomberman/api/addHistory")
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 200) {
                log.debug("Stats sent for user: " + user);
            } else
                log.error("Error, stats lost for user: " + user);
        } catch (IOException e) {
            log.error(e.getStackTrace(), e);
        }
    }

}
