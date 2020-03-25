package ua.info.m1.bomberman.engine.agents;

import ua.info.m1.bomberman.engine.BombermanGame;
import ua.info.m1.bomberman.engine.enums.AgentAction;
import ua.info.m1.bomberman.engine.enums.ColorAgent;
import ua.info.m1.bomberman.engine.strategies.StrategieBasic;

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
