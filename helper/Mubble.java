package xtc.oop.helper;
import java.util.ArrayList;

public class Mubble{
     String header; //header for method
     String name; //class method is in
     String code; //actual code of class, in Block() type node of AST
     
     public Mubble(String iName, String iHeader)
     {
        this.name = iName;
        this.header = formatMethodHeader(iHeader);
        System.out.println("========HEADER: " + iHeader);
        System.out.println("========HEADER: " + header);
        this.code = "";
     }
     
     public String formatMethodHeader(String in)
     {
     //====TODO===//
     //-Deal with isA methods
     //-Go from METHOD FORMAT NOT VTABLE FORMAT
     
        //converts method header from .h format to .cc format
        //From: public String toString
        //To: String __String::toString(String __this) {
        String returnType = "";
        String methodName = "";
        String params = this.name + " __this";
        String[] sploded = in.split(" ");
        int index = 1;
        //Skips public static private, protected...
        if (isModifier(sploded[index]))
        {
            index++;
        }
        
        if ((sploded.length - (index) % 2) == 0) //if even, there is a return type present
        {
            returnType = sploded[index];
            index++;
        }
        else //void return type
            returnType = "void";
        
        boolean type = true;
        for(int i = index; i < sploded.length-1; i++)
        {
            if (type) //it is the param type
            {
                params = params + " " + sploded[i];
                type = false;
            }
            else //it is the variable name
            {
                params = params + ", " + sploded[i];
                type = true;
            }
        }
             
        methodName = sploded[sploded.length - 1];
            
        return returnType + "__" + this.name + "::" + methodName + "(" + params + ")";
     }
     
     public boolean isModifier(String s)
     {

        s = s.trim();

        if(s.equals("static") || s.equals("public") || s.equals("private") || s.equals("protected"))
            return true;
        else
            return false;
     }
     
    public static String getStringBetween(String src, String start, String end)  
    {  
        int lnStart; 
        int lnEnd;  
        String ret = "";  
        lnStart = src.indexOf(start);  
        lnEnd = src.indexOf(end);  
        System.out.println("lnStart: " + lnStart + "\nlnEnd: " + lnEnd);
        if(lnStart != -1 && lnEnd != -1)  
            ret = src.substring(lnStart + start.length(), lnEnd);  
      
            return ret;  
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
