package Controller;

import Model.Game;
import Model.GameComponents.Board;
import View.GameBoardView;
import View.GameView;
import View.ScoreView;

/**
 * The main controller of the application, responsible for controlling the user inputs and
 * manipulate the model's data.
 */
public class GameController {

    /**
     * Constant field storing the width of the {@link javafx.scene.Scene}.
     */
    private final int WIDTH = 800;
    /**
     * Constant field storing the height of the {@link javafx.scene.Scene}.
     */
    private final int HEIGHT = 600;
    /**
     * The {@link Game} which contains all the data of the actual game.
     */
    private Game model;
    /**
     * The current {@link GameView} which presents the data of the application.
     */
    private GameView gameView;
    /**
     * The current {@link GameBoardView} which presents the data of the actual game.
     */
    private GameBoardView gameBoardView;

    /**
     * The current (@link ScoreView} which display's the top results achieved.
     */
    private ScoreView scoreView;

    /**
     * Initializes the model with the representation of a {@link #WIDTH} * {@link #HEIGHT} {@link Board}.
     */
    public void initModel() {
        this.model = new Game(new Board(WIDTH, HEIGHT));
        initGameBoardView();
    }

    /**
     * Initializes the new Score Board scene.
     */
    public void initScoreView() {
        this.scoreView = new ScoreView(this);
    }

    /**
     * Initializes the new Score Board updates with the current result.
     *
     * @param name The name of the player who just won.
     * @param score The score achieved by the player.
     */
    public void initScoreView(String name, Long score) {
        this.scoreView = new ScoreView(this, name, score);
    }

    /**
     * Initializes the View of the actual game.
     */
    public void initGameBoardView() {
        this.gameBoardView = new GameBoardView(this);
    }

    public final GameView getGameView() {
        return gameView;
    }

    public void setGameView(GameView view) {
        this.gameView = view;
    }

    public final Game getModel() {
        return model;
    }

    public final void setModel(Game model) {
        this.model = model;
    }

    public final ScoreView getScoreView() {
        return this.scoreView;
    }

}
