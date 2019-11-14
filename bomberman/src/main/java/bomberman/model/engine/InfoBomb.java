package bomberman.model.engine;

import bomberman.model.repo.StateBomb;

public class InfoBomb {

    StateBomb stateBomb;
    private int x;
    private int y;
    private int range;

    public InfoBomb(int x, int y, int range, StateBomb stateBomb) {
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


}
	