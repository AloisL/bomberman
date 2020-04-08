package client;

import controller.ClientController;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

/**
 * Classe gérant le JPanel de commande
 */
public class PanelInput extends JPanel {

    ClientController clientController;
    ClientView clientView;
    PanelInputPreGame panelInputPreGame;
    PanelInputLogin panelInputLogin;
    PanelInputInGame panelInputInGame;

    /**
     * Constructeur du JPanel de commande
     *
     * @param clientView La vue du jeu
     */
    public PanelInput(ClientController clientController, ClientView clientView) {
        this.clientController = clientController;
        this.clientView = clientView;
        init();
    }

    /**
     * Méthode d'initialisation du JPanel
     */
    private void init() {
        setLayout(new GridBagLayout());
        panelInputLogin = new PanelInputLogin(clientController, clientView);
        panelInputPreGame = new PanelInputPreGame(clientController, clientView);
        panelInputInGame = new PanelInputInGame(clientController, clientView);
    }

    public void loginMode() {
        if (getComponentCount() > 1)
            remove(1);
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        gc.weighty = 2;
        gc.insets = new Insets(5, 5, 5, 5);
        gc.gridy = 0;
        gc.gridx = 0;
        add(clientView.infoLabel, gc);
        gc.gridy = 1;
        add(panelInputLogin, gc);
        clientView.setVisible(true);
        clientView.repaint();
    }

    public void preGameMode() {
        clientView.setSize(new Dimension(500, 220));
        panelInputPreGame.ready = false;
        panelInputPreGame.layoutChooser.setVisible(true);
        panelInputPreGame.searchButton.setText("SEARCH");
        panelInputPreGame.searchButton.setBackground(new ColorUIResource(238, 238, 238));
        panelInputPreGame.searchButton.setEnabled(true);
        panelInputPreGame.readyButton.setEnabled(false);
        panelInputPreGame.searching = false;
        panelInputPreGame.readyButton.setBackground(new ColorUIResource(238, 238, 238));


        if (getComponentCount() > 1)
            remove(1);
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        gc.weighty = 2;
        gc.insets = new Insets(5, 5, 5, 5);
        gc.gridy = 0;
        gc.gridx = 0;
        add(clientView.infoLabel, gc);
        gc.gridy = 1;
        add(panelInputPreGame, gc);
        clientView.setVisible(true);
        clientView.repaint();
    }

    public void inGameMode() {
        panelInputPreGame.searching = false;
        if (getComponentCount() > 1)
            remove(1);
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        gc.weighty = 2;
        gc.insets = new Insets(5, 5, 5, 5);
        gc.gridy = 0;
        gc.gridx = 0;
        add(clientView.infoLabel, gc);
        gc.gridy = 1;
        add(panelInputInGame, gc);
        clientView.setVisible(true);
        clientView.repaint();
    }

}

