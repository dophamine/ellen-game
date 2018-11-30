package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.Actor;

public interface Movable extends Actor {
    int getSpeed();
    default void startedMoving(Direction direction) {};
    default void stoppedMoving() {};
}
