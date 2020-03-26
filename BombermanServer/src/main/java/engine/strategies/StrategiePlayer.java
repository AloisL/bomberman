package engine.strategies;

import engine.enums.AgentAction;

public class StrategiePlayer extends StrategieAgents {

    @Override
    public AgentAction doStrategie() {
        return AgentAction.STOP;
    }

}
