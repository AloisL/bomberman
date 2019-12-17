package bomberman.model;

import bomberman.model.agent.AbstractAgent;
import bomberman.model.agent.AgentFactory;
import bomberman.model.engine.*;
import bomberman.model.repo.AgentAction;
import bomberman.model.strategie.Coordonne;
import bomberman.model.strategie.StrategieAgents;
import bomberman.model.strategie.StrategieBirdAgent;
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

    private ArrayList<AbstractAgent> agents;

    private boolean[][] breakableWalls;
    private ArrayList<InfoItem> items;
    private ArrayList<InfoBomb> bombs;

    public BombermanGame(Integer maxTurn) {
        super(maxTurn);
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
        agents = new ArrayList<>();

        log.debug("Initialisation des broken_walls");
        breakableWalls = map.getStart_brokable_walls();

        log.debug("Initialisation des agents");
        initAgents();

        actionSystem = new ActionSystem(this);

        log.debug("Jeu initialisé");
    }

    /**
     * Méthode d'appel d'un rour de jeu complet
     */
    @Override
    public void takeTurn() {
        // TODO : takeTurn

        AbstractAgent agent_tmp = null;

        for (AbstractAgent agent : agents) {
            log.debug("id ==>" + agent.getId());
            if (agent.getId() == 1) {
                agent_tmp = agent;
                break;
            }
        }


        useStrat(agent_tmp);

        for (AbstractAgent agent : agents) {
            if (agent.getId() == 1) {
                agent_tmp = agent;
                break;
            }
        }

        log.debug(agent_tmp.toString());



        log.debug("Tour " + getCurrentTurn() + " du jeu en cours");
    }

    /**
     * Méthode appelée pour effectuer la strategie jusqu'a un move legale
     */
    public void useStrat(AbstractAgent agent){
        StrategieAgents strat =new StrategieBirdAgent(this,agent);
        Coordonne c=new Coordonne(0,0);
        AgentAction action = strat.strategieAleatoire(c);
        if (agent != null && actionSystem.isLegalAction(agents.get(agents.indexOf(agent)),
                action )) {
            actionSystem.doAction(agents.get(agents.indexOf(agent)), action);
        } else  {
            actionSystem.doAction(agents.get(agents.indexOf(agent)), AgentAction.STOP);
        }
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
        for (InfoAgent agent : map.getStart_agents()) {
            try {
                AbstractAgent abstractAgent = AgentFactory.newAgent(agent.getType(), agent.getX(), agent.getY(),
                        agent.getAgentAction(), agent.getColor(), false, false);
                agents.add(abstractAgent);
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
        infoAgents.addAll(agents);
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
}
