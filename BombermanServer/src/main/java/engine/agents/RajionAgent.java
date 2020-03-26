package engine.agents;

import common.enums.AgentAction;
import common.enums.ColorAgent;
import engine.BombermanGame;
import engine.strategies.StrategieBasic;

public class RajionAgent extends AbstractAgent {

    public RajionAgent(int x, int y, AgentAction agentAction, ColorAgent color, boolean isInvincible, boolean isSick) {
        super(x, y, agentAction, 'R', color, isInvincible, isSick);

        rangeView = 10;
    }

    @Override
    public void setStrategie(BombermanGame bombermanGame) {
        StrategieBasic strat = new StrategieBasic(bombermanGame, this);
        setStrategieAgents(strat);
        /*
        StrategieSafe strat=new StrategieSafe(bombermanGame,this);
        setStrategieAgents(strat);*/
    }
}
