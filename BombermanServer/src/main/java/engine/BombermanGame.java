package engine;

import common.enums.AgentAction;
import common.enums.ColorAgent;
import common.infotypes.InfoAgent;
import common.infotypes.InfoBomb;
import common.infotypes.InfoItem;
import engine.agents.AbstractAgent;
import engine.agents.AgentFactory;
import engine.agents.BombermanAgent;
import engine.subsystems.ActionSystem;
import engine.subsystems.DamageSystem;
import engine.subsystems.ItemSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Observable;

/**
 * La classe principale du jeu bomberman, permet la gestion complète d'une partie
 */
public class BombermanGame extends Observable implements Runnable {

    final static Logger log = (Logger) LogManager.getLogger(BombermanGame.class);

    public Boolean isRunning = false;
    public Integer currentTurn;
    public Long sleepTime = 350L;
    public int maxPlayers;
    public int currentPlayers;
    public String layout;

    private Map map;
    private ActionSystem actionSystem;
    private DamageSystem damageSystem;
    private ItemSystem itemSystem;
    private ArrayList<AbstractAgent> agents;
    private ArrayList<AbstractAgent> agentsIa;
    private boolean[][] breakableWalls;
    private ArrayList<InfoItem> items;
    private ArrayList<InfoBomb> bombs;
    private int nbPlayers;
    private ArrayList<AbstractAgent> players;

    /**
     * Constructor
     *
     * @param layout
     * @param nbPlayers
     */
    public BombermanGame(String layout, int nbPlayers) {
        this.layout = layout;
        this.nbPlayers = nbPlayers;
        map = getMapFromLayout(layout);
    }

    /**
     * Permet de générer la carte du jeu à partir du layout présent sur le disque
     *
     * @param layout
     * @return
     */
    private Map getMapFromLayout(String layout) {
        String layoutPath = "src/main/resources/layouts/" + layout;
        try {
            map = new Map(layoutPath);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return map;
    }

    /**
     * Médode d'initialisation des éléments du jeu
     */
    public void init() {
        log.debug("Initialisation du jeu");
        currentTurn = 0;
        isRunning = false;

        AbstractAgent.resetId();
        items = new ArrayList<>();
        bombs = new ArrayList<>();
        agentsIa = new ArrayList<>();

        breakableWalls = map.getBreakableWalls();
        initAgents();

        for (AbstractAgent agent : agents) {
            if (agent.getColor() != ColorAgent.BLEU) {
                agentsIa.add(agent);
            }
        }

        actionSystem = new ActionSystem(this);
        damageSystem = new DamageSystem(this);
        itemSystem = new ItemSystem(this);

        log.debug("Jeu initialisé");
        setChanged();
        notifyObservers();
    }

    /**
     * Méthode d'initialisation des agents du jeu
     */
    private void initAgents() {
        agents = new ArrayList<>();
        players = new ArrayList<>();
        for (InfoAgent agent : map.getInfoAgents()) {
            int i = 0;
            try {
                AbstractAgent abstractAgent = AgentFactory.newAgent(agent.getType(), agent.getX(), agent.getY(),
                        agent.getAgentAction(), agent.getColor(), false, false);
                // ajout des joueurs
                agents.add(abstractAgent);
                if ((agent.getColor() == ColorAgent.BLEU) && (i < nbPlayers)) {
                    players.add(abstractAgent);
                    i++;
                }
                log.debug("Agent initialisé ==> " + agents.get(agents.size() - 1).toString());
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    /**
     * Méthode de démarrage de la partie,
     * Lance le jeu sur un Thread
     */
    public void launch() {
        isRunning = true; // Permet le lancement du jeu après la mise en pause.
        setChanged();
        notifyObservers();
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Méthode de "cadencage" du jeu bouclant sur un thread tant que le jeu continue
     */
    @Override
    public void run() {
        while (isRunning) {
            step();
            try {
                if (!Thread.currentThread().isInterrupted())
                    Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Méthode de gestion des tours de jeu
     */
    public void step() {
        currentTurn += 1;
        takeTurn();
        setChanged();
        notifyObservers();
    }

    /**
     * Méthode d'appel d'un tour de jeu complet.
     * Cette méthode est appelée à chaque tour de jeu afin d'effectuer les actions globale d'un tour de jeu (état des
     * bombes, déplacement des IA, apparition des items etc).
     */
    public void takeTurn() {
        log.debug("Début du tour " + currentTurn);
        // Exécution des sous-systèmes pour le tour courant
        actionSystem.run();
        damageSystem.run();
        itemSystem.run();
        // Gestion de la fin de la partie
        if (players.size() == 0) gameOver();
        else if (agents.size() == 1) gameWon();
        else {
            for (AbstractAgent agent : agentsIa) {
                if (agent.getColor() != ColorAgent.BLEU) {
                    agent.setStrategie(this);
                    AgentAction action = agent.getStrategie().doStrategie();
                    if (actionSystem.isLegalAction(agent, action)) {
                        agent.setAgentAction(action);
                    } else actionSystem.doAction(agent, AgentAction.STOP);
                }
            }
        }
        setChanged();
        notifyObservers();
        log.debug("Fin du tour " + currentTurn);
    }

    public void stop() {
        isRunning = false;
        setChanged();
        notifyObservers();
    }

    /**
     * Méthode appelée en fin de jeu
     */
    public void gameOver() {
        isRunning = false;
        //bombermanController.gameOver();
    }

    public void gameWon() {
        isRunning = false;
        //bombermanController.gameWon();
    }

    /**
     * Méthode permettant de générer les InfoAgents des Agents du jeu courant
     * Les infoAgents sont un type générique représentant un agent du jeu
     * Utilisé nottament pour le BombermanDTO
     *
     * @return La liste des infosAgents
     */
    public ArrayList<InfoAgent> getInfoAgents() {
        ArrayList<InfoAgent> infoAgents = new ArrayList<>();
        for (AbstractAgent agent : getAgentsCopy()) {
            InfoAgent tmp = new InfoAgent(agent.getX(), agent.getY(), agent.getAgentAction(), agent.getType(),
                    agent.getColor(), agent.isInvincible(), agent.isSick());
            infoAgents.add(tmp);
        }
        return infoAgents;
    }

    /**
     * Getter retournant une copie afin d'éviter la concurrence d'accès.
     * Synchoniser les threads auraient pu être possible mais cela compliquait énormément le code pour un
     * résultat équivalent.
     */
    public ArrayList<AbstractAgent> getAgentsCopy() {
        return new ArrayList<>(agents);
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

    public ActionSystem getActionSystem() {
        return actionSystem;
    }

    public ArrayList<AbstractAgent> getPlayers() {
        return players;
    }

    public ArrayList<AbstractAgent> getAgentsIa() {
        return agentsIa;
    }

    public int getNblife() {
        // TODO: Gestion des joueurs à revoir, ce code est douteux
        BombermanAgent player = (BombermanAgent) players.get(0);
        return player.getNbLifes();
    }

}


