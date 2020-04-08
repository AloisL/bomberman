package client;

import common.BombermanDTO;
import common.enums.AgentAction;
import controller.ClientController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * Classe de gestion de la vue du jeu
 * TODO : Ajouter un grid layout pour éviter les bugs de taille de fenêtre
 */
public class ClientView extends JFrame implements Observer, WindowListener {

    final static Logger log = (Logger) LogManager.getLogger(ClientView.class);

    public JTextPane infoLabel;
    public PanelBomberman panelBomberman;
    public PanelInput panelInput;
    ClientController clientController;
    JPanel mainPanel;
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
        setInfoLabel();
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
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(title);
        setSize(new Dimension(500, 220));
        setLocationRelativeTo(null);
    }

    /**
     * Méthode d'initalisation de la zone d'informations
     */
    private void setInfoLabel() {
        infoLabel = new JTextPane();
        infoLabel.setFocusable(false);
        StyledDocument doc = infoLabel.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
    }

    /**
     * Méthode d'initialisation des Panel de la fenêtre
     */
    public void setPanels() {
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

    /**
     * Méthode appelée lorsque le jeu est mis à jour (uniquement appelée par le controller)
     *
     * @param observable Le jeu
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        ClientController clientController = (ClientController) observable;
        BombermanDTO bombermanDTO = clientController.getBombermanDTO();
        panelBomberman.setInfoGame(bombermanDTO.getBreakableWalls(), bombermanDTO.getInfoAgents(),
                bombermanDTO.getInfoItems(), bombermanDTO.getInfoBombs());
        repaint();
    }

    /**
     * Méthode appelée lorsque la game est quittée ou terminée
     */
    public void postGame() {
        if (panelBomberman != null) {
            panelBomberman.setVisible(false);
            panelBomberman = null;
            log.debug(this.getComponentCount());
        }
        panelInput.preGameMode();
        setSize(new Dimension(500, 220));
    }

    /**
     * Méthode d'ajout du panel du jeu bomberman à la fenêtre
     *
     * @param panelBomberman Le panel du jeu bomberman
     */
    public void addPanelBomberman(PanelBomberman panelBomberman) {
        // Si un panel bomberman est déjà set, on le supprime
        for (Component component : getComponents()) {
            if (component.getClass() == PanelBomberman.class) {
                remove(component);
            }
        }

        this.panelBomberman = panelBomberman;

        // Taille du panel relative à la taille de la map
        int sizeX = clientController.getBombermanDTO().getSizeX() * 50;
        int sizeY = clientController.getBombermanDTO().getSizeY() * 50;

        mainPanel.add(panelBomberman);
        panelBomberman.grabFocus();

        // Ajout des listeners clavier
        initKeyListener();

        // Taille et position de la fenêtre
        setSize(new Dimension(sizeX, sizeY + panelInput.getHeight() + 40));
        repaint();

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
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {
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

    public void serverConnectedAllowReady() {
        panelInput.panelInputPreGame.readyButton.setEnabled(true);
    }

}
