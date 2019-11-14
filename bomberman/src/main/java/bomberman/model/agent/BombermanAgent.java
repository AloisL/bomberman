package bomberman.model.agent;

import bomberman.model.repo.AgentAction;
import bomberman.model.repo.ColorAgent;

public class BombermanAgent extends AbstractAgent {

    public BombermanAgent(int x, int y, AgentAction agentAction, ColorAgent color, boolean isInvincible, boolean isSick) {
        super(x, y, agentAction, 'B', color, isInvincible, isSick);
    }
}
