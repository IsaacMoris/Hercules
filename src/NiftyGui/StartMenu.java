package NiftyGui;

import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.elements.events.NiftyMousePrimaryClickedEvent;
import mygame.Main;


public class StartMenu extends Menu{

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
}
