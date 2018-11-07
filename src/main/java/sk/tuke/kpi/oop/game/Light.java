package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;


public class Light extends AbstractActor implements Switchable, EnergyConsumer {
    private Animation lighton;
    private Animation lightoff;
    private boolean elflow;
    private boolean stav;


    public Light(){
        lightoff = new Animation("sprites/light_off.png",16,16);
        lighton = new Animation("sprites/light_on.png",16,16);
        setAnimation(lightoff);
    }
    public void toggle(){
       stav = !stav;
       if (elflow){
           if (stav){
               setAnimation(lighton);
           }
           else {
               setAnimation(lightoff);
           }
       }
       else {
           setAnimation(lightoff);
       }
    }
    public void setElectricityFlow(boolean energy) {
        elflow = energy;
        if (stav && elflow){
            setAnimation(lighton);
        }
        else {
            setAnimation(lightoff);
        }
    }



    @Override
    public void setPowered(boolean x) {
        elflow = x;
        if (stav && elflow){
            setAnimation(lighton);
        }
        else {
            setAnimation(lightoff);
        }
    }

    @Override
    public void turnOn() {
        stav = true;
        if (elflow) {
            setAnimation(lighton);
        }
        else {
            setAnimation(lightoff);
        }
    }

    @Override
    public void turnOff() {
        stav = false;
        setAnimation(lightoff);
    }

    @Override
    public boolean isOn() {
        return stav;
    }

    public boolean isPowered(){
        return elflow;
    }

}



