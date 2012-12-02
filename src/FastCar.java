/*
 * Schnelles Auto
 * Invariante: Ist schneller als MoveableCar
 */
public class FastCar extends Car{
    FastCarStrategy strategy;

    public FastCar(int x, int y, Orientations startOrientation, String carName){
        super(x, y, startOrientation, carName);
        this.millisecondsToWait = 100;
        //Standardstrategie ist die dumme Fahrweise.
        this.strategy = new DumbStrategy();
    }
    
    public FastCar(Orientations startOrientation, String carName){
        this(0, 0, startOrientation, carName);
    }

    @Override
    protected boolean canDriveTo(Directions direction){
        return (direction == Directions.LEFTFORWARD ||
                direction == Directions.FORWARD ||
                direction == Directions.RIGHTFORWARD
                );
    }


    @Override
    protected void drive() throws InterruptedException {
        this.strategy.performMove(this, currentRacetrack);
        
        System.out.println(currentRacetrack.debugString());
        System.out.println(this.getScore());
    }
    
}
