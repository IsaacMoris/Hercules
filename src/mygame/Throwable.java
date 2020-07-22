/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Mesh;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author kirlos
 */
public class Throwable extends AbstractControl{
    private float speed;
    private rayCasting collisionDetection;

    public Throwable(float speed) {
        super.setSpatial(spatial);
        this.speed = speed;
        this.enabled = true;
        
    }

    @Override
    protected void controlUpdate(float tpf) {
        spatial.lookAt(spatial.getLocalTranslation().add(Vector3f.UNIT_Z), Vector3f.ZERO);
        spatial.move(Vector3f.UNIT_Z.mult(tpf * speed));
        if()
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
    
    
}
