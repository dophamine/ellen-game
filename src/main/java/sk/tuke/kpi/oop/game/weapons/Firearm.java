package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.oop.game.characters.Armed;

public abstract class Firearm {
    private int ammo;
    private int maxAmmo;
    private Armed owner;

    public Firearm(int initAmmo, Armed owner) {
        this.ammo = initAmmo;
        this.maxAmmo = initAmmo;
        setOwner(owner);
    }

    public Firearm(int initAmmo, int maxAmmo, Armed owner) {
        this.ammo = initAmmo;
        this.maxAmmo = maxAmmo;
        setOwner(owner);
    }

    public void setOwner(Armed owner) {
        this.owner = owner;
    }

    public Armed getOwner() {
        return owner;
    }

    protected abstract Fireable createBullet();

    protected void setAmmo(int amount) {
        this.ammo = amount < 0 ? 0 : amount > maxAmmo ? maxAmmo : amount;
    }

    public int getAmmo() {
        return ammo;
    }

    public void reload(int newAmmo) {
        setAmmo(getAmmo() + newAmmo);
    }

    @Nullable
    public Fireable fire() {
        boolean hasAmmo = getAmmo() > 0;
        setAmmo(getAmmo() - 1);

        return hasAmmo ? createBullet() : null;
    }
}
