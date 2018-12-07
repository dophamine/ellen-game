package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.ActorContainer;

import java.util.*;

public class Backpack implements ActorContainer<Collectible> {
    private String name;
    private int capacity;
    private ArrayList<Collectible> backpack;

    public Backpack(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        backpack = new ArrayList(capacity);
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @NotNull
    @Override
    public List<Collectible> getContent() {
        return List.copyOf(backpack);
    }

    @Nullable
    @Override
    public Collectible peek() {
        if (backpack.size() == 0) {
            return null;
        }
        return backpack.get(backpack.size() - 1);
    }

    @Override
    public void remove(@NotNull Collectible actor) {
        backpack.remove(actor);
    }

    @Override
    public void shift() {
        if (backpack.size() == 0 || backpack.size() == 1) {
            return;
        }

        // TODO Collections ...
        backpack.add(0, backpack.remove(backpack.size() - 1));
    }

    @NotNull
    @Override
    public Iterator<Collectible> iterator() {
        return backpack.iterator();
    }

    @Override
    public void add(@NotNull Collectible actor) {
        if (backpack.size() == capacity) {
            throw new IllegalStateException(getName() + " is full");
        }

        backpack.add(actor);
    }

    @NotNull
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSize() {
        return backpack.size();
    }
}
