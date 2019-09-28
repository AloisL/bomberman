package bomberman.view;

import bomberman.controller.BombermanController;
import bomberman.model.engine.PanelBomberman;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class BombermanView implements Observer {
    BombermanController controller;

    JFrame jFrame;

    public BombermanView(BombermanController controller) {
        this.controller = controller;

        initFrame("Bomberman");
        setPanels();
        jFrame.setVisible(true);
    }

    private void initFrame(String title) {
        jFrame = new JFrame();
        jFrame.setTitle(title);
        jFrame.setSize(new Dimension(500, 200));
        Dimension windowSize = jFrame.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2;
        int dy = centerPoint.y - (windowSize.height / 2) - 350;
        jFrame.setLocation(dx, dy);
    }

    private void setPanels() {
        PanelBomberman panelBomberman = new PanelBomberman(controller.getMap());
        jFrame.add(panelBomberman);
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
