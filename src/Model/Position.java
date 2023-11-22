package Model;


public class Position {
    private int x;
    private int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

//    Morten Sandgrav och Alexandros Saltsidis
  public Position(String string){
        char[] chars = string.toCharArray();
        if (string.length() == 2) {
            this.x = Character.toUpperCase(chars[0]) - 'A';
            this.y = chars[1] - '0';
        } else {
            // Hantera ogiltigt format för positionString
            throw new IllegalArgumentException("Invalid position string format: " + string);
        }
    }

    // Morten
    public Position addPosition(Position position) {
        return new Position(this.x + position.x, this.y + position.y);
    }

    // Morten
    public Position subtractPosition(Position position) {
        return new Position(this.x - position.x, this.y - position.y);
    }

    // Morten
    public boolean equals(Position position) {
        return this.x == position.x && this.y == position.y;
    }

    // Amro
    public int getX(){
        return  this.x;
    }

    // Amro
    public int getY(){
        return this.y;
    }
}
