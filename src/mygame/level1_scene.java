package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl;

public class level1_scene extends SimpleApplication implements ActionListener {

    Node Scene;
    Node player;
    private BulletAppState bulletAppState;
    private RigidBodyControl scenePhy;
    private BetterCharacterControl playerControl;
    Spatial floor;
    @Override
    public void simpleInitApp() {

       
        
        bulletAppState = new BulletAppState();  //Physics Lib
        stateManager.attach(bulletAppState);

        Scene = (Node) assetManager.loadModel("Scenes/firstLevelScene.j3o"); // Scene attachment
        //Scene Physics 
        scenePhy = new RigidBodyControl(0f);
        Scene.addControl(scenePhy);
        Scene.setLocalTranslation(0, 0, 0);
        bulletAppState.getPhysicsSpace().add(Scene);
        bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0, -9.81f, 0));
        bulletAppState.getPhysicsSpace().setAccuracy(0.016f);
        rootNode.attachChild(Scene);

        player = (Node) Scene.getChild("Player"); // Player Attachment
        player.center();
        // Player Physics

        player.setLocalTranslation(new Vector3f(0, 10f, 0));
        playerControl = new BetterCharacterControl(1f, 1f, 10f);    //        playerControl = new BetterCharacterControl(1.7f, 2.2f, 30f);
        playerControl.setJumpForce(new Vector3f(0, 50f, 0));
        playerControl.setGravity(new Vector3f(0, 10f, 0));
        playerControl.warp(new Vector3f(0, 10, 0));
        bulletAppState.getPhysicsSpace().add(playerControl);

        player.addControl(playerControl);

        bulletAppState.setDebugEnabled(true);
//        ChaseCamera chaseCam = new ChaseCamera(cam, player, inputManager);
//        chaseCam.setSmoothMotion(true);
        // Controls Mapping
        inputManager.addMapping("Forward",
                new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Back",
                new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Rotate Left",
                new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Rotate Right",
                new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Jump",
                new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, "Rotate Left", "Rotate Right");
        inputManager.addListener(this, "Forward", "Back", "Jump");
    }

    private Vector3f walkDirection = new Vector3f(0, 0, 0);
    private Vector3f viewDirection = new Vector3f(0, 0, 1);
    private boolean rotateLeft = false, rotateRight = false,
            forward = false, backward = false;
    private float speed = 8;
    //Action Listners

    @Override
    public void onAction(String binding, boolean isPressed, float tpf) {
        if (binding.equals("Rotate Left")) {
            rotateLeft = isPressed;
        } else if (binding.equals("Rotate Right")) {
            rotateRight = isPressed;
        } else if (binding.equals("Forward")) {
            forward = isPressed;
        } else if (binding.equals("Back")) {
            backward = isPressed;
        } else if (binding.equals("Jump")) {
            playerControl.jump();
        }
    }

    private CameraNode camNode;

    @Override
    public void simpleUpdate(float tpf) {

        // System.out.println(player.getLocalTranslation());
        // Disable the default flyby cam
        flyCam.setEnabled(false);
        //create the camera Node
        camNode = new CameraNode("Camera Node", cam);
        //This mode means that camera copies the movements of the target:
        camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        //Attach the camNode to the target:
        player.attachChild(camNode);
        //Move camNode, e.g. behind and above the target:
        camNode.setLocalTranslation(new Vector3f(-5, 5, -9));
        //Rotate the camNode to look at the target:
        camNode.lookAt(player.getLocalTranslation(), Vector3f.UNIT_Y);

        // Get current forward and left vectors of the playerNode:
        Vector3f modelForwardDir = player.getWorldRotation().mult(Vector3f.UNIT_Z);
        Vector3f modelLeftDir = player.getWorldRotation().mult(Vector3f.UNIT_X);
        // Determine the change in direction
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
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
