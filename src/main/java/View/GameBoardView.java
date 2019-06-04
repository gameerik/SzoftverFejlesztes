package View;

import Controller.GameController;
import Model.GameComponents.Board;
import Model.GameComponents.Cell;
import Model.GameComponents.Dot;
import Model.GameComponents.State;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * A class which wraps up all the rendering required of the actual game, represented by the {@link Board}.
 */
public class GameBoardView {

    /**
     * Constant field storing the width of the {@link Scene}.
     */
    private final static int WIDTH = 800;

    /**
     * Constant field storing the height of the {@link Scene}.
     */
    private final static int HEIGHT = 600;

    /**
     * The main controller of the {@link View}.
     */
    private GameController controller;

    /**
     * The {@link org.slf4j.Logger logger} used in this class.
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Constructs the view, and initializes the required components, with {@link #init()}.
     *
     * @param controller The main controller.
     */
    public GameBoardView(GameController controller) {
        this.controller = controller;
        init();
    }


    /**
     * Places the 3x1{@link Cell}(Domino), on the {@link Board}, based on the current {@link Board #fd}(direction).
     *
     * @param board The current game Board.
     * @param event The mouse event, which contains the coordinates of the cursor.
     */
    public static void placeDomino(Board board, MouseEvent event) {
        Cell currentCell = board.findCurrentCell(event.getX(), event.getY());
        if (currentCell == null || currentCell.getState() == State.DOMINO)
            return;
        Pair<Integer, Integer> cellBoardPosition = board.findCellBoardPosition(currentCell);
        int positionX = cellBoardPosition.getValue();
        int positionY = cellBoardPosition.getKey();
        fillDomino fd = board.getfillDomino();
        if (fd.getClass() == fillVerticalDomino.class && board.areVerticalNeighborsEmpty(positionX, positionY))
            board.setVerticalDomino(positionX, positionY);
        else if (fd.getClass() == fillHorizontalDomino.class && board.areHorizontalNeighborsEmpty(positionX, positionY))
            board.setHorizontalDomino(positionX, positionY);

    }

    /**
     * Renders the whole {@link Board}, along with the {@link Cell}s within it.
     *
     * @param board The representation of the game.
     * @param gc    The {@link GraphicsContext} which will be used to draw on the {@link Canvas}.
     */
    public void renderBoard(Board board, GraphicsContext gc) {
        if (board.isGoal() && board.isVictory()) {
            logger.info("new score achieved: " + board.getScore());
            controller.initScoreView(controller.getModel().getName(), board.getScore());
        } else if (board.isGoal()) {
            logger.info("game over, failed to place all 21 dominos");
            controller.getGameView().initMenu();
        }
        Cell[][] cells = board.getCells();
        int size = board.getCells().length;
        double cellWidth = board.getCellWidth();
        double cellHeight = board.getCellHeight();
        for (int i = 0; i < size; i++)
            for (int j = 0; j < cells[i].length; j++) {
                Cell currentCell = cells[i][j];
                gc.setFill(currentCell.getColor());
                gc.setStroke(Color.BLACK);
                double cellX = currentCell.getPositionX();
                double cellY = currentCell.getPositionY();
                gc.fillRect(cellX, cellY, cellWidth, cellHeight);
                gc.strokeRect(cellX, cellY, cellWidth, cellHeight);
                if (currentCell.getState() == State.DOMINO)
                    renderDots(board, currentCell, gc);
            }
    }

    /**
     * Renders the {@link Dot}s, of the given {@link Cell}.
     *
     * @param board The representation of the game.
     * @param cell  The {@link Cell}, containing the {@link Dot}s, to be shown.
     * @param gc    The {@link GraphicsContext} which will be used to draw on the {@link Canvas}.
     */
    public static void renderDots(Board board, Cell cell, GraphicsContext gc) {
        if (cell.getDots().size() == 0)
            board.generateDots(cell);
        gc.setFill(Color.WHITE);
        List<Dot> dots = cell.getDots();
        for (Dot dot : dots) {
            gc.fillOval(dot.getPositionX() - cell.getDotWidth() / 2,
                    dot.getPositionY() - cell.getDotHeight() / 2,
                    cell.getDotWidth(), cell.getDotHeight());
        }
    }

    /**
     * Renders the domino based on the current {@link Board #fd}(direction).
     *
     * @param board The representation of the game.
     * @param gc    The {@link Cell}, containing the {@link Dot}s, to be shown.
     * @param event The {@link MouseEvent} which contains the coordinates of the cursor.
     */
    public static void renderDomino(Board board, GraphicsContext gc, MouseEvent event) {
        board.getfillDomino().draw(gc, event.getX(), event.getY(), board.getCellWidth(), board.getCellHeight());
    }

    /**
     * Initializes the actual game {@link Scene}, and set's the handlers for the user input.
     */
    public void init() {
        logger.info("Initializing the actual game canvas");
        GameView gameView = controller.getGameView();
        Stage currentStage = gameView.getCurrentStage();
        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        final Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        currentStage.setScene(scene);
        Board board = controller.getModel().getBoard();
        renderBoard(board, gc);
        canvas.setOnMouseMoved(event -> {
            renderBoard(board, gc);
            renderDomino(board, gc, event);
        });
        canvas.setOnMouseClicked(event -> {
            placeDomino(board, event);
            renderBoard(board, gc);
        });

        scene.setOnKeyPressed(new RotationHandler(board, scene, gc, this));
    }
}
