/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.bounding.BoundingVolume;
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

    /**
     * detecting collectable items
     */
    public List detect_Collision_with(String check_with) {

        this.Sword = (Node) Scene.getChild(check_with);
        if (check_with.equals("Hercules")) {
            this.Sword = player;
        }else if(check_with.equals("Sword")){
        this.Sword=(Node)player.getChild("Sword");
        }

        List<Geometry> touched = new ArrayList<>();
        for (int i = 0; i < collidables_list.size(); i++) {
            if (collided_items(Sword, collidables_list.get(i).getWorldBound())&&collidables_list.get(i) != null) {
                touched.add(collidables_list.get(i));
                System.out.println("hi i took "+collidables_list.get(i).getName());
            }

        }

        return touched;

    }
    public static boolean collided_items(Node A,BoundingVolume B){
    CollisionResults results = new CollisionResults();
    A.collideWith(B, results);
    return results.size()>0;

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
                    || name.equals("Box001_Material #41_0")
                    //|| name.equals("Object_texture000.jpg.001")
                    //|| name.equals("Object_texture001.jpg.001")
                    //|| name.equals("Object_texture002.jpg.001")
                    || name.equals("rdmobj00mesh.008")
                    || name.equals("rdmobj01mesh.008")
                    || name.equals("rdmobj02mesh.008")) {
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
