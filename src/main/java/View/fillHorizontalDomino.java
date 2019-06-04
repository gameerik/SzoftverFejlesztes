package View;

import javafx.scene.canvas.GraphicsContext;

/**
 * Class represent a tool for horizontal rendering of a 3x1{@link Model.GameComponents.Cell}(Domino).
 */
public class fillHorizontalDomino implements fillDomino {

    /**
     * @param gc      The {@link GraphicsContext} which will be used, to draw on the {@link javafx.scene.canvas.Canvas}.
     * @param centerX The horizontal center position of the rectangle, on the {@link javafx.scene.Scene}.
     * @param centerY The vertical center position of the rectangle, on the {@link javafx.scene.Scene}.
     * @param width   The width of a 1x1{@link Model.GameComponents.Cell}.
     * @param height  The width of a 1x1{@link Model.GameComponents.Cell}.
     */
    @Override
    public void draw(GraphicsContext gc, double centerX, double centerY, double width, double height) {
        gc.setFill(dominoColor);
        gc.setStroke(strokeColor);
        gc.fillRect(centerX - 1.5 * width, centerY - height / 2, 3 * width, height);
        for (double j = -1.5; j < 1.5; j++) {
            gc.strokeRect(centerX + j * width, centerY - height / 2, width, height);
        }
    }

}
