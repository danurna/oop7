/**
 * Repraesentiert ein Auto.
 * 
 */
abstract class Car {
    //Repraesentiert einer der vier Himmelsrichtungen, in die das Auto zeigen kann.
    private Orientations orientation;
    private int x;
    private int y;
    
    private int steps = 0;
    private int score = 0;

    public Car(int x, int y, Orientations startOrientation){
        this.x = x;
        this.y = y;
        this.orientation = startOrientation;
    }
    
    public Car(Orientations startOrientation){
        this(0, 0, startOrientation);
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
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }

    //Autos haben nur einen eingeschraenkten Aktionsradius, der hier ueberprueft wird.
    protected boolean canDriveTo(Directions direction){
        return false;
    }

    //Wird vom Thread aufgerufen.
    protected void drive(){

    }
    
    public int getScore() {
        return score;
    }
    
    public void upScore() {
        score += 1;
    }
    
    public int getSteps() {
        return steps;
    }
    
    public void upSteps() {
        steps += 1;
    }

}
