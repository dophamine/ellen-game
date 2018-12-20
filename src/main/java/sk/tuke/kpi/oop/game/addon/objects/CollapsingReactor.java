package sk.tuke.kpi.oop.game.addon.objects;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Reactor;
import sk.tuke.kpi.oop.game.openables.Door;

public class CollapsingReactor extends Reactor {
    public static final Topic<CollapsingReactor> REACTOR_EXPLODED = Topic.create("reactor exploded", CollapsingReactor.class);

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        getScene().getMessageBus().subscribeOnce(Door.DOOR_OPENED, door -> {
            if (door.getName().equals("front door")) {
                turnOn();
            }
        });
    }

    @Override
    protected void onBroken() {
        super.onBroken();
        getScene().getMessageBus().publish(REACTOR_EXPLODED, this);
    }
}
