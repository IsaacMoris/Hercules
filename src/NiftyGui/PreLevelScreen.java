package NiftyGui;

import de.lessvoid.nifty.elements.Element;
import mygame.Main;

public class PreLevelScreen extends Menu{
    
    private static NextLevel nextLevel;
    private Element level1Img, level2Img;
    
    enum NextLevel{
        One,
        Two
    }
    
    
    public PreLevelScreen(){
        super("preLevel");
    }
    
    public static void setNextLevel(int level)
    {
        if(level ==1 )
        nextLevel = NextLevel.One;
        
        else if(level == 2)
        nextLevel = NextLevel.Two;
    }
    
    @Override
    public void onStartScreen() {
        level1Img = screen.findElementById("preLevel1Img");
        level2Img = screen.findElementById("preLevel2Img");
        
        if(nextLevel == NextLevel.One)
        {
            level1Img.setVisible(true);
            level2Img.setVisible(false);
            
        }
        
        else
        {
            level1Img.setVisible(false);
            level2Img.setVisible(true);
        }
    }
}
