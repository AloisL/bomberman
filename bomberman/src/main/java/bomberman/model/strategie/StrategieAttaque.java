package bomberman.model.strategie;

import bomberman.model.BombermanGame;
import bomberman.model.agent.AbstractAgent;
import bomberman.model.agent.BombermanAgent;
import bomberman.model.engine.enums.AgentAction;
import bomberman.model.strategie.utils.Coordonnee;

public class StrategieAttaque extends StrategieAgents {

    public StrategieAttaque(BombermanGame bombermanGame, AbstractAgent agent){
        super(bombermanGame,agent);
    }

    @Override
    public AgentAction doStrategie() {
        BombermanAgent agent= (BombermanAgent)agentCalling;
       Coordonnee imSelf=new Coordonnee(agent.getX(),agent.getY());
       Coordonnee ennemie=new Coordonnee(bombermanGame.getPlayers().get(0).getX(),bombermanGame.getPlayers().get(0).getY());

        if (isInRange(imSelf,agent.getBombRange(),ennemie)) {
            System.out.println("1");
            return AgentAction.PUT_BOMB;
        }
        if(isInRange(imSelf,agentCalling.getRangeView(),ennemie)) {
            System.out.println("2");
            return doMouvement(ennemie);
        }
/*
        if (agent.isSafeSibombe(bombermanGame)) {
            System.out.println(3);
            return AgentAction.PUT_BOMB;
        }

 */

        System.out.println("4");
        return strategieAleatoire();
    }
}
