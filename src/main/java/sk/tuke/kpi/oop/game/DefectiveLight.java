package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class DefectiveLight extends Light implements Repairable{

    private Disposable blick;
    private boolean repaired;

    public void blinking(){
        repaired = false;
        if((int)(Math.random()*10) == 3){
            turnOn();
        }
        else {
            turnOff();
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        blick = new Loop<>(new Invoke(this::blinking)).scheduleOn(this);
    }


    @Override
    public boolean repair() {
        if (!repaired && blick != null) {
            blick.dispose();
            repaired = true;
            new ActionSequence<>(new Wait(10), new Loop<>(new Invoke(this::blinking))).scheduleOn(this);
            return true;
        }
        return false;
    }


}
