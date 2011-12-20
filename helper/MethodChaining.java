//fucking
package xtc.oop.helper;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.FileReader;
import java.util.Arrays;

import xtc.parser.ParseException;
import xtc.parser.Result;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;
import xtc.tree.Location;


import java.util.ArrayList;
import xtc.util.SymbolTable;
import java.util.Stack;
//import xtc.oop.helper.Tuple;

/** A visitor that will be able to give you the return types of a call expression
 */

/*Need to make://{{{
 x additive exp
 x basic cast exp
 x bitwise and exp
 x "" or
 x "" negation
 x "" xor
 x Call expression
 x cast expression
 x class lit
 x conditional
 x expresison;
 x equality expr
 x expression list?
 x instance of expression
 x logical and
 x "" negation
 x "" or
 x multiplicative ex
 x new array ex
 x new class ex
 x postfix ex
 x realtional ex
 x selection ex
 x shift expression
 x super expression
 x this expression
 x unary expression
*///}}}

//EvalCall e = new EvalCall()
//Strng retType = e.dispatch(CENodE)
public class MethodChaining extends Visitor{

    Bubble curBub;
    ArrayList<Bubble> bubbleList;
    SymbolTable table;
    SymbolTable dynamicTypeTable;
    Stack<Tuple> stack;


    public MethodChaining(Bubble curBub, ArrayList<Bubble> bubbleList){
        this.curBub = curBub;
        this.bubbleList = bubbleList;
        this.table = curBub.getTable();
	this.dynamicTypeTable = curBub.getDynamicTypeTable();
	stack = new Stack<Tuple>();
    }

    public String visitCallExpression(GNode n){
        String code = "";
        if(n.getProperty("parent0") != null && ((Node)n.getProperty("parent0")).hasName("CallExpression")) {

        }

        if (n.get(0) == null) {
            
            stack.push(new Tuple("null", "")); //something null
        }
        else {

            
            for (int i = 0; i < n.size(); i++) {
                Object temp = n.get(i);
                if (temp != null) {
                    if (temp instanceof Node) {
                        ((Node)temp).setProperty("parent0", n);
                    }
                }
            }

            code = (String)dispatch(n.getNode(0));

        }
	String var = stack.peek().type;
	boolean staticc = false;
        stack.push(new Tuple(n.getString(2), "")); // n.getString(2);
	if (var.equals("constructor")) {
	    staticc = true;
	}
	
        String code2 = (String)dispatch(n.getNode(3)); // code from arguments
	if (staticc) {
	    String pac = curBub.getPackageName().trim().replace(" ", "::");
	    code = pac + "::_" + code + "::" + code2;
	}
	else {
	    code = code + (n.get(0) == null ? "" : "->") + code2;
	}

        if (n.get(0) != null && n.getNode(0).hasName("CallExpression")) {
            code += "; })";
        }
	
        if (stack.empty()) {
            return code;
        }
        else {
            if(((Node)n.getProperty("parent0")).hasName("CallExpression")) {
                String ty = stack.pop().type;
                code = "({ " + ty + " tmp = " + code+"; tmp";
                stack.push(new Tuple(ty,"tmp"));
            }
            else{
                stack.push(new Tuple(stack.pop().type, code));
            }
            return code;
        }
    }



