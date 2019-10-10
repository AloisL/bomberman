package bomberman.model.agent;

import bomberman.model.engine.InfoAgent;
import bomberman.model.repo.AgentAction;
import bomberman.model.repo.ColorAgent;

public abstract class AbstractAgent extends InfoAgent {

    protected AbstractAgent(int x, int y, AgentAction agentAction, char type, ColorAgent color, boolean isInvincible, boolean isSick) {
        super(x, y, agentAction, type, color, isInvincible, isSick);
    }

    @Override
    public String toString() {
        return "Agent ::: type:" + super.getType() + " poX:" + super.getX() + "\tposY:" + super.getY();
    }
}
