/*
 * Strategie: Fahre solange gerade bis eine Wand kommt. An dieser Wand drehe
 * nach links und fahre wieder gerade und so weiter.
 */
public class DumbStrategy implements FastCarStrategy, MoveableCarStrategy{
    Boolean wallAhead;

    public DumbStrategy() {
        this.wallAhead = false;
    }

    /**
     * VB: Car a muss sich auf dem Racetrack track befinden.
     * VB: Car mus Moveable oder FastCar sein
     */
    @Override
    public void performMove(Car a, Racetrack track) throws InterruptedException{
        Directions drivingDirection = Directions.FORWARD;
        Orientations newOrientation = a.getOrientation();

        //FastCar hat in dieser Situation keine Ausweichmoeglichkeit,
        //MoveableCar aber schon. Es kann nach links fahren und dadurch
        //dem Stillstand entgehen.
        if(wallAhead && a.canDriveTo(Directions.LEFT)){
            drivingDirection = Directions.LEFT;
            newOrientation = newOrientation.turnLeft();
            wallAhead = false;
        }
        
        try{
            track.moveTo(a, drivingDirection);
            a.setOrientation(newOrientation);
        }catch(OutOfRacetrackException e){
            wallAhead = true;
        }
       
    }

}
