package bomberman.controller;

import bomberman.model.Bomberman;
import bomberman.model.engine.Map;
import bomberman.view.BombermanView;
import common.controller.Controller;
import common.view.CommandView;

public class BombermanController implements Controller {
    private Bomberman bomberman;
    private BombermanView bombermanView;
    private CommandView commandView;

    public BombermanController(Bomberman bomberman) {
        this.bomberman = bomberman;
        bombermanView = new BombermanView(this);
        commandView = new CommandView(this, "Bomberman Command");
        this.bomberman.addObserver(commandView);
        this.bomberman.addObserver(bombermanView);
    }

    @Override
    public void init() {
        if (bomberman.getIsRunning()) bomberman.stop();
        bomberman.init();
    }

    @Override
    public void run() {
        bomberman.launch();
    }

    @Override
    public void step() {
        bomberman.step();
    }

    @Override
    public void pause() {
        bomberman.stop();
    }

    @Override
    public void setTime(Integer turnBySec) {
        Long sleepTime = (long) 1000 / turnBySec;
        bomberman.setSleepTime(sleepTime);
    }

    public Map getMap() {
        return bomberman.getMap();
    }

}
