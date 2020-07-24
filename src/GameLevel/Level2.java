package GameLevel;

import Hades.HadesClass;
import mygame.HealthBar;
import Player.Player;
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
import mygame.Main;
import mygame.NPCManager;

public class Level2 extends Level {

    Node StaticGroundObjectsParent;

    BetterCharacterControl dragoncontrol;
    private BulletAppState bulletAppState;
    private RigidBodyControl scenePhy;
    private NPCManager npcManager;
    List<Spatial> StaticGroundObjectsChildren;
    private Player playerClass;
    private Hades.HadesClass Hadesclass ;
    Node playerNode , Hades;
        Effects headFire , handFire;

    @Override
    public void startLevel() {

        betterInput = new BetterInputManager(inputManager);
        bulletAppState = new BulletAppState();  //Physics Lib
        bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        stateManager.attach(bulletAppState);

        Scene = (Node) assetManager.loadModel("Scenes/BossLevel.j3o"); // Scene attachment
        localRootNode.attachChild(Scene);

        scenePhy = new RigidBodyControl(0f);
        Scene.addControl(scenePhy);
        bulletAppState.getPhysicsSpace().add(scenePhy);

        // Removed Ability to only physics some items in scene
//
//        StaticGroundObjectsParent = (Node) Scene.getChild("Ground");
//        StaticGroundObjectsChildren = StaticGroundObjectsParent.getChildren();
//
//        //Scene Physics 
//        scenePhy = new RigidBodyControl[StaticGroundObjectsChildren.size()];
//        for (int i = 0; i < StaticGroundObjectsChildren.size(); i++) {
//            scenePhy[i] = new RigidBodyControl(0f);
//            //    System.out.println(StaticGroundObjectsChildren.size());
//            //   System.out.println(StaticGroundObjectsChildren.get(i).getName());
//            StaticGroundObjectsChildren.get(i).addControl(scenePhy[i]);
//            bulletAppState.getPhysicsSpace().add(scenePhy[i]);
//
//        }
        Scene.setLocalTranslation(0, 0, 0);
        bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0, -9.81f, 0));
        bulletAppState.getPhysicsSpace().setAccuracy(0.016f);

        processor = (FilterPostProcessor) assetManager.loadAsset("Filters/newfilter2.j3f");

        // Player
        CameraNode camNode = new CameraNode("CamNode", cam);
        playerClass = new Player(assetManager, bulletAppState, camNode, localGuiNode);
        playerNode = playerClass.getPlayer();
        TangentBinormalGenerator.generate(playerNode);

        Scene.attachChild(playerNode);
        cam.setFrustumPerspective(45f, (float) cam.getWidth() / cam.getHeight(), 0.01f, 1000f);
        //hades 
        Hadesclass = new HadesClass(playerNode , assetManager, bulletAppState, camNode, localGuiNode );
        Hades=Hadesclass.getHades();
       
        Scene.attachChild(Hades);
      
        headFire = new Effects("fire" , "HeadTop_End" , Scene , localRootNode,assetManager ,1.5f);
       // handFire = new Effects("fire" , "HadesRightHandMiddle" , Scene , localRootNode,assetManager ,1.5f);
        Hades.addControl(headFire);
      // Hades.addControl(handFire);
       
     
  
               
    }

    @Override
    public void update(float tpf) {
        if (BetterInputManager.Pause) {
            Main.pauseButton = true;
        }
    }

}
