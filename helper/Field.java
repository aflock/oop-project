package xtc.oop.helper;
import java.util.ArrayList;
import xtc.oop.StructureParser; //to use cppify
import xtc.tree.GNode;

public class Field{
    /*
     * For holding info about parameters and data fields
     */

    public String name;
    public String type;//static type
	public String dynamicType; //gets set at field resolution time
    public boolean isArray;
    int arrayDims;
    boolean hasAssignment;
    GNode assignmentNode; //for the situations where a field is assigned based on some code

    ArrayList<Integer> concreteDims = new ArrayList<Integer>();
    ArrayList<String> modifiers = new ArrayList<String>();

    public Field(){
        //used to create Deep Copies of Fields
    };

    public Field(String name, String type, String modifier){
        this.name           = name;
        this.type           = type;
        this.isArray        = false;
        this.arrayDims      = 0;
        this.hasAssignment  = false;
        this.assignmentNode = null;
        modifiers.add(modifier);
    }

    public Field(String name, String type){
        this.name           = name;
        this.type           = type;
        this.isArray        = false;
        this.arrayDims      = 0;
        this.hasAssignment  = false;
        this.assignmentNode = null;
    }

    public Field(String name, String type, ArrayList<String> modifiers){
        this.name           = name;
        this.type           = type;
        this.isArray        = false;
        this.arrayDims      = 0;
        this.hasAssignment  = false;
        this.assignmentNode = null;
        modifiers.addAll(modifiers);
    }

    /* @return: returns a deep clone of the Field old
    To be used in inheritMethods() in Bubble
    Creates deep clones of every data member of old Field.
    */
    public Field deepCopy()
    {

        Field copy = new Field();
        copy.setName(new String(this.getName()));
        copy.setType(new String(this.getType()));
        copy.setIsArray(this.getIsArray());
        copy.setArrayDims(this.getArrayDims());

        ArrayList<Integer> newDims = new ArrayList<Integer>();
        for(Integer i : this.concreteDims)
        {
            newDims.add(new Integer(i.intValue()));
        }
        copy.setConcreteDims(newDims);


        ArrayList<String> newModifiers = new ArrayList<String>();
        for(String s : this.modifiers)
        {
            newModifiers.add(new String(s));
        }
        copy.setModifiers(newModifiers);

        //MIGHT NEED TO BE CHANGED. This doesn not create a DEEP copy of the GNode
        if(this.getAssignmentNode() != null)
            copy.setAssignment(this.getAssignmentNode());

        return copy;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    public void setType(String type){
        //TRANSLATE TYPES HERE, do we need to do this for booleans?
        this.type = StructureParser.cppify(type);
    }
    public String getType(){
        return this.type;
    }
    public void setDymanicType(String type){
        this.dynamicType = StructureParser.cppify(type);
    }
    public String getDynamicType(){
        return this.dynamicType;
    }

    public void setIsArray(boolean isArray){
        this.isArray = isArray;
    }
    public boolean getIsArray(){
        return this.isArray;
    }

    public void setArrayDims(int arrayDims){
        this.arrayDims = arrayDims;
    }
    public int getArrayDims(){
        return this.arrayDims;
    }

    public void setConcreteDims(ArrayList<Integer> dims){
        this.concreteDims = dims;
    }

    public void setModifiers(ArrayList<String> mods){
        this.modifiers = mods;
    }

    public void addModifier(String modifier){
        modifiers.add(modifier);
    }
    public void addConcreteDimension(int dim){
        concreteDims.add(dim);
    }
    public void setHasAssignment(boolean hasAssignment){
        this.hasAssignment = hasAssignment;
    }

    public void setAssignment(GNode assignmentNode){
        this.assignmentNode = assignmentNode;
    }

    public GNode getAssignmentNode(){
        return this.assignmentNode;
    }

    public ArrayList<String> getModifiers(){
        return this.modifiers;
    }

    public boolean hasAssignment()
    {
        return this.hasAssignment;
    }

    //for if the field is an array
    public int getDimensions()
    {
        return this.arrayDims;
    }

    public boolean isStatic()
    {
        for(String s : modifiers)
        {
            if(s.equals("static"))
                return true;
        }
        return false;
    }
}
