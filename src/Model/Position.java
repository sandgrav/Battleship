package Model;

import javafx.geometry.Pos;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Position(String string){
        char[] chars = string.toCharArray();
        this.x = chars[0] - 'a';
        this.y = chars[1] - '0';
    }

    public Position addPosition(Position position) {
        return new Position(this.x + position.x, this.y + position.y);
    }

    public Position subtractPosition(Position position) {
        return new Position(this.x - position.x, this.y - position.y);
    }

    public boolean equals(Position position) {
        return this.x == position.x && this.y == position.y;
    }

    public int getX(){
        return  this.x;
    }

    public int getY(){
        return this.y;
    }
}
