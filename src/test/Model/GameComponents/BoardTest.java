package Model.GameComponents;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BoardTest {

    private Board mock;

    @Test
    public void testIsVictory() {
        mock = new Board();
        int size = mock.getBoardSize();
        Assertions.assertFalse(mock.isVictory());
        for (int i = 1; i <  - 1; i++)
            for (int j = 0; j < size; j++)
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
    public void testIsGoal() {
        mock = new Board();
        int size = mock.getBoardSize();
        for (int i = 1; i < size - 1; i++)
            for (int j = 0; j < size; j++)
                mock.setHorizontalDomino(i, j);
        Assertions.assertTrue(mock.isGoal());
        mock = new Board();
        for (int i = 0; i < size; i++)
            for (int j = 1; j < size - 1; j++)
                mock.setVerticalDomino(i, j);
        Assertions.assertTrue(mock.isGoal());
        mock = new Board();
        for (int i = 0; i < size / 2; i++)
            for (int j = 1; j < size - 1; j++)
                mock.setVerticalDomino(i, j);
        Assertions.assertFalse(mock.isGoal());
    }

    @Test
    public void testAreHorizontalNeighborsEmpty() {
        mock = new Board();
        int size = mock.getBoardSize();
        for (int i = 1; i < size - 1; i++)
            for (int j = 1; j < size - 1; j++)
                Assertions.assertTrue(mock.areHorizontalNeighborsEmpty(i, j));
        mock = new Board();
        mock.setHorizontalDomino(1, 1);
        Assertions.assertFalse(mock.areHorizontalNeighborsEmpty(0, 0));
        Assertions.assertFalse(mock.areHorizontalNeighborsEmpty(1, 1));
        Assertions.assertTrue(mock.areHorizontalNeighborsEmpty(1, 2));
        Assertions.assertTrue(mock.areHorizontalNeighborsEmpty(4, 1));
    }

    @Test
    public void testAreVerticalNeighborsEmpty() {
        mock = new Board();
        int size = mock.getBoardSize();
        for (int i = 1; i < size - 1; i++)
            for (int j = 1; j < size - 1; j++)
                Assertions.assertTrue(mock.areVerticalNeighborsEmpty(i, j));
        mock = new Board();
        mock.setVerticalDomino(1, 1);
        Assertions.assertFalse(mock.areVerticalNeighborsEmpty(0, 0));
        Assertions.assertFalse(mock.areVerticalNeighborsEmpty(1, 2));
        Assertions.assertFalse(mock.areVerticalNeighborsEmpty(1, 1));
        Assertions.assertTrue(mock.areVerticalNeighborsEmpty(2, 1));
        Assertions.assertFalse(mock.areVerticalNeighborsEmpty(1, 3));
    }

    @Test
    public void testFindCellBoardPosition() {
        mock = new Board();
        Cell[][] cells = mock.getCells();
        Assertions.assertEquals(1, mock.findCellBoardPosition(cells[1][1]).getValue());
        Assertions.assertEquals(2, mock.findCellBoardPosition(cells[2][1]).getKey());
        Assertions.assertEquals(5, mock.findCellBoardPosition(cells[5][6]).getKey());
    }

    @Test
    public void testIsPlaceable() {
        mock = new Board();
        int size = mock.getBoardSize();
        mock.setDirection(Direction.HORIZONTAL);
        mock.displayPossiblePlacements();
        for (int i = 1; i < size - 2; i++)
            for (int j = 2; j < size - 1; j+=3) {
                Assertions.assertTrue(mock.isPlaceable((j - 2) * size + i, j * size + i));
            }
        mock = new Board();
        mock.setDirection(Direction.VERTICAL);
        mock.displayPossiblePlacements();
        for (int i = 2; i < size - 2; i+= 3)
            for (int j = 1; j < size - 1; j++) {
                Assertions.assertTrue(mock.isPlaceable(j * size + (i -2), j * size + i));
            }
        Assertions.assertFalse(mock.isPlaceable(6,7));
        Assertions.assertFalse(mock.isPlaceable(7,8));
        Assertions.assertFalse(mock.isPlaceable(9,10));
        Assertions.assertFalse(mock.isPlaceable(11,62));
        Assertions.assertFalse(mock.isPlaceable(6,63));
        Assertions.assertFalse(mock.isPlaceable(6,55));
        Assertions.assertFalse(mock.isPlaceable(3,6));
        Assertions.assertFalse(mock.isPlaceable(21,12));

    }

}