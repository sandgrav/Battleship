package Model;

//Björn Samuelsson
public class Shots extends Board {
    int lastShotX = -1;
    int lastShotY = -1;
    public void markShot(int x, int y) {
        lastShotX = x;
        lastShotY = y;
        board[x][y] = "X";
    }
    //Björn Samuelsson
    public void markLastShotAHit() {
        board[lastShotX][lastShotY] = "H";
    }
    //Björn Samuelsson
    public void markLastShotSunk() {
        board[lastShotX][lastShotY] = "S";
    }
    //Björn Samuelsson
    public void markLastShotAMiss() {
        board[lastShotX][lastShotY] = "M";
    }
    //Björn Samuelsson
    public int getLastShotX() {
        return lastShotX;
    }
    //Björn Samuelsson
    public int getLastShotY() {
        return lastShotY;
    }
}
