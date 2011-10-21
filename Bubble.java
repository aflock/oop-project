package xtc.oop;
public class Bubble{
    public String name;
    public String[] methods;
    public String[] dataFields;
    public Bubble parent;
    public String[] children;

    public Bubble(String name, String[] methods,
            String[] dataFields, Bubble parent, String[] children){

        this.name = name;
        this.methods = methods;
        this.dataFields = dataFields;
        this.parent = parent;
        this.children = children;
    }
}
