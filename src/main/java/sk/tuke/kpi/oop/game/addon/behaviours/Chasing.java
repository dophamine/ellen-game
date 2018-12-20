package sk.tuke.kpi.oop.game.addon.behaviours;

import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.awt.Point;

public class Chasing implements Behaviour<Movable> {
    private float distance = 100.f;
    private Movable subject = null;
    private Move<Movable> action = null;
    private Disposable loop;

    public Chasing() { }

    public Chasing(float distance) {
        this.distance = distance;
    }

    @Override
    public void setUp(Movable actor) {
        if (actor == null) return;
        subject = actor;

        if (loop != null) {
            loop.dispose();
        }

        loop = new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::chase),
                new Wait<>(0.3f)
            )
        ).scheduleOn(subject);
    }

    private void chase() {
        if (subject == null) return;

        if (action != null) {
            action.stop();
        }

        Ripley player = subject.getScene().getFirstActorByType(Ripley.class);

        var dx = player.getPosX() + 8 - subject.getPosX();
        var dy = player.getPosY() + 8 - subject.getPosY();

        var length = Math.sqrt((Math.pow(dx, 2) + Math.pow(dy, 2)));
        Point normalVector = new Point((int)(Math.round(dx / length)), (int)(Math.round(dy / length)));

        if (length > distance || player.intersects(subject)) {
            return;
        }

        Direction direction = Direction.NONE;
        for (var dir: Direction.values()) {
            if (dir.getDx() == normalVector.x && dir.getDy() == normalVector.y) {
                direction = dir;
                break;
            }
        }

        action = new Move<>(direction, Short.MAX_VALUE, true);
        action.scheduleOn(subject);
    }
}

