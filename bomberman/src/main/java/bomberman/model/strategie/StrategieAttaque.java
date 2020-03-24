package bomberman.model.strategie;

import bomberman.model.agent.AbstractAgent;
import bomberman.model.agent.BombermanAgent;
import bomberman.model.engine.BombermanGame;
import bomberman.model.engine.enums.AgentAction;
import bomberman.model.strategie.utils.Coordonnee;

public class StrategieAttaque extends StrategieAgents {

    public StrategieAttaque(BombermanGame bombermanGame, AbstractAgent agent) {
        super(bombermanGame, agent);
    }

    @Override
    public AgentAction doStrategie() {
        BombermanAgent agent = (BombermanAgent) agentCalling;
        Coordonnee imself = new Coordonnee(agent.getX(), agent.getY());
        Coordonnee enemy = new Coordonnee(bombermanGame.getPlayers().get(0).getX(), bombermanGame.getPlayers().get(0).getY());

        if (isInRange(imself, agent.getBombRange(), enemy)) {
            return AgentAction.PUT_BOMB;
        }
        if (isInRange(imself, agentCalling.getRangeView(), enemy)) {
            return doMouvement(enemy);
        }
/*
        if (agent.isSafeSibombe(bombermanGame)) {
            System.out.println(3);
            return AgentAction.PUT_BOMB;
        }
 */
        return strategieAleatoire();
    }
}
