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
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.events.NiftyMousePrimaryClickedEvent;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.input.NiftyStandardInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author Besh
 */
public class LevelsMenu extends BaseAppState implements ScreenController{
    
    private Nifty nifty;
    private Screen screen;
    private Element levelPasswordPanel;
    
     @NiftyEventSubscriber(pattern="btnLevel_.*")
    public void startLevelOne(String id, NiftyMousePrimaryClickedEvent event)
    {
       levelPasswordPanel.setVisible(true);
       Element levelPassInput = screen.findElementById("levelPasswordField");
       levelPassInput.setFocus();
    }
    
    @NiftyEventSubscriber(id="btnCancelLevelPassword")
    public void cancelLevelPasswordBtn(String id, NiftyMousePrimaryClickedEvent event)
    {    
        levelPasswordPanel.setVisible(false);
        setTextFieldText("levelPasswordField", "");
    }
    
    @NiftyEventSubscriber(id="btnSubmitLevelPassword")
    public void submitlLevelPasswordBtn(String id, NiftyMousePrimaryClickedEvent event)
    {
        String password = getTextFieldText("levelPasswordField");
        if(password.length()<1) return;
        
        System.out.println(password);
        levelPasswordPanel.setVisible(false);
        setTextFieldText("levelPasswordField", "");
    }
    
    @NiftyEventSubscriber(id="levelPasswordField")
    public void submitlLevelPasswordByHittingEnter(String id, NiftyInputEvent event)
    {
        if(event == NiftyStandardInputEvent.SubmitText)
           submitlLevelPasswordBtn( "", null );
    }
    
    
    @NiftyEventSubscriber(id="btnBack")
    public void backToMainMenu(String id, NiftyMousePrimaryClickedEvent event)
    {    
            nifty.gotoScreen("start");

    }
    
    private String getTextFieldText(String id)
    {
         TextField field = nifty.getCurrentScreen().findNiftyControl(id, TextField.class);
         return field.getRealText();
    }
    
    
    private void setTextFieldText(String id, String text)
    {
         TextField field = nifty.getCurrentScreen().findNiftyControl(id, TextField.class);
         field.setText(text);
    }
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
        levelPasswordPanel = screen.findElementById("levelPasswordPanel");
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
