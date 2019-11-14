package bomberman.model.agent;

import bomberman.model.repo.AgentAction;
import bomberman.model.repo.ColorAgent;

public class RajionAgent extends AbstractAgent {

    public RajionAgent(int x, int y, AgentAction agentAction, ColorAgent color, boolean isInvincible, boolean isSick) {
        super(x, y, agentAction, 'R', color, isInvincible, isSick);
    }
}
