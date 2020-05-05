/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.CameraNode;
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
    private float speed = 8;
    private InputManager inputManager;
    private CameraNode camNode;

    public PlayerMovesControl(Spatial player, BulletAppState bulletAppState, CameraNode camNode) {

        spatial = player;
        this.bulletAppState = bulletAppState;
        this.inputManager = inputManager;
        this.camNode = camNode;

        spatial.setLocalTranslation(new Vector3f(8f, 1f, 0));
        playerControl = new BetterCharacterControl(0.5f, 4f, 77f); // playerControl = new BetterCharacterControl(radius, Height, Weight);
        playerControl.setJumpForce(new Vector3f(0f, 200f, 0f));
        playerControl.setGravity(new Vector3f(0, 10f, 0));
        playerControl.warp(new Vector3f(0, 10, 0));

        spatial.addControl(playerControl);
        bulletAppState.getPhysicsSpace().add(playerControl);
        //intialKeys();
    }

    @Override
    protected void controlUpdate(float tpf) {

        Vector3f modelForwardDir = spatial.getWorldRotation().mult(Vector3f.UNIT_Z);
        Vector3f modelSideDir = spatial.getWorldRotation().mult(Vector3f.UNIT_X);
        walkDirection.set(0, 0, 0);

        if (BetterInputManager.Forward) {
            walkDirection.addLocal(modelForwardDir.mult(speed));
        } else if (BetterInputManager.BackWard) {
            walkDirection.addLocal(modelForwardDir.mult(speed).negate());
        }
        if (BetterInputManager.Right) {
            walkDirection.addLocal(modelSideDir.mult(speed).negate());
        } else if (BetterInputManager.Left) {
            walkDirection.addLocal(modelSideDir.mult(speed));
        }
        playerControl.setWalkDirection(walkDirection); // walk!

        if (BetterInputManager.Jump) {
            playerControl.jump();
        }

        Quaternion rotateRL = new Quaternion().
                fromAngleAxis(FastMath.PI * (BetterInputManager.MouseX), Vector3f.UNIT_Y);
        rotateRL.multLocal(viewDirection);
        playerControl.setViewDirection(viewDirection); // turn!
        
        
        Quaternion rotateUD = new Quaternion().
                fromAngleAxis(FastMath.PI * (BetterInputManager.MouseY), Vector3f.UNIT_X);
        camNode.rotate(rotateUD);
        BetterInputManager.MouseX = BetterInputManager.MouseY = 0;
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

}
