
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
     * VB: Car a muss sich auf dem Racetrack a befinden.
     */
    @Override
    public void performMove(Car a, Racetrack track) throws InterruptedException{
        //Perform some moves.
        Orientations newOrientation = a.getOrientation();
        Directions drivingDirection;

        switch(randomGenerator.nextInt(5)){
            case 0:
                drivingDirection = Directions.LEFTFORWARD;
                newOrientation = newOrientation.turnLeft();
                break;
            case 1:
                drivingDirection = Directions.RIGHTFORWARD;
                newOrientation = newOrientation.turnRight();
                break;
            case 2:
                drivingDirection = Directions.LEFT;
                newOrientation = newOrientation.turnLeft();
                break;
            case 3:
                drivingDirection = Directions.RIGHT;
                newOrientation = newOrientation.turnRight();
                break;
            case 4:
                drivingDirection = Directions.FORWARD;
                break;
            default:
                throw new RuntimeException("Unreachable");
        }

        try{
            track.moveTo(a, drivingDirection);
            a.setOrientation(newOrientation);
        }catch(OutOfRacetrackException e){
            
        }
    }

}
