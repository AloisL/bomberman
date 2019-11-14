package bomberman.controller;

import bomberman.model.BombermanGame;
import bomberman.model.engine.Map;
import bomberman.view.BombermanView;
import bomberman.view.PanelBomberman;
import common.Controller;

/**
 * Classe du controleur du jeu
 */
public class BombermanController implements Controller {

    private BombermanGame bombermanGame;
    private PanelBomberman bombermanPanel;
    private BombermanView bombermanView;

    /**
     * Constructeur
     *
     * @param maxTurn nombre maximal de tours
     */
    public BombermanController(int maxTurn) {
        bombermanGame = new BombermanGame(maxTurn);
        bombermanView = new BombermanView(this, "Bomberman Command");
        bombermanGame.addObserver(bombermanView);
    }

    /**
     * Méthode d'initialisation du jeu
     */
    @Override
    public void init() {
        if (bombermanGame.getIsRunning()) bombermanGame.stop();
        bombermanGame.init();
    }

    /**
     * Méthode de lancement du jeu
     */
    @Override
    public void run() {
        bombermanGame.launch();
    }

    /**
     * Méthode de lancement d'un tour de jeu
     */
    @Override
    public void step() {
        bombermanGame.step();
    }

    /**
     * Méthode mettant le jeu en pause
     */
    @Override
    public void pause() {
        bombermanGame.stop();
    }

    /**
     * Setter du nombre de tours par secondes et conversion en temps de pause du thread
     *
     * @param turnBySec Le nombre de tours par secondes
     */
    @Override
    public void setTime(Integer turnBySec) {
        Long sleepTime = (long) 1000 / turnBySec;
        bombermanGame.setSleepTime(sleepTime);
    }

    /**
     * Méthode de changement du layout du jeu en utilisant le layout choisi dans la vue
     */
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
