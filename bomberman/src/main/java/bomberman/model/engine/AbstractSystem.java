package bomberman.model.engine;

import bomberman.model.BombermanGame;
import bomberman.model.agent.AbstractAgent;
import bomberman.model.engine.info.InfoAgent;
import bomberman.model.engine.info.InfoBomb;
import bomberman.model.engine.info.InfoItem;

import java.util.ArrayList;

public abstract class AbstractSystem {

    BombermanGame bombermanGame;
    ArrayList<AbstractAgent> agents;
    ArrayList<InfoAgent> infoAgents;
    ArrayList<InfoBomb> bombs;
    ArrayList<InfoItem> items;
    boolean[][] breakableWalls;

    public AbstractSystem(BombermanGame bombermanGame) {
        this.bombermanGame = bombermanGame;
        agents = bombermanGame.getAgents();
        infoAgents = bombermanGame.getInfoAgents();
        bombs = bombermanGame.getBombs();
        items = bombermanGame.getItems();
        breakableWalls = bombermanGame.getBreakableWalls();
    }

    /**
     * Méthode appelé lors d'un tour de jeu pour chaque sous-sytème
     */
    public abstract void run();

}
