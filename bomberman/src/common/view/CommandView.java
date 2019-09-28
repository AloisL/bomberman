package common.view;

import common.controller.Controller;
import common.model.Game;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class CommandView implements Observer {
    private Controller controller;

    private Integer currentTurn;

    private JFrame jFrame;

    private JPanel topPanel;
    private JPanel botPanel;

    private JLabel currentTurnLabel;

    public CommandView(Controller controller, String title) {
        this.controller = controller;

        initFrame(title);
        setPanels();
        initInputs();
        initOutputs();

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

        initButton.addActionListener(event -> {
            controller.init();
            runButton.setEnabled(Boolean.TRUE);
            stepButton.setEnabled(Boolean.TRUE);
            pauseButton.setEnabled(Boolean.TRUE);
        });

        runButton.addActionListener(event -> controller.run());

        stepButton.addActionListener(event -> controller.step());

        pauseButton.addActionListener(event -> controller.pause());

        turnSlider.addChangeListener(event -> {
            JSlider turnSlider1 = (JSlider) event.getSource();
            controller.setTime(turnSlider1.getValue());
        });

    }

    private void initOutputs() {
        currentTurnLabel = new JLabel("Waiting");
        currentTurnLabel.setHorizontalAlignment(JLabel.CENTER);
        botPanel.add(currentTurnLabel);
    }

    @Override
    public void update(Observable observable, Object o) {
        Game game = (Game) observable;
        currentTurn = game.getCurrentTurn();
        displayUpdate();
    }

    private void displayUpdate() {
        String currentTurnStr = "Turn: " + currentTurn.toString();
        currentTurnLabel.setText(currentTurnStr);
    }

}
