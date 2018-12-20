package sk.tuke.kpi.oop.game.addon.effects;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.While;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.oop.game.characters.Alive;

public abstract class AbstractExplosive extends AbstractActor implements Disposable {
    /** Millis */
    private long duration = 0;
    private int damage;
    private Class<? extends Alive> target;
    private boolean isOneTime = false;

    public AbstractExplosive(Class<? extends Alive> target, int damage) {
        this.target = target;
        this.damage = damage;
        isOneTime = true;
        init();
    }

    public AbstractExplosive(Class<? extends Alive> target, int damage, long duration) {
        this.target = target;
        this.damage = damage;
        this.duration = duration;
        if (duration == 0) {
            isOneTime = true;
        }
        init();
    }

    protected abstract void init();

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        explode();
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void explode() {
        if (isOneTime) {
            new ActionSequence<>(
                new Invoke<>(this::spreadDamage),
                new Invoke<>(this::dispose)
            ).scheduleOn(this);
        } else {
            final long end = System.currentTimeMillis() + duration;
            new ActionSequence<>(
                new While<>(
                    action -> System.currentTimeMillis() < end,
                    new Invoke<>(this::spreadDamage)
                ),
                new Invoke<>(this::dispose)
            ).scheduleOn(this);
        }
    }



    protected void spreadDamage() {
        for (var actor: getScene().getActors()) {
            if (target.isInstance(actor) && actor.intersects(this)) {
                ((Alive) actor).getHealth().drain(damage);
            }
        }
    }

    @Override
    public void dispose() {
        getScene().cancelActions(this);
        getScene().removeActor(this);
    }
}
