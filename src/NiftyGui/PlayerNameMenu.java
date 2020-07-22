package NiftyGui;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.elements.events.NiftyMousePrimaryClickedEvent;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.input.NiftyStandardInputEvent;
import mygame.Main;


public class PlayerNameMenu  extends Menu{
    
    private NiftyJmeDisplay niftyDisplay;
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
        nifty.fromXml("Interface/NiftyGui.xml", "playerName");
        screen = nifty.getCurrentScreen();
    }
    
    @NiftyEventSubscriber(id="btnPlayerNameOk")
    public void setPlayerNameByOkBtn(String id, NiftyMousePrimaryClickedEvent event)
    {
        String name = getTextFieldText("playerNameField");
        if(name.length()==0) return;
        Main.setPlayerName(name);
        nifty.gotoScreen("start");
    }
    
    @NiftyEventSubscriber(id="playerNameField")
    public void setPlayerNameByHittingEnter(String id, NiftyInputEvent event)
    {
        if(event == NiftyStandardInputEvent.SubmitText)
           setPlayerNameByOkBtn( "", null );
    }
    
    public String getTextFieldText(String id)
    {
         TextField field = nifty.getCurrentScreen().findNiftyControl(id, TextField.class);
         return field.getRealText();
    }
    public void setTextFieldText(String id, String text)
    {
         TextField field = nifty.getCurrentScreen().findNiftyControl(id, TextField.class);
         field.setText(text);
    }
    
    public void Load()
    {
        this.viewPort.addProcessor(niftyDisplay);
    }
    public void Unload()
    {
        this.viewPort.removeProcessor(niftyDisplay);
    }
}
