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
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;

import xtc.tree.Node;
import xtc.util.Pair;

import xtc.lang.cpp.ContextManager.Context;
import xtc.lang.cpp.CSyntax.*;
import xtc.lang.cpp.ForkMergeParserTables.sym;
import xtc.lang.cpp.ForkMergeParser.ValueStack;


/**
* This class stores the identifiers that have been declared variables or
* typedefs.  In the future, it will also store in which configurations
* the identifiers are bound.
*/
public class CContext {
  /** Output bindings and scope changes. */
  protected static boolean DEBUG = false;

  /** Record statistics. */
  protected static boolean STATISTICS = false;

  protected SymbolTable symtab;
  protected CContext parent;
  protected boolean reentrant;
  
  public CContext(SymbolTable symtab, CContext parent) {
    this.symtab = symtab;
    this.parent = parent;
    this.reentrant = false;
  }
  
  public CContext(CContext scope) {
    this.symtab = scope.symtab.addRef();
    if (scope.parent != null) {
      this.parent = new CContext(scope.parent);
    }
    else {
      this.parent = null;
    }
    this.reentrant = scope.reentrant;
  }
  
  public void bind(String ident, boolean typedef, Context context) {
    CContext scope;
    
    if (DEBUG) {
      System.err.println("bind: " + ident + " " + typedef);
    }

    scope = this;
    while (scope.reentrant) scope = scope.parent;
    
    scope.symtab.add(ident, typedef, context);
  }
  
  public static enum trit {
    TRUE,
    FALSE,
    TRUEFALSE
  }
  
  public trit isType(String ident, Context context) {
    CContext scope;
    
    {
      scope = this;
      
      while (true) {
        while (scope.reentrant) scope = scope.parent;
        
        if ( scope.symtab.map.containsKey(ident)
          && scope.symtab.map.get(ident).t != null
        ) {
          break;
        }
        
        if (null == scope.parent) {
          return trit.FALSE;
        }
        
        scope = scope.parent;
      }
    }
    
    scope = this;
    
    do {  //find the symbol in local scope or parent scope
      
      while (scope.reentrant) scope = scope.parent;
      
      if (scope.symtab.map.containsKey(ident)) {
        Entry e;
        boolean typedef;
        boolean var;
        
        e = scope.symtab.map.get(ident);
        
        typedef = false;
        var = false;

        //TODO make sure logic is correct and optimize branching
        if (null != e.t) {
          Context and;

          and = e.t.and(context);
          if (! and.isFalse()) {
            typedef = true;
          }
          and.delRef();
        }
        
        if (null != e.f) {
          Context and;

          and = e.f.and(context);
          if (! and.isFalse()) {
            var = true;
          }
          and.delRef();
        }
        
        if (typedef && var) {
          if (DEBUG) System.err.println("isType: " + ident + " true/false in "/* + context*/);
          
          if (STATISTICS) Statistics.inc(Statistics.row.TYPEDEF_VAR);
          return trit.TRUEFALSE;
        }
        else if (typedef) {
          if (DEBUG) System.err.println("isType: " + ident + " true in "/* + context*/);
          
          return trit.TRUE;
        }
        else if (var) {
          if (DEBUG) System.err.println("isType: " + ident + " false in "/* + context*/);
          
          return trit.FALSE;
        }
      }
      
      if (null == scope.parent) {
        break;
      }
      
      scope = scope.parent;
    } while (true);
    
    if (DEBUG) System.err.println("isType: " + ident + " false in "/* + context*/);
    
    return trit.FALSE;
  }
  
  public Context typedefContext(String ident, Context context) {
    CContext scope;
    
    scope = this;
    
    do {  //find the symbol in local scope or parent scope

      while (scope.reentrant) scope = scope.parent;

      if (scope.symtab.map.containsKey(ident)) {
        Entry e;
        boolean typedef;
        boolean var;
        Context and;
        
        e = scope.symtab.map.get(ident);

        and = e.t.and(context);
        
        if (! and.isFalse()) {
          return and;
        }
        and.delRef();
      }
      
      if (null == scope.parent) {
        break;
      }
      scope = scope.parent;
    } while (true);
    
    return null;
  }
  
  public CContext enterScope(Context context) {
    CContext scope;
    
    if (DEBUG) System.err.println("enter scope");

    scope = this;
    while (scope.reentrant) {
      scope.symtab.delRef();
      scope.symtab = null;
      scope = scope.parent;
    }
    
    scope = new CContext(new SymbolTable(), new CContext(scope));
    
    return scope;
  }
  
  public CContext exitScope(Context context) {
    CContext scope;
    
    if (DEBUG) System.err.println("exit scope");

    scope = this;
    while (scope.reentrant) {
      scope.symtab.delRef();
      scope.symtab = null;
      scope = scope.parent;
    }
    
    scope.symtab.delRef();
    scope.symtab = null;
    scope = scope.parent;
    
    return scope;
  }
  
