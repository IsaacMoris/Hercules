/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Player;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import mygame.AnimationManager;
import mygame.FlyableNPC;
import mygame.NPCManager;

/**
 *
 * @author Mariv
 */
public class HadesMovement extends AbstractControl {

    Node Hades;
    Node Hercules;
    Node Scene;
    private AnimationManager animManager;
    NPCManager np_obj;

    public HadesMovement(Node Hades, Node Hercules, Node Scene, AnimationManager animManager) {
        this.Hades = Hades;
        this.Hercules = Hercules;
        this.Scene = Scene;
        this.animManager = animManager;
        this.np_obj = new FlyableNPC(Hades);
    }

    @Override
    protected void controlUpdate(float tpf) {
       /* if (distance < 50) {

        } else if (distance < 100) {
            np_obj.setPositionToGO(Hercules.getLocalTranslation());
            np_obj.setSpeed(5);

        } else {
            np_obj.setPositionToGO(Hercules.getLocalTranslation());
            np_obj.setSpeed(0);
        }*/
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
