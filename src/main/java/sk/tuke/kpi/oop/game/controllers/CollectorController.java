package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.items.Usable;

public class CollectorController implements KeyboardListener {
    private Keeper<Collectible> actor;

    public CollectorController(Keeper<Collectible> actor) {
        this.actor = actor;
    }

    @Override
    public void keyPressed(@NotNull Input.Key key) {
        switch (key) {
            case ENTER:
                doTake();
                break;
            case BACKSPACE:
                doDrop();
                break;
            case S:
                doShift();
                break;
            case U:
                doUseNearest();
                break;
            case B:
                doUseFromBackpack();
                break;
            default:
        }
    }

    private void doTake() {
        Take<Collectible> action = new Take<>(Collectible.class);
        action.scheduleOn(actor);
    }

    private void doDrop() {
        Drop<Collectible> action = new Drop<>();
        action.scheduleOn(actor);
    }

    private void doShift() {
        new Shift().scheduleOn(actor);
    }

    private void doUseNearest() {
        Use<Actor> action = new Use<>(null);
        action.scheduleOnIntersectingWith(actor);
    }

    private void doUseFromBackpack() {
        if (actor.getContainer().getSize() == 0) return;

        var item = actor.getContainer().peek();
        if (item instanceof Collectible && item instanceof Usable) {
            @SuppressWarnings("unchecked")
            Use<Actor> action = new Use<Actor>((Usable<Actor>) item);
            action.scheduleOnIntersectingWith(actor);
        }
    }
}
