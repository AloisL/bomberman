package engine.agents;

import common.enums.AgentAction;
import common.enums.ColorAgent;
import engine.BombermanGame;
import engine.strategies.StrategieBirdAgent;

public class BirdAgent extends AbstractAgent {

    public BirdAgent(int x, int y, AgentAction agentAction, ColorAgent color, boolean isInvincible, boolean isSick) {
        super(x, y, agentAction, 'V', color, isInvincible, isSick);
        rangeView = 5;
    }

    @Override
    public void setStrategie(BombermanGame bombermanGame) {
        StrategieBirdAgent strat = new StrategieBirdAgent(bombermanGame, this);
        setStrategieAgents(strat);
    }
}
