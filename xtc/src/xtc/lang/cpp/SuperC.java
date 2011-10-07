/*
 * xtc - The eXTensible Compiler
 * Copyright (C) 2009-2011 New York University
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301,
 * USA.
 */
package xtc.lang.cpp;

import java.lang.StringBuilder;

import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.io.IOException;

import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

import xtc.lang.cpp.CSyntax.FlaggedSyntax;
import xtc.lang.cpp.CSyntax.FlaggedSyntax.flag;
import xtc.lang.cpp.CSyntax.ConditionalBlock;
import xtc.lang.cpp.CSyntax.Token;
import xtc.lang.cpp.CSyntax.ValuedToken;
import xtc.lang.cpp.CSyntax.Layout;
import xtc.lang.cpp.CSyntax.Directive;
import xtc.lang.cpp.CSyntax.StartConditional;
import xtc.lang.cpp.CSyntax.If;
import xtc.lang.cpp.CSyntax.Elif;
import xtc.lang.cpp.CSyntax.EndConditional;
import xtc.lang.cpp.CSyntax.StartMacro;
import xtc.lang.cpp.CSyntax.ObjectMacro;
import xtc.lang.cpp.CSyntax.FunctionMacro;
import xtc.lang.cpp.CSyntax.EndMacro;
import xtc.lang.cpp.CSyntax.StartInclude;
import xtc.lang.cpp.CSyntax.EndInclude;
import xtc.lang.cpp.CSyntax.StartComputedInclude;
import xtc.lang.cpp.CSyntax.EndComputedInclude;

import xtc.lang.cpp.ContextManager.Context;

import xtc.lang.cpp.CActions.ActionInterface;

import xtc.tree.Node;

import xtc.Constants;

import xtc.lang.CAnalyzer;
import xtc.lang.CPrinter;
import xtc.lang.CReader;
import xtc.lang.CParser;

import xtc.util.Tool;

import xtc.parser.Result;
import xtc.parser.ParseException;

import xtc.lang.cpp.ForkMergeParserTables.sym;

/**
 * The prefactory tool
 *
 * @author Paul Gazzillo
 * @version $Revision: 1.27 $
 */
public class SuperC extends Tool {
  /** The user defined include paths */
  List<String> I;
  
  /** Additional paths for quoted header file names */
  List<String> iquote;
  
  /** Additional paths for system headers */
  List<String> sysdirs;
  
  /** Command-line macros and includes */
  StringReader commandline;

  /** Semantic actions class */
  private final static ActionInterface semanticActions
    = new CContext.SemanticActions();
  
  /** Create a new tool. */
  public SuperC() { /* Nothing to do. */ }

  /**
   * Return the name of this object.
   * 
   * @return The name of this object.
   */
  public String getName() {
    return "SuperC";
  }


  /**
   * Return a copy of the Constants used.
   * 
   * @return The copy of the Constants.
   */
  public String getCopy() {
    return "(C) 2009-2011 Paul Gazzillo, Robert Grimm, and New York\n"
      + "University\nPortions (The C Grammar) Copyright (c) 1989, "
      + "1990 James  A.  Roskind";
  }


  /**
   * Initialize the tool.
   */
  public void init() {
    super.init();
    
    runtime.
      // Regular preprocessor arguments.
      word("I", "I", true,
           "Add a directory to the header file search path.").
      word("isystem", "isystem", true,
           "Add a system directory to the header file search path.").
      word("iquote", "iquote", true,
           "Add a quote directory to the header file search path.").
      bool("nostdinc", "nostdinc", false,
           "Don't use the standard include paths.").
      word("D", "D", true, "Define a macro.").
      word("U", "U", true, "Undefine a macro.  Occurs after all -D arguments "
           + "which is a departure from gnu cpp.").
      word("include", "include", true, "Include a header.").
      
      // Extra preprocessor arguments.
      bool("nobuiltins", "nobuiltins", false,
           "Disable gcc built-in macros.").
      bool("nocommandline", "nocommandline", false,
           "Do not process command-line defines (-D), undefines (-U), or " +
           "includes (-include).  Useful for testing the preprocessor.").
      word("mandatory", "mandatory", false,
           "Include the given header file even if nocommandline is on.").

      // SuperC component selection.
      bool("E", "E", false,
           "Just do configuration-preserving preprocessing.").
      bool("cppmode", "cppmode", false,
           "Preprocess without preserving configurations.").

      // Output and debugging
      bool("printAST", "printAST", false,
           "Print the parsed AST.").
      bool("showCContext", "showCContext", false,
           "Show scope changes and identifier bindings.").
      bool("traceIncludes", "traceInclude", false,
           "Show every header entrance and exit.").
      bool("showActions", "showActions", false,
           "Show all parsing actions.").
      bool("macroTable", "macroTable", false,
           "Show the macro symbol table.").
      /*bool("noConditions", "noConditions", false,
        "Don't show presence conditions in any output.").*/

      // Statistics
      bool("statistics", "statistics", false,
           "Dynamic analysis of the preprocessor and the parser.").

      // Optimizations
      bool("noDedup", "noDedup", false,
           "Turn off macro definition deduplication.  Not recommended " +
           "except for analysis.")

      ;
  }
  
