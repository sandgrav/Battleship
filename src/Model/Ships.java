package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Written by Morten Sandgrav **/

public class Ships extends Board {

    private List<Ship> shipsList = new ArrayList<>();

    //Amro
    public List<Ship> getShipsList(){
        return this.shipsList;
    }

    public boolean placeShip(int startX, int startY, int length, Direction direction) {
        Position start = new Position(startX, startY);
        Position positionIncrement;
        Position positionRight;
        List<Position> positionsToCheck = new ArrayList<>();
        Position[] ship;

        if (direction == Direction.HORISONTAL) {
            positionIncrement = new Position(1, 0);
            positionRight = new Position(0, 1);
        } else {
            positionIncrement = new Position(0,1);
            positionRight = new Position(-1, 0);
        }

        Position tempPosition = start.subtractPosition(positionIncrement);
        positionsToCheck.add(tempPosition);
        positionsToCheck.add(tempPosition.subtractPosition(positionRight));
        positionsToCheck.add(tempPosition.addPosition(positionRight));
        tempPosition = start;
        for (int i = 0; i < length; i++) {
            if (0 <= tempPosition.getX() && tempPosition.getX() <= 9 && 0 <= tempPosition.getY() && tempPosition.getY() <= 9 && board[tempPosition.getX()][tempPosition.getY()].isEmpty()) {
                positionsToCheck.add(tempPosition.subtractPosition(positionRight));
                positionsToCheck.add(tempPosition.addPosition(positionRight));
                tempPosition = tempPosition.addPosition(positionIncrement);
            } else {
                return false;
            }
        }
        positionsToCheck.add(tempPosition);
        positionsToCheck.add(tempPosition.subtractPosition(positionRight));
        positionsToCheck.add(tempPosition.addPosition(positionRight));

        for (Position pos: positionsToCheck) {
            if (0 <= pos.getX() && pos.getX() <= 9 && 0 <= pos.getY() && pos.getY() <= 9) {
                if (!board[pos.getX()][pos.getY()].isEmpty()) {
                    return false;
                }
            }
        }

        ship = new Position[length];
        tempPosition = start;
        for (int i = 0; i < length; i++) {
            board[tempPosition.getX()][tempPosition.getY()] = "X";
            ship[i] = tempPosition;
            tempPosition = tempPosition.addPosition(positionIncrement);
        }

        // Amro
        shipsList.add(new Ship(ship));

        return true;
    }

    public char checkForShip(Position position) {
        for (Ship ship: shipsList) {
            if (ship.checkForShip(position)) {
                if (ship.isSunk()) {
                    return 's';
                } else {
                    return 'h';
                }
            }
        }

        return 'm';
    }
}

/*
import java.util.Random;

public class Ships {
    private int[] spelarensSkeppsStorlekar; // Array för att lagra skeppsstorlekar för spelaren och fienden
    private int[] fiendensSkeppsStorlekar;

    // Konstruktor som initialiserar skeppsstorlekarna
    public Ships() {
        spelarensSkeppsStorlekar = new int[]{5, 4, 4, 3, 3, 3, 2, 2, 2, 2};
        fiendensSkeppsStorlekar = new int[]{5, 4, 4, 3, 3, 3, 2, 2, 2, 2};
        }

    // Getter-metoder för att hämta skeppsstorlekarna
    public int[] getSpelarensSkeppsStorlekar() {
        return spelarensSkeppsStorlekar;
    }

    public int[] getFiendensSkeppsStorlekar() {
        return fiendensSkeppsStorlekar;
    }

    // Metod för att slumpmässigt placera skepp på spelplanen
    public void placeraSkeppSlumpmässigt(boolean[][] spelplan, int[] skeppsStorlekar, Random slump) {
        for (int skeppsStorlek : skeppsStorlekar) {
            boolean skeppPlacerat = false;

            // Försök placera skeppet tills det är framgångsrikt placerat
            while (!skeppPlacerat) {
                // Slumpmässigt välj position och orientering för skeppet
                int x = slump.nextInt(10);
                int y = slump.nextInt(10);
                boolean vertikalt = slump.nextBoolean();

                // Kontrollera om skeppet kan placeras på den valda positionen
                if (kanPlaceraSkepp(x, y, vertikalt, skeppsStorlek, spelplan)) {
                    // Placera skeppet på spelplanen
                    if (vertikalt) {
                        for (int i = y; i < y + skeppsStorlek; i++) {
                            spelplan[i][x] = true;
                        }
                    } else {
                        for (int i = x; i < x + skeppsStorlek; i++) {
                            spelplan[y][i] = true;
                        }
                    }
                    skeppPlacerat = true;
                }
            }
        }
    }

    // Metod för att kontrollera om ett skepp kan placeras på en given position
    public boolean kanPlaceraSkepp(int x, int y, boolean vertikalt, int storlek, boolean[][] spelplan) {
        // Array för att definiera olika riktningar runt en given position
        int[][] riktningar = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

        // Iterera genom varje position för det aktuella skeppet
        for (int i = 0; i < storlek; i++) {
            // Kontrollera om den aktuella positionen är inom spelplanens gränser och inte redan upptagen
            if (x < 0 || x >= 10 || y < 0 || y >= 10 || spelplan[y][x]) {
                return false; // Skeppet kan inte placeras här
            }

            // Kontrollera intilliggande positioner för att säkerställa att skepp inte placeras för nära varandra
            for (int[] riktning : riktningar) {
                int adjX = x + riktning[0];
                int adjY = y + riktning[1];

                if (adjX >= 0 && adjX < 10 && adjY >= 0 && adjY < 10 && spelplan[adjY][adjX]) {
                    return false; // Skeppet kan inte placeras här på grund av närhet till ett annat skepp
                }
            }

            // Flytta till nästa position baserat på skeppets orientering
            if (vertikalt) {
                y++;
            } else {
                x++;
            }
        }
        return true; // Skeppet kan placeras på den valda positionen
    }}}
}
*/
