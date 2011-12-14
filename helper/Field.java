package xtc.oop.helper; //UPDATE PACKAGE
import java.util.ArrayList;
import xtc.tree.GNode;

public class Field{
    /*
     * For holding info about parameters and data fields
     */

    public String name;
    public String type;
    public boolean isArray;
    int arrayDims;
    boolean hasAssignment;
    GNode assignmentNode; //for the situations where a [someone forgot to finish comment]

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
        for(Integer i : concreteDims)
        {
            newDims.add(new Integer(i.intValue()));
        }
        copy.setConcreteDims(newDims);
        
        ArrayList<String> newModifiers = new ArrayList<String>();
        for(String s : modifiers)
        {
            newModifiers.add(new String(s));
        }
        copy.setModifiers(newModifiers);
        
        //MIGHT NEED TO BE CHANGED. This doesn not create a DEEP copy of the GNode
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
        if(type.equals("int"))
            type = "int32_t";
        else if(type.equals("boolean"))
            type = "bool";

        this.type = type;
    }
    public String getType(){
        return this.type;
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
