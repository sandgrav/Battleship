package View;

import Controller.ConnectionType;
import Controller.GameController;
import Model.Ships;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

//Alexandros Saltsidis
public class GameView extends Application {

    final int NUMROWS = 10;
    int NUMCOLS = 10;
    int CELLSIZE = 30;
    GameController gameController;
    VBox playerBoard;
    VBox enemyBoard;
    Slider slider;

    // Deklarera och skapa en 2d array för spelaren och fiende spelplan
    private boolean[][] playerBoardBox = new boolean[10][10];
    private boolean[][] enemyBoardBox = new boolean[10][10];
    private boolean gameStarted = false;
    private Ships ships;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Battleship");

        ships = new Ships();

        HBox hbox = new HBox(200);
        hbox.setAlignment(Pos.CENTER);
        // Skapa spelplaner för spelare och fiende
        playerBoard = createBoard(NUMROWS, NUMCOLS, CELLSIZE, "Player Board");
        enemyBoard = createBoard(NUMROWS, NUMCOLS, CELLSIZE, "Enemy Board");
        enemyBoard.setManaged(true);
        enemyBoard.setVisible(true);

        hbox.getChildren().add(playerBoard);
        hbox.getChildren().add(enemyBoard);

        // Morten: Add slider for delay
        slider = new Slider(1, 5, 1);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setSnapToTicks(true);
        slider.setBlockIncrement(1);

        // Morten: Lägg till knappar för att starta som server eller klient
        Button button1 = new Button();
        button1.setText("Start server");
        button1.setOnAction((e) -> {
            Dialog dialog = new Dialog();
            gameController.setConnectionType(ConnectionType.SERVER);
            dialog.show(gameController);
        });
        Button button2 = new Button();
        button2.setText("Start client");
        button2.setOnAction((e) -> {
            Dialog dialog = new Dialog();
            gameController.setConnectionType(ConnectionType.CLIENT);
            dialog.show(gameController);
        });
/*
        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> {
            if (!gameStarted) {
                playerBoardBox = new boolean[10][10];
                enemyBoardBox = new boolean[10][10];
                Random random = new Random();
                ships.placeShipsRandomly(playerBoardBox, ships.getPlayerShipSizes(), random);
                displayPlayerShips(playerBoard);

                System.out.println("Game started!");
                gameStarted = true;
                hideEnemyShips(enemyBoard);
            }
        });

        Button exitButton = new Button("Exit Game");
        exitButton.setOnAction(e -> exitGame());

        VBox gameControls = new VBox(startButton, exitButton);
*/
        // Skapa en VBox för knapparna
        VBox gameControls = new VBox(slider, button1, button2);
        gameControls.setAlignment(Pos.CENTER);
        gameControls.setSpacing(20);
        gameControls.setStyle("-fx-padding: 10;");

