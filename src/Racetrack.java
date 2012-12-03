import java.util.Vector;
import java.util.concurrent.CountDownLatch;

/*
 * Repraesentiert die Autodrom Rennstrecke, die ein Rechteck bildet.
 */

class Racetrack {
    private CountDownLatch startLock;

    private Tile[][] track;
    private Boolean gameOver = false;
    private int maxScore;
    private int maxSteps;

    private int xsize;
    private int ysize;

    private Vector<Car> cars;

    public Racetrack(int xsize, int ysize, int maxScore, int maxSteps) {
        track = new Tile[xsize][ysize];
        for (int x = 0; x < xsize; ++x) {
            for (int y = 0; y < ysize; ++y) {
                track[x][y] = new Tile();
            }
        }
        this.maxScore = maxScore;
        this.maxSteps = maxSteps;
        this.xsize = xsize;
        this.ysize = ysize;
        this.cars = new Vector<Car>();
        this.startLock = new CountDownLatch(1);
    }
    
    void startGame() {
        this.startLock.countDown();
    }
    
    public boolean inRange(int x, int y) {
        return !(y >= ysize || x >= xsize || y < 0 || x < 0);
    }

    // NB: Gib Auto zurueck, ob sich auf x, y ein Auto befindet.
    boolean isEmpty(int x, int y) {
        return track[x][y].isEmpty();
    }

    // NB: Gib Auto zurueck, dass sich derzeit auf x, y befindet.
    // Wenn sich auf x, y kein Auto befindet, gib 0 zurueck.
    public Car getCar(int x, int y) {
        return track[x][y].getCar();
    }

    // VB: Auto befindet sich noch nicht auf Racetrack.
    // NB: Auto befindet sich auf Racetrack auf der Position x, y wenn true
    // zurueck gegeben wird, wenn false zurueckgegeben wird befand sich
    // bereits ein Auto auf x, y und der Racetrack bleibt unveraendert.
    public boolean addCar(int x, int y, Car c) {
        synchronized (track[x][y]) {
            if (track[x][y].isEmpty()) {
                track[x][y].setCar(c);
                c.setRacetrack(this);
                c.setX(x);
                c.setY(y);
                cars.add(c);
                return true;
            }
            return false;
        }
    }

    // VB: a befindet sich auf Racetrack.
    // Bewege Auto a in Richtung d. Wenn sich auf Zieltile bereits ein Auto
    // befindet, fuehre etwaige Erhoehungen des Punktestandes von a aus.
    // Wirft GameOverException, wenn das Spiel beendet wurde und keine
    // weiteren Zuege zulaessig sind.
    // Wirft eine OutOfRacetrackException, wenn das Auto versucht, die
    // Strecke zu verlassen.
    public void moveTo(Car a, Directions d) throws OutOfRacetrackException, InterruptedException {
        this.startLock.await();
        
        int x = a.getX();
        int y = a.getY();

        int dx, dy;

        // dx, dy unter Annahme, dass Auto nach Osten schaut.
        switch (d) {
        case LEFTFORWARD:
            dx = 1;
            dy = -1;
            break;
        case FORWARD:
            dx = 1;
            dy = 0;
            break;
        case RIGHTFORWARD:
            dx = 1;
            dy = 1;
            break;
        case RIGHT:
            dx = 0;
            dy = 1;
            break;
        case RIGHTBACKWARD:
            dx = -1;
            dy = 1;
            break;
        case BACKWARD:
            dx = -1;
            dy = 0;
            break;
        case LEFTBACKWARD:
            dx = -1;
            dy = -1;
            break;
        case LEFT:
            dx = 0;
            dy = -1;
            break;
        default:
            throw new RuntimeException("Unreachable");
        }

        int tmp;

        // Korrektur von dx, xy, sodass sie fuer die jeweilige Fahrtrichtung
        // richtig sind.
        switch (a.getOrientation()) {
        case EAST:
            break;
        case WEST:
            dx = -dx;
            dy = -dy;
            break;
        case NORTH:
            tmp = dx;
            dx = dy;
            dy = -tmp;
            break;
        case SOUTH:
            tmp = dx;
            dx = -dy;
            dy = tmp;
            break;
        }

        int nx = x + dx;
        int ny = y + dy;

        if (!inRange(nx, ny)) {
            throw new OutOfRacetrackException();
        }

        // Sperre zuerst das Tile, das weiter links oben ist, um Deadlocks
        // zu verhindern.
        Tile lockfst;
        Tile locksnd;

        Tile fromTile = track[x][y];
        Tile toTile = track[nx][ny];

        if (nx + ny * ysize < x + y * ysize) {
            lockfst = track[nx][ny];
            locksnd = track[x][y];
        } else {
            lockfst = track[x][y];
            locksnd = track[nx][ny];
        }

        synchronized (lockfst) {
            synchronized (locksnd) {
                if (toTile.isEmpty() && !gameOver) {
                    toTile.setCar(a);
                    fromTile.removeCar();
                    a.setX(nx);
                    a.setY(ny);
                    a.upSteps();
                } else if (!gameOver) {
                    if (a.getOrientation() == toTile.getCar().getOrientation()
                            .getOpposite()) {
                        a.upScore();
                    }
                }
            }
        }

        if (a.getScore() >= maxScore || a.getSteps() >= maxSteps) {
            synchronized(gameOver) {
                if (!gameOver) {
                    gameOver = true;
                    for (Car c: cars) {
                        c.stop();
                    }
                }
            }
        }
    }

    // Only approximate for fast cars.
    public String debugString() {
        String ret = "";
        for (int y = 0; y < ysize; ++y) {
            for (int x = 0; x < xsize; ++x) {
                Car c = track[x][y].getCar();
                if (c == null) {
                    ret += '.';
                } else {
                    switch (c.getOrientation()) {
                    case NORTH:
                        ret += '^';
                        break;
                    case EAST:
                        ret += '>';
                        break;
                    case SOUTH:
                        ret += 'v';
                        break;
                    case WEST:
                        ret += '<';
                        break;
                    }
                }
            }
            ret += '\n';
        }
        return ret;
    }

    public static void main(String[] args) throws GameOverException,
            OutOfRacetrackException {
        Racetrack track = new Racetrack(10, 10, 10, 150);
        MoveableCar c1 = new MoveableCar(Orientations.SOUTH, "Car 1");
        MoveableCar c2 = new MoveableCar(Orientations.NORTH, "MIKA HAEKKINEN");
        FastCar c3 = new FastCar(Orientations.NORTH, "SCHUMI");
        FastCar c4 = new FastCar(Orientations.EAST, "VETTEL");
        MoveableCar c5 = new MoveableCar(Orientations.WEST, "ALONSO");


        c1.setStrategy(new DumbStrategy());
        c2.setStrategy(new SchumacherStrategy());
        c3.setStrategy(new ZigZagStrategy());
        c4.setStrategy(new CircleStrategy());
        c5.setStrategy(new SchumacherStrategy());
        
        track.addCar(5, 5, c1);
        track.addCar(3, 3, c2);
        track.addCar(9, 9, c3);
        track.addCar(1, 3, c4);
        track.addCar(4, 7, c5);

        c1.startCar();
        c2.startCar();
        c3.startCar();
        c4.startCar();
        c5.startCar();
        
        track.startGame();
        
        System.out.println(track.debugString());
        // track.moveTo(c1, Directions.FORWARD);
        System.out.println(track.debugString());
        System.out.println(c1.getScore());
    }
}
