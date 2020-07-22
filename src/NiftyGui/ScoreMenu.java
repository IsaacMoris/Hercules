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
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.events.NiftyMousePrimaryClickedEvent;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author Besh
 */
public class ScoreMenu extends BaseAppState implements ScreenController{
    
    private Nifty nifty;
    private Screen screen;
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }
    
    @Override
    public void onStartScreen() {
        Element e = screen.findElementById("displayScore");
        
         try{
            
            String score="";
            FileReader fileReader = new FileReader("assets/Interface/Score/score.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;
            while((line = bufferedReader.readLine()) != null) {
                score +=line + "\n";
                e.getRenderer(TextRenderer.class).setText(score);
            }
            bufferedReader.close();
         }catch (Exception ex){System.out.println(ex);}
    }
    
    @NiftyEventSubscriber(id="btnBack")
    public void backToPrevious(String id, NiftyMousePrimaryClickedEvent event)
    {
        nifty.gotoScreen("start");
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
