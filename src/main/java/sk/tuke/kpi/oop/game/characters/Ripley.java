package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.ActorContainer;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Overlay;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.items.Collectible;

public class Ripley extends AbstractActor implements Movable, Keeper<Collectible> {
    public static Topic<Ripley> RIPLEY_DIED = new Topic<>("Ripley died", Ripley.class);
    final static int MAX_ENERGY = 100;
    final static int MAX_BULLETS = 500;

    private Animation walkAnimation;
    private int speed = 2;
    private int energy = 85;
    private int bullets = 0;
    private Backpack backpack = new Backpack("Backpack", 10);
    private Animation dieAnimation;

    public Ripley() {
        super("Ellen");
        walkAnimation = new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        dieAnimation = new Animation("sprites/player_die.png", 32, 32, 0.1f, Animation.PlayMode.ONCE);
        setAnimation(walkAnimation);
        walkAnimation.stop();
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy < 0 ? 0 : energy > MAX_ENERGY ? MAX_ENERGY : energy;

        if (energy == 0) {
            setAnimation(dieAnimation);
            getScene().getMessageBus().publish(RIPLEY_DIED, this);
        }
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

    public void showRipleyState() {
        Overlay overlay = getScene().getGame().getOverlay();
        int windowHeight = getScene().getGame().getWindowSetup().getHeight();
        int topOffset = GameApplication.STATUS_LINE_OFFSET;
        int yTextPos = windowHeight - topOffset;

        overlay.drawText(" | Energy: " + getEnergy() + " | Ammo: " + getBullets(), 90, yTextPos);
    }

    public void loseEnergy() {
        setEnergy(energy - 1);
    }
}
