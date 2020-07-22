package GameLevel;

import Player.Player;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.List;
import mygame.BetterInputManager;
import mygame.Main;
import mygame.NPCManager;


public class Level2 extends Level{
    
    Node StaticGroundObjectsParent;
    
    BetterCharacterControl dragoncontrol;
    private BulletAppState bulletAppState;
    private RigidBodyControl[] scenePhy; 
    private NPCManager npcManager;
    List<Spatial> StaticGroundObjectsChildren;
    private Player playerClass ;
    
    @Override
    public void startLevel() {
        
        betterInput = new BetterInputManager(inputManager);
        bulletAppState = new BulletAppState();  //Physics Lib
        bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        stateManager.attach(bulletAppState);

        Scene = (Node) assetManager.loadModel("Scenes/BossLevel.j3o"); // Scene attachment
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
        
        processor = (FilterPostProcessor) assetManager.loadAsset("Filters/newfilter.j3f");
    }
    
    @Override
    public void update(float tpf) {
        if(BetterInputManager.Pause)
            Main.pauseButton =true;
    }
    
}
