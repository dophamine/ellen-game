package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.items.Usable;

public class Use<A extends Actor> extends AbstractAction<A> {
    private Usable<A> usable;

    public Use(@Nullable Usable<A> usable) {
        this.usable = usable;
    }

    @Override
    public void execute(float deltaTime) {
        setDone(true);
        if (getActor() == null){
            return;
        }
        usable.useWith(getActor());
    }

    public Disposable scheduleOnIntersectingWith(A mediatingActor) {
        Scene scene = mediatingActor.getScene();
        if (scene == null) return null;

        if (usable != null) {
            scheduleOnWithUsable(mediatingActor);
        } else {
            for (Actor actor : scene) {
                if (mediatingActor.intersects(actor) && actor instanceof Usable && !(actor instanceof Collectible)) {
                    @SuppressWarnings("unchecked")
                    Usable<A> casted = (Usable<A>) actor;
                    usable = casted;
                    return this.scheduleOn(mediatingActor);
                }
            }
        }

        return null;
    }

    private Disposable scheduleOnWithUsable(Actor mediatingActor) {
        Scene scene = mediatingActor.getScene();
        Class<A> usingActorClass = usable.getUsingActorClass();
        for (Actor actor : scene) {
            if (mediatingActor.intersects(actor) && usingActorClass.isInstance(actor)) {
                var a = usingActorClass.cast(actor);
                return this.scheduleOn(a);
            }
        }

        return null;
    }
}
