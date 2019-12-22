package bomberman.model.strategie;

import bomberman.model.BombermanGame;
import bomberman.model.agent.AbstractAgent;
import bomberman.model.repo.AgentAction;

public class StrategieAttaque extends StrategieAgents {

    public StrategieAttaque(BombermanGame bombermanGame, AbstractAgent agent){
        super(bombermanGame,agent);
    }

    @Override
    public AgentAction doStrategie() {
        return AgentAction.STOP;
    }
}
