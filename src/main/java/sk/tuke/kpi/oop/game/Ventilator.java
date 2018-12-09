package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;

public class Ventilator extends AbstractActor implements Repairable {
    public static final Topic<Ventilator> VENTILATOR_REPAIRED = new Topic<>("ventilator repaired", Ventilator.class);

    public Ventilator() {
        setAnimation(new Animation("sprites/ventilator.png", 32, 32, 0.1f));
        getAnimation().stop();
    }

    @Override
    public boolean repair() {
        getAnimation().play();
        getScene().getMessageBus().publish(VENTILATOR_REPAIRED, this);

        return true;
    }
}
