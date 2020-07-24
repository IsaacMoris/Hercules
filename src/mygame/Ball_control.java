/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import GUI_2D.HealthBar;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author HP
 */
public class Ball_control {

    Node Ball;
    Node Original_position;
    Node playerNode;
    Node Scene;
    Node rootNode;
    int counter;
    Vector3f pos;

    public Ball_control(String effectName, Node Original_position, Node playerNode, Node Scene, Node rootNode, AssetManager assetManager) {
        this.Original_position = Original_position;
        this.playerNode = playerNode;
        this.Scene = Scene;
        this.rootNode = rootNode;
        Ball = (Node) assetManager.loadModel("Models/ball/Ball.j3o");
        Ball.scale(0.01f);
        Ball.setLocalTranslation(Original_position.getWorldTranslation());

        
        Scene.attachChild(Ball);
        Effects e = new Effects(effectName, Ball.getName(), Scene, rootNode, assetManager,2.0f);
        Ball.addControl(e);
        counter = -1;
    }

    public void Update(float tpf) {
        if (counter == -1) {
            pos = playerNode.getLocalTranslation().subtract(Ball.getLocalTranslation()).divide(200);
        }
        counter++;

        System.out.println(counter);
        if (Ball.getWorldBound().intersects(playerNode.getWorldBound())) {
             playerNode.getControl(HealthBar.class).DecreaseHealth(50);

          //  System.out.println("Ball hit Herc");
            Ball.setLocalTranslation(Original_position.getLocalTranslation());
            counter = 0;
        }
        if (counter <= 400) {
            if (counter > 100) {
                Ball.move(pos);
            }

        } else {
            counter = 0;
            Ball.setLocalTranslation(Original_position.getWorldTranslation());
            pos = new Vector3f(playerNode.getLocalTranslation().x, (float) (playerNode.getLocalTranslation().y ), playerNode.getLocalTranslation().z).subtract(Ball.getLocalTranslation()).divide(200);

        }
    }

    public void setOriginal_position(Node Original_position) {
        this.Original_position = Original_position;
    }

    
}
