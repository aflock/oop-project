package xtc.oop.helper;
import java.util.ArrayList;

//import helper.Pubble;
//import helper.Bubble;
//import helper.Field;

public class Mubble {
    private final char INHERITED = 'i';
    private final char NEW = 'n';
    private final char OVERLOADED = 'l';
    private final char OVERWRITTEN = 'w';

    boolean constructor;
    boolean main;
    boolean staticMethod;
    char from;

    Bubble className;
    Pubble packageName;

    String methodName;
    String returnType; //if none and not a constructor -->> void
    String visibility;
    ArrayList<String> paraName;
    ArrayList<String> paraType;
    ArrayList<String> paraMod;

    ArrayList<Field> parameters;

    public Mubble(String methodName) { // constructor with a method name
        constructor = false;
        main = false;
        staticMethod = false;
        this.methodName = methodName;
    }

    public void addParameter(Field parameter){
        //adds a parameter to both paraName and paraType lists
        //JK just gonna have a helper object param
        parameters.add(parameter);
    }

    public String code() {
        return "";
    }

    /* generates header for .cc files */
    public String ccHeader() {
        return "";
    }

    public String forward() {
        StringBuilder s = new StringBuilder();
        s.append(returnType).append(" ").append(methodName).append("(").
            append(className.getName());
        for (String para : paraType) {
            s.append(", ").append(para);
        }
        s.append(");");

        return s.toString();
    }

    public char from() {
        return from;
    }

    public Bubble getBubble() {
        return className;
    }

    public Pubble getPackage() {
        return packageName;
    }

    public String getName() {
        return methodName;
    }

    public String getReturnType() {
        return returnType;
    }

    public String getVisibility() {
        return visibility;
    }

    public ArrayList<String> getParameterNames() {
	return paraName;
    }

    public ArrayList<String> getParameterModifier() {
	return paraMod;
    }

    public ArrayList<String> getParameterTypes() {
	return paraType;
    }

    public ArrayList<Field> getParameters() {
        return parameters;
    }

    public boolean isConstructor() { // returns true if this is constructor
        return constructor;
    }

    public boolean isMain() { // returns ture if this is main method
        return main;
    }

    public boolean isStatic() { // returns true if this is static method
        return staticMethod;
    }

    public Mubble setBubble(Bubble className) {
        this.className = className;
        return this;
    }

    public Mubble setConstructor(boolean constructor) {
        this.constructor = constructor;
        return this;
    }

    public Mubble setFrom(char from) {
        this.from = from;
        return this;
    }

    public Mubble setMain(boolean main) {
        this.main = main;
        return this;
    }

    public Mubble setPackage(Pubble packageName) {
        this.packageName = packageName;
        return this;
    }

    public Mubble setReturnType(String returnType) {
        this.returnType = returnType;
        return this;
    }

    public Mubble setStatic(boolean staticMethod) {
        this.staticMethod = staticMethod;
        return this;
    }

    public Mubble setVisibility(String visibility) {
        this.visibility = visibility;
        return this;
    }

    public Mubble setParameters() {
	for (Field f : parameters) {
	    paraName.add(f.name);
	    paraType.add(f.type);
	    if (f.modifiers.size() == 1) {
		paraMod.add(f.modifiers.get(0));
	    }
	    else if (f.modifiers.size() == 0) {
		paraMod.add("");
	    }
	    else {
		System.out.println("Error size cannot be bigger than 2");
	    }
	}
	    return this;
    }
    
    public Mubble setParameterNames(ArrayList<String> paraName) {
        this.paraName = paraName;
        return this;
    }

    public Mubble setParameterTypes(ArrayList<String> paraType) {
        this.paraType = paraType;
        return this;
    }    

    /* generates entry for vtable1 */
    public String vTable1() {
        StringBuilder s = new StringBuilder();
        s.append(returnType).append(" (*");
        s.append(methodName).append(")(");
        if (!isStatic()) {
            s.append(className.getName());
            for (String para : paraType) {
                s.append(", ").append(para);
            }
        }
        else {
            // not sure what to do with static methods
            if (paraType.size() > 0) {
                s.append(paraType.get(0));
            }

            for (int i = 1; i < paraType.size(); i++) {
                s.append(", ").append(paraType.get(i));
            }
        }

        s.append(");");

        return s.toString();
    }

    /* generates entry for vtable.
     * needs fixing. proper casting is needed.
     * TODO @DK now that we are using a unified parameter instead of two this will be slightly different. TNX
     */
    public String vTable2() {
        StringBuilder type = new StringBuilder();
        if (from == INHERITED) {
            type.append("(").append(returnType).append("(*)");
            type.append(className.getName());
            for (String para : paraType) {
                type.append(",").append(para);
            }
            type.append(")");
        }

        StringBuilder s = new StringBuilder();
        s.append(methodName).append("(");
        if (type != null) {
            s.append(type.toString()).append(")");
        }

        if (from == INHERITED) { // this line is not quite right
            s.append("&_").append(className.getParentBubble().getName());
        }
        else {
            s.append("&_").append(className.getName());
        }
        s.append("::").append(methodName);

        return s.toString();
    }
}
