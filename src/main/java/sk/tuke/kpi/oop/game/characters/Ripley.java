package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.ActorContainer;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.items.Collectible;

public class Ripley extends AbstractActor implements Movable, Keeper<Collectible> {
    final static int MAX_ENERGY = 100;
    final static int MAX_BULLETS = 500;

    private Animation walkAnimation;
    private int speed = 2;
    private int energy = 85;
    private int bullets = 0;
    private Backpack backpack = new Backpack("Backpack", 10);

    public Ripley() {
        super("Ellen");
        walkAnimation = new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(walkAnimation);
        walkAnimation.stop();
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy < 0 ? 0 : energy > MAX_ENERGY ? MAX_ENERGY : energy;
    }

    public int getBullets() {
        return bullets;
    }

    public void setBullets(int bullets) {
        this.bullets = bullets < 0 ? 0 : bullets > MAX_BULLETS ? MAX_BULLETS : bullets;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void startedMoving(Direction direction) {
        walkAnimation.play();
        walkAnimation.setRotation(direction.getAngle());
    }

    @Override
    public void stoppedMoving() {
        walkAnimation.stop();
    }

    @Override
    public ActorContainer<Collectible> getContainer() {
        return backpack;
    }
}
