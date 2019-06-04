package Model.GameComponents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class representing a Dot on the surface of a {@link Cell}.
 */
public class Dot {

    /**
     * The horizontal position of the Dot, on the {@link javafx.scene.Scene}.
     */
    private double positionX;

    /**
     * The vertical position of the Dot, on the {@link javafx.scene.Scene}.
     */
    private double positionY;

    /**
     * The {@link org.slf4j.Logger logger} used in this class.
     */
    private Logger logger  = LoggerFactory.getLogger(this.getClass());

    /**
     * Constructs a Dot, at the given coordinates.
     * @param positionX the horizontal position of the Dot, on the {@link javafx.scene.Scene}.
     * @param positionY the vertical position of the Dot, on the {@link javafx.scene.Scene}.
     */
    public Dot(double positionX, double positionY) {
        logger.info("Creating Dot");
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public final double getPositionX() {
        return positionX;
    }

    public final double getPositionY() {
        return positionY;
    }


}
