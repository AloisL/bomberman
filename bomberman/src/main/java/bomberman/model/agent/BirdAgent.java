package bomberman.model.agent;

import bomberman.model.engine.BombermanGame;
import bomberman.model.engine.enums.AgentAction;
import bomberman.model.engine.enums.ColorAgent;
import bomberman.model.strategie.StrategieBirdAgent;

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
