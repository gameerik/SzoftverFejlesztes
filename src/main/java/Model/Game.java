package Model;

import Model.GameComponents.Board;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main model of the Game, which wraps the representation of the game inside a {@link Board}.
 */
public class Game {

    /**
     * The representation of the current game.
     */
    private Board board;

    /**
     * The score achieved by the player.
     */
    private long score;

    /**
     * The name of the current player.
     */
    private String name;

    /**
     * The {@link org.slf4j.Logger logger} used in this class.
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Construct the Game model with the given {@link Board} representation of the game.
     *
     * @param board The representation of the game.
     * @throws InstantiationError if the given representation is {@link java.lang.ref.ReferenceQueue Null}.
     */
    public Game(Board board) {
        logger.info("initiating the Game");
        if (board == null)
            throw new InstantiationError("Game cannot be instantiated, without a board");
        this.board = board;
        this.score = 0;
    }

    public final Board getBoard() {
        return board;
    }

    public final long getScore() {
        return board.getScore();
    }

    public final String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
