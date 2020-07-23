/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Player;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author isaac
 */
public class Score extends AbstractControl {

    private int ScoreCounter = 0;
    BitmapFont myFont;
    BitmapText hudText;

    public Score(Node GuiNode, AssetManager assetManager, Camera cam) {

        myFont = assetManager.loadFont("Interface/Fonts/ComicSansMS.fnt");
        hudText = new BitmapText(myFont, false);
        
        hudText.setSize(myFont.getCharSet().getRenderedSize() * 1.5f);               // font size
        hudText.setColor(ColorRGBA.Yellow);                                        // font color
        hudText.setText("Score : " + ScoreCounter);                               // the text
        hudText.setLocalTranslation(cam.getWidth() / 2 - hudText.getLineWidth() / 2, hudText.getLineHeight(), 0);
        GuiNode.attachChild(hudText);
    }

    @Override
    protected void controlUpdate(float tpf) {
        hudText.setText("Score : " + ScoreCounter);
    }

    public  void IncreaseScore(int Amount) {
        ScoreCounter += Amount;
    }

    public int getScoreCounter() {
        return ScoreCounter;
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }

}
