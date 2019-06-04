package Model.GameComponents;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CellTest {

    final int WIDTH = 800;
    final int HEIGHT = 600;
    final int SIZE = 8;
    final double CELL_WIDTH = WIDTH / SIZE;
    final double CELL_HEIGHT = HEIGHT / SIZE;
    private Board mock;

    @Test
    void testGetColor() {
        mock = new Board(WIDTH, HEIGHT);
        Cell[][] cells = mock.getCells();
        final Color color = Color.BROWN;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                Assertions.assertTrue(cells[i][j].getColor() != color);
        mock.setHorizontalDomino(1, 0);
        Assertions.assertTrue(cells[0][0].getColor() == color);
    }
}