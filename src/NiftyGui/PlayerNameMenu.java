package NiftyGui;

import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.elements.events.NiftyMousePrimaryClickedEvent;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.input.NiftyStandardInputEvent;
import mygame.Main;


public class PlayerNameMenu  extends Menu{
    
    
    public PlayerNameMenu(){
        super("playerName");
    }
    
    @NiftyEventSubscriber(id="btnPlayerNameOk")
    public void setPlayerNameByOkBtn(String id, NiftyMousePrimaryClickedEvent event)
    {
        String name = getTextFieldText("playerNameField");
        if(name.length()==0) return;
        Main.setPlayerName(name);
        nifty.gotoScreen("start");
    }
    
    @NiftyEventSubscriber(id="playerNameField")
    public void setPlayerNameByHittingEnter(String id, NiftyInputEvent event)
    {
        if(event == NiftyStandardInputEvent.SubmitText)
           setPlayerNameByOkBtn( "", null );
    }
    
    public String getTextFieldText(String id)
    {
         TextField field = nifty.getCurrentScreen().findNiftyControl(id, TextField.class);
         return field.getRealText();
    }
    public void setTextFieldText(String id, String text)
    {
         TextField field = nifty.getCurrentScreen().findNiftyControl(id, TextField.class);
         field.setText(text);
    }
}
