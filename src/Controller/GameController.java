package Controller;

import Model.*;
import View.GameView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** Written by Morten Sandgrav **/

public class GameController {
    GameView gameView;
    ConnectionType connectionType;
    boolean firstShot = true;
    String ipAdress;
    int port;
    Client client;
    Server server;
    PrintWriter writer;
    BufferedReader reader;
    Ships ships = new Ships();
    Shots shots = new Shots();
    int delay;
    char kod;
    Position position;
    private List<Ship> shipsList = new ArrayList<>();
    List<int[]> previousShots = new ArrayList<>();
    Runnable startClient = () -> {
        client = new Client(ipAdress, port) ;
        writer = client.getWriter();
        reader = client.getReader();

        delay = (int) gameView.getSlider().getValue();
        placeShips();
        //Skicka iväg skott
        kod = 'i';
        calculateRandomShot();
        SendShotToOpponent ();
        gameloop();
    };

    Runnable startServer = () -> {
        server = new Server(port);
        writer = server.getWriter();
        reader = server.getReader();

        delay = (int) gameView.getSlider().getValue();
        placeShips();
        gameloop();
    };

    private void gameloop() {
        //Loop
        while (!gameOver()) {
            //Motta skott
            receiveShotFromOpponent();
            // game over??
            //Markera skott
            markLastShotWithCode();
            //Hit/miss/sjunkit
            markShotInShips();
            // Delay for 1 to 5 seconds
            delayGame();
            //Beräkna skott
            calculateRandomShot();
            //Skicka iväg skott
            SendShotToOpponent ();
        }
    }

//Alexandros Saltsidis
    private boolean gameOver() {
        // Kontrollera om spelet är över genom att iterera över alla skepp
        for (Ship ship : ships.getShipsList()) {
            // Om ett skepp inte är sänkt, är spelet inte över
            if (!ship.isSunk()) {
                return false;
            }
        }
        // Om alla skepp är sänkta, är spelet över
        return true;
    }

    //Alexandros Saltsidis
    private void receiveShotFromOpponent() {
        try {
            // Läs in motståndarens skott från BufferedReader
            String receivedString = reader.readLine();

            // Kontrollera om meddelandet är giltigt
            if (receivedString != null && !receivedString.isEmpty()) {
                // Skriv ut att ett skott har mottagits från motståndaren
                System.out.println("Receive shot from opponent: " + receivedString);

                // Bearbeta motståndarens meddelande
                processOpponentMessage(receivedString);
            } else {
                // Skriv ut om ett tomt meddelande mottagits från motståndaren
                System.out.println("Don't receive shot from opponent.");
            }
        } catch (IOException e) {
            // Hantera eventuella fel vid läsning från motståndaren
            System.out.println(e.getMessage());
        }
    }

    //Alexandros Saltsidis
    private void processOpponentMessage(String opponentMessage) {
        if (opponentMessage.equalsIgnoreCase("game over")) {
            kod = 'g';
        } else {
            // Dela upp motståndarens meddelande i delar
            String[] parts = opponentMessage.split(" ");

            // Kontrollera om meddelandet har rätt format
            if (parts.length == 3) {
                // Extrahera kod och skott från meddelandet
                //String code = parts[0];
                kod = parts[0].charAt(0);
                String shot = parts[2];

                // Anropa metoden för att hantera motståndarens skott
                //handleOpponentShot(code, shot);
                handleOpponentShot(shot);
            } else {
                // Skriv ut om meddelandet har ogiltigt format
                System.out.println("Wrong " + opponentMessage);
            }
        }
    }

    //Alexandros Saltsidis
    private void handleOpponentShot(String shot) {
    //    private void handleOpponentShot(String code, String shot) {
        // Bearbeta motståndarens skott baserat på kod och skott mottagna
//        System.out.println("Opponent " + code + ", skott: " + shot);

        //Konvertera skott till position om det representerar koordinater, t.ex. "A1"
//        if (!code.equals("g")) {
            position = new Position(shot);
//        }
    }

