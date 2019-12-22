package bomberman.model.agent;

import bomberman.model.BombermanGame;
import bomberman.model.repo.AgentAction;
import bomberman.model.repo.ColorAgent;
import bomberman.model.strategie.StrategieBasic;
import bomberman.model.strategie.StrategieSafe;

public class RajionAgent extends AbstractAgent {

    public RajionAgent(int x, int y, AgentAction agentAction, ColorAgent color, boolean isInvincible, boolean isSick) {
        super(x, y, agentAction, 'R', color, isInvincible, isSick);

        rangeView=5;
    }

    @Override
    public void setStrategie(BombermanGame bombermanGame) {
        StrategieSafe strat=new StrategieSafe(bombermanGame,this);
        setStrategieAgents(strat);
    }
}
