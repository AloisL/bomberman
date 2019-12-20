package bomberman.view;

import bomberman.controller.BombermanController;
import bomberman.model.BombermanGame;
import bomberman.model.repo.AgentAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Classe de gestion de la vue du jeu
 */
public class BombermanView implements Observer, WindowListener {

    private BombermanController controller;
    private Integer currentTurn;
    private JFrame window;
    private JPanel mainPanel;
    private PanelCommand panelCommand;
    private PanelBomberman bombermanPanel;
    private boolean keyboardInputLocked = false;

    /**
     * Constructeur de la vue
     *
     * @param controller Le controleur du jeu
     * @param title      Le titre du jeu
     */
    public BombermanView(BombermanController controller, String title) {
        this.controller = controller;
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
    private void initFrame(String title) {
        window = new JFrame();

        /* Permet de fermer l'application après avoir quitter la vue */
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        window.setTitle(title);
        window.setSize(new Dimension(500, 200));
        Dimension windowSize = window.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2;
        int dy = centerPoint.y - (windowSize.height / 2) - 350;
        window.setLocation(dx, dy);

        /* Permet la gestion du comportement lorsque l'on modifie la taille de la fenêtre */
        window.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                if (controller.getMap() != null) {
                    Integer sizeX = controller.getMap().getSizeX() * 50;
                    Integer sizeY = controller.getMap().getSizeY() * 50;
                    if (bombermanPanel != null) bombermanPanel.setSize(new Dimension(sizeX, sizeY));
                }
                window.repaint();
            }
        });

    }

    /**
     * Méthode d'initialisation des Panel de la fenêtre
     */
    private void setPanels() {
        mainPanel = new JPanel(new BorderLayout());
        panelCommand = new PanelCommand(this);
        mainPanel.add(panelCommand, BorderLayout.NORTH);
        window.add(mainPanel);
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
        String currentTurnStr = "Turn: " + currentTurn.toString();
        panelCommand.getCurrentTurnLabel().setText(currentTurnStr);
    }

    /**
     * Méthode d'ajout du panel du jeu bomberman à la fenêtre
     *
     * @param bombermanPanel Le panel du jeu bomberman
     */
    public void addPanelBomberman(PanelBomberman bombermanPanel) {
        // Si un panel bomberman est déjà set, on le supprime
        if (mainPanel.getComponentCount() == 2) mainPanel.remove(1);

        // Ajout du panel bomberman
        this.bombermanPanel = bombermanPanel;

        Integer sizeX = controller.getMap().getSizeX() * 50;
        Integer sizeY = controller.getMap().getSizeY() * 50;
        this.bombermanPanel.setSize(new Dimension(sizeX, sizeY));
        mainPanel.add(this.bombermanPanel, BorderLayout.CENTER);
        window.setSize(sizeX, sizeY + panelCommand.getHeight() + 40);
        window.repaint();
        initKeyListener();
        this.bombermanPanel.grabFocus();
    }

    public String getLayout() {
        return (String) panelCommand.getLayoutChooser().getSelectedItem();
    }

    public void initKeyListener() {
        bombermanPanel.addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();
                if (key != KeyEvent.VK_SPACE) {
                    controller.updatePlayerAction(AgentAction.STOP);
                }
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_LEFT: {
                        controller.updatePlayerAction(AgentAction.MOVE_LEFT);
                    }
                    break;
                    case KeyEvent.VK_RIGHT: {
                        controller.updatePlayerAction(AgentAction.MOVE_RIGHT);
                    }
                    break;
                    case KeyEvent.VK_UP: {
                        controller.updatePlayerAction(AgentAction.MOVE_UP);
                    }
                    break;
                    case KeyEvent.VK_DOWN: {
                        controller.updatePlayerAction(AgentAction.MOVE_DOWN);

                    }
                    break;
                    case KeyEvent.VK_SPACE: {
                        controller.updatePlayerAction(AgentAction.PUT_BOMB);
                    }
                    break;
                    default: {
                        controller.updatePlayerAction(AgentAction.STOP);
                    }
                }
            }

            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

        });
    }

    public BombermanController getController() {
        return controller;
    }

    public PanelBomberman getBombermanPanel() {
        return bombermanPanel;
    }

    // Méthodes appelées aux différents états de la fenètre
    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        controller.pause();
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {
        controller.pause();
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
}
