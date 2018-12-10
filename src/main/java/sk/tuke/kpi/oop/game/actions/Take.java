package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

public class Take extends AbstractAction<Keeper<Collectible>> {
    Class<Collectible> takableActorsClass;

    public Take(Class<Collectible> takableActorsClass) {
        this.takableActorsClass = takableActorsClass;
    }

    @Override
    public void execute(float deltaTime) {
        setDone(true);
        if (getActor() == null) {
            return;
        }

        var collectible = getActor().getScene().getActors().stream()
            .filter(takable -> takableActorsClass.isInstance(takable) && takable.intersects(getActor()))
            .map(actor -> takableActorsClass.cast(actor))
            .findFirst()
            .orElse(null);

        if (collectible != null) {
            try {
                getActor().getContainer().add(collectible);
            } catch (IllegalStateException ex) {
                displayErrorMessage(ex.getMessage());
            } catch (Exception ex) {
                getActor().getScene().removeActor(collectible);
                displayErrorMessage(ex.getMessage());
            }

            getActor().getScene().removeActor(collectible);
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
