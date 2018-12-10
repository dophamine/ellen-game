package sk.tuke.kpi.oop.game.openables;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.map.SceneMap;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.ArrayList;
import java.util.List;

public class Door extends AbstractActor implements Openable, Usable<Actor> {
    public static final Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);

    public enum Orientation {
        HORIZONTAL, VERTICAL
    }

    private boolean opened = false;
    private List<MapTile> tiles = null;

    public Door(String name, Orientation orientation) {
        super(name);

        Animation.PlayMode playMode = opened ? Animation.PlayMode.ONCE : Animation.PlayMode.ONCE_REVERSED;

        if (orientation == Orientation.HORIZONTAL) {
            setAnimation(new Animation("sprites/hdoor.png", 32,16, 0.1f, playMode));
        } else {
            setAnimation(new Animation("sprites/vdoor.png", 16,32, 0.1f, playMode));
        }
    }

    @Override
    public void useWith(Actor Actor) {
        if (isOpen()) {
            close();
        } else {
            open();
        }
    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }

    @Override
    public void open() {
        if (!isOpen()) {
            getAnimation().setPlayMode(Animation.PlayMode.ONCE);
            opened = true;
            getScene().getMessageBus().publish(DOOR_OPENED, this);
            updateCollision();
        }
    }

    @Override
    public void close() {
        if (isOpen()) {
            getAnimation().setPlayMode(Animation.PlayMode.ONCE_REVERSED);
            opened = false;
            getScene().getMessageBus().publish(DOOR_CLOSED, Door.class);
            updateCollision();
        }
    }

    @Override
    public boolean isOpen() {
        return opened;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        updateCollision();
    }

    private void updateCollision() {
        MapTile.Type type = isOpen() ? MapTile.Type.CLEAR : MapTile.Type.WALL;

        for (MapTile tile: searchOwnTiles()) {
            tile.setType(type);
        }
    }

    private List<MapTile> searchOwnTiles() {
        if (tiles != null) {
            return tiles;
        }

        SceneMap map = getScene().getMap();
        List<MapTile> foundTiles = new ArrayList<>();

        for (int w = 0; w < getAnimation().getWidth() / map.getTileWidth(); w++) {

            for (int h = 0; h < getAnimation().getHeight() / map.getTileHeight(); h++) {
                MapTile tile = map.getTile(
                    getPosX()/map.getTileWidth() + w,
                    getPosY()/map.getTileHeight() + h
                );

                foundTiles.add(tile);
            }
        }

        tiles = foundTiles;

        return tiles;
    }
}
