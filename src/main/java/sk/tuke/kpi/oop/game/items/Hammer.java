package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Repairable;

public class Hammer extends BreakableTool<Repairable> implements Collectible {

    public Hammer(int uses){
        super(uses);
        setAnimation(new Animation("sprites/hammer.png", 16, 16 ));
    }

    public Hammer(){
        this(1);
    }

    @Override
    public void useWith(Repairable repairable) {
        if (repairable != null && repairable.repair()){
            use();
        }
    }

    @Override
    public Class<Repairable> getUsingActorClass() {
        return Repairable.class;
    }
}
