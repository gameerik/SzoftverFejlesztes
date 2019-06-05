package Model.GameComponents;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CellTest {

    private Board mock;

    @Test
    public void testGetState() {
        mock = new Board();
        int size = mock.getBoardSize();
        Cell[][] cells = mock.getCells();
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                Assertions.assertEquals(State.EMPTY, cells[i][j].getState());
    }

}