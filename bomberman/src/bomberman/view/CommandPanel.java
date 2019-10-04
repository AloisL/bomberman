package bomberman.view;

import bomberman.controller.BombermanController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;

public class CommandPanel extends JPanel {

    private JPanel topCommandPanel;
    private JPanel botCommandPanel;

    private JButton initButton;
    private JButton runButton;
    private JButton stepButton;
    private JButton pauseButton;
    private JLabel currentTurnLabel;
    private JComboBox layoutChooser;
    private JSlider turnSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);


    public CommandPanel(BombermanController controller) {
        setPanels();
        initInputs();
        setListeners(controller);
        initOutputs();
    }

    private void setPanels() {
        setLayout(new GridLayout(2, 1));
        topCommandPanel = new JPanel(new GridLayout(1, 4));
        botCommandPanel = new JPanel(new GridLayout(1, 3));
        this.add(topCommandPanel);
        this.add(botCommandPanel);
    }

    private void initInputs() {
        initButton = new JButton(new ImageIcon("res/icones/icon_restart.png"));
        runButton = new JButton(new ImageIcon("res/icones/icon_run.png"));
        stepButton = new JButton(new ImageIcon("res/icones/icon_step.png"));
        pauseButton = new JButton(new ImageIcon("res/icones/icon_pause.png"));

        turnSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
        turnSlider.setMajorTickSpacing(1);
        turnSlider.setPaintTicks(true);
        turnSlider.setPaintLabels(true);

        runButton.setEnabled(Boolean.FALSE);
        stepButton.setEnabled(Boolean.FALSE);
        pauseButton.setEnabled(Boolean.FALSE);

        layoutChooser = new JComboBox<>();
        String[] layouts = new File("res/layouts").list();
        Arrays.sort(layouts);
        for (String layout : layouts) layoutChooser.addItem(layout);

        topCommandPanel.add(initButton);
        topCommandPanel.add(runButton);
        topCommandPanel.add(stepButton);
        topCommandPanel.add(pauseButton);
        botCommandPanel.add(turnSlider);
        botCommandPanel.add(layoutChooser);
    }

    public void setListeners(BombermanController controller) {
        initButton.addActionListener(event -> {
            controller.init();
            controller.changeLayout();
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
        botCommandPanel.add(currentTurnLabel);
    }

    public JLabel getCurrentTurnLabel() {
        return currentTurnLabel;
    }

    public JComboBox getLayoutChooser() {
        return layoutChooser;
    }


}
