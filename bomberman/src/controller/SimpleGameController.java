package controller;

import model.SimpleGame;

public class SimpleGameController implements Controller {

    SimpleGame game;

    public SimpleGameController(SimpleGame game) {
        this.game = game;
    }

    public void init() {
        if (game.getIsRunning()) game.stop();
        game.init();
    }

    public void run() {
        game.launch();
    }

    public void step() {
        game.step();
    }

    public void pause() {
        game.stop();
    }

    public void setTime(Integer turnBySec) {
        Long sleepTime = (long) 1000 / turnBySec;
        game.setSleepTime(sleepTime);
    }

}
