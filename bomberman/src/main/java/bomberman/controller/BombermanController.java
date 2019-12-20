package bomberman.controller;

import bomberman.model.BombermanGame;
import bomberman.model.engine.ActionSystem;
import bomberman.model.engine.Map;
import bomberman.model.repo.AgentAction;
import bomberman.view.BombermanView;
import bomberman.view.PanelBomberman;
import common.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

/**
 * Classe du controleur du jeu
 */
public class BombermanController implements Controller {

    final static org.apache.logging.log4j.core.Logger log = (Logger) LogManager.getLogger(BombermanController.class);

    private BombermanGame bombermanGame;
    private PanelBomberman bombermanPanel;
    private BombermanView bombermanView;

    /**
     * Constructeur
     *
     * @param maxTurn nombre maximal de tours
     */
    public BombermanController(int maxTurn) {
        bombermanGame = new BombermanGame(maxTurn, 1);
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

    public void updatePlayerAction(AgentAction action) {
        if (action == AgentAction.PUT_BOMB) {
            ActionSystem actionSystem = new ActionSystem(bombermanGame);
            if (actionSystem.isLegalAction(bombermanGame.getPlayers().get(0), action)) {
                actionSystem.doAction(bombermanGame.getPlayers().get(0), action);
            }
        }
        bombermanGame.getPlayers().get(0).setAgentAction(action);
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
     * @param turnPerSec Le nombre de tours par secondes
     */
    @Override
    public void setTime(Integer turnPerSec) {
        Long sleepTime = (long) 1000 / turnPerSec;
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
