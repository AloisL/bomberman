package model;

import java.util.Observable;

public abstract class Game extends Observable implements Runnable {
    private Integer turn;
    private Integer maxturn;
    private Boolean isRunning = Boolean.FALSE;

    private Thread thread;

    private Long sleepTime;

    public Game(Integer maxturn, Long sleepTime) {
        this.maxturn = maxturn;
        this.sleepTime = sleepTime;
    }

    public void init() {
        turn = 0;
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
        if (turn < maxturn && gameContinue()) {
            turn += 1;
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
        isRunning = Boolean.TRUE;
        setChanged();
        notifyObservers();
        thread = new Thread(this);
        thread.start();
    }

    public abstract void initializeGame();

    public abstract void takeTurn();

    public abstract void gameOver();

    public abstract boolean gameContinue();

    public Integer getTurn() {
        return turn;
    }

    public void setSleepTime(Long sleepTime) {
        this.sleepTime = sleepTime;
    }

    public Boolean getIsRunning() {
        return isRunning;
    }
}
