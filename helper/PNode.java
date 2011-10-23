package xtc.oop.helper;
import java.util.ArrayList;

public class PNode{
    String name;
    PNode[] packageChildren;
    String[] structChildren;
    PNode parent;
    ArrayList<String> structs = new ArrayList<String>();


    public PNode(String name){
        this.name = name;
    }
    public PNode(String name, PNode parent){
        this.name = name;
        this.parent = parent;
    }

    public boolean hasStruct(String s)
    {
        return structs.contains(s);
    }
    
    public void addPNodeChild(PNode child){
        if(child == null){
            return;
        }
        int len = packageChildren == null ? 1 : packageChildren.length + 1;
        PNode[] temp = new PNode[len];
        if(packageChildren == null){
            temp[0] = child;
            packageChildren = temp;
        }
        else{
            for (int i = 0; i < packageChildren.length ; i++ ){
                temp[i] = packageChildren[i];
            }
            temp[len-1] = child;
            packageChildren = temp;
        }
    }

    public void addStructChild(String child){
        structs.add(child);
        if(child == null){
            return;
        }
        int len = structChildren == null ? 1 : structChildren.length + 1;
        String[] temp = new String[len];
        if(structChildren == null){
            temp[0] = child;
            structChildren = temp;
        }
        else{
            for (int i = 0; i < structChildren.length ; i++ ){
                temp[i] = structChildren[i];
            }
            temp[len-1] = child;
            structChildren = temp;
        }
    }

    public void setParent(PNode p){
        this.parent = p;
    }

    public PNode[] getPackageChildren(){
        return this.packageChildren;
    }

    public String[] getStructChildren(){
        return this.structChildren;
    }

    public String getName(){
        return this.name;
    }

    public PNode getParent(){
        return this.parent;
    }

    public String toString(){
        String toReturn = "";
        toReturn += this.name +"\n";

        if(this.name != "DefaultPackage")
            toReturn += "Parent: " + this.parent.getName() +"\n";

        if(packageChildren != null){
            toReturn += "pChildren: " +"\n";
            for (int i = 0; i < packageChildren.length ; i++ ){
                toReturn += packageChildren[i].getName() + "\n";
            }
        }

        if(structChildren != null){
            toReturn += "struct Children: " +"\n";
            for (int i = 0; i < structChildren.length ; i++ ){
                toReturn += structChildren[i] + "\n";
            }
        }

        return toReturn;
    }

}
