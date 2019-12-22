package bomberman.model.agent;

import bomberman.model.BombermanGame;
import bomberman.model.engine.enums.AgentAction;
import bomberman.model.engine.enums.ColorAgent;
import bomberman.model.engine.enums.StateBomb;
import bomberman.model.engine.info.InfoBomb;
import bomberman.model.strategie.StrategieBasic;


public class BombermanAgent extends AbstractAgent {

    private int nbMaxBomb;
    private int nbBombPlaced;
    private int bombRange;

    public BombermanAgent(int x, int y, AgentAction agentAction, ColorAgent color, boolean isInvincible,
                          boolean isSick) {
        super(x, y, agentAction, 'B', color, isInvincible, isSick);
        nbMaxBomb = 1;
        bombRange = 1;
        nbBombPlaced = 0;
        rangeView= 5;


    }

    public void setStrategie(BombermanGame bombermanGame) {

       /* StrategieSafe strategieSafe=new StrategieSafe(bombermanGame,this);
        setStrategieAgents(strategieSafe);


        if(strategieSafe.checkSiBesoinSafe()==null) {
            StrategieAttaque attaque = new StrategieAttaque(bombermanGame, this);
            setStrategieAgents(attaque);
        }
        */
        StrategieBasic strat = new StrategieBasic(bombermanGame,this);
        setStrategieAgents(strat);


    }

        public boolean canPlaceBomb() {
        if (nbBombPlaced < nbMaxBomb) return true;
        else return false;
    }
/*
    public boolean isSafeSibombe(BombermanGame bombermanGame){
        StrategieSafe strat=new StrategieSafe(bombermanGame,this);
        InfoBomb bomb=new InfoBomb(this,this.getX(),this.getY(),this.getBombRange(),StateBomb.Step1);
        System.out.println(strat.zoneSafe(bomb).x+" : "+strat.zoneSafe(bomb).y);
        if (strat.zoneSafe(bomb).x==0) return false;
        return true;
    }

 */

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

    public int getBombRange(){
        return this.bombRange;
    }


}
