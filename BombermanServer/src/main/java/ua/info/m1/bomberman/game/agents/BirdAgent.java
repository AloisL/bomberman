package ua.info.m1.bomberman.game.agents;

import ua.info.m1.bomberman.game.engine.BombermanGame;
import ua.info.m1.bomberman.game.engine.enums.AgentAction;
import ua.info.m1.bomberman.game.engine.enums.ColorAgent;
import ua.info.m1.bomberman.game.strategies.StrategieBirdAgent;

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
