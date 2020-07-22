package Player;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl;
import mygame.AnimationManager;
import mygame.BetterInputManager;

/**
 *
 * @author isaac
 */
public class Player {

    // Player Attributes
    private final AssetManager assetManager;
    private final BulletAppState bulletAppState;
    private final CameraNode camNode;
    private final Node player;
    private PlayerMovesControl PlayerMoves;
    private HealthBar Health;
    private final Node Gui;
    private int CoinCounter = 0, HeartCounter = 0;
    ;
    private AnimationManager animManager;

    public Player(AssetManager assetManager, BulletAppState bulletAppState, CameraNode camNode, Node GuiNode) {
        // intialize Player Attributes
        this.assetManager = assetManager;
        this.bulletAppState = bulletAppState;
        this.camNode = camNode;
        this.Gui = GuiNode;
        this.CoinCounter = 0;

        player = (Node) assetManager.loadModel("Models/Hercules/Hercules.j3o");

        CustomizeCamera();

        LoadHealth(GuiNode);
        LoadMoveMents();
    }

    private void LoadHealth(Node GuiNode) {
        Health = new HealthBar(camNode.getCamera(), GuiNode, 4000L, false);
        Health.SetHealthPic(assetManager, "Textures/Hercules/Frame.png", "Textures/Hercules/Blood.png");
        player.addControl(Health);
    }

    private void LoadMoveMents() {
        PlayerMoves = new PlayerMovesControl(player, bulletAppState, camNode);
        this.animManager = PlayerMoves.getAnimManager();
        player.addControl(PlayerMoves);
    }

    private void CustomizeCamera() {
        camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        player.attachChild(camNode);
        camNode.setLocalTranslation(0, 300, -500);
        camNode.setLodLevel(4);
    }

    public Node getPlayer() {
        return player;
    }

    private void Die() {
        // animManager.setAnimation("fall");
    }

    public int getCoinCounter() {
        return CoinCounter;
    }

    public void increaseCoinCounter() {
        CoinCounter++;
    }

    public int getHeartCounter() {
        return HeartCounter;
    }

    public void increaseHeartCounter() {
        HeartCounter++;
    }
    

}
