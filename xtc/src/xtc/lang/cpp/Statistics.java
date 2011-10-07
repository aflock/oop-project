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

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Stack;

public class Statistics {
  protected static Map<row, Integer> table = null;
  protected static Map<row, String> details = null;
  protected static Map<Integer, Integer> statefreq = null;
  protected static Map<String, Integer> includes = null;
  protected static Map<String, Integer> guards = null;
  protected static Map<String, Integer> macros = null;
  protected static Set<String> expressionvars = null;
  protected static Set<String> expressionvarsbeforedef = null;
  protected static Set<String> configInText = null;
  protected static Stack<Integer> conditionalNesting = null;
  
  public static void clear() {
    table = null;
    details = null;
    statefreq = null;
    includes = null;
    guards = null;
    macros = null;
    expressionvars = null;
    expressionvarsbeforedef = null;
    configInText = null;
    conditionalNesting = null;
  }

  public static Stack<Integer> conditionalNesting() {
    if (null == conditionalNesting) {
      conditionalNesting = new Stack<Integer>();
    }

    return conditionalNesting;
  }
  
  public static void print() {
    if (null != includes) {
      System.err.println("[BEGIN INCLUDES]");
      for (String file : includes.keySet()) {
        System.err.println(file + "," + includes.get(file) + ","
                           + ((null != guards && guards.containsKey(file))
                              ? guards.get(file) : "0"));
      }
      System.err.println("[END INCLUDES]");
    }
    
    if (null != macros) {
      System.err.println("[BEGIN MACROS]");
      for (String macro : macros.keySet()) {
        System.err.println(macro + "," + macros.get(macro));
      }
      System.err.println("[END MACROS]");
    }

    System.err.println("[BEGIN CONFIG_IN_TEXT]");
    for (String macro : configInText) {
      System.err.println(macro);
    }
    System.err.println("[END CONFIG_IN_TEXT]");
    
    String delim;
      
    delim = "";
    for (row rowname : row.values()) {
      System.err.print(delim);
      System.err.print(rowname);
      delim = ",";
    }
    System.err.println();
      
    delim = "";
    for (row rowname : row.values()) {
      System.err.print(delim);
      System.err.print(table.get(rowname));
      if (details.containsKey(rowname)) {
        System.err.print("(" + details.get(rowname) + ")");
      }
      delim = ",";
    }
    System.err.println();
    
    if (null != statefreq) {
      ArrayList<Integer> sortedList;
      int sum;
      int count;
      
      sum = 0;
      for (Integer i : statefreq.keySet()) {
        sum += statefreq.get(i);
      }
      count = sum;
      
      sortedList = new ArrayList<Integer>(statefreq.keySet());
      java.util.Collections.sort(sortedList);
      System.err.println("states,frequency,cumulative_percentile");
      sum = 0;
      for (Integer i : sortedList) {
        sum += statefreq.get(i);
        System.err.println(i + "," + statefreq.get(i) + ","
                           + (((double) sum) / count));
      }
    }
  }
  
  protected static void printstat(Map<String, Integer> map) {
  }
  
  protected static void sumstats(int[] data) {
    int max, min, mean, median, mode;
    
    max = min = mean = median = mode = 0;
  }
  
  public static void states(int num) {
    if (null == statefreq) {
      statefreq = new HashMap<Integer, Integer>();
    }
    
    if (! statefreq.containsKey(num)) {
      statefreq.put(num, 0);
    }
    
    statefreq.put(num, statefreq.get(num) + 1);
  }
  
  public enum row {
    DEFINE,  /* done */  //times encountering a define directive
    DEFINITION_DUPLICATE,  /* done */  //times encountering a duplicate definition
    UNDEF,  /* done */  //times encountering an undef directive
    REDEFINE,  /* done */  //times an existing definition was removed or context was modified
    MACRO,  /* done */  //number of unique macros
    MAX_DEFINITIONS,  /* done */  //max definitions for a macro

    EXPANSION,  /* done */  //times expanding macro
    MAX_EXPANDED_DEFINITIONS,  /* done */  //max definitions in an expansion
    TRIMMED_DEFINITIONS,  /* done */  //trimmed definitions in an expansion
    EXPANSION_NESTED,  /* done */  //expansion that were part of a macro expansion
    MAX_EXPANSION_NESTED_DEPTH,  /* done */  //maximum depth of nested macro expansion
    OBJECT,  /* done */  //number of object macro definitions
    FUNCTION,  /* done */  //number of function macro definitions
    PRESCAN_DEFINE,  /* done */  //number define directives in function like macro prescanning
    PRESCAN_UNDEF,  /* done */  //number undef directives in function like macro prescanning
    PRESCAN_MACRO,              //number of macros in function like macro prescanning  //number define directives in function like macro prescanning
    PRESCAN_CONDITIONAL,  /* done */  //number conditional directives in function like macro prescanning
    PRESCAN_DIRECTIVE,  /* done */  //number of directives in function like macro prescanning
    HOISTED_FUNCTION,  /* done */  //number of function invocations requiring hoisting
    MAX_HOISTED_FUNCTION,  /* done */  //max functions resulting from hoist
    PASTE,  /* done */  //number of token-pastes
    HOISTED_PASTE,  /* done */  //number of hoisted token-paste arguments
    STRINGIFY,  /* done */  //number of stringifications
    HOISTED_STRINGIFY,  /* done */  //number of hoisted stringification arguments

