package sk.tuke.kpi.oop.game.addon.characters;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.addon.effects.CorpseBurst;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Enemy;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Spider extends Alien implements Alive, Enemy, Movable, Disposable {
    public Spider(Behaviour<? super Alien> behaviour) {
        super(10, behaviour);
    }

    protected void init() {
        getHealth().onExhaustion(() -> {
            dispose();
            burst();
        });

        setAnimation(new Animation("sprites/lurker_born.png", 32, 32, 0.1f, Animation.PlayMode.LOOP));
        getAnimation().stop();
    }

    @Override
    protected void checkCollisionWithActor() {
        if (getScene() == null) return;

        for (Actor actor: getScene().getActors()) {
            if (intersects(actor) && actor instanceof Alive && !(actor instanceof Enemy)) {
                if (((Alive)actor).getHealth().getValue() <= 0) return;

                dispose();
                burst();
                break;
            }
        }
    }

    @Override
    public int getSpeed() {
        return 1;
    }

    @Override
    public void startedMoving(Direction direction) {
        getAnimation().setRotation(direction.getAngle());
        getAnimation().play();
    }

    @Override
    public void stoppedMoving() {
        getAnimation().stop();
    }

    private void burst() {
        getScene().addActor(new CorpseBurst(Ripley.class, 10), getPosX(), getPosY());
    }

    @Override
    public void dispose() {
        getScene().cancelActions(this);
        getAnimation().stop();
        getScene().removeActor(this);
    }
}
