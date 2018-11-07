package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.DefectiveLight;

public class Wrench extends BreakableTool<DefectiveLight> {

    public Wrench(){
        super(2);
        setAnimation(new Animation("sprites/wrench.png", 16, 16 ));
    }
    @Override
    public void useWith(DefectiveLight defectiveLight) {
        if (defectiveLight == null){
            return;
        }
        if (defectiveLight.repair()){
            use();
        }
    }
}
