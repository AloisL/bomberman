package client;

import common.enums.AgentAction;
import controller.ClientController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * Classe de gestion de la vue du jeu
 */
public class ClientView extends JFrame implements WindowListener {

    final static Logger log = (Logger) LogManager.getLogger(ClientView.class);

    public JLabel infoLabel;
    ClientController clientController;
    JPanel mainPanel;
    PanelInput panelInput;
    PanelBomberman panelBomberman;
    String title;

    /**
     * Constructeur de la vue
     *
     * @param clientController Le controleur du jeu
     * @param title            Le titre du jeu
     */
    public ClientView(ClientController clientController, String title) {
        this.title = title;
        this.clientController = clientController;
        clientController.setClientView(this);
        init();
    }

    /**
     * Méthode d'initialisation
     */
    public void init() {
        initFrame(title);
        setPanels();
        setVisible(true);
        setFocusable(true);
    }

    /**
     * Méthode d'initialisation de la fenêtre
     *
     * @param title Le titre du jeu
     */
    public void initFrame(String title) {
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(title);

        if (panelInput == null) setSize(new Dimension(500, 200));
        setLocationRelativeTo(null);

        /* Permet la gestion du comportement lorsque l'on modifie la taille de la fenêtre */
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                if (clientController.getBombermanDTO() != null) {
                    Integer sizeX = clientController.getBombermanDTO().getSizeX() * 50;
                    Integer sizeY = clientController.getBombermanDTO().getSizeY() * 50;
                    if (panelBomberman != null) panelBomberman.setSize(new Dimension(sizeX, sizeY));
                }
                repaint();
            }
        });

    }

    /**
     * Méthode d'initialisation des Panel de la fenêtre
     */
    public void setPanels() {
        infoLabel = new JLabel();
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        if (mainPanel == null) {
            mainPanel = new JPanel(new BorderLayout());
            if (panelInput == null) {
                panelInput = new PanelInput(clientController, this);
                mainPanel.add(panelInput, BorderLayout.NORTH);
                panelInput.loginMode();
            }
            add(mainPanel);
        }
    }

    /*
     */
/**
 * Méthode appelée lorsque le jeu est mis à jour (uniquement appelée par le controller)
 *
 * @param observable Le jeu
 * @param o
 *//*

    @Override
    public void update(Observable observable, Object o) {
        ClientController clientController = (ClientController) observable;
        BombermanDTO bombermanDTO = clientController.getBombermanDTO();
        panelBomberman.setInfoGame(bombermanDTO.getBreakableWalls(), bombermanDTO.getInfoAgents(),
                bombermanDTO.getInfoItems(), bombermanDTO.getInfoBombs());
        displayUpdate();
        repaint();
    }
*/

    /**
     * Méthode de mise à jour de l'affichage
     */
    private void displayUpdate() {
        switch (clientController.gameState) {
            case GAME_WON:
                setInfo(" GAME WON ");
                break;
            case GAME_OVER:
                setInfo(" GAME OVER ");
                break;
            case GAME_RUNNING:
                String currentTurnStr = " Nombre de vie: " + clientController.getLifes();
                setInfo(currentTurnStr);
                break;
        }
    }

    public void gameOver() {
        displayUpdate();
        panelInput.panelControl.gameOver();
    }

    public void gameWon() {
        displayUpdate();
        panelInput.panelControl.gameWon();
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
        int sizeX = clientController.getBombermanDTO().getSizeX() * 50;
        int sizeY = clientController.getBombermanDTO().getSizeY() * 50;

        // Ajout du panel bomberman
        panelBomberman.setSize(new Dimension(sizeX, sizeY));
        mainPanel.add(panelBomberman, BorderLayout.CENTER);
        panelBomberman.grabFocus();

        initKeyListener();

        // Taille et position de la fenêtre
        setSize(sizeX, sizeY + panelInput.getHeight() + 40);
        setLocationRelativeTo(null);
        panelBomberman.repaint();
        setVisible(true);

    }

    public void initKeyListener() {
        panelBomberman.addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();
                if (key != KeyEvent.VK_SPACE) {
                    try {
                        clientController.updatePlayerAction(AgentAction.STOP);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();
                try {

                    switch (key) {
                        case KeyEvent.VK_LEFT: {
                            try {
                                clientController.updatePlayerAction(AgentAction.MOVE_LEFT);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
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
                } catch (IOException e) {
                    e.printStackTrace();
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

    public void setInfo(String message) {
        infoLabel.setText(message);
        repaint();
    }

}
