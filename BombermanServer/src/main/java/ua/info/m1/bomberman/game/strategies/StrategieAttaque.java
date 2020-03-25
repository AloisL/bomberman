package ua.info.m1.bomberman.game.strategies;


import ua.info.m1.bomberman.game.agents.AbstractAgent;
import ua.info.m1.bomberman.game.agents.BombermanAgent;
import ua.info.m1.bomberman.game.engine.BombermanGame;
import ua.info.m1.bomberman.game.engine.enums.AgentAction;
import ua.info.m1.bomberman.game.strategies.utils.Coordonnee;

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