  public CContext exitReentrantScope(Context context) {
    CContext scope;
    
    if (DEBUG) System.err.println("exit reentrant scope");
    
    scope = this;
    while (scope.reentrant) {
      scope.symtab.delRef();
      scope.symtab = null;
      scope = scope.parent;
    }

    scope.reentrant = true;

    return scope;
  }
  
  public CContext reenterScope(Context context) {
    if (DEBUG) System.err.println("reenter scope");
    
    if (! reentrant) {
      //if (cfg.errordetail) {
        //System.err.println("NOT REENTRANT");
      //}
    }
    else {
      reentrant = false;
    }
    
    return this;
  }
  
  public CContext killReentrantScope(Context context) {
    CContext scope;
    
    if (DEBUG) System.err.println("kill reentrant scope");
    
    scope = this;
    while (scope.reentrant) {
      scope.symtab.delRef();
      scope.symtab = null;
      scope = scope.parent;
    }

    return scope;
  }
  
  public void merge(CContext scope) {
    if (this.symtab == scope.symtab) {
      return;
    }
    else {
      symtab.addAll(scope.symtab);
      if (null != parent) {
        parent.merge(scope.parent);
      }
    }
  }
  
  public void delRefs() {
    symtab.delRef();
    if (null != parent) {
      parent.delRefs();
    }
  }
  
  public static boolean mergeable(CContext s, CContext t) {
    if ((null == s) && (null == t)) {
      return true;
    } else if ((null == s) || (null == t)) {
      //System.err.println("TABLES NOT MERGEABLE 1");
      return false;
    } else if (s.symtab == t.symtab) {
      return true;
    } else if (s.reentrant != t.reentrant) {
      //System.err.println("TABLES NOT MERGEABLE 2");
      return false;
    } else {
      return mergeable(s.parent, t.parent);
    }
  }
  
  protected static class Entry {
    Context t;
    Context f;
    
    public Entry(Context t, Context f) {
      this.t = t;
      this.f = f;
    }
  }  
  
  protected static class SymbolTable {
    /** The symbol table */
    public HashMap<String, Entry> map;
    
    /** The reference count for cleaning up the table BDDs */
    public int refs;
    
    /** New table */
    public SymbolTable() {
      this.map = new HashMap<String, Entry>();
      this.refs = 1;
    }
    
    public SymbolTable addRef() {
      refs++;
      
      return this;
    }
  
    public void delRef() {
      refs--;
      if (0 == refs) {  //clean up symbol table
        for (String str : this.map.keySet()) {
          Entry e = this.map.get(str);
  
          if (null != e.t) {
            e.t.delRef();
          }
          if (null != e.f) {
            e.f.delRef();
          }
        }
      }
    }
    
    public void add(String ident, boolean typedef, Context context) {
      if (! map.containsKey(ident)) {
        map.put(ident, new Entry(typedef ? context : null, typedef ? null : context));
        context.addRef();
      }
      else {
        Entry e;
        
        e = map.get(ident);
        
        if (typedef) {
          if (null == e.t) {
            e.t = context;
            context.addRef();
          }
          else {
            Context or;
            
            or = e.t.or(context);
            e.t.delRef();
            e.t = or;
          }
        }
        else {
          if (null == e.f) {
            e.f = context;
            context.addRef();
          }
          else {
            Context or;
            
            or = e.f.or(context);
            e.f.delRef();
            e.f = or;
          }
        }
      }
    }

    public void addAll(SymbolTable symtab) {
      for (String str : symtab.map.keySet()) {
        if (! map.containsKey(str)) {
          Entry e = symtab.map.get(str);
          
          map.put(str, new Entry(e.t, e.f));
          
          if (null != e.t) {
            e.t.addRef();
          }
          
          if (null != e.f) {
            e.f.addRef();
          }
        }
        else {
          Entry d = map.get(str);
          Entry e = symtab.map.get(str);
          
          if (null != e.t) {
            if (null == d.t) {
              d.t = e.t;
              e.t.addRef();
            }
            else {
              Context or;
              
              or = d.t.or(e.t);
              d.t.delRef();
              d.t = or;
            }
          }
          
          if (null != e.f) {
            if (null == d.f) {
              d.f = e.f;
              e.f.addRef();
            }
            else {
              Context or;
              
              or = d.f.or(e.f);
              d.f.delRef();
              d.f = or;
            }
          }
        }
      }
    }
  }

