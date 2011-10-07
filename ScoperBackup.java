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
	      bool("enterScopes", "enterScopes", false, "Enter all scopes in file and print line & col.").
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

	    if (runtime.test("enterScopes"))
	    {
	      new Visitor()
	      {
	        private int count = 0;

            /*
	        public void visitCompilationUnit(GNode n)
	        {
	          visit(n);
	          runtime.console().p("Number of scopes: ").p(count).pln().flush();
	        }

	        public void visitClassBody(GNode n)
	        {
	          Location loc = n.getLocation();
	          runtime.console().p("Enter scope at ").p(loc.file).p(":")
	          .p(loc.line).p(":").p(loc.column).pln().flush();
	          visit(n);
	          count++;
	        }

	        public void visitMethodDeclaration(GNode n)
	        {
        	  Location loc = n.getLocation();
	          runtime.console().p("Enter scope at ").p(loc.file).p(":")
	          .p(loc.line).p(":").p(loc.column).pln().flush();
	          visit(n);
	          count++;
	        }

	        public void visitForStatement(GNode n)
	        {
	        	Location loc = n.getLocation();
		        runtime.console().p("Enter scope at ").p(loc.file).p(":")
		          .p(loc.line).p(":").p(loc.column).pln().flush();
	        	visit(n);
	        	count++;
	        }
            */



            public void visitClassBody(GNode n){
                runtime.console().pln("{");
                for(Object o : n){
                    //Some shit
                }
                visit(n);
                runtime.console().pln("};");
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

            public void visitQualifiedIdentifier(GNode n){
                if(n.hasProperty("parent") &&
                   ((Node)(n.getProperty("parent"))).getName().equals("ImportDeclaration")){
                    for(Object o : n){
                        if(o instanceof String){
                          runtime.console().p(o.toString() + ".");
                        }
                    }
                }
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

                /*
	        	Location loc = n.getLocation();
		        runtime.console().p("Enter scope at ").p(loc.file).p(":")
		          .p(loc.line).p(":").p(loc.column).pln().flush();
	        	visit(n);
	        	count++;
                */

                //runtime.console().format(n).pln().flush();
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



