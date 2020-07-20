package mygame;
import Player.HealthBar;
import Player.PlayerMovesControl;
import Player.*;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.math.Matrix3f;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl;
import com.jme3.system.AppSettings;
import com.jme3.util.TangentBinormalGenerator;
import java.util.List;

public class level1_scene extends BaseAppState
{
    
    private ViewPort viewPort;
    private Node rootNode;
    private Node guiNode;
    private Camera cam;
    private AppSettings settings;
    private AssetManager assetManager;
    private InputManager inputManager;
    private AppStateManager stateManager;
    private Main app;
    private Node localRootNode = new Node("level1 RootNode");
    private Node localGuiNode = new Node("level1 GuiNode");
    
    Node Scene;
    Node player;
    Spatial Grass;
    Node Dummy;
    Node dragon;
    Node Coin;
    FilterPostProcessor processor;
    rayCasting rycast;
    BetterCharacterControl dragoncontrol;
    private BulletAppState bulletAppState;
    private RigidBodyControl[] scenePhy;
    PlayerMovesControl playerMoves;
    
    private AudioManager audioManager;
    private NPCManager npcManager;
    Node StaticGroundObjectsParent;
    List<Spatial> StaticGroundObjectsChildren;
    BetterInputManager betterInput;
    private Player playerClass ;
    
    public void init(AppStateManager stateManager, Application app)
    {
        this.app = (Main) app;
        this.rootNode = this.app.getRootNode();
        this.viewPort = this.app.getViewPort();
        this.guiNode = this.app.getGuiNode();
        this.assetManager = this.app.getAssetManager();
        this.inputManager=this.app.getInputManager(); 
        this.stateManager = this.app.getStateManager();
        this.cam = this.app.getCamera();
        this.settings = this.app.settings;
        
        simpleInitApp();
    }
    
    public void simpleInitApp() {
        
        betterInput = new BetterInputManager(inputManager);
        bulletAppState = new BulletAppState();  //Physics Lib
        bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        stateManager.attach(bulletAppState);

        Scene = (Node) assetManager.loadModel("Scenes/TrainingLevel.j3o"); // Scene attachment
        localRootNode.attachChild(Scene);

        StaticGroundObjectsParent = (Node) Scene.getChild("Ground");
        StaticGroundObjectsChildren = StaticGroundObjectsParent.getChildren();

        //Scene Physics 
        scenePhy = new RigidBodyControl[StaticGroundObjectsChildren.size()];
        for (int i = 0; i < StaticGroundObjectsChildren.size(); i++) {
            scenePhy[i] = new RigidBodyControl(0f);
            //    System.out.println(StaticGroundObjectsChildren.size());
            //   System.out.println(StaticGroundObjectsChildren.get(i).getName());
            StaticGroundObjectsChildren.get(i).addControl(scenePhy[i]);
            bulletAppState.getPhysicsSpace().add(scenePhy[i]);

        }

        Scene.setLocalTranslation(0, 0, 0);
        bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0, -9.81f, 0));
        bulletAppState.getPhysicsSpace().setAccuracy(0.016f);

        // Player
        CameraNode camNode = new CameraNode("CamNode", cam);
        playerClass = new  Player(assetManager , bulletAppState,camNode,localGuiNode);
        player = playerClass.getPlayer();
        TangentBinormalGenerator.generate(player);
        
        Scene.attachChild(player);
        cam.setFrustumPerspective(45f, (float) cam.getWidth() / cam.getHeight(), 0.01f, 1000f);
        
        
       
        
          // Dummy
        Dummy = (Node) Scene.getChild("Dummy");
        Dummy.scale(3);
        localRootNode.attachChild(Dummy);

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

       

        processor = (FilterPostProcessor) assetManager.loadAsset("Filters/newfilter.j3f");

        

        //Sound
        /*audioManager = new AudioManager(assetManager, "basicGame.ogg");
        audioManager.play();*/    //Moved to main 

        //NPC custom control
        npcManager = new NPCManager((Spatial) dragon);
        npcManager.setZOffSet(15f);
        npcManager.setSpeed(5f);
        npcManager.setEnabled(true);
        dragon.addControl(npcManager);


        //Effect
        Effects Fire = new Effects("smoke", "dragonMouth_node", Scene, localRootNode, assetManager, 5.0f);
        dragon.addControl(Fire);


        //  bulletAppState.setDebugEnabled(true);
        //ray casting
        rycast=new rayCasting(localRootNode, player, Scene,cam);
        update(1);
    }
    
    @Override
    public void update(float tpf) {
       // System.out.println("I'm working");
        rycast.detect();
        //rycast.attack_detect(cam);
        npcManager.setPositionToGO(player.getLocalTranslation());
    }
    
    
    public void Load()
    {
        rootNode.attachChild(localRootNode);
        guiNode.attachChild(localGuiNode);
        viewPort.addProcessor(processor);
    }
    
    public void Unload()
    {
        //bulletAppState.cleanup(); // Physics Cleaner
        rootNode.detachChild(localRootNode);
        guiNode.detachChild(localGuiNode);
        viewPort.removeProcessor(processor);
        
    }
    @Override
    protected void initialize(Application app)
    {
    }

    @Override
    protected void cleanup(Application app)
    {
    }

    @Override
    protected void onEnable()
    {
    }

    @Override
    protected void onDisable()
    {
    }
}
