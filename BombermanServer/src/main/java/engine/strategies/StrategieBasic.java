package engine.strategies;

import engine.agents.AbstractAgent;
import engine.BombermanGame;
import engine.enums.AgentAction;
import engine.strategies.utils.Coordonnee;

public class StrategieBasic extends StrategieAgents {

    public StrategieBasic(BombermanGame bombermanGame, AbstractAgent agent) {
        super(bombermanGame, agent);
    }

    public StrategieBasic() {
    }

    @Override
    public AgentAction doStrategie() {
        if (bombermanGame.getPlayers() != null)
            if (bombermanGame.getPlayers().size() != 0) {
                Coordonnee imSelf = new Coordonnee(agentCalling.getX(), agentCalling.getY());
                Coordonnee ennemie = new Coordonnee(bombermanGame.getPlayers().get(0).getX(),
                        bombermanGame.getPlayers().get(0).getY());
                if (isInRange(imSelf, agentCalling.getRangeView(), ennemie))
                    return doMouvement(ennemie);
            }

        return strategieAleatoire();
    }
}
