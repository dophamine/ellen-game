package sk.tuke.kpi.oop.game.openables;

public class LockedDoor extends Door {
    private boolean locked = true;

    public LockedDoor(String name, Orientation orientation) {
        super(name, orientation);
    }

    public void lock() {
        locked = true;
        close();
    }

    public void unlock() {
        locked = false;
        open();
    }

    @Override
    public void open() {
        if (isLocked()) {
            return;
        }

        super.open();
    }

    public boolean isLocked() {
        return locked;
    }
}
