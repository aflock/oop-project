/*
 * xtc - The eXTensible Compiler
 * Copyright (C) 2011 Robert Grimm
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 2 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301,
 * USA.
 */
package xtc.oop;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import xtc.parser.ParseException;
import xtc.parser.Result;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import xtc.lang.JavaFiveParser;

/**
 * A translator from (a subset of) Java to (a subset of) C++.
 *
 * @author Robert Grimm
 * @version $Revision$
 */
public class hw1 extends xtc.util.Tool {

  /** Create a new translator. */
  public Translator() {
    // Nothing to do.
  }

  public String getName() {
    return "Java to C++ Translator";
  }

  public String getCopy() {
    return "My Group";
  }

  public void init() {
    super.init();

    runtime.
      bool("printJavaAST", "printJavaAST", false, "Print Java AST.").
	//      bool("countMethods", "countMethods", false, "Count all Java methods.").
	bool("printHW1", "printHW1", false, "Print scoping info for Homework 1");
      
  }

  public Node parse(Reader in, File file) throws IOException, ParseException {
    JavaFiveParser parser =
      new JavaFiveParser(in, file.toString(), (int)file.length());
    Result result = parser.pCompilationUnit(0);
    return (Node)parser.value(result);
  }

  public void process(Node node) {
    if (runtime.test("printJavaAST")) {
      runtime.console().format(node).pln().flush();
    }

    if (runtime.test("printHW1")) {
      new Visitor() {

	/*
	 My visiting functions for each of the language constructs that
	 introduce a new scope.
	*/
	public void visitBlock(GNode n) {  //visitBlockDeclaration
	  printScope(n);
        }
	public void visitClassBody(GNode n) {
	  printScope(n);
        }
	public void visitConditionalStatement(GNode n) { 
          printScope(n);
        }
        public void visitConstructorDeclaration(GNode n) {
          printScope(n);
        }
        public void visitDoWhileStatement(GNode n) {
	  printScope(n);
        }
        public void visitForStatement(GNode n) {
          printScope(n);
        }
        public void visitMethodDeclaration(GNode n) {
          printScope(n);
        }
        public void visitSwitchStatement(GNode n) {
          printScope(n);
        }	  
        public void visitTryCatchFinallyStatement(GNode n) {
          printScope(n);
        }
	public void visitWhileStatement(GNode n) {
          printScope(n);
        }


	/*
	 The method printScope prints a message when entering the scope, 
	 visits its children GNodes, and prints again when it's exiting. 
	 I wrote this helper method because the output of each visit is 
	 the same.
	*/

        public void printScope(GNode n) {
	  runtime.console().p("Enter scope at ").p(n.getLocation().toString()).pln().flush();
	  visit(n);
	  runtime.console().p("Exiting scope from ").p(n.getLocation().toString()).pln().flush();
	}
	
  
	//Same visit method that we wrote in class
        public void visit(Node n) {
          /*
          for (Iterator<Object> iter = n.iterator(); iter.hasNext(); ) {
            Object o = iter.next();
            }*/

          for (Object o : n) if (o instanceof Node) dispatch((Node)o);
        }

      }.dispatch(node);
    }
  }

  /**
   * Run the translator with the specified command line arguments.
   *
   * @param args The command line arguments.
   */
  public static void main(String[] args) {
    new Translator().run(args);
  }

}