        hbox.getChildren().add(gameControls);
        // Skapa scenen och visa den
        Scene scene = new Scene(hbox);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    // Skapa spelplanen med etiketter och rutor
    private VBox createBoard(int numRows, int numCols, double cellSize, String boardName) {
        VBox boardVBox = new VBox();
        boardVBox.setAlignment(Pos.CENTER);

        Text boardText = new Text(boardName);
        boardVBox.getChildren().add(boardText);
        // Skapa en GridPane för spelplanen
        GridPane gridPane = new GridPane();
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        // Lägg till bokstäver för raderna (A-J)
        for (int row = 0; row < numRows; row++) {
            Text labelText = new Text(String.valueOf((char) ('A' + row)));
            labelText.setStyle("-fx-font-size: 14; -fx-font-weight: bold;"); //Kollade på youtube
            gridPane.add(labelText, 0, row + 1);
        }
        // Lägg till siffror för kolumnerna (0-9)
        for (int col = 0; col < numCols; col++) {
            Text labelText = new Text(Integer.toString(col));
            labelText.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
            labelText.setTextOrigin(VPos.CENTER);
            labelText.setTextAlignment(TextAlignment.CENTER);
            labelText.setWrappingWidth(cellSize);
            gridPane.add(labelText, col + 1, 0);
        }
        // Lägg till rutor för varje cell på spelplanen
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                String cellName = String.valueOf((char) ('A' + row)) + col;
                Rectangle cell = new Rectangle(cellSize, cellSize);
                cell.setFill(Color.LIGHTCYAN);
                cell.setStroke(Color.BLACK);
                gridPane.add(cell, col + 1, row + 1);
                cell.setId(cellName);
            }
        }
        // Lägg till GridPane i VBox
        boardVBox.getChildren().add(gridPane);
        return boardVBox;
    }

    // Morten: Constructor
    public GameView(GameController gameController) {
        this.gameController = gameController;
    }

    // Dölj fiendens skepp på spelplanen
    private void hideEnemyShips(VBox enemyBoard) {
        GridPane enemyGrid = (GridPane) enemyBoard.getChildren().get(1);
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (enemyBoardBox[row][col]) {
                    Rectangle cell = new Rectangle(50, 50);
                    cell.setFill(Color.LIGHTCYAN);
                    cell.setStroke(Color.BLACK);
                    enemyGrid.add(cell, col + 1, row + 1);
                }
            }
        }
    }
    // Visa spelarens skepp på spelplanen
    private void displayPlayerShips(VBox playerBoard) {
        GridPane playerGrid = (GridPane) playerBoard.getChildren().get(1);
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (playerBoardBox[row][col]) {
                    Rectangle cell = new Rectangle(50, 50);
                    cell.setFill(Color.DARKGRAY);
                    cell.setStroke(Color.BLACK);
                    playerGrid.add(cell, col + 1, row + 1);
                }
            }
        }
    }
    // Morten: Markera en koordinat på spelplanen
    public void markCoordinate(VBox board, int x, int y) {
        Platform.runLater(() -> {
            GridPane playerGrid = (GridPane) playerBoard.getChildren().get(1);
            Rectangle cell = new Rectangle(CELLSIZE, CELLSIZE);
            cell.setFill(Color.DARKGRAY);
            cell.setStroke(Color.BLACK);
            playerGrid.add(cell, x + 1, y + 1);
        });
    };
  
    public void markShotOnPlayerboard(int x, int y, boolean hit){
        Platform.runLater(() -> {
            GridPane playerGrid = (GridPane)  playerBoard.getChildren().get(1);
            Button button = (Button) playerGrid.getChildren().stream().filter(node -> GridPane.getColumnIndex(node) == x + 1 && GridPane.getRowIndex(node) == y + 1).findFirst().orElse(null);
            if (button != null){
                if (hit){
                    //Träff markeras med röd färg
                    button.setStyle("-fx-background-color: red;");
                    button.setText("X");
                }else {
                    //Miss markeras med grå färg
                    button.setStyle("-fx-background-color: grey;");
                }
                // Inaktivera knappen så att den inte kan klickas igen
                button.setDisable(true);
            }
        });
    }
    public void markShotOnEnemyboard (int x, int y, boolean hit){
        GridPane enemyGrid = (GridPane) enemyBoard.getChildren().get(1);
        Button button = (Button) enemyGrid.getChildren().stream().filter(node -> GridPane.getColumnIndex(node) == x + 1 && GridPane.getRowIndex(node) == y + 1).findFirst().orElse(null);
        if (button !=null){
            if (hit){
                button.setStyle("-fx-background-color: red;");
                button.setText("X");
            }else {
                button.setStyle("-fx-background-color: grey;");
            }
            button.setDisable(true);
        }
    }

    public Slider getSlider() {
        return slider;
    }

    // Getter för spelarens spelplan
    public VBox getPlayerBoard() {
        return playerBoard;
    }
    // Getter för fiendens spelplan
    public VBox getEnemyBoard() {
        return enemyBoard;
    }
    // Avsluta spelet
    private void exitGame() {
        System.out.println("Game exited.");
        System.exit(0);
    }
}
