/**
 * Beweglicheres Auto.
 * Invariante: Ist langsamer als FastCar
 */
public class MoveableCar extends Car{
    MoveableCarStrategy strategy;

    public MoveableCar(int x, int y, Orientations startOrientation, String carName){
        super(x, y, startOrientation, carName);
        this.milisecondsToWait = 200;
        //Standardstrategie ist die dumme Fahrweise.
        this.strategy = new DumbStrategy();

    }

    public MoveableCar(Orientations startOrientation, String carName){
        this(0, 0, startOrientation, carName);
    }

    @Override
    protected boolean canDriveTo(Directions direction){
        return (direction == Directions.LEFT ||
                direction == Directions.LEFTFORWARD ||
                direction == Directions.FORWARD ||
                direction == Directions.RIGHTFORWARD ||
                direction == Directions.RIGHT );
    }
    
    @Override
    protected void drive(){
        this.strategy.performMove(this, currentRacetrack);
        //System.out.println(currentRacetrack.debugString());
        System.out.println(this.getScore());
    }



}
