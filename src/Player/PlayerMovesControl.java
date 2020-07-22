/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Player;

import com.jme3.animation.AnimControl;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.input.InputManager;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import mygame.AnimationManager;
import mygame.BetterInputManager;

/**
 *
 * @author isaac
 */
public class PlayerMovesControl extends AbstractControl {

    private AnimationManager animManager;

    private BetterCharacterControl playerControl;
    private BulletAppState bulletAppState;

    public AnimationManager getAnimManager() {
        return animManager;
    }
    private Vector3f walkDirection = new Vector3f(0, 0, 0);
    private Vector3f viewDirection = new Vector3f(0, 0, 1);
    private float speed = 8;
    private CameraNode camNode;

    public PlayerMovesControl(Spatial player, BulletAppState bulletAppState, CameraNode camNode) {

        spatial = player;
        this.bulletAppState = bulletAppState;
        this.camNode = camNode;

        player.setLocalRotation(Matrix3f.IDENTITY);

        spatial.setLocalTranslation(new Vector3f(8f, 1f, 0));
        playerControl = new BetterCharacterControl(0.5f, 4f, 77f); // playerControl = new BetterCharacterControl(radius, Height, Weight);
        playerControl.setJumpForce(new Vector3f(0f, 200f, 0f));
        playerControl.setGravity(new Vector3f(0, 10f, 0));
        playerControl.warp(new Vector3f(0, 10, 0));

        spatial.addControl(playerControl);
        bulletAppState.getPhysicsSpace().add(playerControl);

        //Animation
        animManager = new AnimationManager(((Node) player).getChild("Armature").getControl(AnimControl.class), "idle");
        //intialKeys();
    }
//float time;           Delay

    @Override
    protected void controlUpdate(float tpf) {
//time+=tpf;
//System.out.println(time);
        Vector3f modelForwardDir = spatial.getWorldRotation().mult(Vector3f.UNIT_Z);
        Vector3f modelSideDir = spatial.getWorldRotation().mult(Vector3f.UNIT_X);
        walkDirection.set(0, 0, 0);
//if (time>0.4){
        //   time=0;
        if (BetterInputManager.Forward && BetterInputManager.Run) {
            walkDirection.addLocal(modelForwardDir.mult(speed * 2));
            animManager.setAnimation("running", 1);
        } else if (BetterInputManager.Forward) {
            walkDirection.addLocal(modelForwardDir.mult(speed));
            animManager.setAnimation("walk", 1);
        } else if (BetterInputManager.BackWard) {
            walkDirection.addLocal(modelForwardDir.mult(speed).negate());
            animManager.setAnimation("walk_backwards");
        } else if (BetterInputManager.Right) {
            animManager.setAnimation("right strife walk", 1);
            walkDirection.addLocal(modelSideDir.mult(speed).negate());
        } else if (BetterInputManager.Left) {
            walkDirection.addLocal(modelSideDir.mult(speed));
            animManager.setAnimation("left strife walk ", 1);
        } else if (BetterInputManager.Punch) {
            animManager.setAnimation("punch" , 1);

        } else if (BetterInputManager.Power_Punch) {
            animManager.setAnimation("cross punch with left", 1);

        } else if (BetterInputManager.Sword_Attack) {
            animManager.setAnimation("sword skill 3", 1);
        } else {
            animManager.setAnimation("idle");
        }

        if (BetterInputManager.Jump) {

            animManager.setAnimation("jump");
            playerControl.jump();

        }
        //}
        playerControl.setWalkDirection(walkDirection); // walk!

        Quaternion rotateRL = new Quaternion().
                fromAngleAxis(FastMath.PI * (BetterInputManager.MouseX), Vector3f.UNIT_Y);
        rotateRL.multLocal(viewDirection);
        playerControl.setViewDirection(viewDirection); // turn!
        
        float theta = (float) (Math.PI / 2 - camNode.getCamera().getDirection().angleBetween(modelForwardDir) - 1e-6);
        Quaternion rotateUD = new Quaternion().
                fromAngleAxis(BetterInputManager.dir * Float.min(FastMath.PI * (BetterInputManager.MouseY), theta), Vector3f.UNIT_X);

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
