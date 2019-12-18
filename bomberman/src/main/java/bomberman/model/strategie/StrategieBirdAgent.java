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
    public AgentAction doStrategie() {
        return AgentAction.MOVE_UP;
    }

    private void strategieBird(EnumDirection direction) {
        switch (direction) {
            case D:

                break;

            case B:
                break;

            case G:
                break;

            case H:
                break;

            case BG:
                break;

            case BH:
                break;

            case HD:
                break;

            case HG:
                break;

            case STOP:
                break;

            default:
                break;

        }
    }


}