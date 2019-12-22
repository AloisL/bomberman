package bomberman.model.engine.info;

import bomberman.model.engine.enums.AgentAction;
import bomberman.model.engine.enums.ColorAgent;

/**
 * La classe représentant un InfoAgent (Caractéristiques communes à tous les agents du jeu)
 */
public class InfoAgent {

    protected int rangeView;
    private int x;
    private int y;
    private AgentAction agentAction;
    private ColorAgent color;
    private char type;
    private boolean isInvincible;
    private boolean isSick;

    public InfoAgent(int x, int y, AgentAction agentAction, char type, ColorAgent color, boolean isInvincible,
                     boolean isSick) {
        this.x = x;
        this.y = y;
        this.agentAction = agentAction;
        this.color = color;
        this.type = type;

        this.isInvincible = isInvincible;
        this.isSick = isSick;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public ColorAgent getColor() {
        return color;
    }

    public void setColor(ColorAgent color) {
        this.color = color;
    }


    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }


    public boolean isInvincible() {
        return isInvincible;
    }


    public void setInvincible(boolean isInvincible) {
        this.isInvincible = isInvincible;
    }


    public boolean isSick() {
        return isSick;
    }


    public void setSick(boolean isSick) {
        this.isSick = isSick;
    }


    public AgentAction getAgentAction() {
        return agentAction;
    }


    public void setAgentAction(AgentAction agentAction) {
        this.agentAction = agentAction;
    }

    public int getRangeView() {
        return rangeView;
    }
}

/*

case JUMP_UP:
                if ((posY - 2 < 0)
                        || bombermanGame.getMap().get_walls()[posX][posY - 2]
                        || bombermanGame.getBreakableWalls()[posX][posY - 2]) {
                    log.debug(cannotMoveMessage + AgentAction.MOVE_UP.toString());
                    return false;
                } else {
                    log.debug(canMoveMessage + AgentAction.JUMP_UP.toString());
                    return true;
                }
            case JUMP_DOWN:
                if ((posY + 2 > bombermanGame.getMap().getSizeY() + 2)
                        || bombermanGame.getMap().get_walls()[posX][posY + 2]
                        || bombermanGame.getBreakableWalls()[posX][posY + 2]) {
                    log.debug(cannotMoveMessage + AgentAction.JUMP_DOWN.toString());
                    return false;
                } else {
                    log.debug(canMoveMessage + AgentAction.JUMP_DOWN.toString());
                    return true;
                }
            case JUMP_LEFT:
                if ((posX - 2 < 0)
                        || bombermanGame.getMap().get_walls()[posX - 2][posY]
                        || bombermanGame.getBreakableWalls()[posX - 2][posY]) {
                    log.debug(cannotMoveMessage + AgentAction.JUMP_LEFT.toString());
                    return false;
                } else {
                    log.debug(canMoveMessage + AgentAction.JUMP_LEFT.toString());
                    return true;
                }
            case JUMP_RIGHT:
                if ((posX + 2 > bombermanGame.getMap().getSizeX() + 2)
                        || bombermanGame.getMap().get_walls()[posX + 2][posY]
                        || bombermanGame.getBreakableWalls()[posX + 2][posY]) {
                    log.debug(cannotMoveMessage + AgentAction.JUMP_RIGHT.toString());
                    return false;


 */

/*
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

 */
	