    CONDITIONAL,  /* done */  //number of full conditionals (containing all branches and #endif)
    IF,  /* done */  //times encountering an if directive
    IFDEF,  /* done */  //times encountering an ifdef directive
    IFNDEF,  /* done */  //times encountering an ifndef directive
    ELIF,  /* done */  //times encountering an elif directive
    ELSE,  /* done */  //times encountering an else directive
    ENDIF,  /* done */  //times encountering an endif directive
    MAX_CONDITIONAL_DEPTH,  /* done */  //max nesting depth of conditionals
    MAX_CONDITIONAL_BREADTH,  /* done */  //max breadth of a conditional
    EXPRESSION,  /* done */ //same as number of IFs plus ELIFs
    EXPRESSION_EXPANSION,  /* done */  //number of expansions in conditional expressions
    HOISTED_EXPRESSION,  /* done */  //number of times a conditional expression needed hoisted (had multiply-defined macros)
    MAX_HOISTED_EXPRESSION,  /* done */  //max resulting expressions from hoisting
    BRANCH_DEFINE,  /* done */  //times seeing a define in a branch
    BRANCH_EXPANSION,  /* done */  //times seeing an expansion in a branch
    BRANCH_CONDITIONAL,  /* done */  //times seeing a conditional in a branch
    BRANCH_INCLUDE,  /* done */  //times seeing an include in a branch
    BRANCH_TYPEDEF,
    TRIMMED_BRANCHES,

    INCLUDE,  /* done */  //times encountering an include directive
    INCLUDE_NEXT,  /* done */  //times encountering an include_next directive
    MAX_INCLUDE_DEPTH,  /* done */  //maximum include depth encountered
    COMPUTED,  /* done */  //times encountering a computed include
    HOISTED_INCLUDE,  /* done */  //times having to hoist a computed include
    MAX_HOISTED_INCLUDE,  /* done */  //max includes after hoisting

    ERROR,  /* done */  //times encountering a error directive
    WARNING,  /* done */  //times encountering a warning directive
    LINE,  /* done */  //times encountering a line directive
    PRAGMA,  /* done */  //times encountering a pragma directive
    
    BDD_VAR,
    
    C_CONSTRUCTS,
    
    TYPEDEF,  /* done */  //typedefs bound
    VAR,  /* done */  //vars bound
    TYPEDEF_VAR,  /* done */  //times encountering implicit conditional
    
    FORK,  /* done */  //times a state is forked
    MERGE,  /* done */  //times a state is merged
    PARSE_ERROR  /* done */  //number of parsing errors
  }
  
  // NESTED_EXPANSION DEPTH
  // HOISTED_FUNCTION BREADTH
  // HOISTED_EXPRESSION BREADTH
  // EXPANSION TRIMMED DEFINITION
  

  
  /** Increment the given data value */
  public static void inc(row name, int amount) {
    if (null == table) {
      table = new HashMap<row, Integer>();
    }
    
    if (! table.containsKey(name)) {
      table.put(name, 0);
    }
    
    table.put(name, table.get(name) + amount);
  }
  
  /** Increment the given data value */
  public static void inc(row name) {
    inc(name, 1);
  }
  
  /** Update a max value */
  public static void max(row name, int x, String detail) {
    if (null == table) {
      table = new HashMap<row, Integer>();
    }
    if (null == details) {
      details = new HashMap<row, String>();
    }
    
    if (! table.containsKey(name)) {
      table.put(name, x);
      details.put(name, detail);
    }
    else {
      int max;
      
      max = table.get(name);
      if (x > max) {
        table.put(name, x);
        details.put(name, detail);
      }
    }
  }

  public static void include(String file) {
    if (null == includes) {
      includes = new HashMap<String, Integer>();
    }
    
    if (! includes.containsKey(file)) {
      includes.put(file, 0);
    }
    
    includes.put(file, includes.get(file) + 1);
  }
  
  public static void guard(String file) {
    if (null == guards) {
      guards = new HashMap<String, Integer>();
    }
    
    if (! guards.containsKey(file)) {
      guards.put(file, 0);
    }
    
    guards.put(file, guards.get(file) + 1);
  }
  
  public static void macro(String name) {
    if (null == macros) {
      macros = new HashMap<String, Integer>();
    }
    
    if (! macros.containsKey(name)) {
      macros.put(name, 0);
    }
    
    macros.put(name, macros.get(name) + 1);
  }
  
  public static void expressionvar(String name) {
    if (null == expressionvars) {
      expressionvars = new HashSet<String>();
    }
    
    expressionvars.add(name);
  }
  
  public static void expressionvarsbeforedef(String name) {
    if (null == expressionvarsbeforedef) {
      expressionvarsbeforedef = new HashSet<String>();
    }
    
    expressionvarsbeforedef.add(name);
  }

    public static void configInText(String name) {
        if (null == configInText) {
            configInText = new HashSet<String>();
        }
        configInText.add(name);
    }
}
