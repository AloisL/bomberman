package bomberman.model;

import bomberman.model.agent.*;
import bomberman.model.engine.InfoAgent;
import bomberman.model.engine.Map;
import common.Game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.ArrayList;

public class Bomberman extends Game {
    final static Logger log = (Logger) LogManager.getLogger(Bomberman.class);
    private Map map;
    private ArrayList<AbstractAgent> agents;

    public Bomberman(Integer maxTurn) {
        super(maxTurn);
    }

    @Override
    public void initializeGame() {
        System.out.println("Le jeu est initialisé !");

        ArrayList<InfoAgent> startAgents = map.getStart_agents();
        agents = new ArrayList<>();

        for (InfoAgent agent : startAgents) {
            switch (agent.getType()) {
                case 'B':
                    agents.add(new BombermanAgent(agent.getX(), agent.getY()));
                    log.debug("Agent initialisé -> " + agents.get(agents.size() - 1).toString());
                    break;
                case 'R':
                    agents.add(new RajionAgent(agent.getX(), agent.getY()));
                    log.debug("Agent initialisé -> " + agents.get(agents.size() - 1).toString());
                    break;
                case 'E':
                    agents.add(new BasicEnemyAgent(agent.getX(), agent.getY()));
                    log.debug("Agent initialisé -> " + agents.get(agents.size() - 1).toString());
                    break;
                case 'V':
                    agents.add(new BirdAgent(agent.getX(), agent.getY()));
                    log.debug("Agent initialisé -> " + agents.get(agents.size() - 1).toString());
                    break;
                default:
                    System.out.println("Wrong agent type given: " + agent.getType());
                    break;
            }
        }

    }

    @Override
    public void takeTurn() {
        System.out.println("Tour " + getCurrentTurn() + " du jeu en cours");
    }

    @Override
    public void gameOver() {
        System.out.println("Le jeu est fini");
    }

    @Override
    public boolean gameContinue() {
        return true;
    }

    public Map getMap() {
        return map;
    }

    public void setMapFromLayoutPath(String layoutPath) {
        try {
            map = new Map(layoutPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
