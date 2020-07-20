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

/**
 *
 * @author HP
 */
public class rayCasting {
  
    Node player;
    Node Scene;
    Node rootNode;
    Node Sword;
    Camera cam;
    public rayCasting(Node rootNode,Node player,Node Scene,Camera cam){
    this.Scene=Scene;
    this.player=player;
    this.rootNode=rootNode;
    this.Sword=(Node)player.getChild("RightHandMiddle");
    this.cam=cam;
    
    }
    Node find_parent(Geometry geo,String parent)
    {  try{
        Node N=geo.getParent();
        
        if(N==null)System.out.println("i am null");
        Node par=null;
        if(Scene.getChild(parent)!=null)
            par=(Node)Scene.getChild(parent);
        if(parent.equals("Scene Root"))par=Scene;
        
    
        while(N!=null&&!N.getParent().getName().equals(parent)&&N.getParent()!=Scene)
        {
         N.detachAllChildren();
         N=N.getParent();
         
        }
                System.out.println("hello");
        return N;
        
    } 
    catch(Exception e){
        System.out.println("mygame.rayCasting.find_parent()");
    }
    return null;
        
    }
    void colisionDetectionLoop(Vector3f direction ,Vector3f location,Camera ca){
        Ray detect_ray=new Ray(direction,location);
        if(ca!=null)
        detect_ray=new Ray(ca.getLocation(),ca.getDirection());
       
        CollisionResults CollisionResult=new CollisionResults(),colwithherc=new  CollisionResults();
        Scene.collideWith(detect_ray, CollisionResult);
        for (int i = 0; i < CollisionResult.size(); i++) {
          // For each hit, we know distance, impact point, name of geometry.
            float dist = CollisionResult.getCollision(i).getDistance();
            Vector3f pt = CollisionResult.getCollision(i).getContactPoint();
            String hit = CollisionResult.getCollision(i).getGeometry().getName();
            player.collideWith(CollisionResult.getCollision(i).getGeometry().getModelBound(), colwithherc);
            if(colwithherc.size()>0){
              Node N=null;
             
              if(hit.equals("12190_Heart_v1_L3-geom-0")){
                 N= find_parent(CollisionResult.getCollision(i).getGeometry(),"Scene Root");
              //Scene.detachChild(N); 
              }
                
              if(hit.equals("CoinObj_Coin_0")){
                    N= find_parent(CollisionResult.getCollision(i).getGeometry(),"Scene Root");

                //  Scene.detachChild(N);
              }
                
              if(hit.equals("HealthDrink")){
                   N= find_parent(CollisionResult.getCollision(i).getGeometry(),"Scene Root");

                 // Scene.detachChild(N);
              }
                
            
              
              if(N!=null)Scene.detachChild(N);
        
              colwithherc.clear();
          
            }
            // the comment below is exception because the geomtry does not have a parent
            /*if(hit.equals("Box001_Material #41_0")){
               Node N= find_parent(CollisionResult.getCollision(i).getGeometry(),"Rope");
               Scene.detachChild(N);
             }*/
            if(dist<=50)
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
         colisionDetectionLoop(direction,location,null);
         //location.y=abs(location.y)+(float)1.4;
         colisionDetectionLoop(direction,location ,cam);
         location.y=abs(location.y)+(float)2.1;
         //System.out.println("new: "+location);
         // colisionDetectionLoop(direction, location,null);

      
      
     }
     
     /**
      * for detecting attackable items
      */
   public void attack_detect(Camera c){
        Node hand=(Node)player.getChild("RightHandMiddle");
        Sword=(Node)player.getChild("Sword");

        Vector3f direction=new Vector3f( hand.getWorldRotation().getX(),hand.getWorldRotation().getY(),hand.getWorldRotation().getZ()); 
     
        Vector3f location=new Vector3f(Sword.getWorldTranslation().x,Sword.getWorldTranslation().y,Sword.getWorldTranslation().z).add(hand.getWorldTranslation());
        //location=location.mult(Vector3f.UNIT_X);
                
        colisionDetectionLoop(direction, location,c);
               

   }
        
    }

