package ua.info.m1.bomberman.game.strategies;

import ua.info.m1.bomberman.game.agents.AbstractAgent;
import ua.info.m1.bomberman.game.engine.BombermanGame;
import ua.info.m1.bomberman.game.engine.enums.AgentAction;
import ua.info.m1.bomberman.game.strategies.utils.Coordonnee;

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
