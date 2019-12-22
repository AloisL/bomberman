package bomberman.model.engine;

import bomberman.model.agent.BombermanAgent;
import bomberman.model.repo.StateBomb;

public class InfoBomb {

    StateBomb stateBomb;
    BombermanAgent owner;
    private int x;
    private int y;
    private int range;
    public boolean alfStep; //fait en sorte que les bombe prenne 2X plus de temps pour exploser

    public InfoBomb(BombermanAgent owner, int x, int y, int range, StateBomb stateBomb) {
        this.owner = owner;
        this.x = x;
        this.y = y;
        this.range = range;
        this.stateBomb = stateBomb;
        this.alfStep = false;
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

    public StateBomb getStateBomb() {
        return stateBomb;
    }

    public void setStateBomb(StateBomb stateBomb) {
        this.stateBomb = stateBomb;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public BombermanAgent getOwner() {
        return owner;
    }
}
	