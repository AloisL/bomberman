package ua.info.m1.bomberman.engine.strategies;

import ua.info.m1.bomberman.engine.enums.AgentAction;

public class StrategiePlayer extends StrategieAgents {

    @Override
    public AgentAction doStrategie() {
        return AgentAction.STOP;
    }

}
