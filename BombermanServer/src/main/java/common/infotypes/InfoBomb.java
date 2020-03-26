package common.infotypes;

import common.enums.StateBomb;

import java.io.Serializable;

public class InfoBomb implements Serializable {

    StateBomb stateBomb;
    private int ownerId;
    private int x;
    private int y;
    private int range;

    public InfoBomb(int ownerId, int x, int y, int range, StateBomb stateBomb) {
        this.ownerId = ownerId;
        this.x = x;
        this.y = y;
        this.range = range;
        this.stateBomb = stateBomb;
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

    public int getOwnerId() {
        return ownerId;
    }

}
