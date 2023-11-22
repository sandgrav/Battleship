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
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**Alexandros Saltsidis**/
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
    private Rectangle[][] playerCells = new Rectangle[NUMCOLS][NUMROWS];
    private Rectangle[][] enemyCells = new Rectangle[NUMCOLS][NUMROWS];
    private boolean gameStarted = false;
    private Ships ships;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Battleship");

        ships = new Ships();

        HBox hbox = new HBox(200);
        hbox.setAlignment(Pos.CENTER);
        // Skapa spelplaner för spelare och fiende
        playerBoard = createBoard(NUMROWS, NUMCOLS, CELLSIZE, playerCells, "Player Board");
        enemyBoard = createBoard(NUMROWS, NUMCOLS, CELLSIZE, enemyCells, "Enemy Board");
        // Gör fiendens spelplan hanterad och synlig
        enemyBoard.setManaged(true);
        enemyBoard.setVisible(true);

        hbox.getChildren().add(playerBoard);
        hbox.getChildren().add(enemyBoard);

        // Morten: Add slider for delay
        Label label = new Label("Delay");
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
/* Alexandros har lagt två knappar
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
        // Skapade en VBox för knapparna
        VBox gameControls = new VBox(label, slider, button1, button2);
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
    private VBox createBoard(int numRows, int numCols, double cellSize, Rectangle[][] cells, String boardName) {
        // Skapat en vertikal box (VBox) för att organisera komponenter vertikalt på gränssnittet.
        VBox boardVBox = new VBox();
        boardVBox.setAlignment(Pos.CENTER);

        Text boardText = new Text(boardName);
        boardVBox.getChildren().add(boardText);
        // Skapar en GridPane för att båtarna ska stå inom rutor.
        GridPane gridPane = new GridPane();
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        // Lägg till bokstäver för raderna (A-J)
        for (int row = 0; row < numRows; row++) {
            Text labelText = new Text(String.valueOf((char) ('A' + row)));
            // Ställer in stilattribut för textetiketten i JavaFX
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
                cells[row][col] = new Rectangle(cellSize, cellSize);
                cells[row][col].setFill(Color.LIGHTCYAN);
                cells[row][col].setStroke(Color.BLACK);
                gridPane.add(cells[row][col], col + 1, row + 1);
                cells[row][col].setId(cellName);
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
//Alexandros Saltsidis
    // Dölj fiendens skepp på spelplanen
    private void hideEnemyShips(VBox enemyBoard) {
        // Hämta GridPane för fiendens spelplan från VBox
        GridPane enemyGrid = (GridPane) enemyBoard.getChildren().get(1);
        // for loop igenom varje rad och kolumn på spelplanen
        for (int row = 0; row < 10; row++) {
            // Kontrollera om det finns ett skepp på den aktuella positionen
            for (int col = 0; col < 10; col++) {
                if (enemyBoardBox[row][col]) {
                    // Skapa en rektangel för att representera en cell
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
    public void markCoordinate(Rectangle[][] cells, int x, int y) {
        Platform.runLater(() -> {
            cells[x][y].setFill(Color.DARKGRAY);
            cells[x][y].setStroke(Color.BLACK);
        });
    };

    //FAHRI
    public void markShotOnBoard(int x, int y, boolean hit, Rectangle[][] cells) {
        Platform.runLater(() -> {
            //VBox boardGrid = isPlayerBoard ? playerBoard : enemyBoard;
//            Button button = findButton(boardGrid, x, y);

//            if (button != null) {
                if (hit) {
                    // Om träff, ändra knappens utseende
                    cells[x][y].setStyle("-fx-background-color: red;");
                    cells[x][y].setFill(Color.RED);
//                    cells[x][y].setText("X");
                } else {
                    // Om miss, ändra knappens utseende
                    cells[x][y].setStyle("-fx-background-color: blue;");
                    cells[x][y].setFill(Color.BLUE);
                }

                // Inaktivera knappen så att den inte kan klickas igen
//                button.setDisable(true);
//            }
        });
    }
    //FAHRI
    public Button findButton(VBox boardGrid, int x, int y) {
        return (Button) boardGrid.getChildren().stream()
                .filter(node -> {
                    Integer colIndex = GridPane.getColumnIndex(node);
                    Integer rowIndex = GridPane.getRowIndex(node);
                    return (colIndex != null && colIndex == x) && (rowIndex != null && rowIndex == y);
                })
                .findFirst()
                .orElse(null);
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

    public Rectangle[][] getPlayerCells() {
        return playerCells;
    }

    public Rectangle[][] getEnemyCells() {
        return enemyCells;
    }

    // Avsluta spelet
//    private void exitGame() {
//        System.out.println("Game exited.");
//        System.exit(0);
//    }
}
