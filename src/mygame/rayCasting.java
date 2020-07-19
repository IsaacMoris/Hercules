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
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 *
 * @author HP
 */
public class rayCasting {
  
    Node player;
    Node Scene;
    Node rootNode;
    Node Sword;
    public rayCasting(Node rootNode,Node player,Node Scene){
    this.Scene=Scene;
    this.player=player;
    this.rootNode=rootNode;
    this.Sword=(Node)player.getChild("RightHandMiddle");
    
    }
    Node find_parent(Geometry geo)
    {
     Node N=geo.getParent();
        while(N!=null&&N.getParent()!=Scene)
        {
         N=N.getParent();
        }
        return N;
    }
    void colisionDetectionLoop(Vector3f direction ,Vector3f location){
        Ray detect_ray=new Ray(direction,location);
       
        CollisionResults CollisionResult=new CollisionResults(),colwithherc=new  CollisionResults();
        Scene.collideWith(detect_ray, CollisionResult);
        for (int i = 0; i < CollisionResult.size(); i++) {
          // For each hit, we know distance, impact point, name of geometry.
            float dist = CollisionResult.getCollision(i).getDistance();
            Vector3f pt = CollisionResult.getCollision(i).getContactPoint();
            String hit = CollisionResult.getCollision(i).getGeometry().getName();
            player.collideWith(CollisionResult.getCollision(i).getGeometry().getModelBound(), colwithherc);
            if(colwithherc.size()>0){
              Node N=find_parent(CollisionResult.getCollision(i).getGeometry());
              if(N==null)continue;
              if(hit.equals("12190_Heart_v1_L3-geom-0"))
                Scene.detachChild(N); 
              if(hit.equals("CoinObj_Coin_0"))
                Scene.detachChild(N);
              if(hit.equals("HealthDrink"))
                Scene.detachChild(N);
             //if(hit.equals("Box1"))
                // Scene.detachChild(N); 
        
              colwithherc.clear();
          
            }
            if(dist<=10)
            System.out.println("  You shot " + hit + " at " + pt + ", " + dist + " wu away.");

        }
    }
     
 
     final double pi=acos(-1);
     /**
      * detecting collectable items
      */
     public void detect(){

        Vector3f direction=new Vector3f(player.getWorldRotation().getX(),player.getWorldRotation().getY(),player.getWorldRotation().getZ()); 
     
        Vector3f location=new Vector3f(player.getLocalTranslation().x, player.getLocalTranslation().y,player.getLocalTranslation().z);
       //  System.err.println("old: "+location);
         colisionDetectionLoop(direction, location);
         location.y=abs(location.y)+(float)1.4;
         colisionDetectionLoop(direction, location);
         location.y=abs(location.y)+(float)2.1;
         //System.out.println("new: "+location);
          colisionDetectionLoop(direction, location);

      
      
     }
     
     /**
      * for detecting attackable items
      */
   public void attack_detect(Node player1){
        Node hand=(Node)player1.getChild("RightHandMiddle");
        Sword=(Node)player1.getChild("Sword");

        Vector3f direction=new Vector3f( hand.getWorldRotation().getX(),hand.getWorldRotation().getY(),hand.getWorldRotation().getZ()); 
     
        Vector3f location=new Vector3f(Sword.getWorldTranslation().x,Sword.getWorldTranslation().y,Sword.getWorldTranslation().z).add(hand.getWorldTranslation());
        //location=location.mult(Vector3f.UNIT_X);
                
        colisionDetectionLoop(direction, location);
               

   }
        
    }

