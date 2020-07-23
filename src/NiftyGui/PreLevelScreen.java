package NiftyGui;

import de.lessvoid.nifty.elements.Element;
import mygame.Main;

public class PreLevelScreen extends Menu{
    
    static NextLevel nextLevel;
    private Element level1Img, level2Img;
    
    enum NextLevel{
        One,
        Two
    }
    
    
    public PreLevelScreen(){
        super("preLevel");
    }
    
    @Override
    public void onStartScreen() {
        level1Img = screen.findElementById("preLevel1Img");
        level2Img = screen.findElementById("preLevel2Img");
        
        if(nextLevel == NextLevel.One)
        {
            level1Img.setVisible(true);
            level2Img.setVisible(false);
            
            Main.setCurrentLevel(1);
        }
        
        else
        {
            level1Img.setVisible(false);
            level2Img.setVisible(true);
            Main.setCurrentLevel(1);
        }
        
        Main.moveToNextLevel();
        //nifty.exit();
    }
}
