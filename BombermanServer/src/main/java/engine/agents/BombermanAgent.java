package engine.agents;

import common.enums.AgentAction;
import common.enums.ColorAgent;
import common.enums.StateBomb;
import common.infotypes.InfoBomb;
import engine.BombermanGame;
import engine.strategies.StrategieAttaque;
import engine.strategies.StrategieSafe;

public class BombermanAgent extends AbstractAgent {

    public boolean isLinked = false;
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
        rangeView = 5;
    }

    public boolean canPlaceBomb() {
        if (!isSick() && (nbBombPlaced < nbMaxBomb)) return true;
        else return false;
    }

    @Override
    public void setStrategie(BombermanGame bombermanGame) {
        StrategieAttaque attaque = new StrategieAttaque(bombermanGame, this);
        setStrategieAgents(attaque);
    }

    public boolean isSafeSibombe(BombermanGame bombermanGame) {
        StrategieSafe strat = new StrategieSafe(bombermanGame, this);
        InfoBomb bomb = new InfoBomb(id, getX(), getY(), getBombRange(), StateBomb.Step1);
        System.out.println(strat.zoneSafe(bomb).x + " : " + strat.zoneSafe(bomb).y);
        if (strat.zoneSafe(bomb).x == 0) return false;
        return true;
    }

    public InfoBomb addBomb() {
        InfoBomb bomb = new InfoBomb(id, getX(), getY(), bombRange, StateBomb.Step1);
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

    public int getBombRange() {
        return bombRange;
    }

    public int getNbLifes() {
        return nbLifes;
    }
}
