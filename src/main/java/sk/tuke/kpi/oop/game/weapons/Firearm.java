package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.Nullable;

public abstract class Firearm {
    private int ammo = 0;
    private int maxAmmo;

    public Firearm(int initAmmo) {
        this.ammo = initAmmo;
        this.maxAmmo = initAmmo;
    }

    public Firearm(int initAmmo, int maxAmmo) {
        this.ammo = initAmmo;
        this.maxAmmo = maxAmmo;
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
