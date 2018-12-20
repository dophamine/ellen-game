package sk.tuke.kpi.oop.game.addon.effects;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;

public class CorpseBurst extends AbstractExplosive {
    public CorpseBurst(Class<? extends Alive> target, int damage) {
        super(target, damage);
    }

    @Override
    protected void init() {
        setAnimation(new Animation("sprites/corpse_burst.png", 32, 32, 0.05f));
        getAnimation().setPlayMode(Animation.PlayMode.ONCE);
    }
}
