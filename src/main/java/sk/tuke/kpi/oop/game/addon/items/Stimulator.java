package sk.tuke.kpi.oop.game.addon.items;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.items.Usable;

public class Stimulator extends AbstractActor implements Usable<Ripley>, Collectible {
    public Stimulator() {
        setAnimation(new Animation("sprites/stimulator.png", 16, 16));
    }

    @Override
    public void useWith(Ripley actor) {
        if (actor == null) return;

        final int initSpeed = actor.getSpeed();

        actor.setSpeed(actor.getSpeed() * 2);
        actor.getContainer().remove(this);
        getScene().removeActor(this);

        new ActionSequence<>(
            new Wait<>(3),
            new Invoke<>(() -> actor.setSpeed(initSpeed))
        ).scheduleOn(actor);
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }
}
