package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.ActorContainer;

import java.util.*;

public class Backpack implements ActorContainer<Collectible> {
    private String name;
    private int capacity;
    private List<Collectible> container;

    public Backpack(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        container = new ArrayList<Collectible>(capacity);
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @NotNull
    @Override
    public List<Collectible> getContent() {
        return List.copyOf(container);
    }

    @Nullable
    @Override
    public Collectible peek() {
        if (container.isEmpty()) {
            return null;
        }
        return container.get(container.size() - 1);
    }

    @Override
    public void remove(@NotNull Collectible actor) {
        container.remove(actor);
    }

    @Override
    public void shift() {
        if (container.isEmpty() || container.size() == 1) {
            return;
        }

        // TODO Collections ...
        container.add(0, container.remove(container.size() - 1));
    }

    @NotNull
    @Override
    public Iterator<Collectible> iterator() {
        return container.iterator();
    }

    @Override
    public void add(@NotNull Collectible actor) {
        if (container.size() == capacity) {
            throw new IllegalStateException(getName() + " is full");
        }

        container.add(actor);
    }

    @NotNull
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSize() {
        return container.size();
    }
}
