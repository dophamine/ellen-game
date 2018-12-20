package sk.tuke.kpi.oop.game.addon.effects;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;

public class MineExplosive extends AbstractExplosive {
    public MineExplosive() {
        super(Alive.class, 500, 400);
    }

    @Override
    protected void init() {
        setAnimation(new Animation("sprites/large_explosion.png", 32, 32, 0.1f));
        getAnimation().setPlayMode(Animation.PlayMode.LOOP);
    }
}
