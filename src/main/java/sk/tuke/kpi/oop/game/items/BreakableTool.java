package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;

public abstract class BreakableTool<T extends Actor> extends AbstractActor implements Usable<T> {
    private int remainingUses;

    public BreakableTool(int uses) {
        remainingUses = uses;
    }

    @Override
    public void useWith(T actor) {
        use();
    }

    public void use() {
        remainingUses -= 1;
        if (remainingUses == 0) {
            getScene().removeActor(this);
        }
    }

    public int getRemainingUses () {
            return remainingUses;
        }
}
