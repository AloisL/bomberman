package model;

import view.ViewCommand;
import view.ViewSimpleGame;

public class Test {

    public static void main(String... args) {
        SimpleGame simpleGame = new SimpleGame(10, Long.valueOf(1000));
        ViewSimpleGame viewSimpleGame = new ViewSimpleGame(simpleGame);
        viewSimpleGame.run();
        ViewCommand viewCommand = new ViewCommand(simpleGame);
        viewCommand.run();
        simpleGame.init();
        simpleGame.run();
        simpleGame.stop();
    }
}
