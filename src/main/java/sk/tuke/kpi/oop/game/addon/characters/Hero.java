package sk.tuke.kpi.oop.game.addon.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.openables.Door;

public class Hero extends AbstractActor {


    public Hero() {
        setAnimation(new Animation("sprites/body.png", 64, 48));
        getAnimation().setAlpha(0);
        getAnimation().setScale(0.7f);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        scene.getMessageBus().subscribeOnce(Door.DOOR_OPENED, door -> {
            if (door.getName().equals("front door")) {
                getAnimation().setAlpha(1);
                printLastWords();
            }
        });
    }

    private void printLastWords() {
        int windowHeight = getScene().getGame().getWindowSetup().getHeight();
        String text = "Unknown hero: - Run away!! Reactor is gonna to explode! Aaaarrghhhh...";
        getScene().getGame().getOverlay().drawText(text, 20, windowHeight - 100).showFor(7);
    }
}
