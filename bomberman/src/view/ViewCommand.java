package view;

import controller.SimpleGameController;
import model.Game;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class ViewCommand implements Observer {
    SimpleGameController controller;

    private JFrame jFrame;

    private JPanel topPanel;
    private JPanel botPanel;

    private JLabel maxTurnLabel;
    private Integer maxTurn;

    public ViewCommand(Observable game, SimpleGameController controller) {
        game.addObserver(this);
        this.controller = controller;

        initFrame();
        setPanels();
        initInputs();
        initOutputs();

        jFrame.setVisible(true);
    }

    private void initFrame() {
        jFrame = new JFrame();
        jFrame.setTitle("Game");
        jFrame.setSize(new Dimension(500, 200));
        Dimension windowSize = jFrame.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2;
        int dy = centerPoint.y - (windowSize.height / 2) - 350;
        jFrame.setLocation(dx, dy);
    }

    private void setPanels() {
        JPanel mainPanel = new JPanel(new GridLayout(2, 1));
        topPanel = new JPanel(new GridLayout(1, 2));
        botPanel = new JPanel(new GridLayout(1, 4));
        mainPanel.add(topPanel);
        mainPanel.add(botPanel);
        jFrame.add(mainPanel);
    }

    private void initInputs() {
        JButton initButton = new JButton(new ImageIcon("ressources/icones/icon_restart.png"));
        JButton runButton = new JButton(new ImageIcon("ressources/icones/icon_run.png"));
        JButton stepButton = new JButton(new ImageIcon("ressources/icones/icon_step.png"));
        JButton pauseButton = new JButton(new ImageIcon("ressources/icones/icon_pause.png"));

        JSlider turnSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
        turnSlider.setMajorTickSpacing(1);
        turnSlider.setPaintTicks(true);
        turnSlider.setPaintLabels(true);

        runButton.setEnabled(Boolean.FALSE);
        stepButton.setEnabled(Boolean.FALSE);
        pauseButton.setEnabled(Boolean.FALSE);

        topPanel.add(initButton);
        topPanel.add(runButton);
        topPanel.add(stepButton);
        topPanel.add(pauseButton);
        botPanel.add(turnSlider);

        initButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evenement) {
                controller.init();
                runButton.setEnabled(Boolean.TRUE);
                stepButton.setEnabled(Boolean.TRUE);
                pauseButton.setEnabled(Boolean.TRUE);
            }
        });

        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evenement) {
                controller.run();
            }
        });

        stepButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evenement) {
                controller.step();
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evenement) {
                controller.pause();
            }
        });

        turnSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent event) {
                JSlider turnSlider = (JSlider) event.getSource();
                controller.setTime(turnSlider.getValue());
            }
        });

    }

    private void initOutputs() {
        maxTurnLabel = new JLabel();
        maxTurnLabel.setHorizontalAlignment(JLabel.CENTER);
        botPanel.add(maxTurnLabel);
    }

    @Override
    public void update(Observable observable, Object o) {
        Game game = (Game) observable;
        maxTurn = game.getTurn();
        display();
    }

    private void display() {
        String turnStr = "Turn: " + maxTurn.toString();
        maxTurnLabel.setText(turnStr);
    }

}
