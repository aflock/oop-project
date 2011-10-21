public class Bubble{
    String name;
    String[] methods;
    String[] dataFields;
    Bubble parent;
    String[] children;
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
    }

    public void setMethods(String[] methods) {
	this.methods = methods;
    }

    public void setDataFields(String[] dataFields) {
	this.dataFields = dataFields;
    }

    public String getName() {
	return name;
    }
    
    public void setParent(Bubble parent) {
	this.parent = parent;
    }

    public void setChildren(String[] children) {
	this.children = children;
    }

    public void addChild(String child) {
	int len = children == null ? 1 : children.length + 1;
	String[] temp = new String[len];
	if (children == null) {
	    temp[0] = child;
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
	    StringBuilder s = new StringBuilder("[");
	    for (int i = 0; i < children.length; i++) {
		s.append(children[i]);
		if (i != children.length - 1)
		    s.append(", ");
	    }
	    return s.append("]").toString();
	}	
    }
}
