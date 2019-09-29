package simplegame.controller;

import common.controller.Controller;
import common.view.CommandView;
import simplegame.model.SimpleGame;
import simplegame.view.SimpleGameView;

public class SimpleGameController implements Controller {
    private SimpleGame game;
    private CommandView commandView;
    private SimpleGameView viewSimpleGame;

    public SimpleGameController(SimpleGame game) {
        this.game = game;
        commandView = new CommandView(this, "SimpleGame Command");
        viewSimpleGame = new SimpleGameView(this, "SimpleGame");
        this.game.addObserver(commandView);
        this.game.addObserver(viewSimpleGame);
    }

    @Override
    public void init() {
        if (game.getIsRunning()) game.stop();
        game.init();
    }

    @Override
    public void run() {
        game.launch();
    }

    @Override
    public void step() {
        game.step();
    }

    @Override
    public void pause() {
        game.stop();
    }

    @Override
    public void setTime(Integer turnBySec) {
        Long sleepTime = (long) 1000 / turnBySec;
        game.setSleepTime(sleepTime);
    }

}
