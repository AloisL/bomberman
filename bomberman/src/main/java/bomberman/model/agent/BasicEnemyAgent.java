package bomberman.model.agent;

import bomberman.model.BombermanGame;
import bomberman.model.repo.AgentAction;
import bomberman.model.repo.ColorAgent;
import bomberman.model.strategie.Coordonnee;
import bomberman.model.strategie.StrategieAttaque;
import bomberman.model.strategie.StrategieBasic;

import java.util.Observable;

public class BasicEnemyAgent extends AbstractAgent {

    public BasicEnemyAgent(int x, int y, AgentAction agentAction, ColorAgent color, boolean isInvincible, boolean isSick) {
        super(x, y, agentAction, 'E', color, isInvincible, isSick);
        rangeView =4;

    }
        @Override
        public void setStrategie(BombermanGame bombermanGame) {
            StrategieBasic strat=new StrategieBasic(bombermanGame,this);
            setStrategieAgents(strat);

        }





}
