/*
 * Repraesentiert die Richtungen in die ein Auto zeigen kann.
 */
public enum Orientations {
    NORTH, EAST, SOUTH, WEST;

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
}
