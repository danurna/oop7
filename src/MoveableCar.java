/**
 * Beweglicheres Auto.
 * Invariante: Ist langsamer als FastCar.
 */
public class MoveableCar extends Car{
    MoveableCarStrategy strategy;

    public MoveableCar(int x, int y, Orientations startOrientation, String carName){
        super(x, y, startOrientation, carName);
        this.millisecondsToWait = 20;
        //Standardstrategie ist die dumme Fahrweise.
        this.strategy = new DumbStrategy();

    }

    public MoveableCar(Orientations startOrientation, String carName){
        this(0, 0, startOrientation, carName);
    }

    //VB: strategy != null
    public void setStrategy(MoveableCarStrategy strategy){
        this.strategy = strategy;
    }

    @Override
    protected boolean canDriveTo(Directions direction){
        return (direction == Directions.LEFT ||
                direction == Directions.LEFTFORWARD ||
                direction == Directions.FORWARD ||
                direction == Directions.RIGHTFORWARD ||
                direction == Directions.RIGHT );
    }

    //VB: this.strategy != null, currentRacetrack != null
    @Override
    protected void drive() throws InterruptedException {
        this.strategy.performMove(this, currentRacetrack);
    }



}
