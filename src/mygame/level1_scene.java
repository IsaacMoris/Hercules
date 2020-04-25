package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioData;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.font.BitmapText;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix3f;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.BillboardControl;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import com.jme3.util.TangentBinormalGenerator;

public class level1_scene extends SimpleApplication implements ActionListener {

    Node Scene;
    //Node Shapes;
    Node player;
    Spatial Grass;
    Node test;
    Node dragon;
    Node Coin;
    BetterCharacterControl dragoncontrol;
    private BulletAppState bulletAppState;
    private RigidBodyControl scenePhy;
    PlayerMovesControl playerMoves;
    private AnimationManager animManager;
    private AudioManager audioManager;
    
    HealthBar healthbar ;
    
    @Override
    public void simpleInitApp() {

        bulletAppState = new BulletAppState();  //Physics Lib
        stateManager.attach(bulletAppState);

        Scene = (Node) assetManager.loadModel("Scenes/TrainingLevel.j3o"); // Scene attachment
        //Grass = Scene.getChild("Grass");
        // Scene.detachChild(Grass);
        //    Grass.removeFromParent();
        //Scene Physics 
        scenePhy = new RigidBodyControl(0f);
        Scene.addControl(scenePhy);

        Scene.setLocalTranslation(0, 0, 0);
        bulletAppState.getPhysicsSpace().add(Scene);
        bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0, -9.81f, 0));
        bulletAppState.getPhysicsSpace().setAccuracy(0.016f);

        //bulletAppState.cleanup();
        //   Grass.setLocalTranslation(10,0,10);
        //  rootNode.attachChild(Grass);
        rootNode.attachChild(Scene);
        bulletAppState.update(60);

        // Player
        player = (Node) assetManager.loadModel("Models/Hercules/HercWithAnim/herc.j3o");
        TangentBinormalGenerator.generate(player);
        player.setLocalRotation(Matrix3f.IDENTITY);

        test = (Node) Scene.getChild("Dummy");
        test.scale(3);
        rootNode.attachChild(test);

       

//      Dragon
       /* dragon = (Node) Scene.getChild("Dragon");
        AnimControl animControl = dragon.getChild("Armature").getControl(AnimControl.class);
        // animControl.addListener(this);
        AnimChannel animChannal = animControl.createChannel();
        animChannal.setAnim("flying");
        for (String anim : animControl.getAnimationNames()) {
            System.out.println(anim);
        }

        //Coin
        Coin = (Node) Scene.getChild("Coin");
        animControl = Coin.getChild("CoinObj").getControl(AnimControl.class);
        // animControl.addListener(this);
        animChannal = animControl.createChannel();
        animChannal.setAnim("CoinObj|Coin");
        for (String anim : animControl.getAnimationNames()) {
            System.out.println(anim);
        }*/

        playerMoves = new PlayerMovesControl(player, bulletAppState);

        player.addControl(playerMoves);
        //  bulletAppState.setDebugEnabled(true);
        Scene.attachChild(player);
        ChaseCamera chaseCam = new ChaseCamera(cam, player, inputManager);
        chaseCam.setSmoothMotion(true);

       // FilterPostProcessor processor = (FilterPostProcessor) assetManager.loadAsset("Filters/newfilter.j3f");
      //  viewPort.addProcessor(processor);
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

        //Sound
        audioManager = new AudioManager(assetManager, "basicGame.ogg");
        audioManager.play();
        
        
        
        healthbar=new HealthBar(assetManager,settings.getWidth(),settings.getHeight());
        guiNode.addControl(healthbar);
        guiNode.attachChild(healthbar.getHealth());
        guiNode.attachChild(healthbar.getFace());
      
        healthbar.setDamage(175);


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
            if (isPressed) {
                animManager.setAnimation("walk");
            } else {
                animManager.setAnimation("idle");
            }
        } else if (binding.equals("Back")) {
            if (isPressed) {
                animManager.setAnimation("walk");

            } else {
                animManager.setAnimation("idle");
            }
            playerMoves.setBackward(isPressed);

        } else if (binding.equals("Jump")) {
            playerMoves.setJump(isPressed);
            //animation: jump
            if (isPressed) {
                animManager.jump();
            }
        }
        
        
    }

    @Override
    public void simpleUpdate(float tpf) {

    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code

    }
}
