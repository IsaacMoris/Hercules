package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Matrix3f;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl;
import com.jme3.util.TangentBinormalGenerator;
import java.util.List;

public class level1_scene extends SimpleApplication {

    Node Scene;
    Node player;
    Spatial Grass;
    Node test;
    Node dragon;
    Node Coin;
    BetterCharacterControl dragoncontrol;
    private BulletAppState bulletAppState;
    private RigidBodyControl[] scenePhy;
    PlayerMovesControl playerMoves;
    
    private AudioManager audioManager;
    private NPCManager npcManager;
    Node T;
    List<Spatial> A;
    HealthBar healthbar;
    BetterInputManager betterInput;

    @Override
    public void simpleInitApp() {
        betterInput = new BetterInputManager(inputManager);
        bulletAppState = new BulletAppState();  //Physics Lib
        bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        stateManager.attach(bulletAppState);

        Scene = (Node) assetManager.loadModel("Scenes/TrainingLevel.j3o"); // Scene attachment
        rootNode.attachChild(Scene);

        T = (Node) Scene.getChild("Ground");
        A = T.getChildren();

        //Scene Physics 
        scenePhy = new RigidBodyControl[A.size()];
        for (int i = 0; i < A.size(); i++) {
            scenePhy[i] = new RigidBodyControl(0f);
            //    System.out.println(A.size());
            //   System.out.println(A.get(i).getName());
            A.get(i).addControl(scenePhy[i]);
            bulletAppState.getPhysicsSpace().add(scenePhy[i]);

        }

        Scene.setLocalTranslation(0, 0, 0);
        bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0, -9.81f, 0));
        bulletAppState.getPhysicsSpace().setAccuracy(0.016f);

        // Player
        player = (Node) assetManager.loadModel("Models/Hercules/Hercules.j3o");
        TangentBinormalGenerator.generate(player);
        
        Scene.attachChild(player);
        CameraNode camNode = new CameraNode("CamNode", cam);
        camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        player.attachChild(camNode);
        camNode.setLocalTranslation(0, 200, -500);
        

        test = (Node) Scene.getChild("Dummy");
        test.scale(3);
        rootNode.attachChild(test);

//      Dragon
        dragon = (Node) Scene.getChild("Dragon");
        AnimControl animControl = dragon.getChild("Armature").getControl(AnimControl.class);
        // animControl.addListener(this);
        AnimChannel animChannal = animControl.createChannel();
        animChannal.setAnim("flying");

        //Coin
        Coin = (Node) Scene.getChild("Coin");
        animControl = Coin.getChild("CoinObj").getControl(AnimControl.class);
        // animControl.addListener(this);
        animChannal = animControl.createChannel();
        animChannal.setAnim("CoinObj|Coin");

        playerMoves = new PlayerMovesControl(player, bulletAppState, camNode);
        player.addControl(playerMoves);

        FilterPostProcessor processor = (FilterPostProcessor) assetManager.loadAsset("Filters/newfilter.j3f");
        viewPort.addProcessor(processor);

        

        //Sound
        audioManager = new AudioManager(assetManager, "basicGame.ogg");
        audioManager.play();

        //NPC custom control
        npcManager = new NPCManager((Spatial) dragon);
        npcManager.setZOffSet(15f);
        npcManager.setSpeed(5f);
        npcManager.setEnabled(true);
        dragon.addControl(npcManager);

        healthbar = new HealthBar(assetManager, settings.getWidth(), settings.getHeight());
        guiNode.addControl(healthbar);
        guiNode.attachChild(healthbar.getHealth());
        guiNode.attachChild(healthbar.getFace());
        healthbar.setDamage(175);

        Effects Fire = new Effects("fire", "dragonMouth_node", Scene, rootNode, assetManager);
        dragon.addControl(Fire);

        //  bulletAppState.setDebugEnabled(true);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //dragon follow player
        npcManager.setPositionToGO(player.getLocalTranslation());
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code

    }
}
