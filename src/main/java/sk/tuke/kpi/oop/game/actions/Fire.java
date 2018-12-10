package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.weapons.Fireable;
import sk.tuke.kpi.oop.game.weapons.Firearm;

public class Fire extends AbstractAction<Armed> {
    @Override
    public void execute(float deltaTime) {
        setDone(true);
        Firearm gun = getActor().getFirearm();
        if (gun == null) return;

        Fireable bullet = gun.fire();

        if (bullet == null) return;

        Direction direction = Direction.fromAngle(getActor().getAnimation().getRotation());

        // TODO Make correct bullet launch point

        getActor().getScene().addActor(bullet, getActor().getPosX() + 16 /2, getActor().getPosY() + 32);

        new Move(direction, Integer.MAX_VALUE, false).scheduleOn(bullet);
    }
}