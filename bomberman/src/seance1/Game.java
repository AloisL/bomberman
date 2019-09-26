package seance1;

public abstract class Game implements Runnable {

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
    }

    @Override
    public void run() {
        while (isRunning) {
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
    }

    public void stop() {
        isRunning = Boolean.FALSE;
    }

    public void launch()    {
        isRunning = Boolean.FALSE;
        thread = new Thread(this);
        thread.start();
    }

    public abstract void initializeGame();

    public abstract void takeTurn();

    public abstract void gameOver();

    public abstract boolean gameContinue();

}
