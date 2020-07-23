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
import java.sql.Time;
import jme3tools.optimize.TextureAtlas;

/**
 *
 * @author isaac
 */
public class HealthBar extends AbstractControl {

    private float SettingsHeight, SettingsWidth;
    private Picture BloodPic, FramePic;
    private Texture2D BloodTexture;
    final private float BloodHeight, BloodWidth;
    final private float BloodX, BloodY;

    final private float FrameHeight, FrameWidth;
    final private float FrameX, FrameY;

    final private float CircleRatio = 170.0f / 247.0f;
    final private float LeftPart = 31.0f / 247.0f, Bottompart = 60.0f / 251.0f;
    final private float FaceRatio = 247.0f / 251.0f;

    final private int MaxHealth = 175;
    private int CurHealth = 175;
    private int Damage = 0;

    private boolean Shild = false;
    private Long LastAttacked, ShildTime;

    public HealthBar(Camera cam, Node GuiNode, Long shildTime, boolean Left) {

        this.SettingsHeight = cam.getHeight();  // set the height and width of screen to cam height and width
        this.SettingsWidth = cam.getWidth();
        this.ShildTime = shildTime;               // Shild time that protect the health from decreasing

        LastAttacked = System.currentTimeMillis();

        FrameHeight = SettingsHeight / 6.0f;
        FrameWidth = FrameHeight * FaceRatio;
        FrameX = Left ? 0 : SettingsWidth - FrameWidth * 1.2f;
        FrameY = SettingsHeight - FrameHeight * 1.1f;

        BloodHeight = BloodWidth = CircleRatio * FrameWidth;
        BloodX = FrameX + LeftPart * FrameWidth;
        BloodY = FrameY + Bottompart * FrameHeight;

        BloodPic = new Picture("Blood Pic");
        FramePic = new Picture("Frame Picture");

        GuiNode.attachChild(BloodPic);
        GuiNode.attachChild(FramePic);
    }

    public void SetHealthPic(AssetManager assetManager, String Frame, String Blood) {

        BloodTexture = (Texture2D) assetManager.loadTexture(Blood);
        BloodPic.setTexture(assetManager, BloodTexture, true);
        BloodPic.setWidth(BloodWidth);
        BloodPic.setHeight(BloodHeight);
        BloodPic.setPosition(BloodX, BloodY);

        FramePic.setImage(assetManager, Frame, true);
        FramePic.setWidth(FrameWidth);
        FramePic.setHeight(FrameHeight);
        FramePic.setPosition(FrameX, FrameY);

    }

    public void IncreaseHealth(int health) {
        this.Damage -= health;

    }

    public void DecreaseHealth(int Damage) {
        if (Shild) {
            return;
        }
        this.Damage += Damage;
        Shild = true;
        LastAttacked = System.currentTimeMillis();

    }



    @Override
    protected void controlUpdate(float tpf) {
        if (System.currentTimeMillis() - LastAttacked > ShildTime) {
            Shild = false;
        }
        if (Math.abs(Damage) >= 1) {
            CurHealth += Damage > 0 ? -1 : 1;
            Damage += Damage > 0 ? -1 : 1;
            CurHealth = CurHealth > MaxHealth ? MaxHealth : CurHealth;
        }
        BloodTexture.getImage().setHeight(CurHealth);
        BloodPic.setHeight(BloodHeight * CurHealth / MaxHealth);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

}
