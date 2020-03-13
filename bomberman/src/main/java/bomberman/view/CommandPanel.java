package bomberman.view;

import bomberman.controller.BombermanController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;

/**
 * Classe gérant le JPanel de commande
 */
public class CommandPanel extends JPanel {

    BombermanController bombermanController;
    BombermanView bombermanView;
    JPanel topCommandPanel;
    JPanel botCommandPanel;
    JButton initButton;
    JButton runButton;
    JButton stepButton;
    JButton pauseButton;
    JLabel infoLabel;
    JComboBox layoutChooser;
    JSlider turnSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);

    /**
     * Constructeur du JPanel de commande
     *
     * @param bombermanView La vue du jeu
     */
    public CommandPanel(BombermanView bombermanView) {
        this.bombermanView = bombermanView;
        init(bombermanView.getBombermanController());
    }

    /**
     * Méthode d'initialisation du JPanel
     *
     * @param bombermanController Le controleur du jeu
     */
    private void init(BombermanController bombermanController) {
        this.bombermanController = bombermanController;
        setPanels();
        initInputs();
        setListeners(bombermanController);
        initOutputs();
    }

    /**
     * Méthode d'initialisation des Panels
     */
    private void setPanels() {
        setLayout(new BorderLayout());
        topCommandPanel = new JPanel(new GridBagLayout());
        botCommandPanel = new JPanel(new GridBagLayout());
        add(topCommandPanel, BorderLayout.NORTH);
        add(botCommandPanel, BorderLayout.SOUTH);
    }

    /**
     * Méthode d'initialisation des entrées utilisateur
     */
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

    /**
     * Méthode d'initialisation des listeners
     *
     * @param controller Le controleur du jeu
     */
    public void setListeners(BombermanController controller) {
        initButton.addActionListener(event -> {
            controller.changeLayout();
            controller.init();
            runButton.setEnabled(true);
            stepButton.setEnabled(true);
            bombermanView.bombermanPanel.grabFocus();
        });

        runButton.addActionListener(event -> {
            controller.run();
            pauseButton.setEnabled(true);
            initButton.setEnabled(false);
            runButton.setEnabled(false);
            bombermanView.bombermanPanel.grabFocus();
        });

        stepButton.addActionListener(event -> controller.step());

        pauseButton.addActionListener(event -> {
            controller.pause();
            initButton.setEnabled(true);
            pauseButton.setEnabled(false);
            runButton.setEnabled(true);
            bombermanView.bombermanPanel.grabFocus();
        });

        turnSlider.addChangeListener(event -> {
            JSlider turnSlider1 = (JSlider) event.getSource();
            controller.setTime(turnSlider1.getValue());
            bombermanView.bombermanPanel.grabFocus();
        });
    }

    /**
     * Méthode d'initialisation des sorties
     */
    private void initOutputs() {
        infoLabel = new JLabel("Waiting");
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        botCommandPanel.add(infoLabel);
    }

    public void gameOver() {
        bombermanController.pause();
        initButton.setEnabled(Boolean.TRUE);
        pauseButton.setEnabled(Boolean.FALSE);
        runButton.setEnabled(Boolean.FALSE);
        stepButton.setEnabled(Boolean.FALSE);
        bombermanView.getBombermanPanel().grabFocus();
    }

    public void gameWon() {
        bombermanController.pause();
        initButton.setEnabled(Boolean.TRUE);
        pauseButton.setEnabled(Boolean.FALSE);
        runButton.setEnabled(Boolean.FALSE);
        stepButton.setEnabled(Boolean.FALSE);
        bombermanView.getBombermanPanel().grabFocus();
    }

}
