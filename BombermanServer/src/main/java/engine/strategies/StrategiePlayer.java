package engine.strategies;

import common.enums.AgentAction;

public class StrategiePlayer extends StrategieAgents {

    @Override
    public AgentAction doStrategie() {
        return AgentAction.STOP;
    }

}
