package Model.GameComponents;

import View.fillDomino;
import View.fillHorizontalDomino;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;
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
     * The width of a cell in pixels.
     */
    private double cellWidth;
    /**
     * The height of a cell in pixels.
     */
    private double cellHeight;
    /**
     * Collection of {@link Cell} which build up the board.
     */
    private Cell[][] cells;
    /**
     * The current direction of the 3x1 {@link Cell} (domino).
     */
    private fillDomino fd;
    /**
     * The {@link org.slf4j.Logger logger} used in this class.
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Creates the board, with the given dimensions (in pixels).
     * Also, initiates {@link #cells} with empty states and the corresponding colors.
     *
     * @param width  The width of the board in pixels.
     * @param height The height of the board in pixels.
     */
    public Board(double width, double height) {
        logger.info("Creating Board");
        this.width = width;
        this.height = height;
        this.cellWidth = width / boardSize;
        this.cellHeight = height / boardSize;
        this.fd = new fillHorizontalDomino();
        this.numOfEmptyCells = boardSize * boardSize;
        this.startTime = System.nanoTime();
        cells = new Cell[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++) {
                Color color = i % 2 == j % 2 ? Color.WHITE : Color.BLACK;
                cells[i][j] = new Cell(j * cellWidth,
                        i * cellHeight,
                        color, State.EMPTY,
                        (new Random().nextInt(MAX_DOTS) + MIN_DOTS),
                        cellWidth / MAX_DOTS,
                        cellHeight / MAX_DOTS);
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
                        && (areHorizontalNeighborsEmpty(i, j) || areVerticalNeighborsEmpty(i, j)))
                    return false;
        endTime = System.nanoTime();
        logger.info("Current game state: Game is over");
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
        if (boardPositionX == 0 || boardPositionX == boardSize - 1)
            return false;
        Cell rightNeighbour = cells[boardPositionY][boardPositionX + 1];
        Cell leftNeighbour = cells[boardPositionY][boardPositionX - 1];
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
        if (boardPositionY == 0 || boardPositionY == boardSize - 1)
            return false;
        Cell bottomNeighbour = cells[boardPositionY + 1][boardPositionX];
        Cell topNeighbour = cells[boardPositionY - 1][boardPositionX];
        return bottomNeighbour.getState() == State.EMPTY && topNeighbour.getState() == State.EMPTY;
    }

    /**
     * Searches for the {@link Cell} where the cursor is located.
     *
     * @param mouseX The horizontal position of the cursor.
     * @param mouseY The vertical position of the cursor.
     * @return The cell, if it was found, {@link java.lang.ref.ReferenceQueue Null} otherwise.
     */
    public Cell findCurrentCell(double mouseX, double mouseY) {
        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++) {
                Cell currentCell = cells[i][j];
                if ((mouseX > currentCell.getPositionX() && mouseX < currentCell.getPositionX() + cellWidth)
                        && (mouseY > currentCell.getPositionY() && mouseY < currentCell.getPositionY() + cellHeight))
                    return currentCell;
            }
        return null;
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
            cells[j][positionX].setState(State.DOMINO);
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
            cells[positionY][i].setState(State.DOMINO);
    }

    /**
     * Searches for the given {@link Cell}'s position on the {@link Board}.
     *
     * @param cell The {@link Cell} whom position we seek.
     * @return The horizontal and vertical {@link Board} position of the {@link Cell} wrapped in a {@link Pair}.
     */
    public Pair<Integer, Integer> findCellBoardPosition(Cell cell) {
        logger.info("looking for cell");
        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++)
                if (cell == cells[j][i])
                    return new Pair<>(j, i);
        return null;
    }

    /**
     * Generates the position of each {@link Dot}, for the given {@link Cell}, based on the {@link Cell #numOfDots}.
     *
     * @param cell The {@link Cell} to generate the {@link Dot}/Dots.
     */
    public void generateDots(Cell cell) {
        logger.info("Generating dots on the current cell");
        int numOfDots = cell.getNumOfDots();
        List<Dot> dots = cell.getDots();
        double positionX = cell.getPositionX();
        double positionY = cell.getPositionY();
        switch (numOfDots) {
            case 1:
                dots.add(new Dot(positionX + cellWidth / 2, positionY + cellHeight / 2));
                break;
            case 2:
                dots.add(new Dot(positionX + cellWidth / 3, positionY + cellHeight / 2));
                dots.add(new Dot(positionX + cellWidth * 2 / 3, positionY + cellHeight / 2));
                break;
            case 3:
                dots.add(new Dot(positionX + cellWidth / 2, positionY + cellHeight / 4));
                dots.add(new Dot(positionX + cellWidth / 2, positionY + cellHeight * 2 / 4));
                dots.add(new Dot(positionX + cellWidth / 2, positionY + cellHeight * 3 / 4));
                break;
            case 4:
                dots.add(new Dot(positionX + cellWidth / 3, positionY + cellHeight / 3));
                dots.add(new Dot(positionX + cellWidth / 3, positionY + cellHeight * 2 / 3));
                dots.add(new Dot(positionX + cellWidth * 2 / 3, positionY + cellHeight / 3));
                dots.add(new Dot(positionX + cellWidth * 2 / 3, positionY + cellHeight * 2 / 3));
                break;
            case 5:
                dots.add(new Dot(positionX + cellWidth / 3, positionY + cellHeight / 3));
                dots.add(new Dot(positionX + cellWidth / 3, positionY + cellHeight * 2 / 3));
                dots.add(new Dot(positionX + cellWidth / 2, positionY + cellHeight / 2));
                dots.add(new Dot(positionX + cellWidth * 2 / 3, positionY + cellHeight / 3));
                dots.add(new Dot(positionX + cellWidth * 2 / 3, positionY + cellHeight * 2 / 3));
                break;
            case 6:
                dots.add(new Dot(positionX + cellWidth / 3, positionY + cellHeight / 4));
                dots.add(new Dot(positionX + cellWidth / 3, positionY + cellHeight * 2 / 4));
                dots.add(new Dot(positionX + cellWidth / 3, positionY + cellHeight * 3 / 4));
                dots.add(new Dot(positionX + cellWidth * 2 / 3, positionY + cellHeight / 4));
                dots.add(new Dot(positionX + cellWidth * 2 / 3, positionY + cellHeight * 2 / 4));
                dots.add(new Dot(positionX + cellWidth * 2 / 3, positionY + cellHeight * 3 / 4));
                break;
        }
        cell.setDots(dots);
    }

    public final double getCellWidth() {
        return cellWidth;
    }

    public final double getCellHeight() {
        return cellHeight;
    }


    public final Cell[][] getCells() {
        return cells;
    }

    /**
     * Return {@link #fd}.
     *
     * @return {@link #fd}.
     */
    public final fillDomino getfillDomino() {
        return fd;
    }

    /**
     * Sets {@link #fd} to the given parameter.
     *
     * @param fd the implementation of a {@link fillDomino}, representing the direction of the 3x1{@link Cell}(Domino).
     */
    public void setfillDomino(fillDomino fd) {
        this.fd = fd;
    }

    public final long getScore() {
        return TimeUnit.SECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
    }

    public final void setNumOfEmptyCells(int numOfEmptyCells) {
        this.numOfEmptyCells = numOfEmptyCells;
    }

}
