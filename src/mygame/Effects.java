package mygame;
import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
/**
 *
 * @author Oliver
 */
public class Effects extends AbstractControl
{
    private final ParticleEmitter effect;
    private final Spatial effectPosition; //msh positive
    private  Material mat;

    public Effects(String effectName , String effectPositionStr,Node Scene ,Node rootNode ,AssetManager assetManager)
    {
    
    effect = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);
    
    if (effectName.equals("fire"))
    {
        mat = new Material(assetManager,"Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/flame.png"));
        effect.setEndColor(  new ColorRGBA(1f, 0f, 0f, 1f));   // red
        effect.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
    }
    else if (effectName.equals("smoke"))//smoke
    {}
    effect.setMaterial(mat);
    effect.setImagesX(2);
    effect.setImagesY(2); // 2x2 texture animation
    effect.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
    effect.setStartSize(1.5f);
    effect.setEndSize(0.1f);
    effect.setGravity(0, 0, 0);
    effect.setLowLife(1f);
    effect.setHighLife(3f);
    effect.getParticleInfluencer().setVelocityVariation(0.3f);
    
    rootNode.attachChild(effect);
    
    effectPosition =  Scene.getChild(effectPositionStr);
    
    }

    @Override
    protected void controlUpdate(float tpf)
    {
        effect.setLocalTranslation(effectPosition.getWorldTranslation());
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp)
    {
    } 
}
