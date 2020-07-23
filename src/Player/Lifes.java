package Player;

import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.ui.Picture;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author isaac
 */
public class Lifes {

    private int LifesCounter = 0;
    private final int Length, height;
    private AssetManager assetManager;
    private Node GuiNode;
    List<Picture> heartList;

    public Lifes(int LifesCounter, AssetManager assetManager, Camera cam, Node GuiNode) {
        this.assetManager = assetManager;
        this.GuiNode = GuiNode;
        heartList = new ArrayList<Picture>();
        Length = cam.getWidth() / 28;
        height = cam.getHeight() * 75 / 100;

        AddLife(LifesCounter);

    }

    public void AddLife(int amount) {
        for (int i = 0; i < amount; i++) {
            Picture Heart = new Picture("Heart" + LifesCounter);
            Heart.setImage(assetManager, "Textures/Heart.png", true);
            Heart.setWidth(Length);
            Heart.setHeight(Length);
            Heart.setPosition((Length + 2) * LifesCounter, height);
            heartList.add(Heart);
            GuiNode.attachChild(Heart);
            LifesCounter++;
        }
    }

    public void RemoveLife() {
        if (LifesCounter > 0) {
            LifesCounter--;
            GuiNode.detachChildNamed(heartList.get(LifesCounter).getName());
            heartList.remove(LifesCounter);
        }
        System.out.println(heartList.size());
    }

    public int getLifesCounter() {
        return LifesCounter;
    }

}
