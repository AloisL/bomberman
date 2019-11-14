package bomberman.model.strategie;


import bomberman.model.BombermanGame;
import bomberman.model.agent.AbstractAgent;
import bomberman.model.repo.AgentAction;

import static com.sun.activation.registries.LogSupport.log;

public class StrategieBirdAgent extends StrategieAgents {

    public  StrategieBirdAgent(){}
    public StrategieBirdAgent(BombermanGame bombermanGame, AbstractAgent agent){
        super(bombermanGame, agent);
        this.viewNbBlocks = 4;
    };

    @Override
    public AgentAction doStrategie(){
        return AgentAction.MOVE_UP;
    }
    private void strategieBird() {
        if (this.agentToKill()!=null){

        }
    }




}