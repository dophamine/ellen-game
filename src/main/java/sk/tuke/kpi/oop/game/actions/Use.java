package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Usable;

public class Use extends AbstractAction<Ripley> {
    private Usable<Actor> usable;

    public Use(Usable<Actor> usable) {
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


//    public Disposable scheduleOnIntersectingWith(Ripley mediatingActor) {
//        return scheduleOn(mediatingActor);
//    }
}
