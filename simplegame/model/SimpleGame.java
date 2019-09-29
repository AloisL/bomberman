package simplegame.model;

import common.model.Game;

public class SimpleGame extends Game {

    public SimpleGame(Integer maxTurn) {
        super(maxTurn);
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
        Thread.currentThread().interrupt();
    }

    @Override
    public boolean gameContinue() {
        return true;
    }
}