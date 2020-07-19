package mygame;
import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.effect.shapes.EmitterPointShape;
import com.jme3.effect.shapes.EmitterSphereShape;
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
    private  ParticleEmitter effect;
    private final Spatial effectPosition; //msh positive
    private  Material mat;
    public Effects(String effectName,String effectPositionStr,Node Scene,Node rootNode,AssetManager assetManager,Float size)
    {
        if (effectName.equals("fire"))
        {
            effect = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);
            mat = new Material(assetManager,"Common/MatDefs/Misc/Particle.j3md");
            mat.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/flame.png"));
            effect.setEndColor(new ColorRGBA(1f, 0f, 0f, 1f));   // red
            effect.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
            effect.setMaterial(mat);
            effect.setImagesX(2);
            effect.setImagesY(2); // 2x2 texture animation
            effect.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
            effect.setStartSize(size);
            effect.setEndSize(0.1f);
            effect.setGravity(0, 0, 0);
            effect.setLowLife(1f);
            effect.setHighLife(3f);
            effect.getParticleInfluencer().setVelocityVariation(0.3f);
            effect.setShape(new EmitterSphereShape(Vector3f.ZERO,2f));
        }
        else if (effectName.equals("smoke"))//smoke
        {
          effect = new ParticleEmitter("smoke", ParticleMesh.Type.Triangle, 30);
          mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
          mat.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/Debris.png"));
          effect.setMaterial(mat);
          effect.setImagesX(3);
          effect.setImagesY(3); // 3x3 texture animation
          effect.setRotateSpeed(4);
          effect.setSelectRandomImage(true);
          effect.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 4, 0));
          effect.setStartSize(size);
          effect.setEndSize(0.1f);
          effect.setStartColor(ColorRGBA.White);
          effect.setGravity(0, 6, 0);
          effect.getParticleInfluencer().setVelocityVariation(.60f);
          effect.emitAllParticles();
        }
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
