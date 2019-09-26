package view;

import model.Game;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ViewCommand extends SwingWorker<String, Object> implements Observer {

    private JFrame jFrame;
    private JLabel maxturn;

    private Integer turn;

    public ViewCommand(Observable game) {
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

        maxturn = new JLabel("");
        maxturn.setVisible(true);

        jFrame.add(maxturn);

        jFrame.setVisible(true);
    }

    @Override
    public void update(Observable observable, Object o) {
        Game game = (Game) observable;
        turn = game.getMaxturn();
        display();
    }

    private void display() {
        maxturn.setText(turn.toString());
    }

    @Override
    protected String doInBackground() {
        return null;
    }
}
