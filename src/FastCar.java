/*
 * Schnelles Auto
 * Invariante: Ist schneller als MoveableCar
 */
public class FastCar extends Car{
    FastCarStrategy strategy;

    public FastCar(int x, int y, Orientations startOrientation){
        super(x, y, startOrientation);
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
        
    }


}
