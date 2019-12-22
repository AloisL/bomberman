package bomberman.model.agent;

import bomberman.model.engine.info.InfoBomb;
import bomberman.model.repo.AgentAction;
import bomberman.model.repo.ColorAgent;
import bomberman.model.repo.StateBomb;

public class BombermanAgent extends AbstractAgent {

    private int nbMaxBomb;
    private int nbBombPlaced;
    private int bombRange;
    private int nbLifes = 3;
    private int nbTurnInvinsible;
    private int nbTurnSick;

    public BombermanAgent(int x, int y, AgentAction agentAction, ColorAgent color, boolean isInvincible,
                          boolean isSick) {
        super(x, y, agentAction, 'B', color, isInvincible, isSick);
        nbMaxBomb = 1;
        bombRange = 1;
        nbBombPlaced = 0;
    }

    public boolean canPlaceBomb() {
        if (!isSick() && (nbBombPlaced < nbMaxBomb)) return true;
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

    public void removeLife() {
        nbLifes--;
    }

    /**
     * Retourne le statut de mort l'agent (true == mort, false == vivant)
     *
     * @return boolean
     */
    public boolean isDead() {
        if (nbLifes < 1) return true;
        else return false;
    }

    public void addMaxBomb() {
        nbMaxBomb++;
    }

    public void reduceMaxBomb() {
        if (nbMaxBomb > 1) nbMaxBomb--;
    }

    public void addBombRange() {
        bombRange++;
    }

    public void reduceBombRange() {
        if (bombRange > 1) bombRange--;
    }

    public void makeInvinsible() {
        setInvincible(true);
        nbTurnInvinsible = 20;
    }

    public void reduceInvinsibleTurn() {
        nbTurnInvinsible--;
        if (nbTurnInvinsible <= 0) setInvincible(false);
    }

    public void makeSick() {
        setSick(true);
        nbTurnSick = 10;
    }

    public void reduceSickTurn() {
        nbTurnSick--;
        if (nbTurnSick <= 0) setSick(false);
    }


}
