package Model;

import Controller.GameController;

import java.util.LinkedList;
import java.util.Queue;

public class Shot {
    final Position[] directions = new Position[]{new Position(-1, 0), new Position(0, 1), new Position(1, 0), new Position(0, -1)};
    int direction = 0;
    Position firstHit;
    Queue<Position> oneDirection = new LinkedList<>();
    Queue<Position> theOtherDirection = new LinkedList<>();

    public Position getNextShot(char code, Position position) {
        Position shot = null;
        int[] temp;

        switch (Character.toUpperCase(code)) {
            case 'M':
            case 'I':
                if (firstHit == null) {
                    // We are looking for a ship
                    temp = GameController.generateRandomShot();
                    shot = new Position(temp[0], temp[1]);
                } else {
                    // We have found a ship
                    if ((!oneDirection.isEmpty())) {
                        // We have finished the first part, lets get started on the second part
                        oneDirection.clear();
                        shot = theOtherDirection.poll();
                    } else if (!theOtherDirection.isEmpty()) {
                        // But we haven't found its direction yet
                        if (direction < 4) {
                            // Look in the next direction
                            shot = firstHit.addPosition(directions[direction++]);
                        } else {
                            // Weird a ship with length one??
                            System.out.println("Something went wrong!!");
                        }
                    } else {
                        // Shouldn't get here??
                        System.out.println("Shouldn't get here??");
                    }
                }
                break;
            case 'S':
                // The ship has been sunk, look for another ship
                direction = 0;
                firstHit = null;
                oneDirection.clear();
                theOtherDirection.clear();
                temp = GameController.generateRandomShot();
                shot = new Position(temp[0], temp[1]);
                break;
            case 'H':
                if (firstHit == null) {
                    // We found a ship
                    firstHit = position;
                    direction = 0;
                    shot = firstHit.addPosition(directions[direction++]);
                } else {
                    if (oneDirection.isEmpty() && theOtherDirection.isEmpty()) {
                        // We have a direction, add possible shots
                        Position direction = position.subtractPosition(firstHit);
                        Position tempPosition = position;
                        for (int i = 0; i < 3; i++) {
                            tempPosition = tempPosition.addPosition(direction);
                            oneDirection.add(tempPosition);
                        }
                        tempPosition = firstHit;
                        for (int i = 0; i < 3; i++) {
                            tempPosition = tempPosition.subtractPosition(direction);
                            theOtherDirection.add(tempPosition);
                        }
                    }
                    // Start shooting!!
                    if (!oneDirection.isEmpty()) {
                        shot = oneDirection.poll();
                    } else if (!theOtherDirection.isEmpty()) {
                        shot = theOtherDirection.poll();
                    } else {
                        // We shouldn't be here
                        System.out.println("We shouldn't be here!!");
                    }
                }
                break;
        }

        return shot;
    }
}
