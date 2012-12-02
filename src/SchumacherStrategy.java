
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



    @Override
    public void performMove(Car a, Racetrack track) throws InterruptedException{
        //Perform some moves.
        int randomInt = randomGenerator.nextInt(5);
        Orientations newOrientation = a.getOrientation();

        Directions drivingDirection;

        switch(randomInt){
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
