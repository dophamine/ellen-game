package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.ActorFactory;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.CollectorController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

import java.util.Timer;
import java.util.TimerTask;

public class MissionImpossible implements SceneListener {
    public static class Factory implements ActorFactory {
        @Nullable
        @Override
        public Actor create(@Nullable String type, @Nullable String name) {
            switch (name) {
                case "access card":
                    return new AccessCard();
                case "door":
                    return new LockedDoor();
                case "ellen":
                    return new Ripley();
                case "energy":
                    return new Energy();
                case "locker":
                    return new Locker();
                case "ventilator":
                    return new Ventilator();
            }

            switch (type) {
                case "ripley":
                    return new Ripley();
            }

            return null;
        }
    }

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        Ripley ripley = scene.getFirstActorByType(Ripley.class);

        if (ripley == null) return;

        scene.follow(ripley);

        var movableController = scene.getInput().registerListener(new MovableController(ripley));
        var collectorController = scene.getInput().registerListener(new CollectorController(ripley));

        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, v -> {
            movableController.dispose();
            collectorController.dispose();
        });

        Timer parasitesAttack = new Timer();
        scene.getMessageBus().subscribe(Door.DOOR_OPENED, d -> {
            parasitesAttack.scheduleAtFixedRate(new TimerTask(){
                @Override
                public void run(){
                    ripley.loseEnergy();
                }
            }, 0, 300);
        });

        scene.getMessageBus().subscribe(Ventilator.VENTILATOR_REPAIRED, v -> {
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
