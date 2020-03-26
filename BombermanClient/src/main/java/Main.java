import client.ClientView;
import controller.ClientController;

public class Main {
    public static void main(String[] args) {
        new ClientView(new ClientController(), "Bomberman");
    }
}
