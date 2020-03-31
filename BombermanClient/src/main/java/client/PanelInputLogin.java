package client;

import controller.ClientController;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PanelInputLogin extends JPanel {

    ClientController clientController;
    ClientView clientView;

    JTextField serverField;
    JTextField usernameField;
    JPasswordField passwordField;
    JCheckBox showPasswordCheckBox;
    JButton loginButton;

    boolean serverClicked = false;
    boolean usernameClicked = false;
    boolean passwordClicked = false;

    public PanelInputLogin(ClientController clientController, ClientView clientView) {
        this.clientController = clientController;
        this.clientView = clientView;
        setLayout(new GridBagLayout());
        initField();
        clientView.setInfo("Choose a server and login:");
        initListeners();
        placeWigdgets();
    }

    private void initField() {
        serverField = new JTextField("127.0.0.1");
        serverField.setHorizontalAlignment(JTextField.CENTER);
        usernameField = new JTextField("Username");
        usernameField.setHorizontalAlignment(JTextField.CENTER);
        passwordField = new JPasswordField("Password");
        passwordField.setHorizontalAlignment(JTextField.CENTER);
        showPasswordCheckBox = new JCheckBox("Show");
        loginButton = new JButton("Login");
    }

    private void initListeners() {
        serverField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEven) {
                if (!usernameClicked) {
                    serverField.setText("");
                    serverClicked = true;
                }
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {

            }
        });

        usernameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEven) {
                if (!usernameClicked) {
                    usernameField.setText("");
                    usernameClicked = true;
                }
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {

            }
        });

        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                if (!passwordClicked) {
                    if (!showPasswordCheckBox.isSelected()) passwordField.setEchoChar('*');
                    passwordField.setText("");
                    passwordClicked = true;
                }
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {

            }
        });

        showPasswordCheckBox.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                if (showPasswordCheckBox.isSelected())
                    passwordField.setEchoChar((char) 0);
                else passwordField.setEchoChar('*');
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Si le token de connexion n'est pas vide, alors la connexion a été approuvée
                if (clientController.login(serverField.getText(), usernameField.getText(), passwordField.getText()) != null) {
                    PanelInput panelInput = (PanelInput) getParent();
                    panelInput.preGameMode();
                }
            }
        });
    }

    private void placeWigdgets() {
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(5, 165, 5, 5);

        int spacer = 30;
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 5;
        gc.weighty = 4;

        gc.gridwidth = 1;

        gc.gridy = 0;
        gc.gridx = 2;
        add(serverField, gc);

        gc.gridy = 1;
        gc.gridx = 2;
        add(usernameField, gc);

        gc.gridy = 2;
        gc.gridx = 2;
        add(passwordField, gc);

        gc.insets = new Insets(5, 5, 5, 5);
        gc.gridy = 2;
        gc.gridx = 3;
        add(showPasswordCheckBox, gc);

        gc.insets = new Insets(5, 165, 5, 5);
        gc.gridy = 3;
        gc.gridx = 2;
        add(loginButton, gc);
    }

}
