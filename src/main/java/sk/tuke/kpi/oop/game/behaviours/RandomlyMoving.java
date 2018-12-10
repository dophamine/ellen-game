package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class RandomlyMoving implements Behaviour<Movable> {
    public RandomlyMoving() {
    }

    @Override
    public void setUp(Movable actor) {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask(){
            Move<Movable> action = null;

            @Override
            public void run(){
                if (actor == null) {
                    timer.cancel();
                }

                if (Math.random() < 0.5) return;

                if (action != null) {
                    action.stop();
                }

                final int min = 0;
                final int max = Direction.values().length - 1;

                long index = new Random().nextInt((max - min) + 1) + min;
                long duration = new Random().nextInt((3000 - min) + 1) + min;

                Direction direction = Direction.values()[(int)index];
                action = new Move<Movable>(direction, duration);
                action.scheduleOn(actor);
            }
      }, 0, 500);
    }

}
