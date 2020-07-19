package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication
{
    private static int currentLevel=0;
    private static boolean isResumed = false;
    private static boolean moveToNextLevel;
    private static float soundLevel = 0.1f;
    private static String playerName;
    NiftyGui menu;
    level1_scene level1;
    AppSettings settings;
    BetterInputManager betterInputManager;
    boolean the_level_is_working=false;
    
    static private AudioManager audioManager;
    
    public static void main(String[] args)
    {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp()
    {
       settings = new AppSettings(true);
       settings.setHeight(1280);
       settings.setWidth(720);
       this.setSettings(settings);
       this.setShowSettings(true);
        
        //Declare Screens
       menu = new NiftyGui();
       level1 = new level1_scene();
       
       //Inizialize Screens
       menu.init(stateManager, this, "start"); 
       menu.Load();
       
       //Audio Manager
       audioManager = new AudioManager(assetManager, "menuTrack.ogg");
       playMusic("menuTrack.ogg");
       betterInputManager = new BetterInputManager(this.getInputManager());
       level1 = new level1_scene();
      
    }

    @Override
    public void simpleUpdate(float tpf)
    {
        if(the_level_is_working)
            level1.update(tpf);
        if(moveToNextLevel == true )
        {
            if(currentLevel == 1)
            {
                menu.Unload();
                level1.init(stateManager, this);
                level1.Load();
                the_level_is_working=true;
                level1.update(tpf);
                
                playMusic("basicGame.ogg");
            }
            else if(currentLevel == 2) {} //To be implemented later
            moveToNextLevel = false;
        }
        
        
        if(isResumed)
        {
            playMusic("basicGame.ogg");
            menu.Unload();
           if(currentLevel == 1){
            level1.Load();
            the_level_is_working=true;
           }
               
           else if(currentLevel == 2){} //To be implemented later
           
           isResumed = false;
        }
        if(BetterInputManager.Pause && currentLevel > 0 && isResumed == false )
        {
            level1.Unload();
            the_level_is_working=false;
            menu.gotoPauseMenu();
            menu.Load();
            
            playMusic("menuTrack.ogg");
        }
    }
    public static void setCurrentLevel(int level)
    {
        currentLevel = level;
    }
    public static void moveToNextLevel()
    {
        moveToNextLevel = true;
    }
    
    public static void setPlayerName(String name)
    {
        playerName = name;
    }
    
    public static void isResumed(boolean answer)
    {
        isResumed = answer;
    }
    
    
    public static void setSoundLevel(float value)
    {
        soundLevel = value;
        audioManager.setVolume(value);
    }
    
    public static float getSoundLevel()
    {
        return soundLevel;
    }
    
    private void playMusic(String audioName)
    {
        audioManager.setTrack(audioName);
        audioManager.setVolume(soundLevel);
        audioManager.play();
    }
    @Override
    public void simpleRender(RenderManager rm){}
}