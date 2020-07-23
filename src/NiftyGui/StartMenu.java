package NiftyGui;

import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.elements.events.NiftyMousePrimaryClickedEvent;


public class StartMenu extends Menu{
    
    
    public StartMenu(){
        super("start");
    }
    
    
    @NiftyEventSubscriber(id="btnStart")
    public void startGame(String id, NiftyMousePrimaryClickedEvent event)
    {
        PreLevelScreen.nextLevel = PreLevelScreen.NextLevel.One;
            nifty.gotoScreen("preLevel");
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
