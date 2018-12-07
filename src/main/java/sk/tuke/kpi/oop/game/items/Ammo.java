package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Ammo extends AbstractActor implements Usable<Ripley>, Disposable, Collectible {
    final private int amount = 50;

    public Ammo() {
        setAnimation(new Animation("sprites/ammo.png", 16,16));
    }

    @Override
    public void useWith(Ripley Actor) {
        use(Actor);
    }

    private void use(Ripley actor) {
        actor.setBullets(amount);
        dispose();
    }

    @Override
    public void dispose() {
        getScene().removeActor(this);
    }
}
