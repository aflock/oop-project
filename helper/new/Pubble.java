package xtc.oop.helper;
import java.util.ArrayList;

public class Pubble{
    String name; //The name of this package
    ArrayList<Pubble> children; //The package children
    ArrayList<Bubble> bubbles; //All the classes within this package
    Pubble parent; //The parent package

//=========Constructors=======//
    public Pubble(String name){
        this.name = name;
    }
    
    public Pubble(String name, Pubble parent){
        this.name = name;
        this.parent = parent;
    }

//=========NON-GETTER/SETTER METHODS=======//
    
    //Adds a new package to this Pubble's packageChildren
    public void addChild(Pubble child){
        this.children.add(child);
    }
    
    public void addBubble(Bubble b){
        this.bubbles.add(b);
    }

    
//=========GETTER/SETTER METHODS=======//  
    
    public String getName(){
        return this.name;
    }
    public void setName(String n){
        this.name = n;    
    }

    public Pubble getParent(){
        return this.parent;
    }
    public void setParent(Pubble p){
        return this.parent = p;
    }

    public ArrayList<Bubble> getBubbles(){
        return this.bubbles;
    }
    //No Setter
    
    public ArrayList<Pubble> getChildren(){
        return this.children;
    }
    //No Setter
}
