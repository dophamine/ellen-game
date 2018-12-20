package sk.tuke.kpi.oop.game.addon.objects;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.items.Usable;

public class Button extends AbstractActor implements Usable<Actor> {
    public static final Topic<Button> BUTTON_PRESSED = Topic.create("button pressed", Button.class);

    private Animation activeAnim;
    private Animation inactiveAnim;
    private boolean isActive = false;
    private String type;

    public Button(String type) {
        this.type = type;
        activeAnim = new Animation("sprites/button_green.png", 16, 16);
        inactiveAnim = new Animation("sprites/button_red.png", 16, 16);
        activeAnim.setScale(0.5f);
        activeAnim.setAlpha(0.5f);
        inactiveAnim.setScale(0.5f);
        inactiveAnim.setAlpha(0.5f);
        updateAnimation();
    }

    public String getType() {
        return type;
    }

    @Override
    public void useWith(Actor actor) {
        isActive = isActive ? false : true;

        getScene().getMessageBus().publish(BUTTON_PRESSED, this);
        updateAnimation();
    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }

    private void updateAnimation() {
        setAnimation(isActive ? inactiveAnim : activeAnim);
    }

    public boolean isActive() {
        return isActive;
    }
}


