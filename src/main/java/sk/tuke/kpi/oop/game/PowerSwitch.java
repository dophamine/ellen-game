package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;

public class PowerSwitch extends AbstractActor {

    private Switchable geret;
    public boolean stav;


    public PowerSwitch(Switchable geret){
        this.geret=geret;
        setAnimation(new Animation("sprites/switch.png",16,16));
        stav = false;
        getAnimation().setTint(Color.GRAY);
    }



    public Switchable getDevice(){

        return geret;
    }



    public void switchOn(){
        if (geret != null){
            this.geret.turnOn();
            getAnimation().setTint(Color.PINK);
            stav = true;
        }
    }

    public void switchOff(){
        if (geret != null){
            this.geret.turnOff();
            getAnimation().setTint(Color.GRAY);
            stav = false;
        }
    }

    public boolean isStav() {
        return stav;
    }
}


