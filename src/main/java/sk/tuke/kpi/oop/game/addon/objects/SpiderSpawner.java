package sk.tuke.kpi.oop.game.addon.objects;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.While;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Point;
import sk.tuke.kpi.oop.game.addon.behaviours.Chasing;
import sk.tuke.kpi.oop.game.addon.characters.Spider;

import java.util.Random;

public class SpiderSpawner extends AbstractActor {
    private int waves = 3;
    private int waveSize = 3;
    private float delay = 3;
    private String type;
    private boolean isActivated = false;

    protected SpiderSpawner(String type) {
        this.type = type;
        init();
    }

    protected void init() {
        setAnimation(new Animation("sprites/hole.png", 32, 32, 0.1f, Animation.PlayMode.ONCE));
        getAnimation().stop();
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        getScene().getMessageBus().subscribe(Trigger.TRIGGER_ACTIVATED, trigger -> {
            if (trigger.getType().equals(type) && !isActivated) {
                activate();
            }
        });
    }

    protected void activate() {
        isActivated = true;
        getAnimation().play();
        new ActionSequence<>(
            new Wait<>(1),
            new While<>(
                action -> waves != 0,
                new ActionSequence<>(
                    new Invoke<>(() -> {
                        decrementWaveCount();
                        spawnWave();
                    }),
                    new Wait<>(delay)
                )
            )
        ).scheduleOn(this);
    }

    private void decrementWaveCount() {
        waves--;
    }

    private void spawnWave() {
        if (getScene().getMap().intersectsWithWall(this)) {
            System.out.println("Spawner can't spawn enemies in the wall");
            return;
        }

        for (int i = 1; i <= waveSize; i++) {
            spawnEnemy();
        }
    }

    private void spawnEnemy() {
        Point pos = getNearRandomPoint();
        var actor = new Spider(new Chasing(100));
        getScene().addActor(actor, pos.getX(), pos.getY());

        var map = getScene().getMap();

        while (map.intersectsWithWall(actor)) {
            Point newPos = getNearRandomPoint();
            actor.setPosition(newPos.getX(), newPos.getY());
        }
    }

    private Point getNearRandomPoint() {
        final int maxRange = 20;
        int x = new Random().nextInt((2 * maxRange ) + 1) - maxRange + getPosX();
        int y = new Random().nextInt((2 * maxRange ) + 1) - maxRange + getPosY();

        return new Point(x, y);
    }

    public void setWaves(int waves) {
        this.waves = waves;
    }

    public void setWaveSize(int waveSize) {
        this.waveSize = waveSize;
    }

    public void setDelay(float delay) {
        this.delay = delay;
    }
}
