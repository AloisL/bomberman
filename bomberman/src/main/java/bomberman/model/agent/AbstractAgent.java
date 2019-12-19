package bomberman.model.agent;

import bomberman.model.BombermanGame;
import bomberman.model.engine.InfoAgent;
import bomberman.model.repo.AgentAction;
import bomberman.model.repo.ColorAgent;
import bomberman.model.strategie.StrategieAgents;
import bomberman.model.strategie.StrategieSafe;

import java.util.Observer;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractAgent extends InfoAgent {

    private static AtomicInteger count = new AtomicInteger(0);
    private StrategieAgents strategie;
    private int id;

    protected AbstractAgent(int x, int y, AgentAction agentAction, char type, ColorAgent color, boolean isInvincible, boolean isSick) {
        super(x, y, agentAction, type, color, isInvincible, isSick);
        id = count.incrementAndGet();
    }

    public static void resetId() {
        count = new AtomicInteger(0);
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Agent ::: ID=" + id + " Type=" + super.getType() + " x=" + super.getX() + " y=" + super.getY();
    }

/*
    public InfoBomb isSafe(){
        if (etat.getStrategieAgents().isInRange())
    }
*/

    public  StrategieAgents getStrategie(){
       return this.strategie;
    }
    public void setStrategie(BombermanGame bombermanGame){
        this.strategie = new StrategieSafe(bombermanGame,this);
    }

    public AgentAction retourneAction(){
        return strategie.doStrategie();
    }
}
