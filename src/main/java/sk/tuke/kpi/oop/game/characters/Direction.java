package sk.tuke.kpi.oop.game.characters;

public enum Direction {
    NORTH (0, 1),
    EAST (1, 0),
    SOUTH (0, -1),
    WEST (-1, 0),
    NONE(0, 0),
    NORTH_EAST (1, 1),
    NORTH_WEST (-1, 1),
    SOUTH_EAST (1, -1),
    SOUTH_WEST (-1, -1);

    private final int dx;
    private final int dy;

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    float getAngle() {
        float angle = 0.f;
        switch (this) {
            case NORTH: angle = 0.f;
                break;
            case NORTH_EAST: angle = 45.f;
                break;
            case EAST: angle = 90.f;
                break;
            case SOUTH_EAST: angle = 135.f;
                break;
            case SOUTH: angle = 180.f;
                break;
            case SOUTH_WEST: angle = 225.f;
                break;
            case WEST: angle = 270.f;
                break;
            case NORTH_WEST: angle = 315.f;
                break;
            default:
        }

        return 360.f - angle;
    }

    public Direction combine(Direction other) {
        for (Direction dir :Direction.values()) {
            if (dir.dx == (dx != 0 ? dx : other.dx) && dir.dy == (dy != 0 ? dy : other.dy)) {
                return dir;
            }
        }

        return NONE;
    }
}
