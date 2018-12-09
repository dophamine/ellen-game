package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.ActorContainer;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.gamelib.graphics.Overlay;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.CollectorController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.*;

public class FirstSteps implements SceneListener {
    private Ripley player = new Ripley();

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        scene.addActor(player,0,0);
        ActorContainer<Collectible> backpack = player.getContainer();
        scene.getGame().pushActorContainer(player.getContainer());

        MovableController controller = new MovableController(player);
        scene.getInput().registerListener(controller);
        scene.getInput().registerListener(new CollectorController(player));

        scene.addActor(new Energy(), 110, 120);

        scene.addActor(new Ammo(), -10, 160);

        backpack.add(new Ammo());
        backpack.add(new Energy());
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        player.showRipleyState();
    }
}
