package sk.tuke.kpi.oop.game.weapons;

import sk.tuke.kpi.oop.game.characters.Armed;

public class Gun extends Firearm {
    public Gun(int initAmmo, Armed owner) {
        super(initAmmo, owner);
    }

    public Gun(int initAmmo, int maxAmmo, Armed owner) {
        super(initAmmo, maxAmmo, owner);
    }

    @Override
    protected Fireable createBullet() {
        return new Bullet(this);
    }
}
