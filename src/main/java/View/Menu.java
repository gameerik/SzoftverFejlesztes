package View;

import Controller.GameController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * Class wrapping the Menu scene.
 */
public class Menu {

    /**
     * Constant field storing the width of the {@link Scene}.
     */
    private final int WIDTH = 800;

    /**
     * Constant field storing the height of the {@link Scene}.
     */
    private final int HEIGHT = 600;

    /**
     * Constant field storing the name of the start button.
     */
    private final String startButtonName = "startButton";

    /**
     * Constant field storing the name of the score button.
     */
    private final String scoreButtonName = "scoretButton";

    /**
     * Constant field storing the name of the background image.
     */

    /**
     * The root of the primary {@link javafx.stage.Stage}.
     */
    private Group root;

    /**
     * The menu {@link Scene}.
     */
    private Scene scene;

    /**
     * The main controller of the menu.
     */
    private GameController controller;

    /**
     * The {@link org.slf4j.Logger logger} used in this class.
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * Constructs a new menu {@link Scene}, and attaches the {@link Button}s it.
     *
     * @param controller The main controller.
     */
    public Menu(GameController controller) {
        logger.info("Constructing the menu and the visual components");
        this.controller = controller;
        this.root = controller.getGameView().getRoot();
        init();
    }

    /**
     * Responsible for initializing the start and score buttons, also for forwarding the received user input to the
     * {@link #controller}.
     */
    public void init() {
        logger.info("Initiating the menu");
        scene = new Scene(root, WIDTH, HEIGHT);
        InputStream imageStream = getClass().getResourceAsStream("/background.png");
        Image image = new Image(imageStream);
        Label playerLabel = new Label("Player Name: ");
        TextField playerName = new TextField("");
        playerLabel.setLabelFor(playerName);
        playerName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String current, String input) {
                if (input != null && !input.isEmpty()) {
                    logger.info("new input on the Name field");
                }
            }
        });
        Button start = new Button(startButtonName);
        Button score = new Button(scoreButtonName);
        start.setStyle("-fx-background-color: #32CD32;");
        score.setStyle("-fx-background-color: #32CD32;");
        start.setMinWidth(WIDTH / 6);
        start.setMinHeight(HEIGHT / 8);
        score.setMinWidth(WIDTH / 6);
        score.setMinHeight(HEIGHT / 8);
        double startCenterX = start.getMinWidth() / 2;
        double scoreCenterX = score.getMinWidth() / 2;
        start.setLayoutX(WIDTH / 2 - startCenterX);
        start.setLayoutY(HEIGHT / 3);
        score.setLayoutX(WIDTH / 2 - scoreCenterX);
        score.setLayoutY(HEIGHT / 2);
        start.setOnMouseClicked(event -> {
            String name = playerName.getText();
            if (name != null && !name.isEmpty()) {
                controller.initModel();
                controller.getModel().setName(name);
                logger.info("current player: " + controller.getModel().getName());
            }
        });
        score.setOnMouseClicked(event -> {
            controller.initScoreView();
        });;
        playerName.setLayoutX(start.getLayoutX() - WIDTH / 32);
        playerName.setLayoutY(start.getLayoutY() - HEIGHT / 16);
        playerLabel.setLayoutX(playerName.getLayoutX());
        playerLabel.setLayoutY(playerName.getLayoutY() - HEIGHT / 28);
        root.getChildren().addAll(score, start, playerLabel, playerName);
        scene.setFill(new ImagePattern(image));
    }

    public final Scene getMenuScene() {
        return scene;
    }

}
