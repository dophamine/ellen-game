package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.framework.AbstractActor;

public class Computer extends AbstractActor implements EnergyConsumer{
    private Animation normalAnimation;
    private boolean elFlow;


    public Computer() {
        normalAnimation = new Animation("sprites/computer.png", 80, 48, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(normalAnimation);
    }
    public int add(int a, int b){
        if (elFlow) {
            return a + b;
        }
        else {
            return 0;
        }
    }
    public float add(float a, float b){
        if (elFlow) {
            return a + b;
        }
        else {
            return 0;
        }
    }
    public int sub(int a, int b){
        if (elFlow) {
            return a - b;
        }
        else {
            return 0;
        }
    }
    public float sub(float a, float b){
        if (elFlow) {
            return a - b;
        }
        else {
            return 0;
        }
    }

    @Override
    public void setPowered(boolean x) {
        elFlow = x;
        if (elFlow){
            setAnimation(normalAnimation);
        }
        else {
            normalAnimation.pause();
        }
    }
}