    private void markLastShotWithCode() {
        // Markera föregående skott med mottagna kod
        // markera båda i model och UI
        switch (Character.toUpperCase(kod)) {
            case 'M':
                shots.markLastShotAMiss();
                gameView.markShotOnBoard(shots.getLastShotX(), shots.getLastShotY(), false, gameView.getEnemyCells());
                break;
            case 'H':
                shots.markLastShotAHit();
                gameView.markShotOnBoard(shots.getLastShotX(), shots.getLastShotY(), true, gameView.getEnemyCells());
                break;
            case 'S':
                shots.markLastShotSunk();
                gameView.markShotOnBoard(shots.getLastShotX(), shots.getLastShotY(), true, gameView.getEnemyCells());
                break;
        }
    }

    private boolean markShotInShips() {
        // Markera skott i Ships
        // markera båda i model och UI
        // Sätt kod till 'g' om alla skepp är borta
        //--------------------------------

        // Generera slumpmässig skottposition
        int[] shotCoordinates = generateRandomShot();
        // Loopa genom varje skepp på det aktuella spelbrädet (ships1)
        kod = ships.checkForShip(new Position(shotCoordinates[0], shotCoordinates[1]));
/*
/*
        for (Ship ship : shipsList) {

            // Ifall skotten träffar de aktuella skeppet-->
            if (ship.checkForShip(new Position(shotCoordinates[0], shotCoordinates[1]))) {

                // Ta bort sänkt skepp från listan
                shipsList.remove(ship);
//                shots.markLastShotAHit(shotCoordinates[0], shotCoordinates[1]);


                // Markera skottet på spelbrädet i UI, indikerar att skeppet träffats
                gameView.markShotOnBoard(shotCoordinates[0], shotCoordinates[1], true, gameView.getPlayerBoard());

                // Om alla skepp är sänkta på det aktuella brädet
                if (shipsList.isEmpty()) {
                    //Sätt kod til 'g'
                    kod = 'g';
                }else {
                    kod = 'H';
                }

                return true; // Skottet träffade ett skepp

            }
        }
        // Om skottet inte träffade något skepp -->
        // Markera skottet som en miss i Shots-klassen
        shots.markLastShotAMiss(shotCoordinates[0], shotCoordinates[1]);

//        shots.markLastShotAMiss(shotCoordinates[0], shotCoordinates[1]);

        // Markera skottet på spelbrädet i UI som ett missat skott
        gameView.markShotOnBoard(shotCoordinates[0], shotCoordinates[1], false, gameView.getPlayerCells());
        kod = 'M';
*/
        if (kod == 'h' || kod == 's') {
            gameView.markShotOnBoard(shotCoordinates[0], shotCoordinates[1], true, gameView.getPlayerCells());
        } else {
            gameView.markShotOnBoard(shotCoordinates[0], shotCoordinates[1], false, gameView.getPlayerCells());
        }
        // Inget skepp träffat
        return false;

    }

