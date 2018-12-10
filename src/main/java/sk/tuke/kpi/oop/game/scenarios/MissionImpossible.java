package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.framework.Scenario;
import sk.tuke.kpi.gamelib.map.SceneMap;
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
import java.util.Timer;
import java.util.TimerTask;

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

        scene.follow(ripley);

        var movableController = scene.getInput().registerListener(new MovableController(ripley));
        var collectorController = scene.getInput().registerListener(new CollectorController(ripley));


        scene.getMessageBus().subscribeOnce(Ripley.RIPLEY_DIED, v -> {
            movableController.dispose();
            collectorController.dispose();
        });

        Timer parasitesAttack = new Timer();
        scene.getMessageBus().subscribeOnce(Door.DOOR_OPENED, d -> {
            parasitesAttack.scheduleAtFixedRate(new TimerTask(){
                @Override
                public void run(){
                    ripley.loseEnergy();
                }
            }, 0, 100);
        });

        scene.getMessageBus().subscribeOnce(Ventilator.VENTILATOR_REPAIRED, v -> {
            parasitesAttack.cancel();
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
