import client.ClientView;
import controller.ClientController;

public class Main {
    public static void main(String[] args) {
        ClientView clientView = new ClientView(new ClientController(), "Bomberman");
    }
}
