package engine.agents;

import common.enums.AgentAction;
import common.enums.ColorAgent;
import common.infotypes.InfoAgent;
import engine.BombermanGame;
import engine.strategies.StrategieAgents;
import engine.strategies.StrategieSafe;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractAgent extends InfoAgent {

    private static AtomicInteger count = new AtomicInteger(0);
    int id;
    private StrategieAgents strategie;

    protected AbstractAgent(int x, int y, AgentAction agentAction, char type, ColorAgent color, boolean isInvincible,
                            boolean isSick) {
        super(x, y, agentAction, type, color, isInvincible, isSick);
        super.setAgentAction(AgentAction.STOP);
        id = count.incrementAndGet();
    }

    public static void resetId() {
        count = new AtomicInteger(0);
    }

    @Override
    public String toString() {
        return "id=" + id + " type=" + super.getType() + " pos=(" + super.getX() + "," + super.getY() + ")";
    }

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
