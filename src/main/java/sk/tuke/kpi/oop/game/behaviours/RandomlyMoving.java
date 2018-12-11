package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.Random;

public class RandomlyMoving implements Behaviour<Movable> {
    private Movable movable = null;
    private Move<Movable> action = null;

    public RandomlyMoving() {
    }

    @Override
    public void setUp(Movable actor) {
        if (actor == null) return;
        movable = actor;

        new Loop<Movable>(new ActionSequence<>(new Invoke<>(this::makeMove), new Wait<>(1))).scheduleOn(actor);
    }

    private void makeMove() {
        if (movable == null) return;

        if (action != null) {
            action.stop();
        }

        final int min = 0;
        final int max = Direction.values().length - 1;

        long index = new Random().nextInt((max - min) + 1) + min;
        long duration = new Random().nextInt((3000 - min) + 1) + min;

        Direction direction = Direction.values()[(int)index];
        action = new Move<>(direction, duration);

        action.scheduleOn(movable);
    }
}
