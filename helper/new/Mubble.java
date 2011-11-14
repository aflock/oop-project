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
    ArrayList<Field> parameters;

    public Mubble(String methodName) { // constructor with a method name
        constructor = false;
        main = false;
        staticMethod = false;
        this.methodName = methodName;
    }

    public void addParamter(Field parameter){
        //adds a parameter to both paraName and paraType lists
        //JK just gonna have a helper object param
        parameters.add(parameter);
    }

    String code() {
        return "";
    }

    /* generates header for .cc files */
    String ccHeader() {
        return "";
    }

    String forward() {
        StringBuilder s = new StringBuilder();
        s.append(returnType).append(" ").append(methodName).append("(").
            append(className.getName());
        for (String para : paraType) {
            s.append(", ").append(para);
        }
        s.append(");");

        return s.toString();
    }

    char from() {
        return from;
    }

    Bubble getBubble() {
        return className;
    }

    Pubble getPackage() {
        return packageName;
    }

    String getName() {
        return methodName;
    }

    String getReturnType() {
        return returnType;
    }

    String getVisibility() {
        return visibility;
    }

    ArrayList<Field> getParameters() {
        return parameters;
    }

    boolean isConstructor() { // returns true if this is constructor
        return constructor;
    }

    boolean isMain() { // returns ture if this is main method
        return main;
    }

    boolean isStatic() { // returns true if this is static method
        return staticMethod;
    }

    Mubble setBubble(Bubble className) {
        this.className = className;
        return this;
    }

    Mubble setConstructor(boolean constructor) {
        this.constructor = constructor;
        return this;
    }

    Mubble setFrom(char from) {
        this.from = from;
        return this;
    }

    Mubble setMain(boolean main) {
        this.main = main;
        return this;
    }

    Mubble setPackage(Pubble packageName) {
        this.packageName = packageName;
        return this;
    }

    Mubble setReturnType(String returnType) {
        this.returnType = returnType;
        return this;
    }

    Mubble setStatic(boolean staticMethod) {
        this.staticMethod = staticMethod;
        return this;
    }

    Mubble setVisibility(String visibility) {
        this.visibility = visibility;
        return this;
    }

    /*
    Mubble setParameterNames(ArrayList<String> paraName) {
        this.paraName = paraName;
        return this;
    }

    Mubble setParameterTypes(ArrayList<String> paraType) {
        this.paraType = paraType;
        return this;
    }
    */

    /* generates entry for vtable1 */
    String vTable1() {
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
    String vTable2() {
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
//{{{
    /*
       String header; //header for method
       String methName; //name of the method
       String name; //class method is in
       String code; //actual code of class, in Block() type node of AST
       String packageName;
       boolean mainMeth; //method is the main method
       boolean isConstructor;

       public Mubble(String iName, String iHeader, boolean construct)
       {
       this.name = iName;
       this.methName = extractMethodName(iHeader);
       if(construct){
       this.header = iHeader;
       }
       else
       this.header = formatMethodHeader(iHeader);
       this.code = "";
       this.isConstructor = construct;
       }

       public Mubble(String classname , String methodname,
       String header, boolean construct)
       {
       this.name = classname;
       this.methName = methodname;
       this.header = header;
       this.isConstructor = construct;
       }

       public String convertPrimitiveType(String s) {
       if (s.equals("int"))
       return "int32_t";
       if (s.equals("boolean"))
       return "bool";
       return s;
       }

       public String extractMethodName(String in)
       {

       String[] sploded = in.trim().split(" ");
       if (sploded[sploded.length - 1] == "main")
       mainMeth = true;
       else
       mainMeth = false;

       return sploded[sploded.length - 1];
       }

       public String formatMethodHeader(String in)
       {
    //====TODO===//
    //-Deal with isA methods
    if (mainMeth == true)
    return "int main(void)";


    //converts method header from .h format to .cc format
    //From: public String toString
    //To: String __String::toString(String __this) {
    if (in == null) { // in should not be null
    return null;
    }
    if (getName().equals("Object") ||
    getName().equals("String") ||
    getName().equals("Class")) return in;

    if (in.matches(".*[\\(\\)].*")) {
    return in;
    }

    String tab = "";

    for (int i = 0; i < in.length(); i++) {
        if (in.charAt(i) == '\t') tab = "\t";
    }

    int square = 0;
    for (int i = 0; i < in.length(); i++) {
        if (in.charAt(i) == '[') square++;
    }

    String[] temp2 = in.split("[ \t]");

    int count = 0;
    for (int j = 0; j < temp2.length; j++) {
        if (temp2[j].length() != 0) count++;
    }

    String[] temp = new String[count-square];
    int index = 0;
    for (int j = 0; j < temp2.length; j++) {
        if (temp2[j].length() != 0) {
            if (temp2[j].charAt(0) == '[') {
                //temp[index-1] += "[]";
                temp[index-1] = "__rt::Array<" + temp[index-1] + ">*";
            }
            else {
                temp[index++] = convertPrimitiveType(temp2[j]);
            }
        }
    }

    int num = 0;
    for (int j = 0; j < temp.length; j++) {
        if (temp[j].equals("public") ||
                temp[j].equals("private") ||
                temp[j].equals("protected") ||
                temp[j].equals("static")) {
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

    s += "_"  + getName() + "::" + temp[temp.length-1] + "(" +
        getName() + " __this";

    for (int j = index; j < temp.length - 1; j+=2) {
        s += ", " + temp[j] + " " + temp[j+1];
    }

    s += ")";
    return s + tab;
    }


public String getCode(){
    return this.code;
}

public String getHeader(){
    return this.header;
}

public String getMethName(){
    return this.methName;
}

public String getName(){
    return this.name;
}

public String getPackageName(){
    return this.packageName;
}

public static String getStringBetween(String src, String start, String end)
{
    int lnStart;
    int lnEnd;
    String ret = "";
    lnStart = src.indexOf(start);
    lnEnd = src.indexOf(end);
    if(lnStart != -1 && lnEnd != -1)
        ret = src.substring(lnStart + start.length(), lnEnd);

    return ret;
}


public boolean isConstructor()
{
    return this.isConstructor;
}

public boolean isModifier(String s)
{
    s = s.trim();

    if(s.equals("static") || s.equals("public") ||
            s.equals("private") || s.equals("protected"))
        return true;
    else
        return false;
}

//returns a String of the formatted method for .cc file
public String prettyPrinter()
{
    String ret = "";

    if(methName.equals("main")){
        ret += "int main(int argc, char* argv[]){\n";
        ret += this.code +"\n";
        ret += "return 0;\n";
        ret += "}\n";
    }
    else{
        if(isConstructor())
            ret += this.header + " : __vptr(&__vtable) {\n";
        else
            ret += this.header + "{\n";
        ret += this.code + "\n";
        ret += "}\n";
    }

    return ret;
}

public void setCode(String s) {
    this.code = s;
}

public void setPackageName(String pack)
{
    this.packageName = pack;
}
*///}}}
}



