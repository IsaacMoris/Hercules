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
import com.jme3.scene.control.AbstractControl;


public abstract class NPCManager extends AbstractControl{
    private Vector3f positionToGO;
    private float speed, zOffSet;
    protected boolean stayOnGround;

    public NPCManager(Spatial spatial) {
        super.setSpatial(spatial);
        positionToGO = new Vector3f().zero();
        zOffSet = 0f;
        speed = 0f;
        super.enabled = false;
    }

    //Setters And Getters
    public float getZOffSet() {
        return zOffSet;
    }

    public void setZOffSet(float zOffSet) {
        this.zOffSet = zOffSet;
    }

    public Vector3f getPositionToGO() {
        return positionToGO;
    }

    public void setPositionToGO(Vector3f positionToGO) {
        this.positionToGO = positionToGO;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
    
    protected void moveForward(float tpf){
        spatial.lookAt(positionToGO, Vector3f.ZERO);
        if(Math.abs(positionToGO.z - spatial.getLocalTranslation().z) > Math.abs(zOffSet)){
            if(positionToGO.z - Math.abs(zOffSet) > spatial.getLocalTranslation().z)
                spatial.move(Vector3f.UNIT_Z.mult(tpf * speed));
            else if(positionToGO.z + Math.abs(zOffSet) < spatial.getLocalTranslation().z)
                spatial.move(Vector3f.UNIT_Z.mult(tpf * speed * -1));
        }
    }
}