  /**
   * Prepare for file processing.  Build header search paths.
   * Include command-line headers. Process command-line and built-in macros.
   */
  public void prepare() {
    super.prepare();
    
    // Use the Java implementation of JavaBDD
    // Setting it here means the user doesn't have to set it on the commandline
    System.setProperty("bdd", "java");
    
    iquote = new LinkedList<String>();
    I = new LinkedList<String>();
    sysdirs = new LinkedList<String>();

    // The following shows which command-line options add to ""
    // headers and which add to <> headers.  Additionally, only
    // -isystem are considered system headers.  System headers have a
    // special marker to cpp, but SuperC does not need to use this.

    // currentheaderdirectory iquote I    isystem standardsystem
    // ""                     ""     ""   ""     ""
    //                               <>   <>     <>
    //                                    marked system headers 
    
    if (!runtime.test("nostdinc")) {
      for (int i = 0; i < Builtins.sysdirs.length; i++) {
        sysdirs.add(Builtins.sysdirs[i]);
      }
    }
    
    for (Object o : runtime.getList("isystem")) {
      if (o instanceof String) {
        String s;
        
        s = (String) o;
        if (sysdirs.indexOf(s) < 0) {
          sysdirs.add(s);
        }
      }
    }

    for (Object o : runtime.getList("I")) {
      if (o instanceof String) {
        String s;

        s = (String) o;

        // Ignore I if already a system path.
        if (sysdirs.indexOf(s) < 0) {
          I.add(s);
        }
      }
    }
    
    for (Object o : runtime.getList("iquote")) {
      if (o instanceof String) {
        String s;
        
        s = (String) o;
        // cpp permits bracket and quote search chains to have
        // duplicate dirs.
        if (iquote.indexOf(s) < 0) {
          iquote.add(s);
        }
      }
    }

    // Make one large file for command-line/builtin stuff.
    StringBuilder commandlinesb;

    commandlinesb = new StringBuilder();
    
    if (! runtime.test("nobuiltins")) {
      commandlinesb.append(Builtins.builtin);
    }
    
    if (! runtime.test("nocommandline")) {
      for (Object o : runtime.getList("D")) {
        if (o instanceof String) {
          String s, name, definition;
          
          s = (String) o;
          
          // Truncate at first newline according to gcc spec.
          if (s.indexOf("\n") >= 0) {
            s = s.substring(0, s.indexOf("\n"));
          }
          if (s.indexOf("=") >= 0) {
            name = s.substring(0, s.indexOf("="));
            definition = s.substring(s.indexOf("=") + 1);
          }
          else {
            name = s;
            // The default for command-line defined guard macros.
            definition = "1";
          }
          commandlinesb.append("#define " + name + " " + definition + "\n");
        }
      }
      
      for (Object o : runtime.getList("U")) {
        if (o instanceof String) {
          String s, name, definition;
          
          s = (String) o;
          // Truncate at first newline according to gcc spec.
          if (s.indexOf("\n") >= 0) {
            s = s.substring(0, s.indexOf("\n"));
          }
          name = s;
          commandlinesb.append("#undef " + name + "\n");
        }
      }
      
      for (Object o : runtime.getList("include")) {
        if (o instanceof String) {
          String filename;
          
          filename = (String) o;
          commandlinesb.append("#include \"" + filename + "\"\n");
        }
      }
    }
    
    if (null != runtime.getString("mandatory")
        && runtime.getString("mandatory").length() > 0) {
      commandlinesb.append("#include \"" + runtime.getString("mandatory")
                           + "\"\n");
    }
    
    if (commandlinesb.length() > 0) {
      commandline = new StringReader(commandlinesb.toString());
    }
    else {
      commandline = null;
    }
    
    //TODO free up commandline string builder
  }

