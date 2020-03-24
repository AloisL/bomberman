package controller;

import res.Map;
import res.enums.AgentAction;
import res.enums.GameState;

import java.util.Observable;

public class ClientController extends Observable implements Runnable {

    boolean isRunning;
    long sleepTime = 100;

    public GameState gameState;
    private Map map;
    private int lifes;

    /**
     * Méthode daemon permettant de faire des appels REST au serveur permettant d'obtenir toutes les infos du jeu.
     */
    @Override
    public void run() {
        isRunning = true;
        while (isRunning) {
            // 1 : update la map via appels REST au serveur de jeu
            // 2 : update la vue via le pattern observateur
            setChanged();
            notifyObservers();
            try {
                if (!Thread.currentThread().isInterrupted())
                    Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                isRunning = false;
                e.printStackTrace();
            }
        }
    }

    public void init() {
        // Initilisation de la partie
        // Sera utilisé pour l'authentification

        /**
         * if (bombermanGame.getIsRunning()) bombermanGame.stop();
         * bombermanGame.init();
         */
    }

    public void start() {
        // useless
        // méthode censée effectuer lancer le jeu;
        // Sera utilisée pour mettre le joueur dans l'état pret
        // Lorsque tous les joueurs sont prets, le serveur lance la partie
        /**
         * gameState = GameState.GAME_RUNNING;
         * bombermanGame.launch();
         */
    }

    public void pause() {
        // useless
        // méthode censée effectuer mettre en pause le jeu
        /**
         * gameState = GameState.GAME_PAUSED;
         * bombermanGame.stop();
         */
    }

    public void step() {
        // useless
        // méthode censée effectuer un tour de jeu
        /**
         * bombermanGame.step();
         */
    }

    public void changeLayout() {
        // useless ?
        // méthode censée changer la carte du jeu
        /**
         * gameState = GameState.GAME_PAUSED;
         * bombermanGame.stop();
         */
    }

    public void setTime(int value) {
        // useless
        /**
         * Long sleepTime = (long) 1000 / turnPerSec;
         * bombermanGame.setSleepTime(sleepTime);
         */
    }

    public void updatePlayerAction(AgentAction action) {
        // envoi de la prochaine action souhaitée au serveur.

        /**
         * ArrayList<AbstractAgent> players = bombermanGame.getPlayers();
         *         if (!players.isEmpty()) {
         *             AbstractAgent player = players.get(0);
         *             // Le placement de la bombe doit être instantané, on byepasse donc la cadence du jeu.
         *             if (action == AgentAction.PUT_BOMB) {
         *                 ActionSystem actionSystem = new ActionSystem(bombermanGame);
         *                 if (actionSystem.isLegalAction(player, action)) {
         *                     actionSystem.doAction(player, action);
         *                 }
         *             }
         *             if (player != null) player.setAgentAction(action);
         *         } else log.debug("updatePlayerAction: Liste des joueurs vide");
         */
    }

    public Map getMap() {
        return map;
    }

    public int getLifes() {
        return lifes;
    }


}
