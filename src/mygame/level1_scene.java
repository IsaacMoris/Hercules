package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl;
import com.jme3.util.TangentBinormalGenerator;

public class level1_scene extends SimpleApplication implements ActionListener {

    Node Scene;

    Node player;
    private BulletAppState bulletAppState;
    private RigidBodyControl scenePhy;
    PlayerMovesControl playerMoves;
    private AnimationManager animManager;

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

        player = (Node)assetManager.loadModel("Models/Hercules/HercWithAnim/herc.j3o");
        TangentBinormalGenerator.generate(player);
        player.setLocalRotation(Matrix3f.IDENTITY);

        playerMoves = new PlayerMovesControl(player,bulletAppState);
        player.addControl(playerMoves);

        // bulletAppState.setDebugEnabled(true);
        Scene.attachChild(player);
        ChaseCamera chaseCam = new ChaseCamera(cam, player, inputManager);
        chaseCam.setSmoothMotion(true);

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
        
        //Animation
        animManager = new AnimationManager(player);
    }

    //Action Listners
    @Override
    public void onAction(String binding, boolean isPressed, float tpf) {
        if (binding.equals("Rotate Left")) {
            playerMoves.setRotateLeft(isPressed);
        } else if (binding.equals("Rotate Right")) {
            playerMoves.setRotateRight(isPressed);

        } else if (binding.equals("Forward")) {
           playerMoves.setForward(isPressed);
           if(isPressed){
                animManager.setAnimation("walk");
            }
            else{
                animManager.setAnimation("idle");
            }
        } else if (binding.equals("Back")) {
            playerMoves.setBackward(isPressed);
        } else if (binding.equals("Jump")) {
            playerMoves.setJump(isPressed);
            //animation: jump
        }
    }

    private CameraNode camNode;

    @Override
    public void simpleUpdate(float tpf) {

    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
