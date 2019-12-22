package bomberman.model;

import bomberman.model.agent.AbstractAgent;
import bomberman.model.agent.AgentFactory;
import bomberman.model.engine.ActionSystem;
import bomberman.model.engine.BombSystem;
import bomberman.model.engine.Map;
import bomberman.model.engine.info.InfoAgent;
import bomberman.model.engine.info.InfoBomb;
import bomberman.model.engine.info.InfoItem;
import bomberman.model.strategie.Coordonnee;
import common.Game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.ArrayList;

/**
 * La classe principale du jeu bomberman, hérite de la classe Game
 */
public class BombermanGame extends Game {

    final static Logger log = (Logger) LogManager.getLogger(BombermanGame.class);
    private Map map;
    private ActionSystem actionSystem;
    private BombSystem bombSystem;
    private ArrayList<AbstractAgent> agents;

    private boolean[][] breakableWalls;
    private ArrayList<InfoItem> items;
    private ArrayList<InfoBomb> bombs;
    private int nbPlayers;
    private ArrayList<AbstractAgent> players;

    public BombermanGame(Integer maxTurn, int nbPlayers) {
        super(maxTurn);
        this.nbPlayers = nbPlayers;
    }

    /**
     * Médode d'initialisation des éléments du jeu
     */
    @Override
    public void initializeGame() {
        log.debug("Initialisation du jeu");

        AbstractAgent.resetId();
        items = new ArrayList<>();
        bombs = new ArrayList<>();

        log.debug("Initialisation des broken_walls");
        breakableWalls = map.getStart_brokable_walls();

        log.debug("Initialisation des agents");
        initAgents();

        actionSystem = new ActionSystem(this);
        bombSystem = new BombSystem(this);

        log.debug("Jeu initialisé");
    }

    /**
     * Méthode d'appel d'un tour de jeu complet.
     * Cette méthode est appelée à chaque tour de jeu afin d'effectuer les actions globale d'un tour de jeu (état des
     * bombes, déplacement des IA, apparition des items etc).
     */
    @Override
    public void takeTurn() {
        log.debug("Tour " + getCurrentTurn() + " du jeu en cours");

        actionSystem.run();
        bombSystem.run();

        log.debug("Tour " + getCurrentTurn() + " du jeu terminé");

        setChanged();
        notifyObservers();
    }

    /**
     * Méthode appelée en fin de jeu
     */
    @Override
    public void gameOver() {
        log.debug("Le jeu est fini");
    }

    /**
     * TODO : ??
     *
     * @return booléen
     */
    @Override
    public boolean gameContinue() {
        return true;
    }

    /**
     * Méthode permettant de changer la carte du jeu
     *
     * @param layoutPath le chemin du fichier comportant le layout de la carte
     */
    public void setMapFromLayoutPath(String layoutPath) {
        try {
            map = new Map(layoutPath);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Méthode d'initialisation des agents du jeu
     */
    public void initAgents() {
        agents = new ArrayList<>();
        players = new ArrayList<>();
        for (InfoAgent agent : map.getStart_agents()) {
            int i = 0;
            try {
                AbstractAgent abstractAgent = AgentFactory.newAgent(agent.getType(), agent.getX(), agent.getY(),
                        agent.getAgentAction(), agent.getColor(), false, false);
                // ajout des joueurs
                agents.add(abstractAgent);
                if ((agent.getType() == 'B') && (i < nbPlayers)) {
                    players.add(abstractAgent);
                    i++;
                }
                log.debug("Agent initialisé ==> " + agents.get(agents.size() - 1).toString());
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    /**
     * Méthode permettant de générer les InfoAgents des Agents du jeu courant
     *
     * @return La liste des infosAgents
     */
    public ArrayList<InfoAgent> getInfoAgents() {
        ArrayList<InfoAgent> infoAgents = new ArrayList<>(agents);
        return infoAgents;
    }

    public Map getMap() {
        return map;
    }

    public boolean[][] getBreakableWalls() {
        return breakableWalls;
    }

    public ArrayList<InfoItem> getItems() {
        return items;
    }

    public ArrayList<InfoBomb> getBombs() {
        return bombs;
    }

    public ArrayList<AbstractAgent> getAgents() {
        return agents;
    }

    public void update() {
        setChanged();
        notifyObservers();
    }

    public boolean isFree(Coordonnee c) {
        if (breakableWalls[c.x][c.y] || map.get_walls()[c.x][c.y]) {
            return false;
        }
        for (InfoBomb b : bombs) {
            if (b.getX() == c.x && b.getY() == c.y) return false;
        }
        return true;
    }

    public ArrayList<AbstractAgent> getPlayers() {
        return players;
    }

}


