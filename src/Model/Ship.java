package Model;

import java.util.Dictionary;

public class Ship {

    public Ship(int[] start, int size, Direction direction){
        this.position = new Position(start[0],start[1]);
        this.size = size;
        this.direction = direction;

        this.allPositions = new boolean[10][10];
        
        
        if (direction == Direction.HORISONTAL){
            for (int i = this.position.getX(); i < this.position.getX() + this.size;i++){
                this.allPositions[i][this.position.getY()] = true;
            }
        }
        else {
            for (int i = this.position.getY(); i < this.position.getY() + this.size; i++){
                this.allPositions[this.position.getX()][i] = true;
            }
        }
        
    }


    private Position position;
    private int size;
    private Direction direction;
    
    private boolean[][] allPositions;


    public Position getPosition(){
        return this.position;
    }

    public boolean matchPosition(int x, int y){
        return this.allPositions[x][y];
    }


    public int getSize(){
        return  this.size;
    }

    public void hitShip(){
        this.size--;
    }



    public Direction getDirection(){
        return  this.direction;
    }

    public void setDirection(Direction direction){
        this.direction = direction;
    }
}
