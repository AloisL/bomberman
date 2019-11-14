package bomberman.model.agent;

import bomberman.model.repo.AgentAction;
import bomberman.model.repo.ColorAgent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class AgentFactory {

    final static Logger log = (Logger) LogManager.getLogger(AgentFactory.class);

    public static AbstractAgent newAgent(char type, int posX, int posY, AgentAction agentAction, ColorAgent colorAgent,
                                         boolean isInvincible, boolean isSick) throws Exception {
        switch (type) {
            case 'B':
                return new BombermanAgent(posX, posY, agentAction, colorAgent, isInvincible, isSick);
            case 'R':
                return new RajionAgent(posX, posY, agentAction, colorAgent, isInvincible, isSick);
            case 'E':
                return new BasicEnemyAgent(posX, posY, agentAction, colorAgent, isInvincible, isSick);
            case 'V':
                return new BirdAgent(posX, posY, agentAction, colorAgent, isInvincible, isSick);
            default:
                throw new Exception("Wrong agent type given: " + type + " --> null agent initialized");
        }
    }

}
