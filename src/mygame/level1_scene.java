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
import com.jme3.util.TangentBinormalGenerator;

public class level1_scene extends SimpleApplication implements ActionListener {

    Node Scene;
    //Node Shapes;
    Node player;
    Spatial Grass; Node test;
    Node dragon; Node Coin;
    BetterCharacterControl dragoncontrol;
    private BulletAppState bulletAppState;
    private RigidBodyControl scenePhy;
    PlayerMovesControl playerMoves;
    private AnimationManager animManager;
    private AudioManager audioManager;
     public int health=100;
    BitmapText helloText;
    Geometry healthbar;
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
                //bulletAppState.update(60);

        
        // Player
        player = (Node)assetManager.loadModel("Models/Hercules/HercWithAnim/herc.j3o");
        TangentBinormalGenerator.generate(player);
        player.setLocalRotation(Matrix3f.IDENTITY);
        
        
        test = (Node) Scene.getChild("Dummy");
        test.scale(3);
        rootNode.attachChild(test);
        
        BillboardControl billboard = new BillboardControl();
        healthbar = new Geometry("healthbar", new Quad(5f, 0.2f));
        healthbar.scale(50);
        Material mathb = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
        mathb.setColor("Color", ColorRGBA.Red);
        healthbar.setMaterial(mathb);
        player.attachChild(healthbar);
        //healthbar.center();
        healthbar.move(-80, 250, -20);
        healthbar.addControl(billboard);
        
//        Dragon
        dragon =(Node) Scene.getChild("Dragon");
       AnimControl animControl = dragon.getChild("Armature").getControl(AnimControl.class);
       // animControl.addListener(this);
      AnimChannel  animChannal = animControl.createChannel();
       animChannal.setAnim("flying");
         for (String anim : animControl.getAnimationNames()) {
    System.out.println(anim);
}
         
      //Coin
      Coin =(Node) Scene.getChild("Coin");
        animControl = Coin.getChild("CoinObj").getControl(AnimControl.class);
       // animControl.addListener(this);
        animChannal = animControl.createChannel();
       animChannal.setAnim("CoinObj|Coin");
         for (String anim : animControl.getAnimationNames()) {
    System.out.println(anim);}
      


        playerMoves = new PlayerMovesControl(player,bulletAppState);
        
       // Scene.attachChild(dragon);
        
       
//        sword=player.getChild("Models/Sword/sword.j3o");
        
        
        //Scene.attachChild(sword);
        
       
        
                /** Write text on the screen (HUD) */
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        helloText = new BitmapText(guiFont, false);
        helloText.setSize(guiFont.getCharSet().getRenderedSize());
        helloText.setText("Health:"+health);
        helloText.setLocalTranslation(300, helloText.getLineHeight(), 0);
        guiNode.attachChild(helloText);
 
        
        
           player.addControl(playerMoves);
      //  bulletAppState.setDebugEnabled(true);
        Scene.attachChild(player);
        ChaseCamera chaseCam = new ChaseCamera(cam, player, inputManager);
        chaseCam.setSmoothMotion(true);

        FilterPostProcessor processor = (FilterPostProcessor) assetManager.loadAsset("Filters/newfilter.j3f");
viewPort.addProcessor(processor);
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
            if(isPressed){
            animManager.setAnimation("walk");
            
            }
            else{
                animManager.setAnimation("idle");
            }
            playerMoves.setBackward(isPressed);
            
        } else if (binding.equals("Jump")) {
            playerMoves.setJump(isPressed);
            //animation: jump
            if(isPressed){
                animManager.jump();
            }            
        }
    }

    private CameraNode camNode;
    float x=1;
    @Override
    public void simpleUpdate(float tpf) {
     if (player.getWorldBound().intersects( test.getWorldBound() ) && health!=0 ){
         health-=0.01f;
         //healthbar.s;
                 helloText.setText("Health:"+health);
         if (health== 0){
                 
                 helloText.setText("You are dead");
         
         }
                          
        //to listen to the played audio
        //listener.setLocation(cam.getLocation());
        //listener.setRotation(cam.getRotation());
     }
//          Scene.detachChild(sword);
//          
//          Scene.detachChild(swo);
//         swo.removeFromParent();
//         sword.removeFromParent();
//          bulletAppState.cleanup();
//System.out.println("Intersect: "+x);x++;
//      
   //}
     
  

     
     
       //  dragon.setLocalTranslation(player.getLocalTranslation().x,10,2);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
       
    }
}
