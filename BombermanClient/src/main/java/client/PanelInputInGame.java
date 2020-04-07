package client;

import controller.ClientController;

import javax.swing.*;

public class PanelInputInGame extends JPanel {

    ClientController clientController;
    ClientView clientView;
    JButton returnButton;

    public PanelInputInGame(ClientController clientController, ClientView clientView) {
        this.clientController = clientController;
        this.clientView = clientView;
        initInputs();
        initListeners();
        setVisible(true);
    }

    private void initInputs() {
        returnButton = new JButton("QUIT");
        add(returnButton);
    }

    // TODO : JEE gérer la perte de la partie lorque l'on quitte une partie
    private void initListeners() {
        returnButton.addActionListener(actionEvent -> {
            clientController.stop("Game aborted, count as a loss.");
        });
    }

}
