package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.weapons.Fireable;
import sk.tuke.kpi.oop.game.weapons.Firearm;

public class Fire<A extends Armed> extends AbstractAction<A> {
    @Override
    public void execute(float deltaTime) {
        setDone(true);
        if (getActor() == null) return;
        Firearm gun = getActor().getFirearm();
        if (gun == null) return;

        Fireable bullet = gun.fire();

        if (bullet == null) return;

        Direction direction = Direction.fromAngle(getActor().getAnimation().getRotation());

        getActor().getScene().addActor(bullet, getActor().getPosX() + 16 /2, getActor().getPosY() + 32);

        new Move<Fireable>(direction, Short.MAX_VALUE, false).scheduleOn(bullet);
    }
}
