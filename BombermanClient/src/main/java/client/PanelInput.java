package client;

import controller.ClientController;

import javax.swing.*;
import java.awt.*;

/**
 * Classe gérant le JPanel de commande
 */
public class PanelInput extends JPanel {

    ClientController clientController;
    ClientView clientView;
    PanelControl panelControl;
    PanelLogin panelLogin;

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
        panelLogin = new PanelLogin(clientController, clientView);
        panelControl = new PanelControl(clientController, clientView);
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
        add(panelLogin, gc);
        clientView.setVisible(true);
        clientView.repaint();
    }

    public void controlMode() {
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
        add(panelControl, gc);
        clientView.setVisible(true);
        clientView.repaint();
    }

}

