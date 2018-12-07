package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.ActorContainer;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

public class Drop extends AbstractAction<Keeper<Collectible>> {
    @Override
    public void execute(float deltaTime) {
        setDone(true);
        if (getActor() == null) {
            return;
        }

        ActorContainer<Collectible> container = getActor().getContainer();
        Collectible item = container.peek();
        if (item != null) {
            container.remove(item);
            getActor().getScene().addActor(item, getActor().getPosX(), getActor().getPosY());
        }
    }
}
