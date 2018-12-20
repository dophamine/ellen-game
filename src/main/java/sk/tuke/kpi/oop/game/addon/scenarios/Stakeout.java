package sk.tuke.kpi.oop.game.addon.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.ActorFactory;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.oop.game.addon.objects.*;
import sk.tuke.kpi.oop.game.addon.behaviours.Chasing;
import sk.tuke.kpi.oop.game.addon.characters.Hero;
import sk.tuke.kpi.oop.game.addon.characters.Spider;
import sk.tuke.kpi.oop.game.addon.items.Mine;
import sk.tuke.kpi.oop.game.addon.items.Stimulator;
import sk.tuke.kpi.oop.game.addon.openables.DistantContolledDoor;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;
import sk.tuke.kpi.oop.game.behaviours.Observing;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.CollectorController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;

public class Stakeout implements SceneListener {
    public static class Factory implements ActorFactory {
        @Nullable
        @Override
        public Actor create(@Nullable String type, @Nullable String name) {
            if (name != null) {
                switch (name) {
                    case "ellen":
                        return new Ripley();
                    case "hero":
                        return new Hero();
                    case "alien":
                        return new Alien(50, getAIBehaviour(type));
                    case "energy":
                        return new Energy();
                    case "electricity trap":
                        return new ElectricityTrap(type);
                    case "mine":
                        return new Mine();
                    case "ammo":
                        return new Ammo();
                    case "spider":
                        return new Spider(getAIBehaviour(type));
                    case "enemy spawner":
                        return new SpiderSpawnerBuilder(type).delay(2).build();
                    case "trigger":
                        return new Trigger(type);
                    case "stimulator":
                        return new Stimulator();
                    case "switchable door":
                        return new DistantContolledDoor(name, getDoorOrientation(type));
                    case "button":
                        return new Button(type);
                    case "door":
                    case "front door":
                    case "service door":
                    case "back door":
                    case "exit door":
                        return new Door(name, getDoorOrientation(type));
                    case "reactor":
                        return new CollapsingReactor();
                    default:
                        return  null;
                }
            }

            return null;
        }

        private Door.Orientation getDoorOrientation(String type) {
            return type.equals("horizontal") ? Door.Orientation.HORIZONTAL : Door.Orientation.VERTICAL;
        }

        private Behaviour<? super Alien> getAIBehaviour(String type) {
            if (type.equals("running")) {
                return new RandomlyMoving();
            } else if (type.equals("waiting1")) {
                return new Observing<>(
                    Door.DOOR_OPENED,
                    door -> door.getName().equals("front door"),
                    new RandomlyMoving()
                );
            } else if (type.equals("waiting2")) {
                return new Observing<>(
                    Door.DOOR_OPENED,
                    door -> door.getName().equals("back door"),
                    new RandomlyMoving()
                );
            } else if (type.equals("waiting and chase 1")) {
                return new Observing<>(
                    Door.DOOR_OPENED,
                    door -> door.getName().equals("service door"),
                    new Chasing()
                );
            } else if (type.equals("waiting and chase 2")) {
                return new Observing<>(
                    Door.DOOR_OPENED,
                    door -> door.getName().equals("switchable door"),
                    new Chasing(60)
                );
            } else if (type.equals("chasing")) {
                return new Chasing();
            } else return new Chasing();
        }
    }

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        Ripley ripley = scene.getFirstActorByType(Ripley.class);

        if (ripley == null) return;

        onStartGame(scene);
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

        scene.getMessageBus().subscribeOnce(CollapsingReactor.REACTOR_EXPLODED, reactor -> {
            for (Actor anActor: scene.getActors()) {
                if (anActor instanceof Alive) {
                    ((Alive) anActor).getHealth().exhaust();
                } else if (anActor instanceof Collectible) {
                    scene.removeActor(anActor);
                }
            }
        });

        scene.getMessageBus().subscribe(Door.DOOR_OPENED, door -> {
            if (door.getName().equals("exit door")) {
                scene.cancelActions(ripley);
                movableController.dispose();
                collectorController.dispose();
                shooterController.dispose();
                onEndGameSuccess(scene);
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

    private void onStartGame(Scene scene) {
        int windowHeight = scene.getGame().getWindowSetup().getHeight();
        scene.getGame().getOverlay().drawText("I hear some screams outside the door...", 30, windowHeight - 200).showFor(3);
    }

    private void onEndGameSuccess(Scene scene) {
        int windowWidth = scene.getGame().getWindowSetup().getWidth();
        scene.getGame().getOverlay().drawText("You successfully escaped!", windowWidth / 2 - 100, 100).showFor(10);
    }
}
