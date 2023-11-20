package Model;

/** Written by Morten Sandgrav **/

public class Shots extends Board {
    int lastShotX = -1;
    int lastShotY = -1;
    public void markShot(int x, int y) {
        lastShotX = x;
        lastShotY = y;
        board[x][y] = "X";
    }
    public void markLastShotAHit() {
        board[lastShotX][lastShotY] = "H";
    }

    public void markLastShotSunk() {
        board[lastShotX][lastShotY] = "S";
    }

    public void markLastShotAMiss() {
        board[lastShotX][lastShotY] = "M";
    }

    public int getLastShotX() {
        return lastShotX;
    }

    public int getLastShotY() {
        return lastShotY;
    }
}
