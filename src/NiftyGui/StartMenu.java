/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NiftyGui;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
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
public class StartMenu extends BaseAppState implements ScreenController{
    
    private Nifty nifty;
    private Screen screen;

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
        nifty.gotoScreen("levels");
    }
    
    @NiftyEventSubscriber(id="btnOptions")
    public void displayOptions(String id, NiftyMousePrimaryClickedEvent event)
    {
        nifty.gotoScreen("options");
        OptionsMenu.parentScreen = OptionsMenu.ParentScreen.start;
    }
    
    @NiftyEventSubscriber(id="btnScore")
    public void displayScore(String id, NiftyMousePrimaryClickedEvent event)
    {
        nifty.gotoScreen("score");
    }
    
    @NiftyEventSubscriber(id="btnExit")
    public void ExitGame(String id, NiftyMousePrimaryClickedEvent event)
    {
        System.exit(0);
    }
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
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
