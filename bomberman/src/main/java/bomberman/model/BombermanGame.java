package bomberman.model;

import bomberman.model.agent.AbstractAgent;
import bomberman.model.agent.AgentFactory;
import bomberman.model.agent.BombermanAgent;
import bomberman.model.engine.*;
import bomberman.model.repo.AgentAction;

import bomberman.model.repo.ColorAgent;
import bomberman.model.repo.StateBomb;

import bomberman.model.strategie.Coordonnee;
import bomberman.model.strategie.StrategieAgents;
import bomberman.model.strategie.StrategieSafe;

import bomberman.model.repo.ItemType;

import common.Game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.ArrayList;

/**
 * La classe principale du jeu bomberman, hérite de la classe Game
 */
public class BombermanGame extends Game {

    final static Logger log = (Logger) LogManager.getLogger(BombermanGame.class);
    private Map map;
    private ActionSystem actionSystem;
    private ArrayList<AbstractAgent> agents;


    private boolean[][] breakableWalls;
    private ArrayList<InfoItem> items;
    private ArrayList<InfoBomb> bombs;

    public BombermanGame(Integer maxTurn) {
        super(maxTurn);
    }

    /**
     * Médode d'initialisation des éléments du jeu
     */
    @Override
    public void initializeGame() {
        log.debug("Initialisation du jeu");



        AbstractAgent.resetId();
        items = new ArrayList<>();
        bombs = new ArrayList<>();
        agents = new ArrayList<>();

        log.debug("Initialisation des broken_walls");
        breakableWalls = map.getStart_brokable_walls();

        log.debug("Initialisation des agents");
        initAgents();
        // TODO initiaisation des stratéggie (initStrategies())

        actionSystem = new ActionSystem(this);


        log.debug("Jeu initialisé");

    }

    /**
     * Méthode d'appel d'un tour de jeu complet.
     * Cette méthode est appelée à chaque tour de jeu afin d'effectuer les actions globale d'un tour de jeu (état des
     * bombes, déplacement des IA, apparition des items etc).
     */
    @Override
    public void takeTurn() {

        for (InfoAgent infoAgent : getInfoAgents()) {
            AgentAction agentAction = infoAgent.getAgentAction();
            if (actionSystem.isLegalAction((AbstractAgent) infoAgent, agentAction))
                actionSystem.doAction((AbstractAgent) infoAgent, agentAction);
        }

        ArrayList<InfoBomb> bombToBeRemoved = new ArrayList<>();

        for (InfoBomb bomb : bombs) {
            switch (bomb.getStateBomb()) {
                case Step1:
                    bomb.setStateBomb(StateBomb.Step2);
                    break;
                case Step2:
                    bomb.setStateBomb(StateBomb.Step3);
                    break;
                case Step3:
                    bomb.setStateBomb(StateBomb.Boom);
                    break;
                case Boom:
                    bombToBeRemoved.add(bomb);
                default:
                    log.error("Etat de bombe inconnu");
            }
        }

        for (InfoBomb bomb : bombs) if (bomb.getStateBomb() == StateBomb.Boom) bombHit(bomb);

        for (InfoBomb bomb : bombToBeRemoved) {
            bomb.getOwner().freeBombSlot();
            bombs.remove(bomb);
        }

        for (AbstractAgent agent: agents) {
            if (agent.getColor()!= ColorAgent.BLEU){
                StrategieAgents strat=new StrategieSafe(this,agent);
                takeTurnIa(agent,strat.doStrategie());
            }
        }

        log.debug("Tour " + getCurrentTurn() + " du jeu en cours");
    }

    /**
     * Méthode d'appel d'un tour de jeu d'un agent Bomberman
     * Cette méthode est indépendante de la méthode takeTurn().
     * Cette méthode est appelée dès qu'un agent bomberman effectue une action clavier.*
     * Cette méthode met à jour le jeu indépendemment des tours classiques.
     *
     * @param bombermanAgent
     * @param agentAction
     */
    public void takeTurn(BombermanAgent bombermanAgent, AgentAction agentAction) {
        if (actionSystem.isLegalAction(bombermanAgent, agentAction)) actionSystem.doAction(bombermanAgent, agentAction);
        setChanged();
        notifyObservers();
    }

    public void takeTurnIa(AbstractAgent agent, AgentAction agentAction) {
        if (actionSystem.isLegalAction(agent, agentAction)) actionSystem.doAction(agent, agentAction);
        setChanged();
        notifyObservers();
    }


    /**
     * Méthode appelée en fin de jeu
     */
    @Override
    public void gameOver() {
        log.debug("Le jeu est fini");
    }

    /**
     * TODO : ??
     *
     * @return booléen
     */
    @Override
    public boolean gameContinue() {
        return true;
    }

