package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.items.Usable;

public class Use extends AbstractAction<Actor> {
    private Usable<Actor> usable;

    public Use(@Nullable Usable<Actor> usable) {
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

    public Disposable scheduleOnIntersectingWith(Actor mediatingActor) {
        Scene scene = mediatingActor.getScene();
        if (scene == null) return null;

        if (usable != null) {
            scheduleOnWithUsable(mediatingActor);
        } else {
            for (Actor actor : scene) {
                if (mediatingActor.intersects(actor) && actor instanceof Usable && !(actor instanceof Collectible)) {
                    @SuppressWarnings("unchecked")
                    Usable<Actor> casted = (Usable<Actor>) actor;
                    usable = casted;
                    return this.scheduleOn(mediatingActor);
                }
            }
        }

        return null;
    }

    private Disposable scheduleOnWithUsable(Actor mediatingActor) {
        Scene scene = mediatingActor.getScene();
        Class<Actor> usingActorClass = usable.getUsingActorClass();
        for (Actor actor : scene) {
            if (mediatingActor.intersects(actor) && usingActorClass.isInstance(actor)) {
                return this.scheduleOn(usingActorClass.cast(actor));
            }
        }

        return null;
    }
}
