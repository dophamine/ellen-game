package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.items.Usable;

public class CollectorController implements KeyboardListener {
    private Keeper<Collectible> player;

    public CollectorController(Keeper<Collectible> player) {
        this.player = player;
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
        action.scheduleOn(player);
    }

    private void doDrop() {
        Drop<Collectible> action = new Drop<>();
        action.scheduleOn(player);
    }

    private void doShift() {
        new Shift().scheduleOn(player);
    }

    private void doUseNearest() {
        Scene scene = player.getScene();

        Actor anActor = scene.getActors().stream()
            .filter(actor -> actor.intersects(player) && actor instanceof Usable && !(actor instanceof Collectible))
            .findFirst()
            .orElse(null);

        if (anActor == null) return;

        @SuppressWarnings("unchecked")
        Usable<Actor> usable = (Usable<Actor>) anActor;
        Use<Actor> action = new Use<>(usable);
        action.scheduleOn(player);
    }

    private void doUseFromBackpack() {
        if (player.getContainer().getSize() == 0) return;

        Collectible item = null;

        try {
            item = player.getContainer().peek();
        } catch (Exception e) {
            item = null;
        }

        if (item instanceof Collectible && item instanceof Usable) {
            @SuppressWarnings("unchecked")
            Use<Actor> action = new Use<Actor>((Usable<Actor>) item);
            action.scheduleOnIntersectingWith(player);
        }
    }
}
