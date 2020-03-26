package engine.agents;

import engine.BombermanGame;
import engine.enums.AgentAction;
import engine.enums.ColorAgent;
import engine.strategies.StrategieBasic;

public class BasicEnemyAgent extends AbstractAgent {

    public BasicEnemyAgent(int x, int y, AgentAction agentAction, ColorAgent color, boolean isInvincible,
                           boolean isSick) {
        super(x, y, agentAction, 'E', color, isInvincible, isSick);
        rangeView = 4;
    }

    @Override
    public void setStrategie(BombermanGame bombermanGame) {
        StrategieBasic strat = new StrategieBasic(bombermanGame, this);
        setStrategieAgents(strat);
    }


}
