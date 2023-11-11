package Controller;

import Model.Direction;
import Model.Ship;
import Model.Ships;
import Model.Shots;
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
    Runnable startClient = () -> {
        client = new Client(ipAdress, port) ;
        writer = client.getWriter();
        reader = client.getReader();

//        try {
            placeShips();
            // Koden som ska köras efter klienten är startat
/*
        } catch (IOException e) {
            e.getMessage();
        }
*/
    };

    Runnable startServer = () -> {
        String string;
        int j = 1;
        server = new Server(port);
//        try {
            placeShips();
            // Koden som ska köras efter klienten är startat
/*
        } catch (IOException e) {
            e.getMessage();
        }
*/
    };


    private static final char[] yLabels = {'A','B','C','D','E','F','G','H','I','J'};


    //Om man är server
    //Starter loop

    //Om man är client
    //Beräkna skott
    //Skicka iväg skott
    //Starter loop

    //Loop
    //Motta skott
    //Markera skott
    //Hit/miss
    //Beräkna skott
    //Skicka iväg skott

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

