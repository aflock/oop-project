//Using Decl in a non-hacky way
//

package xtc.oop;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.FileReader;

import java.util.*;

import xtc.parser.ParseException;
import xtc.parser.Result;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;
import xtc.tree.Location;

import xtc.tree.Printer;

import xtc.lang.JavaFiveParser;

//our imports
import xtc.oop.helper.Bubble;
import xtc.oop.helper.Mubble;
import xtc.oop.helper.Pubble;
import xtc.oop.helper.Field;

import xtc.oop.StructureParser;
import xtc.oop.ImplementationParser;
//
import java.util.regex.*;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class NewTranslator extends xtc.util.tool{


    public static ArrayList<Bubble> bubbleList; //Classes
    public static ArrayList<Pubble> pubbleList;	//Packages
    public static ArrayList<Mubble> mubbleList; //Methods

        super.init();
    }

    public Node parse(Reader in, File file) throws IOException, ParseException
    {
        JavaFiveParser parser = new JavaFiveParser(in, file.toString(), (int)file.length());
        Result result = parser.pCompilationUnit(0);

        return (Node)parser.value(result);
    }//}}}

    public static String findFile(String query) {//{{{

        String sep = System.getProperty("file.separator");
        String cp = System.getProperty("java.class.path");
        //Hardcoded as the working directory, otherwise real classpath
        cp = ".";

        query = query.replace(".",sep).concat(".java");
        //System.out.println("+++++"+query);
        return findFile(cp, query);
    }//}}}

    public static String findFile(String cp, String query) {//{{{
        String sep = System.getProperty("file.separator");
        File f = new File(cp);
        File [] files = f.listFiles();
        for(int i = 0; i < files.length; i++) {
            //System.out.println(sep+(cp.equals(".") ? "\\\\" : "")+cp+sep);
            //////////////////////////////////////
            //Hardcoding that sep is / and cp is .
            //////////////////////////////////////
            //System.out.println(query);
            if(files[i].isDirectory()) {
                String a = findFile(files[i].getAbsolutePath(), query);
                if(!a.equals(""))
                    return a;
            }
            else if(files[i].getAbsolutePath().replaceAll("/\\./",sep).endsWith(query))
                return files[i].getAbsolutePath();
        }
        return "";
    }//}}}


    public void process(Node node){

        StructureParser s = new StructureParser(pubbleList, mubbleList, bubbleList, parsed);
        s.dispatch(node);

        ImplementationParser i = new ImplementationParser(pubbleList, mubbleList, bubbleList, parsed);
        i.dispatch(node);


    }

    public static void main (String [] args)
    {
        /* new Translator
         * new StructParser //was Decl
         * new ImplParser   //was Impl
         */

        prepStructures();

        NewTranslator t = new NewTranslator();
        t.init();
        t.prepare();
        for(int i = 0; i< args.length; i++){
            try{ //TODO put in flag to not system exit also maybe change to run
                t.process(args[i]);
            } catch (Exception e) {System.out.println(e);}
        }

        //At this point, pubbleList contains all the packages but they aren't linked together
        constructPackageTree();

        //at this point, shit should be ready to print

    }

    //helper methods
    public void prepStructures(){

        /*
         * create root packageNode
         * create empty BubbleList
         * create empty MubbleList
         * create langList(?)
         *
         * create Object and String and Array (exception too!) Bubbles
         */
        populateLangList();//putting all of java_lang methods into the langList

        pubbleList.add(new Pubble("DefaultPackage", null)); //parent is null
        //pre-load Object Bubble
        Bubble object = new Bubble("Object", null);

        /*DO WE NEED THIS? HOW ARE WE CONSTRUCTING A VTABLE NOW? */
        //Creating Object's Vtable
        object.add2Vtable("Class __isa;");
        object.add2Vtable("int32_t (*hashCode)(Object);");
        object.add2Vtable("bool (*equals)(Object, Object);");
        object.add2Vtable("Class (*getClass)(Object);");
        object.add2Vtable("String (*toString)(Object);");
        bubbleList.add(object);


        //pre-load String Bubble
        Bubble string = new Bubble("String", null);
        //Creating Object's Vtable
        string.add2Vtable("Class __isa;");
        string.add2Vtable("int32_t (*hashCode)(String);");
        string.add2Vtable("bool (*equals)(String, Object);");
        string.add2Vtable("Class (*getClass)(String);");
        string.add2Vtable("String (*toString)(String);");
        string.add2Vtable("int32_t (*length)(String);");
        string.add2Vtable("char (*charAt)(String, int_32_t);");
        bubbleList.add(string);
    }
}

