package Controller;

import Model.*;
import View.Dialog;
import View.GameView;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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
        System.out.println(delay);
        placeShips();
        gameloop();

    };

    private void gameloop() {
        //Loop
        while (true) {
            //Motta skott
            receiveShotFromOpponent();
            // game over??
            if (kod == 'g') {
                break;
            }
            //Markera skott
            markLastShotWithCode();
            //Hit/miss/sjunkit
            markShotInShips();
            delayGame();
            //Beräkna skott
            calculateRandomShot();
            //Skicka iväg skott
            SendShotToOpponent ();
            // game over??
            if (kod == 'g') {
                break;
            }
        }
    }

    private boolean gameOver() {
        // Kolla om alla egna skepp är borta
        return false;
    }

    private void receiveShotFromOpponent() {
        // läsa sträng från motståndaren och dela upp i kod och skott
        System.out.println("receiveShotFromOpponent");
        String string;

        try {
            string = reader.readLine();
            System.out.println(string);
            kod = string.charAt(0);
            if (kod != 'g') {
                position = new Position(string.substring(string.length()-2));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void markLastShotWithCode() {
        // Markera föregående skott med mottagna kod
        // markera båda i model och UI
        System.out.println("markLastShotWithCode");
        switch (kod) {
            case 'h':
                shots.markLastShotAHit();
                break;
            case 'm':
                shots.markLastShotAMiss();
                break;
            case 's':
                shots.markLastShotShipSunk();
                break;
            default:
                // Something went wrong
        }
    }

    private void markShotInShips() {
        // Markera skott i Ships
        // markera båda i model och UI
        // Sätt kod till 'g' om alla skepp är borta
        System.out.println("markShotInShips");
        boolean result = true;
        kod = ships.checkForShip(position);
        if (kod == 's') {
            for (Ship ship: ships.getShipsList()) {
                result = result && ship.isSunk();
            }
            if (result) {
                kod = 'g';
            }
        }
    }

    private void delayGame() {
        try {
            Thread.sleep(delay * 1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    private void calculateRandomShot() {
        // Beräkna random skott och kolla om man tidigare har skjutit där
        System.out.println("calculateRandomShot");
        Random random = new Random();
        int x;
        int y;
        do {
            x = random.nextInt(10);
            y = random.nextInt(10);
        } while (!shots.isEmpty(x,y));
        shots.markShot(x,y);
        position = new Position(x, y);
    }

    private void SendShotToOpponent() {
        // skicka sträng till understander med kod och skott
        // Om kod är g, skicka "game over"
        System.out.println("SendShotToOpponent");
        String string;

        if (kod == 'g') {
            string = "game over";
        } else {
            string = kod + " shot " + (char)('a' + position.getX()) + position.getY();
        }
        System.out.println(string);
        writer.println(string);
    }

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
                gameView.markCoordinate(gameView.getPlayerBoard(), x, y);
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

