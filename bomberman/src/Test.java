import bomberman.controller.BombermanController;
import bomberman.model.Bomberman;

public class Test {

    public static void main(String... args) {
        Bomberman bomberman = new Bomberman(10);
        new BombermanController(bomberman);
    }
}
