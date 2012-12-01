/**
 * Beweglicheres Auto.
 * Invariante: Ist langsamer als FastCar
 */
public class MoveableCar extends Car{
    MoveableCarStrategy strategy;

    public MoveableCar(int x, int y, Orientations startOrientation){
        super(x, y, startOrientation);

    }

    //Autos haben nur einen eingeschraenkten Aktionsradius, der hier ueberprueft wird.
    private boolean canDriveTo(int direction){
        return false;
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

    }
    
}
