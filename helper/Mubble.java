package xtc.oop.helper;
import java.util.ArrayList;

public class Mubble{
     String header; //header for method
     String name; //class method is in
     String code; //actual code of class, in Block() type node of AST
     
     public Mubble(String iName, String iHeader)
     {
        this.name = iName;
        this.header = iHeader;
        this.code = "";
     }
     
     public String formatMethodHeader(String in)
     {
     //====TODO===//
     //-Deal with isA methods
     
        //converts method header from .h format to .cc format
        //From: String (*getName)(Class);
        //To: String __Class::getName(Class __this) {
        
        String[] sploded = in.split(" ");
        String returnType = sploded[0];
        for (String s : sploded)
            System.out.println("   Sploded: ");
            
        return "";
     }
     
     public String getHeader(){
        return this.header;
     }
     
     public String getName(){
        return this.name;
     }
     
     public String getCode(){
        return this.code;
     }
}
