package Model;

/** Written by Morten Sandgrav **/

public class Ships extends Board {
    public boolean placeShip(int startX, int startY, int length, Direction direction) {
        int tempX = startX;
        int tempY = startY;
        int tempLength = length + 1;

        if (direction == Direction.HORISONTAL) {
            tempX = startX - 1;
            if ((startX + length) < 9) {
                tempLength = length + 2;
            } else if ((startX + length) > 9) {
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
            tempY = startY - 1;
            if ((startY + length) < 9) {
                tempLength = length + 2;
            } else if ((startY + length) > 9) {
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
