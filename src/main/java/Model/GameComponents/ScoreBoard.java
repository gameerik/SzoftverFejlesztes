package Model.GameComponents;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Class representing the scoreboard.
 */
public class ScoreBoard {

    /**
     * Constant field storing the maximum number of result.
     */
    private final int MAX = 10;
    /**
     * Collection of {@link Player}, containt the top scoring players.
     */
    List<Player> wrapper;
    /**
     * Map containing the player's name and the achieved score.
     */
    private Map<String, Long> results;
    /**
     * The minimum score which is still part of the top ten results.
     */
    private Long currentMin;
    /**
     * The player's name with the current lowest score.
     */
    private String currentMinPlayer;

    /**
     * The file's name which contains the results obtained by players.
     */
    private final String fileName = "top.json";

    /**
     * The {@link org.slf4j.Logger logger} used in this class.
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    ;

    /**
     * Constructs a new ScoreBoard to showcase the top scores achieved by players.
     */
    public ScoreBoard() {
        logger.info("Constructing the scoreboard scene");
        this.results = new HashMap<>();
        wrapper = new ArrayList<Player>();
        loadTopResults();
        List<String> orderedResults = orderResults();
    }

    /**
     * Loads the top results achieved by player, from a JSON file.
     */
    private void loadTopResults() {
        logger.info("loading the scoreboard based on previous results");
        try {
            Gson gson = new Gson();
            InputStream input = getClass().getResourceAsStream("/top.json");
            Type listType = new TypeToken<List<Player>>() {
            }.getType();
            wrapper = gson.fromJson(new InputStreamReader(input), listType);
            if (wrapper == null)
                return;
            for (Player player : wrapper) {
                update(player);
            }
        } catch (Exception e) {
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
     *
     * @param results The {@link HashMap} containing the player - score {@link Map.Entry}s.
     */
    private void updateMin(Map<String, Long> results) {
        Long min = Long.MIN_VALUE;
        String minPlayer = "";
        for (Map.Entry<String, Long> temp : results.entrySet()) {
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
        Map<String, Long> temp = new HashMap<>(results);
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
     * @param name  The name of the player.
     * @param score The score achieved by the player.
     */
    public void updateResults(String name, Long score) {
        Gson gson = new Gson();
        loadTopResults();
        wrapper.add(new Player(name, score));
        FileWriter writer = null;

        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File resultJson = new File(pathToTopResults());
            writer = new FileWriter(resultJson, false);
            writer.write(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * Append's the {@link #wrapper}, with the new result.
     *
     * @param name  The player's name.
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


    /**
     * Returns the path to the json file which holds the information about the scores of the players.
     *
     * @return the path to the top.json file.
     */
    private String pathToTopResults() {
        String result = new String();
        try {
            Path workingDirectory = FileSystems.getDefault().getPath("").toAbsolutePath();
            result = workingDirectory.toString() + File.separator + "top" + File.separator;
        } catch (Exception e) {
            logger.error(e.toString());
        }
        File directory = new File(result);
        if (directory.mkdir()) {
            logger.info("Directory {} created", result);
        }

        try {
            result = result + "top.json";
            Path dest = Paths.get(result);
            if (Files.notExists(dest)) {
                Files.createFile(Paths.get(result));
                logger.info("File {} created", result);
            } else {
                logger.info("{} already exists", result);
            }
        } catch (IOException e) {
            logger.error(e.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * Display's the current top scores obtained by players so far.
     */
    public void display() {
        List<String> scores = orderResults();
        int size = scores.size();
        for (int i = 0; i < size; i++)
            System.out.println(scores.get(i));
    }

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
         * Constructs the wrapper object.
         *
         * @param name  The player name.
         * @param score The player score.
         */
        public Player(String name, Long score) {
            this.name = name;
            this.score = score;
        }
    }

}
