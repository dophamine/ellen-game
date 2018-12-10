package sk.tuke.kpi.oop.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum Direction {
    NORTH (0, 1),
    EAST (1, 0),
    SOUTH (0, -1),
    WEST (-1, 0),
    NONE(0, 0),
    NORTHEAST(1, 1),
    NORTHWEST(-1, 1),
    SOUTHEAST(1, -1),
    SOUTHWEST(-1, -1);

    private static class DirectionHelper {
        public static Map<Direction, Float> directionToAngleMap = new HashMap<>(Map.ofEntries(
            Map.entry(NORTH, 0.f),
            Map.entry(NORTHEAST, 360 - 45.f),
            Map.entry(EAST, 360 - 90.f),
            Map.entry(SOUTHEAST, 360 - 135.f),
            Map.entry(SOUTH, 360 - 180.f),
            Map.entry(SOUTHWEST, 360 - 225.f),
            Map.entry(WEST, 360 - 270.f),
            Map.entry(NORTHWEST, 360 - 315.f)
        ));
    }

    public static Direction fromAngle(float angle) {
        for (var entry : DirectionHelper.directionToAngleMap.entrySet()) {
            if (Objects.equals(angle, entry.getValue())) {
                return entry.getKey();
            }
        }

        return NONE;
    }

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

    public float getAngle() {
        return DirectionHelper.directionToAngleMap.getOrDefault(this, 0.f);
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
