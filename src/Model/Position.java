package Model;

public class Position {

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }


    private int x;
    private int y;


    public int getX(){
        return  this.x;
    }

    public int getY(){
        return this.y;
    }
}
