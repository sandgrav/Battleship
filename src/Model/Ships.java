package Model;

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
