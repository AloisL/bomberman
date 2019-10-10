package bomberman.model;

import bomberman.model.agent.*;
import bomberman.model.engine.InfoAgent;
import bomberman.model.engine.InfoBomb;
import bomberman.model.engine.InfoItem;
import bomberman.model.engine.Map;
import bomberman.model.repo.AgentAction;
import bomberman.view.PanelBomberman;
import common.Game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.ArrayList;

public class Bomberman extends Game {

    final static Logger log = (Logger) LogManager.getLogger(Bomberman.class);

    private PanelBomberman bombermanPanel;
    private Map map;

    private ArrayList<AbstractAgent> agents;
    private boolean[][] breakableWalls;
    private ArrayList<InfoItem> items;
    private ArrayList<InfoBomb> bombs;

    public Bomberman(Integer maxTurn) {
        super(maxTurn);
    }

    @Override
    public void initializeGame() {
        log.debug("Le jeu est initialisé !");

        ArrayList<InfoAgent> startAgents = map.getStart_agents();

        agents = new ArrayList<>();
        breakableWalls = map.getStart_brokable_walls();
        items = new ArrayList<InfoItem>();
        bombs = new ArrayList<InfoBomb>();

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
        canMove(agents.get(agents.size() - 1), AgentAction.MOVE_RIGHT);
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
                if (canMove(agent, AgentAction.MOVE_UP)) return true;
                else return false;
            case MOVE_DOWN:
                if (canMove(agent, AgentAction.MOVE_DOWN)) return true;
                else return false;
            case MOVE_LEFT:
                if (canMove(agent, AgentAction.MOVE_LEFT)) return true;
                else return false;
            case MOVE_RIGHT:
                if (canMove(agent, AgentAction.MOVE_RIGHT)) return true;
                else return false;
            case STOP:
                // TODO
                return true;
            case PUT_BOMB:
                // TODO
                return true;
            default:
                log.error(agent.toString() + " ==> " + action.toString() + " non reconnue");
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
                if ((posY - 1 < 0 || map.get_walls()[posX][posY - 1])) {
                    log.debug(cannotMoveMessage + AgentAction.MOVE_UP.toString());
                    return false;
                } else {
                    log.debug(canMoveMessage + AgentAction.MOVE_UP.toString());
                    return true;
                }
            case MOVE_DOWN:
                if ((posY + 1 > map.getSizeY() + 1) || map.get_walls()[posX][posY + 1]) {
                    log.debug(cannotMoveMessage + AgentAction.MOVE_DOWN.toString());
                    return false;
                } else {
                    log.debug(canMoveMessage + AgentAction.MOVE_DOWN.toString());
                    return true;
                }
            case MOVE_LEFT:
                if ((posX - 1 < 0) || map.get_walls()[posX - 1][posY]) {
                    log.debug(cannotMoveMessage + AgentAction.MOVE_LEFT.toString());
                    return false;
                } else {
                    log.debug(canMoveMessage + AgentAction.MOVE_LEFT.toString());
                    return true;
                }
            case MOVE_RIGHT:
                if ((posX + 1 > map.getSizeX() + 1) || map.get_walls()[posX + 1][posY]) {
                    log.debug(cannotMoveMessage + AgentAction.MOVE_RIGHT.toString());
                    return false;
                } else {
                    log.debug(canMoveMessage + AgentAction.MOVE_RIGHT.toString());
                    return true;
                }
            default:
                log.error(agent.toString() + " ==> Wrong action given");
                return false;
        }
    }

    public void moveAgent(AbstractAgent agent, AgentAction action) {
        agents.remove(agent);
        Integer posX = agent.getX();
        Integer posY = agent.getY();
        switch (action) {
            case MOVE_UP:
                agent.setY(posY - 1);
                agents.add(agent);
            case MOVE_DOWN:
                agent.setY(posY + 1);
                agents.add(agent);
            case MOVE_LEFT:
                agent.setX(posX - 1);
                agents.add(agent);
            case MOVE_RIGHT:
                agent.setX(posX + 1);
                agents.add(agent);
            default:
                log.debug("Action inconnue ==> " + action.toString());
        }

    }

}
