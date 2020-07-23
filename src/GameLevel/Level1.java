package GameLevel;

import Player.Player;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.util.TangentBinormalGenerator;
import java.util.List;
import mygame.BetterInputManager;
import mygame.Effects;
import mygame.FlyableNPC;
import mygame.GamePlay;
import mygame.Main;
import mygame.NPCManager;



public class Level1 extends Level{
    
    private GamePlay GP;
    Node playerNode;
    Spatial Grass;
    Node Dummy;
    Node dragon;
    Node Coin;
    Node HealthDrink;
    Node StaticGroundObjectsParent;
    
    BetterCharacterControl dragoncontrol;
    private BulletAppState bulletAppState;
    private RigidBodyControl[] scenePhy; 
    private FlyableNPC npcManager;
    List<Spatial> StaticGroundObjectsChildren;
    private Player playerClass ;
    
    @Override
    public void startLevel() {
        
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
        playerNode = playerClass.getPlayer();
        TangentBinormalGenerator.generate(playerNode);
        
        Scene.attachChild(playerNode);
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
        
        //healthDrink
        HealthDrink = (Node) Scene.getChild("HealthDrink");
        HealthDrink.setLocalScale((float) (HealthDrink.getLocalScale().x+0.5), (float) (HealthDrink.getLocalScale().y+0.5), (float) (HealthDrink.getLocalScale().z+0.5));
        
       

       processor = (FilterPostProcessor) assetManager.loadAsset("Filters/newfilter.j3f");

        

        //Sound
        /*audioManager = new AudioManager(assetManager, "basicGame.ogg");
        audioManager.play();*/    //Moved to main 

        //NPC custom control
        npcManager = new FlyableNPC((Spatial) dragon);
        npcManager.setZOffSet(15f);
        npcManager.setSpeed(5f);
        npcManager.setEnabled(true);
        npcManager.setyOffSet(50);
        dragon.addControl(npcManager);


        //Effect
        Effects Fire = new Effects("smoke", "dragonMouth_node", Scene, localRootNode, assetManager, 5.0f);
        dragon.addControl(Fire);


        //  bulletAppState.setDebugEnabled(true);
        //ray casting
        
        //GamePlay
       // GP=new GamePlay(playerClass,Scene);
        update(1);
    }
    
    @Override
    public void update(float tpf) {
       // System.out.println("I'm working");
      
        npcManager.setPositionToGO(playerNode.getLocalTranslation());
        //GP.update();
        
        if(BetterInputManager.Pause)
            Main.pauseButton =true;
    }
    
}
