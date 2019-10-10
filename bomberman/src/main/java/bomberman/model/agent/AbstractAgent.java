package bomberman.model.agent;

import bomberman.model.repo.AgentAction;

public abstract class AbstractAgent {

    private char type;
    private Integer posY;
    private Integer posX;
    private AgentAction agentAction;

    protected AbstractAgent(Integer posX, Integer posY, char type) {
        this.posX = posX;
        this.posY = posY;
        this.type = type;
    }

    public Integer getPosX() {
        return posX;
    }

    public void setPosX(Integer posX) {
        this.posX = posX;
    }

    public Integer getPosY() {
        return posY;
    }

    public void setPosY(Integer posY) {
        this.posY = posY;
    }

    public char getType() {
        return type;
    }

    @Override
    public String toString() {
        return "type:" + type + "\tpoX:" + posX.toString() + "\tposY:" + posY.toString();
    }
}
