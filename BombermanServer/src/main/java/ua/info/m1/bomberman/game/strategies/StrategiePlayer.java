package ua.info.m1.bomberman.game.strategies;

import ua.info.m1.bomberman.game.engine.enums.AgentAction;

public class StrategiePlayer extends StrategieAgents {

    @Override
    public AgentAction doStrategie() {
        return AgentAction.STOP;
    }

}
