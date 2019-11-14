package bomberman.model;

import bomberman.model.agent.*;
import bomberman.model.engine.InfoAgent;
import bomberman.model.engine.InfoBomb;
import bomberman.model.engine.InfoItem;
import bomberman.model.engine.Map;
import bomberman.model.repo.AgentAction;
import common.Game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.ArrayList;

public class BombermanGame extends Game {

    final static Logger log = (Logger) LogManager.getLogger(BombermanGame.class);

    private Map map;

    private ArrayList<AbstractAgent> agents;

    private boolean[][] breakableWalls;
    private ArrayList<InfoItem> items;
    private ArrayList<InfoBomb> bombs;

    public BombermanGame(Integer maxTurn) {
        super(maxTurn);
    }

    @Override
    public void initializeGame() {

        // TODO : design pattern fabrique

        log.debug("Le jeu est initialisé !");

        AbstractAgent.resetId();

        ArrayList<InfoAgent> startAgents = map.getStart_agents();

        log.debug("Initialisation des broken_walls");
        breakableWalls = map.getStart_brokable_walls();
        log.debug("Initialisation des items");
        items = new ArrayList<>();
        log.debug("Initialisation des bombes");
        bombs = new ArrayList<>();
        log.debug("Initialisation des agents");
        agents = new ArrayList<>();

        for (InfoAgent agent : startAgents) {
            switch (agent.getType()) {
                case 'B':
                    agents.add(new BombermanAgent(agent.getX(), agent.getY(), agent.getAgentAction(), agent.getColor(), false, false));
                    log.debug("Agent initialisé ==> " + agents.get(agents.size() - 1).toString());
                    break;
                case 'R':
                    agents.add(new RajionAgent(agent.getX(), agent.getY(), agent.getAgentAction(), agent.getColor(), false, false));
                    log.debug("Agent initialisé ==> " + agents.get(agents.size() - 1).toString());
                    break;
                case 'E':
                    agents.add(new BasicEnemyAgent(agent.getX(), agent.getY(), agent.getAgentAction(), agent.getColor(), false, false));
                    log.debug("Agent initialisé ==> " + agents.get(agents.size() - 1).toString());
                    break;
                case 'V':
                    agents.add(new BirdAgent(agent.getX(), agent.getY(), agent.getAgentAction(), agent.getColor(), false, false));
                    log.debug("Agent initialisé ==> " + agents.get(agents.size() - 1).toString());
                    break;
                default:
                    log.error("Wrong agent type given: " + agent.getType());
                    break;
            }
        }

    }

    @Override
    public void takeTurn() {
        AbstractAgent agent_tmp = null;
        for (AbstractAgent agent : agents) {
            log.debug("id ==>" + agent.getId());
            if (agent.getId() == 1) {
                agent_tmp = agent;
                break;
            }
        }
        if (agent_tmp != null && isLegalMove(agents.get(agents.indexOf(agent_tmp)), AgentAction.MOVE_RIGHT)) {
            doAction(agents.get(agents.indexOf(agent_tmp)), AgentAction.MOVE_RIGHT);
        } else if (agent_tmp != null && isLegalMove(agents.get(agents.indexOf(agent_tmp)), AgentAction.MOVE_LEFT)) {
            doAction(agents.get(agents.indexOf(agent_tmp)), AgentAction.MOVE_LEFT);
        }
        for (AbstractAgent agent : agents) {
            if (agent.getId() == 1) {
                agent_tmp = agent;
                break;
            }
        }

        log.debug(agent_tmp.toString());

        //if (i)

        if (isLegalMove(agent_tmp, agent_tmp.getAgentAction())) {
            doAction(agent_tmp, agent_tmp.getAgentAction());
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

    public boolean isLegalMove(AbstractAgent agent, AgentAction action) {
        switch (action) {
            case MOVE_UP:
                return canMove(agent, AgentAction.MOVE_UP);
            case MOVE_DOWN:
                return canMove(agent, AgentAction.MOVE_DOWN);
            case MOVE_LEFT:
                return canMove(agent, AgentAction.MOVE_LEFT);
            case MOVE_RIGHT:
                return canMove(agent, AgentAction.MOVE_RIGHT);
            case STOP:
                // TODO case STOP
                return true;
            case PUT_BOMB:
                // TODO case PUT_BOMB
                return true;
            default:
                log.error(agent.toString() + " ==> Action: " + action.toString() + " non reconnue");
                return false;
        }
    }

    private boolean canMove(AbstractAgent agent, AgentAction action) {
        Integer posX = agent.getX();
        Integer posY = agent.getY();
        final String cannotMoveMessage = agent.toString() + " ==> CANNOT ";
        final String canMoveMessage = agent.toString() + " ==> CAN ";
        switch (action) {
            case MOVE_UP:
                if ((posY - 1 < 0) || map.get_walls()[posX][posY - 1] || breakableWalls[posX][posY - 1]) {
                    log.debug(cannotMoveMessage + AgentAction.MOVE_UP.toString());
                    return false;
                } else {
                    log.debug(canMoveMessage + AgentAction.MOVE_UP.toString());
                    return true;
                }
            case MOVE_DOWN:
                if ((posY + 1 > map.getSizeY() + 1) || map.get_walls()[posX][posY + 1] || breakableWalls[posX][posY + 1]) {
                    log.debug(cannotMoveMessage + AgentAction.MOVE_DOWN.toString());
                    return false;
                } else {
                    log.debug(canMoveMessage + AgentAction.MOVE_DOWN.toString());
                    return true;
                }
            case MOVE_LEFT:
                if ((posX - 1 < 0) || map.get_walls()[posX - 1][posY] || breakableWalls[posX - 1][posY]) {
                    log.debug(cannotMoveMessage + AgentAction.MOVE_LEFT.toString());
                    return false;
                } else {
                    log.debug(canMoveMessage + AgentAction.MOVE_LEFT.toString());
                    return true;
                }
            case MOVE_RIGHT:
                if ((posX + 1 > map.getSizeX() + 1) || map.get_walls()[posX + 1][posY] || breakableWalls[posX + 1][posY]) {
                    log.debug(cannotMoveMessage + AgentAction.MOVE_RIGHT.toString());
                    return false;
                } else {
                    log.debug(canMoveMessage + AgentAction.MOVE_RIGHT.toString());
                    return true;
                }
            default:
                log.error(agent.toString() + " ==> Action: " + action.toString() + " non compatible");
                return false;
        }
    }

    public void doAction(AbstractAgent agent, AgentAction action) {
        agents.remove(agent);
        Integer posX = agent.getX();
        Integer posY = agent.getY();
        switch (action) {
            case MOVE_UP:
                agent.setY(posY - 1);
                agents.add(agent);
                break;
            case MOVE_DOWN:
                agent.setY(posY + 1);
                agents.add(agent);
                break;
            case MOVE_LEFT:
                agent.setX(posX - 1);
                agents.add(agent);
                break;
            case MOVE_RIGHT:
                agent.setX(posX + 1);
                agents.add(agent);
                break;
            case STOP:
                //TODO case STOP
                agents.add(agent);
                break;
            case PUT_BOMB:
                //TODO case PUT_BOMB
                agents.add(agent);
                break;
            default:
                log.debug("Action inconnue ==> " + action.toString());
                break;
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

    public ArrayList<InfoAgent> getInfoAgents() {
        ArrayList<InfoAgent> infoAgents = new ArrayList<>();
        infoAgents.addAll(agents);
        return infoAgents;
    }
}
