package xtc.oop;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.FileReader;

import java.util.*; //ut oh, is Grimm going to be mad?

import xtc.parser.ParseException;
import xtc.parser.Result;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;
import xtc.tree.Location;
import xtc.tree.Printer;

import xtc.lang.JavaFiveParser;

//OUR IMPORTS
import java.io.FileWriter;
import java.io.BufferedWriter;

import xtc.oop.helper.new.Bubble;   //NEED TO UPDATE TO OUR NEW DATA STRUCTURES
import xtc.oop.helper.new.Mubble;
import xtc.oop.helper.new.PNode;

public class StructureParser extends xtc.tree.Visitor //aka Decl
{

    public static ArrayList<Bubble> bubbleList;
    public static ArrayList<PNode> packageTree;
    public static ArrayList<Mubble> mubbleList;
    public static ArrayList<String> parsed; //keeps track of what ASTs have been parsed
    public Pubble curPub;
    public Bubble curBub;
    public Mubble curMub;

    public StructureParser(ArrayList<Pubble> packageTree, ArrayList<Mubble> mubbleList, ArrayList<Bubble> bubbleList, ArrayList<String> parsed)
    {
        this.packageTree = packageTree;
        this.mubbleList = mubbleList;
        this.bubbleList = bubbleList;
        this.parsed = parsed;
    }
    
    public void visit(Node n)
    {
        int counter = 1;
        if(n.hasProperty("parent0")) {
            Node temp = (Node)n.getProperty("parent0");

            while(temp != null) {
                n.setProperty("parent"+(counter++), temp.getProperty("parent0"));
                temp = (Node)temp.getProperty("parent0");
            }
        }

        for (Object o : n){
            if (o instanceof Node){
                ((Node)o).setProperty("parent_name", n.getName() );
                ((Node)o).setProperty("parent0", n );
                dispatch((Node)o);
            }
        }
    }


    public void visitClassDeclaration(GNode n){
        //n.getString(0) is the Modifiers node
        //n.getString(1) is the name of the class
        String className = n.getString(1);
        curBub = new Bubble(className);
        visit(n);


        //===================Getting Inheritance=====================//
        String parentName = "";
        if (!n.hasProperty("parent_class")){
            n.setProperty("parent_class", "Object");
        }
        parentName = (String)n.getProperty("parent_class");

        Boolean parentFound = false;
        Bubble parent = null;
        for(Bubble b : bubbleList){
            if(b.hasName(parentName)){             //if the parent has already been added by a child
                parent = b;                        
                parentFound = true;
                b.addBubble(curBub);               //add myself as my parent's child
            }
        }

        if(!parentFound){
            parent = new Bubble(parentName);
            bubbleList.add(parent);
            parent.addBubble(curBub);              //adding myself as my paren't child
        }
        
        //parentBubble is found at this point, setting it to curBub's parent, add curBub to parent's children
        curBub.setParentBubble(parent);


        //curBub should be complete here, all it's dataFields, methods, children bubbles, package..etc
        bubbleList.add(curBub);
    }



    public void visitFieldDeclaration(GNode n){
        visit(n);
    }

    public void visitDimensions(GNode n) {
        visit(n);
    }

    public void visitModifiers(GNode n){
        visit(n);

    }

    public void visitMethodDeclaration(GNode n){
        visit(n);

        /*
         * get method name
         * Create a new mubble
         * Visit
         * Add this new mubble to curbub
         * Also add to mubble list
         */

        //Create a Mubble to add
        Mubble freshMubble;
        String name = n.getString(3);
        if (name == "static"){
            name = n.getString(4);
            freshMubble = new Mubble(name);
            freshMubble.setStatic(true);
        }
        else{
            freshMubble = new Mubble(name);
        }

        visit(n);

        mubbleList.add(freshMubble);
        curBub.addMubble(freshMubble);

        /*//{{{
         * Old from Decl
        methods.add("");
        visit(n);
        String name = n.getString(3);
        if (name == "static")
            name = name + " " + n.getString(4);
        methods.set(methods.size()-1,methods.get(methods.size()-1)+" "+name);
        *///}}}

    }

    public void visitModifier(GNode n){
        visit(n);
        //parent0 = Modifiers
        Node parent1 = (Node)n.getProperty("parent1");
        Node parent2 = (Node)n.getProperty("parent2");
        if ((parent1.hasName("MethodDeclaration")) &&
                (parent2.hasName("ClassBody")))
        {
            String name = n.getString(0);
            methods.set(methods.size()-1,methods.get(methods.size()-1)+" "+name);
		}
		else if((parent1.hasName("FieldDeclaration")) &&
			(parent2.hasName("ClassBody"))) 
	    {
		    dataFields.set(dataFields.size()-1,dataFields.get(dataFields.size()-1)+" "+n.getString(0));
		}
     }
            
    public void visitDeclarators(GNode n) {
        visit(n);
    }

    public void visitDeclarator(GNode n) {
        visit(n);
    }

    public void visitIntegerLiteral(GNode n) {
        visit(n);
    }

    public void visitClassBody(GNode n){
        visit(n);
    }

    public void visitClassDeclaration(GNode n){
        visit(n);
    }

    public void visitFormalParameters(GNode n){
        visit(n);
    }

    public void visitFormalParameter(GNode n) {

        visit(n);
    }

    public void visitQualifiedIdentifier(GNode n){
        visit(n);
        Node parent0 = (Node)n.getProperty("parent0");
        Node parent1 = (Node)n.getProperty("parent1");
        Node parent2 = (Node)n.getProperty("parent2");
        
        //finding inheritance
        if ((parent1.hasName("Extension")) &&
        (parent2.hasName("ClassDeclaration"))){
            String name = n.getString(0);
            parent2.setProperty("parent_class", name);
        }
        
        //finding the package curBub belongs to
        if (parent0.getName().equals("PackageDeclaration")){
            //looping through something like...
            /*QualifiedIdentifier(
              "xtc",
              "oop",
              "helper"
            )*/
            String name, packageName;
            for(int i=0; i<n.size(); i++){
                name = n.getString(i);
                packageName += " " + name;
            }
            //check to see if this package is already in pubbleList
            Pubble packPub;
            Boolean inPubbleList = false;
            for(Pubble p : pubbleList)
            {
                if(p.getName().equals(packageName))
                {
                    inPubbleList = true;
                    packPub = p;
                }
            }
            
            //if its not in the packageList, create a new Pubble, and add it to pubbleList
            if(!inPubbleList)
            {
                packPub = new Pubble(packageName);
                pubbleList.add(packPub);
            }
            
            packPub.addBubble(curBub);
            curBub.setParentPubble(packPub);
        }
    }

    public void visitImportDeclaration(GNode n){
        visit(n);
    }

    public void visitForStatement(GNode n)
    {
        visit(n);
    }

    public void visitBasicForControl(GNode n)
    {
        visit(n);
    }

    public void visitPrimitiveType(GNode n) {
        visit(n);
    }

    public void visitType(GNode n)
    {
        visit(n);
    }

    public void visitExpressionList(GNode n)
    {
        visit(n);
    }

    public void visitRelationalExpression(GNode n)
    {
        visit(n);
    }

}
