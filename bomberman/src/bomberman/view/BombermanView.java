package bomberman.view;

import bomberman.controller.BombermanController;
import common.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Observable;
import java.util.Observer;

public class BombermanView implements Observer {

    private BombermanController controller;
    private Integer currentTurn;

    private JFrame window;

    private JPanel mainPanel;
    private CommandPanel commandPanel;
    private PanelBomberman bombermanPanel;

    public BombermanView(BombermanController controller, String title) {
        this.controller = controller;
        initFrame(title);
        setPanels();
        window.setVisible(true);
    }

    private void initFrame(String title) {
        window = new JFrame();

        // Permet de fermer l'application après avoir quitter la vue.
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        window.setTitle(title);
        window.setSize(new Dimension(500, 200));
        Dimension windowSize = window.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2;
        int dy = centerPoint.y - (windowSize.height / 2) - 350;
        window.setLocation(dx, dy);

        window.addComponentListener(new ComponentAdapter() {
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

    private void setPanels() {
        mainPanel = new JPanel(new BorderLayout());
        commandPanel = new CommandPanel(controller);
        mainPanel.add(commandPanel, BorderLayout.NORTH);
        window.add(mainPanel);
    }

    @Override
    public void update(Observable observable, Object o) {
        Game game = (Game) observable;
        currentTurn = game.getCurrentTurn();
        displayUpdate();
    }

    private void displayUpdate() {
        String currentTurnStr = "Turn: " + currentTurn.toString();
        commandPanel.getCurrentTurnLabel().setText(currentTurnStr);
    }

    public String getLayout() {
        return (String) commandPanel.getLayoutChooser().getSelectedItem();
    }

    public void addPanelBomberman(PanelBomberman bombermanPanel) {
        if (mainPanel.getComponentCount() == 2) mainPanel.remove(1);
        this.bombermanPanel = bombermanPanel;
        Integer sizeX = controller.getMap().getSizeX() * 50;
        Integer sizeY = controller.getMap().getSizeY() * 50;
        this.bombermanPanel.setSize(new Dimension(sizeX, sizeY));
        mainPanel.add(this.bombermanPanel, BorderLayout.CENTER);
        window.setSize(sizeX, sizeY + commandPanel.getHeight() + 40);
        window.repaint();
    }

}
