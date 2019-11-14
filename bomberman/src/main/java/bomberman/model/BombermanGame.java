package bomberman.model;

import bomberman.model.agent.AbstractAgent;
import bomberman.model.agent.AgentFactory;
import bomberman.model.engine.*;
import bomberman.model.repo.AgentAction;
import common.Game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.ArrayList;

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

        actionSystem = new ActionSystem(this);

        log.debug("Jeu initialisé");
    }

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

        if (agent_tmp != null && actionSystem.isLegalAction(agents.get(agents.indexOf(agent_tmp)),
                AgentAction.MOVE_RIGHT)) {
            actionSystem.doAction(agents.get(agents.indexOf(agent_tmp)), AgentAction.MOVE_RIGHT);
        } else if (agent_tmp != null && actionSystem.isLegalAction(agents.get(agents.indexOf(agent_tmp)),
                AgentAction.MOVE_LEFT)) {
            actionSystem.doAction(agents.get(agents.indexOf(agent_tmp)), AgentAction.MOVE_LEFT);
        }

        for (AbstractAgent agent : agents) {
            if (agent.getId() == 1) {
                agent_tmp = agent;
                break;
            }
        }

        log.debug(agent_tmp.toString());

        if (actionSystem.isLegalAction(agent_tmp, agent_tmp.getAgentAction())) {
            actionSystem.doAction(agent_tmp, agent_tmp.getAgentAction());
        } else {
            agent_tmp.setAgentAction(AgentAction.MOVE_RIGHT);
            log.debug(agent_tmp.toString());
            log.debug(agent_tmp.getAgentAction().toString());
        }

        log.debug("Tour " + getCurrentTurn() + " du jeu en cours");
    }

    @Override
    public void gameOver() {
        log.debug("Le jeu est fini");
    }

    @Override
    public boolean gameContinue() {
        return true;
    }

    public Map getMap() {
        return map;
    }

    public void setMapFromLayoutPath(String layoutPath) {
        try {
            map = new Map(layoutPath);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
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

    public ArrayList<InfoAgent> getInfoAgents() {
        ArrayList<InfoAgent> infoAgents = new ArrayList<>();
        infoAgents.addAll(agents);
        return infoAgents;
    }
}
