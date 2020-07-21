package Player;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl;
import mygame.AnimationManager;

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
    private final PlayerMovesControl PlayerMoves;
    private final HealthBar Health;
    private final Node Gui;
    private int HealthCounter,CoinCounter,HeartCounter;
    private AnimationManager animManager;


    public Player(AssetManager assetManager, BulletAppState bulletAppState, CameraNode camNode, Node Gui) {
        // intialize Player Attributes
        this.assetManager = assetManager;
        this.bulletAppState = bulletAppState;
        this.camNode = camNode;
        this.Gui = Gui;
        this.HealthCounter=100;
        this.CoinCounter=0;
        this.HeartCounter=0;

        player = (Node) assetManager.loadModel("Models/Hercules/Hercules.j3o");

        CustomizeCamera();

        PlayerMoves = new PlayerMovesControl(player, bulletAppState, camNode);
        Health = new HealthBar(assetManager, camNode.getCamera() , Gui);
        this.animManager=PlayerMoves.getAnimManager();

        Health.setDamage(100);
        player.addControl(PlayerMoves);
        player.addControl(Health);
    }

    private void CustomizeCamera() {
        camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        player.attachChild(camNode);
        camNode.setLocalTranslation(0, 300, -500);
        camNode.setLodLevel(4);
    }

    public void setHealthCounter(int increased_amount) {
        this.HealthCounter += increased_amount;
    }

    public Node getPlayer() {
        return player;
    }
    private void Die(){
       // animManager.setAnimation("fall");
    }
    public int getHealthCounter() {
        return HealthCounter;
    }

    public int getCoinCounter() {
        return CoinCounter;
    }

    public int getHeartCounter() {
        return HeartCounter;
    }
    public void increaseHeartCounter() {
        HeartCounter++;
    }
    public void increaseCoinCounter() {
        CoinCounter++;
    }
    public void TakeDamage(int Damage) {
        Health.setDamage(Damage);
        HealthCounter-=Damage;
        if(HealthCounter<=0)Die();
        
    }
    
    

}
