package xtc.oop;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.FileReader;

import xtc.parser.ParseException;
import xtc.parser.Result;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;
import xtc.tree.Location;

import xtc.lang.JavaFiveParser;

/** A Java file Scope analyzer
 * For each static scope, prints
 * 		Enter scope at <filename>:<line>:<column>
 * upon entering the scope.
 *
 * @author Calvin Hawkes
 * @version 1.0
 */

public class Scoper extends xtc.util.Tool
{
    public Scoper()
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
            bool("printH", "printH", false, "print the .h that is interpreted from given AST").
            bool("printJavaAST", "printJavaAST", false, "Print Java AST.");
    }

    public Node parse(Reader in, File file) throws IOException, ParseException
    {
        JavaFiveParser parser = new JavaFiveParser(in, file.toString(), (int)file.length());
        Result result = parser.pCompilationUnit(0);

        return (Node)parser.value(result);
    }

    public void process(Node node)
    {
        if (runtime.test("printJavaAST"))
        {
            runtime.console().format(node).pln().flush();
        }

        if (runtime.test("printH"))
        {
            //for .h filez
            new Visitor()
            {
                private int count = 0;


                String[] fields;

                public int toIndex(String s) {
                    if(s.equals("public"))
                        return 0;
                    else if(s.equals("private"))
                        return 1;
                    else if(s.equals("protected"))
                        return 2;
                    else
                        return -1;
                }

                String[] classMembers;


                public void visitFieldDeclaration(GNode n){
                    /*
                       if(n.hasProperty("parent") &&
                       ((Node)(n.getProperty("parent"))).getName().equals("ClassBody")){
                    //runtime.console().p(printStringDescendants(n)).flush();
                       }
                       */
                    visit(n);
                }

                //Doesn't work right now because we use printStringDescendents
                public void visitDimensions(GNode n) {
                    if(!n.isEmpty()) {
                        for (int i = 0; i < n.size(); i++) {
                            if(n.get(i) instanceof String) {
                                n.set(i,n.getString(i)+"]");
                            }
                        }
                    }
                    visit(n);
                }

                public void visitModifiers(GNode n){
                    Node temp = (Node)(n.getProperty("parent"));
                    if (n.isEmpty() && (temp.getName().equals("FieldDeclaration") || temp.getName().equals("MethodDeclaration"))){
                        temp.setProperty("visibility", new Integer(3));
                    }
                    visit(n);

                }

                String tempString = "";
                public void visitMethodDeclaration(GNode n){
                    //want to skip main method, since that will be put in main.cc
                    if((n.getString(3) != null) && !(n.getString(3).equals( "main") )){
                        tempString = "";
                        //get visibility
                        int index = -1;
                        Node firstChild = n.getNode(0);
                        if (!firstChild.isEmpty()){
                            Node secondChild = firstChild.getNode(0);
                            String visibility = secondChild.getString(0);
                            index = toIndex(visibility);
                            if(index != -1){
                                n.setProperty("visibility", new Integer(index));
                            }
                            else{
                                n.setProperty("visibility", new Integer(3));
                            }
                        }
                        else{
                            n.setProperty("visibility", new Integer(3));
                        }

                        //we assume visibility exists as a property
                        //get Method name
                        String name = n.getString(3);

                        index = (Integer)(n.getProperty("visibility"));
                        tempString += name + "(";
                        visit(n);
                    }
                }

                public void visitModifier(GNode n){
                    String name = "";
                    int index;

                    Node temp = (Node)(n.getProperty("parent"));
                    Node grandParent = (Node)(temp.getProperty("parent"));
                    if (grandParent.getName().equals("FieldDeclaration")){
                        if (!n.isEmpty()){
                            name =  n.getString(0);
                            index = toIndex(name);
                            if(index != -1)
                                grandParent.setProperty("visibility", new Integer(index));
                            else//not a visibility marker
                                classMembers[((Integer) (grandParent.getProperty("visibility"))).intValue()] += name + " ";

                        }
                    }

                    else if (grandParent.getName().equals("MethodDeclaration")){

                    }


                    visit(n);

                }

                public void visitDeclarators(GNode n) {
                    if (((Node)(n.getProperty("parent"))).getName().equals("BasicForControl"))
                    {
                        visit(n);
                        runtime.console().p("; ").flush();
                    }
                    visit(n);
                }

                public void visitDeclarator(GNode n) {
                    Node temp = (Node)(n.getProperty("parent"));
                    Node grandParent = (Node)(temp.getProperty("parent"));
                    for(Object o : n) {
                        if (grandParent.getName().equals("FieldDeclaration")){
                            //Hacks V
                            if (!n.isEmpty() && o instanceof String){
                                String name =  n.getString(0);
                                classMembers[((Integer) (grandParent.getProperty("visibility"))).intValue()] += name + " " + (n.getNode(2) != null ? "= " : "");

                            }
                        }

                        else if(o instanceof String)
                            runtime.console().p(o.toString());
                        else if (o == null)
                            runtime.console().p("=");
                        else
                            printStringDescendants((GNode)o);

                    }
                    visit(n);
                    //Appending semi-colon
                    if (grandParent.getName().equals("FieldDeclaration")){
                        classMembers[((Integer) (grandParent.getProperty("visibility"))).intValue()] += ";\n    ";
                    }
                }

                public void visitIntegerLiteral(GNode n) {
                    Node temp = (Node)(n.getProperty("parent"));
                    Node grandParent = (Node)(temp.getProperty("parent"));
                    Node greatGrandParent = (Node)(grandParent.getProperty("parent"));
                    if(greatGrandParent instanceof GNode  && greatGrandParent.getName().equals("FieldDeclaration"))  {
                        classMembers[((Integer) (greatGrandParent.getProperty("visibility"))).intValue()] += n.getString(0);
                    }
                    visit(n);
                }

                public void visitClassBody(GNode n){
                    String [] mems = {"public:\n    ", "private:\n    ", "protected:\n    ", ""};
                    classMembers = mems;

                    //fields = {"foo","bar"};
                    runtime.console().pln("{");
                    for(Object o : n){
                        if((o instanceof GNode) && (((Node)o).getName().equals("FieldDeclaration" ) )){
                        }
                        //Some shit
                        //iterate and when we see a field declaration add the correct
                        //string to the current field['correct string'] the array
                        //at end of all children, flush that.
                    }
                    visit(n);
                    for (int i = 0; i<classMembers.length; i++){
                        if(!(classMembers[i].equals("public:\n    ") || classMembers[i].equals("private:\n    ") || classMembers[i].equals("protected:\n    ")))
                            runtime.console().pln(classMembers[i]);
                    }
                    runtime.console().pln("};").flush();
                }

                public void visitClassDeclaration(GNode n){
                    //print class
                    runtime.console().p("class ");
                    //print modifiers
                    //print name
                    for( Object o : n ){
                        if(o instanceof String){
                            runtime.console().p(o.toString() + " ");
                        }

                        /*
                           else if( o instanceof Node &&
                           ((Node)o).getName().equals("ClassBody")){
                           runtime.console().pln("{");
                           runtime.console().pln("};");
                           }
                           */

                    }
                    visit(n);
                    runtime.console().pln().flush();

                }

                public void visitFormalParameters(GNode n){
                    Node temp = (Node)(n.getProperty("parent"));

                    visit(n);

                    if(temp.getName().equals("MethodDeclaration")){
                        Integer index = (Integer)(temp.getProperty("visibility"));
                        classMembers[index] +=  ");\n";
                    }
                }

                public void visitFormalParameter(GNode n) {
                    Node temp = (Node)(n.getProperty("parent"));
                    Node grandParent = (Node)(temp.getProperty("parent"));

                    if (grandParent.getName().equals("MethodDeclaration")) {
                        String t = printStringDescendants(n);
                        //System.out.println(t);
                        //tempString += printStringDescendants(n) + ", ";
                        int index = (Integer)(grandParent.getProperty("visibility"));

                        tempString += t + (temp.indexOf(n) != temp.size()-1 ? ", " : "");
                        classMembers[index] += tempString;
                        tempString = "";
                    }

                    visit(n);
                }

                public void visitQualifiedIdentifier(GNode n){
                    Node temp = (Node)(n.getProperty("parent"));
                    Node grandParent = (Node)(temp.getProperty("parent"));
                    if(n.hasProperty("parent") && (temp.getName().equals("ImportDeclaration"))){
                        for(Object o : n){
                            if(o instanceof String){
                                runtime.console().p(o.toString() + ".");
                            }
                        }
                    }

                    if(grandParent.getName().equals("MethodDeclaration")){
                        Integer index = (Integer)(grandParent.getProperty("visibility"));
                        String name = n.getString(0).toLowerCase();
                        //System.out.println(tempString);
                        classMembers[index] += name + " " + tempString;
                    }
                    //clear just in case
                    tempString = "";
                }

                public void visitImportDeclaration(GNode n){
                    //TODO change to #include for C++ goodness
                    runtime.console().p("import ");

                    visit(n);

                    for(Object o : n){
                        if(o instanceof String){
                            runtime.console().p(o.toString() + ".");
                        }
                    }
                    runtime.console().pln(";").flush();
                }

                public void visitForStatement(GNode n)
                {
                    //Calvin's Modifications 10-10-11
                    /*
                       for(Object o : n)
                       {
                       runtime.console().pln("child: " + o.toString());
                       }
                       */

                    visit(n);
                    runtime.console().pln("}").flush();
                }

                public void visitBasicForControl(GNode n)
                {
                    /* NOTES:
                       -Always has 3 children (int i=0; i < 9; i++)
                       */
                    runtime.console().p("for(").flush();
                    visit(n);
                    runtime.console().pln(") {").flush();
                }

                public void visitPrimitiveType(GNode n) {
                    Node parent = ((Node)(n.getProperty("parent")));
                    Node grandParent = ((Node)(parent.getProperty("parent")));
                    if(grandParent.getName().equals("FieldDeclaration")) {
                        if (!n.isEmpty()){
                            String name =  n.getString(0);

                            classMembers[((Integer) (grandParent.getProperty("visibility"))).intValue()] += name + " ";
                        }
                    }
                    visit(n);
                }

                public void visitType(GNode n)
                {
                    if (((Node)(n.getProperty("parent"))).getName().equals("BasicForControl"))
                    {
                        printStringDescendants(n);
                        runtime.console().p(" ").flush();
                    }
                    visit(n);
                }





                public void visitExpressionList(GNode n)
                {
                    if (((Node)(n.getProperty("parent"))).getName().equals("BasicForControl"))
                    {
                        printStringDescendants(n);
                        runtime.console().p("; ").flush();
                    }
                }

                public void visitRelationalExpression(GNode n)
                {
                    if (((Node)(n.getProperty("parent"))).getName().equals("BasicForControl"))
                    {
                        printStringDescendants(n);
                        runtime.console().p("; ").flush();
                    }
                }

                //Recursively goes through GNode n's descendants and prints the strings
                public String printStringDescendants(GNode n)
                {
                    String toReturn = "";
                    //runtime.console().pln("PARENT NODE: " + ((Node)(n.getProperty("parent"))).getName()).flush();
                    //runtime.console().pln("NODE: " + n.getName()).flush();
                    for(Object o : n)
                    {
                        if(o != null)
                        {
                            //runtime.console().pln("CHILD: " + o.toString()).flush();
                            if(o instanceof String){
                                //System.out.println(o.toString());
                                toReturn +=  o.toString() + " ";
                            }
                            else
                                toReturn +=  printStringDescendants((GNode)o);
                        }
                    }
                    return toReturn;
                }

                /*
                   public void visitWhileStatement(GNode n)
                   {
                   Location loc = n.getLocation();
                   runtime.console().p("Enter scope at ").p(loc.file).p(":")
                   .p(loc.line).p(":").p(loc.column).pln().flush();
                   visit(n);
                   count++;
                   }

                   public void visitDoWhileStatement(GNode n)
                   {
                   Location loc = n.getLocation();
                   runtime.console().p("Enter scope at ").p(loc.file).p(":")
                   .p(loc.line).p(":").p(loc.column).pln().flush();
                   visit(n);
                   count++;
                   }
                   public void visitSwitchStatement(GNode n)
                   {
                   Location loc = n.getLocation();
                   runtime.console().p("Enter scope at ").p(loc.file).p(":")
                   .p(loc.line).p(":").p(loc.column).pln().flush();
                   visit(n);
                   count++;
                   }

                   public void visitTryCatchFinallyStatement(GNode n)
                   {
                   Location loc = n.getLocation();
                   runtime.console().p("Enter scope at ").p(loc.file).p(":")
                   .p(loc.line).p(":").p(loc.column).pln().flush();
                   visit(n);
                   count++;
                   }

                   public void visitSynchronizedStatement(GNode n)
                   {
                   Location loc = n.getLocation();
                   runtime.console().p("Enter scope at ").p(loc.file).p(":")
                   .p(loc.line).p(":").p(loc.column).pln().flush();
                   visit(n);
                   count++;
                   }

*/

                public void visit(Node n)
                {

                    if (n.isEmpty()){
                        //runtime.console().pln(n.toString()).flush();
                        //System.out.println(n.properties());
                    }


                    for (Object o : n){
                        if (o instanceof Node){
                            ((Node)o).setProperty("parent_name", n.getName() );
                            ((Node)o).setProperty("parent", n );
                            dispatch((Node)o);
                        }
                    }
                }


            }.dispatch(node);

        }

    }

    /**
     * Run the scoper with the specified command line arguments.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args)
    {
        new Scoper().run(args);

    }
}



