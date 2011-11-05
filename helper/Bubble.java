package xtc.oop.helper;
import java.util.ArrayList;

public class Bubble{
    String name;
    String[] methods;
    String[] dataFields;
    String packageName;
    Bubble parent;
    String[] children;
    Bubble[] bChildren;
    ArrayList<String> vtable;
    String[] constructors;

    public Bubble(String name, String[] methods,
		  String[] dataFields, Bubble parent, String[] children, 
		  String packageName, String[] constructors){

        this.name = name;
        this.packageName = packageName;
        this.methods = methods;
        this.dataFields = dataFields;
        this.parent = parent;
        this.children = children;
        this.vtable = new ArrayList<String>();
	this.constructors = trim(constructors);
    }

    public Bubble(String name, String child) {
        this.name = name;
        if (child != null) {
            String temp[] = { child };
            this.children = temp;
        }
        this.vtable = new ArrayList<String>();
        this.methods = null;
    }

    public void addChild(String child) {
	if (child == null) {
	    return;
	}
        int len = children == null ? 1 : children.length + 1;
        String[] temp = new String[len];
        if (children == null) {
            temp[0] = child;
            children = temp;
        }
        else {
            for (int i = 0; i < children.length; i++) {
                temp[i] = children[i];
            }

            temp[len - 1] = child;
            children = temp;
        }
    }

    public boolean add2Vtable(String add){
        /* returns true if the method is an overwritten method, false if not*/
	
        //add = add.trim();
	//format the string
	add = this.format(add);
	//if it's a method [in the format: rt_type (*name)(params) ]
	if(add.matches(".*\\(\\*.*\\)\\(.*\\).*")) {
	    String sig = add.split("([\\w\\s]*\\(\\*)|(\\)\\(.*)")[1];
	    // System.out.println("SIG: \t\t"+sig);
	    int index = -1;
	    for(int i = 0; i < this.vtable.size(); i++) {
		//System.out.println("-----"+this.vtable.get(i));
		if(this.vtable.get(i).matches(".*\\(\\*.*\\)\\(.*\\).*") &&
		   this.vtable.get(i).split("([\\w\\s]*\\(\\*)|(\\)\\(.*)")[1]
		   .equals(sig)) {
		    //System.out.println("WOWOOWOWOWOWOWOWOWOWO");
		    index = i;
		}
	    }
	    
	    if(index != -1) {
		System.out.println("==========OVERWRITING " + sig + "in " 
				   + this.name);
		
		this.vtable.set(index,add + "\t");
                return true;
	    }
	    else {
		    this.vtable.add(add);
		    return false;
	    }
	    
        }
        //if it's not a method
	else {
	    this.vtable.add(add);
	}
	return false;
    }

