package bomberman.model.agent;

import bomberman.model.engine.BombermanGame;
import bomberman.model.engine.enums.AgentAction;
import bomberman.model.engine.enums.ColorAgent;
import bomberman.model.engine.infotype.InfoAgent;
import bomberman.model.strategie.StrategieAgents;
import bomberman.model.strategie.StrategieSafe;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractAgent extends InfoAgent {

    private static AtomicInteger count = new AtomicInteger(0);
    private StrategieAgents strategie;
    private int id;


    protected AbstractAgent(int x, int y, AgentAction agentAction, char type, ColorAgent color, boolean isInvincible,
                            boolean isSick) {
        super(x, y, agentAction, type, color, isInvincible, isSick);
        super.setAgentAction(AgentAction.STOP);
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
        return "id=" + id + " type=" + super.getType() + " pos=(" + super.getX() + "," + super.getY() + ")";
    }


/*
    public InfoBomb isSafe(){
        if (etat.getStrategieAgents().isInRange())
    }
*/

    public StrategieAgents getStrategie() {
        return strategie;
    }

    public void setStrategie(BombermanGame bombermanGame) {
        strategie = new StrategieSafe(bombermanGame, this);
    }

    public AgentAction retourneAction() {
        return strategie.doStrategie();
    }

    public void setStrategieAgents(StrategieAgents strategie) {
        this.strategie = strategie;

    }


}
