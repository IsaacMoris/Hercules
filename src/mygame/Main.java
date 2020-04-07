package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        level1_scene app = new level1_scene();
        AppSettings settings = new AppSettings(true);
        settings.setHeight(720);
        settings.setWidth(1280);
        app.setSettings(settings);
        app.setShowSettings(false);
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
