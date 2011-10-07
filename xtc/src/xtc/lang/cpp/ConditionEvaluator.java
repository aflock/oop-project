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

import java.util.List;

import java.io.StringReader;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Token;
import xtc.tree.Visitor;

import xtc.type.C;
import xtc.type.Type;

import xtc.parser.ParseException;
import xtc.parser.Result;

import xtc.lang.cpp.ContextManager.Context;
import xtc.lang.cpp.MacroTable.Macro;
import xtc.lang.cpp.MacroTable.Entry;

import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;

/** A visitor to partially evaluate conditional expressions that may contain
  * both constants and free variable.  The visitor evaluates constants as
  * much as possible.  However, if a free variable is used in a non-logical
  * operation, a new free variable is made to represent the expression.
  *
  * @author Paul Gazzillo
  */
public class ConditionEvaluator extends Visitor {
  /** Gather statistics. */
  public static boolean STATISTICS = false;

  /** The macro table used for the defined operator.  If null, no resolution
    * is done.
    */
  protected MacroTable macroTable;
  
  /** The context in which the conditional expression is evaluated.  This is
    * use to trim infeasible macro definitions for the defined operator and
    * must be non-null when macroTable is non-null.
    */
  protected ContextManager contextManager;
  
  protected BDDFactory B;
  
  /** The common type operations for C */
  protected final C cops;

  boolean dostring = false;  //when dostring is true, dispatching will return
                             //either a string or integer (not a bdd)
  
  /** Construct a new expression evaluator with a macro table for defined
    * operations.
    */
  public ConditionEvaluator(ContextManager contextManager, MacroTable macroTable) {
    this.contextManager = contextManager;
    this.macroTable = macroTable;
    this.B = contextManager.vars().B;
    this.cops = new C();
  }

  public BDD evaluate(String expression) {
    BDD expressionBDD;
    
    try {
      ConditionParser parser;
      StringReader reader;
      Result result;
      Node tree;
  
      reader = new StringReader(expression);
      parser = new ConditionParser(
        reader,
        "EXPRESSION",
        expression.length()
      );
      
      try {
        result = parser.pConstantExpression(0);
        tree = (Node) parser.value(result);
        
        //TODO if the parser is not at the end of input, expression syntax error
        
        expressionBDD = ensureBDD(dispatch(tree));
        
        result = null;
        tree = null;
        
      }
      catch (Exception e) {
        e.printStackTrace();
        System.err.println("could not parse conditional expression");
        expressionBDD = B.zero();
        tree = null;
      }
      
      reader.close();
      parser = null;
    }
    catch (Exception e) {
      expressionBDD = B.zero();
    }
    
    return expressionBDD;
  }
  
  /** Process an integer constant. */
  public Object visitIntegerConstant(GNode n) {
    xtc.type.Type result;
    
    result = cops.typeInteger(n.getString(0));

    return result.getConstant().bigIntValue().longValue();
  }

  /** Process a character constant. */
  public Object visitCharacterConstant(GNode n) {
    xtc.type.Type result;
    
    result = cops.typeCharacter(n.getString(0));
    
    return result.getConstant().bigIntValue().longValue();
  }

  /** Process primary identifier. */
  public Object visitPrimaryIdentifier(GNode n) {
    if (STATISTICS) {
      Statistics.expressionvar(n.getString(0));
      if (null != macroTable && ! macroTable.table.containsKey(n.getString(0))) {
        Statistics.expressionvarsbeforedef(n.getString(0));
      }
    }
    
    return n.getString(0);
  }

  /** Process a unary minus. */
  public Object visitUnaryMinusExpression(GNode n) {
    Object a;
    
    dostring = true;
    
    a = dispatch(n.getGeneric(0));
    
    dostring = false;
    
    if (a instanceof Long) {
      return - (Long) a;
    }
    else {
      return "- " + parens(a);
    }
  }

  /** Process a unary plus. */
  public Object visitUnaryPlusExpression(GNode n) {
    return dispatch(n.getGeneric(0));
  }

  /** Process a logical negation. */
  public Object visitLogicalNegationExpression(GNode n) {
    if (dostring) {
      Object a = dispatch(n.getGeneric(0));
      
      if (a instanceof Long) {
        return "" + ((((Long) a) == 0) ? 1 : 0);
      }
      else {
        return "! " + parens(a);
      }
    }
    else {
      BDD a = ensureBDD(dispatch(n.getGeneric(0)));
      BDD bdd;
      
      bdd = a.not();
      
      a.free();
      
      return bdd;
    }
  }

