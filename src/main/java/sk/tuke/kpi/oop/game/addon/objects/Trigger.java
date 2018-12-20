package sk.tuke.kpi.oop.game.addon.objects;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Trigger extends AbstractActor {
    public static final Topic<Trigger> TRIGGER_ACTIVATED = Topic.create("triggered", Trigger.class);
    private String type;

    public Trigger(String type) {
        this.type = type;
        setAnimation(new Animation("sprites/invisible.png", 1, 1));
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        new Loop<>(new Invoke<>(() -> {
            for (Actor actor: getScene().getActors()) {
                if (actor.intersects(this) && actor instanceof Ripley) {
                    getScene().getMessageBus().publish(TRIGGER_ACTIVATED, this);
                    break;
                }
            }
        })).scheduleOn(this);
    }

    public String getType() {
        return type;
    }
}
