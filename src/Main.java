import Controller.GameController;
import View.GameView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        // Skapa en instans av GameController och GameView
        GameController gameController = new GameController();
        GameView gameView = new GameView(gameController);
        gameController.setGameView(gameView);
        // Starta spelet genom att initiera JavaFX Scene
        gameView.start(primaryStage);
    }
}