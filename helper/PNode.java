package xtc.oop.helper;

public class PNode{
    String name;
    PNode[] packageChildren;
    String[] structChildren;

    public PNode(String name){
        this.name = name;
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

    public PNode[] getPackageChildren(){
        return this.packageChildren;
    }

    public String[] getStructChildren(){
        return this.structChildren;
    }

    public String getName(){
        return this.name;
    }

}
