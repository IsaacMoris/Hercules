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
import de.lessvoid.nifty.controls.SliderChangedEvent;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.events.NiftyMousePrimaryClickedEvent;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.input.NiftyStandardInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class NiftyGui extends BaseAppState implements ScreenController
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
    
    private Element menuPanel;
    private Element soundControlPanel;
    private Element playerNamePanel;
    private Element levelsPanel;
    private Element levelPasswordPanel;
    private Element pausePanel;
    private Slider soundSlider;

    public void init(AppStateManager stateManager, Application app, String screenId)
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
        nifty.fromXml("Interface/NiftyMenus.xml", screenId);
        screen = nifty.getCurrentScreen();
        //flyCam.setEnabled(false);
        
    }
    
    public void Load()
    {
        viewPort.addProcessor(niftyDisplay);
    }
    
    public void Unload()
    {
        viewPort.removeProcessor(niftyDisplay);
    }
    
    public void gotoPauseMenu()
    {
        nifty.gotoScreen("pause");
    }
    
    @Override
    public void bind(Nifty nifty, Screen screen)
    {
        this.nifty = nifty;
        this.screen = screen;
        
        // declared here to use the same instance
        menuPanel = screen.findElementById("mainMenuPanel");
        soundControlPanel = screen.findElementById("soundPanel");
        playerNamePanel = screen.findElementById("playerNamePanel");
        levelsPanel = screen.findElementById("levelsPanel");
        levelPasswordPanel = screen.findElementById("levelPasswordPanel");
        pausePanel = screen.findElementById("pausePanel");
        soundSlider = screen.findNiftyControl("soundSlider", Slider.class);
    }
    
    
    // Player Name Panel Buttons & Text Fields
    @NiftyEventSubscriber(id="btnPlayerNameOk")
    public void setPlayerNameByOkBtn(String id, NiftyMousePrimaryClickedEvent event)
    {
        String name = getTextFieldText("playerNameField");
        if(name.length()==0) return;
        Main.setPlayerName(name);
        
        menuPanel.setVisible(true);
        playerNamePanel.setVisible(false);
    }
    
    @NiftyEventSubscriber(id="playerNameField")
    public void setPlayerNameByHittingEnter(String id, NiftyInputEvent event)
    {
        if(event == NiftyStandardInputEvent.SubmitText)
           setPlayerNameByOkBtn( "", null );
    }
    
    
    
    // Main Menue Buttons
    @NiftyEventSubscriber(id="btnStart")
    public void startGame(String id, NiftyMousePrimaryClickedEvent event)
    {
        nifty.exit();
        Main.setCurrentLevel(1);
        Main.moveToNextLevel();
    }
    
    @NiftyEventSubscriber(id="btnLevels")
    public void displayLevels(String id, NiftyMousePrimaryClickedEvent  event)
    {
        levelsPanel.setVisible(true);
        menuPanel.setVisible(false);
    }
    
    @NiftyEventSubscriber(id="btnOptions")
    public void displayOptions(String id, NiftyMousePrimaryClickedEvent event)
    {
        soundControlPanel.setVisible(true);
        menuPanel.setVisible(false);
        soundSlider.setValue(Main.getSoundLevel()*100);// To make start and pause screens sliders have the same value
    }
    
    @NiftyEventSubscriber(id="btnExit")
    public void ExitGame(String id, NiftyMousePrimaryClickedEvent event)
    {
        System.exit(0);
    }
    
    
    
    // Pause Menu Scrreen
    @NiftyEventSubscriber(id="btnEndGame")
    public void endGameBtn(String id, NiftyMousePrimaryClickedEvent  event)
    {
        nifty.gotoScreen("start");
        Main.setCurrentLevel(0);
    }
    
    @NiftyEventSubscriber(id="btnResume")
    public void resumeGame(String id, NiftyMousePrimaryClickedEvent  event)
    {
        nifty.exit();
        Main.isResumed(true);
    }
    
    @NiftyEventSubscriber(id="btnBackPauseScreen")
    public void backToPauseMenu(String id, NiftyMousePrimaryClickedEvent  event)
    {
        pausePanel.setVisible(true);
        soundControlPanel.setVisible(false);
    }
    @NiftyEventSubscriber(id="btnOptionsPauseScreen")
    public void displayOptionsPauseScreen(String id, NiftyMousePrimaryClickedEvent  event)
    {
        soundControlPanel.setVisible(true);
        pausePanel.setVisible(false);
        soundSlider.setValue(Main.getSoundLevel()*100); // To make start and pause screens sliders have the same value
    }
    
    
    
    // Sound Panel Buttons & Slider
    @NiftyEventSubscriber(id="soundSlider")
    public void controlSoundLevel(String id, SliderChangedEvent  event)
    {
        float value = soundSlider.getValue();
        System.out.println(value/100);
        Main.setSoundLevel(value/100);
    }
    
    @NiftyEventSubscriber(id="btnBack")
    public void backToMainMenu(String id, NiftyMousePrimaryClickedEvent event)
    {    
        Element startBtn = screen.findElementById("btnStart");
        soundControlPanel.setVisible(false);
        levelsPanel.setVisible(false);
        levelPasswordPanel.setVisible(false);
        menuPanel.setVisible(true);
        startBtn.setFocus();
    }
    
    
    // Levels Panel Buttons
    @NiftyEventSubscriber(pattern="btnLevel_.*")
    public void startLevelOne(String id, NiftyMousePrimaryClickedEvent event)
    {
       levelPasswordPanel.setVisible(true);
       Element levelPassInput = screen.findElementById("levelPasswordField");
       levelPassInput.setFocus();
    }
    
    @NiftyEventSubscriber(id="btnCancelLevelPassword")
    public void cancelLevelPasswordBtn(String id, NiftyMousePrimaryClickedEvent event)
    {    
        levelPasswordPanel.setVisible(false);
        setTextFieldText("levelPasswordField", "");
    }
    
    @NiftyEventSubscriber(id="btnSubmitLevelPassword")
    public void submitlLevelPasswordBtn(String id, NiftyMousePrimaryClickedEvent event)
    {
        String password = getTextFieldText("levelPasswordField");
        if(password.length()<1) return;
        
        System.out.println(password);
        levelPasswordPanel.setVisible(false);
        setTextFieldText("levelPasswordField", "");
    }
    
    @NiftyEventSubscriber(id="levelPasswordField")
    public void submitlLevelPasswordByHittingEnter(String id, NiftyInputEvent event)
    {
        if(event == NiftyStandardInputEvent.SubmitText)
           submitlLevelPasswordBtn( "", null );
    }
    
    private String getTextFieldText(String id)
    {
         TextField field = nifty.getCurrentScreen().findNiftyControl(id, TextField.class);
         return field.getRealText();
    }
    private void setTextFieldText(String id, String text)
    {
         TextField field = nifty.getCurrentScreen().findNiftyControl(id, TextField.class);
         field.setText(text);
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
