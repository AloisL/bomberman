package res.infotype;

import res.enums.AgentAction;
import res.enums.ColorAgent;

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

    public int getY() {
        return y;
    }

    public ColorAgent getColor() {
        return color;
    }

    public char getType() {
        return type;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    public boolean isSick() {
        return isSick;
    }

    public AgentAction getAgentAction() {
        return agentAction;
    }

}