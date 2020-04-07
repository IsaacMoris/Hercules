/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author isaac
 */
public class PlayerMovesControl extends AbstractControl {

    private BetterCharacterControl playerControl;
    private BulletAppState bulletAppState;
    private Vector3f walkDirection = new Vector3f(0, 0, 0);
    private Vector3f viewDirection = new Vector3f(0, 0, 1);
    private boolean rotateLeft = false, rotateRight = false , forward = false, backward = false , jump=false ;
    private float speed = 8;
    
    public PlayerMovesControl(Spatial player , BulletAppState  bulletAppState) {

        spatial=player;
        this.bulletAppState=bulletAppState;
        
        spatial.setLocalTranslation(new Vector3f(8f, 1f, 0));
        playerControl = new BetterCharacterControl(0.5f, 2.3f, 77f);    //        playerControl = new BetterCharacterControl(radius, Height, Weight);
        playerControl.setJumpForce(new Vector3f(0, 200f, 0));
        playerControl.setGravity(new Vector3f(0, 10f, 0));
        playerControl.warp(new Vector3f(0, 10, 0));
        
        spatial.addControl(playerControl);
        bulletAppState.getPhysicsSpace().add(playerControl);
    }

    @Override
    protected void controlUpdate(float tpf) 
    {
        
        
        Vector3f modelForwardDir = spatial.getWorldRotation().mult(Vector3f.UNIT_Z);
        Vector3f modelLeftDir = spatial.getWorldRotation().mult(Vector3f.UNIT_X);
        walkDirection.set(0, 0, 0);
        
        if (forward) {
            walkDirection.addLocal(modelForwardDir.mult(speed));
        } else if (backward) {
            walkDirection.addLocal(modelForwardDir.mult(speed).
                    negate());
        }
        playerControl.setWalkDirection(walkDirection); // walk!
        // Determine the change in rotation
        if (rotateLeft) {
            Quaternion rotateL = new Quaternion().
                    fromAngleAxis(FastMath.PI * tpf, Vector3f.UNIT_Y);
            rotateL.multLocal(viewDirection);
        } else if (rotateRight) {
            Quaternion rotateR = new Quaternion().
                    fromAngleAxis(-FastMath.PI * tpf, Vector3f.UNIT_Y);
            rotateR.multLocal(viewDirection);
        }
        playerControl.setViewDirection(viewDirection); // turn!
        
        if(jump)
            playerControl.jump();
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    public void setRotateLeft(boolean rotateLeft) {
        this.rotateLeft = rotateLeft;
    }

    public void setRotateRight(boolean rotateRight) {
        this.rotateRight = rotateRight;
    }

    public void setForward(boolean forward) {
        this.forward = forward;
    }

    public void setBackward(boolean backward) {
        this.backward = backward;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

}
