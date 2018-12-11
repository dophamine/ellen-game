package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.actions.Move;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

import java.util.*;

public class MovableController implements KeyboardListener {
    private Movable actor;
    private Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.RIGHT, Direction.EAST),
        Map.entry(Input.Key.DOWN, Direction.SOUTH),
        Map.entry(Input.Key.LEFT, Direction.WEST)
    );
    private Move<Movable> moveAction;
    private Set<Input.Key> pressedKeys = new LinkedHashSet<>();

    public MovableController(Movable actor) {
        this.actor = actor;
    }

    @Override
    public void keyPressed(@NotNull Input.Key key) {
        if (keyDirectionMap.containsKey(key)) {
            pressedKeys.add(key);
            updateMovement();
        }
    }

    @Override
    public void keyReleased(@NotNull Input.Key key) {
        if (keyDirectionMap.containsKey(key)) {
            pressedKeys.remove(key);

            if (moveAction != null) {
                if (pressedKeys.isEmpty()) {
                    moveAction.stop();
                    moveAction = null;
                } else {
                    updateMovement();
                }
            }
        }
    }

    private Direction calcDirection() {
        // reversing the list
        List<Input.Key> list = new ArrayList<Input.Key>(pressedKeys);
        Collections.reverse(list);

        Direction newDirection = Direction.NONE;
        for (Input.Key value: list) {
            newDirection = newDirection.combine(keyDirectionMap.get(value));
        }

        return newDirection;
    }

    private void updateMovement() {
        if (moveAction != null) {
            moveAction.stop();
        }

        moveAction = new Move<Movable>(calcDirection(), Short.MAX_VALUE);
        moveAction.scheduleOn(actor);
    }
}
