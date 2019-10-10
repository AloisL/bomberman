package bomberman.controller;

import bomberman.model.Bomberman;
import bomberman.model.engine.Map;
import bomberman.view.BombermanView;
import bomberman.view.PanelBomberman;
import common.Controller;

public class BombermanController implements Controller {

    private Bomberman bomberman;
    private PanelBomberman bombermanPanel;
    private BombermanView bombermanView;

    public BombermanController(int maxTurn) {
        bomberman = new Bomberman(maxTurn);
        bombermanView = new BombermanView(this, "Bomberman Command");
        bomberman.addObserver(bombermanView);
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
        String layout = "res/layouts/" + bombermanView.getLayout();
        bomberman.setMapFromLayoutPath(layout);
        bombermanPanel = new PanelBomberman(getMap());
        bombermanView.addPanelBomberman(bombermanPanel);
    }

    public Map getMap() {
        return bomberman.getMap();
    }

    public Bomberman getBomberman() {
        return bomberman;
    }

}
