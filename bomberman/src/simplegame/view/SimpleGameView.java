package simplegame.view;

import common.model.Game;
import simplegame.controller.SimpleGameController;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class SimpleGameView implements Observer {
    private SimpleGameController controller;

    private JFrame jFrame;
    private JLabel turnDisplay;

    private Integer turn;

    public SimpleGameView(SimpleGameController controller, String title) {
        this.controller = controller;
        jFrame = new JFrame();
        jFrame.setTitle(title);
        jFrame.setSize(new Dimension(700, 700));

        Dimension windowSize = jFrame.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2;
        int dy = centerPoint.y - (windowSize.height / 2) - 350;

        jFrame.setLocation(dx, dy);

        turnDisplay = new JLabel("Waiting");
        turnDisplay.setVisible(true);

        jFrame.add(turnDisplay);

        jFrame.setVisible(true);
    }

    @Override
    public void update(Observable observable, Object o) {
        Game game = (Game) observable;
        turn = game.getCurrentTurn();
        display();
    }

    private void display() {
        turnDisplay.setText(turn.toString());
    }

}
