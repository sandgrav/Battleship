package Model;

/** Written by Morten Sandgrav **/

public class Ships extends Board {
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
//Alexandros Saltsidis
public class Ships {
    private int[] playerShipSizes;
    private int[] enemyShipSizes;

    public Ships() {
        playerShipSizes = new int[]{5, 4, 4, 3, 3, 3, 2, 2, 2, 2};
        enemyShipSizes = new int[]{5, 4, 4, 3, 3, 3, 2, 2, 2, 2};
    }

    public int[] getPlayerShipSizes() {
        return playerShipSizes;
    }

    public int[] getEnemyShipSizes() {
        return enemyShipSizes;
    }

    public void placeShipsRandomly(boolean[][] board, int[] shipSizes, Random random) {
        for (int shipSize : shipSizes) {
            boolean shipPlaced = false;

            while (!shipPlaced) {
                int x = random.nextInt(10);
                int y = random.nextInt(10);
                boolean vertical = random.nextBoolean();

                if (canPlaceShip(x, y, vertical, shipSize, board)) {
                    if (vertical) {
                        for (int i = y; i < y + shipSize; i++) {
                            board[i][x] = true;
                        }
                    } else {
                        for (int i = x; i < x + shipSize; i++) {
                            board[y][i] = true;
                        }
                    }
                    shipPlaced = true;
                }
            }
        }
    }
    public boolean canPlaceShip(int x, int y, boolean vertical, int size, boolean[][] board) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

        for (int i = 0; i < size; i++) {
            if (x < 0 || x >= 10 || y < 0 || y >= 10 || board[y][x]) {
                return false;
            }

            for (int[] dir : directions) {
                int adjX = x + dir[0];
                int adjY = y + dir[1];

                if (adjX >= 0 && adjX < 10 && adjY >= 0 && adjY < 10 && board[adjY][adjX]) {
                    return false;
                }
            }
            if (vertical) {
                y++;
            } else {
                x++;
            }
        }
        return true;
    }
}
*/
