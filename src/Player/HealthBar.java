package Player;

import com.jme3.asset.AssetManager;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import com.jme3.ui.Picture;
import jme3tools.optimize.TextureAtlas;

/**
 *
 * @author isaac
 */
public class HealthBar extends AbstractControl {

    private Picture health, Face;
    private Texture2D healthTexture;
    private AssetManager assetManager;
    private float SettingsHeight, SettingsWidth;

    final private float FaceHeight, FaceWidth;
    final private float HealthHeight, HealthWidth;
    final private float CircleRatio = 170.0f / 247.0f;
    final private float LeftPart = 31.0f / 247.0f, Bottompart = 60.0f / 251.0f;

    final private float FaceRatio = 247.0f / 251.0f;

    final private float FaceX, FaceY, HealthX, healthY;

    final private int MaxHealth = 175;
    private int CurHealth = 175;
    private int Damage;

    public HealthBar(AssetManager assetManager, Camera cam, Node Gui) {

        this.assetManager = assetManager;

        this.SettingsHeight = cam.getHeight();
        this.SettingsWidth = cam.getWidth();
        
        
        
        FaceHeight = SettingsHeight / 6.0f;
        FaceWidth = FaceHeight * FaceRatio;

        HealthHeight = HealthWidth = CircleRatio * FaceWidth;

        FaceX = 0;
        FaceY = SettingsHeight - FaceHeight * 1.2f;

        HealthX = FaceX + LeftPart * FaceWidth;
        healthY = FaceY + Bottompart * FaceHeight;
        Initial(Gui);
    }

    void Initial(Node Gui) {

        healthTexture = (Texture2D) assetManager.loadTexture("Textures/Health.png");

        health = new Picture("Health Pic");
        health.setTexture(assetManager, healthTexture, true);
        health.setWidth(HealthWidth);

        health.setHeight(HealthHeight);
        health.setPosition(HealthX, healthY);

        Face = new Picture("Face Picture");
        Face.setImage(assetManager, "Textures/Face.png", true);

        Face.setWidth(FaceWidth);
        Face.setHeight(FaceHeight);
        Face.setPosition(FaceX, FaceY);
        
        Gui.attachChild(getHealth());
        Gui.attachChild(getFace());
    }

    public Picture getHealth() {
        return health;
    }

    public Picture getFace() {
        return Face;
    }

    public void setDamage(int Damage) {
        this.Damage += Damage;

    }
    float x = 0;

    @Override
    protected void controlUpdate(float tpf) {
        if (Damage >= 1) {
            x += 0.2f;
            if (x >= 1f) {
                CurHealth -= 1;
                x -= 1;
                Damage -= 1;
                healthTexture.getImage().setHeight(CurHealth);
                health.setHeight(HealthHeight * CurHealth / MaxHealth);
            }
        }

    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

}
