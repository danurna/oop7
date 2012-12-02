/**
 * Beweglicheres Auto.
 * Invariante: Ist langsamer als FastCar
 */
public class MoveableCar extends Car{
    MoveableCarStrategy strategy;

    public MoveableCar(int x, int y, Orientations startOrientation){
        super(x, y, startOrientation);
        this.milisecondsToWait = 200;
        //Standardstrategie ist die dumme Fahrweise.
        this.strategy = new DumpStrategy();

    }

    public MoveableCar(Orientations startOrientation){
        this(0, 0, startOrientation);
    }

    @Override
    protected boolean canDriveTo(Directions direction){
        if(direction == Directions.LEFT ||
                direction == Directions.LEFTFORWARD ||
                direction == Directions.FORWARD ||
                direction == Directions.RIGHTFORWARD ||
                direction == Directions.RIGHT ){
           return true;
        }else{
           return false;
        }
    }
    
    @Override
    protected void drive(){
        this.strategy.performMove(this, currentRacetrack);
    }



}
