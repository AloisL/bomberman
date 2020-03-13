package bomberman.model.engine.subsystems;

import bomberman.model.agent.AbstractAgent;
import bomberman.model.agent.BombermanAgent;
import bomberman.model.engine.BombermanGame;
import bomberman.model.engine.enums.AgentAction;
import bomberman.model.engine.infotype.InfoAgent;
import bomberman.model.engine.infotype.InfoBomb;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.ArrayList;

/**
 * Classe de gestion des actions du jeu
 */
public class ActionSystem extends AbstractSystem {

    final static Logger log = (Logger) LogManager.getLogger(ActionSystem.class);

    /**
     * Constructeur
     *
     * @param bombermanGame Le jeu
     */
    public ActionSystem(BombermanGame bombermanGame) {
        super(bombermanGame);
    }

    /**
     * Méthode effectuant les actions de chacunes des entités du jeu pour un tour de jeu
     */
    @Override
    public void run() {
        ArrayList<InfoAgent> infoAgents = bombermanGame.getInfoAgents();
        for (InfoAgent infoAgent : infoAgents) {
            AbstractAgent agent = (AbstractAgent) infoAgent;
            AgentAction agentAction = agent.getAgentAction();
            if (isLegalAction(agent, agentAction))
                if (agent.getClass() != BombermanAgent.class) {
                    if (bombermanGame.getCurrentTurn() % 2 == 0)
                        doAction(agent, agentAction);
                } else doAction(agent, agentAction);
        }
    }

    /**
     * Méthode de vérification de la possibilité d'effectuer une action
     *
     * @param agent  Un ajent du jeu
     * @param action Une action
     * @return True si l'action est possible, False sinon
     */
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
            case JUMP_DOWN:
                return canMove(agent, AgentAction.JUMP_DOWN);
            case JUMP_LEFT:
                return canMove(agent, AgentAction.JUMP_LEFT);
            case JUMP_RIGHT:
                return canMove(agent, AgentAction.JUMP_RIGHT);
            case JUMP_UP:
                return canMove(agent, AgentAction.JUMP_UP);
            case STOP:
                return true;
            case PUT_BOMB:
                if (agent.getClass() == BombermanAgent.class) {
                    BombermanAgent agentBomberman = (BombermanAgent) agent;
                    if (agentBomberman.canPlaceBomb()) return true;
                } else return false;
            default:
                log.error(agent.toString() + " ==> Action: " + action.toString() + " non reconnue");
                return false;
        }
    }

    /**
     * Méthode de vérification de la possibilité d'effectuer une mouvement
     *
     * @param agent  Un agent du jeu
     * @param action Une action (mouvement)
     * @return True si le mouvement est possible, False sinon
     */
    private boolean canMove(AbstractAgent agent, AgentAction action) {
        Integer posX = agent.getX();
        Integer posY = agent.getY();

        switch (action) {
            case MOVE_UP:
                if ((posY - 1 < 0)
                        || bombermanGame.getMap().get_walls()[posX][posY - 1]
                        || bombermanGame.getBreakableWalls()[posX][posY - 1]) {
                    return false;
                } else {
                    return true;
                }
            case MOVE_DOWN:
                if ((posY + 1 > bombermanGame.getMap().getSizeY() + 1)
                        || bombermanGame.getMap().get_walls()[posX][posY + 1]
                        || bombermanGame.getBreakableWalls()[posX][posY + 1]) {
                    return false;
                } else {
                    return true;
                }
            case MOVE_LEFT:
                if ((posX - 1 < 0)
                        || bombermanGame.getMap().get_walls()[posX - 1][posY]
                        || bombermanGame.getBreakableWalls()[posX - 1][posY]) {
                    return false;
                } else {
                    return true;
                }
            case MOVE_RIGHT:
                if ((posX + 1 > bombermanGame.getMap().getSizeX() + 1)
                        || bombermanGame.getMap().get_walls()[posX + 1][posY]
                        || bombermanGame.getBreakableWalls()[posX + 1][posY]) {
                    return false;
                } else {
                    return true;
                }
            case JUMP_UP:
                if ((posY - 2 < 0)
                        || bombermanGame.getMap().get_walls()[posX][posY - 2]
                        || bombermanGame.getBreakableWalls()[posX][posY - 2]) {
                    return false;
                } else {
                    return true;
                }
            case JUMP_DOWN:
                if (posY + 2 < bombermanGame.getMap().getSizeY()) {
                    if (bombermanGame.getMap().get_walls()[posX][posY + 2]
                            || bombermanGame.getBreakableWalls()[posX][posY + 2]) {
                        return false;
                    } else {
                        return true;
                    }
                }
                return false;
            case JUMP_LEFT:
                if ((posX - 2 < 0)
                        || bombermanGame.getMap().get_walls()[posX - 2][posY]
                        || bombermanGame.getBreakableWalls()[posX - 2][posY]) {
                    return false;
                } else {
                    return true;
                }
            case JUMP_RIGHT:
                if (posX + 2 < bombermanGame.getMap().getSizeX()) {
                    if (bombermanGame.getMap().get_walls()[posX + 2][posY]
                            || bombermanGame.getBreakableWalls()[posX + 2][posY]) {
                        return false;
                    } else {
                        return true;
                    }
                }
                return false;
            default:
                log.error(agent.toString() + " ==> Action: " + action.toString() + " non reconnue");
                return false;
        }
    }

    /**
     * Méthode d'éxecution d'une action (à appeler après isLegalAction()
     *
     * @param agent  Un agent du jeu
     * @param action Une action
     */
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
            case JUMP_UP:
                agent.setY(posY - 2);
                bombermanGame.getAgents().add(agent);
                break;
            case JUMP_DOWN:
                agent.setY(posY + 2);
                bombermanGame.getAgents().add(agent);
                break;
            case JUMP_LEFT:
                agent.setX(posX - 2);
                bombermanGame.getAgents().add(agent);
                break;
            case JUMP_RIGHT:
                agent.setX(posX + 2);
                bombermanGame.getAgents().add(agent);
                break;
            case STOP:
                bombermanGame.getAgents().add(agent);
                break;
            case PUT_BOMB:
                BombermanAgent agentBomberman = (BombermanAgent) agent;
                InfoBomb bomb = agentBomberman.addBomb();
                bombermanGame.getBombs().add(bomb);
                bombermanGame.getAgents().add(agent);
                break;
            default:
                log.debug("Action inconnue ==> " + action.toString());
                bombermanGame.getAgents().add(agent);
                break;
        }
        agent.setAgentAction(action);
    }

}