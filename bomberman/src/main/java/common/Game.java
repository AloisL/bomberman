package common;

import java.util.Observable;

public abstract class Game extends Observable implements Runnable {

    private Integer currentTurn;
    private Integer maxTurn;
    private Boolean isRunning = Boolean.FALSE;
    private Long sleepTime = 650L;

    protected Game(Integer maxTurn) {
        this.maxTurn = maxTurn;
    }

    public void init() {
        currentTurn = 0;
        isRunning = Boolean.TRUE;
        initializeGame();
        setChanged();
        notifyObservers();
    }

    @Override
    public void run() {
        while (isRunning) {
            step();
            try {
                if (!Thread.currentThread().isInterrupted())
                    Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void step() {
        if (currentTurn < maxTurn && gameContinue()) {
            currentTurn += 1;
            takeTurn();
        } else {
            isRunning = Boolean.FALSE;
            gameOver();
        }
        setChanged();
        notifyObservers();
    }

    public void stop() {
        isRunning = Boolean.FALSE;
        setChanged();
        notifyObservers();
    }

    public void launch() {
        isRunning = Boolean.TRUE; // Permet le lancement du jeu après la mise en pause.
        setChanged();
        notifyObservers();
        Thread thread = new Thread(this);
        thread.start();
    }

    public abstract void initializeGame();

    public abstract void takeTurn();

    public abstract void gameOver();

    public abstract boolean gameContinue();

    public Integer getCurrentTurn() {
        return currentTurn;
    }

    public void setSleepTime(Long sleepTime) {
        this.sleepTime = sleepTime;
    }

    public Boolean getIsRunning() {
        return isRunning;
    }

}
