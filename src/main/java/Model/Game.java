package Model;

import Model.GameComponents.Board;
import Model.GameComponents.Direction;
import Model.GameComponents.ScoreBoard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The main model of the Model, which wraps the representation of the game inside a {@link Board}.
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
     * Construct the Model model with the given {@link Board} representation of the game.
     *
     * @param board The representation of the game.
     * @throws InstantiationError if the given representation is {@link java.lang.ref.ReferenceQueue Null}.
     */
    public Game(Board board) {
        logger.info("initiating the Model");
        if (board == null)
            throw new InstantiationError("Model cannot be instantiated, without a board");
        this.board = board;
        this.score = 0;
    }

    /**
     * Starts the game loop, also initiates the {@link java.util.Scanner} responsible for receiving user input.
     */
    public void play() {
        logger.info("starting game loop");
        Scanner scan = new Scanner(System.in);
        while (!board.isGoal()) {
            display();
            setNextDominoDirection(scan);
            displayPossibilities();
            setAndPlaceNextDomino(scan);
        }
        ScoreBoard sb = new ScoreBoard();
        if (board.isVictory()) {
            System.out.println("Congratulations! You won with a score of: " + getScore());
            System.out.println("Here are the top scores:\n");
            sb.display();
        } else {
            System.out.println("Game over!\nFailed to place all 21 dominos, here are the top scores:\nv");
            sb.display();
        }
    }

    /**
     * Draws the current state of the {@link Model.GameComponents.Board} along with information about the Domino
     * palcement to the user.
     */
    public void display() {
        System.out.println("This is the current state of the board. Dominos can be placed only using empty cells.Three" +
                " cells are used to place a Domino [vertically or horizontally]");
        board.display();
    }

    /**
     * Set's the direction of the next 3x1{@link Model.GameComponents.Cell}(Domino), based on the user's choice.
     *
     * @param scan The {@link Scanner} object used to receive the user input.
     */
    public void setNextDominoDirection(Scanner scan) {
        String input = "";
        while (!input.equals("v") && !input.equals("h")) {
            System.out.println("Choose the next domino's direction: vertical or horizontal? [V/H]");
            if (scan.hasNext()) {
                input = scan.next();
                input = input.toLowerCase();
            }
        }
        board.setDirection(input.equals("v") ? Direction.VERTICAL : Direction.HORIZONTAL);
    }

    /**
     * Set's the position of the 3x1{@link Model.GameComponents.Cell}(Domino) which will get placed on the
     * {@link Board}, based on the {@link Model.GameComponents.Cell} numbers given by the user.
     *
     * @param scan The {@link Scanner} object used to receive the user input.
     */
    public void setAndPlaceNextDomino(Scanner scan) {
        logger.info("waiting for the user to give 2 viable cells");
        Map.Entry<Integer, Integer> boundaries;
        do {
            boundaries = getCellNumbers(scan);
        } while (!board.isPlaceable(boundaries.getKey(), boundaries.getValue()));
    }

    /**
     * Stores and returns two {@link Model.GameComponents.Cell}s chosen by the user.
     *
     * @param scan he {@link Scanner} object used to receive the user input.
     * @return the chosen two {@link Model.GameComponents.Cell}s of a Domino stored in a {@link Map.Entry}.
     */
    public Map.Entry<Integer, Integer> getCellNumbers(Scanner scan) {
        logger.info("Getting two cell positions from the user");
        Integer start = -1, end = -1;
        do {
            System.out.println("Choose the starting point of the next domino (cell number)");
            if (scan.hasNextInt())
                start = scan.nextInt();
            else
                scan.next();
        } while (!board.getCurrentEmptyCells().containsKey(start));
        do {
            System.out.println("Choose the ending point of the next domino (cell number)");
            if (scan.hasNextInt())
                end = scan.nextInt();
           else
               scan.next();
        } while (!board.getCurrentEmptyCells().containsKey(end));
        return new AbstractMap.SimpleEntry<>(start, end);
    }


    /**
     * Displays the possible placement choices, based on the {@link Model.GameComponents.Board}'s current empty
     * {@link Model.GameComponents.Cell}s and the user given {@link Model.GameComponents.Direction}.
     */
    public void displayPossibilities() {
        board.displayPossiblePlacements();
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
