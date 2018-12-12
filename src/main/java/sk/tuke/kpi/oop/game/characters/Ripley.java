package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.Nullable;
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
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;

public class Ripley extends AbstractActor implements Movable, Keeper<Collectible>, Alive, Armed {
    public static Topic<Ripley> RIPLEY_DIED = new Topic<>("Ripley died", Ripley.class);
    final static int MAX_BULLETS = 500;

    private Animation walkAnimation;
    private int speed = 2;
    private Backpack backpack = new Backpack("Backpack", 10);
    private Animation dieAnimation;
    private Health health;
    private Firearm gun;

    public Ripley() {
        super("Ellen");
        walkAnimation = new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        dieAnimation = new Animation("sprites/player_die.png", 32, 32, 0.1f, Animation.PlayMode.ONCE);
        setAnimation(walkAnimation);
        walkAnimation.stop();

        health = new Health(100);
        gun = new Gun(150, 250);

        Ripley self = this;
        health.onExhaustion(new Health.ExhaustionEffect() {
            @Override
            public void apply() {
                setAnimation(dieAnimation);
                getScene().getMessageBus().publish(RIPLEY_DIED, self);
                getScene().cancelActions(self);
            }
        });
    }

    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    @Nullable
    public Firearm getFirearm() {
        return gun;
    }

    @Override
    public void setFirearm(Firearm weapon) {
        gun = weapon;
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

        overlay.drawText(
            " | HP: " + getHealth().getValue() + "/" + getHealth().getMaxValue() +
            " | Ammo: " + getFirearm().getAmmo(), 90, yTextPos
        );
    }

    public void loseEnergy() {
        getHealth().drain(1);
    }
}
