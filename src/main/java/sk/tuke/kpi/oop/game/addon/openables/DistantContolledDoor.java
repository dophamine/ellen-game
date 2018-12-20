package sk.tuke.kpi.oop.game.addon.openables;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.oop.game.Switchable;
import sk.tuke.kpi.oop.game.addon.objects.Button;
import sk.tuke.kpi.oop.game.openables.Door;

public class DistantContolledDoor extends Door implements Switchable {
    private boolean locked = true;

    public DistantContolledDoor(String name, Orientation orientation) {
        super(name, orientation);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        scene.getMessageBus().subscribe(Button.BUTTON_PRESSED, button -> {
            if (button.getType().equals(getName())) {
                if (button.isActive()) {
                    turnOn();
                } else {
                    turnOff();
                }
            }
        });
    }

    public void turnOff() {
        locked = true;
        close();
    }

    public void turnOn() {
        locked = false;
        open();
    }

    @Override
    public void open() {
        if (isOn()) {
            return;
        }

        super.open();
    }

    public boolean isOn() {
        return locked;
    }
}