    public String childrenToString() {
	if (children == null) {
	    return "No Children";
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

    /* Given a method string, convert it into a format of an element of 
       vtable entry. */     
    public String format(String method) {	
	if (method.startsWith(" ")) {
	    int square = 0; //count the number of arrays. fix me.
	    for (int i = 0; i < method.length(); i++) {
		if (method.charAt(i) == '[') square++;
	    }
	    String[] temp2 = method.split(" ");
	    int count = 0;

	    // count the number of non-empty strings
	    for (int i = 0; i < temp2.length; i++) {
		if (temp2[i].length() != 0) count++;
	    }

	    String[] temp = new String[count-square];
	    int index = 0;
	    for (int i = 0; i < temp2.length; i++) {
		if (temp2[i].length() != 0) {
		    if (temp2[i].charAt(0) == '[') {			
			temp[index-1] = "__rt::Array<" + temp[index-1] + ">*";
		    }
		    else {
			temp[index++] = temp2[i];
		    }
		}
	    }

	    int num = 0;
	    for (int i = 0; i < temp.length; i++) {
		if (temp[i].equals("public") ||
		    temp[i].equals("private") ||
		    temp[i].equals("protected") ||
		    temp[i].equals("static") ||
		    temp[i].equals("final")) {
		    //do nothing
		}
		else {
		    num++;
		}
	    }
	    String s = "";
	    if (num % 2 == 0) { // there is a return type
		s += temp[temp.length-num] + " ";
		index = temp.length-num+1;
	    }
	    else { // void
		s += "void ";
		index = temp.length-num;
	    }

	    s += "(*" + temp[temp.length-1] + ")(" + getName();

	    for (int j = index; j < temp.length - 1; j+=2) {
		s += ", " + temp[j];
	    }


	    s += ");";
	    return s;
	}
	return method;
    }

    public String[] getChildren() {
        return this.children;
    }
    
    public String[] getConstructors(){
        return this.constructors;
    }

    public String[] getDataFields(){
        return this.dataFields;
    }    

    public String[] getFormatedMethods() {
	String[] mm = new String[vtable.size()-1];
	String[] temp = new String[mm.length];
	for (int i = 0; i < mm.length; i++) {
	    temp[i] = vtable.get(i+1);
	    String[] s = temp[i].split("[\\s\\(\\)\\*\\,\\;]");
	    String real = "";
	    int num = 0;
	    for (int j = 0; j < s.length; j++) {
		if (!s[j].equals("")) {
		    if (num == 0) {
			real += s[j] + " ";
		    }
		    else if (num == 1) {
			real += s[j] + "(";
		    }
		    else if (num == 2) {
			real += s[j];
		    }
		    else {
			real += ", " + s[j];
		    }
		    num++;
		}
	    }
	    mm[i] = real + ");";
	}
	return mm;
    }

    public String[] getMethods(){
        return this.methods;
    }

    public String getName() {
	if (name == null) {
	    return "No Name";
	}
	return name;
    }
    
    public String getPackageName(){
        return this.packageName;
    }    
    
    public Bubble getParent() {
	return parent;
    }

    public ArrayList<String> getVtable(){
        return this.vtable;
    }    

    public String indentLevel(int indent){
        String toReturn = "";
        for( int i=0; i<indent; i++){
            toReturn += "  ";
        }
        return toReturn;
    }

    public String parentToString(){
        if(this.parent != null) {
            return this.parent.getName();
	}
        else {
            return "No Parent";
	}
    }

    public void printToFile(int indent) {
	// namespace needs to be added
	// String or java.lang::String ?
	if (getName().equals("Object") ||
	    getName().equals("String") ||
	    getName().equals("Class")) return;

	System.out.println("#pragma once");
	System.out.println();
	System.out.println("#include \"java_lang.h\"");
	System.out.println();
	System.out.println("struct _" + getName() + ";");
	System.out.println("struct _" + getName() + "_VT;");
	System.out.println();
	System.out.println("typdef _" + getName() + "* " + getName() + ";");
	System.out.println();
	System.out.println("struct _" + getName() + " {");
	System.out.println(indentLevel(indent) + "_" + getName() +
			   "_VT* __vprt;");

	String[] s = getConstructors();
	for (int i = 0; i < s.length; i++) {
	    System.out.println(indentLevel(indent) + "_" + s[i]);
	}
	//System.out.println(indentLevel(indent) + "_" + getName() + "();");
	System.out.println();
	String[] m = getFormatedMethods();
	for (int i = 0; i < m.length; i++) {
	    System.out.println(indentLevel(indent) + "static " + m[i]);
	}

	System.out.println();
	System.out.println(indentLevel(indent) + "static Class __class();");
	System.out.println();
	System.out.println(indentLevel(indent) + "static _" + getName() +
			   "_VT __vtable;");
	System.out.println("};");
	System.out.println();

	System.out.println("struct _" + getName() + "_VT {");
	ArrayList<String> vt = getVtable();
	for (int i = 0; i < vt.size(); i++) {
	    System.out.println(indentLevel(indent) + vt.get(i));
	}
	System.out.println();
	System.out.println("};");
    }

    public void printVtable(){
        System.out.println("//==============================================");
        System.out.println("//" + this.name + "'s vtable:");
        for(String s : this.vtable)
            System.out.println(s);

        //System.out.println("================================");
    }

    

    public void setChildren(String[] children) {
	if (children == null) {
	    return;
	}
	this.children = children;
    }

    public void setConstructors(String[] constructors){
        this.constructors = trim(constructors);
    }

    
    public void setDataFields(String[] dataFields) {
	if (dataFields == null) {
	    return;
	}
	
	//find number of non-null strings
	int r_length = 0;
	for(int i = 0; i < dataFields.length; i++) {
	    if(!dataFields[i].equals(""))
		r_length++;
	}
	//make temp array of correct size
	String [] temp = new String [r_length];
	int temp_i = 0;
	
	for(int i = 0; i < dataFields.length; i++) {
	    //types
	    dataFields[i] 
		= dataFields[i].replaceAll("(?<!\\w)int(?!\\w)","int32_t");
	    dataFields[i] 
		= dataFields[i].replaceAll("(?<!\\w)boolean(?!\\w)","bool");
	    dataFields[i] 
		= dataFields[i].replaceAll("(?<!\\w)final(?!\\w)","const");

	    //don't add nulls to temp
	    if(!dataFields[i].equals("")){
		//System.out.println("_______________________"+dataFields[i]);
		temp[temp_i++] = dataFields[i]+";";
	    }
	}

	this.dataFields = temp;
    }

    public void setMethodAtIndex(int index, String meth) {
       this.methods[index] = meth;
    }

    public void setMethods(String[] methods) {
	if (methods == null) {
	    return;
	}
	this.methods = methods;
    }    

    public void setPackageName(String name) {
        this.packageName = name;
    }

    public void setParent(Bubble parent) {
	if (parent == null) {
	    return;
	}
	this.parent = parent;
    }

    //changed to make it arraylist
    public void setVtable(ArrayList<String> vtable) {
	if (vtable == null) {
	    return;
	}
	this.vtable = vtable;
    }    

    //sets the vtable at index i to string s
    public void setVtableIndex(int i, String s) {
        this.vtable.set(i, s);
    }    

    public String toString() {
        StringBuilder s = new StringBuilder("Name: " + getName() + "\n");
        s.append("Package: " + getPackageName() + "\n");
        s.append("Children: " + childrenToString() + "\n");
        s.append("Parent: " + parentToString());
        return s.toString();
    }

    public String[] trim(String[] s) {
	int index = 0;
	String[] temp = new String[s.length];

	for (int i = 0; i < s.length; i++) {
	    String[] a = s[i].split(" ");
	    for (int j = 0; j < a.length; j++) {		
		if (a[j].startsWith(this.name)) {
		    temp[index++] = s[i];
		    break;
		}
	    }
	}

	String[] result = new String[index];
	for (int i = 0; i < index; i++) {
	    result[i] = temp[i];
	}
	return result;
    }    
}
