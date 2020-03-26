package engine;

import common.enums.AgentAction;
import common.enums.ColorAgent;
import common.infotypes.InfoAgent;
import common.infotypes.InfoBomb;
import common.infotypes.InfoItem;
import engine.agents.AbstractAgent;
import engine.agents.AgentFactory;
import engine.agents.BombermanAgent;
import engine.strategies.utils.Coordonnee;
import engine.subsystems.ActionSystem;
import engine.subsystems.DamageSystem;
import engine.subsystems.ItemSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * La classe principale du jeu bomberman, hérite de la classe Game
 */
public class BombermanGame extends Game {

    final static Logger log = (Logger) LogManager.getLogger(BombermanGame.class);

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

    public BombermanGame(String layout, Integer maxTurn, int nbPlayers) {
        super(maxTurn);
        this.nbPlayers = nbPlayers;
        String layoutPath = "src/main/resources/layouts/" + layout;
        try {
            map = new Map(layoutPath);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
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

        breakableWalls = map.getBreakableWalls();
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
        //bombermanController.gameOver();
    }

    public void gameWon() {
        isRunning = false;
        //bombermanController.gameWon();
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
     * Méthode d'initialisation des agents du jeu
     */
    private void initAgents() {
        agents = new ArrayList<>();
        players = new ArrayList<>();
        for (InfoAgent agent : map.getInfoAgents()) {
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
        ArrayList<InfoAgent> infoAgents = new ArrayList<>();
        for (AbstractAgent agent : getAgents()) {
            InfoAgent tmp = new InfoAgent(agent.getX(), agent.getY(), agent.getAgentAction(), agent.getType(),
                    agent.getColor(), agent.isInvincible(), agent.isSick());
            infoAgents.add(tmp);
        }
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

    /**
     * Getter retournant une copie permettant d'éviter la concurrence d'accès.
     * Synchoniser les threads auraient pu être possible mais cela compliquait énormément le code pour un
     * résultat équivalent.
     */
    public ArrayList<AbstractAgent> getAgents() {
        return new ArrayList<>(agents);
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


