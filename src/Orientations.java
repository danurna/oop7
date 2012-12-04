/*
 * Repraesentiert die Richtungen in die ein Auto zeigen kann.
 */
public enum Orientations {
    NORTH, EAST, SOUTH, WEST;

    // NB: Retourniert entgegenegesetzte Richtung.
    public Orientations getOpposite() {
        switch (this) {
        case NORTH:
            return SOUTH;
        case EAST:
            return WEST;
        case SOUTH:
            return NORTH;
        case WEST:
            return EAST;
        default:
            throw new RuntimeException("Unreachable");
        }
    }

    // NB: Retourniert Richtung linksgedreht.
    public Orientations turnLeft() {
        switch (this) {
        case NORTH:
            return WEST;
        case EAST:
            return NORTH;
        case SOUTH:
            return EAST;
        case WEST:
            return SOUTH;
        default:
            throw new RuntimeException("Unreachable");
        }
    }

    // NB: NB: Retourniert Richtung rechtsgedreht.
    public Orientations turnRight() {
        switch (this) {
        case NORTH:
            return EAST;
        case EAST:
            return SOUTH;
        case SOUTH:
            return WEST;
        case WEST:
            return NORTH;
        default:
            throw new RuntimeException("Unreachable");
        }
    }
}
