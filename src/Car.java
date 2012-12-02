/**
 * Repraesentiert ein Auto.
 * 
 */
abstract class Car implements Runnable{
    //Repraesentiert einer der vier Himmelsrichtungen, in die das Auto zeigen kann.
    private Orientations orientation;
    private int x;
    private int y;
    private boolean running;
    private String name;
    protected int milisecondsToWait;
    protected Racetrack currentRacetrack;
    protected Thread t;
    
    private int steps = 0;
    private int score = 0;

    public Car(int x, int y, Orientations startOrientation, String carName){
        this.x = x;
        this.y = y;
        this.orientation = startOrientation;
        this.name = carName;
        this.milisecondsToWait = 0;
    }
    
    public Car(Orientations startOrientation, String carName){
        this(0, 0, startOrientation, carName);
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

    public void startCar(){
        this.t = new Thread(this);
        t.start();
    }

    //Autos haben nur einen eingeschraenkten Aktionsradius, der hier ueberprueft wird.
    protected boolean canDriveTo(Directions direction){
        return false;
    }

    //Wird vom Thread aufgerufen.
    protected void drive() throws InterruptedException {

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

    public void stop(){
        t.interrupt();
        t = null;
    }

    @Override
    public void run() {
        // t = Thread.currentThread();
        while( !Thread.interrupted() ){
            try {
               this.drive();
            

               Thread.sleep(milisecondsToWait);
            }catch(InterruptedException e){
                break;
            }
        }

        System.out.println(name + " " + score);
    }

    @Override
    public String toString() {
        return name;
    }


}
