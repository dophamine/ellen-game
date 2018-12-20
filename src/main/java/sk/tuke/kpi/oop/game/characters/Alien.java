package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

public class Alien extends AbstractActor implements Movable, Enemy, Alive {
    public static class AlienMother extends Alien {
        private Health health;

        public AlienMother() {
            health = new Health(200);
            init();
        }

        public AlienMother(int healthValue, Behaviour<? super Alien> behaviour) {
            super(healthValue, behaviour);
            init();
        }

        protected void init() {
            super.init();
            setAnimation(new Animation("sprites/mother.png", 112, 162, 0.2f));
            getAnimation().stop();
        }
    }

    private Health health;
    private Behaviour<? super Alien> behaviour = null;

    public Alien() {
        health = new Health(100);
        init();
    }

    public Alien(int healthValue, Behaviour<? super Alien> behaviour) {
        this.behaviour = behaviour;
        health = new Health(healthValue);
        init();
    }

    protected void init() {
        health.onExhaustion(() -> {
            getScene().cancelActions(this);
            getAnimation().stop();
            getScene().removeActor(this);
        });

        setAnimation(new Animation("sprites/alien.png", 32, 32, 0.1f));
        getAnimation().stop();
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        if (behaviour != null) {
            behaviour.setUp(this);
        }

        onSceneBehaviour();
    }

    protected void onSceneBehaviour() {
        new Loop<>(new Invoke<>(this::checkCollisionWithActor)).scheduleOn(this);
    }

    @Override
    public int getSpeed() {
        return 1;
    }

    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public void startedMoving(Direction direction) {
        getAnimation().play();
        getAnimation().setRotation(direction.getAngle());
    }

    @Override
    public void stoppedMoving() {
        getAnimation().stop();
    }

    protected void checkCollisionWithActor() {
        if (getScene() == null) return;

        for (Actor actor: getScene().getActors()) {
            if (intersects(actor) && actor instanceof Alive && !(actor instanceof Enemy)) {
                ((Alive) actor).getHealth().drain(1);
                break;
            }
        }
    }
}
