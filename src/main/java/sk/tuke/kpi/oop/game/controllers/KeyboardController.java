package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.actions.Move;
import sk.tuke.kpi.oop.game.characters.Direction;
import sk.tuke.kpi.oop.game.characters.Movable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class KeyboardController implements KeyboardListener {
    private Movable actor;
    private Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.RIGHT, Direction.EAST),
        Map.entry(Input.Key.DOWN, Direction.SOUTH),
        Map.entry(Input.Key.LEFT, Direction.WEST)
    );
    private Move moveAction;
    private Set<Input.Key> pressedKeys = new HashSet<>();

    public KeyboardController(Movable actor) {
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
                if (pressedKeys.size() == 0) {
                    moveAction.stop();
                } else {
                    updateMovement();
                }
            }
        }
    }

    private Direction calcDirection() {
        Direction newDirection = Direction.NONE;
        for (Input.Key value: pressedKeys) {
            newDirection = newDirection.combine(keyDirectionMap.get(value));
        }

        return newDirection;
    }

    private void updateMovement() {
        if (moveAction != null) {
            moveAction.stop();
        }

        moveAction = new Move(calcDirection(), Integer.MAX_VALUE);
        moveAction.scheduleOn(actor);
    }
}
