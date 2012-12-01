/**
 * Repraesentiert ein Auto.
 * 
 */
abstract class Car {
    //Repraesentiert einer der vier Himmelsrichtungen, in die das Auto zeigen kann.
    private Orientations orientation;
    private int x;
    private int y;

    public Car(int x, int y, Orientations startOrientation){
        this.x = x;
        this.y = y;
        this.orientation = startOrientation;
    }

    public Orientations getOrientation(){
        return this.orientation;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    //Autos haben nur einen eingeschraenkten Aktionsradius, der hier ueberprueft wird.
    protected boolean canDriveTo(Directions direction){
        return false;
    }

    //Wird vom Thread aufgerufen.
    protected void drive(){

    }
    


}
