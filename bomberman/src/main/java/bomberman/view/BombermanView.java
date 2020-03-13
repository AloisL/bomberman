package bomberman.view;

import bomberman.controller.BombermanController;
import bomberman.model.engine.BombermanGame;
import bomberman.model.engine.enums.AgentAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Classe de gestion de la vue du jeu
 */
public class BombermanView implements Observer, WindowListener {

    final static org.apache.logging.log4j.core.Logger log = (Logger) LogManager.getLogger(BombermanView.class);

    BombermanController bombermanController;
    JFrame window;
    JPanel mainPanel;
    CommandPanel commandPanel;
    BombermanPanel bombermanPanel;
    Integer currentTurn;
    String title;

    /**
     * Constructeur de la vue
     *
     * @param bombermanController Le controleur du jeu
     * @param title               Le titre du jeu
     */
    public BombermanView(BombermanController bombermanController, String title) {
        this.bombermanController = bombermanController;
        this.title = title;
        window = new JFrame();
        init();
    }

    /**
     * Méthode d'initialisation
     */
    public void init() {
        initFrame(title);
        setPanels();
        window.setVisible(true);
        window.setFocusable(true);
    }

    /**
     * Méthode d'initialisation de la fenêtre
     *
     * @param title Le titre du jeu
     */
    public void initFrame(String title) {

        /* Permet de fermer l'application après avoir quitter la vue */
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        window.setTitle(title);
        if (commandPanel == null) window.setSize(new Dimension(500, 200));
        window.setLocationRelativeTo(null);

        /* Permet la gestion du comportement lorsque l'on modifie la taille de la fenêtre */
        window.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                if (bombermanController.getMap() != null) {
                    Integer sizeX = bombermanController.getMap().getSizeX() * 50;
                    Integer sizeY = bombermanController.getMap().getSizeY() * 50;
                    if (bombermanPanel != null) bombermanPanel.setSize(new Dimension(sizeX, sizeY));
                }
                window.repaint();
            }
        });

    }

    /**
     * Méthode d'initialisation des Panel de la fenêtre
     */
    public void setPanels() {
        if (mainPanel == null) {
            mainPanel = new JPanel(new BorderLayout());
            if (commandPanel == null) {
                commandPanel = new CommandPanel(this);
                mainPanel.add(commandPanel, BorderLayout.NORTH);
            }
            window.add(mainPanel);
        }
    }

    /**
     * Méthode appelée lorsque le jeu est mis à jour (uniquement appelée par le jeu)
     *
     * @param observable Le jeu
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        BombermanGame bombermanGame = (BombermanGame) observable;
        bombermanPanel.setInfoGame(bombermanGame.getBreakableWalls(), bombermanGame.getInfoAgents(),
                bombermanGame.getItems(), bombermanGame.getBombs());
        currentTurn = bombermanGame.getCurrentTurn();
        displayUpdate();
        window.repaint();
    }

    /**
     * Méthode de mise à jour de l'affichage
     */
    private void displayUpdate() {
        switch (bombermanController.gameState) {
            case GAME_WON:
                commandPanel.infoLabel.setText(" GAME WON ");
                break;
            case GAME_OVER:
                commandPanel.infoLabel.setText(" GAME OVER ");
                break;
            case GAME_RUNNING:
                String currentTurnStr = " Nombre de vie: " + bombermanController.getBombermanGame().getNblife();
                commandPanel.infoLabel.setText(currentTurnStr);
                break;
        }
    }

    public void gameOver() {
        displayUpdate();
        commandPanel.gameOver();
    }

    public void gameWon() {
        displayUpdate();
        commandPanel.gameWon();
    }

    /**
     * Méthode d'ajout du panel du jeu bomberman à la fenêtre
     *
     * @param bombermanPanel Le panel du jeu bomberman
     */
    public void addPanelBomberman(BombermanPanel bombermanPanel) {
        // Si un panel bomberman est déjà set, on le supprime
        if (this.bombermanPanel != null) mainPanel.remove(this.bombermanPanel);

        this.bombermanPanel = bombermanPanel;

        // Taille du panel relative à la taille de la map
        int sizeX = bombermanController.getMap().getSizeX() * 50;
        int sizeY = bombermanController.getMap().getSizeY() * 50;

        // Ajout du panel bomberman
        bombermanPanel.setSize(new Dimension(sizeX, sizeY));
        mainPanel.add(bombermanPanel, BorderLayout.CENTER);
        bombermanPanel.grabFocus();

        initKeyListener();

        // Taille et position de la fenêtre
        window.setSize(sizeX, sizeY + commandPanel.getHeight() + 40);
        window.setLocationRelativeTo(null);
        bombermanPanel.repaint();
        window.setVisible(true);

    }

    public String getLayout() {
        return (String) commandPanel.layoutChooser.getSelectedItem();
    }

    public void initKeyListener() {
        bombermanPanel.addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();
                if (key != KeyEvent.VK_SPACE) {
                    bombermanController.updatePlayerAction(AgentAction.STOP);
                }
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_LEFT: {
                        bombermanController.updatePlayerAction(AgentAction.MOVE_LEFT);
                    }
                    break;
                    case KeyEvent.VK_RIGHT: {
                        bombermanController.updatePlayerAction(AgentAction.MOVE_RIGHT);
                    }
                    break;
                    case KeyEvent.VK_UP: {
                        bombermanController.updatePlayerAction(AgentAction.MOVE_UP);
                    }
                    break;
                    case KeyEvent.VK_DOWN: {
                        bombermanController.updatePlayerAction(AgentAction.MOVE_DOWN);
                    }
                    break;
                    case KeyEvent.VK_SPACE: {
                        bombermanController.updatePlayerAction(AgentAction.PUT_BOMB);
                    }
                    default:
                        bombermanController.updatePlayerAction(AgentAction.STOP);
                        break;
                }
            }

            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

        });
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {
    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        bombermanController.pause();
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {
        bombermanController.pause();
    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {
    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {
    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {
    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {
    }

    public BombermanController getBombermanController() {
        return bombermanController;
    }

    public BombermanPanel getBombermanPanel() {
        return bombermanPanel;
    }


}
