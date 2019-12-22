package bomberman.model.agent;

import bomberman.model.repo.AgentAction;
import bomberman.model.repo.ColorAgent;

public class BasicEnemyAgent extends AbstractAgent {

    public BasicEnemyAgent(int x, int y, AgentAction agentAction, ColorAgent color, boolean isInvincible,
                           boolean isSick) {
        super(x, y, agentAction, 'E', color, isInvincible, isSick);
    }


}