  public static class SemanticActions implements CActions.ActionInterface {
    /* Bind variables and typedefs.
     * The c grammar, c.y, contains empty actions that serve as placeholders
     * for adding variable names and typedefs to the symbol table.  Bison
     * reduces these as empty productions named $@xx, where xx is a number.
     * The following code extracts the type specifier and declarator
     * then adds the variable or typedef to the symbol table for the
     * lexer to use for the lexer hack.
     */
    public void BindIdentifier(ForkMergeParser.Subparser subparser) {
      ValueStack value = subparser.v;
      Context context = subparser.getContext();
      CContext scope = subparser.scope;
      boolean typedef;
      Token ident;
      
      //get nodes containing the type and the declarator
      Object a = value.get(3).value;
      Object b = value.get(2).value;
      
      Token t;
      
      /** Assume the typedef keyword is the first token of a typedef */
      while (true) {
        if (a instanceof Node) {
          a = ((Node) a).get(0);
        } else if (a instanceof Pair) {
          a = ((Pair) a).head();
        } else {
          break;
        }
      }
      
      t = (Token) a;

      if (sym.TYPEDEF == t.type()) {
        typedef = true;
        if (STATISTICS) Statistics.inc(Statistics.row.TYPEDEF);
      }
      else {
        typedef = false;
        if (STATISTICS) Statistics.inc(Statistics.row.VAR);
      }
      
      ident = getident(b);
      
      if (typedef && STATISTICS) {
          System.err.println("TYPEDEF: " + ident.file + ":" + ident.line + ":"
                             + ident.charBegin + ":" + ident.charEnd
                             + " " + CLexer.getString(ident));
      }

      scope.bind(CLexer.getString(ident), typedef, context);
    }
    
    public void BindIdentifierInList(ForkMergeParser.Subparser subparser) {
      ValueStack value = subparser.v;
      Context context = subparser.getContext();
      CContext scope = subparser.scope;
      
      boolean typedef;
      Token ident;
      
      //get nodes containing the type and the declarator
      Object a = value.get(5).value;
      Object b = value.get(2).value;
      
      Token t;
      
      while (true) {
        if (a instanceof Node) {
          a = ((Node) a).get(0);
        } else if (a instanceof Pair) {
          a = ((Pair) a).head();
        } else {
          break;
        }
      }
      
      t = (Token) a;

      if (sym.TYPEDEF == t.type()) {
        typedef = true;
        if (STATISTICS) Statistics.inc(Statistics.row.TYPEDEF);
      }
      else {
        typedef = false;
        if (STATISTICS) Statistics.inc(Statistics.row.VAR);
      }
      
      ident = getident(b);

      if (typedef) {
          System.err.println("TYPEDEF: " + ident.file + ":" + ident.line + ":"
                             + ident.charBegin + ":" + ident.charEnd
                             + " " + CLexer.getString(ident));
      }

      scope.bind(CLexer.getString(ident), typedef, context);
    }
    
    public void BindVar(ForkMergeParser.Subparser subparser) {
      ValueStack value = subparser.v;
      Context context = subparser.getContext();
      CContext scope = subparser.scope;
      
      Token ident;
      
      Object b = value.get(2).value;
      
      ident = getident(b);
      
      scope.bind(CLexer.getString(ident), false, context);

      if (STATISTICS) Statistics.inc(Statistics.row.VAR);
    }
    
    public void BindEnum(ForkMergeParser.Subparser subparser) {
      ValueStack value  = subparser.v;
      Context context = subparser.getContext();
      CContext scope = subparser.scope;
      
      String ident;
      
      //must occur after an identifier.or.typedef.name token
      ident = CLexer.getString(((Token) value.get(2).value));
      
      scope.bind(ident, false, context);
      
      if (STATISTICS) Statistics.inc(Statistics.row.VAR);
    }
    
    public void EnterScope(ForkMergeParser.Subparser subparser) {
      Context context = subparser.getContext();
      
      subparser.scope = subparser.scope.enterScope(context);
    }
    
    public void ExitScope(ForkMergeParser.Subparser subparser) {
      Context context = subparser.getContext();
      
      subparser.scope = subparser.scope.exitScope(context);
    }

    public void ExitReentrantScope(ForkMergeParser.Subparser subparser) {
      Context context = subparser.getContext();
      
      subparser.scope = subparser.scope.exitReentrantScope(context);
    }
    
    public void ReenterScope(ForkMergeParser.Subparser subparser) {
      Context context = subparser.getContext();
      
      subparser.scope = subparser.scope.reenterScope(context);
    }
    
    public void KillReentrantScope(ForkMergeParser.Subparser subparser) {
      Context context = subparser.getContext();
      
      subparser.scope = subparser.scope.killReentrantScope(context);
    }
    
    /** Find the identifier in a declarator.  Assume the first identifier in the
      * subtree is the var or typedef name.
      */
    public static Token getident(Object o) {
      if (o instanceof Token) {
        Token token = ((Token) o);
        
        if (sym.IDENTIFIER == token.type()) {
          return token;
        } else {
          return null;
        }
      } else if (o instanceof Pair) {
        Pair<Object> b = (Pair<Object>) o;
        
        while (b != Pair.<Object>empty()) {
          Object child = b.head();
          
          if (null != child) {
            Token ident = getident(child);
            
            if (null != ident) {
              return ident;
            }
          }
          
          b = b.tail();
        }
        
        return null;
      } else if (o instanceof Context) {
        return null;
      } else {
        Node b = (Node) o;
        
        for (int i = 0; i < b.size(); i++) {
          Object child = b.get(i);
          
          if (null != child) {
            Token ident = getident(child);
            
            if (null != ident) {
              return ident;
            }
          }
        }
        
        return null;
      }
    }
  }
}
