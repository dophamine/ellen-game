package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;

import java.util.HashSet;
import java.util.Set;


public class Reactor extends AbstractActor implements Repairable, Switchable{

    private int temperature;
    private int damage;
    private Animation normalAnimation;
    private Animation hotAnimation;
    private Animation brokenAnimation;
    private Animation offAnimation;
    private Animation damagedAnimation;
    private Animation extinguishedAnimation;
    private boolean running;
    private Set<EnergyConsumer> devices;








    public Reactor () {
        temperature = 0;
        damage = 0;
        normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        brokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        offAnimation = new Animation("sprites/reactor.png", 80, 80 );
        damagedAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
        extinguishedAnimation = new Animation("sprites/reactor_extinguished.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        devices = new HashSet<>();
        setAnimation(offAnimation);

    }

    public void turnOn(){
        running=true;
        for (EnergyConsumer dev : devices) {
            dev.setPowered(true);
        }
        updateAnimation();
    }
    public void turnOff(){
        running=false;
        for (EnergyConsumer dev : devices) {
            dev.setPowered(false);
        }
        updateAnimation();
    }

    @Override
    public boolean isOn() {
        return running;
    }

    public int getTemperature(){
        return temperature;
    }
    public int getDamage(){
        return damage;
    }

    private void updateAnimation(){
       updateAnimation2();
        if(temperature >= 6000){
            damage = 100;
            running = false;
            for (EnergyConsumer dev : devices) {
                dev.setPowered(false);
            }
            setAnimation(brokenAnimation);
        }
        if(damage == 100){
            setAnimation(brokenAnimation);
        }
        if (!running && damage < 100){
            setAnimation(offAnimation);
        }
        if (temperature == 4000 && damage == 100){
            setAnimation(extinguishedAnimation);
        }
    }

    private void updateAnimation2(){
        if(temperature >= 2000){
            setAnimation(damagedAnimation);
        }
        if (temperature < 2000){
            setAnimation(normalAnimation);
        }
        if(temperature >= 4000){
            setAnimation(hotAnimation);
        }
    }

    public void increaseTemperature(int increment){
        int value = increment;

        if(value > 0 && running) {
            if (damage >= 33 && damage <= 66) {
                value *= 1.5;
            }
            if (damage > 66) {
                value *= 2;
            }
            temperature = temperature + value;
            if (temperature > 2000) {
                damage = (temperature - 2000) / 40;
            }
            updateAnimation();
        }
    }

    public void decreaseTemperature(int decrement){
        int value2 = decrement;
        if(value2 > 0 && running) {
            if (damage > 50) {
                value2 *= 0.5;
            }
            if (damage < 100) {
                temperature = temperature - value2;
            }
            updateAnimation();
        }
    }

    public boolean repair(){
       if (damage == 0){
           return false;
       }

            if (damage<=50){
                damage=50;
            }
            else{
                damage=damage-50;
            }
            temperature = (damage*40)+2000;
            updateAnimation();
            return true;
    }

    public void repairWith(int primitiveNumber){
        System.out.println(primitiveNumber);

    }

    public boolean isRunning(){
        return running;
    }



    public boolean extinguish(){
        if (temperature >= 6000){
            temperature = 4000;
            updateAnimation();
            return true;
        }
        return false;

    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new PerpetualReactorHeating(1).scheduleOn(this);
    }

    public void addDevice(EnergyConsumer energyConsumer){
            if (energyConsumer == null){
                return;
            }
            devices.add(energyConsumer);
            if (running){
                energyConsumer.setPowered(true);
            }
    }

    public void removeDevice(EnergyConsumer energyConsumer){
        if (energyConsumer == null){
            return;
        }
        devices.remove(energyConsumer);
        energyConsumer.setPowered(false);
    }
}



