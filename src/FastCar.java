/*
 * Schnelles Auto
 * Invariante: Ist schneller als MoveableCar
 */
public class FastCar extends Car{
    FastCarStrategy strategy;

    public FastCar(int x, int y, Orientations startOrientation){
        super(x, y, startOrientation);
        this.milisecondsToWait = 100;
        //Standardstrategie ist die dumme Fahrweise.
        this.strategy = new DumpStrategy();
    }
    
    public FastCar(Orientations startOrientation){
        this(0, 0, startOrientation);
    }

    @Override
    protected boolean canDriveTo(Directions direction){
        if(direction == Directions.LEFTFORWARD ||
                direction == Directions.FORWARD ||
                direction == Directions.RIGHTFORWARD
                ){
           return true;
        }else{
           return false;
        }
    }


    @Override
    protected void drive(){
        this.strategy.performMove(this, currentRacetrack);
    }

    @Override
    public void run() {
        for(;;){
            this.drive();
        }
    }

    


}
