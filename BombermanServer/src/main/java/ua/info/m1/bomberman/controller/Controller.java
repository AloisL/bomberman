package ua.info.m1.bomberman.controller;

public interface Controller {
    void init();

    void run();

    void step();

    void pause();

    void gameOver();
}
