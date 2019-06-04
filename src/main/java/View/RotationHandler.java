package View;

import Model.GameComponents.Board;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Class for handling the rotation, when {@link KeyCode #R} key is received as an user input.
 */
public class RotationHandler implements EventHandler<KeyEvent> {

    /**
     * The {@link Scene}, where the user input was received.
     */
    private Scene scene;

    /**
     * The {@link GraphicsContext}, which will be used to draw on the {@link Canvas}.
     */
    private GraphicsContext gc;

    /**
     * The {@link Board} of the current game.
     */
    private Board board;

    /**
     * The {@link GameBoardView} which instantiated the rotation handler.
     */
    private GameBoardView view;

    /**
     * The {@link org.slf4j.Logger logger} used in this class.
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Constructs a RotationHandler, based on the input parameters.
     *
     * @param board The {@link Board} of the current game.
     * @param scene The {@link Scene} where the user input was received.
     * @param gc    The {@link GraphicsContext}, which will be used to draw on the {@link Canvas}
     * @param view The {@link GameBoardView} which instantiated the rotation handler.
     */
    public RotationHandler(Board board, Scene scene, GraphicsContext gc, GameBoardView view) {
        logger.info("Creating rotation handler");
        this.scene = scene;
        this.gc = gc;
        this.board = board;
        this.view = view;
    }

    /**
     * Handles the rotation of the 3x1{@link Model.GameComponents.Cell}s(Dominos), which are going to be placed on the
     * {@link Board}, if the {@link KeyCode #R} keypress was received .
     *
     * @param event The {@link KeyEvent} which was received on the {@link Scene}.
     */
    @Override
    public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.R) {
            logger.info("rotating");
            fillDomino rd = board.getfillDomino();
            board.setfillDomino(rd.getClass() == fillVerticalDomino.class ? new fillHorizontalDomino() : new fillVerticalDomino());
            scene.setOnMouseMoved(e -> {
                view.renderBoard(board, gc);
                GameBoardView.renderDomino(board, gc, e);
            });
        }
    }

}
