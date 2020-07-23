/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;

/**
 *
 * @author kirlos
 */
public final class Animation extends AnimationManager{
    private final AnimChannel animChannal;

    public Animation(AnimControl control, String animName) {
        super(control);
        animChannal = animControl.createChannel();
        setAnimation(animName);
    }

    @Override
    public void setAnimation(String animName) {
        try {
           String lastAnim = animChannal.getAnimationName();
           if(lastAnim == null)
                animChannal.setAnim(animName, 0.5f);
           else if(!lastAnim.equals(animName))
                animChannal.setAnim(animName, 0.5f);
           
        } catch (Exception e) {
            System.out.print("Animation Name Not Found .. Error: " + e);
        }
    }
    public String  getAnimation()
    {
        return  animChannal.getAnimationName();
    }
    @Override
    public void setAnimation(String name, float speed) {
        setAnimation(name);
        animChannal.setSpeed(speed);
    }

    @Override
    public void setAnimation(String name, float speed, LoopMode loopMode) {
        setAnimation(name, speed);
        animChannal.setLoopMode(loopMode);
    }
    
}