  public Node parse(Reader in, File file) throws IOException, ParseException {
    HeaderFileManager fileManager;
    MacroTable macroTable;
    ContextManager contextManager;
    Stream preprocessor;
    
    macroTable = new MacroTable();
    contextManager = new ContextManager();

    Statistics.clear();
    
    if (null != commandline) {
      CSyntax syntax;
      
      try {
        commandline.reset();
      }
      catch (Exception e) {
        e.printStackTrace();
      }

      fileManager = new HeaderFileManager(commandline,
                                          new File("<command-line>"),
                                          iquote, I, sysdirs);
      preprocessor = new Preprocessor(fileManager, macroTable, contextManager);
      
      do {
        syntax = preprocessor.scan();
      } while (! CLexer.isType(syntax, sym.EOF));
    }
    
    fileManager = new HeaderFileManager(in, file, iquote, I, sysdirs);
    preprocessor = new Preprocessor(fileManager, macroTable, contextManager);
    
    if (runtime.test("E")) {
      // Preprocess only.
      CSyntax syntax;
      boolean seenNewline = true;
      
      do {
        syntax = preprocessor.scan();
        
        if (! runtime.test("statistics")) {
          if (syntax.isToken() || syntax.isLayout()
              || syntax.isConditional()) {

            // Add a newline before a conditional directive to mimic
            // correct preprocessor directive usage.
            if (syntax.isConditional() && ! seenNewline) {
              System.out.print("\n");
              seenNewline = true;
            }

            if (syntax.isFlaggedSyntax()
                && ((FlaggedSyntax) syntax).hasFlag(flag.PREV_WHITE)) {
              System.out.print(" ");
            }

            System.out.print(syntax);
            
            // Keep track of whether we have seen a newline already.
            if (syntax.isLayout() && ((Layout) syntax).newline
                && ((Layout) syntax).value.endsWith("\n")) {
              seenNewline = true;
            } else if (syntax.isConditional()) {
              System.out.print("\n");
              seenNewline = true;
            } else {
              seenNewline = false;
            }
          }
        }
        
        if (syntax.isStartConditional()) {
          ((StartConditional) syntax).context.delRef();
        }
      } while (! CLexer.isType(syntax, sym.EOF));
      
    } else {
      // Preprocessor and Parse
      ForkMergeParser parser;
      Object translationUnit;
      
      preprocessor = new TokenFilter(preprocessor);

      parser = new ForkMergeParser(preprocessor, contextManager,
                                   semanticActions);

      translationUnit = parser.parse();

      if (runtime.test("printAST")) {
        ForkMergeParser.printAST(translationUnit);
      }
    }
    

    // Print optional statistics and debugging information.

    if (runtime.test("macroTable")) {
      System.err.println("Macro Table");
      System.err.println(macroTable);
    }
    
    if (runtime.test("statistics")) {
      System.err.println("[START EXPRESSION VARS]");
      System.err.println("NAME,CONFIG VAR,BEFORE DEFINED");
      for (String var : Statistics.expressionvars) {
        System.err.print(var);
        System.err.print(",");
        if (! macroTable.table.containsKey(var)) {
          System.err.print("1");
        }
        else {
          System.err.print("0");
        }
        System.err.print(",");
        if (Statistics.expressionvarsbeforedef.contains(var)) {
          System.err.print("1");
        }
        else {
          System.err.print("0");
        }
        System.err.println();
      }
      System.err.println("[END EXPRESSION VARS]");

      System.err.println("[START BDD VARS]");
      for (String var : contextManager.vars().indices) {
        System.err.println(var);
      }
      System.err.println("[END BDD VARS]");

      Statistics.print();
    }
    
    return null;
  }
  
  /**
   * Preprocess the given CPP CST.
   * 
   * @param node The CPP CST
   * @return The preprocessed CPP CST
   */
  public Node preprocess(Node node) {
    return null;
  }

  /**
   * Run the tool with the specified command line arguments.
   *
   * @param args The command line arguments.
   */
  public static void main(String[] args) {
    new SuperC().run(args);
  }
}
