package bomberman.view;

import bomberman.controller.BombermanController;
import bomberman.model.engine.PanelBomberman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

public class BombermanView implements Observer {

    private BombermanController controller;
    private PanelBomberman panelBomberman;
    private JFrame window;

    public BombermanView(BombermanController controller) {
        this.controller = controller;
        initFrame("Bomberman");
        setPanels();
        //setVisible(true);
    }

    private void initFrame(String title) {
        window = new JFrame();

        // Permet de fermer l'application après avoir quitter la vue.
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        window.setTitle(title);
        Integer sizeX = controller.getMap().getSizeX() * 50;
        Integer sizeY = controller.getMap().getSizeY() * 50;
        window.setSize(new Dimension(sizeX, sizeY));
        Dimension windowSize = window.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2;
        int dy = centerPoint.y - (windowSize.height / 2) - 350;
        window.setLocation(dx, dy);
    }

    private void setPanels() {
        panelBomberman = new PanelBomberman(controller.getMap());
        window.add(panelBomberman);
    }

    @Override
    public void update(Observable observable, Object o) {

    }

    public void setVisible(boolean bool) {
        window.setVisible(bool);
    }

    public JPanel getPanelBomberman() {
        return panelBomberman;
    }

    public void closeWindow() {
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
    }

}
