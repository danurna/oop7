/*
 * Fahre solange gerade aus bis das Auto gerade noch wenden kann. 
 */
public class DumbStrategy implements FastCarStrategy, MoveableCarStrategy{
    Boolean wallAhead;

    public DumbStrategy() {
        this.wallAhead = false;
    }


    
    @Override
    public void performMove(Car a, Racetrack track) throws InterruptedException{
        //Perform some moves.
        Directions drivingDirection = Directions.FORWARD;
        Orientations newOrientation = a.getOrientation();

        //FastCar hat in dieser Situation keine Ausweichmöglichkeit,
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
            System.out.println("OutOfRacetrackException");
            wallAhead = true;
        }
       
    }

}
