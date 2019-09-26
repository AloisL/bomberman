package view;

import model.Game;

import java.util.Observable;
import java.util.Observer;

public class ViewCommand implements Observer {

    private Integer turn;

    public ViewCommand(Observable game)   {
        game.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        Game game = (Game) observable;
        turn = game.getTurn();
    }
}
