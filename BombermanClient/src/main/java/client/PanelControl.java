package client;

import controller.ClientController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;

public class PanelControl extends JPanel {

    ClientController clientController;
    ClientView clientView;
    JButton initButton;
    JButton pauseButton;
    JLabel infoLabel;
    JComboBox layoutChooser;
    JSlider turnSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);

    boolean gamePaused = false;

    public PanelControl(ClientController clientController, ClientView clientView) {
        this.clientController = clientController;
        this.clientView = clientView;

        setLayout(new GridBagLayout());
        initInputs();
        initListeners();
        initOutputs();
        placeWidgets();
    }

    /**
     * Méthode d'initialisation des entrées utilisateur
     */
    private void initInputs() {
        initButton = new JButton(new ImageIcon("res/icones/icon_restart.png"));
        pauseButton = new JButton(new ImageIcon("res/icones/icon_pause.png"));

        turnSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
        turnSlider.setMajorTickSpacing(1);
        turnSlider.setPaintTicks(true);
        turnSlider.setPaintLabels(true);

        pauseButton.setEnabled(Boolean.FALSE);

        layoutChooser = new JComboBox<>();
        String[] layouts = new File("res/layouts").list();
        Arrays.sort(layouts);
        for (String layout : layouts) layoutChooser.addItem(layout);
    }


    /**
     * Méthode d'initialisation des listeners
     */
    public void initListeners() {
        initButton.addActionListener(event -> {
            clientController.run((String) layoutChooser.getSelectedItem());
            //clientView.panelBomberman.grabFocus();
        });

        pauseButton.addActionListener(event -> {
            if (gamePaused) {
                clientController.start();
                pauseButton.setIcon(new ImageIcon("res/icones/icon_pause.png"));
            } else {
                clientController.pause();
                pauseButton.setIcon(new ImageIcon("res/icones/icon_step.png"));
            }
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
        infoLabel = new JLabel();
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
    }

    private void placeWidgets() {
        JPanel infoPanel = new JPanel(new GridBagLayout());
        JPanel inputPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        int windowSizeY = clientView.window.getSize().height;
        int spacer = windowSizeY / 2 - 70;

        gc.insets = new Insets(spacer, 5, 10, 5);

        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 4;
        gc.weighty = 2;

        gc.insets = new Insets(5, 5, 5, 5);

        infoPanel.add(infoLabel, gc);

        gc.gridy = 0;
        gc.gridx = 1;
        gc.gridwidth = 2;
        inputPanel.add(layoutChooser, gc);

        gc.gridy = 1;
        gc.gridx = 1;
        gc.gridwidth = 1;
        inputPanel.add(initButton, gc);

        gc.gridx = 2;
        inputPanel.add(pauseButton, gc);

        GridBagConstraints gc2 = new GridBagConstraints();

        gc2.weightx = 1;
        gc2.weighty = 2;

        gc2.gridx = 0;
        gc2.gridy = 0;
        add(infoPanel, gc2);

        gc2.insets = new Insets(10, 0, 0, 0);

        gc2.gridx = 0;
        gc2.gridy = 1;
        add(inputPanel, gc2);
    }

    public void gameOver() {
        clientController.pause();
        initButton.setEnabled(true);
        pauseButton.setEnabled(false);
        clientView.getPanelBomberman().grabFocus();
    }

    public void gameWon() {
        clientController.pause();
        initButton.setEnabled(true);
        pauseButton.setEnabled(false);
        clientView.getPanelBomberman().grabFocus();
    }


}
