package bomberman.model.strategie;

import bomberman.model.repo.AgentAction;

public class StrategieBasic extends StrategieAgents {

    public StrategieBasic(){}

    @Override
    public AgentAction doStrategie() {
        return AgentAction.STOP;
    }
}
