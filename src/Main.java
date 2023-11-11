import Controller.GameController;
import View.Dialog;
import View.GameView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GameController gameController = new GameController();
        GameView gameView = new GameView(gameController);
        gameController.setGameView(gameView);
        gameView.start(primaryStage);
    }
}
