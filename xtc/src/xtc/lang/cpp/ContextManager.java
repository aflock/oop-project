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

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDD;

/** Conditional context manager.  It abstracts away the nitty-gritty
  * of using the BDD objects.
  *
  * @author Paul Gazzillo
  * @version $Revision: 1.34 $
  */
public class ContextManager {
  protected BDDFactory B;
  protected Variables vars;
  
  protected Stack<BDD> stack;

  protected BDD global;
  protected BDD branch;
  protected BDD notBranches;
  
  /** The current context */
  private Context current;
  
  public ContextManager() {
    this.B = BDDFactory.init(5000000, 50000);
    this.vars = new Variables(B);
    this.stack = new Stack<BDD>();
    this.global = B.one();
    this.branch = null;
    this.notBranches = null;
    this.current = null;
  }
  
  public ContextManager(ContextManager contextManager) {
    this.B = contextManager.B;
    this.vars = contextManager.vars;
    
    this.stack = new Stack<BDD>();

    for (BDD bdd : contextManager.stack) {
      if (bdd != null) {
        this.stack.add(bdd.id());
      }
      else {
        this.stack.add(null);
      }
    }

    this.global = contextManager.global.id();
    if (null != contextManager.branch) {
      this.branch = contextManager.branch.id();
    }
    else {
      this.branch = null;
    }
    if (null != contextManager.notBranches) {
      this.notBranches = contextManager.notBranches.id();
    }
    else {
      this.notBranches = null;
    }
    
    this.current = null;
  }
  
  /** Free all BDDs contained in this contextManager */
  public void free() {
    global.free();
    if (null != branch) {
      branch.free();
    }
    if (null != notBranches) {
      notBranches.free();
    }
    for (BDD bdd : stack) {
      if (null != bdd) {
        bdd.free();
      }
    }
    
    if (null != current) {
      current.delRef();
      current = null;
    }
  }
  
  /** Pushes the global context */
  public void push() {
    stack.push(notBranches);
    stack.push(branch);
    stack.push(global);
    
    notBranches = B.one();
    branch = B.zero();
    global = B.zero();
    
    if (null != current) {
      current.delRef();
      current = null;
    }
  }
  
  /** Enters a new local context.  Will destroy passed in bdd. */
  public void enter(BDD bdd) {
    notBranches.andWith(branch.not());
    branch.free();
    branch = bdd;
    
    global.free();
    //       peekGlobal   &&  notBranches   &&     branch
    global = stack.peek().and(notBranches).andWith(branch.id());
    
    if (null != current) {
      current.delRef();
      current = null;
    }
  }
  
  /** Elif needs to first enter else (to evaluate to expression properly),
    * then update the context for the elif expression.
    */
  public void enterElif(BDD bdd) {
    branch = bdd;
    global = stack.peek().and(notBranches).andWith(branch.id());
    
    if (null != current) {
      current.delRef();
      current = null;
    }
  }
  
  /** Else is just a branch whose local context is TRUE. */
  public void enterElse() {
    enter(B.one());
  }
  
  /** Pops the global context */
  public void pop() {
    global.free();
    branch.free();
    notBranches.free();
    
    global = stack.pop();
    branch = stack.pop();
    notBranches = stack.pop();
    
    if (null != current) {
      current.delRef();
      current = null;
    }
  }
  
  /** Returns the parent context */
  public Context parent() {
    return new Context(stack.peek().id());
  }
  
  public boolean isTrue() {
    return global.isOne();
  }
  
  public boolean isFalse() {
    return global.isZero();
  }
  
  public Context reference() {
    if (null == current) {
      current = new Context(global.id());
    }
    current.addRef();
    
    return current;
  }
  
  public boolean is(Context context) {
    return global.equals(context.bdd());
  }
  
  public Variables vars() {
    return vars;
  }
  
  /** A reference-counted context that automatically cleans up BDD when
    * nothing references it anymore.
    */
  public class Context {
    protected BDD bdd;
    protected int refs;
    
    /** Creates a new Context out of the given bdd.  Make sure the bdd
      * is not shared by anyone else.
      */
    public Context(BDD bdd) {
      this.bdd = bdd;
      this.refs = 1;
    }
    
    public Context(boolean value) {
      this.bdd = value ? B.one() : B.zero();
      this.refs = 1;
    }
    
    public boolean isTrue() {
      return bdd.isOne();
    }
    
    public boolean isFalse() {
      return bdd.isZero();
    }
    
    /** Return the negated context. */
    public Context not() {
      return new Context(bdd.not());
    }
    
    /** Return this context and c.  Free any intermediate bdds. */
    public Context and(Context c) {
      return new Context(bdd.and(c.bdd));
    }
    
