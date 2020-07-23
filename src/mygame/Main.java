package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import NiftyGui.*;
import GameLevel.*;
/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication
{
    private static int currentLevel=0;
    private static boolean isResumed = false;
    public static boolean pauseButton = false;
    private static boolean moveToNextLevel;
    private static float soundLevel = 0.1f;
    private static String playerName;
    Menu menu;
    Level level;
    public AppSettings settings;
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
       //settings.setHeight(1280);
       //settings.setWidth(720);
       this.setSettings(settings);
       this.setShowSettings(true);
        
        //Declare Screens
       menu = new PreLoadScreen();
       
       //Inizialize Screen
       menu.init(stateManager, this, menu); 
       menu.Load();
       
       //Audio Manager
       audioManager = new AudioManager(assetManager, "menuTrack.ogg");
       playMusic("menuTrack.ogg");
       betterInputManager = new BetterInputManager(this.getInputManager());
      
    }

    @Override
    public void simpleUpdate(float tpf)
    {
        if(the_level_is_working)
            level.update(tpf);
        
        if(moveToNextLevel == true )
        {
            currentLevel++;
            if(currentLevel-2 == 1)
            {
                level = new Level1();
                level.init(stateManager, this);
                level.Load();
                menu.Unload();
                
                the_level_is_working = true;
                moveToNextLevel = false;
                currentLevel =1;
                playMusic("basicGame.ogg");
            }
            else if(currentLevel-2 == 2) {} //To be implemented later
        }
        
        
        if(isResumed)
        {
            playMusic("basicGame.ogg");
            menu.Unload();
            level.Load();
            the_level_is_working=true;          
            isResumed = false;
        }
        if(pauseButton)
        {
            level.Unload();
            the_level_is_working=false;
            
            menu = new PauseMenu();
            menu.init(stateManager, this, menu);
            menu.Load();
            
            playMusic("menuTrack.ogg");
            
            pauseButton = false;
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