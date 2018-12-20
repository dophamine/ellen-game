package sk.tuke.kpi.oop.game.addon.items;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.addon.effects.MineExplosive;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Enemy;
import sk.tuke.kpi.oop.game.characters.Health;

public class Mine extends AbstractActor implements Alive, Enemy, Disposable {
    private Health health = new Health(10);
    private Animation mineAnimation;
    private boolean isActivated = false;

    public Mine() {
        init();
    }

    protected void init() {
        mineAnimation = new Animation("sprites/mine.png", 16, 16, 0.1f, Animation.PlayMode.ONCE_REVERSED);
        setAnimation(mineAnimation);
        mineAnimation.stop();
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        health.onExhaustion(() -> explode());
        new Loop<>(
            new Invoke<>(this::checkCollisionWithActor)
        ).scheduleOn(this);
    }

    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public void dispose() {
        getScene().removeActor(this);
    }

    private void checkCollisionWithActor() {
        if (getScene() == null || isActivated) return;

        for (Actor actor: getScene().getActors()) {
            if (intersects(actor) && actor instanceof Alive && !(actor instanceof Enemy)) {
                explode();
                break;
            }
        }
    }

    private void explode() {
        isActivated = true;
        new ActionSequence<>(
            new Invoke<>(() -> getAnimation().play()),
            new Wait<>(mineAnimation.getFrameDuration() * mineAnimation.getFrameCount()),
            new Invoke<>(() -> getScene().addActor(new MineExplosive() {}, getPosX() - getWidth()/2, getPosY() - getHeight()/2)),
            new Invoke<>(() -> dispose())
        ).scheduleOn(this);
    }
}
