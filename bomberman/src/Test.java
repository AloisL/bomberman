import controller.SimpleGameController;
import model.SimpleGame;
import view.ViewCommand;

public class Test {

    public static void main(String... args) {
        SimpleGame simpleGame = new SimpleGame(10, Long.valueOf(1000));
        //ViewSimpleGame viewSimpleGame = new ViewSimpleGame(simpleGame);
        ViewCommand viewCommand = new ViewCommand(simpleGame, new SimpleGameController(simpleGame));
    }
}
