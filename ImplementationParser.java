package xtc.oop;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.FileReader;

import java.util.*; //ut oh, is Grimm going to be mad? Yes. TODO import only what we need

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

import xtc.oop.helper.Bubble;   //NEED TO UPDATE TO OUR NEW DATA STRUCTURES
import xtc.oop.helper.Mubble;
import xtc.oop.helper.Pubble;

public class ImplementationParser extends xtc.tree.Visitor //aka IMPL
{

    public static ArrayList<Bubble> bubbleList;
    public static ArrayList<Pubble> pubbleList;
    public static ArrayList<Mubble> mubbleList;
    public static ArrayList<String> parsed; //keeps track of what ASTs have been parsed

    public ImplementationParser(ArrayList<Pubble> packageTree, ArrayList<Mubble> mubbleList, ArrayList<Bubble> bubbleList, ArrayList<String> parsed)
    {
        this.pubbleList = pubbleList;
        this.mubbleList = mubbleList;
        this.bubbleList = bubbleList;
        this.parsed     = parsed;
    }


    public void visit(Node n)//{{{
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
//}}}

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



}
