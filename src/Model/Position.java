package Model;

public class Position {

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }


    private int x;
    private int y;

//    Alexandros Saltsidis
public Position(String positionString) {
    // Antag att positionString har formatet "XY" där X är en bokstav och Y är ett nummer
    if (positionString.length() == 2) {
        // Konvertera första tecknet (X) till versaler och subtrahera 'A' för att få ett numeriskt värde
        this.x = Character.toUpperCase(positionString.charAt(0)) - 'A';

        // Extrahera andra tecknet och konvertera det till ett heltal, subtrahera 1 för att få det numeriska värdet
        this.y = Integer.parseInt(positionString.substring(1)) - 1;
    } else {
        // Hantera ogiltigt format för positionString
            throw new IllegalArgumentException("Invalid position string format: " + positionString);
        }
    }

    public int getX(){
        return  this.x;
    }

    public int getY(){
        return this.y;
    }
}