    // Morten Sandgrav
    // Delay for 1 to 5 seconds
    private void delayGame() {
        try {
            Thread.sleep(delay * 1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    private void calculateRandomShot() {
        //Beräkna random skott och kolla om man tidigare har skjutit där
        //----------------------------------------------

        // Variabel för att lagra det nya slumpmässiga skottet
        int[] newShot;

        // Generera ett slumpmässigt skott och kontrollera om det redan har tagits
        do {
            newShot = generateRandomShot();
        } while (hasShotAlreadyBeenTaken(newShot));

        position = new Position(newShot[0], newShot[1]);

        // Skriv ut koordinaterna för det nya skottet till konsolen
        System.out.println("Nytt skott: (" + newShot[0] + ", " + newShot[1] + ")");

        // Anropa metoden i Shots-klassen för att markera skottet
        shots.markShot(newShot[0], newShot[1]);

        // Lägg till det nya skottet i listan över tidigare skott
        previousShots.add(newShot);
    }
    private boolean hasShotAlreadyBeenTaken(int[] shot) {

        // Loopa genom varje tidigare skott i listan previousShots
        for (int[] previousShot : previousShots) {

            // Jämför koordinaterna för det nuvarande och tidigare skott med det givna skottet
            if (previousShot[0] == shot[0] && previousShot[1] == shot[1]) {
                return true;
            }

        }
        return false;
    }

    private String calculateRandomShotText() {
        // Genererar en array med slumpmässiga koordinater för skottet
        int[] shot = generateRandomShot();

        // Skapar en sträng med den slumpmässiga skotttexten
        // (shot[0] + 1) används för att justera från nollindexering till ettindexering
        // yLabels[shot[1]] används för att hämta den associerade etiketten från yLabels-arrayen
        return "" + (shot[0] + 1) + yLabels[shot[1]];
    }

    private void SendShotToOpponent() {
        try {
            // Ange rätt kod för skott här
//            String code = "place";

            // Använd din logik för att generera skott här
//            String shot = calculateRandomShotText();

            // Skapa ett meddelande som innehåller skottkoden och skottet
//            String shotMessage = code + " shot " + shot;
            int temp = 'A' + position.getX();
            String shotMessage = kod + " shot " + (char)(temp) + position.getY();

            // Skicka skottmeddelandet till motståndaren
            sendShotMessage(shotMessage);
        } catch (Exception e) {
            // Hantera eventuella fel som kan uppstå vid skickande av skott
            System.err.println(e.getMessage());
        }
    }

    private void sendShotMessage(String message) {
        try {
            // Skriver meddelandet till Writer
            writer.println(message);

            // (flush) PrintWriter för att säkerställa att meddelandet skickas direkt
//            writer.flush();
        } catch (Exception e) {
            // Om ett undantag uppstår, skriv ut undantagsmeddelandet till konsolen
            System.out.println(e.getMessage());
        }
    }

    // Amro
    private static final char[] yLabels = {'A','B','C','D','E','F','G','H','I','J'};

/*  Amro
    public String[][] shotLogic(String[][] board,int[] shot){

        int x = shot[0] - 1;
        int y = shot[1] - 1;

        String shotText = "" + shot[0] + yLabels[shot[1]];


        if (connectionType == ConnectionType.SERVER){
            String goal = board[x][y];

            switch (goal){
                case " ":
                    System.out.println("m shot " + shotText);
                    Dialog.showMessage("m shot " + shotText);
                board[x][y] = "M";
                break;
                case "S":

                    Ship ship = ships.getShipsList().stream().filter(s -> s.matchPosition(x,y)).findFirst().get();

                    ship.hitShip();

                    if (ship.getSize() > 0){
                        System.out.println("h shot " + shotText );
                        Dialog.showMessage(" h shot " + shotText);
                        board[x][y] = "H";

                    }
                    else {
                        System.out.println("s shot "+ shotText);
                        Dialog.showMessage("s shot "+ shotText);
                        board[x][y] = "H";
                    }
                    break;
                default:
                    System.out.println("wrong");
                    break;
            }
        }
        else {
            if (firstShot){
                System.out.println("i shot " + shotText);
                Dialog.showMessage("i shot " + shotText);
            }
        }


        return board;

    }*/

        public static int[] generateRandomShot () {
            Random random = new Random();
            int x = random.nextInt(10); // Slumpmässig x-koordinat för skott
            int y = random.nextInt(10); // Slumpmässig y-koordinat för skott
            int[] shot = {x, y};
            return shot;
        }

    private void placeShips() {
        Random random = new Random();
        int x;
        int y;
        Direction direction;

        int[] shipsLength = new int[]{5, 4, 4, 3, 3, 3, 2, 2, 2, 2};
        for (int ship: shipsLength) {
            do {
                x = random.nextInt(10);
                y = random.nextInt(10);
                direction = Direction.values()[random.nextInt(2)];
            } while (!ships.placeShip(x, y, ship, direction));
            System.out.println("Ship placed at (" + x + ", " + y + "), length " + ship + " and riktning " + direction);
            for (int i = 0; i < ship; i++) {
                gameView.markCoordinate(gameView.getPlayerCells(), x, y);
                if (direction == Direction.HORISONTAL) {
                    x++;
                } else {
                    y++;
                }
            }
        }
    };

    public GameView getGameView() {
        return gameView;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public ConnectionType getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(ConnectionType connectionType) {
        this.connectionType = connectionType;
    }

    public String getIpAdress() {
        return ipAdress;
    }

    public void setIpAdress(String ipAdress) {
        this.ipAdress = ipAdress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Runnable getStartClient() {
        return startClient;
    }

    public Runnable getStartServer() {
        return startServer;
    }
}

