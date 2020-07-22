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
import de.lessvoid.nifty.controls.Slider;
import de.lessvoid.nifty.controls.SliderChangedEvent;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.events.NiftyMousePrimaryClickedEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import mygame.Main;

/**
 *
 * @author Besh
 */
public class OptionsMenu extends BaseAppState implements ScreenController{
    private Nifty nifty;
    private Screen screen;
    private Slider soundSlider;
    static public ParentScreen parentScreen;
    
    enum ParentScreen{
        start,
        pause
    };
    
    @Override
    public void onStartScreen() {
        soundSlider.setValue(Main.getSoundLevel()*100);
    }
    
    
    @NiftyEventSubscriber(id="soundSlider")
    public void controlSoundLevel(String id, SliderChangedEvent  event)
    {
        float value = soundSlider.getValue();
        System.out.println(value/100);
        Main.setSoundLevel(value/100);
    }
    
    @NiftyEventSubscriber(id="btnBack")
    public void backToPrevious(String id, NiftyMousePrimaryClickedEvent event)
    {    
        Element startBtn = screen.findElementById("btnStart");
        if(this.parentScreen == ParentScreen.start)
            nifty.gotoScreen("start");
        else 
            nifty.gotoScreen("pause");
    }
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
        soundSlider = screen.findNiftyControl("soundSlider", Slider.class);
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
    public void onEndScreen() {}
}