    /**
     * Méthode permettant de changer la carte du jeu
     *
     * @param layoutPath le chemin du fichier comportant le layout de la carte
     */
    public void setMapFromLayoutPath(String layoutPath) {
        try {
            map = new Map(layoutPath);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Méthode d'initialisation des agents du jeu
     */
    public void initAgents() {
        for (InfoAgent agent : map.getStart_agents()) {
            try {
                AbstractAgent abstractAgent = AgentFactory.newAgent(agent.getType(), agent.getX(), agent.getY(),
                        agent.getAgentAction(), agent.getColor(), false, false);
                agents.add(abstractAgent);
                log.debug("Agent initialisé ==> " + agents.get(agents.size() - 1).toString());
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    /**
     * Méthode permettant de générer les InfoAgents des Agents du jeu courant
     *
     * @return La liste des infosAgents
     */
    public ArrayList<InfoAgent> getInfoAgents() {
        ArrayList<InfoAgent> infoAgents = new ArrayList<>();
        infoAgents.addAll(agents);
        return infoAgents;
    }

    public Map getMap() {
        return map;
    }

    public boolean[][] getBreakableWalls() {
        return breakableWalls;
    }

    public ArrayList<InfoItem> getItems() {
        return items;
    }

    public ArrayList<InfoBomb> getBombs() {
        return bombs;
    }

    public ArrayList<AbstractAgent> getAgents() {
        return agents;
    }



    public void bombHit(InfoBomb bomb) {
        int range = bomb.getRange();
        int posXbomb = bomb.getX();
        int posYbomb = bomb.getY();

        // Tue les agents dans la range de la bombe
        ArrayList<AbstractAgent> agentsToBeRemoved = new ArrayList<>();
        for (AbstractAgent agent : agents) {
            int posXagent = agent.getX();
            int posYagent = agent.getY();

            if (posXagent == posXbomb) {
                for (int i = 0; i <= range; i++) {
                    if ((posYagent == posYbomb + i) || (posYagent == posYbomb - i)) agentsToBeRemoved.add(agent);
                }
            }
            if (posYagent == posYbomb) {
                for (int i = 0; i <= range; i++) {
                    if ((posXagent == posXbomb + i) || (posXagent == posXbomb - i)) agentsToBeRemoved.add(agent);
                }
            }
        }
        for (AbstractAgent agent : agentsToBeRemoved) agents.remove(agent);

        // Détruit les murs dans la range de la bombe
        for (int i = 0; i <= range; i++) {
            if (breakableWalls[posXbomb][posYbomb + i] == true) {
                breakableWalls[posXbomb][posYbomb + i] = false;
                int randomItem = (int) Math.random() * 2;
                if (randomItem == 0) {
                    randomItem = (int) Math.random() * 5;
                    items.add(new InfoItem(posXbomb, posYbomb + i, getInfoItemFromInt(randomItem)));
                }
            }
            if (breakableWalls[posXbomb][posYbomb - i] == true) {
                breakableWalls[posXbomb][posYbomb - i] = false;
                int randomItem = (int) Math.random() * 2;
                if (randomItem == 0) {
                    randomItem = (int) Math.random() * 5;
                    items.add(new InfoItem(posXbomb, posYbomb - i, getInfoItemFromInt(randomItem)));
                }
            }
        }
        for (int i = 0; i <= range; i++) {
            if (breakableWalls[posXbomb + i][posYbomb] == true) {
                breakableWalls[posXbomb + i][posYbomb] = false;
                int randomItem = (int) Math.random() * 2;
                if (randomItem == 0) {
                    randomItem = (int) Math.random() * 5;
                    items.add(new InfoItem(posXbomb + i, posYbomb, getInfoItemFromInt(randomItem)));
                }
            }
            if (breakableWalls[posXbomb - i][posYbomb] == true) {
                breakableWalls[posXbomb - i][posYbomb] = false;
                int randomItem = (int) Math.random() * 2;
                if (randomItem == 0) {
                    randomItem = (int) Math.random() * 5;
                    items.add(new InfoItem(posXbomb - i, posYbomb, getInfoItemFromInt(randomItem)));
                }
            }
        }

    }

    public ItemType getInfoItemFromInt(int i) {
        switch (i) {
            case 0:
                return ItemType.FIRE_UP;
            case 1:
                return ItemType.FIRE_DOWN;
            case 2:
                return ItemType.BOMB_UP;
            case 3:
                return ItemType.BOMB_DOWN;
            case 4:
                return ItemType.FIRE_SUIT;
            case 5:
                return ItemType.SKULL;
            default:
                log.error("Item inconnu ==> " + i);
                return null;
        }
    }

    public void update() {
        setChanged();
        notifyObservers();
    }

    public boolean isFree(Coordonnee c) {
        if (breakableWalls[c.x][c.y] || map.get_walls()[c.x][c.y]) {
            return false;
        }
        for (InfoBomb b : bombs) {
            if (b.getX() == c.x && b.getY() == c.y) return false;
        }
        return true;
    }


}


