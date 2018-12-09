package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;


public class Move implements Action<Movable> {
    private boolean isExecutedEarlier = false;
    private Direction direction;
    private float duration = 0.f;
    private Movable actor;

    public Move(Direction direction) {
        this.direction = direction;
    }

    public Move(Direction direction, float duration) {
        this.direction = direction;
        this.duration = duration;
    }

    @Override
    public void execute(float deltaTime) {
        if (!isExecutedEarlier) {
            getActor().startedMoving(direction);
            isExecutedEarlier = true;
        }

        int speed = getActor().getSpeed();

        int tmpPosX = getActor().getPosX();
        int tmpPosY = getActor().getPosY();

        float length = (float)Math.sqrt((Math.pow(direction.getDx(), 2) + Math.pow(direction.getDy(), 2)));
        float dx = direction.getDx()/length;
        float dy = direction.getDy()/length;

        int posX = Math.round(getActor().getPosX() + dx * speed);
        int posY = Math.round(getActor().getPosY() + dy * speed);

        var map = getActor().getScene().getMap();

        getActor().setPosition(posX , tmpPosY);
        if (map.intersectsWithWall(getActor())) {
            getActor().setPosition(tmpPosX , tmpPosY);
        }

        getActor().setPosition(getActor().getPosX(), posY);
        if (map.intersectsWithWall(getActor())) {
            getActor().setPosition(getActor().getPosX() , tmpPosY);
        }

        duration -= deltaTime;

        if (isDone()) {
            getActor().stoppedMoving();
        }
    }

    @Nullable
    @Override
    public Movable getActor() {
        return actor;
    }

    @Override
    public void setActor(@Nullable Movable movable) {
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
        getActor().stoppedMoving();
    }
}
