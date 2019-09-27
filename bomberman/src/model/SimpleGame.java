package model;

public class SimpleGame extends Game {

    public SimpleGame(Integer maxturn, Long sleepTime) {
        super(maxturn, sleepTime);
    }

    @Override
    public void initializeGame() {
        System.out.println("Le jeu est initialisé !");
    }

    @Override
    public void takeTurn() {
        System.out.println("Tour " + getTurn() + " du jeu en cours");
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