package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.oop.game.weapons.Firearm;

public interface Armed extends Actor {
    @Nullable
    Firearm getFirearm();

    void setFirearm(Firearm weapon);
}
