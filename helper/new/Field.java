public class Field{
    /*
     * For holding info about parameters and data fields
     */

    String name;
    String type;
    ArrayList<String> modifiers = new ArrayList<String>();

    public Field(String name, String type, String modifier){
        this.name = name;
        this.type = type;
        modifiers.add(modifier);
    }

    public Field(String name, String type, ArrayList<String> modifiers){
        this.name = name;
        this.type = type;
        modifiers.addAll(modifiers);
    }
}
