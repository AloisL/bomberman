package engine;

import common.enums.AgentAction;
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
import server.GameManager;
import server.GameServerInstance;

import java.util.ArrayList;
import java.util.Observable;

/**
 * La classe principale du jeu bomberman, permet la gestion complète d'une partie
 */
public class BombermanGame extends Observable implements Runnable {

    final static Logger log = (Logger) LogManager.getLogger(BombermanGame.class);

    public volatile boolean isRunning = false;
    public ArrayList<GameServerInstance> gameServerInstances = new ArrayList<>();
    public Integer currentTurn;
    public Long sleepTime = 350L;
    public String layout;
    public ArrayList<AbstractAgent> players;
    public int maxPlayers;
    private Map map;
    private ActionSystem actionSystem;
    private DamageSystem damageSystem;
    private ItemSystem itemSystem;
    private ArrayList<AbstractAgent> agents;
    private ArrayList<AbstractAgent> agentsIa;
    private boolean[][] breakableWalls;
    private ArrayList<InfoItem> items;
    private ArrayList<InfoBomb> bombs;

    /**
     * Constructor
     *
     * @param layout
     * @param maxPlayers
     */
    public BombermanGame(String layout, int maxPlayers) {
        this.layout = layout;
        this.maxPlayers = maxPlayers;

        map = getMapFromLayout(layout);

        AbstractAgent.resetId();

        items = new ArrayList<>();
        bombs = new ArrayList<>();
        agentsIa = new ArrayList<>();

        agents = new ArrayList<>();
        players = new ArrayList<>();
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

        breakableWalls = map.getBreakableWalls();
        initAgents();
        linkInstances();

        for (AbstractAgent agent : agents) {
            if (agent.getType() != 'B') {
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
        for (InfoAgent agent : map.getInfoAgents()) {
            int i = 0;
            try {
                AbstractAgent abstractAgent = AgentFactory.newAgent(agent.getType(), agent.getX(), agent.getY(),
                        agent.getAgentAction(), agent.getColor(), false, false);
                // ajout des joueurs
                agents.add(abstractAgent);
                if ((agent.getType() == 'B') && (i < maxPlayers)) {
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
     * Méthode permettant de lier les agents bomberman aux instances de jeu par ordre d'entrée dans la partie
     */
    private void linkInstances() {
        for (GameServerInstance gameServerInstance : gameServerInstances) {
            BombermanAgent linkedBombermanAgent = gameServerInstance.bombermanAgent;
            if (linkedBombermanAgent == null) {
                for (AbstractAgent agent : players) {
                    BombermanAgent bombermanAgent = (BombermanAgent) agent;
                    if (!bombermanAgent.isLinked) {
                        gameServerInstance.bombermanAgent = bombermanAgent;
                        bombermanAgent.isLinked = true;
                        break;
                    }
                }
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

        /** Appels des sous-systèmes pour le tour courant **/
        // Gestion des actions
        actionSystem.run();
        // Gestion des dégats
        damageSystem.run();
        // Génération et obtention des objets
        itemSystem.run();

        gameStatus();

        setChanged();
        notifyObservers();
        log.debug("Fin du tour " + currentTurn);
    }

    /**
     * Gestion de la fin de partie
     */
    private void gameStatus() {
        if (players.size() == 0) stop();
        else if (agents.size() == 1) stop();
    }

    /**
     * Méthode appelée en fin de jeu
     */
    public void stop() {
        // TODO : gestion de fin de game terminée.
        isRunning = false;
        setChanged();
        notifyObservers();
        for (GameServerInstance gmi : gameServerInstances) {
            gmi.terminate();
        }
        GameManager.closeGame(this);
    }

    /**
     * Méthode de gestion des inputs joueur
     *
     * @param gameServerInstance
     * @param agentAction
     */
    public void setAction(GameServerInstance gameServerInstance, AgentAction agentAction) {
        if (isRunning) {
            BombermanAgent player = gameServerInstance.bombermanAgent;

            // Le placement de la bombe doit être instantané, on byepasse donc la cadence du jeu.
            // On ne peut malgré tout poser qu'un certain nombre de bombes définit dans les propriété de l'agent
            if (agentAction == AgentAction.PUT_BOMB) {
                ActionSystem actionSystem = new ActionSystem(this);
                if (actionSystem.isLegalAction(gameServerInstance.bombermanAgent, agentAction)) {
                    actionSystem.doAction(player, agentAction);
                }
            }
            // Dans tous les autres cas on définit la prochaine action a effectuer
            player.setAgentAction(agentAction);
        }
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

    public void removeInstanceLeftovers(GameServerInstance gameServerInstance) {
        BombermanAgent playerToRemove = gameServerInstance.bombermanAgent;

        if (players.contains(playerToRemove))
            players.remove(playerToRemove);

        if (agents.contains(playerToRemove))
            agents.remove(playerToRemove);

        if (gameServerInstances.contains(gameServerInstance))
            gameServerInstances.remove(gameServerInstance);
    }
}


