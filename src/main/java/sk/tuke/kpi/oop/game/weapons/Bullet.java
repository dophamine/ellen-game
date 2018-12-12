package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Alive;

public class Bullet extends AbstractActor implements Fireable, Disposable {
    private Firearm owner;

    public Bullet(Firearm owner) {
        setAnimation(new Animation("sprites/bullet.png", 16, 16, 0.1f));
        setOwner(owner);
    }

    public void setOwner(Firearm owner) {
        this.owner = owner;
    }

    public Firearm getOwner() {
        return owner;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        new Loop<>(new Invoke<>(this::checkCollisionWithActor)).scheduleOn(this);
    }

    @Override
    public int getSpeed() {
        return 4;
    }

    @Override
    public void startedMoving(Direction direction) {
        getAnimation().setRotation(direction.getAngle());
    }

    @Override
    public void collidedWithWall() {
        dispose();
    }

    @Override
    public void dispose() {
        getScene().cancelActions(this);
        getScene().removeActor(this);
    }

    private void checkCollisionWithActor() {
        if (getScene() == null) return;

        for (Actor actor: getScene().getActors()) {
            if (actor != getOwner().getOwner() && intersects(actor) && actor instanceof Alive) {
                ((Alive) actor).getHealth().drain(10);
                dispose();
                break;
            }
        }
    }
}
