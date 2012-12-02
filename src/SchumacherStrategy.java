
import java.util.Random;

/**
 * Michael Schumacher ist schon etwas aelter und verwirrt. Er repraesentiert
 * unsere Random Strategie.
 */
public class SchumacherStrategy implements FastCarStrategy{

    @Override
    public void performMove(Car a, Racetrack track) throws InterruptedException{
        //Perform some moves.
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(3);
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
                drivingDirection = Directions.FORWARD;
                break;
            default:
                drivingDirection = Directions.FORWARD;
        }

        try{
            track.moveTo(a, drivingDirection);
            a.setOrientation(newOrientation);
        }catch(OutOfRacetrackException e){
            System.out.println("OutOfRacetrackException");
        }
    }

}
