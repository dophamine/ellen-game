package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Helicopter extends AbstractActor {

    public Helicopter(){
        setAnimation(new Animation("sprites/heli.png",64,64,0.1f, Animation.PlayMode.LOOP));
    }

    private void seek(){
        Player ellen = (Player) getScene().getFirstActorByName("Player");
        if (ellen.getPosY() < this.getPosY()){
            this.setPosition(this.getPosX(),this.getPosY()-1);
        }
        if (ellen.getPosY() > this.getPosY()){
            this.setPosition(this.getPosX(),this.getPosY()+1);
        }
        if (ellen.getPosX() < this.getPosX()){
            this.setPosition(this.getPosX()-1,this.getPosY());
        }
        if (ellen.getPosX() > this.getPosX()){
            this.setPosition(this.getPosX()+1,this.getPosY());
        }
        if (this.intersects(ellen)){
            ellen.setEnergy(ellen.getEnergy()-1);
        }
    }
    public void searchAndDestroy(){
        new Loop<>(new Invoke(this::seek)).scheduleOn(this);
    }
}
