package xtc.oop.helper;
import java.util.ArrayList;

public class Bubble{
	ArrayList<Bubble> bubbles; //ArrayList of the children of this Bubble
	ArrayList<Mubble> mubbles; //ArrayList of Mubbles in this class
	ArrayList<Field> dataFields; // Field is a new structure created to hold both variable name, type, and modifiers
	String name; //This class' name
	Bubble parentBubble; //This Bubble's parent Bubble
	Pubble parentPubble; //This class' package (reference)
	//Removed Constructor arraylist because mubbles have a flag for that

	/* DO WE NEED THIS?:
	   String visibility; //The visibility for this class
	   */

	////////////////////
	/* CONSTRUCTOR(S) */
	////////////////////

	public Bubble(String name) {
		//TODO: Make this constructor, what are the params?
		//What's the context it's called from?

		this.name = name;
		this.bubbles = new ArrayList<Bubble>();
		this.dataFields = new ArrayList<Field>();
		this.mubbles = new ArrayList<Mubble>();
	}

	public Bubble(){
		this.bubbles = new ArrayList<Bubble>();
		this.dataFields = new ArrayList<Field>();
		this.mubbles = new ArrayList<Mubble>();
	}


	////////////////
	/* Other Meth */
	////////////////
	public boolean hasName(String name)
	{
		if (this.name.equals(name))
			return true;
		else
			return false;
	}

	/////////////
	/* SETTERS */
	/////////////


	//Add a Bubble to the Bubble list
	public void addBubble(Bubble b) {
		this.bubbles.add(b);
	}

	//Add a data field
	public void addField(Field f) {
		this.dataFields.add(f);
	}

	//Add a Mubble to this Bubble
	public void addMubble(Mubble m) {
		this.mubbles.add(m);
	}

	//Set the name of this Bubble
	public void setName(String name) {
		this.name = name;
	}

	//Set the parent Bubble of this Bubble
	public void setParentBubble(Bubble b) {
		this.parentBubble = b;
	}

	//Set the parent Pubble of this Bubble
	public void setParentPubble(Pubble p) {
		this.parentPubble = p;
	}


	/* Setter for visibility
	//Set the visibility of this Bubble
	public void getVisibility(String visibility) {
	this.visibility = visibility;
	}
	*/

	/////////////
	/* GETTERS */
	/////////////

	//Returns ArrayList of child Bubbles
	public ArrayList<Bubble> getBubbles() {
		return this.bubbles;
		//return this.bubbles.toArray();
	}

	//Returns ArrayList of dataFields
	public ArrayList<Field> getDataFields() {
		return this.dataFields;
	}

	//Returns ArrayList of Mubbles
	public ArrayList<Mubble> getMubbles() {
		return this.mubbles;
		//return this.mubbles.toArray();
	}

	//Returns the name of this class
	public String getName() {
		return this.name;
	}

	//Returns this class' parent class
	public Bubble getParentBubble() {
		return this.parentBubble;
	}

	//Returns this class' package
	public Pubble getParentPubble() {
		return this.parentPubble;
	}

	public String getPackageName(){
		return parentPubble.getName();
	}


	/* Getter for visibility
	//Returns the visibility of this class (public, private, etc.)
	public String getVisibility() {
	return this.visibility;
	}
	*/

	///////////////////
	/* MISC. METHODS */
	///////////////////

	public String getTypeDef() {
		String pkgpath = "";
		Pubble p = this.parentPubble;
		//e.g. java::lang::Object
		while(p != null) {
			pkgpath = p.getName() + "::" + pkgpath;
			p = p.getParent();
		}
		//why do we need two typedefs? one with underscores one without?
		return "typedef " + pkgpath + this.name + " " + this.name + ";\n" +
			"typedef " + pkgpath + "_"+this.name + " _" + this.name + ";\n";
	}

	public String getFDeclStruct() {
		return "struct _"+this.name+";\n"+
			"struct _"+this.name+"_VT;\n";
	}

	public String getStruct() {
		String struct = "struct _" + this.name + " {\n";
		//indent
		struct += "//Data fields\n";
		//add the VT vptr
		struct += "_"+this.name+"_VT* __vptr;\n";
		//iterate through datafields, print them
		for(int i = 0; i < this.dataFields.size(); i++) {
			//output data fields
		}
		struct+="\n//Constructors\n";
		for(Mubble constructor : mubbles) {
			if(constructor.isConstructor())
				struct += "_"+constructor.getName()+"();\n";
		}
		struct+="\n//Forward declaration of methods\n";
		//Hardcoding the vt and class
		struct += "static Class __class();\n" +
			"static _"+this.name+"_VT __vtable;\n";
		for(Mubble m : mubbles) {
			//HARDCODING STATIC, MAY NEED TO CHANGE
			struct+= "static "+m.forward() + "\n";
		}
		//unindent
		struct += "};\n";
		return struct;
	}

	public String getStructVT() {
		String struct = "// The vtable layout for "+this.name+".\n";
		struct += "struct _"+this.name+"_VT {\n";
		//indent
		//Hardcoding class
		struct += "Class __isa;";
		for(Mubble m : mubbles) {
			struct += m.vTable1()+"\n";
		}
		//Make VT constructor in-line, hardcoding class (indent?)
		struct+="\n_"+this.name+"_VT()\n: __isa(_"+this.name+"::__class())";
		for(Mubble m : mubbles) {
			struct += ",\n"+m.vTable2();
		}
		struct+=" {\n";
		//unindent
		struct+="}\n";
		//unindent
		struct+="};\n";
		return struct;
	}
}
