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

public class PanelLogin extends JPanel {

    ClientController clientController;
    ClientView clientView;

    JTextField loginField;
    JPasswordField passwordField;
    JCheckBox showPasswordCheckBox;
    JButton loginButton;

    boolean loginClicked = false;
    boolean passwordClicked = false;

    public PanelLogin(ClientController clientController, ClientView clientView) {
        this.clientController = clientController;
        this.clientView = clientView;

        setLayout(new GridBagLayout());

        loginField = new JTextField("Username");
        passwordField = new JPasswordField("Password");
        showPasswordCheckBox = new JCheckBox("Show");
        loginButton = new JButton("Login");

        initListeners();
        placeWigdgets();
    }

    private void initListeners() {
        loginField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEven) {
                if (!loginClicked) {
                    loginField.setText("");
                    loginClicked = true;
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
                else if (passwordClicked) passwordField.setEchoChar('*');
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Si le token de connexion n'est pas vide, alors la connexion a été approuvée
                if (!clientController.login(loginField.getText(), passwordField.getText()).isEmpty()) {
                    clientView.panelInput.remove(0);
                    clientView.panelInput.add(clientView.panelInput.panelControl);
                    clientView.window.setVisible(true);
                    clientView.window.repaint();
                }
            }
        });
    }

    private void placeWigdgets() {
        loginField.setPreferredSize(new Dimension(100, 10));
        passwordField.setPreferredSize(new Dimension(100, 10));

        GridBagConstraints gc = new GridBagConstraints();

        int windowSizeY = clientView.window.getSize().height;
        int spacer = windowSizeY / 2 - 55;

        gc.fill = GridBagConstraints.BOTH;
        gc.insets = new Insets(spacer, 5, 10, 5);

        gc.weightx = 5;
        gc.weighty = 4;

        gc.gridy = 1;

        gc.gridx = 1;
        add(loginField, gc);

        gc.gridx = 2;
        add(passwordField, gc);

        gc.gridx = 3;
        add(showPasswordCheckBox, gc);

        gc.insets = new Insets(10, 5, spacer, 5);
        gc.gridy = 2;
        gc.gridx = 2;
        add(loginButton, gc);
    }

}
