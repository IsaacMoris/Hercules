package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.input.ChaseCamera;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import javax.swing.text.JTextComponent;


public class level1_scene extends SimpleApplication {

     
      Node Scene  ;
      Node player ;
   

    @Override
    public void simpleInitApp()
    {
        
        Scene=  (Node)assetManager.loadModel("Scenes/firstLevelScene.j3o");
        player = (Node)Scene.getChild("Player");
       
        rootNode.attachChild(Scene);
        flyCam.setEnabled(false);
        flyCam.setMoveSpeed(200);
        flyCam.setZoomSpeed(200);
        ChaseCamera chaseCam = new ChaseCamera(cam, player, inputManager);
        chaseCam.setSmoothMotion(true);
        
       
        
    }
    @Override
    public void simpleUpdate(float tpf) {
        
       player.move(.001f, 0, 0);
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
