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
