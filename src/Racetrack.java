import java.util.Vector;

/*
 * Repraesentiert die Autodrom Rennstrecke, die ein Rechteck bildet.
 */

class Racetrack {

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
    public void moveTo(Car a, Directions d) throws GameOverException,
            OutOfRacetrackException {

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

        if (ny >= ysize || nx >= xsize) {
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
                } else if (!gameOver) {
                    if (a.getOrientation() == toTile.getCar().getOrientation()
                            .getOpposite()) {
                        a.upScore();
                        a.upSteps();
                    }
                }
            }
        }

        if (a.getScore() >= maxScore || a.getSteps() >= maxSteps) {
            gameOver = true;
        }

        if (gameOver) {
            throw new GameOverException();
        }

    }

    // Synchronized um den debug print in einem konsistenten
    // Zustand zu erhalten. Sonst koennte es dazu kommen, dass
    // ein Auto doppelt angezeigt wird.
    public synchronized String debugString() {
        String ret = "";
        for (int y = 0; y < ysize; ++y) {
            for (int x = 0; x < xsize; ++x) {
                if (track[x][y].isEmpty()) {
                    ret += '.';
                } else {
                    switch (track[x][y].getCar().getOrientation()) {
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
        Racetrack track = new Racetrack(10, 10, 10, 1);
        Car c1 = new FastCar(Orientations.SOUTH);
        Car c2 = new FastCar(Orientations.NORTH);
        track.addCar(1, 1, c1);
        track.addCar(1, 2, c2);
        System.out.println(track.debugString());
        track.moveTo(c1, Directions.FORWARD);
        System.out.println(track.debugString());
        System.out.println(c1.getScore());
    }
}
