package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

public class AccessCard extends AbstractActor implements Usable<LockedDoor>, Collectible {
    public AccessCard() {
        setAnimation(new Animation("sprites/key.png", 16,16, 0.1f));
    }

    @Override
    public void useWith(LockedDoor door) {
        if (door.isLocked()) {
            door.unlock();
        } else {
            door.lock();
        }
    }

    @Override
    public Class<LockedDoor> getUsingActorClass() {
        return LockedDoor.class;
    }
}
