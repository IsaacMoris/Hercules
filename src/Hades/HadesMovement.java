/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hades;

import com.jme3.animation.AnimControl;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import mygame.Animation;
import mygame.AnimationManager;
import mygame.FlyableNPC;
import mygame.NPCManager;

/**
 *
 * @author Mariv
 */
public class HadesMovement extends AbstractControl {

    Node Hercules;
    private AnimationManager animManager;
    private BetterCharacterControl HadesControl;
    private int speed = 4;
    private Vector3f walkDirection = new Vector3f(0, 0, 0);

    public HadesMovement(Node Hercules, Node Hades, BulletAppState bulletAppState) {
        this.spatial = Hades;
        this.Hercules = Hercules;

        animManager = new Animation(((Node) Hades).getChild("Armature").getControl(AnimControl.class), "idle");

        HadesControl = new BetterCharacterControl(0.5f, 4f, 100f); //new BetterCharacterControl(radius, Height, Weight);
        HadesControl.setJumpForce(new Vector3f(0f, 200f, 0f));
        HadesControl.setGravity(new Vector3f(0, 10f, 0));
        HadesControl.warp(new Vector3f(0, 10, 0));
        spatial.addControl(HadesControl);
        bulletAppState.getPhysicsSpace().add(HadesControl);
    }

    @Override
    public void controlUpdate(float tpf) {

        walkDirection.set(0, 0, 0);
        double distance = Hercules.getLocalTranslation().distance(spatial.getLocalTranslation());
        Vector3f Dir = Hercules.getLocalTranslation().add(spatial.getLocalTranslation().negate()).normalize();

        HadesControl.setViewDirection(Dir);

        if (distance < 5) {
            animManager.setAnimation("punch");

        } else if (distance < 30) {
            animManager.setAnimation("flying2", 1);
            walkDirection.addLocal(Dir.mult(speed));
            System.out.println(walkDirection);

        } else {

            animManager.setAnimation("idle");
        }

        HadesControl.setWalkDirection(walkDirection);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

}
