package jdungeonquest;

public class App {

    public static void main(String[] args) {
        Game game = new Game();
        GUI gui = new GUI(game);
        gui.showMainMenu();
    }
}
