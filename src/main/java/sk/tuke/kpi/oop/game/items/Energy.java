package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Energy extends AbstractActor implements Usable<Ripley>, Disposable, Collectible {
    public Energy() {
        setAnimation(new Animation("sprites/energy.png", 16,16));
    }

    @Override
    public void useWith(Ripley Actor) {
        use(Actor);
    }

    private void use(Ripley actor) {
        actor.setEnergy(100);
        dispose();
    }

    @Override
    public void dispose() {
        getScene().removeActor(this);
    }
}
