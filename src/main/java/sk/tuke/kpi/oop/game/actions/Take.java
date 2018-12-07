package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

public class Take extends AbstractAction<Keeper<Collectible>> {
    Class<Collectible> takableActorsClass = null;

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
            } catch (Exception ex) {
                if (!(ex instanceof IllegalStateException)) {
                    // TODO FIX Deletion ???
                    getActor().getScene().removeActor(collectible);
                }

                Scene scene = getActor().getScene();

                int windowHeight = scene.getGame().getWindowSetup().getHeight();
                int xTextPos = 0;
                int yTextPos = - windowHeight/2;

                scene.getOverlay().drawText(ex.getMessage(), xTextPos, yTextPos).showFor(2);
            }

            getActor().getScene().removeActor(collectible);
        }
    }
}
