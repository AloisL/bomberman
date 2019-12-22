package bomberman.model.strategie;

import bomberman.model.BombermanGame;
import bomberman.model.agent.AbstractAgent;
import bomberman.model.repo.AgentAction;
import bomberman.model.repo.EnumDirection;

public class StrategieBirdAgent extends StrategieAgents {

    public StrategieBirdAgent() {
    }

    public StrategieBirdAgent(BombermanGame bombermanGame, AbstractAgent agent) {
        super(bombermanGame, agent);
        viewNbBlocks = 4;
    }

    @Override
    public AgentAction doStrategie(){
        Coordonnee imSelf= new Coordonnee(agentCalling.getX(),agentCalling.getY());
        AgentAction action = doMouvement(checkEnnemie());
        if (isInRange(imSelf,agentCalling.getRangeView(),checkEnnemie())) {
            if(!bombermanGame.getActionSystem().isLegalAction(this.agentCalling,action)) {
                switch (action){
                    case MOVE_UP:
                        return AgentAction.JUMP_UP;
                    case MOVE_DOWN:
                        return AgentAction.JUMP_DOWN;
                    case MOVE_LEFT:
                        return AgentAction.JUMP_LEFT;
                    case MOVE_RIGHT:
                        return AgentAction.JUMP_RIGHT;
                    default:
                        return action;

                }
            }
        }
        return AgentAction.STOP;
    }




}