
import java.util.Random;

/*
 * Fahre immer im Kreis.
 */
public class CircleStrategy implements FastCarStrategy{
    private Directions oldDrivingDirection;
    private Boolean circleClockwise;

    public CircleStrategy(){
        this.oldDrivingDirection = Directions.FORWARD;
        this.circleClockwise = new Random().nextBoolean();
    }

    /**
     * VB: Car a muss sich auf dem Racetrack a befinden.
     * VB: Car muss FastCar sein
     */
    @Override
    public void performMove(Car a, Racetrack track) throws InterruptedException{
        //Perform some moves.
        Directions drivingDirection;

        if(circleClockwise){
            drivingDirection = Directions.RIGHTFORWARD;
        }else{
            drivingDirection = Directions.LEFTFORWARD;
        }

        if(oldDrivingDirection == drivingDirection){
            drivingDirection = Directions.FORWARD;
        }

        try{
            track.moveTo(a, drivingDirection);
        }catch(OutOfRacetrackException e){
            
        }
    }

}