    public String visitArguments(GNode n){
        for (int i = 0; i < n.size(); i++) {
            
            Node temp = n.getNode(i);
            if (temp != null) {
                temp.setProperty("parent0", n);
            }
        }
        String code = "";
        if (n.size() > 0) {
            code += (String)dispatch(n.getNode(0));
        }

        for (int i = 1; i < n.size(); i++) {
            code += ", " +  (String)dispatch(n.getNode(i));
        }
        int size = n.size();
        ArrayList<String> list = new ArrayList<String>();
        
        for (int i = 0; i < size; i++) {
            list.add(0, stack.pop().type);

        }
        
        String m = stack.pop().type;
        
        Tuple t = stack.pop();

        String type = t.type;

        String var = t.code;

        Bubble bub = null;


        if(type.equals("constructor"))
            type = var;


        if (!(type == null)) {
            bub = Bubble.findBubble(bubbleList, type);
        }
        else {
            bub = curBub;
        }


        Mubble theMub = bub.findMethod(bubbleList, m, list);

        stack.push(new Tuple(theMub.getReturnType(), ""));

        if (theMub.isStatic()) {

            String aa = "";
            Node parent0 = (Node)n.getProperty("parent0");
            if (parent0.get(0) == null) {
                aa += "_" + theMub.getClassName() + "::";
            }
            return aa + theMub.getName() + "(" + code + ")";
        }
        if (theMub.isPrivate()) {
            if (type == null) {
                if (n.size() > 0) {

                    return theMub.getName() + "(__this, " + code + ")";
                }
                else {
                    return theMub.getName() + "(__this" + code + ")";
                }
            }
            else {
                if (n.size() > 0) {
                    return theMub.getName() + "(" + var + ", " + code + ")";
                }
                else {
                    return theMub.getName() + "(" + var + "" + code + ")";
                }
            }
        }
        else {
            if (type == null) {
                if (n.size() > 0) {
                    return "__vptr->"+theMub.getName() + "(__this, " + code + ")";
                }
                else {
                    return "__vptr->"+theMub.getName() + "(__this" + code + ")";
                }
            }
            else {
                if (n.size() > 0) {
                    return "__vptr->"+theMub.getName() + "(" + var + ", " + code + ")";
                }
                else {
                    return "__vptr->"+theMub.getName() + "(" + var + "" + code + ")";
                }
            }
        }


        //return theMub.getName() + "(" + code + ")";
    }

    public String visitUnaryExpression(GNode n){
	return "";
    }
    public String visitThisExpression(GNode n){
        //probably dont need this
        return "XXXerror in EvalCALLXXX";
    }
    public String visitSuperExpression(GNode n){
        //probably dont need this
        return "XXXerror in EvalCALLXXX";
    }
    public String visitShiftExpression(GNode n){
	return "";
    }
    public String visitSelectionExpression(GNode n){
	return "";
    }

    public String visitStringLiteral(GNode n){
	stack.push(new Tuple("String", "__rt::literal(" + n.getString(0) + ")"));
	return "__rt::literal(" + n.getString(0) + ")"
;
    }

    public String visitPostfixExpression(GNode n){
    	return "";
    }

    public String visitNewClassExpression(GNode n){
	stack.push(new Tuple(n.getNode(2).getString(0), ""));
	// do we need underscores?
	String s = n.getNode(2).getString(0); //kind of hacky
	if (s.equals("Object") || s.equals("String") || s.equals("Class")) {
	    return "new java::lang::__" + s + "()";
	}
	return "new " + s;
    }

    public String visitNewArrayExpression(GNode n){
       	return "";
    }
    public String visitMultiplicativeExpression(GNode n){
    	return "";
    }
    public String visitLogicalNegationExpression(GNode n){
     	return "";
    }
    public String visitLogicalOrExpression(GNode n){
	return "";

    }
    public String visitLogicalAndExpression(GNode n){
	return "";
    }
    public String visitInstanceOfExpression(GNode n){
	return "";
    }

    public String visitExpressionList(GNode n) {

        return "XXXerror in EvalCALLXXX";
    }

    public String visitRelationalExpression(GNode n)
    {
	return "";
    }
    public String visitEqualityExpression(GNode n){
     	return "";
    }

    public String visitExpression(GNode n){
     	return "";
    }

    public String visitConditionalExpression(GNode n){
        //This cannot happen
     	return "";
    }
    public String visitExpressionStatement(GNode n){
     	return "";
    }

    public String visitClassLiteralExpression(GNode n){
     	return "";
    }

    public String visitCastExpression(GNode n){
        String type = n.getNode(0).getNode(0).getString(0);
        String code = "((" + type + ")" + n.getNode(1).getString(0)+")";
        stack.push(new Tuple(type, code));
     	return code;
    }

    public String visitBitwiseAndExpression(GNode n){
	return "";
    }

    public String visitBitwiseOrExpression(GNode n){
	return "";
    }

    public String visitBitwiseNegationExpression(GNode n){
	return "";
    }

    public String visitBitwiseXorExpression(GNode n){
	return "";
    }

     public static String cppify(String a) {
	if (a.equals("int"))
	    return "int32_t";
	if (a.equals("long"))
	    return "int64_t";
	if (a.equals("short"))
	    return "int16_t";
	if (a.equals("boolean"))
	    return "bool";
	return a; // byte??
    }

    public String visitBasicCastExpression(GNode n){
        String type = cppify((String)dispatch(n.getNode(0)));
        Tuple child = stack.pop();
        child.code = "(" + type + ")";
        stack.push(child);
	    return child.code;
    }

