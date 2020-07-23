package NiftyGui;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;


public class Menu extends BaseAppState implements ScreenController{
    
    protected Nifty nifty;
    protected Screen screen;
    protected NiftyJmeDisplay niftyDisplay;
    protected ViewPort viewPort;
    protected Node guiNode;
    protected AssetManager assetManager;
    protected InputManager inputManager;
    protected AudioRenderer audioRenderer;
    protected String screennID;
    
    
    Menu(String screenID){
        this.screennID = screenID;
    }
    
    public void init(AppStateManager stateManager, SimpleApplication app, Menu menu)
    {
        this.viewPort = app.getViewPort();
        this.guiNode = app.getGuiNode();
        this.inputManager = app.getInputManager();
        this.audioRenderer = app.getAudioRenderer();
        this.assetManager = app.getAssetManager();
        
        niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager,audioRenderer,viewPort);             
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/NiftyGui.xml", menu.screennID);
        screen = nifty.getCurrentScreen();
    }
    
    public void Load()
    {
        this.viewPort.addProcessor(niftyDisplay);
    }
    
    public void Unload()
    {
        this.viewPort.removeProcessor(niftyDisplay);
    }
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }
    @Override
    protected void initialize(Application app) {}
    @Override
    protected void cleanup(Application app) {}
    @Override
    protected void onEnable() {}
    @Override
    protected void onDisable() {}
    @Override
    public void onStartScreen() {}
    @Override
    public void onEndScreen() {}
    
}
