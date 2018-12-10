package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;


public class Move<A extends Movable> implements Action<A> {
    private boolean isExecutedEarlier = false;
    private Direction direction;
    private float duration = 0.f;
    private A actor;
    private boolean avoidObstacles = true;

    public Move(Direction direction) {
        this.direction = direction;
    }

    public Move(Direction direction, float duration) {
        this.direction = direction;
        this.duration = duration;
    }

    public Move(Direction direction, float duration, boolean avoidObstacles) {
        this.direction = direction;
        this.duration = duration;
        this.avoidObstacles = avoidObstacles;
    }

    @Override
    public void execute(float deltaTime) {
        if (getActor() == null) return;

        if (!isExecutedEarlier) {
            getActor().startedMoving(direction);
            isExecutedEarlier = true;
        }

        int speed = getActor().getSpeed();

        float length = (float)Math.sqrt((Math.pow(direction.getDx(), 2) + Math.pow(direction.getDy(), 2)));
        float dx = direction.getDx()/length;
        float dy = direction.getDy()/length;

        int posX = Math.round(getActor().getPosX() + dx * speed);
        int posY = Math.round(getActor().getPosY() + dy * speed);

        if (avoidObstacles) {
            tryToAvoidObstacles(posX, posY);
        } else {
            var map = getActor().getScene().getMap();
            getActor().setPosition(posX, posY);

            if (map.intersectsWithWall(getActor())) {
                getActor().collidedWithWall();
                stop();
                return;
            }
        }

        if (getActor() != null) {
            getActor().updateMoving();
        }

        duration -= deltaTime;

        if (isDone() && getActor() != null) {
            getActor().stoppedMoving();
        }
    }

    @Nullable
    @Override
    public A getActor() {
        return actor;
    }

    @Override
    public void setActor(@Nullable A movable) {
        actor = movable;
    }

    @Override
    public boolean isDone() {
        return duration <= 1e-5;
    }

    @Override
    public void reset() {
        actor = null;
        duration = 0.f;
        isExecutedEarlier = false;
    }

    public void stop() {
        duration = 0.f;

        if (getActor() != null) {
            getActor().stoppedMoving();
        }
    }

    private void tryToAvoidObstacles(int posX, int posY) {
        int tmpPosX = getActor().getPosX();
        int tmpPosY = getActor().getPosY();
        var map = getActor().getScene().getMap();

        getActor().setPosition(posX , tmpPosY);

        if (map.intersectsWithWall(getActor())) {
            getActor().collidedWithWall();
            getActor().setPosition(tmpPosX , tmpPosY);
        }

        getActor().setPosition(getActor().getPosX(), posY);
        if (map.intersectsWithWall(getActor())) {
            getActor().setPosition(getActor().getPosX() , tmpPosY);
        }
    }
}
