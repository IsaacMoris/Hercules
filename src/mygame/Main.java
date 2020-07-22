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
    PlayerNameMenu startMenu;
    PauseMenu pauseMenu;
    Level1 level1;
    Level2 level2;
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
       startMenu = new PlayerNameMenu();
       level1 = new Level1();
       
       //Inizialize Screens
       startMenu.init(stateManager, this); 
       startMenu.Load();
       
       //Audio Manager
       audioManager = new AudioManager(assetManager, "menuTrack.ogg");
       playMusic("menuTrack.ogg");
       betterInputManager = new BetterInputManager(this.getInputManager());
      
    }

    @Override
    public void simpleUpdate(float tpf)
    {
        if(the_level_is_working)
            level2.update(tpf);//level1.update(tpf);
        if(moveToNextLevel == true )
        {
            if(currentLevel == 1)
            {
                startMenu.Unload();
                /*level1 = new Level1();
                level1.init(stateManager, this);
                level1.Load();*/
                the_level_is_working=true;
                //level1.update(tpf);
                level2 = new Level2();
                level2.init(stateManager, this);
                level2.Load();
                
                playMusic("basicGame.ogg");
            }
            else if(currentLevel == 2) {} //To be implemented later
            moveToNextLevel = false;
        }
        
        
        if(isResumed)
        {
            playMusic("basicGame.ogg");
            pauseMenu.Unload();
           if(currentLevel == 1){
            level1.Load();
            the_level_is_working=true;
           }
               
           else if(currentLevel == 2){} //To be implemented later
           
           isResumed = false;
        }
        if(pauseButton)
        {
            //level1.Unload();
            level2.Unload();
            the_level_is_working=false;
            
            pauseMenu = new PauseMenu();
            pauseMenu.init(stateManager, this);
            pauseMenu.Load();
            
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