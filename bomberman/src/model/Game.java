package model;

import java.util.Observable;

public abstract class Game extends Observable implements Runnable {

    protected Integer turn;
    protected Integer maxturn;
    protected Boolean isRunning;

    protected Thread thread;
    protected Long sleepTime;

    public Game(Integer maxturn, Long sleepTime) {
        this.maxturn = maxturn;
        this.sleepTime = sleepTime;
    }

    public void init() {
        turn = 0;
        isRunning = Boolean.TRUE;
        initializeGame();
        setChanged();
        notifyObservers();
    }

    @Override
    public void run() {
        while (isRunning.booleanValue()) {
            step();
            try {
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


    public abstract void initializeGame();

    public abstract void takeTurn();

    public abstract void gameOver();

    public abstract boolean gameContinue();

    public Integer getTurn() {
        return turn;
    }

    public Integer getMaxturn() {
        return maxturn;
    }

    public Boolean getIsRunning() {
        return isRunning;
    }

    public Long getSleepTime() {
        return sleepTime;
    }
}
