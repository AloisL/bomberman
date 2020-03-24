package res.infotype;

import res.enums.StateBomb;

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

    public int getY() {
        return y;
    }

    public StateBomb getStateBomb() {
        return stateBomb;
    }

    public int getRange() {
        return range;
    }

}
	