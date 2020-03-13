package bomberman.model.agent;

import bomberman.model.engine.BombermanGame;
import bomberman.model.engine.enums.AgentAction;
import bomberman.model.engine.enums.ColorAgent;
import bomberman.model.strategie.StrategieBasic;

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
