/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.elements.events.NiftyMousePrimaryClickedEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import mygame.Main;

/**
 *
 * @author Besh
 */
public class PauseMenu extends BaseAppState implements ScreenController{
    
    private Nifty nifty;
    private NiftyJmeDisplay niftyDisplay;
    private Screen screen;
    private ViewPort viewPort;
    private Node guiNode;
    private AssetManager assetManager;
    private InputManager inputManager;
    private AudioRenderer audioRenderer;
    
    
    public void init(AppStateManager stateManager, SimpleApplication app)
    {
        this.viewPort = app.getViewPort();
        this.guiNode = app.getGuiNode();
        this.inputManager = app.getInputManager();
        this.audioRenderer = app.getAudioRenderer();
        this.assetManager = app.getAssetManager();
        
        niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager,audioRenderer,viewPort);             
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/NiftyGui.xml", "pause");
        screen = nifty.getCurrentScreen();
    }
    
    @NiftyEventSubscriber(id="btnResume")
    public void resumeGame(String id, NiftyMousePrimaryClickedEvent  event)
    {
        nifty.exit();
        Main.isResumed(true);
    }
    
    @NiftyEventSubscriber(id="btnOptions")
    public void displayOptions(String id, NiftyMousePrimaryClickedEvent  event)
    {
         nifty.gotoScreen("options");
        OptionsMenu.parentScreen = OptionsMenu.ParentScreen.pause;
    }
    
    @NiftyEventSubscriber(id="btnEndGame")
    public void endGameBtn(String id, NiftyMousePrimaryClickedEvent  event)
    {
        nifty.gotoScreen("start");
        Main.setCurrentLevel(0);
    }
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
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
