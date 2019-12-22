package bomberman.model.strategie;

import bomberman.model.engine.enums.AgentAction;

public class StrategiePlayer extends StrategieAgents {

    @Override
    public AgentAction doStrategie() {
        return AgentAction.STOP;
    }

}
