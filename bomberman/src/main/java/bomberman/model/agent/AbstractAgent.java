package bomberman.model.agent;

public abstract class AbstractAgent {

    private char type;
    private Integer posY;
    private Integer posX;

    protected AbstractAgent(Integer posX, Integer posY, char type) {
        this.posX = posX;
        this.posY = posY;
        this.type = type;
    }

    public Integer getPosX() {
        return posX;
    }

    public Integer getPosY() {
        return posY;
    }

    public char getType() {
        return type;
    }

    @Override
    public String toString() {
        return "type:" + type + " poX:" + posX.toString() + " posY:" + posY.toString();
    }
}
