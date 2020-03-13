package bomberman.model.engine;

import bomberman.controller.BombermanController;
import bomberman.model.agent.AbstractAgent;
import bomberman.model.agent.AgentFactory;
import bomberman.model.agent.BombermanAgent;
import bomberman.model.engine.enums.AgentAction;
import bomberman.model.engine.enums.ColorAgent;
import bomberman.model.engine.infotype.InfoAgent;
import bomberman.model.engine.infotype.InfoBomb;
import bomberman.model.engine.infotype.InfoItem;
import bomberman.model.engine.subsystems.ActionSystem;
import bomberman.model.engine.subsystems.DamageSystem;
import bomberman.model.engine.subsystems.ItemSystem;
import bomberman.model.strategie.utils.Coordonnee;
import common.Game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.ArrayList;

/**
 * La classe principale du jeu bomberman, hérite de la classe Game
 */
public class BombermanGame extends Game {

    final static Logger log = (Logger) LogManager.getLogger(BombermanGame.class);

    private BombermanController bombermanController;
    private Map map;
    private ActionSystem actionSystem;
    private DamageSystem damageSystem;
    private ItemSystem itemSystem;
    private ArrayList<AbstractAgent> agents;
    private ArrayList<AbstractAgent> agentsIa;
    private boolean[][] breakableWalls;
    private ArrayList<InfoItem> items;
    private ArrayList<InfoBomb> bombs;
    private int nbPlayers;
    private ArrayList<AbstractAgent> players;

    public BombermanGame(BombermanController bombermanController, Integer maxTurn, int nbPlayers) {
        super(maxTurn);
        this.nbPlayers = nbPlayers;
        this.bombermanController = bombermanController;
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
        agentsIa = new ArrayList<>();

        breakableWalls = map.getStart_brokable_walls();
        initAgents();

        for (AbstractAgent agent : agents) {
            if (agent.getColor() != ColorAgent.BLEU) {
                agentsIa.add(agent);
            }
        }

        actionSystem = new ActionSystem(this);
        damageSystem = new DamageSystem(this);
        itemSystem = new ItemSystem(this);

        log.debug("Jeu initialisé");
    }

    /**
     * Méthode d'appel d'un tour de jeu complet.
     * Cette méthode est appelée à chaque tour de jeu afin d'effectuer les actions globale d'un tour de jeu (état des
     * bombes, déplacement des IA, apparition des items etc).
     */
    @Override
    public void takeTurn() {
        log.debug("Début du tour " + getCurrentTurn());
        actionSystem.run();
        damageSystem.run();
        itemSystem.run();

        if (players.size() == 0) gameOver();
        else if (agents.size() == 1) gameWon();
        else {
            for (AbstractAgent agent : agentsIa) {
                if (agent.getColor() != ColorAgent.BLEU) {
                    agent.setStrategie(this);
                    AgentAction action = agent.getStrategie().doStrategie();
                    if (actionSystem.isLegalAction(agent, action)) {
                        agent.setAgentAction(action);
                    } else actionSystem.doAction(agent, AgentAction.STOP);
                }
            }
        }

        setChanged();
        notifyObservers();

        log.debug("Fin du tour " + getCurrentTurn());
    }

    /**
     * Méthode appelée en fin de jeu
     */
    @Override
    public void gameOver() {
        isRunning = false;
        bombermanController.gameOver();
    }

    public void gameWon() {
        isRunning = false;
        bombermanController.gameWon();
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
                if ((agent.getColor() == ColorAgent.BLEU) && (i < nbPlayers)) {
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

    public boolean isFree(Coordonnee c) {
        if (c.x > 0 && c.y > 0 && c.x < map.getSizeX() && c.y < map.getSizeY()) {
            if (breakableWalls[c.x][c.y] || map.get_walls()[c.x][c.y]) {
                return false;
            }
            for (InfoBomb b : bombs) {
                if (b.getX() == c.x && b.getY() == c.y) {
                    return false;
                }
            }
            return true;
        }
        return false;
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

    public ActionSystem getActionSystem() {
        return actionSystem;
    }

    public ArrayList<AbstractAgent> getPlayers() {
        return players;
    }

    public ArrayList<AbstractAgent> getAgentsIa() {
        return agentsIa;
    }


    public int getNblife() {
        // TODO: Gestion des joueurs à revoir, ce code est douteux
        BombermanAgent player = (BombermanAgent) players.get(0);
        return player.getNbLifes();
    }

}


