
import java.util.Random;

/*
 * Schnelles Auto, dass andauernd Richtung wechselt und so faehrt, dass es
 * moeglichst immer weiter fahren kann.
 */
public class ZigZagStrategy implements FastCarStrategy{
    Directions oldDrivingDirection;

    public ZigZagStrategy(){
        this.oldDrivingDirection = Directions.LEFTFORWARD;
    }

    /**
     * VB: Car a muss sich auf dem Racetrack a befinden.
     */
    @Override
    public void performMove(Car a, Racetrack track) throws InterruptedException{
        //Perform some moves.
        Orientations newOrientation = a.getOrientation();
        Directions drivingDirection = oldDrivingDirection;

        if(oldDrivingDirection == Directions.LEFTFORWARD){
            if(!wallAheadAfterStep(a, Directions.RIGHTFORWARD, track)){
                drivingDirection = Directions.RIGHTFORWARD;
            }
        }else{
            if(!wallAheadAfterStep(a, Directions.LEFTFORWARD, track)){
                drivingDirection = Directions.LEFTFORWARD;
            }
        }

        if(drivingDirection == Directions.LEFTFORWARD){
            newOrientation = newOrientation.turnLeft();
        }else{
            newOrientation = newOrientation.turnRight();
        }

        //Speichere Direction fuer naechsten Aufruf.
        oldDrivingDirection = drivingDirection;

        try{
            track.moveTo(a, drivingDirection);
            a.setOrientation(newOrientation);
        }catch(OutOfRacetrackException e){
            
        }

    }

    /**
     * Ueberprueft, ob naechster Schritt zu einer "Wand vorraus"-Situation
     * fuehren wuerde.
     *
     * @param a - Auto, mit dem die Ueberpruefung stattfinden soll.
     * @param drivingDirection - Vorgeschlagene Richtung.
     * @param track - Racetrack, auf dem die Ueberpruefung stattfinden soll.
     *
     * @return boolean, ob eine "Wand vorraus"-Situation entsteht.
     */
    private boolean wallAheadAfterStep(Car a, Directions drivingDirection, Racetrack track){
        Orientations orientationAfterStep = a.getOrientation();
        int x = a.getX();
        int y = a.getY();
        int dx = 0;
        int dy = 0;
        int tmp = 0;

        //Berechnung mit Annahme, dass Orientation EAST ist.
        switch(drivingDirection){
            case FORWARD:
                dx = 1;
                break;
            case LEFTFORWARD:
                dx = 1; dy = -1;
                orientationAfterStep = orientationAfterStep.turnLeft();
                break;
            case RIGHTFORWARD:
                dx = 1; dy = 1;
                orientationAfterStep = orientationAfterStep.turnRight();
                break;
            default:
                throw new RuntimeException("Unreachable");
        }

        //An die echte Orientation des Autos anpassen.
        switch (a.getOrientation()) {
            case EAST:
                break;
            case WEST:
                dx = -dx;
                dy = -dy;
                break;
            case NORTH:
                tmp = dx;
                dx = dy;
                dy = -tmp;
                break;
            case SOUTH:
                tmp = dx;
                dx = -dy;
                dy = tmp;
                break;
        }

        //Einen Forward Step simulieren
        switch (orientationAfterStep) {
            case EAST:
                dx = dx + 1; //FOWARD
                break;
            case WEST:
                dx = dx - 1; //FOWARD
                break;
            case NORTH:
                dy = dy - 1; //FOWARD
                break;
            case SOUTH:
                dy = dy + 1; //FOWARD
                break;
        }

        x = x + dx;
        y = y + dy;

        return !track.inRange(x, y);
    }

}
