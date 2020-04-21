package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication  {

    public static void main(String[] args) {
       level1_scene app = new level1_scene();
        AppSettings settings = new AppSettings(true);
        settings.setHeight(600);
        settings.setWidth(800);
        app.setSettings(settings);
        app.setShowSettings(true);
        app.start();
    
   
    }

    @Override
    public void simpleInitApp()
    {
       
        
    }

   
    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
