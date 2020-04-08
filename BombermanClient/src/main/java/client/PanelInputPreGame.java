package client;

import controller.ClientController;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.util.ArrayList;

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
        ArrayList<String> layouts = new ArrayList<>();
        layouts.add("alone.lay");
        layouts.add("arene.lay");
        layouts.add("exemple.lay");
        layouts.add("jeu1.lay");
        layouts.add("jeu_symetrique.lay");
        layouts.add("niveau1.lay");
        layouts.add("niveau2.lay");
        layouts.add("niveau3.lay");
        for (String layout : layouts) layoutChooser.addItem(layout);
    }

    /**
     * Méthode d'initialisation des listeners
     */
    public void initListeners() {
        searchButton.addActionListener(event -> {
            if (!searching) {
                layoutChooser.setVisible(false);
                clientController.init((String) layoutChooser.getSelectedItem());
                searchButton.setText("CANCEL");
                searchButton.setBackground(Color.red);
                searching = true;
            } else {
                clientController.closeConnection();
                layoutChooser.setVisible(true);
                searchButton.setText("SEARCH");
                searchButton.setBackground(new ColorUIResource(238, 238, 238));
                readyButton.setBackground(new ColorUIResource(238, 238, 238));
                readyButton.setEnabled(false);
                clientController.setInfo("Search for a server");
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