  /** Process a bitwise negation. */
  public Object visitBitwiseNegationExpression(GNode n) {
    Object a, result;
    
    dostring = true;
    
    a = dispatch(n.getGeneric(0));
            
    dostring = false;
    
    if (a instanceof Long) {
      result = ~ (Long) a;
    }
    else {
      return "~ " + parens(a);
    }
    
    return result;
  }

  /** Process a multiplicative operation. */
  public Object visitMultiplicativeExpression(GNode n) {
    Object a, b, result;
    String op;
    
    dostring = true;
    
    a = dispatch(n.getGeneric(0));
    b = dispatch(n.getGeneric(2));
    op = n.getString(1);
    
    dostring = false;
    
    if (a instanceof Long && b instanceof Long) {
      if ((op.equals("/") || op.equals("%")) && (Long) b == 0) {
        System.err.println("division by zero in #if");
        result = 0;
      }
      if (op.equals("*")) {
        result = (Long) a * (Long) b;
      }
      else if (op.equals("/")) {
        result = (Long) a / (Long) b;
      }
      else if (op.equals("%")) {
        result = (Long) a % (Long) b;
      }
      else {
        result = "";
      }
    }
    else {
      result = parens(a) + " " + op + " " + parens(b);
    }
    
    return result;
  }

  /** Process an additive operation. */
  public Object visitAdditiveExpression(GNode n) {
    Object a, b, result;
    String op;
    
    dostring = true;
    
    a = dispatch(n.getGeneric(0));
    b = dispatch(n.getGeneric(2));
    op = n.getString(1);
    
    dostring = false;
    
    if (a instanceof Long && b instanceof Long) {
      if (op.equals("+")) {
        result = (Long) a + (Long) b;
      }
      else if (op.equals("-")) {
        result = (Long) a - (Long) b;
      }
      else {
        result = "";
      }
    }
    else {
      result = parens(a) + " " + op + " " + parens(b);
    }
    
    return result;
  }

  /** Process a shift expression. */
  public Object visitShiftExpression(GNode n) {
    Object a, b, result;
    String op;
    
    dostring = true;
    
    a = dispatch(n.getGeneric(0));
    b = dispatch(n.getGeneric(2));
    op = n.getString(1);
    
    dostring = false;
    
    if (a instanceof Long && b instanceof Long) {
      if (op.equals("<<")) {
        result = (Long) a << (Long) b;
      }
      else if (op.equals(">>")) {
        result = (Long) a >> (Long) b;
      }
      else {
        result = "";
      }
    }
    else {
      result = parens(a) + " " + op + " " + parens(b);
    }
    
    return result;
  }

  /** Process a relational expression. */
  public Object visitRelationalExpression(GNode n) {
    Object a, b, result;
    String op;
    
    dostring = true;
    
    a = dispatch(n.getGeneric(0));
    b = dispatch(n.getGeneric(2));
    op = n.getString(1);
    
    dostring = false;
    
    if (a instanceof Long && b instanceof Long) {
      Long x = (Long) a;
      Long y = (Long) b;
      long zero = 0;
      long one = 1;
      
      if (op.equals("<")) {
        result = x < y ? one : zero;
      }
      else if (op.equals("<=")) {
        result = x <= y ? one : zero;
      }
      else if (op.equals(">")) {
        result = x > y ? one : zero;
      }
      else if (op.equals(">=")) {
        result = x >= y ? one : zero;
      }
      else {
        result = "";
      }
    }
    else {
      result = parens(a) + " " + op + " " + parens(b);
    }
    
    return result;
  }

