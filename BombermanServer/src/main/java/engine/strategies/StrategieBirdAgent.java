package engine.strategies;

import common.enums.AgentAction;
import engine.BombermanGame;
import engine.agents.AbstractAgent;
import engine.strategies.utils.Coordonnee;

public class StrategieBirdAgent extends StrategieAgents {

    public StrategieBirdAgent() {
    }

    public StrategieBirdAgent(BombermanGame bombermanGame, AbstractAgent agent) {
        super(bombermanGame, agent);
        viewNbBlocks = 4;
    }

    @Override
    public AgentAction doStrategie() {
        Coordonnee imself = new Coordonnee(agentCalling.getX(), agentCalling.getY());
        Coordonnee enemy = new Coordonnee(bombermanGame.getPlayers().get(0).getX(), bombermanGame.getPlayers().get(0).getY());

        if (isInRange(imself, agentCalling.getRangeView(), enemy)) {
            AgentAction action = doMouvement(enemy);
            //System.out.println(!bombermanGame.getActionSystem().isLegalAction(this.agentCalling,action));
            if (!bombermanGame.getActionSystem().isLegalAction(agentCalling, action)) {
                System.out.println(action.toString());
                switch (action) {
                    case MOVE_UP:
                        return AgentAction.JUMP_UP;
                    case MOVE_DOWN:
                        System.out.println(bombermanGame.getActionSystem().isLegalAction(agentCalling, AgentAction.JUMP_DOWN));
                        return AgentAction.JUMP_DOWN;
                    case MOVE_LEFT:
                        return AgentAction.JUMP_LEFT;
                    case MOVE_RIGHT:
                        return AgentAction.JUMP_RIGHT;
                    default:
                        return action;
                }
            } else {
                System.out.println(action.toString());
                return action;
            }
        }
        return strategieAleatoire();
    }

}
