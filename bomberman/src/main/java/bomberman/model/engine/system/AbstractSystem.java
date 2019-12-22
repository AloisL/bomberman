package bomberman.model.engine.system;

import bomberman.model.BombermanGame;
import bomberman.model.agent.AbstractAgent;
import bomberman.model.engine.info.InfoBomb;
import bomberman.model.engine.info.InfoItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.ArrayList;

/**
 * Patron de méthode représentant les sous-sytème
 */
public abstract class AbstractSystem {

    final static Logger log = (Logger) LogManager.getLogger(AbstractSystem.class);

    BombermanGame bombermanGame;
    ArrayList<AbstractAgent> agents;
    ArrayList<AbstractAgent> players;
    ArrayList<InfoBomb> bombs;
    ArrayList<InfoItem> items;
    boolean[][] breakableWalls;

    public AbstractSystem(BombermanGame bombermanGame) {
        this.bombermanGame = bombermanGame;
        agents = bombermanGame.getAgents();
        players = bombermanGame.getPlayers();
        bombs = bombermanGame.getBombs();
        items = bombermanGame.getItems();
        breakableWalls = bombermanGame.getBreakableWalls();
    }

    /**
     * Méthode appelé lors d'un tour de jeu pour chaque sous-sytème
     */
    public abstract void run();

}
