package client;

import controller.ClientController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;

/**
 * Classe gérant le JPanel de commande
 */
public class PanelCommand extends JPanel {

    ClientController clientController;
    ClientView clientView;
    JPanel topCommandPanel;
    JPanel botCommandPanel;
    JButton initButton;
    JButton startButton;
    JButton stepButton;
    JButton pauseButton;
    JLabel infoLabel;
    JComboBox layoutChooser;
    JSlider turnSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);

    /**
     * Constructeur du JPanel de commande
     *
     * @param clientView La vue du jeu
     */
    public PanelCommand(ClientController clientController, ClientView clientView) {
        this.clientController = clientController;
        this.clientView = clientView;
        init();
    }

    /**
     * Méthode d'initialisation du JPanel
     *
     */
    private void init() {
        setPanels();
        initInputs();
        setListeners();
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
        startButton = new JButton(new ImageIcon("res/icones/icon_run.png"));
        stepButton = new JButton(new ImageIcon("res/icones/icon_step.png"));
        pauseButton = new JButton(new ImageIcon("res/icones/icon_pause.png"));

        turnSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
        turnSlider.setMajorTickSpacing(1);
        turnSlider.setPaintTicks(true);
        turnSlider.setPaintLabels(true);

        startButton.setEnabled(Boolean.FALSE);
        stepButton.setEnabled(Boolean.FALSE);
        pauseButton.setEnabled(Boolean.FALSE);

        layoutChooser = new JComboBox<>();
        String[] layouts = new File("res/layouts").list();
        Arrays.sort(layouts);
        for (String layout : layouts) layoutChooser.addItem(layout);

        topCommandPanel.add(initButton);
        topCommandPanel.add(startButton);
        topCommandPanel.add(stepButton);
        topCommandPanel.add(pauseButton);
        botCommandPanel.add(turnSlider);
        botCommandPanel.add(layoutChooser);
    }

    /**
     * Méthode d'initialisation des listeners
     *
     */
    public void setListeners() {
        initButton.addActionListener(event -> {
            clientController.changeLayout();
            clientController.init();
            startButton.setEnabled(true);
            stepButton.setEnabled(true);
            clientView.panelBomberman.grabFocus();
        });

        startButton.addActionListener(event -> {
            clientController.start();
            pauseButton.setEnabled(true);
            initButton.setEnabled(false);
            startButton.setEnabled(false);
            clientView.panelBomberman.grabFocus();
        });

        stepButton.addActionListener(event -> clientController.step());

        pauseButton.addActionListener(event -> {
            clientController.pause();
            initButton.setEnabled(true);
            pauseButton.setEnabled(false);
            startButton.setEnabled(true);
            clientView.panelBomberman.grabFocus();
        });

        turnSlider.addChangeListener(event -> {
            JSlider turnSlider1 = (JSlider) event.getSource();
            clientController.setTime(turnSlider1.getValue());
            clientView.panelBomberman.grabFocus();
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
        clientController.pause();
        initButton.setEnabled(Boolean.TRUE);
        pauseButton.setEnabled(Boolean.FALSE);
        startButton.setEnabled(Boolean.FALSE);
        stepButton.setEnabled(Boolean.FALSE);
        clientView.getPanelBomberman().grabFocus();
    }

    public void gameWon() {
        clientController.pause();
        initButton.setEnabled(Boolean.TRUE);
        pauseButton.setEnabled(Boolean.FALSE);
        startButton.setEnabled(Boolean.FALSE);
        stepButton.setEnabled(Boolean.FALSE);
        clientView.getPanelBomberman().grabFocus();
    }

}
