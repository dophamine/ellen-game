package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.items.Usable;

public class Use<A extends Actor> extends AbstractAction<A> {
    private Usable<A> usable;

    public Use(Usable<A> usable) {
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
            Class<A> usingActorClass = usable.getUsingActorClass();
            for (Actor actor : scene) {
                if (mediatingActor.intersects(actor) && usingActorClass.isInstance(actor)) {
                    var a = usingActorClass.cast(actor);
                    return this.scheduleOn(a);
                }
            }
        }

        return null;
    }
}
