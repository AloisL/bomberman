package client;

import controller.ClientController;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.io.File;
import java.util.Arrays;

public class PanelInputPreGame extends JPanel {

    ClientController clientController;
    ClientView clientView;
    JButton searchButton;
    JButton readyButton;
    JComboBox layoutChooser;
    boolean ready = false;
    boolean searching = false;

    public PanelInputPreGame(ClientController clientController, ClientView clientView) {
        this.clientController = clientController;
        this.clientView = clientView;
        setLayout(new GridBagLayout());
        initInputs();
        initListeners();
        placeWidgets();
    }

    /**
     * Méthode d'initialisation des entrées utilisateur
     */
    private void initInputs() {
        searchButton = new JButton("SEARCH");
        readyButton = new JButton("READY");
        layoutChooser = new JComboBox<>();
        String[] layouts = new File("src/main/java/common/layouts").list();
        Arrays.sort(layouts);
        for (String layout : layouts) layoutChooser.addItem(layout);
    }

    /**
     * Méthode d'initialisation des listeners
     */
    public void initListeners() {
        searchButton.addActionListener(event -> {
            if (!searching) {
                layoutChooser.setVisible(false);
                clientController.initConnection((String) layoutChooser.getSelectedItem());
                searchButton.setText("CANCEL");
                searchButton.setBackground(Color.red);
                readyButton.setEnabled(true);
                searching = true;
            } else {
                clientController.closeConnection();
                layoutChooser.setVisible(true);
                searchButton.setText("SEARCH");
                searchButton.setBackground(new ColorUIResource(238, 238, 238));
                readyButton.setBackground(new ColorUIResource(238, 238, 238));
                readyButton.setEnabled(false);
                clientController.setInfo("SEARCH for a server and press READY.");
                searching = false;
            }

        });

        readyButton.addActionListener(event -> {
            if (!ready) {
                clientController.ready(true);
                readyButton.setBackground(Color.green);
                ready = true;
            } else {
                clientController.ready(false);
                readyButton.setBackground(new ColorUIResource(238, 238, 238));
                ready = false;
            }

        });
    }

    private void placeWidgets() {
        JPanel infoPanel = new JPanel(new GridBagLayout());
        JPanel inputPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 4;
        gc.weighty = 2;

        gc.insets = new Insets(0, 5, 5, 5);

        gc.gridy = 0;
        gc.gridx = 1;
        gc.gridwidth = 2;
        inputPanel.add(layoutChooser, gc);

        gc.gridy = 1;
        gc.gridx = 1;
        gc.gridwidth = 1;
        inputPanel.add(searchButton, gc);

        gc.gridx = 2;
        inputPanel.add(readyButton, gc);

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

}
