/**
 * Repraesentiert ein Auto.
 * 
 */
abstract class Car implements Runnable{
    //Repraesentiert einer der vier Himmelsrichtungen, in die das Auto zeigen kann.
    private Orientations orientation;
    private int x;
    private int y;
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


    //VB: setOrientation darf nur ausgefuehrt werden, wenn vorher eine passende
    //Beweung stattgefunden hat. 
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

    //VB: x >= 0
    public void setX(int x) {
        this.x = x;
    }

    //VB: y >= 0
    public void setY(int y) {
        this.y = y;
    }

    //VB: rt != null
    //VB: Das Rennen auf diesem Track darf noch nicht begonnen haben
    public void setRacetrack(Racetrack rt) {
        this.currentRacetrack = rt;
    }

    //VB: Rennen hat begonnen
    //NB: Der Thread laeuft.
    public void startCar(){
        this.t = new Thread(this);
        t.start();
    }

    //Autos haben nur einen eingeschraenkten Aktionsradius, der hier ueberprueft wird.
    //Unterklassen liefern passenden boolean zurueck.
    protected boolean canDriveTo(Directions direction){
        return false;
    }

    //Wird vom Thread aufgerufen. Implementierung in Unterklassen.
    protected abstract void drive() throws InterruptedException; 
    
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

    //NB: Es laeuft fuer diese Instanz kein Thread mehr.
    public void stop(){
        if(t != null){
            t.interrupt();
            t = null;
        }
    }



    /**
     * Thread Methode, die unterbrochen werden kann. Ruft drive Methode auf.
     * Am Ende der Methode wird der Name und Score ausgegeben, da das
     * Rennen zu Ende ist.
     * 
     */
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

        System.out.println("Auto \'"+name + "\' Punkte=" + score);
    }

    
    @Override
    public String toString() {
        return name;
    }


}
