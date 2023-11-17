package Model;

import java.util.Arrays;
import java.util.Dictionary;

public class Ship {
    Position[] positions;
    boolean[] hits;
    boolean sunk = false;

    public boolean checkForShip(Position position) {
        for (int i = 0; i < positions.length - 1; i++) {
            if (positions[i].equals(position)) {
                hits[i] = true;
                sunk = true;
                for (int j = 0; j < hits.length - 1; j++) {
                    sunk = sunk && hits[i];
                }
                return true;
            }
        }

  /* Alexandros_GameController
        else {
            for (int i = this.position.getY(); i < this.position.getY() + this.size; i++){
                this.allPositions[this.position.getX()][i] = true;
            }
        }
        
    }


    private Position position;
    private int size;
    private int hits;
    private Direction direction;

    
    private boolean[][] allPositions;


    public Position getPosition(){
        return this.position;
*/
        return false;

    }

    public Ship(Position[] positions) {
        this.positions = positions;
        for (boolean b : hits = new boolean[this.positions.length]) {
            b = false;
        }
    }

    public boolean isSunk() {
        return sunk;
    }

    public boolean isSunk() {
        return hits >= size;
    }
}
