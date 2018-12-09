package sk.tuke.kpi.oop.game.items;

public interface Usable<T>{
    void useWith(T actor);

    Class<T> getUsingActorClass();
}
