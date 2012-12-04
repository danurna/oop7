
import java.util.Random;

/**
 * Michael Schumacher ist schon etwas aelter und verwirrt. Er repraesentiert
 * unsere Random Strategie.
 */
public class SchumacherStrategy implements MoveableCarStrategy{
    private Random randomGenerator;

    public SchumacherStrategy() {
        this.randomGenerator = new Random(1337);
    }

    /**
     * VB: Car a muss sich auf dem Racetrack track befinden.
     * VB: Car muss Moveable Car sein
     */
    @Override
    public void performMove(Car a, Racetrack track) throws InterruptedException{
        //Perform some moves.
        Directions drivingDirection;

        switch(randomGenerator.nextInt(5)){
            case 0:
                drivingDirection = Directions.LEFTFORWARD;
                break;
            case 1:
                drivingDirection = Directions.RIGHTFORWARD;
                break;
            case 2:
                drivingDirection = Directions.LEFT;
                break;
            case 3:
                drivingDirection = Directions.RIGHT;
                break;
            case 4:
                drivingDirection = Directions.FORWARD;
                break;
            default:
                throw new RuntimeException("Unreachable");
        }

        try{
            track.moveTo(a, drivingDirection);
        }catch(OutOfRacetrackException e){
            
        }
    }

}
