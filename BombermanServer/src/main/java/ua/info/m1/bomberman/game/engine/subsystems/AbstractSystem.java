package ua.info.m1.bomberman.game.engine.subsystems;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.info.m1.bomberman.game.agents.AbstractAgent;
import ua.info.m1.bomberman.game.engine.BombermanGame;
import ua.info.m1.bomberman.game.engine.infotypes.InfoBomb;
import ua.info.m1.bomberman.game.engine.infotypes.InfoItem;

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
