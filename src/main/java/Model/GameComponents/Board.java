package Model.GameComponents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Class representing the board of the game.
 */
public class Board {

    /**
     * Constant field storing the maximum amount of dots, a {@link Cell} can have.
     */
    final int MAX_DOTS = 6;
    /**
     * Constant field storing the maximum amount of dots, a {@link Cell} can have.
     */
    final int MIN_DOTS = 1;
    /**
     * The width and height of the board.
     */
    private final int boardSize = 8;
    /**
     * The start time of the game.
     */
    private long startTime;
    /**
     * The end time of the game.
     */
    private long endTime;
    /**
     * The number representing the current amount of empty cells on the board.
     */
    private int numOfEmptyCells;
    /**
     * The width of the board, in pixels.
     */
    private double width;
    /**
     * The height of the board, in pixels.
     */
    private double height;

    /**
     * Collection of {@link Cell} which build up the board.
     */
    private Cell[][] cells;


    /**
     * Collection storing the {@link State EMPTY} cells of the current board, and their coordinates.
     */
    private Map<Integer, Map.Entry<Integer, Integer>> currentEmptyCells;

    /**
     * The current direction of the 3x1 {@link Cell} (domino).
     */
    private Direction direction;
    /**
     * The {@link org.slf4j.Logger logger} used in this class.
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Creates a 8x8 Board, filled with {@link State EMPTY} cells, containing [1,6] (inclusive) dots.
     */
    public Board() {
        logger.info("Creating Board");
        this.direction = Direction.HORIZONTAL;
        this.numOfEmptyCells = boardSize * boardSize;
        this.startTime = System.nanoTime();
        cells = new Cell[boardSize][boardSize];
        State initialState = State.EMPTY;
        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++) {
                int numOfDots = new Random().nextInt(6) + 1;
                cells[i][j] = new Cell(j, i, initialState, numOfDots);
            }
    }

    /**
     * Checks whether the game resulted in a victory.
     *
     * @return true, if all 21 domino pieces were placed on the board.
     */
    public final boolean isVictory() {
        return numOfEmptyCells == 1;
    }

    /**
     * Checks whether the game reached a state, where it cannot be continued.
     *
     * @return false, if there is still enough space for at least one domino placement.
     */
    public final boolean isGoal() {
        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++)
                if (cells[j][i].getState() == State.EMPTY
                        && (areHorizontalNeighborsEmpty(j, i) || areVerticalNeighborsEmpty(j, i)))
                    return false;
        endTime = System.nanoTime();
        logger.info("Current game state: Model is over");
        return true;
    }

    /**
     * Checks whether the left and right neighbors, of the given {@link Cell} are empty.
     * Similar to {@link #areHorizontalNeighborsEmpty(int, int) areVerticalNeighborsEmpty}.
     *
     * @param boardPositionX the horizontal position of the {@link Cell} on the board.
     * @param boardPositionY the vertical position of the {@link Cell} on the board.
     * @return true, if both left and right neighbors are empty.
     */
    public final boolean areHorizontalNeighborsEmpty(int boardPositionX, int boardPositionY) {
        logger.info("checking if cell has horizontal neighbors");
        if (boardPositionX == 0 || boardPositionX == boardSize - 1)
            return false;
        Cell rightNeighbour = cells[boardPositionX + 1][boardPositionY];
        Cell leftNeighbour = cells[boardPositionX - 1][boardPositionY];
        return rightNeighbour.getState() == State.EMPTY && leftNeighbour.getState() == State.EMPTY;
    }

    /**
     * Checks whether the under and the above neighbors, of the given {@link Cell} are empty.
     * Similar to {@link #areHorizontalNeighborsEmpty(int, int) areHorizontalNeighborsEmpty}.
     *
     * @param boardPositionX The horizontal position of the {@link Cell} on the board.
     * @param boardPositionY The vertical position of the {@link Cell} on the board.
     * @return true, if both the under and the above neighbors are empty.
     */
    public final boolean areVerticalNeighborsEmpty(int boardPositionX, int boardPositionY) {
        logger.info("checking if cell has vertical neighbors");
        if (boardPositionY == 0 || boardPositionY == boardSize - 1)
            return false;
        Cell bottomNeighbour = cells[boardPositionX][boardPositionY + 1];
        Cell topNeighbour = cells[boardPositionX][boardPositionY - 1];
        return bottomNeighbour.getState() == State.EMPTY && topNeighbour.getState() == State.EMPTY;
    }

    /**
     * Sets the current state of the {@link Cell}, along with the under and the above neighbors,
     * to {@link State #DOMINO}.
     *
     * @param positionX The horizontal position of the {@link Cell}.
     * @param positionY The vertical position of the {@link Cell}.
     */
    public void setVerticalDomino(int positionX, int positionY) {
        logger.info("Placing vertical domino");
        numOfEmptyCells -= 3;
        for (int j = positionY - 1; j <= positionY + 1; j++)
            cells[positionX][j].setState(State.DOMINO);
    }


    /**
     * Sets the current state of the {@link Cell}, along with the left and right neighbors,
     * to {@link State #DOMINO}.
     *
     * @param positionX The horizontal position of the {@link Cell}.
     * @param positionY The vertical position of the {@link Cell}.
     */
    public void setHorizontalDomino(int positionX, int positionY) {
        logger.info("Placing horizontal domino");
        numOfEmptyCells -= 3;
        for (int i = positionX - 1; i <= positionX + 1; i++)
            cells[i][positionY].setState(State.DOMINO);
    }

    /**
     * Searches for the given {@link Cell}'s position on the {@link Board}.
     *
     * @param cell The {@link Cell} whom position we seek.
     * @return The horizontal and vertical {@link Board} position of the {@link Cell} wrapped in a pair.
     */
    public Map.Entry<Integer, Integer> findCellBoardPosition(Cell cell) {
        logger.info("looking for cell");
        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++)
                if (cell == cells[j][i])
                    return new AbstractMap.SimpleEntry<>(j, i);
        return null;
    }

    /**
     * Draws the board on the console, highlighting each {@link Cell}'s current state.
     */
    public void display() {
        logger.info("displaying the board");
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++)
                cells[j][i].display();
            System.out.println();
        }

    }

    /**
     * Draws the board illustrating the possible choices of 3x1{@link Cell}(Domino) placements.
     * Also stores the {@link State EMPTY} cells in {@link #currentEmptyCells}.
     */
    public void displayPossiblePlacements() {
        logger.info("displaying the empty cells");
        this.currentEmptyCells = new HashMap<>();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Cell current = cells[j][i];
                int cellNumber = j * boardSize + i;
                current.display(Integer.toString(cellNumber));
                if (current.getState() == State.EMPTY)
                    currentEmptyCells.put(cellNumber, new AbstractMap.SimpleEntry<>(j, i));
            }
            System.out.println();
        }
    }

    /**
     * Checks whether a 3x1{@link Cell} can be placed in the current {@link Direction}, if it can be placed, it does
     * place it.
     *
     * @param start The starting cell of the domino.
     * @param end   The ending cell of the domino.
     * @return true, if the domino can be placed in the current {@link Direction} between the starting and ending cell.
     */
    public boolean isPlaceable(Integer start, Integer end) {
        logger.info("checking if the domino is placeable");
        boolean result = false;
        int x;
        int y;
        int sumStart = currentEmptyCells.get(start).getKey() + currentEmptyCells.get(start).getValue();
        int sumEnd = currentEmptyCells.get(end).getKey() + currentEmptyCells.get(end).getValue();
        if (!(Math.abs(sumStart - sumEnd) == 2))
            return false;
        if (direction == Direction.HORIZONTAL) {
            x = Math.min(currentEmptyCells.get(start).getKey(), currentEmptyCells.get(end).getKey()) + 1;
            y = currentEmptyCells.get(end).getValue();
            logger.info("x: "  + x + " " + "y: " + y);
            result =  y == currentEmptyCells.get(start).getValue() && areHorizontalNeighborsEmpty(x, y);
            if (result) {
                setHorizontalDomino(x, y);
                return result;
            }
        } else {
            x = currentEmptyCells.get(end).getKey();
            y = Math.min(currentEmptyCells.get(start).getValue(), currentEmptyCells.get(end).getValue()) + 1;
            logger.info("x: "  + x + " " + "y: " + y);
            result = x == currentEmptyCells.get(start).getKey() && areVerticalNeighborsEmpty(x, y);
            if (result)
                setVerticalDomino(x, y);
        }
        return result;
    }

    public final Cell[][] getCells() {
        return cells;
    }

    /**
     * Returns the current {@link Direction}.
     *
     * @return {@link #direction}.
     */
    public final Direction getDirection() {
        return direction;
    }

    /**
     * Sets {@link #direction} to the given {@link Direction}.
     *
     * @param direction representing the direction of the 3x1{@link Cell}(Domino).
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public final long getScore() {
        return TimeUnit.SECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
    }

    public final void setNumOfEmptyCells(int numOfEmptyCells) {
        this.numOfEmptyCells = numOfEmptyCells;
    }

    public final int getBoardSize() {
        return boardSize;
    }

    public final Map<Integer, Map.Entry<Integer, Integer>> getCurrentEmptyCells() {
        return currentEmptyCells;
    }

}