    /** Return this context and not c.  Free any intermediate bdds. */
    public Context andNot(Context c) {
      Context newContext;
      BDD notBDD;
      
      notBDD = c.bdd.not();
      newContext = new Context(bdd.and(notBDD));
      notBDD.free();
      
      return newContext;
    }
    
    /** Return this context or c.  Free any intermediate bdds. */
    public Context or(Context c) {
      return new Context(bdd.or(c.bdd));
    }
    
    /** Restrict */
    public Context restrict(Context c) {
      return new Context(bdd.restrict(c.bdd()));
    }
    
    /** Compare */
    public boolean is(Context context) {
      return is(context.bdd());
    }
    
    /** Compare */
    public boolean is(BDD bdd) {
      return this.bdd.equals(bdd);
    }
    
    /** */
    public boolean mutex(Context context) {
      Context and;
      
      and = this.and(context);
      
      if (and.isFalse()) {
        and.delRef();
        
        return true;
      }
      else {
        and.delRef();
        
        return false;
      }
    }
    
    public Context addRef() {
      if (refs > 0) {
        refs++;
      }
      
      return this;
    }
    
    public void delRef() {
      if (refs > 0) {
        refs--;
        
        if (0 == refs) {
          bdd.free();
        }
      }
    }
    
    public Context copy() {
      return new Context(bdd().id());
    }
    
    public BDD bdd() {
      return bdd;
    }

    /** Output the context as a valid cpp conditional expression */
    public String toString() {
      StringBuilder sb;
      List allsat;
      boolean firstTerm;
      
      if (bdd.isOne()) {
        return "1";
      }
      else if (bdd.isZero()) {
        return "0";
      }
      
      allsat = (List) bdd.allsat();
      
      sb = new StringBuilder();
      firstTerm = true;
      for (Object o : allsat) {
        byte[] sat;
        boolean first;
        
        if (! firstTerm) {
          sb.append(" || ");
        }
        
        firstTerm = false;

        sat = (byte[]) o;
        first = true;
        for (int i = 0; i < sat.length; i++) {
          if (sat[i] >= 0 && ! first) {
            sb.append(" && ");
          }
          switch (sat[i]) {
            case 0:
              sb.append("!");
            case 1:
              sb.append(vars.getName(i));
              first = false;
              break;
          }
        }
      }
      
      return sb.toString();
    }
  }
  
  /** Maps BDD variables and strings */
  public static class Variables {
    /** The BDD factory */
    protected BDDFactory B;
    
    /** The initial number of variables */
    protected int varNum;
    
    /** The number of variables to add when varNum is topped */
    protected int extVarNum;
    
    /** Hash from variable name to BDD variable index */
    protected Map<String, BDD> variables;
    
    /** Map from BDD variable index to variable name */
    protected List<String> indices;
    
    public Variables(BDDFactory B) {
      this(B, 1023, 512);
    }
    
    public Variables(BDDFactory B, int varNum, int extVarNum) {
      this.B = B;
      this.varNum = varNum;
      this.extVarNum = extVarNum;
  
      this.variables = new HashMap<String, BDD>();
      this.indices = new ArrayList<String>();
      B.setVarNum(this.varNum);
      B.setMinFreeNodes(.40);
      B.setMaxIncrease(500000);
    }
    
    /** Return a copy of the BDD with just the variable in it.  If it's not
      * there, then it creates one.  If we are out of variable numbers, then
      * we increase them.  A copy of the BDD is returned to prevent freeing
      * of variables and their index numbers.
      */
    public BDD getVar(String str) {
      if (variables.containsKey(str)) {
        return variables.get(str).id();
      }
      else {
        int newNum = indices.size();
        BDD newBDD;
        
        if (newNum > varNum - 1) {
          varNum += extVarNum;
          //System.err.println("INCREASE: " + varNum);
          B.extVarNum(extVarNum);
        }
        
        newBDD = B.ithVar(newNum);
        variables.put(str, newBDD);
        indices.add(str);
        
        Statistics.inc(Statistics.row.BDD_VAR);
        
        return newBDD.id();
      }
    }
    
    /** Get the variable name given the bdd variable number */
    public String getName(int i) {
      if (i < indices.size()) {
        return indices.get(i);
      }
      else {
        return null;
      } 
    }
    
    /** Whether the given variable name is part of the variable set */
    public boolean hasVar(String name) {
      return variables.containsKey(name);
    }
    
    /** Construct (defined NAME) given NAME */
    public String defVar(String name) {
      return "(defined " + name + ")";
    }
    
    /** Get the (defined NAME) variable of identifier NAME */
    public BDD getDefVar(String name) {
      return getVar(defVar(name));
    }
    
    /** Whether is has the variable (defined NAME) given NAME */
    public boolean hasDefVar(String name) {
      return variables.containsKey(defVar(name));
    }
  }
}

