package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.ActorContainer;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;

public class Drop<A extends Actor> extends AbstractAction<Keeper<A>> {
    @Override
    public void execute(float deltaTime) {
        setDone(true);
        if (getActor() == null) {
            return;
        }

        ActorContainer<A> container = getActor().getContainer();
        A item = container.peek();
        if (item != null) {
            container.remove(item);
            getActor().getScene().addActor(item, getActor().getPosX(), getActor().getPosY());
        }
    }
}
