package NiftyGui;

import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.elements.events.NiftyMousePrimaryClickedEvent;
import mygame.Main;


public class PauseMenu extends Menu{

    public PauseMenu(){
        super("pause");
    }
    
    @NiftyEventSubscriber(id="btnResume")
    public void resumeGame(String id, NiftyMousePrimaryClickedEvent  event)
    {
        nifty.exit();
        Main.isResumed(true);
    }
    
    @NiftyEventSubscriber(id="btnOptions")
    public void displayOptions(String id, NiftyMousePrimaryClickedEvent  event)
    {
         nifty.gotoScreen("options");
        OptionsMenu.parentScreen = OptionsMenu.ParentScreen.pause;
    }
    
    @NiftyEventSubscriber(id="btnEndGame")
    public void endGameBtn(String id, NiftyMousePrimaryClickedEvent  event)
    {
        nifty.gotoScreen("start");
    }
}
