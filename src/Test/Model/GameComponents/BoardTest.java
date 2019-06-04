package Model.GameComponents;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BoardTest {

    final int WIDTH = 800;
    final int HEIGHT = 600;
    final int SIZE = 8;
    final double CELL_WIDTH = WIDTH / SIZE;
    final double CELL_HEIGHT = HEIGHT / SIZE;
    private Board mock;

    @Test
    void testIsVictory() {
        mock = new Board(WIDTH, HEIGHT);
        Assertions.assertFalse(mock.isVictory());
        for (int i = 1; i < SIZE - 1; i++)
            for (int j = 0; j < SIZE; j++)
                mock.setHorizontalDomino(i, j);
        Assertions.assertFalse(mock.isVictory());
        mock.setNumOfEmptyCells(1);
        Assertions.assertTrue(mock.isVictory());
        mock.setNumOfEmptyCells(2);
        Assertions.assertFalse(mock.isVictory());
        mock.setNumOfEmptyCells(22);
        Assertions.assertFalse(mock.isVictory());
        mock.setNumOfEmptyCells(0);
        Assertions.assertFalse(mock.isVictory());
    }

    @Test
    void testIsGoal() {
        mock = new Board(WIDTH, HEIGHT);
        for (int i = 1; i < SIZE - 1; i++)
            for (int j = 0; j < SIZE; j++)
                mock.setHorizontalDomino(i, j);
        Assertions.assertTrue(mock.isGoal());
        mock = new Board(WIDTH, HEIGHT);
        for (int i = 0; i < SIZE; i++)
            for (int j = 1; j < SIZE - 1; j++)
                mock.setVerticalDomino(i, j);
        Assertions.assertTrue(mock.isGoal());
        mock = new Board(WIDTH, HEIGHT);
        for (int i = 0; i < SIZE / 2; i++)
            for (int j = 1; j < SIZE - 1; j++)
                mock.setVerticalDomino(i, j);
        Assertions.assertFalse(mock.isGoal());
    }

    @Test
    void testAreHorizontalNeighborsEmpty() {
        mock = new Board(WIDTH, HEIGHT);
        for (int i = 1; i < SIZE - 1; i++)
            for (int j = 1; j < SIZE - 1; j++)
                Assertions.assertTrue(mock.areHorizontalNeighborsEmpty(i, j));
        mock = new Board(WIDTH, HEIGHT);
        mock.setHorizontalDomino(1, 1);
        Assertions.assertFalse(mock.areHorizontalNeighborsEmpty(0, 0));
        Assertions.assertFalse(mock.areHorizontalNeighborsEmpty(1, 1));
        Assertions.assertTrue(mock.areHorizontalNeighborsEmpty(1, 2));
        Assertions.assertTrue(mock.areHorizontalNeighborsEmpty(4, 1));
    }

    @Test
    void testAreVerticalNeighborsEmpty() {
        mock = new Board(WIDTH, HEIGHT);
        for (int i = 1; i < SIZE - 1; i++)
            for (int j = 1; j < SIZE - 1; j++)
                Assertions.assertTrue(mock.areVerticalNeighborsEmpty(i, j));
        mock = new Board(WIDTH, HEIGHT);
        mock.setVerticalDomino(1, 1);
        Assertions.assertFalse(mock.areVerticalNeighborsEmpty(0, 0));
        Assertions.assertFalse(mock.areVerticalNeighborsEmpty(1, 2));
        Assertions.assertFalse(mock.areVerticalNeighborsEmpty(1, 1));
        Assertions.assertTrue(mock.areVerticalNeighborsEmpty(2, 1));
        Assertions.assertFalse(mock.areVerticalNeighborsEmpty(1, 3));
    }

    @Test
    void testFindCellBoardPosition() {
        mock = new Board(WIDTH, HEIGHT);
        Cell[][] cells = mock.getCells();
        Assertions.assertEquals(1, mock.findCellBoardPosition(cells[1][1]).getValue());
        Assertions.assertEquals(2, mock.findCellBoardPosition(cells[2][1]).getKey());
        Assertions.assertEquals(5, mock.findCellBoardPosition(cells[5][6]).getKey());
    }

    @Test
    void testGenerateDots() {
        mock = new Board(WIDTH, HEIGHT);
        Cell[][] cells = mock.getCells();
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) {
                mock.generateDots(cells[i][j]);
                Assertions.assertEquals(cells[i][j].getNumOfDots(), cells[i][j].getDots().size());
            }
    }

    @Test
    void testFindCurrentCell() {
        mock = new Board(WIDTH, HEIGHT);
        Cell[][] cells = mock.getCells();
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) {
                Assertions.assertEquals(cells[i][j],mock.findCurrentCell(j * mock.getCellWidth() + 1,
                        i * mock.getCellHeight() + 1));
            }
    }
}