package Model;

/** Written by Morten Sandgrav **/

public enum Direction {
    HORISONTAL(0),
    VERTICAL(1);

    private final int value;

    Direction(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
