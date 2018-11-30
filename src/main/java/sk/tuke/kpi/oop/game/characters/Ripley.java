package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Ripley extends AbstractActor implements Movable {
    private Animation walkAnimation;

    public Ripley() {
        super("Ellen");
        walkAnimation = new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(walkAnimation);
    }

    @Override
    public int getSpeed() {
        return 2;
    }

    @Override
    public void startedMoving(Direction direction) {
        walkAnimation.setRotation(direction.getAngle());
    }

    @Override
    public void stoppedMoving() {
        walkAnimation.pause();
    }
}
