package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;

public class Hammer extends BreakableTool<Reactor> {



    public Hammer(int uses){
        super(uses);
        setAnimation(new Animation("sprites/hammer.png", 16, 16 ));
    }

    public Hammer(){
        this(1);
    }

    @Override
    public void useWith(Reactor reactor) {
        if (reactor != null && reactor.repair()){
            use();
        }
    }
}
