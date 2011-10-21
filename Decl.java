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
import Bubble

/** A Java file Scope analyzer
 * For each static scope, prints
 * 		Enter scope at <filename>:<line>:<column>
 * upon entering the scope.
 *
 * @author Calvin Hawkes
 * @version 1.0
 */

public class Decl extends xtc.util.Tool
{
    public Decl()
    {
        // Nothing to do.
    }

    public String getName()
    {
        return "Java Scope Analyzer";
    }

    public String getCopy()
    {

        return "My Group";
    }

    public void init()
    {
        super.init();

        runtime.
            bool("printClassH", "printClassH", false, "print the .h that is interpreted from given AST").
            bool("printClassCC", "printClassCC", false, "Print Java AST.");
    }

    public Node parse(Reader in, File file) throws IOException, ParseException
    {
        JavaFiveParser parser = new JavaFiveParser(in, file.toString(), (int)file.length());
        Result result = parser.pCompilationUnit(0);

        return (Node)parser.value(result);
    }

    public void process(Node node)
    {
        if (runtime.test("printClassCC"))
        {
            //GET DAT
        }

        if (runtime.test("printClassH"))
        {
            //for .h filez
            new Visitor()
            {

                //assemble the forces
                ArrayList<String> dataFields = new ArrayList<String>();
                ArrayList<String> methods = new ArrayList<String>();
                String className = "";
                String tempString = "";
                int counter = 0;

                public void visitFieldDeclaration(GNode n){
                    dataFields.add("");
                    visit(n);
                    //dataField.add("\n");
                }

                public void visitDimensions(GNode n) {
                    visit(n);
                }

                public void visitModifiers(GNode n){
                    visit(n);
                }

                public void visitMethodDeclaration(GNode n){
                    methods.add("");
                    visit(n);
                    String name = n.getString(3);
                    methods.set(methods.size()-1,methods.get(methods.size()-1)+" "+name);
                }

                public void visitVoidType(GNode n){
                    visit(n);
                    Node parent1 = (Node)n.getProperty("parent1");
                    Node parent2 = (Node)n.getProperty("parent2");
                    if ((parent1.getName().equals("MethodDeclaration")) &&
                            (parent2.getName().equals("ClassBody"))){
                        methods.set(methods.size()-1,methods.get(methods.size()-1)+" void");
                    }
                }

                public void visitModifier(GNode n){
                    visit(n);
                    Node parent1 = (Node)n.getProperty("parent1");
                    Node parent2 = (Node)n.getProperty("parent2");
                    if ((parent1.getName().equals("MethodDeclaration")) &&
                            (parent2.getName().equals("ClassBody"))){
                        String name = n.getString(0);
                        methods.set(methods.size()-1,methods.get(methods.size()-1)+" "+name);
                    }
                }

                public void visitDeclarators(GNode n) {
                    visit(n);
                }

                public void visitCompilationUnit(GNode n) {

                    visit(n);

                    runtime.console().pln("CLASS NAME:");
                    runtime.console().pln(className);
                    runtime.console().pln("DATA FIELDS:");
                    for(String a : dataFields){
                        runtime.console().pln(a);
                    }
                    runtime.console().pln("METHOD HEADERS:");
                    for(String a : methods){
                        runtime.console().pln(a);
                    }
                    runtime.console().p("\n").flush();
                }

                public void visitDeclarator(GNode n) {
                    visit(n);
                    Node parent1 = (Node)n.getProperty("parent1");
                    Node parent2 = (Node)n.getProperty("parent2");
                    if ((parent1.getName().equals("FieldDeclaration")) &&
                            (parent2.getName().equals("ClassBody"))){
                        String name = n.getString(0);
                        dataFields.set(dataFields.size()-1,dataFields.get(dataFields.size()-1)+" "+name);
                    }

                }

                public void visitIntegerLiteral(GNode n) {
                    visit(n);
                }

                public void visitClassBody(GNode n){
                    visit(n);
                }

                ArrayList<String> methods = new ArrayList<String>;
                ArrayList<String> dataFields = new ArrayList<String>;
                ArrayList<String> children = new ArrayList<String>;
                String name;
                Bubble parent;
                //String parent;


                public void visitClassDeclaration(GNode n){
                    visit(n);
                    //get parent
                    //if none: parent = object
                    className = n.getString(1);
                    String parentName = "";
                    //get inheritance
                    if (!n.hasProperty("parent_class")){
                        n.setProperty("parent_class", "Object");
                    }
                    parentName = n.getProperty("parent_class");

                    Boolean parentFound = false;
                    Bubble parent;
                    for(Bubble b : bubbleList){
                        //if the bubble has already been added by a child
                        if(b.name.equals(parentName)){
                            //want to set the child field of this bubble with my name
                            parent = b;
                            parentFound = true;
                            b.addChild(className);
                        }
                    }
                    if(!parentFound){
                        parent = new Bubble(parentName, className);
                        bubbleList.add(parent);
                    }

                    //if classname in bubbleList
                    //set the data fields
                    Boolean bubbleExists = false;
                    for(Bubble b : bubbleList){
                        if(b.name.equals(className) {
                            b.setMethods(methods.toArray(new String[methods.size()]));
                            b.setDataFields(DataFields.toArray(new String[DataFields.size()]));
                            if(parent != null) //it won't ever be null, but just to make compiler happy :P
                                b.setParent(parent);
                            bubbleExists = true;
                        }
                    }
                    //else: make that node
                    if(!bubbleExists){
                        Bubble temp = new Bubble(className, methods, dataFields, n.getProperty("parent_class"), children?);
                        bubbleList.add(temp);
                    }
                }

                public void visitExtension(Gnode n){
                    visit(n)
                }

                public void visitFormalParameters(GNode n){

                    visit(n);
                    Node parent1 = (Node)n.getProperty("parent1");
                    Node parent2 = (Node)n.getProperty("parent2");

                    if ((parent1.getName().equals("MethodDeclaration")) &&
                            (parent2.getName().equals("ClassBody"))){
                        methods.set(methods.size()-1,methods.get(methods.size()-1)+"(");
                    }

                    //TODO this ending parens is out of order- is it necessary? need to discuss what format we need/want these in
                    if ((parent1.getName().equals("MethodDeclaration")) &&
                            (parent2.getName().equals("ClassBody"))){
                        methods.set(methods.size()-1,methods.get(methods.size()-1)+")");
                    }
                }

                public void visitFormalParameter(GNode n) {
                    visit(n);
                    Node parent1 = (Node)n.getProperty("parent1");
                    Node parent2 = (Node)n.getProperty("parent2");
                    if ((parent1.getName().equals("MethodDeclaration")) &&
                            (parent2.getName().equals("ClassBody"))){
                        String name = n.getString(3);
                        methods.set(methods.size()-1,methods.get(methods.size()-1)+" "+name);
                    }
                }

                public void visitQualifiedIdentifier(GNode n){
                    visit(n);
                    //for(String s : n.properties())
                    //    System.out.println(s);
                    Node parent1 = (Node)n.getProperty("parent1");
                    Node parent2 = (Node)n.getProperty("parent2");
                    //System.out.println(parent1);
                    //System.out.println(parent2);
                    if ((parent1.getName().equals("FieldDeclaration")) &&
                            (parent2.getName().equals("ClassBody"))){
                        String name = n.getString(0);
                        dataFields.set(dataFields.size()-1,dataFields.get(dataFields.size()-1)+" "+name);
                    }
                    if ((parent1.getName().equals("MethodDeclaration")) &&
                            (parent2.getName().equals("ClassBody"))){
                        String name = n.getString(0);
                        methods.set(methods.size()-1,methods.get(methods.size()-1)+" "+name);
                    }
                    if ((parent1.getName().equals("Extension")) &&
                            (parent2.getName().equals("ClassDeclaration"))){
                        String name = n.getString(0);
                        parent2.setProperty("parent_class", name);
                    }
                    //visit(n);
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

                public void visit(Node n)
                {

                    int counter = 1;
                    if(n.hasProperty("parent0")) {
                        Node temp = (Node)n.getProperty("parent0");

                        while(temp != null) {
                            //System.out.println(temp);
                            //temp = (Node)temp.getProperty("parent0");


                            n.setProperty("parent"+(counter++), temp.getProperty("parent0"));
                            temp = (Node)temp.getProperty("parent0");
                            //if(n.getProperty("parent2") == null)
                                //System.out.println(temp);
                        }
                    }
                    //don't need this, but not deleting.
                    for (String s : n.properties()) {
                        //System.out.println(n.getProperty(s));
                    }

                    for (Object o : n){
                        if (o instanceof Node){
                            ((Node)o).setProperty("parent_name", n.getName() );
                            ((Node)o).setProperty("parent0", n );
                            dispatch((Node)o);
                        }
                    }
                }
            }.dispatch(node);
        }
    }

    /**
     * Run the thing with the specified command line arguments.
     *
     * @param args The command line arguments.
     */
    ArrayList<Bubble> bubbleList = new ArrayList<Bubble>;
    public static void main(String[] args)
    {
        //Calvin and ALott
        String[] dependencies = <><><><><>;
        new Decl().run(args);
            Decl().run(finddependencies)
            for depend in dependencies:
                Decl().run(constructBubbles, depend)

    }
}



