package jdungeonquest;

import jdungeonquest.gui.GUI;

public class App {

    public static void main(String[] args) {
        Game game = new Game();
        GUI gui = new GUI(game);
        gui.showMainMenu();
    }
}
