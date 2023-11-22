package Model;

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
                    sunk = sunk && hits[j];
                }
                return true;
            }
        }
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
}
