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
	String temp[] = { child };
	this.children = temp;
	//tempChildren = new ArrayList<String>();
	//tempChildren.add(child);	
    }

    public void setMethods(String[] methods) {
	this.methods = methods;
    }

    public void setDataFields(String[] dataFields) {
	this.dataFields = dataFields;
    }

    /*
    public void setParent(Bubble parent) {
	this.parent = parent;
    }
    */


    public void setChildren(String[] children) {
	this.children = children;
    }

    public void addChild(String child) {
	int len = children == null ? 0 : children.length + 1;
	String[] temp = new String[len];
	if (children == null) {
	    temp[0] = children[0];
	}
	else {
	    for (int i = 0; i < children.length; i++) {
		temp[i] = children[i];
	    }
	}
	temp[len - 1] = child;
	children = temp;
    }

    public String childrenToString() {
	if (children == null) {
	    return null;
	}
	else { 
	    String s = "[";
	    for (int i = 0; i < children.length; i++) {
		s += children[i];
		if (i != children.length - 1)
		    s += ", ";
	    }
	    return s + "]";
	}	
    }
}
