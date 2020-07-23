package NiftyGui;

import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.events.NiftyMousePrimaryClickedEvent;

public class PreLoadScreen  extends Menu{
    

    
    public PreLoadScreen(){
        super("preLoad");
    }
    
    
    @Override
    public void onStartScreen() {
        Element enterBtn = screen.findElementById("btnPreLoad");
        enterBtn.setFocus();
    }
    
    @NiftyEventSubscriber(id="btnPreLoad")
    public void pressEnterToContinye(String id, NiftyMousePrimaryClickedEvent event)
    {
            nifty.gotoScreen("playerName");
    }
    
}
