package bomberman.model.agent;

import bomberman.model.repo.AgentAction;
import bomberman.model.repo.ColorAgent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

/**
 * Classe gérant l'instantiation des différents types d'Agents
 */
public class AgentFactory {

    final static Logger log = (Logger) LogManager.getLogger(AgentFactory.class);

    /**
     * @param type         Char représentant le type d'Agents voulu
     * @param posX         La position x initiale
     * @param posY         La position y initiale
     * @param agentAction  L'action initiale
     * @param colorAgent   Le couleur initiale
     * @param isInvincible Etat initial de l'invisibilité
     * @param isSick       Etat de maladie initial
     * @return Un type d'agent en cohérence avec le type passé en paramètre
     * @throws Exception Une exception est levée si le type passé en paramètre est inconnu
     */
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
