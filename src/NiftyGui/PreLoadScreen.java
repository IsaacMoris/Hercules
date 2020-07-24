package NiftyGui;

import de.lessvoid.nifty.elements.Element;

public class PreLoadScreen  extends Menu{
    
    private static State state;
    private Element preLoadImg;
    private Element winImg;
    private Element loseImg;
    public enum State{
        none,
        winner,
        loser
    }
    
    public PreLoadScreen(){
        super("preLoad");
    }
    
    @Override
    public void onStartScreen() {
        preLoadImg = screen.findElementById("preLoadImg");
        winImg = screen.findElementById("winImg");
        loseImg = screen.findElementById("LoseImg");
        
        preLoadImg.setVisible(false);
        winImg.setVisible(false);
        loseImg.setVisible(false);
        
        
        if(this.state == State.winner)
        {
            winImg.setVisible(true);
            nifty.gotoScreen("start");
        }
        
        else if(this.state == State.loser)
        {
            loseImg.setVisible(true);
            nifty.gotoScreen("start");
        }
        
        else
        {
            preLoadImg.setVisible(true);
            nifty.gotoScreen("playerName");
        }
    }
    
    
    public void setState(State state)
    {
        this.state = state;
    }
}
