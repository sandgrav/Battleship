import View.GameView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GameView gameView = new GameView();
        gameView.start(primaryStage);
    }
}
