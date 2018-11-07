package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class SmartCooler extends Cooler {
    public SmartCooler(Reactor reactor) {
        super(reactor);
    }

    public void cool(){
        if (getReactor() == null){
            return;
        }
        if (get().getTemperature() >= 2500){
            turnOn();
        }
        if (get().getTemperature() <= 1500){
            turnOff();
        }
        if (isOn()){
            coolReactor();
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke(this::cool)).scheduleOn(this);
    }
}
