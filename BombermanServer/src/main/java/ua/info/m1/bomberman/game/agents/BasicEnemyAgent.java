package ua.info.m1.bomberman.game.agents;

import ua.info.m1.bomberman.game.engine.BombermanGame;
import ua.info.m1.bomberman.game.engine.enums.AgentAction;
import ua.info.m1.bomberman.game.engine.enums.ColorAgent;
import ua.info.m1.bomberman.game.strategies.StrategieBasic;

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
