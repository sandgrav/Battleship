import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Label label;
        GridPane ships = new GridPane();
        ships.setGridLinesVisible(true);
        char letter = 'A';
        for (int i = 0; i < 11; i++) {
            ships.getColumnConstraints().add(new ColumnConstraints(20));
           /* label = new Label();
            label.setText(Character.toString(letter));
            ships.add(label, );*/
        }
        for (int i = 0; i < 11; i++) {
            ships.getRowConstraints().add(new RowConstraints(20));
            label = new Label();
            label.setMinWidth(20);
            label.setText(Character.toString(letter));
            ships.add(label, 0, i);
            letter++;
        }
        label = new Label();
        label.setText("Ships");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(label, ships);
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10));
        hBox.setSpacing(20);
        hBox.getChildren().add(vBox);
        label = new Label();
        label.setText("Shots");
        GridPane shots = new GridPane();
        shots.setGridLinesVisible(true);
        for (int i = 0; i < 11; i++) {
            shots.getColumnConstraints().add(new ColumnConstraints(20));
        }
        for (int i = 0; i < 11; i++) {
            shots.getRowConstraints().add(new RowConstraints(20));
        }
        vBox = new VBox();
        vBox.getChildren().addAll(label, shots);
        hBox.getChildren().add(vBox);

        // Add buttons
        Button button1 = new Button();
        button1.setText("Start server");
        button1.onMouseClickedProperty(test());
        Button button2 = new Button();
        button2.setText("Start client");

        // Layout for right node in borderpane (for buttons)
        vBox = new VBox();
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);
        vBox.getChildren().addAll(button1, button2);

        // Overall layout
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(hBox);
        borderPane.setRight(vBox);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void test() {

    }
}