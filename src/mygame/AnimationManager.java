package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.scene.Node;

public class AnimationManager {
    Node player;
    private final AnimControl animControl;
    private final AnimChannel animChannal;

    public AnimationManager(Node player) {
        this.player = player;
        animControl = player.getChild("Armature").getControl(AnimControl.class);
       // animControl.addListener(this);
        animChannal = animControl.createChannel();
       animChannal.setAnim("idle");
    }
    
    public void setAnimation(String name){
        try {
            animChannal.setAnim(name);
        } catch (Exception e) {
            System.out.print("Animation Name Not Found .. Error: " + e);
        }
    }
    
    public void setAnimation(String name, float speed){
        setAnimation(name);
        animChannal.setSpeed(speed);
    }
    
    public void setAnimation(String name, float speed, LoopMode loopMode){
        setAnimation(name, speed);
        animChannal.setLoopMode(loopMode);
    }
    
}
