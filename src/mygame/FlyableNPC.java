/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;

/**
 *
 * @author kirlos
 */
public class FlyableNPC extends NPCManager {
    
    private float yOffSet;
    
    public FlyableNPC(Spatial spatial) {
        super(spatial);
        stayOnGround = false;
        yOffSet = 0;
    }

    public float getyOffSet() {
        return yOffSet;
    }

    public void setyOffSet(float yOffSet) {
        this.yOffSet = yOffSet;
    }

    public boolean isStayOnGround() {
        return stayOnGround;
    }

    public void setStayOnGround(boolean stayOnGround) {
        this.stayOnGround = stayOnGround;
    }
    
    

    @Override
    protected void controlUpdate(float tpf) {
        moveForward(tpf);
        Vector3f moveValue = Vector3f.UNIT_Y.mult(tpf * -getSpeed());
        if((stayOnGround && moveValue.y + spatial.getLocalTranslation().y >= 0)
                || (!stayOnGround && moveValue.y + spatial.getLocalTranslation().y >= yOffSet)){
            spatial.move(moveValue);
        }else if(stayOnGround){
            spatial.setLocalTranslation(Vector3f.ZERO);
        }else if((moveValue.y * -1) + spatial.getLocalTranslation().y <= yOffSet){
            spatial.move(moveValue.mult(-1f));
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
    
}
