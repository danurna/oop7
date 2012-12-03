
public class Test {

	/** Verzoegert ausfuerung um n millisec */
	public static void waiting (int n){     
        long t0, t1;
        t0 =  System.currentTimeMillis();
        do{
            t1 = System.currentTimeMillis();
        }
        while ((t1 - t0) < (n ));
    }
	
    /**
     * Hier werden die Punkte aus der Angabe getestet und vorgestellt wie man mit 
     * den Klassen umgeht:
     */
    public static void main(String[] args) {

    	System.out.println("TESTFALL 1: FrontalCrash mit Dummy Strategie und FastCars");
    	System.out.println("Rennbahn eine reine gerad keine Chance zum Ausweichen, Umdrehen unmoeglich:");
    	// 10 laengeneinheiten x, 1 leinheit y, maxPunkte 1, maxMoves 10
    	Racetrack a = new Racetrack(10,1,1,10);
    	System.out.println(a.debugString());
    	
    	
    	System.out.println("Setze Auto fuer Frontalcrash und starte:");
    	//setze 2 Autos an die Enden
    	FastCar fast1 = new FastCar(Orientations.EAST,"c1");
    	a.addCar(0, 0, fast1);
    	
    	FastCar fast2 = new FastCar(Orientations.WEST,"c2");
    	a.addCar(9, 0, fast2);
    	System.out.println(a.debugString());
    	
    	System.out.println("Ergebnisse:");
    	fast1.startCar();
    	fast2.startCar();
    	a.startGame();
    	waiting(200);
    	System.out.println(a.debugString());
    	
    	
    	
    	
    	System.out.println("------------------------------------------");
    	System.out.println("Testfall2 grosse strecke mit vielen autos:");
    	a = new Racetrack(20,16,10,100);
    	
    	// 6 autos:
    	fast1 = new FastCar(Orientations.SOUTH,"car1");
    	fast2 = new FastCar(Orientations.WEST,"car2");
    	FastCar fast3 = new FastCar(Orientations.NORTH,"car3");
    	MoveableCar mov1=new MoveableCar(Orientations.EAST,"car4");
    	MoveableCar mov2=new MoveableCar(Orientations.WEST,"car5");
    	MoveableCar mov3=new MoveableCar(Orientations.NORTH,"car6");
    	MoveableCar mov4=new MoveableCar(Orientations.EAST,"car7");
    	
    	// unterschiedliche strategien
    	fast1.setStrategy(new ZigZagStrategy());
    	fast3.setStrategy(new CircleStrategy());
    	mov2.setStrategy(new SchumacherStrategy());
    	mov4.setStrategy(new SchumacherStrategy());

    	// fuege Autos hinzu:
    	a.addCar(0,0,fast1);
    	a.addCar(3,4,fast2);
    	a.addCar(19,10,fast3);
    	a.addCar(4,9,mov1);
    	a.addCar(3,15,mov2);
    	a.addCar(14,14,mov3);
    	a.addCar(7,7,mov4);

    	System.out.println("Spielfeld-Starte:");
    	System.out.println(a.debugString());
    	

    	System.out.println("Spielfeld-Zwischenstand:");

    	fast1.startCar();
    	fast2.startCar();
    	fast3.startCar();
    	mov1.startCar();
    	mov2.startCar();
    	mov3.startCar();
    	mov4.startCar();
    	a.startGame();
    	waiting(10);
    	System.out.println(a.debugString());
    	System.out.println("Punkte-Endstand");
    	waiting(1000);
    	System.out.println();
    	System.out.println("Spielfeld-Endstand:");
    	System.out.println(a.debugString());
    	
    }
}
