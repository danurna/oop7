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
    protected int millisecondsToWait;
    protected Racetrack currentRacetrack;
    private Thread t;
    
    private int steps = 0;
    private int score = 0;

    /**
     * Initialisiere ein Auto.
     *
     * @param x - Startposition
     * @param y - Startposition
     * @param startOrientation - Anfangsorientierung
     * @param carName - Name des Autos
     */
    public Car(int x, int y, Orientations startOrientation, String carName){
        this.x = x;
        this.y = y;
        this.orientation = startOrientation;
        this.name = carName;
        this.millisecondsToWait = 0;
    }
    
    public Car(Orientations startOrientation, String carName){
        this(0, 0, startOrientation, carName);
    }

    public void setOrientation(Orientations orientation){
        this.orientation = orientation;
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

    //VB: x <= 0
    public void setX(int x) {
        this.x = x;
    }

    //VB: y <= 0
    public void setY(int y) {
        this.y = y;
    }

    //VB: rt != null
    public void setRacetrack(Racetrack rt) {
        this.currentRacetrack = rt;
    }

    //NB: Der Thread laeuft.
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

    //Erhoeht den Score um eins.
    public void upScore() {
        score += 1;
    }
    
    // Verringert den Score um eins.
    public void downScore() {
        score -= 1;
    }

    public int getSteps() {
        return steps;
    }

    //Erhoeht die Schritte um eins.
    public void upSteps() {
        steps += 1;
    }

    //NB: Es laeuft fuer eine Instanz kein Thread mehr.
    public void stop(){
        if(t != null){
            t.interrupt();
            t = null;
        }
    }

    @Override
    public void run() {

        while( !Thread.interrupted() ){
            try {
               this.drive();
               Thread.sleep(millisecondsToWait);
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
