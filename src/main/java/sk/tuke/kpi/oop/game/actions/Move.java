package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;


public class Move<A extends Movable> implements Action<A> {
    private boolean isExecutedEarlier = false;
    private Direction direction;
    private float duration = 0.f;
    private float initialDuration;
    private A actor;
    private boolean avoidObstacles = false;
    private boolean isStopped = false;

    public Move(Direction direction) {
        this.direction = direction;
    }

    public Move(Direction direction, float duration) {
        this.direction = direction;
        this.duration = duration < 0 ? 0.f : duration;
        this.initialDuration = this.duration;
    }

    public Move(Direction direction, float duration, boolean avoidObstacles) {
        this.direction = direction;
        this.duration = duration < 0 ? 0.f : duration;
        this.initialDuration = this.duration;
        this.avoidObstacles = avoidObstacles;
    }

    @Override
    public void execute(float deltaTime) {
        if (getActor() == null || isDone()) return;

        if (!isExecutedEarlier) {
            getActor().startedMoving(direction);
            isExecutedEarlier = true;
        }

        int speed = getActor().getSpeed();
//        double length = Math.sqrt((Math.pow(direction.getDx(), 2) + Math.pow(direction.getDy(), 2)));
//        double dx = direction.getDx()/length;
//        double dy = direction.getDy()/length;

        int posX =  (getActor().getPosX() + direction.getDx() * speed);
        int posY = (getActor().getPosY() + direction.getDy() * speed);

        int tmpPosX = getActor().getPosX();
        int tmpPosY = getActor().getPosY();

        if (avoidObstacles) {
            tryToAvoidObstacles(posX, posY);
        } else {
            var map = getActor().getScene().getMap();
            getActor().setPosition(posX, posY);

            checkForIntersectedActor();

            if (getActor() == null) {
                stop();
                return;
            }

            if (map.intersectsWithWall(getActor())) {
                stop();
                getActor().setPosition(tmpPosX, tmpPosY);
                getActor().collidedWithWall();
                return;
            }
        }

        duration -= deltaTime;

        if (isDone()) {
            stop();
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
        return duration <= 1e-5 && isExecutedEarlier;
    }

    @Override
    public void reset() {
        setActor(null);
        duration = initialDuration;
        isExecutedEarlier = false;
        isStopped = false;
    }

    public void stop() {
        if (isStopped) return;

        duration = 0.f;
        isStopped = true;
        if (getActor() != null) {
            getActor().stoppedMoving();
        }
    }

    private void checkForIntersectedActor() {
        if (getActor() == null) return;

        for (var actor: getActor().getScene().getActors()) {
            if (getActor().intersects(actor)) {
                getActor().collidedWithActor(actor);
                break;
            }
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
