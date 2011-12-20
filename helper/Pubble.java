package xtc.oop.helper; //UPDATE PACKAGE
import java.util.ArrayList;

public class Pubble{
    String name; //The name of this package
    ArrayList<Pubble> children; //The package children
    ArrayList<Bubble> bubbles; //All the classes within this package
    Pubble parent; //The parent package

    //=========Constructors=======//
    public Pubble()
    {
        this.children = new ArrayList<Pubble>();
        this.bubbles = new ArrayList<Bubble>();
    }

    public Pubble(String name){
        this.name = name;
        this.children = new ArrayList<Pubble>();
        this.bubbles = new ArrayList<Bubble>();
    }

    public Pubble(String name, Pubble parent){
        this.children = new ArrayList<Pubble>();
        this.bubbles = new ArrayList<Bubble>();
        this.name = name;
        this.parent = parent;
    }



    //=========NON-GETTER/SETTER METHODS=======//

    //Adds a new package to this Pubble's packageChildren
    public void addChild(Pubble child){
        this.children.add(child);
    }

    public void removeChild(Pubble child){
        this.children.remove(child);
    }

    public void addBubble(Bubble b){
        this.bubbles.add(b);
    }

    //is originally called on root pubble, recursively goes and gets the contents of the .cc file
    //parameter is the name of the file being written to, so that we can include the .h file at the top
    //this is a helper method that calls the original getCC, we need this method so we only print the include
    //once per file
    public String getCC(String file){
        String ret = "#include \"" + file + ".h\"\n";
        ret += "#include <iostream>\n\n";
        //ret += "using namespace java::lang;"; //not sure if we need this
	// don't need it
        //ret += "using namespace std;\n\n";
        ret += this.getCC();
        return ret;
    }
    public String getCC(){
        String ret = "";


        if(!(name.equals("Default Package"))){
            String[] truncated = name.trim().split(" ");
            String tname = truncated[truncated.length-1];
            ret += "namespace " + tname + " {\n";
        }


        for(Bubble b: bubbles){
            ret += b.getCC();
        }

        //now do it for all children
        for(Pubble p : children){
            ret += p.getCC();
        }

        if(!(name.equals("Default Package")))
            ret += "}\n\n";

        //now put the main, but only once


        //hard code this
        if(!(name.equals("Default Package"))){
            String[] truncated = name.trim().split(" ");
            String tname = truncated[truncated.length-1];
            ret += "namespace " + tname + " {\n";
        }

        for(Bubble b : bubbles){
            ret += "_" + b.getName() + "_VT _" + b.getName() + "::__vtable;\n\n";
	    String bpack = b.getPackageName().trim();
	    System.out.println("W" + bpack);
	    System.out.println("T" + b.getParentBubble());
	    System.out.println("F" + b.getParentBubble().getName());
	    String parName = b.getParentBubble().getName();
	    String ppack = "java lang";
	    if(!(parName.equals("Object") || parName.equals("String") || parName.equals("Class"))) {
		ppack = b.getParentBubble().getPackageName().trim();
	    }
	    String p = b.getParentBubble().getName();


            ret += "Class " + (b.getPackageName().equals("Default Package") ? "": (b.getPackageName().trim().replace(" ", "::") + "::"))+ "_" + b.getName() + "::__class() { \n static Class k = new java::lang::__Class(__rt::literal(\"" + (bpack.equals("") ? "" : (bpack.replace(" ", ".") + "."))+b.getName() + "\"), "+(ppack.equals("") ? "" :ppack.replace(" ", "::")+"::")+((p.equals("Object") || p.equals("String") || p.equals("Class")) ? "_" : "")+"_" +p+"::__class());\nreturn k;\n}\n\n";
        }

        if(!(name.equals("Default Package")))
            ret += "}\n\n";

        if(name.equals("Default Package")){
            ret+=getMain();
        }

        return ret;
    }
     public String getMain(){
        //get the main
        String ret = "";
         for(Bubble b : bubbles){
             for(Mubble m : b.getMubbles()) {
                 if(m.isMain()) {
                     System.out.println("found main!~!");
                     ret += "int main(int argc, const char* argv[]) {\n";
                     ret += m.getCode();
                     ret += "return 0;\n";
                     ret += "}\n\n";
                     return ret;
                 }
             }
         }
         for(Pubble p : children){
            ret+= p.getMain();
         }
         return ret;
     }

    public String typeDef(){
        //returns absolute typedefs for all package's children's bubbles, recursively

        String ret = "";
        for(Bubble b : bubbles){
            //e.g. java::lang::Object
            String pkgpath = "";
            if(!(name.equals("Default Package")))
                pkgpath = "::" + name.trim().replace(" ", "::") + "::";
            /*

               while(x != null && !(x.getName().equals("Default Package"))) {
               String[] splitName = p.getName().trim().split(" ");
               pkgpath = splitName[splitName.length -1] + "::" + pkgpath;
               x = x.getParent();
               }
               */
            ret += "typedef " + "__rt::Ptr< " + pkgpath + "_" + b.getName() + "> " + b.getName() + ";\n";
        }
        for(Pubble p: children){
            ret += p.typeDef();
        }
        return ret;
    }

    //returns a string with the correct information for a .h file
    //lines will be delimited by \n but will not be correctly indented
    public String getH()
    {
        //a bit of hardcoding
        String ret = "#include \"java_lang.h\"\n\n";
        ret += "typedef __rt::Ptr<java::lang::__Object> Object;\n";
        ret += "typedef __rt::Ptr<java::lang::__Class> Class;\n";
        ret += "typedef __rt::Ptr<java::lang::__String> String;\n\n";
        ret += getForwardDecl();
        ret += "\n\n";
        ret += "//Absolute typedefs to make below code more readable\n";
        ret += typeDef();
        ret += "\n";
        ret += getVTables();

        return ret;
    }

    /*prints all forward declarations of data fields, vtables and typedefs for this pnode
      Ex.
      namespace lang {
      struct __Object;
      struct __Object_VT;

      typedef __Object* Object;
      }
      */
    public String getForwardDecl()
    {
        String ret = "";
        if(!(name.equals("Default Package"))){
            String[] truncated = name.trim().split(" ");
            String tname = truncated[truncated.length-1];
            ret += "namespace " + tname + " {\n";
        }
        for(Bubble b : bubbles){
            ret += b.getFDeclStruct();
        }

        /*
           for(Bubble b: bubbles){
           ret += b.getTypeDef();
           }
           */

        //now do it for all children
        for(Pubble p : children){
            ret += p.getForwardDecl();
        }
        if(!(name.equals("Default Package")))
            ret += "}";
        return ret;
    }


    //returns actual
    public String getVTables()
    {
        String ret = "";
        if(!(name.equals("Default Package"))){
            String[] truncated = name.trim().split(" ");
            String tname = truncated[truncated.length-1];
            ret += "namespace " + tname + " {\n";
        }
        for(Bubble b : bubbles){
            ret += b.getStruct();
            ret += b.getStructVT();
        }

        //now do it for all children
        for(Pubble p : children){
            ret += p.getVTables();
        }

        if(!(name.equals("Default Package")))
            ret += "}";
        return ret;
    }

    //=========GETTER/SETTER METHODS=======//

    public String getName(){
        return this.name;
    }
    public void setName(String n){
        this.name = n;
    }

    public Pubble getParent(){
        return this.parent;
    }
    public void setParent(Pubble p){
        this.parent = p;
    }

    public ArrayList<Bubble> getBubbles(){
        return this.bubbles;
    }
    //No Setter

    public ArrayList<Pubble> getChildren(){
        return this.children;
    }
    //No Setter
}
