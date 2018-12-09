package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("rawtypes")
public class Locker extends AbstractActor implements Usable<Actor> {
    private ArrayList<Collectible> store = new ArrayList<>(
        Arrays.asList(new Hammer())
    );

    public Locker() {
        setAnimation(new Animation("sprites/locker.png", 16, 16));
    }

    @Override
    public void useWith(Actor actor) {
        for (Collectible item: store) {
            getScene().addActor(item, actor.getPosX(), actor.getPosY());
        }

        store.clear();
    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }
}
