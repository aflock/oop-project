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

    public StructureParser(ArrayList<Pubble> packageTree, ArrayList<Mubble> mubbleList, ArrayList<Bubble> bubbleList, ArrayList<String> parsed)
    {
        this.packageTree = packageTree;
        this.mubbleList = mubbleList;
        this.bubbleList = bubbleList;
        this.parsed = parsed;
    }


    public void visitClassDeclaration(GNode n){
        //n.getString(0) is the Modifiers node
        //n.getString(1) is the name of the class
        bubbleList.add(new Bubble()); //Create New Bubble which whatever constructor we decide

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
