package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.characters.Armed;

public class Ammo extends AbstractActor implements Usable<Armed>, Collectible {
    final private int amount = 50;

    public Ammo() {
        setAnimation(new Animation("sprites/ammo.png", 16,16));
    }

    @Override
    public void useWith(Armed Actor) {
        use(Actor);
    }

    private void use(Armed actor) {
        if (actor == null) return;
        
        actor.getFirearm().reload(amount);

        if (actor instanceof Keeper) {
            @SuppressWarnings("unchecked")
            Keeper<Collectible> keeper = (Keeper<Collectible>) actor;
            keeper.getContainer().remove(this);
        }

        getScene().removeActor(this);
    }

    @Override
    public Class<Armed> getUsingActorClass() {
        return Armed.class;
    }
}
