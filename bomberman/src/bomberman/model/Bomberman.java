package bomberman.model;

import bomberman.model.engine.Map;
import common.model.Game;

public class Bomberman extends Game {
    private Map map;

    public Bomberman(Integer maxTurn) {
        super(maxTurn);
        setMapFromLayoutPath("res/layouts/alone.lay");
    }

    @Override
    public void initializeGame() {
        System.out.println("Le jeu est initialisé !");
    }

    @Override
    public void takeTurn() {
        System.out.println("Tour " + getCurrentTurn() + " du jeu en cours");
    }

    @Override
    public void gameOver() {
        System.out.println("Le jeu est fini");
    }

    @Override
    public boolean gameContinue() {
        return true;
    }

    public Map getMap() {
        return map;
    }

    public void setMapFromLayoutPath(String layout) {
        try {
            this.map = new Map(layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
