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

        loginMode();
    }

    public void loginMode() {
        if (getComponentCount() == 0)
            add(panelLogin);
        else {
            remove(1);
            add(panelLogin);
        }
    }

    public void controlMode() {
        if (getComponentCount() == 0)
            add(panelControl);
        else {
            remove(1);
            add(panelControl);
        }
    }
}
