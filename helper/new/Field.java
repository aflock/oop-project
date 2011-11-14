public class Field{
    /*
     * For holding info about parameters and data fields
     */

    String name;
    String type;
    boolean isArray;
    int arrayDims;
    ArrayList<String> modifiers = new ArrayList<String>();

    public Field(){};

    public Field(String name, String type, String modifier){
        this.name      = name;
        this.type      = type;
        this.isArray   = false;
        this.arrayDims = 0;

        modifiers.add(modifier);
    }

    public Field(String name, String type, ArrayList<String> modifiers){
        this.name = name;
        this.type = type;
        modifiers.addAll(modifiers);
    }

    public void setName(String name){
        this.name = name;
    }
    public void setType(String type){
        this.type = type;
    }
    public void setIsArray(boolean isArray){
        this.isArray = isArray;
    }
    public void setArrayDims(int arrayDims){
        this.arrayDims = arrayDims;
    }
    public void addModifier(String modifier){
        modifiers.add(modifier);
    }
}
