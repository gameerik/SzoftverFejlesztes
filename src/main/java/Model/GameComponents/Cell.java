package Model.GameComponents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Class representing the cells which build up the board.
 */
public class Cell {

    /**
     * The {@link State} of the cell.
     * Can be either {@link State #EMPTY} or {@link State #DOMINO}
     */
    private State state;

    /**
     * The horizontal position of the cell.
     */
    private double positionX;

    /**
     * The vertical position of the cell.
     */
    private double positionY;

    /**
     * The number of dots, this {@link Cell} contains.
     */
    private int numOfDots;

    /**
     * The {@link org.slf4j.Logger logger} used in this class.
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Constructs a Cell, having set number of dots, which will be used to construct a {@link Board}.
     *
     * @param positionX The horizontal position of the {@link Cell} on the.
     * @param positionY The vertical position of the {@link Cell} on the.
     * @param state     The {@link State} of the Cell.
     * @param numOfDots The number of dots on the Cell.
     */
    public Cell(double positionX, double positionY, State state, int numOfDots) {
        logger.info("Creating Cell");
        this.state = state;
        this.positionX = positionX;
        this.positionY = positionY;
        this.numOfDots = numOfDots;
    }

    /**
     * Checks whether the current {@link State} of the Cell is {@link State #EMPTY}.
     *
     * @return true, if it is, false otherwise.
     */
    public boolean isEmpty() {
        return state == State.EMPTY;
    }

    /**
     * Drawing this cell on the console, along with it's current {@link State}.
     *
     * @param value The value shown if the cell is {@link State #EMPTY}.
     */
    public void display(String value)  {
        System.out.printf("[%2s]", this.state == State.EMPTY ? value : "D");
    }

    /**
     * Drawing this cell on the console, along with it's current {@link State}.
     *
     */
    public void display()  {
        display(" ");
    }


    public final double getPositionX() {
        return positionX;
    }

    public final double getPositionY() {
        return positionY;
    }

    public final State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public final int getNumOfDots() {
        return numOfDots;
    }

    public void setNumOfDots(int numOfDots) {
        this.numOfDots = numOfDots;
    }


}
