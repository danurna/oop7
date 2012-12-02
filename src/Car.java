/**
 * Repraesentiert ein Auto.
 * 
 */
abstract class Car implements Runnable{
    //Repraesentiert einer der vier Himmelsrichtungen, in die das Auto zeigen kann.
    private Orientations orientation;
    private int x;
    private int y;
    protected int milisecondsToWait;
    protected Racetrack currentRacetrack;
    
    private int score = 0;

    public Car(int x, int y, Orientations startOrientation){
        this.x = x;
        this.y = y;
        this.orientation = startOrientation;
        milisecondsToWait = 0;
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

    public void setRacetrack(Racetrack rt) {
        this.currentRacetrack = rt;
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

    @Override
    public void run() {
        for(;;){
            this.drive();
            try{
               Thread.sleep(milisecondsToWait);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            
        }
    }


}
