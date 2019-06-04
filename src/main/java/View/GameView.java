package View;

import Controller.GameController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class wrapping up the whole Game's {@link View} and initiates the starting point of the application.
 */
public class GameView extends Application {

    /**
     * The current stage of the Graphical User Interface.
     */
    Stage currentStage;
    /**
     * The current active scene on the stage.
     */
    Scene currentScene;
    /**
     * The initial root {@link Group}, which contains the {@link Scene} and other components.
     */
    Group root;
    /**
     * The main controller of the view.
     */
    private GameController controller;
    /**
     * The {@link org.slf4j.Logger logger} used in this class.
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * The entry point of the application.
     *
     * @param stage The primary {@link Stage}.
     */
    @Override
    public void start(Stage stage) {
        logger.info("Enterint application");
        currentStage = stage;
        currentStage.setTitle("Best game ever");
        currentStage.setResizable(false);
        controller = new GameController();
        controller.setGameView(this);
        initMenu();
    }

    /**
     * Initializes the {@link Menu} and updates the current scene.
     */
    public void initMenu() {
        root = new Group();
        logger.info("Initiating the menu");
        Menu menu = new Menu(controller);
        Scene menuScene = menu.getMenuScene();
        currentStage.setScene(menuScene);
        currentScene = menuScene;
        currentStage.show();
    }


    public final Stage getCurrentStage() {
        return currentStage;
    }

    public final Group getRoot() {
        return root;
    }

    public void setRoot(Group root) {
        this.root = root;
    }

    public final Scene getCurrentScene() {
        return currentScene;
    }
}