  /** Process a equality expression. */
  public Object visitEqualityExpression(GNode n) {
    Object a, b, result;
    String op;
    boolean mydostring = dostring;
    
    dostring = true;
    
    a = dispatch(n.getGeneric(0));
    b = dispatch(n.getGeneric(2));
    op = n.getString(1);
    
    dostring = false;
    
    if (a instanceof Long && b instanceof Long) {
      if (op.equals("==")) {
        result = (Long) a == (Long) b;
      }
      else if (op.equals("!=")) {
        result = (Long) a != (Long) b;
      }
      else {
        result = "";
      }
    }
    else {
      String sa, sb;
      
      if (a instanceof String) {
        sa = (String) a;
      }
      else if (a instanceof Long) {
        sa = ((Long) a).toString();
      }
      else {
        return null;
      }
      
      if (b instanceof String) {
        sb = (String) b;
      }
      else if (b instanceof Long) {
        sb = ((Long) b).toString();
      }
      else {
        return null;
      }
      
      if (op.equals("==") && sa.equals(sb)) {
        result = mydostring ? "1" : B.one();
      }
      else {
      result = parens(sa) + " " + op + " " + parens(sb);
      }
    }
    
    return result;
  }

  /** Process a bitwise and. */
  public Object visitBitwiseAndExpression(GNode n) {
    Object a, b, result;
    
    dostring = true;
    
    a = dispatch(n.getGeneric(0));
    b = dispatch(n.getGeneric(1));
            
    dostring = false;
    
    if (a instanceof Long && b instanceof Long) {
      result = (Long) a & (Long) b;
    }
    else {
      result = parens(a) + " & " + parens(b);
    }
    
    return result;
  }

  /** Process a bitwise xor. */
  public Object visitBitwiseXorExpression(GNode n) {
    Object a, b, result;
    
    dostring = true;
    
    a = dispatch(n.getGeneric(0));
    b = dispatch(n.getGeneric(1));
            
    dostring = false;
    
    if (a instanceof Long && b instanceof Long) {
      result = (Long) a ^ (Long) b;
    }
    else {
      result = parens(a) + " ^ " + parens(b);
    }
    
    return result;
  }

  /** Process a bitwise or. */
  public Object visitBitwiseOrExpression(GNode n) {
    Object a, b, result;
    
    dostring = true;
    
    a = dispatch(n.getGeneric(0));
    b = dispatch(n.getGeneric(1));
            
    dostring = false;
    
    if (a instanceof Long && b instanceof Long) {
      result = (Long) a | (Long) b;
    }
    else {
      result = parens(a) + " | " + parens(b);
    }
    
    return result;
  }

  /** Process a logical and. */
  public Object visitLogicalAndExpression(GNode n) {
    if (dostring) {
      Object a = dispatch(n.getGeneric(0));
      Object b = dispatch(n.getGeneric(1));
      
      if (a instanceof Long && b instanceof Long) {
        return (Long) a & (Long) b;
      }
      else {
        return parens(a) + " && " + parens(b);
      }
    }
    else {
      BDD a, b, bdd;
      
      a = ensureBDD(dispatch(n.getGeneric(0)));
      b = ensureBDD(dispatch(n.getGeneric(1)));
      
      bdd = a.andWith(b);
      
      return bdd;
    }
  }

  /** Process a logical or. */
  public Object visitLogicalOrExpression(GNode n) {
    if (dostring) {
      Object a = dispatch(n.getGeneric(0));
      Object b = dispatch(n.getGeneric(1));
      
      if (a instanceof Long && b instanceof Long) {
        return (Long) a | (Long) b;
      }
      else {
        return parens(a) + " || " + parens(b);
      }
    }
    else {
      BDD a, b, bdd;
      
      a = ensureBDD(dispatch(n.getGeneric(0)));
      b = ensureBDD(dispatch(n.getGeneric(1)));
      
      bdd = a.orWith(b);
      
      return bdd;
    }
  }
  

