package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;

public class AnimationManager {
    private final AnimControl animControl;
    private final AnimChannel animChannal;

    public AnimationManager(AnimControl control, String animName) {
        animControl = control;
        animChannal = animControl.createChannel();
        animChannal.setAnim(animName);  
    }
    
    public void setAnimation(String animName){
        try {
           String lastAnim = animChannal.getAnimationName();
           if(lastAnim.equals(animName)==false)
            animChannal.setAnim(animName, 0.5f);
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
    
    public void setIntruptAnimation(String animName){
        String lastAnim = animChannal.getAnimationName();
        setAnimation(animName);
        if(lastAnim != null)
            setAnimation(lastAnim);
    }
}
