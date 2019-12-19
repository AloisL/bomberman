package bomberman.model.agent;

import bomberman.model.engine.InfoBomb;
import bomberman.model.repo.AgentAction;
import bomberman.model.repo.ColorAgent;
import bomberman.model.repo.StateBomb;

public class BombermanAgent extends AbstractAgent {

    private int nbMaxBomb;
    private int nbBombPlaced;
    private int bombRange;

    // TODO classe gestion de bombes

    public BombermanAgent(int x, int y, AgentAction agentAction, ColorAgent color, boolean isInvincible,
                          boolean isSick) {
        super(x, y, agentAction, 'B', color, isInvincible, isSick);
        nbMaxBomb = 1;
        bombRange = 1;
        nbBombPlaced = 0;
    }

    public boolean canPlaceBomb() {
        if (nbBombPlaced < nbMaxBomb) return true;
        else return false;
    }

    public InfoBomb addBomb() {
        InfoBomb bomb = new InfoBomb(this, getX(), getY(), bombRange, StateBomb.Step1);
        nbBombPlaced++;
        return bomb;
    }

    public void freeBombSlot() {
        nbBombPlaced--;
    }

    public void setNbMaxBomb(int nbMaxBomb) {
        this.nbMaxBomb = nbMaxBomb;
    }

    public void setBombRange(int bombRange) {
        this.bombRange = bombRange;
    }


}
