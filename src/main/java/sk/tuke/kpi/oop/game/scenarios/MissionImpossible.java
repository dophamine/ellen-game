package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.ActorFactory;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.CollectorController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

import java.util.Objects;

public class MissionImpossible implements SceneListener {
    public static class Factory implements ActorFactory {
        @Nullable
        @Override
        public Actor create(@Nullable String type, @Nullable String name) {
            if (name != null) {
                switch (name) {
                    case "access card":
                        return new AccessCard();
                    case "door":
                        return new LockedDoor(name, getDoorOrientation(type));
                    case "ellen":
                        return new Ripley();
                    case "energy":
                        return new Energy();
                    case "locker":
                        return new Locker();
                    case "ventilator":
                        return new Ventilator();
                    default:
                        return null;
                }
            }

            return null;
        }

        private Door.Orientation getDoorOrientation(String type) {
            return Objects.equals(type, "horizontal") ? Door.Orientation.HORIZONTAL : Door.Orientation.VERTICAL;
        }
    }

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        Ripley ripley = scene.getFirstActorByType(Ripley.class);

        if (ripley == null) return;

        scene.getGame().pushActorContainer(ripley.getContainer());
        scene.follow(ripley);

        var movableController = scene.getInput().registerListener(new MovableController(ripley));
        var collectorController = scene.getInput().registerListener(new CollectorController(ripley));

        scene.getMessageBus().subscribeOnce(Ripley.RIPLEY_DIED, v -> {
            movableController.dispose();
            collectorController.dispose();
        });

        // parasites attack
        scene.getMessageBus().subscribeOnce(Door.DOOR_OPENED, d -> {
            Disposable parasitesAttack = new Loop<>(
                new ActionSequence<>(
                    new Invoke<>(() -> {
                        ripley.loseEnergy();
                    }),
                    new Wait<>(0.3f)
                )
            ).scheduleOn(ripley);

            scene.getMessageBus().subscribeOnce(Ventilator.VENTILATOR_REPAIRED, v -> {
                parasitesAttack.dispose();
            });
        });
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        Ripley ripley = scene.getFirstActorByType(Ripley.class);

        if (ripley != null) {
            ripley.showRipleyState();
        }
    }
}
