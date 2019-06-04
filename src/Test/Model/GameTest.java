package Model;

import Model.GameComponents.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    final Game game = new Game(new Board(600,800));
    @Test
    void testGetBoard() {
        assertThrows(InstantiationError.class, () -> {
            new Game(null);
        });
    }

}