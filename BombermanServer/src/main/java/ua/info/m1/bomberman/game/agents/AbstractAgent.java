package ua.info.m1.bomberman.game.agents;

import ua.info.m1.bomberman.game.engine.BombermanGame;
import ua.info.m1.bomberman.game.engine.enums.AgentAction;
import ua.info.m1.bomberman.game.engine.enums.ColorAgent;
import ua.info.m1.bomberman.game.engine.infotypes.InfoAgent;
import ua.info.m1.bomberman.game.strategies.StrategieAgents;
import ua.info.m1.bomberman.game.strategies.StrategieSafe;

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
