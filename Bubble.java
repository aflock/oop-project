import java.util.ArrayList;

public class Bubble{
    String name;
    String[] methods;
    String[] dataFields;
    Bubble parent;
    String[] children;
    ArrayList<String> tempChildren;
    public Bubble(String name, String[] methods,
            String[] dataFields, Bubble parent, String[] children){

        this.name = name;
        this.methods = methods;
        this.dataFields = dataFields;
        this.parent = parent;
        this.children = children;
    }

    public Bubble(String name, String child) {
	this.name = name;
	tempChildren = new ArrayList<String>();
	tempChildren.add(child);
    }

    public void setMethods(String[] methods) {
	this.methods = methods;
    }

    public void setDataFields(String[] dataFields) {
	this.dataFields = dataFields;
    }

    public void setParent(Bubble parent) {
	this.parent = parent;
    }
    
    public void setChildren(String[] children) {
	this.children = children;
    }		       

    public void addChild(String child) {
	if (tempChildren == null) {
	    tempChildren = new ArrayList<String>();
	}
	tempChildren.add(child);
    }
}
