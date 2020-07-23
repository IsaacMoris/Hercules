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
    private Score score;
    private Lifes lifes;
    
    public Player(AssetManager assetManager, BulletAppState bulletAppState, CameraNode camNode, Node GuiNode) {
        // intialize Player Attributes
        this.assetManager = assetManager;
        this.bulletAppState = bulletAppState;
        this.camNode = camNode;
        
        player = (Node) assetManager.loadModel("Models/Hercules/Hercules.j3o");
        
        CustomizeCamera();
        LoadHealth(GuiNode);
        LoadMoveMents();
        LoadScore(GuiNode);
        LoadLifes(GuiNode);
    }
    
    private void LoadHealth(Node GuiNode) {
        Health = new HealthBar(camNode.getCamera(), GuiNode, 4000L, true);
        Health.SetHealthPic(assetManager, "Textures/Hercules/Frame.png", "Textures/Hercules/Blood.png");
        player.addControl(Health);
    }
    
    private void LoadMoveMents() {
        PlayerMoves = new PlayerMovesControl(player, bulletAppState, camNode);
        player.addControl(PlayerMoves);
    }
    
    private void LoadScore(Node GuiNode) {
        score = new Score(GuiNode, assetManager, camNode.getCamera());
        player.addControl(score);
    }
    
    private void LoadLifes(Node GuiNode) {
        lifes = new Lifes(3, assetManager, camNode.getCamera(), GuiNode);
        // player.addControl(lifes);
    }
    
    private void CustomizeCamera() {
        camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        player.attachChild(camNode);
        camNode.setLocalTranslation(0, 350, -600);
        camNode.setLodLevel(4);
    }
    
    public Node getPlayer() {
        return player;
    }
    
    private void Die() {
        // animManager.setAnimation("fall");
    }
    
    public int getCoinCounter() {
        return score.getScoreCounter();
    }
    
    public void increaseCoinCounter(int Amount) {
        score.IncreaseScore(Amount);
    }

    public void AddLife() {
        lifes.AddLife(1);
    }
    
}
