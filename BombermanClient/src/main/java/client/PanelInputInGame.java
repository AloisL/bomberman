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
        returnButton = new JButton("Return");
        add(returnButton);
    }

    private void initListeners() {
        returnButton.addActionListener(actionEvent -> {
            clientController.stop();
        });
    }

}
