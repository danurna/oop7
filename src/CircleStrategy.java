
import java.util.Random;

/*
 * Fahre immer im Kreis.
 */
public class CircleStrategy implements FastCarStrategy{
    Directions oldDrivingDirection;
    Boolean circleClockwise;

    public CircleStrategy(){
        this.oldDrivingDirection = Directions.FORWARD;
        this.circleClockwise = new Random().nextBoolean();
    }
    
    @Override
    public void performMove(Car a, Racetrack track) throws InterruptedException{
        //Perform some moves.
        Orientations newOrientation = a.getOrientation();

        Directions drivingDirection;

        if(circleClockwise){
            drivingDirection = Directions.RIGHTFORWARD;
            newOrientation = newOrientation.turnRight();
        }else{
            drivingDirection = Directions.LEFTFORWARD;
            newOrientation = newOrientation.turnLeft();
        }

        if(oldDrivingDirection == drivingDirection){
            drivingDirection = Directions.FORWARD;
        }

        try{
            track.moveTo(a, drivingDirection);
            a.setOrientation(newOrientation);
        }catch(OutOfRacetrackException e){
            //System.out.println("OutOfRacetrackException");
        }
    }

}
