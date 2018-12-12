package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.ActorFactory;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;
import sk.tuke.kpi.oop.game.behaviours.Observing;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.CollectorController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;

import java.util.Objects;

public class EscapeRoom implements SceneListener {
    public static class Factory implements ActorFactory {
        @Nullable
        @Override
        public Actor create(@Nullable String type, @Nullable String name) {
            if (name != null) {
                switch (name) {
                    case "ellen":
                        return new Ripley();
                    case "energy":
                        return new Energy();
                    case "ammo":
                        return new Ammo();
                    case "alien":
                        return new Alien(50, getAlienBehaviour(type));
                    case "alien mother":
                        return new Alien.AlienMother(200, getAlienBehaviour(type));
                    case "front door":
                    case "back door":
                    case "exit door":
                        return new Door(name, getDoorOrientation(type));
                    default:
                        return  null;
                }
            }

            return null;
        }

        private Door.Orientation getDoorOrientation(String type) {
            return Objects.equals(type, "horizontal") ? Door.Orientation.HORIZONTAL : Door.Orientation.VERTICAL;
        }

        private Behaviour<? super Alien> getAlienBehaviour(String type) {
            if (Objects.equals(type, "running")) {
                return new RandomlyMoving();
            } else if (Objects.equals(type, "waiting1")) {
                return new Observing<>(
                    Door.DOOR_OPENED,
                    door -> door.getName().equals("front door"),
                    new RandomlyMoving()
                );
            } else if (Objects.equals(type, "waiting2")) {
                return new Observing<>(
                    Door.DOOR_OPENED,
                    door -> door.getName().equals("back door"),
                    new RandomlyMoving()
                );
            } else return new RandomlyMoving();
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
        var shooterController = scene.getInput().registerListener(new ShooterController(ripley));

        scene.getMessageBus().subscribeOnce(Ripley.RIPLEY_DIED, actor -> {
            scene.cancelActions(actor);
            movableController.dispose();
            collectorController.dispose();
            shooterController.dispose();
        });

        scene.getMessageBus().subscribe(Door.DOOR_OPENED, door -> {
            System.out.println(door.getName());
            if (door.getName().equals("exit door")) {
                scene.cancelActions(ripley);
                movableController.dispose();
                collectorController.dispose();
                shooterController.dispose();
                onEndGame(scene);
            }
        });
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        Ripley ripley = scene.getFirstActorByType(Ripley.class);

        if (ripley != null) {
            ripley.showRipleyState();
        }
    }

    private void onEndGame(Scene scene) {
        int windowHeight = scene.getGame().getWindowSetup().getHeight();
        int windowWidth = scene.getGame().getWindowSetup().getWidth();
        scene.getGame().getOverlay().drawText("Well done!", windowWidth / 2 - 50, windowHeight / 2).showFor(10);
    }
}
