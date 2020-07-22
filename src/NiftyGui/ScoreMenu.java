package NiftyGui;

import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.events.NiftyMousePrimaryClickedEvent;
import de.lessvoid.nifty.elements.render.TextRenderer;
import java.io.BufferedReader;
import java.io.FileReader;


public class ScoreMenu extends Menu{
    
    @Override
    public void onStartScreen() {
        Element e = screen.findElementById("displayScore");
        
         try{
            
             int counter = 1;
            String score="";
            FileReader fileReader = new FileReader("assets/Interface/Score/score.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;
            while((line = bufferedReader.readLine()) != null && counter<= 8) {
                {
                    score += line + "\n";
                    counter ++;
                }
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
    
}
