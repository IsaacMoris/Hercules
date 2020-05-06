package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication
{
    private static int currentWindowIndix=0;
    private static boolean moveToNextWindow;
    private static float soundLevel;
    static boolean isMute;
    
    mainMenuScreen menu;
    level1_scene level1;
    AppSettings settings;
    
    
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
       menu = new mainMenuScreen();
       level1 = new level1_scene();
       
       //Inizialize Screens
       menu.init(stateManager, this);
       level1.init(stateManager, this);
       menu.Load();
    }

    @Override
    public void simpleUpdate(float tpf)
    {
        if(moveToNextWindow==true)
        {
            if(currentWindowIndix == 1)
            {
                menu.Unload();
                level1.Load();
            }
            //else if(moveToNextWindow == 2) To be implemented later
            moveToNextWindow = false;
        }
    }
    public static void increasecurrentWindowIndix()
    {
        currentWindowIndix++;
    }
    public static void moveToNextWindow()
    {
        moveToNextWindow = true;
    }
    
    public static void setSoundLevel(float value)
    {
        soundLevel = value;
    }
    @Override
    public void simpleRender(RenderManager rm){}
}
