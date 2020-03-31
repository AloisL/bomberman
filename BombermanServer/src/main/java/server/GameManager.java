package server;

import engine.BombermanGame;

import java.util.ArrayList;

public class GameManager {

    static ArrayList<BombermanGame> onlineGames;

    public static BombermanGame findOrCreateGame(String layout) {
        for (BombermanGame onlineGame : onlineGames) {
            if (onlineGame.layout.equals(layout)) {
                if (onlineGame.currentPlayers < onlineGame.maxPlayers) return onlineGame;
            }
        }
        return new BombermanGame(layout, getMaxPlayers(layout));
    }

    private static int getMaxPlayers(String layout) {
        if (layout.equals("alone.lay")) return 1;
        else if (layout.equals("arene.lay")) return 10;
        else if (layout.equals("exemple.lay")) return 3;
        else if (layout.equals("jeu1.lay")) return 2;
        else if (layout.equals("jeu_symetrique.lay")) return 4;
        else if (layout.equals("niveau1.lay")) return 1;
        else if (layout.equals("niveau2.lay")) return 1;
        else if (layout.equals("niveau3.lay")) return 1;
        else return 0;
    }

    public void makePlayerReady() {

    }

}
