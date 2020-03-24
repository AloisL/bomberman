package client;

import controller.ClientController;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import res.Map;
import res.enums.AgentAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Classe de gestion de la vue du jeu
 */
public class ClientView implements Observer, WindowListener {

    final static Logger log = (Logger) LogManager.getLogger(ClientView.class);

    ClientController clientController;
    JFrame window;
    JPanel mainPanel;
    PanelCommand panelCommand;
    PanelBomberman panelBomberman;
    String title;

    /**
     * Constructeur de la vue
     *
     * @param clientController Le controleur du jeu
     * @param title            Le titre du jeu
     */
    public ClientView(ClientController clientController, String title) {
        this.clientController = clientController;
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
        if (panelCommand == null) window.setSize(new Dimension(500, 200));
        window.setLocationRelativeTo(null);

        /* Permet la gestion du comportement lorsque l'on modifie la taille de la fenêtre */
        window.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                if (clientController.getMap() != null) {
                    Integer sizeX = clientController.getMap().getSizeX() * 50;
                    Integer sizeY = clientController.getMap().getSizeY() * 50;
                    if (panelBomberman != null) panelBomberman.setSize(new Dimension(sizeX, sizeY));
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
            if (panelCommand == null) {
                panelCommand = new PanelCommand(clientController, this);
                mainPanel.add(panelCommand, BorderLayout.NORTH);
            }
            window.add(mainPanel);
        }
    }

    /**
     * Méthode appelée lorsque le jeu est mis à jour (uniquement appelée par le controller)
     *
     * @param observable Le jeu
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {

        ClientController clientController = (ClientController) observable;
        Map map = clientController.getMap();
        panelBomberman.setInfoGame(map.getBreakableWalls(), map.getInfoAgents(),
                map.getInfoItems(), map.getInfoBombs());
        displayUpdate();
        window.repaint();
    }

    /**
     * Méthode de mise à jour de l'affichage
     */
    private void displayUpdate() {
        switch (clientController.gameState) {
            case GAME_WON:
                panelCommand.infoLabel.setText(" GAME WON ");
                break;
            case GAME_OVER:
                panelCommand.infoLabel.setText(" GAME OVER ");
                break;
            case GAME_RUNNING:
                String currentTurnStr = " Nombre de vie: " + clientController.getLifes();
                panelCommand.infoLabel.setText(currentTurnStr);
                break;
        }
    }

    public void gameOver() {
        displayUpdate();
        panelCommand.gameOver();
    }

    public void gameWon() {
        displayUpdate();
        panelCommand.gameWon();
    }

    /**
     * Méthode d'ajout du panel du jeu bomberman à la fenêtre
     *
     * @param panelBomberman Le panel du jeu bomberman
     */
    public void addPanelBomberman(PanelBomberman panelBomberman) {
        // Si un panel bomberman est déjà set, on le supprime
        if (this.panelBomberman != null) mainPanel.remove(this.panelBomberman);

        this.panelBomberman = panelBomberman;

        // Taille du panel relative à la taille de la map
        int sizeX = clientController.getMap().getSizeX() * 50;
        int sizeY = clientController.getMap().getSizeY() * 50;

        // Ajout du panel bomberman
        panelBomberman.setSize(new Dimension(sizeX, sizeY));
        mainPanel.add(panelBomberman, BorderLayout.CENTER);
        panelBomberman.grabFocus();

        initKeyListener();

        // Taille et position de la fenêtre
        window.setSize(sizeX, sizeY + panelCommand.getHeight() + 40);
        window.setLocationRelativeTo(null);
        panelBomberman.repaint();
        window.setVisible(true);

    }

    public String getLayout() {
        return (String) panelCommand.layoutChooser.getSelectedItem();
    }

    public void initKeyListener() {
        panelBomberman.addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();
                if (key != KeyEvent.VK_SPACE) {
                    clientController.updatePlayerAction(AgentAction.STOP);
                }
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_LEFT: {
                        clientController.updatePlayerAction(AgentAction.MOVE_LEFT);
                    }
                    break;
                    case KeyEvent.VK_RIGHT: {
                        clientController.updatePlayerAction(AgentAction.MOVE_RIGHT);
                    }
                    break;
                    case KeyEvent.VK_UP: {
                        clientController.updatePlayerAction(AgentAction.MOVE_UP);
                    }
                    break;
                    case KeyEvent.VK_DOWN: {
                        clientController.updatePlayerAction(AgentAction.MOVE_DOWN);
                    }
                    break;
                    case KeyEvent.VK_SPACE: {
                        clientController.updatePlayerAction(AgentAction.PUT_BOMB);
                    }
                    default:
                        clientController.updatePlayerAction(AgentAction.STOP);
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
        clientController.pause();
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {
        clientController.pause();
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

    public PanelBomberman getPanelBomberman() {
        return panelBomberman;
    }


}
