
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

        Directions drivingDirection;

        switch(randomInt){
            case 0:
                drivingDirection = Directions.LEFTFORWARD;
                break;
            case 1:
                drivingDirection = Directions.RIGHTFORWARD;
                break;
            case 2:
                drivingDirection = Directions.FORWARD;
                break;
            default:
                drivingDirection = Directions.FORWARD;
        }

        try{
            track.moveTo(a, drivingDirection);
        }catch(OutOfRacetrackException e){
            System.out.println("OutOfRacetrackException");
        }
    }

}
