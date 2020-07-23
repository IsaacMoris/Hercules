/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import java.util.ArrayList;

/**
 *
 * @author kirlos
 */
public final class BetterAnimation extends AnimationManager {
    private final AnimChannel UpperBodyChannal, LowerBodyChannal;

    /**
     *
     * @param control
     * @param animName
     * @param UpperBones
     * @param LowerBones
     */
    public BetterAnimation(AnimControl control, String animName, ArrayList<String> UpperBones, ArrayList<String> LowerBones) {
        super(control);
        
        UpperBodyChannal = animControl.createChannel();
        setUpperBodyBones(UpperBones);
        this.setUpperBodyAnimation(animName);
        
        LowerBodyChannal = animControl.createChannel();
        setLowerBodyBones(LowerBones);
        setLowerBodyAnimation(animName);
    }
    
    private void setUpperBodyBones(ArrayList<String> UpperBones){
        for(String bone : UpperBones){
            UpperBodyChannal.addFromRootBone(animControl.getSkeleton().getBone(bone));
        }
    }

    private void setLowerBodyBones(ArrayList<String> LowerBones) {
        for(String bone : LowerBones){
            LowerBodyChannal.addFromRootBone(animControl.getSkeleton().getBone(bone));
        }
    }

    @Override
    public void setAnimation(String animName) {
        setUpperBodyAnimation(animName);
        setLowerBodyAnimation(animName);
    }

    @Override
    public void setAnimation(String name, float speed) {
        setUpperBodyAnimation(name, speed);
        setLowerBodyAnimation(name, speed);
    }

    @Override
    public void setAnimation(String name, float speed, LoopMode loopMode) {
        setUpperBodyAnimation(name, speed, loopMode);
        setLowerBodyAnimation(name, speed, loopMode);
    }
    
    
    public void setUpperBodyAnimation(String animName) {
        try {
           String UpperBodyAnim = UpperBodyChannal.getAnimationName();
           if(UpperBodyAnim == null)
               UpperBodyChannal.setAnim(animName, 0.5f);
            else if (!UpperBodyAnim.equals(animName))
                UpperBodyChannal.setAnim(animName, 0.5f);

        } catch (Exception e) {
            System.out.print("Animation Name Not Found .. Error: " + e);
        }
    }
    
    
    public void setUpperBodyAnimation(String name, float speed) {
        setUpperBodyAnimation(name);
        UpperBodyChannal.setSpeed(speed);
    }


    public void setUpperBodyAnimation(String name, float speed, LoopMode loopMode) {
        setUpperBodyAnimation(name, speed);
        UpperBodyChannal.setLoopMode(loopMode);
    }
    
    public void setLowerBodyAnimation(String animName) {
        try {
           String LowerBodyAnim = LowerBodyChannal.getAnimationName();
           if(LowerBodyAnim == null)
               LowerBodyChannal.setAnim(animName, 0.5f);
           else if(!LowerBodyAnim.equals(animName))
               LowerBodyChannal.setAnim(animName, 0.5f);

        } catch (Exception e) {
            System.out.print("Animation Name Not Found .. Error: " + e);
        }
    }
    
    public void setLowerBodyAnimation(String name, float speed) {
        setUpperBodyAnimation(name);
        LowerBodyChannal.setSpeed(speed);
    }


    public void setLowerBodyAnimation(String name, float speed, LoopMode loopMode) {
        setUpperBodyAnimation(name, speed);
        LowerBodyChannal.setLoopMode(loopMode);
    }

    
}
