package common;

public interface Controller {
    void init();

    void run();

    void step();

    void pause();

    void setTime(Integer turnPerSec);

    void gameOver();
}
