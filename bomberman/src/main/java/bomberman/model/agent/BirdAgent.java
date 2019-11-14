package bomberman.model.agent;

import bomberman.model.repo.AgentAction;
import bomberman.model.repo.ColorAgent;

public class BirdAgent extends AbstractAgent {

    public BirdAgent(int x, int y, AgentAction agentAction, ColorAgent color, boolean isInvincible, boolean isSick) {
        super(x, y, agentAction, 'V', color, isInvincible, isSick);
    }
}
