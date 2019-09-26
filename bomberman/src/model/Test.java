package model;

import view.ViewSimpleGame;

import javax.swing.*;

public class Test {

    public static void main(String... args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SimpleGame simpleGame = new SimpleGame(10, Long.valueOf(1000));
                new ViewSimpleGame(simpleGame);
                simpleGame.init();
                simpleGame.run();
            }
        });
    }
}
