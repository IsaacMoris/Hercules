package mygame;

import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;

public abstract class AnimationManager {
    protected AnimControl animControl;

    public AnimationManager(AnimControl animControl) {
        this.animControl = animControl;
    }
    
    public abstract void setAnimation(String animName);
    public abstract void setAnimation(String name, float speed);
    public abstract void setAnimation(String name, float speed, LoopMode loopMode);
}
