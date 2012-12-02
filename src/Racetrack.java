/*
 * Repraesentiert die Autodrom Rennstrecke, die ein Rechteck bildet.
 */

class Racetrack {

    private Tile[][] track;
    private boolean gameOver = false;
    private int maxScore;

    private int xsize;
    private int ysize;

    public Racetrack(int xsize, int ysize, int maxScore) {
        track = new Tile[xsize][ysize];
        for (int x = 0; x < xsize; ++x) {
            for (int y = 0; y < ysize; ++y) {
                track[x][y] = new Tile();
            }
        }
        this.maxScore = maxScore;
        this.xsize = xsize;
        this.ysize = ysize;
    }

    boolean isEmpty(int x, int y) {
        return track[x][y].isEmpty();
    }

    public Car getCar(int x, int y) {
        return track[x][y].getCar();
    }
    
    public boolean addCar(int x, int y, Car c) {
        synchronized (track[x][y]) {
            if (track[x][y].isEmpty()) {
                track[x][y].setCar(c);
                c.setX(x);
                c.setY(y);
                return true;
            }
            return false;
        }
    }

    public void moveTo(Car a, Directions d) throws GameOverException {

        int x = a.getX();
        int y = a.getY();

        int dx, dy;

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
            // Out of bounds;
            // FIXME
        }

        Tile lockfst;
        Tile locksnd;

        Tile fromTile = track[x][y];
        Tile toTile = track[nx][ny];

        if (nx + ny * track.length < x + y * track.length) {
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
                    }
                }
            }
        }

        if (gameOver) {
            throw new GameOverException();
        }

        if (a.getScore() >= maxScore) {
            gameOver = true;
        }
    }

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
    
    public static void main(String[] args) throws GameOverException {
        Racetrack track = new Racetrack(10, 10, 10);
        Car c1 = new FastCar(Orientations.WEST);
        Car c2 = new FastCar(Orientations.SOUTH);
        track.addCar(1, 1, c1);
        track.addCar(1, 2, c2);
        System.out.println(track.debugString());
        track.moveTo(c1, Directions.RIGHTFORWARD);
        System.out.println(track.debugString());
        System.out.println(c1.getScore());
    }
}
