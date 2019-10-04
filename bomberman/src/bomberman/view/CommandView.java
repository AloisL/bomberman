package bomberman.view;

import bomberman.controller.BombermanController;
import common.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

public class CommandView implements Observer {

    private BombermanController controller;
    private Integer currentTurn;

    // Utiliser JPanel si l'on souhaite ajouter ces commandes à une fenetre de jeu (via getter du JPanel)
    private JFrame window;

    private JPanel mainPanel;
    private JPanel commandPanel;
    private JPanel topPanel;
    private JPanel botPanel;
    private JPanel panelBomberman;
    private JComboBox<String> layoutChooser;
    private JLabel currentTurnLabel;

    public CommandView(BombermanController controller, String title) {
        this.controller = controller;
        initFrame(title);
        setPanels();
        initLayoutChooser();
        initInputs();
        initOutputs();
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
                window.repaint();
            }
        });
    }

    private void setPanels() {
        mainPanel = new JPanel(new GridLayout(2, 1));
        commandPanel = new JPanel(new GridLayout(2, 1));
        topPanel = new JPanel(new GridLayout(1, 4));
        botPanel = new JPanel(new GridLayout(1, 3));
        mainPanel.add(commandPanel);
        commandPanel.add(topPanel);
        commandPanel.add(botPanel);
        window.add(mainPanel);
    }

    private void setPanels(JPanel panelBomberman) {
        if (mainPanel.getComponentCount() == 2) mainPanel.remove(1);

        this.panelBomberman = panelBomberman;

        Integer sizeX = controller.getMap().getSizeX() * 50;
        Integer sizeY = controller.getMap().getSizeY() * 50;
        panelBomberman.setSize(new Dimension(sizeX, sizeY));
        mainPanel.add(panelBomberman);

        window.repaint();
    }

    private void initInputs() {
        JButton initButton = new JButton(new ImageIcon("res/icones/icon_restart.png"));
        JButton runButton = new JButton(new ImageIcon("res/icones/icon_run.png"));
        JButton stepButton = new JButton(new ImageIcon("res/icones/icon_step.png"));
        JButton pauseButton = new JButton(new ImageIcon("res/icones/icon_pause.png"));

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
        botPanel.add(layoutChooser);

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

    private void initLayoutChooser() {
        layoutChooser = new JComboBox<>();
        String[] layouts = new File("res/layouts").list();
        Arrays.sort(layouts);
        for (String layout : layouts) layoutChooser.addItem(layout);
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

    public String getLayout() {
        return (String) layoutChooser.getSelectedItem();
    }

    public void addPanelBomberman(JPanel panelBomberman) {
        setPanels(panelBomberman);
    }

}
