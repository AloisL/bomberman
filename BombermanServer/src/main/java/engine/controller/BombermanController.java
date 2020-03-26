package engine.controller;

/**
 * Classe du controleur du jeu
 */
public class BombermanController {
/*

    final static Logger log = (Logger) LogManager.getLogger(BombermanController.class);

    public GameState gameState = GameState.WAITING;
    public int nbLife;
    BombermanGame bombermanGame;
    BombermanPanel bombermanPanel;
    BombermanView bombermanView;

    */
/**
 * Constructeur
 *
 * @param maxTurn nombre maximal de tours
 *//*

    public BombermanController(int maxTurn) {
        bombermanGame = new BombermanGame(this, maxTurn, 1);
        bombermanView = new BombermanView(this, "Bomberman Command");
        bombermanGame.addObserver(bombermanView);
    }

    */
/**
 * Méthode d'initialisation du jeu
 *//*

    public void init() {
        if (bombermanGame.getIsRunning()) bombermanGame.stop();
        bombermanGame.init();
    }

    */
/**
 * Méthode de lancement du jeu
 *//*

    public void run() {
        gameState = GameState.GAME_RUNNING;
        bombermanGame.launch();
    }

    */
/**
 * Méthode de lancement d'un tour de jeu
 *//*

    public void step() {
        bombermanGame.step();
    }

    */
/**
 * Méthode permettant de communiquer une action utilisateur au personnage du jeu
 *
 * @param action
 *//*

    public void updatePlayerAction(AgentAction action) {
        ArrayList<AbstractAgent> players = bombermanGame.getPlayers();
        if (!players.isEmpty()) {
            AbstractAgent player = players.get(0);
            // Le placement de la bombe doit être instantané, on byepasse donc la cadence du jeu.
            if (action == AgentAction.PUT_BOMB) {
                ActionSystem actionSystem = new ActionSystem(bombermanGame);
                if (actionSystem.isLegalAction(player, action)) {
                    actionSystem.doAction(player, action);
                }
            }
            if (player != null) player.setAgentAction(action);
        } else log.debug("updatePlayerAction: Liste des joueurs vide");

    }

    */
/**
 * Méthode mettant le jeu en pause
 *//*

    @Override
    public void pause() {
        gameState = GameState.GAME_PAUSED;
        bombermanGame.stop();
    }

    */
/**
 * Méthode appelée lorsque la partie est terminée
 *//*

    @Override
    public void gameOver() {
        gameState = GameState.GAME_OVER;

        // TODO GAME OVER aux joueurs perdants
        bombermanView.gameOver();
    }

    */
/**
 * Méthode appelée lorsque la partie est gagnée
 *//*

    public void gameWon() {
        gameState = GameState.GAME_WON;

        // TODO GAME WON au joueur gagnant
        bombermanView.gameWon();
    }

    */
/**
 * Méthode de changement du layout du jeu en utilisant le layout choisi dans la vue
 *//*

    public void changeLayout() {
        String layout = "res/layouts/" + bombermanView.getLayout();
        bombermanView.init();
        bombermanGame.setMapFromLayoutPath(layout);
        bombermanPanel = new BombermanPanel(getMap());
        bombermanView.addPanelBomberman(bombermanPanel);
    }

    public Map getMap() {
        return bombermanGame.getMap();
    }

    public BombermanGame getBombermanGame() {
        return bombermanGame;
    }
*/

}