  /** Make a new BDD argument, "defined M".  If a macro table was supplied
    * to the evaluator, look for M there and evaluate the operation.
    */
  public Object visitDefinedExpression(GNode n) {
    String parameter;
    
    parameter = n.getGeneric(0).getString(0);
    
    if (STATISTICS) {
      Statistics.expressionvar(parameter);
      if (null != macroTable && ! macroTable.table.containsKey(parameter)) {
        Statistics.expressionvarsbeforedef(parameter);
      }
    }
    
    //evaluate the defined operation, preserving configurations
    if (macroTable != null) {
      List<Entry> definitions = macroTable.get(parameter, contextManager);

      if (definitions != null && definitions.size() > 0) {
        boolean hasDefined, hasUndefined, hasFree;
        
        //three conditions
        //1) defined under all configurations, so output 1 (true)
        //2) undefined under all configurations, so output 0 (false)
        //3) partially defined, so output union of configurations
        
        hasDefined = false;
        hasUndefined = false;
        hasFree = false;
        for (Entry def : definitions) {
          if (def.macro.state == Macro.State.FREE) {
            hasFree = true;
          }
          else if (def.macro.state == Macro.State.DEFINED) {
            hasDefined = true;
          }
          else if (def.macro.state == Macro.State.UNDEFINED) {
            hasUndefined = true;
          }
        }
        
        //fully defined in this context
        if (hasDefined && ! hasUndefined && ! hasFree) {
          return B.one(); //the constant true BDD
        }
        //not defined in this context
        else if (hasUndefined && ! hasDefined && ! hasFree) {
          return B.zero(); //the constant false BDD
        }
        //partially defined in this context
        else {
          BDD defined = B.zero();
          List<Token> tokenlist;
          int c;
          
          for (Entry def : definitions) {
            if (def.macro.state == Macro.State.FREE) {
              BDD newDefined;
              BDD varBDD;
              BDD term;
              
              varBDD = contextManager.vars().getDefVar(parameter);
              
              term = def.context.bdd().and(varBDD);
              newDefined = defined.or(term);
              term.free();
              defined.free();
              varBDD.free();
              defined = newDefined;
            }
            else if (def.macro.state == Macro.State.DEFINED) {
              BDD newDefined;
              
              newDefined = defined.or(def.context.bdd());
              defined.free();
              defined = newDefined;
            }
            else if (def.macro.state == Macro.State.UNDEFINED) {
              //do nothing
            }
          }
          
          return defined;
        } //end partially defined
      } //end has definitions
    } //end has macro table

    /*if (runtime.test("cppmode")) {
      //return false in cpp mode
      return "0";
      }
      else*/ {
      //if there are no macro table entries, just return the operation as is
      return "defined " + parameter;  //return a string
    }
  }
  
  /** Process a conditional expression. */
  public Object visitConditionalExpression(GNode n) {
    if (dostring) {
      Object a = dispatch(n.getGeneric(0));
      Object b = dispatch(n.getGeneric(1));
      Object c = dispatch(n.getGeneric(2));
      
      if (a instanceof Long) {
        return ((Long) a != 0) ? b : c;
      }
      else {
        return parens(a) + " ? " + parens(b) + " : " + parens(c);
      }
    }
    else {
      BDD a = ensureBDD(dispatch(n.getGeneric(0)));
      BDD b = ensureBDD(dispatch(n.getGeneric(1)));
      BDD c = ensureBDD(dispatch(n.getGeneric(2)));
      BDD ab, na, nac, bdd;
      
      //implement with a & b | !a & c
      ab = a.and(b);
      b.free();
      na = a.not();
      a.free();
      nac = na.and(c);
      c.free();
      na.free();
      bdd = ab.or(nac);
      nac.free();
      ab.free();
      
      return bdd;
    }
  }

  /** Ensures that parentheses surround terms to preserve order of operations
    */
  public String parens(Object a) {
    String s;
    
    if (a instanceof String) {
      s = (String) a;
    }
    else if (a instanceof Long) {
      s = ((Long) a).toString();
    }
    else {
      return null;
    }
    
    if (s.indexOf(" ") >= 0) {
      return "(" + s + ")";
    }
    else {
      return s;
    }
  }

  /** Takes whatever the evaluation returns, string, boolean, or integer, and
    * creates a BDD out of it if isn't already.
    */
  public BDD ensureBDD(Object o) {
    if (o instanceof BDD) {
      return (BDD) o;
    }
    else if (o instanceof Long) {
      if ((Long) o == 0) {
        return B.zero();
      }
      else {
        return B.one();
      }
    }
    else if (o instanceof String) {
      String s = parens(o);

      return contextManager.vars().getVar(s);
    }
    else if (o instanceof Boolean) {
      Boolean b = (Boolean) o;
      
      if (b) {
        return B.one();
      }
      else {
        return B.zero();
      }
    }
    else {
      System.err.println("FATAL: ensureBDD, unforeseen type from evaluator");
      System.err.println(o);
      System.err.println(o.getClass());
      System.exit(-1);
      
      return null;
    }
  }
}

