/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kirlos
 */
public class ThrowableObject extends AbstractControl{
    private final float speed;
    private CollisionResults collisionDetection;
    private final ArrayList<Geometry> collectables;

    public ThrowableObject(Spatial spatial, ArrayList<Geometry> collectables, float speed) {
        super.setSpatial(spatial);
        this.speed = speed;
        this.enabled = true;
        this.collectables = collectables;
    }

    @Override
    protected void controlUpdate(float tpf) {
        //spatial.lookAt(spatial.getLocalTranslation().add(Vector3f.UNIT_Z), Vector3f.ZERO);
        spatial.move(Vector3f.UNIT_Z.mult(tpf * speed));
        for(Geometry obj : collectables){
            spatial.collideWith(obj, collisionDetection);
            if(!collisionDetection.equals(obj)){
                try {
                    this.finalize();
                } catch (java.lang.Throwable ex) {
                    Logger.getLogger(ThrowableObject.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
    
}
