package Model.GameComponents;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the cells which build up the board.
 */
public class Cell {

    /**
     * The {@link Color} of the domino.
     */
    private final Color dominoColor = Color.BROWN;
    /**
     * The {@link State} of the cell.
     * Can be either {@link State #EMPTY} or {@link State #DOMINO}
     */
    private State state;
    /**
     * The {@link Color} of the cell.
     */
    private Color color;
    /**
     * The horizontal {@link javafx.scene.Scene} position of the cell.
     */
    private double positionX;

    /**
     * The vertical {@link javafx.scene.Scene} position of the cell.
     */
    private double positionY;

    /**
     * The unified width, of each {@link Dot} the {@link Cell} contains.
     */
    private double dotWidth;


    /**
     * The unified height, of each {@link Dot} the {@link Cell} contains.
     */
    private double dotHeight;

    /**
     * Collection of {@link Dot}s, which contain their coordinates, inside the {@link Cell}.
     */
    private List<Dot> dots;

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
     * @param positionX The horizontal position of the {@link Cell} on the {@link javafx.scene.Scene}.
     * @param positionY The vertical position of the {@link Cell} on the {@link javafx.scene.Scene}.
     * @param color     The {@link Color} of the Cell.
     * @param state     The {@link State} of the Cell.
     * @param numOfDots The number of {@link Dot}s on the Cell.
     * @param dotWidth  The unified width of the {@link Dot}.
     * @param dotHeight The unified height of the {@link Dot}.
     */
    public Cell(double positionX, double positionY, Color color, State state, int numOfDots, double dotWidth, double dotHeight) {
        logger.info("Creating Cell");
        this.color = color;
        this.state = state;
        this.positionX = positionX;
        this.positionY = positionY;
        this.numOfDots = numOfDots;
        this.dotWidth = dotWidth;
        this.dotHeight = dotHeight;
        dots = new ArrayList<>();
    }

    private boolean isEmpty() {
        return state == State.EMPTY;
    }

    /**
     * Renders the {@link #dots} of the Cell, on a {@link javafx.scene.canvas.Canvas}.
     *
     * @param gc The {@link GraphicsContext}, which is used to draw on the {@link javafx.scene.canvas.Canvas}.
     */
    public void renderDots(GraphicsContext gc) {
        logger.info("Rendering dots");
        for (Dot dot : dots) {
            gc.setFill(Color.WHITE);
            gc.fillOval(dot.getPositionX(), dot.getPositionY(), dotWidth, dotHeight);
        }
    }

    /**
     * Return the color of the domino, based on it's {@link State}.
     *
     * @return {@link Color #BROWN} if the state of the cell is {@link State #DOMINO}, {@link #color} otherwise.
     */
    public final Color getColor() {
        if (state == State.DOMINO)
            return dominoColor;
        return color;
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

    public final double getDotWidth() {
        return dotWidth;
    }

    public final double getDotHeight() {
        return dotHeight;
    }

    public final List<Dot> getDots() {
        return dots;
    }

    public void setDots(List<Dot> dots) {
        this.dots = dots;
    }


}
