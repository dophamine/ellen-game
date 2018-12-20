package sk.tuke.kpi.oop.game.addon.objects;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Switchable;
import sk.tuke.kpi.oop.game.characters.Alive;

public class ElectricityTrap extends AbstractActor implements Switchable {
    private boolean isActivated = true;
    private String type;

    public ElectricityTrap(String type) {
        this.type = type;
        setAnimation(new Animation("sprites/electricity.png", 16, 48, 0.1f, Animation.PlayMode.LOOP));
        updateAnimation();
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        new Loop<>(new Invoke<>(this::checkCollisionWithActor)).scheduleOn(this);

        scene.getMessageBus().subscribe(Button.BUTTON_PRESSED, button -> {
            if (type.equals(button.getType())) {
                if (button.isActive()) {
                    turnOff();
                } else {
                    turnOn();
                }
            }
        });
    }

    private void checkCollisionWithActor() {
        if (getScene() == null || !isActivated) return;

        for (Actor actor: getScene().getActors()) {
            if (intersects(actor) && actor instanceof Alive) {
                ((Alive) actor).getHealth().exhaust();
                break;
            }
        }
    }

    @Override
    public void turnOn() {
        isActivated = true;
        updateAnimation();
    }

    @Override
    public void turnOff() {
        isActivated = false;
        updateAnimation();
    }

    @Override
    public boolean isOn() {
        return isActivated;
    }

    protected void updateAnimation() {
        if (isOn()) {
            getAnimation().play();
            getAnimation().setAlpha(1);
        } else {
            getAnimation().stop();
            getAnimation().setAlpha(0);
        }
    }
}
