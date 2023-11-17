package Model;

import java.util.Arrays;

/** Written by Morten Sandgrav **/

public abstract class Board {
    String[][] board = new String[10][10];

    public Board() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.board[i][j] = "";
            }
        }
    }

    public boolean isEmpty(int x, int y) {
        if (x < 0 || x > 9 || y < 0 || y > 9)
            return true;
        else
            return board[x][y].isEmpty();
    }

}
