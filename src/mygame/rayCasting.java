/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.collision.CollisionResults;
import static com.jme3.math.FastMath.abs;
import static com.jme3.math.FastMath.acos;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Mariv
 */
public class rayCasting {

    Node player;
    Node Scene;
    Node Sword;
    List<Geometry> collidables_list;
    Map<Spatial, Boolean> visited;

    public rayCasting(Node player, Node Scene) {
        this.Scene = Scene;
        this.player = player;
        this.Sword = (Node) player.getChild("Sword");
        this.collidables_list = new ArrayList<>();
        dfs(Scene);

    }

    Node find_parent(Geometry geo, String parent) {
        try {
            Node N = geo.getParent();

            //if(N==null)System.out.println("i am null");
            Node par = null;
            if (Scene.getChild(parent) != null) {
                par = (Node) Scene.getChild(parent);
            }
            if (parent.equals("Scene Root")) {
                par = Scene;
            }

            while (N != null && !N.getName().equals(parent) && N.getParent() != Scene) {
                // N.detachAllChildren();
                N = N.getParent();

            }
            // System.out.println("hello");
            return N;

        } catch (Exception e) {
            System.out.println("mygame.rayCasting.find_parent()");
        }
        return null;

    }

    /**
     * detecting collectable items
     */
    private List detect(boolean is_attacking) {
        List<Geometry> attacked = new ArrayList<>(), touched = new ArrayList<>();
        for (int i = 0; i < collidables_list.size(); i++) {
            CollisionResults results = new CollisionResults(), attackRes = new CollisionResults();
            player.collideWith(collidables_list.get(i).getWorldBound(), results);
            Sword.collideWith(collidables_list.get(i).getWorldBound(), attackRes);
            String name = collidables_list.get(i).getName();
            String parent = "";

            if (results.size() > 0) {

                if (name.equals("12190_Heart_v1_L3-geom-0")) {
                    parent = "HeartShape";
                }
                if (name.equals("HealthDrink")) {
                    parent = "HealthDrink";
                }
                if (name.equals("CoinObj_Coin_0")) {
                    parent = "Coin";
                }
                if (name.equals("Box001_Material #41_0")) {
                    parent = "Bagmy";
                }
                Node N = find_parent(collidables_list.get(i), parent);
                if (N != null) {
                    // Scene.detachChild(N);
                    System.out.println("hi i took " + name);
                    touched.add(collidables_list.get(i));
                }
            }
            if (attackRes.size() > 0) {
                if (name.equals("Box001_Material #41_0")) {
                    parent = "Bagmy";
                }
                Node N = find_parent(collidables_list.get(i), parent);

                if (N != null) {
                    attacked.add(collidables_list.get(i));
                }
            }
        }

        if (is_attacking) {
            return attacked;
        }
        return touched;

    }

    public List attack_detect() {
        return detect(true);
    }

    public List touch_detect() {
        return detect(false);
    }

    /**
     * for detecting attackable items
     */
    private void dfs(Spatial Scene) {

        if (Scene instanceof Geometry) {
            Geometry g = (Geometry) Scene;

            String name = Scene.getName();
            if (name.equals("12190_Heart_v1_L3-geom-0")
                    || name.equals("HealthDrink")
                    || name.equals("CoinObj_Coin_0")
                    || name.equals("Box001_Material #41_0")) {
                collidables_list.add(g);

            }
            return;

        }

        List<Spatial> children = ((Node) Scene).getChildren();
        for (int i = 0; i < children.size(); i++) {

            Spatial child = children.get(i);
            dfs(child);
        }

    }

}
