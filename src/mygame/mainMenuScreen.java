package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.Slider;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.events.NiftyMousePrimaryClickedEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class mainMenuScreen extends BaseAppState implements ScreenController
{
    private Nifty nifty;
    private NiftyJmeDisplay niftyDisplay;
    private Screen screen;
    private ViewPort viewPort;
    private Node rootNode;
    private Node guiNode;
    private AssetManager assetManager;
    private InputManager inputManager;
    private AudioRenderer audioRenderer;
    private FlyByCamera flyCam;
    private Main app;

    public void init(AppStateManager stateManager, Application app)
    {
        this.app = (Main) app;
        this.rootNode = this.app.getRootNode();
        this.viewPort = this.app.getViewPort();
        this.guiNode = this.app.getGuiNode();
        this.inputManager = this.app.getInputManager();
        this.audioRenderer = this.app.getAudioRenderer();
        this.assetManager = this.app.getAssetManager();
        this.flyCam = this.app.getFlyByCamera();
        
        
        niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager,audioRenderer,viewPort);             
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/startMenu.xml", "start");
        screen = nifty.getCurrentScreen();
        flyCam.setEnabled(false);
    }
    
    public void Load()
    {
        viewPort.addProcessor(niftyDisplay);
    }
    
    public void Unload()
    {
        viewPort.removeProcessor(niftyDisplay);
    }
    
    
    @Override
    public void bind(Nifty nifty, Screen screen)
    {
        this.nifty = nifty;
        this.screen = screen;
    }
    
    @NiftyEventSubscriber(id="btnStart")
    public void startGame(String id, NiftyMousePrimaryClickedEvent event)
    {
        Main.increasecurrentWindowIndix();
        Main.moveToNextWindow();
    }
    
    @NiftyEventSubscriber(id="speakerON")
    public void muteSound(String id, NiftyMousePrimaryClickedEvent event)
    {
        Element speakerON = screen.findElementById(id);
        Element speakerOFF = screen.findElementById("speakerOFF");
        speakerON.setVisible(false);
        speakerOFF.setVisible(true);
        Main.isMute = true;
    }
    
    @NiftyEventSubscriber(id="speakerOFF")
    public void unMuteSound(String id, NiftyMousePrimaryClickedEvent event)
    {
        Element speakerON = screen.findElementById("speakerON");
        Element speakerOFF = screen.findElementById(id);
        speakerON.setVisible(true);
        speakerOFF.setVisible(false);
        Main.isMute = false;
    }
    
    @NiftyEventSubscriber(id="btnBack")
    public void backToMenu(String id, NiftyMousePrimaryClickedEvent event)
    {
        Element soundPanel = screen.findElementById("soundPanel");
        Element menuPanel = screen.findElementById("mainMenuPanel");      
        Element startBtn = screen.findElementById("btnStart");
        soundPanel.setVisible(false);
        menuPanel.setVisible(true);
        startBtn.setFocus();
    }
    
    @NiftyEventSubscriber(id="btnOptions")
    public void changeSoundLevel(String id, NiftyMousePrimaryClickedEvent event)
    {
        Element soundPanel = screen.findElementById("soundPanel");
        Element menuPanel = screen.findElementById("mainMenuPanel");
        soundPanel.setVisible(true);
        menuPanel.setVisible(false);
    }
    
    @NiftyEventSubscriber(id="soundSlider")
    public void controlSoundLevel(String id, NiftyMousePrimaryClickedEvent event)
    {
        Slider slider = screen.findNiftyControl(id, Slider.class);
        float value = slider.getValue();
        Main.setSoundLevel(value);
    }
    
    @NiftyEventSubscriber(id="btnExit")
    public void ExitGame(String id, NiftyMousePrimaryClickedEvent event)
    {
        System.exit(0);
    }
    @Override
    protected void initialize(Application app){}
    @Override
    protected void cleanup(Application app){}
    @Override
    protected void onEnable(){}
    @Override
    protected void onDisable(){}
    @Override
    public void onStartScreen(){}
    @Override
    public void onEndScreen(){} 
}
