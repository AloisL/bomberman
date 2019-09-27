package view;

import model.Game;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ViewSimpleGame implements Observer {

    private JFrame jFrame;
    private JLabel turnDisplay;

    private Integer turn;

    public ViewSimpleGame(Observable game) {
        game.addObserver(this);

        jFrame = new JFrame();
        jFrame.setTitle("Game");
        jFrame.setSize(new Dimension(700, 700));

        Dimension windowSize = jFrame.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2;
        int dy = centerPoint.y - (windowSize.height / 2) - 350;

        jFrame.setLocation(dx, dy);

        turnDisplay = new JLabel("");
        turnDisplay.setVisible(true);

        jFrame.add(turnDisplay);

        jFrame.setVisible(true);
    }

    @Override
    public void update(Observable observable, Object o) {
        Game game = (Game) observable;
        turn = game.getTurn();
        display();
    }

    private void display() {
        turnDisplay.setText(turn.toString());
    }

}
