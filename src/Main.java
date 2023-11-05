import Controller.GameController;
import View.GameView;
import javafx.application.Application;
import javafx.stage.Stage;

/** Written by Morten Sandgrav **/

public class Main extends Application {
    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GameController gameController = new GameController();
        GameView gameView = new GameView(gameController);
        primaryStage.setScene(gameView.getScene());

        primaryStage.show();
    }

}