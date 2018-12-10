package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.characters.Alive;

public class Energy extends AbstractActor implements Usable<Alive>, Collectible {
    public Energy() {
        setAnimation(new Animation("sprites/energy.png", 16,16));
    }

    @Override
    public void useWith(Alive actor) {
        if (actor == null) return;

        actor.getHealth().refill(100);

        if (actor instanceof Keeper) {
            @SuppressWarnings("unchecked")
            Keeper<Collectible> keeper = (Keeper<Collectible>) actor;
            keeper.getContainer().remove(this);
        } else {
            getScene().removeActor(this);
        }
    }

    @Override
    public Class<Alive> getUsingActorClass() {
        return Alive.class;
    }
}
