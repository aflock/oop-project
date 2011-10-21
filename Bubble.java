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
}
