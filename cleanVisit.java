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
                    visit(n);
                }

                public void visitDimensions(GNode n) {
                    visit(n);
                }

                public void visitModifiers(GNode n){
                    visit(n);

                }

                String tempString = "";
                public void visitMethodDeclaration(GNode n){

                    visit(n);
                }

                public void visitModifier(GNode n){
                    visit(n);

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
     * Run the thing with the specified command line arguments.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args)
    {
        new Decl().run(args);

    }
}



