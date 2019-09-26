package seance1;

public class Test {
    public static void main(String[] args) {
        SimpleGame simpleGame = new SimpleGame(10, Long.valueOf(1000));
        simpleGame.init();
        simpleGame.run();
    }
}
