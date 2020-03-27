package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.math.FastMath;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;

public class level1_scene extends SimpleApplication {

    Node Scene;
    Node player;
    private BulletAppState bulletAppState;
    private RigidBodyControl scenePhy;
    private BetterCharacterControl playerControl;
    // Controls
    private final static Trigger Jump = new KeyTrigger(KeyInput.KEY_SPACE);
    private final static Trigger Move_Forward = new KeyTrigger(KeyInput.KEY_UP);
    private final static Trigger Move_Backward = new KeyTrigger(KeyInput.KEY_DOWN);
    private final static Trigger Move_Right = new KeyTrigger(KeyInput.KEY_RIGHT);
    private final static Trigger Move_Left = new KeyTrigger(KeyInput.KEY_LEFT);

    private final static String JumpS = "Jump";
    private final static String Move_ForwardS = "Move Forward";
    private final static String Move_BackwardS = "Move Backward";
    private final static String Move_RightS = "Move Right";
    private final static String Move_LeftS = "Move Left";

    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);

        Scene = (Node) assetManager.loadModel("Scenes/firstLevelScene.j3o");
        //  scenePhy = new RigidBodyControl(0f);
        //   Scene.addControl(scenePhy);
        // bulletAppState.getPhysicsSpace().add(Scene);
        // bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0, -9.81f, 0));
        //  bulletAppState.getPhysicsSpace().setAccuracy(0.016f);
        rootNode.attachChild(Scene);

        player = (Node) Scene.getChild("Player");
      //  player.rotate(0, FastMath.DEG_TO_RAD * 180, 0);
        //player.setLocalTranslation(new Vector3f(0, 1.3f, 0));

        //   playerControl = new BetterCharacterControl(1.5f, 6f, 1f);    //        playerControl = new BetterCharacterControl(1.7f, 2.2f, 30f);
        //   player.addControl(playerControl);
        //  playerControl.setJumpForce(new Vector3f(0, 5f, 0));
        //  playerControl.setGravity(new Vector3f(0,-10f , 0));
        //   playerControl.warp(new Vector3f(0, 10, 10));
        //    bulletAppState.getPhysicsSpace().add(playerControl);
        //  bulletAppState.getPhysicsSpace().addAll(player);
        flyCam.setEnabled(false);
        flyCam.setMoveSpeed(200);
        flyCam.setZoomSpeed(200);
        ChaseCamera chaseCam = new ChaseCamera(cam, player, inputManager);
        chaseCam.setSmoothMotion(true);
        // Controls Mapping
        inputManager.addMapping(JumpS, Jump);
        inputManager.addMapping(Move_ForwardS, Move_Forward);
        inputManager.addMapping(Move_BackwardS, Move_Backward);
        inputManager.addMapping(Move_LeftS, Move_Left);
        inputManager.addMapping(Move_RightS, Move_Right);
        //Controls Listners
        inputManager.addListener(actionListener, new String[]{JumpS});
        inputManager.addListener(actionListener, new String[]{Move_ForwardS});
        inputManager.addListener(actionListener, new String[]{Move_LeftS});
        inputManager.addListener(actionListener, new String[]{Move_RightS});
        inputManager.addListener(actionListener, new String[]{Move_BackwardS});
        
         
    }

    //Action Listners
    private ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            System.out.println("You triggered: " + name);
            
            if (name.equals(JumpS) && !isPressed) {
                // implement action here
                player.move(0, 1, 0);
            }
            if (name.equals(Move_ForwardS) && !isPressed) {
               // implement action here
               
                 player.move(-1, 0, 0);

            }
            if (name.equals(Move_BackwardS) && !isPressed) {
               // implement action here
               
              player.move(1, 0, 0);

            }
            if (name.equals(Move_LeftS) && !isPressed) {
               // implement action here
             player.move(0, 0, 1);

            }
            if (name.equals(Move_RightS) && !isPressed) {
              // implement action here
              player.move(0, 0, -1);

            }
        }
    };

    @Override
    public void simpleUpdate(float tpf) {

        // player.move(-0.02f, 0.01f, 0.01f);

    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
