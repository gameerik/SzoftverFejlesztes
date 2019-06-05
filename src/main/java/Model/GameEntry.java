package Model;

import Model.GameComponents.Board;

/**
 * This class provides the entry point of the application.
 */
public class GameEntry {

    /**
     * The entry point of the application.
     *
     * @param args command line parameters, not used by the application.
     */
    public static void main(String... args) {
        Game game = new Game(new Board());
        game.play();
    }
}
