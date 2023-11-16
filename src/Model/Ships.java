package Model;

import java.util.ArrayList;
import java.util.List;

/** Written by Morten Sandgrav **/

public class Ships extends Board {

    private List<Ship> shipsList;
//Alexandros Saltsidis
    public Ships() {
        this.shipsList = new ArrayList<>();
    }

    public List<Ship> getShipsList(){
        return this.shipsList;
    }

    public boolean placeShip(int startX, int startY, int length, Direction direction) {
        int tempX = startX;
        int tempY = startY;
        int tempLength = length;

        if (direction == Direction.HORISONTAL) {
            if (tempX > 0) {
                tempX--;
                tempLength++;
            }
            if ((tempX + tempLength) < 9) {
                tempLength++;
            } else if ((tempX + tempLength) > 9) {
                return false;
            }
            for (int i = 0; i < tempLength; i++) {
                if (!(isEmpty(tempX + i, tempY - 1) && isEmpty(tempX + i, tempY) && isEmpty(tempX + i, tempY + 1))) {
                    return false;
                }
            }
            for (int i = 0; i < length; i++) {
                board[startX + i][startY] = "S";
            }
            this.shipsList.add(new Ship(new int[]{startX,startY},length,direction));
        } else {
            if (tempY > 0){
                tempY--;
                tempLength++;
            }
            if ((tempY + tempLength) < 9) {
                tempLength++;
            } else if ((tempY + tempLength) > 9) {
                return false;
            }
            for (int i = 0; i < tempLength; i++) {
                if (!(isEmpty(tempX - 1, tempY + i) && isEmpty(tempX, tempY + i) && isEmpty(tempX + 1, tempY + i))) {
                    return false;
                }
            }
            for (int i = 0; i < length; i++) {
                board[startX][startY + i] = "S";
            }
            this.shipsList.add(new Ship(new int[]{startX,startY},length,direction));
        }

        return true;
    }

    boolean isEmpty(int x, int y) {
        if (x < 0 || x > 9 || y < 0 || y > 9)
            return true;
        else
            return board[x][y].isEmpty();
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
