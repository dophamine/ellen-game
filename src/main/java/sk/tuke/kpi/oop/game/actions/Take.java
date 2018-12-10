package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;

public class Take<A extends Actor> extends AbstractAction<Keeper<A>> {
    Class<A> takableActorsClass;

    public Take(Class<A> takableActorsClass) {
        this.takableActorsClass = takableActorsClass;
    }

    @Override
    public void execute(float deltaTime) {
        setDone(true);
        if (getActor() == null) {
            return;
        }

        A takable = null;
        for (var actor: getActor().getScene().getActors()) {
            if (takableActorsClass.isInstance(actor) && actor.intersects(getActor())) {
                takable = takableActorsClass.cast(actor);
                break;
            }
        }

        if (takable != null) {
            try {
                getActor().getContainer().add(takable);
            } catch (IllegalStateException ex) {
                displayErrorMessage(ex.getMessage());
            } catch (Exception ex) {
                getActor().getScene().removeActor(takable);
                displayErrorMessage(ex.getMessage());
            }
        }
    }

    private void displayErrorMessage(String message) {
        Scene scene = getActor().getScene();

        int windowHeight = scene.getGame().getWindowSetup().getHeight();
        int xTextPos = 0;
        int yTextPos = -windowHeight / 2;

        scene.getOverlay().drawText(message, xTextPos, yTextPos).showFor(2);
    }
}
