package sk.tuke.kpi.oop.game.behaviours;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.messages.Topic;

import java.util.function.Predicate;

public class Observing<T, A extends Actor> implements Behaviour<A> {
    private Topic<T> topic;
    private Predicate<T> predicate;
    private Behaviour<A> delegate;

    public Observing(@NotNull Topic<T> topic, @NotNull Predicate<T> predicate, @NotNull Behaviour<A> delegate) {
        this.topic = topic;
        this.predicate = predicate;
        this.delegate = delegate;
    }

    @Override
    public void setUp(A actor) {
        if (actor == null) return;
        Scene scene = actor.getScene();
        if (scene == null) return;

        scene.getMessageBus().subscribeOnce(topic, t -> {
            if (predicate.test(t)) {
                delegate.setUp(actor);
            }
        });
    }
}
