package GameLevel;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import mygame.AudioManager;
import mygame.BetterInputManager;
import mygame.Main;


public class Level extends BaseAppState{
    
    protected ViewPort viewPort;
    protected Node rootNode;
    protected Node guiNode;
    protected Camera cam;
    protected AppSettings settings;
    protected AssetManager assetManager;
    protected InputManager inputManager;
    protected AppStateManager stateManager;
    protected AudioManager audioManager;
    final protected Node localRootNode = new Node("level RootNode");
    final protected Node localGuiNode = new Node("level GuiNode");
    
    protected Node Scene;
    protected FilterPostProcessor processor;
    protected BetterInputManager betterInput;
    
    public void init(AppStateManager stateManager, SimpleApplication app)
    {
        this.rootNode = app.getRootNode();
        this.viewPort = app.getViewPort();
        this.guiNode = app.getGuiNode();
        this.assetManager = app.getAssetManager();
        this.inputManager=app.getInputManager(); 
        this.stateManager = app.getStateManager();
        this.cam = app.getCamera();
        this.settings = ((Main)app).settings;
        
        startLevel();
    }
    
    public void Load()
    {
        rootNode.attachChild(localRootNode);
        guiNode.attachChild(localGuiNode);
        viewPort.addProcessor(processor);
    }
    
    public void Unload()
    {
        rootNode.detachChild(localRootNode);
        guiNode.detachChild(localGuiNode);
        viewPort.removeProcessor(processor);
        
    }
    
    protected void startLevel(){}
    @Override
    protected void initialize(Application app) {}

    @Override
    protected void cleanup(Application app) {}

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}
        
}