    public String visitAdditiveExpression(GNode n){
        Node parent0 = (Node)n.getProperty("parent0");
        String type = "";
        if (parent0.hasName("CallExpression")) {
            // this would not likely happen
        }
        else if (parent0.hasName("Arguments")){	    
            dispatch(n.getNode(0));
            dispatch(n.getNode(2));            
            Tuple second = stack.pop();
            Tuple first = stack.pop();	    
            if (n.getString(1).equals("+")) {
                if (first.type.equals("String") || second.type.equals("String")) {
                    type = "String";
                }
                else if (first.type.equals(second.type)) {		    
                    type = first.type;
		    if (type.equals("int16_t")) {
			type = "int32_t";
		    }
		}
                else {
                    if (first.type.equals("int64_t") || second.type.equals("int64_t")) {
			type = "int64_t";
		    }
		    else if (first.type.equals("int32_t") || second.type.equals("int32_t")) {
			type = "int32_t";
		    }		    
		    
                }

                stack.push(new Tuple(type, first.code + "+" + second.code));
                return first.code + "+" + second.code;
            }
            else {
                if (first.type.equals(second.type)) {
                    type = first.type;
                }
                else {

                }
                stack.push(new Tuple(type, first.code + "-" + second.code));
                return first.code + "-" + second.code;
            }

        }
	return "";

    }

    public String visitPrimaryIdentifier(GNode n){
	// push the type onto the stack
	// return variable name

	Node parent0 = (Node)n.getProperty("parent0");
	String type = "";
	if (parent0.hasName("CallExpression")) {
	    type = (String)dynamicTypeTable.lookup(n.getString(0));
	    if(type == null)
		type = (String)table.lookup(n.getString(0));
	}
	else if (parent0.hasName("Arguments")) {
	    type = (String)table.lookup(n.getString(0));
	}
	else if (parent0.hasName("AdditiveExpression")) {
	    type = (String)table.lookup(n.getString(0));
	}
	else {
	}

	if (type == null) {
	    type = "constructor";
	}
	stack.push(new Tuple(type, n.getString(0)));
	return n.getString(0);
    }

    public String visitIntegerLiteral(GNode n) {
	String type = "";
	if (n.getString(0).toLowerCase().endsWith("l")) {
	    type = "int64_t";
	}
	else if (n.getString(0).toLowerCase().endsWith("s")) {
	    type = "int16_t";
	}
	else {
	    type = "int32_t";
	}
	stack.push(new Tuple(type, n.getString(0)));
	return n.getString(0);
    }

    public String visitCharacterLiteral(GNode n) {
	stack.push(new Tuple("char", n.getString(0)));
	return n.getString(0);
    }

    public String visitFloatingPointLiteral(GNode n) {
	String type = "";
	if (n.getString(0).toLowerCase().endsWith("d")) {
	    type = "double";
	}
	type = "float";
	stack.push(new Tuple(type, n.getString(0)));
	return n.getString(0);
    }

    public String visitQualifiedIdentifier(GNode n){
    	return "";
    }

    public String visit(Node o)
    {
	return (String)(dispatch((Node)o));
    }

    // shouldnt need these VV
    /*
    public String visitFieldDeclaration(GNode n){//{{{
        visit(n);
    }

    public String visitDimensions(GNode n) {
        visit(n);
    }

    public String visitModifiers(GNode n){
        visit(n);

    }

    public String visitMethodDeclaration(GNode n){

        visit(n);
    }

    public String visitModifier(GNode n){
        visit(n);

    }

    public String visitDeclarators(GNode n) {
        visit(n);
    }

    public String visitDeclarator(GNode n) {
        visit(n);
    }
    */


    /*
    public String visitClassBody(GNode n){
        visit(n);
    }

    public String visitClassDeclaration(GNode n){
        visit(n);
    }

    public String visitFormalParameters(GNode n){
        visit(n);
    }

    public String visitFormalParameter(GNode n) {

        visit(n);
    }
	*/

	/*

    public String visitImportDeclaration(GNode n){
        visit(n);
    }

    public String visitForStatement(GNode n)
    {
        visit(n);
    }

    public String visitBasicForControl(GNode n)
    {
        visit(n);
    }

    public String visitPrimitiveType(GNode n) {
        visit(n);
    }

    public String visitType(GNode n)
    {
        visit(n);
    }//}}}
    */

}






