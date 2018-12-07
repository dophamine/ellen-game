package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;

public class Shift extends AbstractAction<Keeper<?>> {
    @Override
    public void execute(float deltaTime) {
        setDone(true);
        if (getActor() == null) {
            return;
        }

        getActor().getContainer().shift();
    }
}
