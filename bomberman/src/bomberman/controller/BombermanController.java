package bomberman.controller;

import bomberman.model.Bomberman;
import bomberman.model.engine.Map;
import bomberman.view.BombermanView;
import bomberman.view.CommandView;
import common.Controller;

public class BombermanController implements Controller {

    private Bomberman bomberman;
    private BombermanView bombermanView;
    private CommandView commandView;

    public BombermanController(int maxTurn) {
        this.bomberman = new Bomberman(maxTurn);
        commandView = new CommandView(this, "Bomberman Command");
        bombermanView = new BombermanView(this);
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

    public void changeLayout() {
        // Suppresion de la vue précédente
        bomberman.deleteObserver(bombermanView);
        bombermanView.closeWindow();

        String layout = "res/layouts/" + commandView.getLayout();
        bomberman.setMapFromLayoutPath(layout);
        bombermanView = new BombermanView(this);
        bomberman.addObserver(bombermanView);
        commandView.addPanelBomberman(bombermanView.getPanelBomberman());
    }

    public Map getMap() {
        return bomberman.getMap();
    }

}
