package View;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Represents a function that accepts a {@link GraphicsContext} along with the 2D coordinates and dimensions of a
 * rectangle.
 */
@FunctionalInterface
public interface fillDomino {

    /**
     * The {@link Color} of the 3x1 {@link Model.GameComponents.Cell}(Domino).
     */
    final Color dominoColor = Color.CHOCOLATE;

    /**
     * The stroke {@link Color} of the 3x1 {@link Model.GameComponents.Cell}(Domino).
     */
    final Color strokeColor = Color.WHITE;

    /**
     * Renders a rectangle representing a 3x1{@link Model.GameComponents.Cell}(Domino).
     *
     * @param gc        The {@link GraphicsContext} which will be used, to draw on the {@link javafx.scene.canvas.Canvas}.
     * @param positionX The start horizontal position, on the {@link javafx.scene.Scene}.
     * @param positionY The start vertical position, on the {@link javafx.scene.Scene}.
     * @param width     The width of a 1x1{@link Model.GameComponents.Cell}.
     * @param height    The width of a 1x1{@link Model.GameComponents.Cell}.
     */
    public void draw(GraphicsContext gc, double positionX, double positionY, double width, double height);

}
