package bomberman.controller;

import bomberman.model.BombermanGame;
import bomberman.model.engine.Map;
import bomberman.view.BombermanView;
import bomberman.view.PanelBomberman;
import common.Controller;

public class BombermanController implements Controller {

    private BombermanGame bombermanGame;
    private PanelBomberman bombermanPanel;
    private BombermanView bombermanView;

    public BombermanController(int maxTurn) {
        bombermanGame = new BombermanGame(maxTurn);
        bombermanView = new BombermanView(this, "Bomberman Command");
        bombermanGame.addObserver(bombermanView);
    }

    @Override
    public void init() {
        if (bombermanGame.getIsRunning()) bombermanGame.stop();
        bombermanGame.init();
    }

    @Override
    public void run() {
        bombermanGame.launch();
    }

    @Override
    public void step() {
        bombermanGame.step();
    }

    @Override
    public void pause() {
        bombermanGame.stop();
    }

    @Override
    public void setTime(Integer turnBySec) {
        Long sleepTime = (long) 1000 / turnBySec;
        bombermanGame.setSleepTime(sleepTime);
    }

    public void changeLayout() {
        String layout = "res/layouts/" + bombermanView.getLayout();
        bombermanGame.setMapFromLayoutPath(layout);
        bombermanPanel = new PanelBomberman(getMap());
        bombermanView.addPanelBomberman(bombermanPanel);
    }

    public Map getMap() {
        return bombermanGame.getMap();
    }

    public BombermanGame getBombermanGame() {
        return bombermanGame;
    }

}
