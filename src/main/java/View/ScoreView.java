package View;

import Controller.GameController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Class wrapping the visual components of the scoreboard.
 */
public class ScoreView {

    /**
     * Constant field storing the width of the {@link Scene}.
     */
    private final int WIDTH = 800;

    /**
     * Constant field storing the height of the {@link Scene}.
     */
    private final int HEIGHT = 600;

    /**
     * Constant field storing the maximum number of result.
     */
    private final int MAX = 10;

    /**
     * Map containing the player's name and the achieved score.
     */
    private Map<String,Long> results;

    /**
     * The minimum score which is still part of the top ten results.
     */
    private Long currentMin;

    /**
     * The player's name with the current lowest score.
     */
    private String currentMinPlayer;

    /**
     * The main controller of the scoreboard.
     */
    private GameController controller;

    /**
     * The root of the primary {@link javafx.stage.Stage}.
     */
    private Group root;

    /**
     * The menu {@link Scene}.
     */
    private Scene scene;

    /**
     * The {@link org.slf4j.Logger logger} used in this class.
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Inner class, which will be used to wrap JSON objects.
     */
    private class Player {
	/**
	 * The score achieved by the player.
	 */
        public Long score;
	
	/**
	 * The name of the player.
	 */
        public String name;

	/**
         * Construct the wrapper object.
         *
         * @param name The player name.
         * @param score The player score.
         */
        public Player(String name, Long score) {
            this.name = name;
            this.score = score;
        }
    };

    /**
     * Collection of {@link Player}, containt the top scoring players.
     */
    List<Player> wrapper;


    /**
     * Constructs a new ScoreView to showcase the top scores achieved by players.
     * @param controller The main controller.
     */
    public ScoreView(GameController controller) {
        logger.info("Constructing the scoreboard scene");
        this.controller = controller;
        this.root = new Group();
        this.results = new HashMap<>();
        if (wrapper == null)
            wrapper = new ArrayList<Player>();
        init();
    }

    /**
     * Constructs a new ScoreView,and checks wheter the new player achievements, require the update of the score board.
     *
     * @param controller The main controller.
     * @param name The player's name who finished the game with a victory.
     * @param score The player's score.
     */
    public ScoreView(GameController controller, String name, Long score) {
        logger.info("Constructing the scoreboard scene");
        this.controller = controller;
        this.root = new Group();
        this.results = new HashMap<>();
        if (wrapper == null)
            wrapper = new ArrayList<Player>();
        loadTopResults();
        addResult(name, score);
        init();
    }

    /**
     * Responsible for initializing the {@link Scene} representing scoreboard.
     */
    public void init() {
        logger.info("initializing the scoreboard scene");
        scene = new Scene(root,WIDTH,HEIGHT);
        Button back = new Button("Back");
        root.getChildren().add(back);
        back.setOnMouseClicked(event -> {
            controller.getGameView().initMenu();
        });
        back.setStyle("-fx-background-color: #32CD32;");
        back.setMinWidth(WIDTH / 6);
        back.setMinHeight(HEIGHT / 6);
        back.setLayoutX(WIDTH - back.getMinWidth());
        back.setLayoutY(HEIGHT - back.getMinHeight());
        loadTopResults();
        List<Text> resultText = new ArrayList<>();
        final double horizontal = 40;
        final double vertical = HEIGHT / (MAX + 4);
        List<String> orderedResults = orderResults();
        for (int i = 0; i < orderedResults.size(); i++) {
            Text temp = new Text(horizontal, (i + 1) * vertical, orderedResults.get(i));
            temp.setFont(new Font(15));
            resultText.add(temp);
        }
        root.getChildren().addAll(resultText);
        Stage currentStage = controller.getGameView().getCurrentStage();
        currentStage.setScene(scene);
        currentStage.show();
    }

    /**
     * Loads the top results achieved by player, from a JSON file.
     */
    private void loadTopResults() {
        logger.info("loading the scoreboard based on previous results");
        try {
            Gson gson = new Gson();
            InputStream input = getClass().getResourceAsStream("/top.json");
            Type listType = new TypeToken<List<Player>>(){}.getType();
            wrapper = gson.fromJson(new InputStreamReader(input), listType);
            if (wrapper == null)
                return;
            for (Player player : wrapper) {
                update(player);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the scoreboard.
     *
     * @param player The player object which contains both the player name and score.
     */
    private void update(Player player) {
        String name = player.name;
        Long score = player.score;
        if (results.size() < MAX || score < currentMin) {
            results.put(name, score);
            updateMin(results);
        }
        if (results.size() > MAX)
            results.remove(currentMinPlayer);
    }

    /**
     * Updates {@link #currentMin} and {@link #currentMinPlayer}.
     * @param results The {@link HashMap} containing the player - score {@link Map.Entry}s.
     */
    private void updateMin(Map<String, Long> results) {
            Long min = Long.MIN_VALUE;
            String minPlayer = "";
            for (Map.Entry<String,Long> temp : results.entrySet()) {
                Long tempValue = temp.getValue();
                String tempName = temp.getKey();
                if (tempValue > min) {
                    min = tempValue;
                    minPlayer = tempName;
                }
            }
            currentMin = min;
            currentMinPlayer = minPlayer;
    }

    /**
     * Orders the results achieved by the players, and creates the String List containing the ordered results.
     *
     * @return The player names and the scores ordered by score.
     */
    private List<String> orderResults() {
        LinkedList<String> result = new LinkedList<>();
        Map<String,Long> temp = new HashMap<>(results);
        String minPlayer = currentMinPlayer;
        Long min = currentMin;
        int size = temp.size();
        for (int i = 0; i < size; i++) {
            result.addFirst(currentMinPlayer + " " + currentMin);
            temp.remove(currentMinPlayer);
            updateMin(temp);
        }
        this.currentMin = min;
        this.currentMinPlayer = minPlayer;
        return result;
    }

    /**
     * Updates the JSON file, which stores the previous results.
     *
     * @param name The name of the player.
     * @param score The score achieved by the player.
     */
    public void updateResults(String name, Long score) {
        Gson gson = new Gson();
        loadTopResults();
        wrapper.add(new Player(name, score));
    }

    /**
     * Append's the {@link #wrapper}, with the new result.
     *
     * @param name The player's name.
     * @param score The player's score.
     */
    public void addResult(String name, Long score) {
        if (this.wrapper != null)
            wrapper.add(new Player(name, score));
        else {
            wrapper = new ArrayList<Player>();
            wrapper.add(new Player(name, score));
        }
    }

}
