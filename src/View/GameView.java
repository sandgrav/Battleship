package View;

import Model.Ships;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.util.Random;
//Alexandros Saltsidis
public class GameView extends Application {
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

        int numRows = 10;
        int numCols = 10;
        int cellSize = 50;

        VBox playerBoard = createBoard(numRows, numCols, cellSize, "Player Board");
        VBox enemyBoard = createBoard(numRows, numCols, cellSize, "Enemy Board");
        enemyBoard.setManaged(true);
        enemyBoard.setVisible(true);

        hbox.getChildren().add(playerBoard);
        hbox.getChildren().add(enemyBoard);

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
        gameControls.setAlignment(Pos.CENTER);
        gameControls.setSpacing(20);
        gameControls.setStyle("-fx-padding: 10;");

        hbox.getChildren().add(gameControls);

        Scene scene = new Scene(hbox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createBoard(int numRows, int numCols, double cellSize, String boardName) {
        VBox boardVBox = new VBox();
        boardVBox.setAlignment(Pos.CENTER);

        Text boardText = new Text(boardName);
        boardVBox.getChildren().add(boardText);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        for (int row = 0; row < numRows; row++) {
            Text labelText = new Text(String.valueOf((char) ('A' + row)));
            labelText.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
            gridPane.add(labelText, 0, row + 1);
        }
        for (int col = 0; col < numCols; col++) {
            Text labelText = new Text(Integer.toString(col));
            labelText.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
            labelText.setTextOrigin(VPos.CENTER);
            labelText.setTextAlignment(TextAlignment.CENTER);
            labelText.setWrappingWidth(cellSize);
            gridPane.add(labelText, col + 1, 0);
        }
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
        boardVBox.getChildren().add(gridPane);
        return boardVBox;
    }

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

    private void exitGame() {
        System.out.println("Game exited.");
        System.exit(0);
    }
}
