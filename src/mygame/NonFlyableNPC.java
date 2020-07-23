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
public class NonFlyableNPC extends NPCManager{

    public NonFlyableNPC(Spatial spatial) {
        super(spatial);
        stayOnGround = true;
    }
    

    @Override
    protected void controlUpdate(float tpf) {
        moveForward(tpf);
        Vector3f moveValue = Vector3f.UNIT_Y.mult(tpf * -getSpeed());
        if(moveValue.y + spatial.getLocalTranslation().y >= 0){
            spatial.move(moveValue);
        }else{
            spatial.setLocalTranslation(Vector3f.ZERO);
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
    
}
