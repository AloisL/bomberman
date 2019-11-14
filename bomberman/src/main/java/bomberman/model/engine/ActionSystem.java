package bomberman.model.engine;

import bomberman.model.BombermanGame;
import bomberman.model.agent.AbstractAgent;
import bomberman.model.repo.AgentAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class ActionSystem {

    final static Logger log = (Logger) LogManager.getLogger(ActionSystem.class);

    BombermanGame bombermanGame;

    public ActionSystem(BombermanGame bombermanGame) {
        this.bombermanGame = bombermanGame;
    }

    public boolean isLegalAction(AbstractAgent agent, AgentAction action) {
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

    public void doAction(AbstractAgent agent, AgentAction action) {
        bombermanGame.getAgents().remove(agent);
        Integer posX = agent.getX();
        Integer posY = agent.getY();
        switch (action) {
            case MOVE_UP:
                agent.setY(posY - 1);
                bombermanGame.getAgents().add(agent);
                break;
            case MOVE_DOWN:
                agent.setY(posY + 1);
                bombermanGame.getAgents().add(agent);
                break;
            case MOVE_LEFT:
                agent.setX(posX - 1);
                bombermanGame.getAgents().add(agent);
                break;
            case MOVE_RIGHT:
                agent.setX(posX + 1);
                bombermanGame.getAgents().add(agent);
                break;
            case STOP:
                //TODO case STOP
                bombermanGame.getAgents().add(agent);
                break;
            case PUT_BOMB:
                //TODO case PUT_BOMB
                bombermanGame.getAgents().add(agent);
                break;
            default:
                log.debug("Action inconnue ==> " + action.toString());
                bombermanGame.getAgents().add(agent);
                break;
        }
    }

    private boolean canMove(AbstractAgent agent, AgentAction action) {
        Integer posX = agent.getX();
        Integer posY = agent.getY();
        final String cannotMoveMessage = agent.toString() + " ==> CANNOT ";
        final String canMoveMessage = agent.toString() + " ==> CAN ";
        switch (action) {
            case MOVE_UP:
                if ((posY - 1 < 0)
                        || bombermanGame.getMap().get_walls()[posX][posY - 1]
                        || bombermanGame.getBreakableWalls()[posX][posY - 1]) {
                    log.debug(cannotMoveMessage + AgentAction.MOVE_UP.toString());
                    return false;
                } else {
                    log.debug(canMoveMessage + AgentAction.MOVE_UP.toString());
                    return true;
                }
            case MOVE_DOWN:
                if ((posY + 1 > bombermanGame.getMap().getSizeY() + 1)
                        || bombermanGame.getMap().get_walls()[posX][posY + 1]
                        || bombermanGame.getBreakableWalls()[posX][posY + 1]) {
                    log.debug(cannotMoveMessage + AgentAction.MOVE_DOWN.toString());
                    return false;
                } else {
                    log.debug(canMoveMessage + AgentAction.MOVE_DOWN.toString());
                    return true;
                }
            case MOVE_LEFT:
                if ((posX - 1 < 0)
                        || bombermanGame.getMap().get_walls()[posX - 1][posY]
                        || bombermanGame.getBreakableWalls()[posX - 1][posY]) {
                    log.debug(cannotMoveMessage + AgentAction.MOVE_LEFT.toString());
                    return false;
                } else {
                    log.debug(canMoveMessage + AgentAction.MOVE_LEFT.toString());
                    return true;
                }
            case MOVE_RIGHT:
                if ((posX + 1 > bombermanGame.getMap().getSizeX() + 1)
                        || bombermanGame.getMap().get_walls()[posX + 1][posY]
                        || bombermanGame.getBreakableWalls()[posX + 1][posY]) {
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

}
