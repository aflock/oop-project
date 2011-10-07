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

import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;
import java.util.ListIterator;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;

import xtc.tree.Node;
import xtc.tree.GNode;
import xtc.util.Pair;

import xtc.lang.cpp.CSyntax;
import xtc.lang.cpp.CSyntax.FlaggedSyntax;
import xtc.lang.cpp.CSyntax.FlaggedSyntax.flag;
import xtc.lang.cpp.CSyntax.ConditionalBlock;
import xtc.lang.cpp.CSyntax.Token;
import xtc.lang.cpp.CSyntax.ValuedToken;
import xtc.lang.cpp.CSyntax.Keyword;
import xtc.lang.cpp.CSyntax.Identifier;
import xtc.lang.cpp.CSyntax.Literal;
import xtc.lang.cpp.CSyntax.Punctuation;
import xtc.lang.cpp.CSyntax.Layout;
import xtc.lang.cpp.CSyntax.Directive;
import xtc.lang.cpp.CSyntax.Conditional;
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

import xtc.lang.cpp.ContextManager.Context;
import xtc.lang.cpp.ForkMergeParserTables;
import xtc.lang.cpp.ForkMergeParserTables.sym;
import xtc.lang.cpp.CContext.trit;
import xtc.lang.cpp.CContext.SymbolTable;

import xtc.lang.cpp.CActions.ActionInterface;
import xtc.lang.cpp.CActions.NodeType;


import net.sf.javabdd.*;

/**
 * This class expands macros and processes header files
 *
 * @author Paul Gazzillo
 * @version $Revision: 1.132 $
 */
public class ForkMergeParser {
  /** Show all parsing actions. */
  public static boolean SHOW_ACTIONS = false;

  /** Show error details. */
  public static boolean SHOW_ERRORS = false;

  /** Gather statistics. */
  public static boolean STATISTICS = false;

  /** The stream from which the parser gets syntactic units */
  protected Stream stream;
  
  /** The context manager. */
  protected ContextManager contextManager;
  
  /** The implementation of semantic actions for action nodes. */
  protected ActionInterface semanticActions;

  /** The list of subparsers. */
  protected Pair<Subparser> P;
  
  /** The union of all invalid contexts. */
  //protected Context invalidContext;
  
  /** Print diagnostics every DELAY iterations. */
  //static int DELAY = cfg.nodelay ? 1 : 200;
  
  /** The iterator for printing diagnostics. */
  static int delay = 0;
  
  /** The name of the conditional node. */
  static final String CONDITIONAL_NODE = "$conditional";
  
  /** Constants. */
  static enum ParsingAction { SHIFT, REDUCE, ACCEPT, ERROR }

  /** Array of error messages. */
  final static String[] ERRMSG = {
    "no default action",
    "invalid table entry",
    "error directive"};

  /** Error code for no default action.  */
  final static int NODEFAULT = 0;
  
  /** Error code for invalid table entry. */
  final static int INVALID = 1;
  
  /** Error code for seen an error directive. */
  final static int ERRDIRECTIVE = 2;
  
  /** */
  final static int STARTSTATE = 0;
  
  /** */
  Object topvalue
    = GNode.create(ForkMergeParserTables.yytname
                   .table[ForkMergeParserTables
                          .yystos.table[STARTSTATE]],
                   false);

  /** The list of accepted parsers. */
  Pair<Subparser> accepted = new Pair<Subparser>(null);
  
  /** A pointer to the end of the list of accepted parsers. */
  Pair<Subparser> currentAccepted = accepted;
  
  /** Create a new parser. */
  public ForkMergeParser(Stream stream, ContextManager contextManager,
                         ActionInterface semanticActions) {
    this.stream = stream;
    this.contextManager = contextManager;
    this.semanticActions = semanticActions;
    this.P = new Pair<Subparser>(null);
  }
  
  /** Parse the syntax stream. */
  public Object parse() {
    OrderedSyntax topConditional = new OrderedSyntax(stream);
    OrderedSyntax a = moveToNext(topConditional);
    CContext topscope = new CContext(new SymbolTable(), null);
    Pair<Subparser> Pfirst = new Pair<Subparser>(null);
    
    //invalidContext = contextManager.new Context(false);

    Pfirst
      .setTail(new Pair<Subparser>(new Subparser(new Lookahead(a,
                                                               contextManager
                                                               .new
                                                               Context(true),
                                                               null,
                                                               -1,
                                                               topConditional,
                                                               trit.FALSE),
                                                 new StateStack(STARTSTATE,
                                                                null),
                                                 new ValueStack(topvalue,
                                                                true,
                                                                false,
                                                                null),
                                                 topscope)));
    
    while (true) {
      // P is not forkable and not mergeable
      
      //printDiagnostics(Pfirst);
      //printDiagnostics(P);
      
      if (STATISTICS) {  //collect state-space data
        collectStateSpaceData(Pfirst);
      }

      switch (a.syntax.syntaxKind()) {
      case TOKEN:
        // At a regular token
        reduceAll(Pfirst);
        if (Pfirst.tail() == Pair.<Subparser>empty()) {
          //all parser's accepted or had an error
          getPfirst(Pfirst);
          if (Pfirst.tail() != Pair.<Subparser>empty()) {
            a = Pfirst.tail().head().a.t;
          }
        } else {
          merge(Pfirst);
          shiftAll(Pfirst);
          a = moveToNext(a);
          setLookahead(Pfirst, a);
        }
        
        break;
        
        // At a conditional
      case IF:
        getLookaheads(Pfirst);
        forkAndReduce(Pfirst);
        if (Pfirst.tail() == Pair.<Subparser>empty()) {
          //all parser's accepted or had an error
          getPfirst(Pfirst);
          if (Pfirst.tail() != Pair.<Subparser>empty()) {
            a = Pfirst.tail().head().a.t;
          }
        } else {
          merge(Pfirst);
          getLookaheads(Pfirst);  //merged subparsers lose their lookaheads
          forkByConditional(Pfirst);
          forkByBranch(Pfirst);
          a = Pfirst.tail().head().a.t;
        }
        
        break;
      
        // At the end of a branch
      case ELIF:
        // Fall through
      case END_CONDITIONAL:
        //move subparsers in Pfirst to next thing after (all) end conditionals
        //if Pfirst no longer correct, add back to P and get Pfirst, try merge
        //otherwise, keep going
        if (Pfirst.tail().head().a.t.syntax.isElif()) {
          // We are at end of branch, so try to merge
          OrderedSyntax elif = Pfirst.tail().head().a.t;
          OrderedSyntax nextAfter = elif;
            
          while (nextAfter.syntax.isEndConditional() ||
                 nextAfter.syntax.isElif()) {
            nextAfter = moveToEndif(nextAfter);
            nextAfter = moveToNext(nextAfter);
          }

          setLookahead(Pfirst, nextAfter);
          switch (nextAfter.syntax.syntaxKind()) {
          case TOKEN:
            reduceAll(Pfirst);
            break;

          case IF:
            getLookaheads(Pfirst);
            forkAndReduce(Pfirst);
            break;
          }
          setLookahead(Pfirst, elif);
        }

        a = moveToEndif(a);
        setLookahead(Pfirst, a);
        movePfirstToP(Pfirst);
        getPfirst(Pfirst);
        if (Pfirst.tail().head().a.t.syntax.isEndConditional()) {
          // We are at end of branch, so try to merge
          OrderedSyntax nextAfter = Pfirst.tail().head().a.t;
          OrderedSyntax firstAfter = moveToNext(Pfirst.tail().head().a.t);
            
          while (nextAfter.syntax.isEndConditional() ||
                 nextAfter.syntax.isElif()) {
            nextAfter = moveToEndif(nextAfter);
            nextAfter = moveToNext(nextAfter);
          }

          setLookahead(Pfirst, nextAfter);
          switch (nextAfter.syntax.syntaxKind()) {
          case TOKEN:
            reduceAll(Pfirst);
            if (Pfirst.tail() == Pair.<Subparser>empty()) {
              //all parser's accepted or had an error
              getPfirst(Pfirst);
            } else {
              setLookahead(Pfirst, a);
              merge(Pfirst);
              setLookahead(Pfirst, firstAfter);
              movePfirstToP(Pfirst);
              getPfirst(Pfirst);
            }
            break;

          case IF:
            getLookaheads(Pfirst);
            forkAndReduce(Pfirst);
            if (Pfirst.tail() == Pair.<Subparser>empty()) {
              //all parser's accepted or had an error
              getPfirst(Pfirst);
            } else {
              setLookahead(Pfirst, a);
              merge(Pfirst);
              setLookahead(Pfirst, firstAfter);
              movePfirstToP(Pfirst);
              getPfirst(Pfirst);
            }
            break;
          }
        }

        if (Pfirst.tail() != Pair.<Subparser>empty()) {
          a = Pfirst.tail().head().a.t;
        }

        //old merge style
        /*
        while (a.syntax.isEndConditional() || a.syntax.isElif()) {
          a = moveToEndif(a);
          a = moveToNext(a);
        }
          
        setLookahead(Pfirst, a);
        movePfirstToP(Pfirst);
        getPfirst(Pfirst);
        a = Pfirst.tail().head().a.t;
        */
        
        break;
      }

      //printDiagnostics(Pfirst);
      //printDiagnostics(P);
      
      if (Pfirst.tail() == Pair.<Subparser>empty()) break;
    }
    
    System.err.println("done");
    /*if (invalidContext.isTrue()) {
      System.err.println("warning: no valid configurations found");
      }*/
    
    GNode translationUnit = null;

    if (accepted.tail() != Pair.<Subparser>empty()) {
      Pair<Object> values = Pair.<Object>empty();

      merge(accepted);

      currentAccepted = accepted;
      while (currentAccepted.tail() != Pair.<Subparser>empty()) {
        currentAccepted = currentAccepted.tail();
        Subparser p = currentAccepted.head();
        values = new Pair<Object>(new BranchNode(p.v.value,
                                                 p.getContext().addRef()));
        p.clearContext();
      }

      values = values.reverse();
      translationUnit = GNode.create("TranslationUnit",
                                     GNode.createFromPair("StaticChoice",
                                                          values.head(),
                                                          values.tail()));
    } else {
      System.err.println("warning: no accepted subparsers");
    }
    
    //invalidContext.delRef();
    //topscope.delRefs();

    return translationUnit;
  }
  
  /** Perform a shift for one subparser. */
  private static void shift(Subparser subparser) {
    Lookahead x = subparser.a;
    Token token = (Token) x.t.syntax;
    int yystate = x.actiondata;
    Node node;
    OrderedSyntax next;

    if (SHOW_ACTIONS) {
      System.err.println("shifting " + token.type() + "("
                         + CLexer.getString(token) + ")");
    }
    
    if (NodeType.LAYOUT
        == CActions.nodeType[ForkMergeParserTables.translate(token.type())]) {
      // roundtrip, don't null it
      token = null;
    }
    subparser.s = new StateStack(yystate, subparser.s);
    subparser.v = new ValueStack(token, false, false, subparser.v);
  }
  
  /** Perform a reduce for one subparser. */
  private void reduce(Subparser subparser) {
    StateStack topState = subparser.s;
    ValueStack topValue = subparser.v;
    int production = subparser.a.actiondata;
    int yylen = ForkMergeParserTables.yyr2.table[production];
    int symbol = ForkMergeParserTables.yyr1.table[production];
    String nodeName = ForkMergeParserTables.yytname.table[symbol];;
    Object reduced = null;
    int yystate;
    CActions.NodeType nodeType = CActions.nodeType[symbol];
    
    if (SHOW_ACTIONS) {
      System.err.println("reducing " + nodeName + " action: " + nodeType);
    }

    /*if (STATISTICS && cfg.csus) {
      if (nodeName.equals("Statement")) {
      Statistics.inc(Statistics.row.STATEMENT);
      } else if (nodeName.equals("Declaration")) {
      Statistics.inc(Statistics.row.DECLARATION);
      } else if (nodeName.equals("FunctionDefinition")) {
      Statistics.inc(Statistics.row.FUNCTION_DEFINITION);
      }
      }*/
    
    switch (nodeType) {
    case ACTION:
      reduced = null;
      assert 0 == yylen;  //action nodes should be empty
      break;
    
    case PASS_THROUGH:
      if (0 == yylen) {
        reduced = null;
      } else if (1 == yylen && ! topValue.duplicateList) {
        reduced = topValue.value;
        topState = topState.next;
        topValue = topValue.next;
      } else {
        Pair<Object> values = Pair.<Object>empty();
    
        for (int i = 0; i < yylen; i++) {
          if (null != topValue.value) {
            while (true) {
              values = new Pair<Object>(topValue.value, values);
              if (! topValue.duplicateList) {
                break;
              }
              topValue = topValue.next;
            }
          }
          topValue = topValue.next;
          topState = topState.next;
        }
        
        if (Pair.<Object>empty() == values) {
          reduced = null;
        } else {
          reduced = GNode.createFromPair(nodeName, values.head(),
                                         values.tail());
        }
      }
      break;
      
    case LAYOUT:
      for (int i = 0; i < yylen; i++) {
        topValue = topValue.next;
        topState = topState.next;
      }
        
      reduced = null;
      break;
    
    case LIST:
      if (0 == yylen) {
        reduced = new ListNode(nodeName);
      } else {
        Pair<Object> values = Pair.<Object>empty();
      
        for (int i = 0; i < yylen; i++) {
          if (null != topValue.value) {
            while (true) {
              values = new Pair<Object>(topValue.value, values);
              if (! topValue.duplicateList) {
                break;
              }
              topValue = topValue.next;
            }
          }
          topValue = topValue.next;
          topState = topState.next;
        }
          
        if (Pair.<Object>empty() != values) {
          if (values.head() instanceof ListNode) {
            reduced = values.head();
            if (Pair.<Object>empty() != values.tail()) {
              BranchNode branchNode
                = new BranchNode(GNode.createFromPair(nodeName,
                                                      values.tail().head(),
                                                      values.tail().tail()),
                                 subparser.getContext().addRef());
              ((ListNode) reduced).add(GNode.create("StaticChoice",
                                                    branchNode));
            }
          } else {
            reduced = new ListNode(nodeName, values);
          }
        } else {
          reduced = new ListNode(nodeName);
        }
      }
        
      break;
    
    case DEFAULT:
      if (0 == yylen) {
        reduced = null;
      } else {
        Pair<Object> values = Pair.<Object>empty();
    
        for (int i = 0; i < yylen; i++) {
          if (null != topValue.value) {
            while (true) {
              values = new Pair<Object>(topValue.value, values);
              if (! topValue.duplicateList) {
                break;
              }
              topValue = topValue.next;
            }
          }
          //if (top.value instanceof Pair) top.value = new Pair<Object>(null);
          topValue = topValue.next;
          topState = topState.next;
        }
        
        if (Pair.<Object>empty() == values) {
          reduced = GNode.create(nodeName, true);
        } else {
          reduced = GNode.createFromPair(nodeName, values.head(),
                                         values.tail());
        }
      }
      break;
      
    default:
      assert true;
    }
    
    yystate = ForkMergeParserTables.yypgoto.table[symbol - ForkMergeParserTables.YYNTOKENS] + topState.state;
    if (0 <= yystate && yystate <= ForkMergeParserTables.YYLAST
        && ForkMergeParserTables.yycheck.table[yystate] == topState.state) {
      yystate = ForkMergeParserTables.yytable.table[yystate];
    }
    else {
      yystate = ForkMergeParserTables.yydefgoto.table[symbol - ForkMergeParserTables.YYNTOKENS];
    }
    
    subparser.s = new StateStack(yystate, topState);
    boolean isComplete = CActions.isComplete(symbol);
    boolean isCompleteList = isComplete
      && NodeType.LIST == CActions.nodeType[symbol];
    subparser.v = new ValueStack(reduced, isComplete, isCompleteList,
                                 topValue);
    
    CActions.dispatchAction(subparser, symbol, semanticActions);
  }
  
  private void accept(Subparser p) {
    System.err.println("accept");
    currentAccepted.setTail(new Pair<Subparser>(p));
    currentAccepted = currentAccepted.tail();
  }
  
  private static void error(Subparser p) {
  }
  
  /**
   * Perform all reductions for the first firstSize subparsers.  After
   * finishing this function, all firstSize subparsers will be
   * ready to shift.  If some subparser(s) finds an accept or error, it will
   * be removed and the new firstSize will be returned.
   * 
   */
  private void reduceAll(Pair<Subparser> Pfirst) {
    Pair<Subparser> lastP = null;
    Pair<Subparser> currentP = Pfirst;
    
    while (currentP.tail() != Pair.<Subparser>empty()) {
      lastP = currentP;
      currentP = currentP.tail();
      Subparser p = currentP.head();
      
      while (true) {
        if (p.a.isTypedef == trit.TRUEFALSE) {
          Lookahead a1 = p.L.tail().head();
          Lookahead a2 = p.L.tail().tail().head();
          getAction(a1, p.s, p.scope);
          getAction(a2, p.s, p.scope);
          if (a1.action == a2.action && a1.actiondata == a2.actiondata) {
            p.a.action = a1.action;
            p.a.actiondata = a1.actiondata;
          } else {
            //fork implicit conditional
            Subparser forked = new Subparser(a2, p.s, p.v,
                                             new CContext(p.scope));
            p.a.c.delRef();
            p.clearContext();  // fork invalidates subparser's condition
            p.L = null;
            p.a = a1;
            currentP.setTail(new Pair<Subparser>(forked, currentP.tail()));
            if (STATISTICS) {
              System.err.println("TYPEDEF_AMBIGUITY: "
                                 + p.a.t.syntax.file + ":" + p.a.t.syntax.line + ":"
                                 + p.a.t.syntax.charBegin + ":"
                                 + p.a.t.syntax.charEnd);
            }
          }
        } else {
          getAction(p.a, p.s, p.scope);
        }
        
        if (ParsingAction.REDUCE != p.a.action) {
          break;
        } else {
          reduce(p);
        }
      }
      
      switch (p.a.action) {
      case ACCEPT:
        System.err.println("accept");

        currentAccepted.setTail(new Pair<Subparser>(p));
        currentAccepted = currentAccepted.tail();

        //remove the parser
        lastP.setTail(currentP.tail());
        currentP = lastP;
        
        break;
      case ERROR:
        if (SHOW_ACTIONS) System.err.println("error");

        //remove the parser and free memory
        lastP.setTail(currentP.tail());
        currentP = lastP;
        p.free();

        break;
      }
    }
  }
  
  /**
   * Set the lookaheads for the subparsers if they dont already have them.
   * All the subparsers should be on an #if.
   */
  private void getLookaheads(Pair<Subparser> Pfirst) {
    Pair<Lookahead> L = null;
    Pair<Subparser> currentP = Pfirst;
    
    while (currentP.tail() != Pair.<Subparser>empty()) {
      currentP = currentP.tail();
      Subparser p = currentP.head();
      
      if (null == p.L) {
        if (null == L) {
          L = lookaheads(p.a);
        }
        
        p.L = new Pair<Lookahead>(null);
        
        Pair<Lookahead> fromL = L;
        Pair<Lookahead> toL = p.L;
        
        while (fromL.tail() != Pair.<Lookahead>empty()) {
          fromL = fromL.tail();
          Lookahead x = fromL.head();
          Context c = p.getContext().and(x.c);
          
          if (! c.isFalse()) {
            trit isTypedef;
            
            if (CLexer.isType(x.t.syntax, sym.IDENTIFIER)) {
              isTypedef = p.scope.isType(CLexer.getString(x.t.syntax), c);
            } else {
              isTypedef = trit.FALSE;
            }
            
            if (isTypedef == trit.TRUEFALSE) {
              //fork implicit conditional
              Context typedefContext
                = p.scope.typedefContext(CLexer.getString(x.t.syntax), c);
              Context varContext = p.a.c.andNot(typedefContext);
              toL.setTail(new Pair<Lookahead>(new Lookahead(x.t,
                                                            typedefContext,
                                                            null, -1,
                                                            x.conditional,
                                                            trit.TRUE)));
              toL = toL.tail();
              toL.setTail(new Pair<Lookahead>(new Lookahead(x.t,
                                                            varContext,
                                                            null,
                                                            -1, x.conditional,
                                                            trit.FALSE)));
              toL = toL.tail();
            } else {
              toL.setTail(new Pair<Lookahead>(new Lookahead(
                                                            x.t, c, null, -1,
                                                            x.conditional,
                                                            isTypedef)));
              toL = toL.tail();
            }
          } else {
            c.delRef();
          }
        }
        
        p.clearContext();
      }
    }
    
    //free memory
    if (null != L) {
      Pair<Lookahead> fromL = L;
      
      while (fromL.tail() != Pair.<Lookahead>empty()) {
        fromL = fromL.tail();
        fromL.head().c.delRef();
      }
    }
  }
  
  /**
   * Fork and reduce subparsers so that each new subparser's lookaheads are
   * all shifts.  Performs shared reductions too.
   * Lookaheads with an accept or error action
   * are removed with a message.
   * Maintains ordering of P.
   */
  private void forkAndReduce(Pair<Subparser> Pfirst) {
    while (true) {
      forkByAction(Pfirst);
      if (! sharedReduce(Pfirst)) break;
    }
  }
  
  /** Fork subparsers into reduce and shift */
  private void forkByAction(Pair<Subparser> Pfirst) {
    Pair<Subparser> lastP = null;
    Pair<Subparser> currentP = Pfirst;
    
    while (currentP.tail() != Pair.<Subparser>empty()) {
      lastP = currentP;
      currentP = currentP.tail();
      Subparser p = currentP.head();
      
      if (p.L.tail() == Pair.<Lookahead>empty()) {
        //all lookaheads were accepts or errors, remove subparser
        lastP.setTail(currentP.tail());
        currentP = lastP;
      } else {
        ParsingAction firstAction = p.L.tail().head().action;
        
        if (null == firstAction) {
          Subparser reduce = null;
          Subparser shift = null;
          Pair<Lookahead> lastL = null;
          Pair<Lookahead> L = p.L;
          Pair<Lookahead> forkL = null;
          
          while (L.tail() != Pair.<Lookahead>empty()) {
            lastL = L;
            L = L.tail();
            
            Lookahead x = L.head();
            
            //TODO implicit conditional
            
            getAction(x, p.s, p.scope);
            
            switch (x.action) {
            case ACCEPT:
              System.err.println("accept");
  
              lastL.setTail(L.tail());  //remove lookahead
              L = lastL;
              //and add it to accepted parser
              currentAccepted.setTail(new Pair<Subparser>(new Subparser(x, p.s,
                                                                        p.v,
                                                                        new CContext(p.scope))));
              currentAccepted = currentAccepted.tail();
              
              break;
              
            case ERROR:
              lastL.setTail(L.tail());  //remove lookahead
              L = lastL;
              
              if (SHOW_ACTIONS) System.err.println("error");
              
              /*Context newInvalid = invalidContext.or(x.c);
                invalidContext.delRef();
                invalidContext = newInvalid;*/
              
              if (SHOW_ERRORS) {
                System.err.println(ERRMSG[x.actiondata]);
                System.err.print(p);
                System.err.println(x.t.syntax
                                   + ":" + x.t.syntax.file
                                   + ":" + x.t.syntax.line + "\n");
              }
              /*if (cfg.notrimerrors) {
                for (Lookahead lookahead : point.L.values()) {
                if (ERROR != lookahead.action) {
                Context newC = lookahead.c.or(x.c);
                lookahead.c.delRef();
                lookahead.c = newC;
                }
                }
                }*/
              
              x.c.delRef();
    
              if (STATISTICS) Statistics.inc(Statistics.row.PARSE_ERROR);
              break;
    
            case REDUCE:
              if (null == firstAction) {
                reduce = p;
                firstAction = x.action;
                
              } else if (null == reduce) {
                reduce = new Subparser(new Lookahead(p.a.t, null, null, -1,
                                                     p.a.conditional, null),
                                       p.s,
                                       p.v,
                                       new CContext(p.scope));
                
                //add forked subparser to list of subparsers (before current)
                lastP.setTail(new Pair<Subparser>(reduce, lastP.tail()));
                lastP = lastP.tail();
                
                reduce.L = new Pair<Lookahead>(null);
                forkL = reduce.L;
    
                p.clearContext();  //fork invalidates context
              }
    
              if (reduce != p) {
                lastL.setTail(L.tail());  //move lookahead to reduce
                L = lastL;
                forkL.setTail(new Pair<Lookahead>(x));
                forkL = forkL.tail();
              }
              
              break;
    
            case SHIFT:
              if (null == firstAction) {
                shift = p;
                firstAction = x.action;
                
              } else if (null == shift) {
                shift = new Subparser(
                                      new Lookahead(p.a.t, null, null, -1,
                                                    p.a.conditional, null),
                                      p.s,
                                      p.v,
                                      new CContext(p.scope));
    
                //add forked subparser to list of subparsers (before current)
                lastP.setTail(new Pair<Subparser>(shift, lastP.tail()));
                lastP = lastP.tail();
                
                shift.L = new Pair<Lookahead>(null);
                forkL = shift.L;
                
                p.clearContext();  //fork invalidates context
              }
              
              if (shift != p) {
                lastL.setTail(L.tail());  //move lookahead to shiftPoint
                L = lastL;
                forkL.setTail(new Pair<Lookahead>(x));
                forkL = forkL.tail();
              }
              
              break;
            }
          }
          
          if (p.L.tail() == Pair.<Lookahead>empty()) {
            //all lookaheads were accepts or errors, remove subparser
            lastP.setTail(currentP.tail());
            currentP = lastP;
          }
        }
      }
    }
  }
  
  /**
   * Reduces all subparsers that are ready to reduce.  Returns true
   * only if some reduce action was performed.
   */
  private boolean sharedReduce(Pair<Subparser> Pfirst) {
    Pair<Subparser> lastP = null;
    Pair<Subparser> currentP = Pfirst;
    boolean reduced = false;
    
    while (currentP.tail() != Pair.<Subparser>empty()) {
      lastP = currentP;
      currentP = currentP.tail();
      Subparser p = currentP.head();
      ParsingAction firstAction = p.L.tail().head().action;
      
      switch (firstAction) {
      case REDUCE:
        boolean fork = false;
        int firstData = p.L.tail().head().actiondata;
          
        {  //need to fork when different productions to reduce
          Pair<Lookahead> L = p.L;
          while (L.tail() != Pair.<Lookahead>empty()) {
            L = L.tail();
            Lookahead x = L.head();
  
            if (x.actiondata != firstData) {
              fork = true;
              break;
            }
          }
        }
          
        if (fork) {
          Subparser forked = new Subparser(
                                           new Lookahead(p.a.t, null, null,
                                                         -1, p.a.conditional,
                                                         null),
                                           p.s,
                                           p.v,
                                           new CContext(p.scope));
            
          p.clearContext();  //fork invalidates context
            
          forked.L = new Pair<Lookahead>(null);
            
          //add forked parser to subparsers (after the current one)
          currentP.setTail(new Pair<Subparser>(forked, currentP.tail()));
  
          Pair<Lookahead> lastL = null;
          Pair<Lookahead> L = p.L;
          Pair<Lookahead> forkL = forked.L;
            
          while (L.tail() != Pair.<Lookahead>empty()) {
            lastL = L;
            L = L.tail();
              
            Lookahead x = L.head();
  
            if (x.actiondata != firstData) {
              lastL.setTail(L.tail());  //remove lookahead
              L = lastL;
              forkL.setTail(new Pair<Lookahead>(x));
              forkL = forkL.tail();
            }
          }
        }
          
        p.a.actiondata = firstData;
        reduce(p);
          
        //TODO only fork reduced subparsers
        p.L.tail().head().action = null;  //induce getAction
          
        reduced = true;
        break;
      }
    }
    
    return reduced;
  }

  /**
   * Fork subparsers by the conditional in which their lookaheads are in.
   * This is necessary so that subparsers will merge with subparsers
   * that skipped a conditional due to empty branches.
   */
  private void forkByConditional(Pair<Subparser> Pfirst) {
    //all are shifts, now fork the shifts by their parent conditional
    Pair<Subparser> lastP = null;
    Pair<Subparser> currentP = Pfirst;
    
    while (currentP.tail() != Pair.<Subparser>empty()) {
      lastP = currentP;
      currentP = currentP.tail();
      Subparser p = currentP.head();

      Subparser forked = p;
      Pair<Lookahead> lastL = null;
      Pair<Lookahead> L = forked.L;
      OrderedSyntax conditional = L.tail().head().conditional;
      
      while (L.tail() != Pair.<Lookahead>empty()) {
        lastL = L;
        L = L.tail();
        Lookahead x = L.head();
        
        if (! x.conditional.same(conditional)) {
          if (x.conditional.compare(conditional) > 0) {
            //fork a new subparser by its lookaheads only
            forked = new Subparser(
                                   new Lookahead(x.conditional, null,
                                                 x.action, x.actiondata,
                                                 x.conditional, null),
                                   forked.s,
                                   forked.v,
                                   new CContext(forked.scope));
            forked.L = new Pair<Lookahead>(null, L);
            lastL.setTail(Pair.<Lookahead>empty());
            L = forked.L.tail();
            //add forked parser to Pfirst
            currentP.setTail(new Pair<Subparser>(forked, currentP.tail()));
            currentP = currentP.tail();
            conditional = x.conditional;
          } else {
            forked = new Subparser(
                                   x,
                                   forked.s,
                                   forked.v,
                                   new CContext(forked.scope));
            lastL.setTail(Pair.<Lookahead>empty());
            //add forked parser to Pfirst
            currentP.setTail(new Pair<Subparser>(forked, currentP.tail()));
            currentP = currentP.tail();
          }
        }
      }
    }

    movePfirstToP(Pfirst);
    getPfirst(Pfirst);
  }
  
  /** Move the (now invalid) Pfirst back into P, preserving order. */
  private void movePfirstToP(Pair<Subparser> Pfirst) {
    Pair<Subparser> lastP = null;
    Pair<Subparser> currentP = Pfirst;
    
    while (currentP.tail() != Pair.<Subparser>empty()) {
      lastP = currentP;
      currentP = currentP.tail();
      Subparser p = currentP.head();
      
      lastP.setTail(currentP.tail());  //remove it from Pfirst
      currentP = lastP;
      orderedAddToP(p);  //add it to P
    }
  }
  
  /**
   * Fork the Pfirst subparsers into their branches.
   */
  private void forkByBranch(Pair<Subparser> Pfirst) {
    Pair<Subparser> lastP = null;
    Pair<Subparser> currentP = Pfirst;
    
    while (currentP.tail() != Pair.<Subparser>empty()) {
      lastP = currentP;
      currentP = currentP.tail();
      Subparser p = currentP.head();

      Pair<Lookahead> lastL = null;
      Pair<Lookahead> L = p.L;
      boolean first = true;
      
      p.L = null;
      
      if (L == null) {
        System.err.println(p);
      }
      
      while (L.tail() != Pair.<Lookahead>empty()) {
        lastL = L;
        L = L.tail();
        Lookahead x = L.head();
        
        if (first) {  //just reuse the parser for the first lookahead
          p.clearContext();  //fork clears context
          p.a = x;
          first = false;
        } else {
          p = new Subparser(
                            x,
                            p.s,
                            p.v,
                            new CContext(p.scope));
          lastL.setTail(Pair.<Lookahead>empty());
        }
        
        /*if (p.v.isCompleteList && p.v.value instanceof ListNode) {
          p.v = new ValueStack(new ListNode(((ListNode) p.v.value).getName()),
          true,
          true,
          p.v);
          p.v.duplicateList = true;
          }*/
        
        orderedAddToP(p);
      }
    }
    
    Pfirst.setTail(Pair.<Subparser>empty());
    
    getPfirst(Pfirst);
  }

  /**
   * Get Pfirst from P.  After this, Pfirst will be a list of all subparsers
   * at the earliest part of the program.
   */
  private void getPfirst(Pair<Subparser> Pfirst) {
    OrderedSyntax first = null;
    Pair<Subparser> currentPfirst = Pfirst;
    Pair<Subparser> lastP = null;
    Pair<Subparser> currentP = P;
    while (currentP.tail() != Pair.<Subparser>empty()) {
      lastP = currentP;
      currentP = currentP.tail();
      Subparser p = currentP.head();
      
      if (null == first) {
        first = p.a.t;
        currentPfirst.setTail(new Pair<Subparser>(p));
        currentPfirst = currentPfirst.tail();
        lastP.setTail(currentP.tail());
        currentP = lastP;
        
      } else if (first.same(p.a.t)) {
        currentPfirst.setTail(new Pair<Subparser>(p));
        currentPfirst = currentPfirst.tail();
        lastP.setTail(currentP.tail());
        currentP = lastP;

      } else {
        break;
      }
    }
  }
  
  /** Add subparser p to P, preserving the order or P. */
  private void orderedAddToP(Subparser p) {
    Pair<Subparser> lastP = null;
    Pair<Subparser> currentP = P;
    
    while (currentP.tail() != Pair.<Subparser>empty()) {
      lastP = currentP;
      currentP = currentP.tail();
      Subparser currentp = currentP.head();
      int compare = p.a.t.compare(currentp.a.t);
      
      if (compare < 0) {
        lastP.setTail(new Pair<Subparser>(p, currentP));  //add before
        return;
      } else if (compare == 0 && (p.sequence < currentp.sequence)) {
        lastP.setTail(new Pair<Subparser>(p, currentP));  //add before
        return;
      }
    }
    
    //the new subparser is later than all in P, so add it at the end.
    currentP.setTail(new Pair<Subparser>(p));
    return;
  }
  
  /**
   * Shift the firstSize first subparsers in P.
   */
  private void shiftAll(Pair<Subparser> Pfirst) {
    Pair<Subparser> currentP = Pfirst;
    
    while (currentP.tail() != Pair.<Subparser>empty()) {
      currentP = currentP.tail();
      Subparser p = currentP.head();
      shift(p);
    }
  }
  
  /**
   * This updates the lookahead of Pfirst.  Also, this
   * function checks whether the next token is an implicit conditional
   * and sets the value isTypedef in the lookahead, p.a.   */
  private void setLookahead(Pair<Subparser> Pfirst, final OrderedSyntax next) {
    Pair<Subparser> currentP = Pfirst;
    
    while (currentP.tail() != Pair.<Subparser>empty()) {
      currentP = currentP.tail();
      Subparser p = currentP.head();
      p.a.t = next;
      p.a.action = null;
      p.a.actiondata = -1;
      if (CLexer.isType(p.a.t.syntax, sym.IDENTIFIER)) {
        p.a.isTypedef = p.scope.isType(CLexer.getString(p.a.t.syntax),
                                       p.getContext());
        if (p.a.isTypedef == trit.TRUEFALSE) {
          p.L = new Pair<Lookahead>(null);
          Pair<Lookahead> currentL = p.L;
          Context typedefContext
            = p.scope.typedefContext(CLexer.getString(p.a.t.syntax),
                                     p.getContext());
          Context varContext = p.a.c.andNot(typedefContext);
          currentL.setTail(new Pair<Lookahead>(new Lookahead(p.a.t,
                                                             typedefContext,
                                                             null, -1,
                                                             p.a.conditional,
                                                             trit.TRUE)));
          currentL = currentL.tail();
          currentL.setTail(new Pair<Lookahead>(new Lookahead(p.a.t,
                                                             varContext,
                                                             null, -1,
                                                             p.a.conditional,
                                                             trit.FALSE)));
        }
      } else {
        p.a.isTypedef = trit.FALSE;
      }
    }
  }
  
  /** The mapping of state to subparser.  For merging. */
  protected Pair<Subparser> mapStates[] = new Pair[ForkMergeParserTables.YYNSTATES];
  
  /** Pointer to last element of a list in mapStates.  For merging. */
  protected Pair<Subparser> mapStatesCurrent[] = new Pair[ForkMergeParserTables.YYNSTATES];
  
  /**
   * Merge all subparsers that are in the same state and have mergeable
   * symbol table.  Takes P_a, the subparsers at the earliest position
   * in the program.  Since we know all the subparsers are at the same
   * position, all we have to do is check whether their states and
   * their symbol tables are mergeable.
   */
  private void merge(Pair<Subparser> Pfirst) {
    if (Pfirst.tail().tail() == Pair.<Subparser>empty()) {
      //only one subparser in Pfirst, no need to check for merge
      return;
    }
    
    //step 1: separate P_a into lists by state number
    Pair<Integer> stateNums = new Pair<Integer>(null);
    Pair<Integer> currentNum = stateNums;
    Pair<Subparser> lastP = null;
    Pair<Subparser> currentP = Pfirst;
    boolean reduced = false;
    
    while (currentP.tail() != Pair.<Subparser>empty()) {
      lastP = currentP;
      currentP = currentP.tail();
      Subparser p = currentP.head();
      
      if (mapStates[p.s.state] == null) {
        currentNum.setTail(new Pair<Integer>(p.s.state));
        currentNum = currentNum.tail();
        lastP.setTail(currentP.tail());
        mapStates[p.s.state] = new Pair<Subparser>(null, currentP);
        mapStatesCurrent[p.s.state] = mapStates[p.s.state].tail();
        currentP.setTail(Pair.<Subparser>empty());
        currentP = lastP;
      } else {
        mapStatesCurrent[p.s.state].setTail(lastP.tail());
        mapStatesCurrent[p.s.state] = mapStatesCurrent[p.s.state].tail();
        lastP.setTail(currentP.tail());
        currentP.setTail(Pair.<Subparser>empty());
        currentP = lastP;
      }
    }
    
    currentP = Pfirst;
    
    //step 2: merge subparsers that have same state stack and symbol table
    currentNum = stateNums;
    while (currentNum.tail() != Pair.<Integer>empty()) {
      currentNum = currentNum.tail();
      int i = currentNum.head();
      if (mapStates[i] != null) {
        while (mapStates[i].tail() != Pair.<Subparser>empty()) {
          Pair<Subparser> subparsers = null, currentSubparsers = null;
          Pair<ValueStack> values = null, currentValues = null;
          Pair<Subparser> last = null;
          Pair<Subparser> current = mapStates[i];
          
          while (current.tail() != Pair.<Subparser>empty()) {
            last = current;
            current = current.tail();
            Subparser p = current.head();
            
            if (null == subparsers) {
              subparsers = new Pair<Subparser>(p);
              currentSubparsers = subparsers;
              values = new Pair<ValueStack>(p.v);
              currentValues = values;
              last.setTail(current.tail());
              current = last;
            } else if (mergeable(subparsers.head(), p)
                       && (p.a.t.syntax.isToken()  //don't merge implicit fork
                           && subparsers.head().a.isTypedef
                           == p.a.isTypedef || ! p.a.t.syntax.isToken())) {
              currentSubparsers.setTail(new Pair<Subparser>(p));
              currentSubparsers = currentSubparsers.tail();
              currentValues.setTail(new Pair<ValueStack>(p.v));
              currentValues = currentValues.tail();
              last.setTail(current.tail());
              current = last;
            }
          }

          currentP.setTail(new Pair<Subparser>(merge(subparsers, values)));
          currentP = currentP.tail();
        }
      }
    }
    
    
    //cleanup: free the state->subparser mapping table
    currentNum = stateNums;
    while (currentNum.tail() != Pair.<Integer>empty()) {
      currentNum = currentNum.tail();
      int i = currentNum.head();
      if (mapStates[i] != null) {
        mapStates[i] = null;
        mapStatesCurrent[i] = null;
      }
    }
    
  }
  
  /**
   * Test whether two subparsers are mergeable.  Two subparsers are
   * mergeable when (a) they have the same states in their state stacks
   * and (b) their symbol tables are mergeable.
   */
  private static boolean mergeable(Subparser p, Subparser q) {
    if (p.v.isComplete && q.v.isComplete &&
        mergeable(p.s, q.s)
        && CContext.mergeable(p.scope, q.scope)) {
      return true;
    } else {
      return false;
    }
  }
  
  /** Check whether two state stacks are mergeable. */
  private static boolean mergeable(StateStack s, StateStack t) {
    while (true) {
      if (s == t) {
        break;
      }
      
      if ((null == s || null == t) || s.state != t.state) {
        return false;
      }
      
      s = s.next;
      t = t.next;
    }
    
    return true;
  }
  
  /** Used to avoid duplicate AST nodes in the merged nodes. */
  private static IdentityHashMap<Object, Object> nodeHash
    = new IdentityHashMap<Object, Object>();
  
  /**
   * Merges the nodes in the state stacks and merges the symbol tables.
   * Merges nodes in the state stack as far as necessary until all
   * state stacks reach a common node.  Returns a subparser with
   * merged nodes and symbol tables.
   */
  private Subparser merge(Pair<Subparser> subparsers,
                          final Pair<ValueStack> values
                          ) {
    Subparser p = subparsers.head();

    if (SHOW_ACTIONS && subparsers.tail() != Pair.<Subparser>empty()) {
      System.err.println("merge");
    }
    if (subparsers.tail() == Pair.<Subparser>empty()) {
      return subparsers.head(); //no need to merge, only one subparser
    }
    
    while (true) {
      Pair<ValueStack> currentValue = values;
      Pair<Subparser> currentP = subparsers;
      Pair<Object> nodes = null;
      Pair<Object> currentNode = null;
      ValueStack lastValue = values.head();
      boolean sameValues = true;
      boolean sameList = true;
      ListNode commonList = null;
      boolean firstTime = true;

      while (currentValue != Pair.<ValueStack>empty()) {
        Object node = null;
        
        if (null != currentValue.head().value) {
          node = currentValue.head().value;
        }

        if (lastValue != currentValue.head()) {
          sameValues = false;
        }
        
        if (node != null && ! nodeHash.containsKey(node)) {
          nodeHash.put(node, null);
          if (null == nodes) {
            nodes = new Pair<Object>(null);
            currentNode = nodes;
          }

          if (node instanceof ListNode) {
            if (null == commonList) {
              commonList = ((ListNode) node);
            } else if (node != commonList) {
              sameList = false;
            }

            // Since we currently wrap all list elements in static choice
            // nodes, merged lists don't need static contexts.
            node = new BranchNode(node, null);
          } else {
            node = new BranchNode(node, currentP.head().getContext().addRef());
            sameList = false;
          }

          currentNode.setTail(new Pair<Object>(node));
          currentNode = currentNode.tail();
        }
        currentValue = currentValue.tail();
        currentP = currentP.tail();
      }
      nodeHash.clear();
      
      if (! sameValues) {
        if (null != nodes) {
          // Check the common case that all subparsers have same list value.

          if (sameList) {
            values.head().value = commonList;
          } else {
            GNode choice = GNode.createFromPair("StaticChoice",
                                                nodes.tail().head(),
                                                nodes.tail().tail());
            values.head().value = choice;
          }
        }

        if (firstTime) {
          if (P.tail() != Pair.<Subparser>empty()) {
            Pair<Subparser> head = subparsers;
            while (head != Pair.<Subparser>empty()) {
              Pair<Subparser> Phead = P;
              while (Phead.tail() != Pair.<Subparser>empty()) {
                Phead = Phead.tail();
                if (Phead.head().s == head.head().s) {
                  Phead.head().s = p.s;
                }
              }
              head = head.tail();
            }
          }

          firstTime = false;
        }
      } else {
        break;
      }
      
      currentValue = values;
      while (currentValue != Pair.<ValueStack>empty()) {
        currentValue.setHead(currentValue.head().next);
        
        currentValue = currentValue.tail();
      }
      
    }
    
    //merge symbol tables, lookaheads, and context
    Context context = p.getContext().addRef();
    
    if (null != p.L) {
      Pair<Lookahead> currentL = p.L;

      while (currentL.tail() != Pair.<Lookahead>empty()) {
        currentL = currentL.tail();
        currentL.head().c.delRef();
      }
      p.L = null;
    }
    
    subparsers = subparsers.tail();

    while (subparsers != Pair.<Subparser>empty()) {
      Subparser with = subparsers.head();
      Context newContext = context.or(with.getContext());
      context.delRef();
      context = newContext;
      
      p.scope.merge(with.scope);
      with.free();
      subparsers = subparsers.tail();
    }
    p.clearContext(); //merge changes context
    p.a.c = context;
    
    return p;
  }
  
  /** Print diagnostic information given cfg flags. */
  private static void printDiagnostics(Pair<Subparser> P) {
    Pair<Subparser> currentP;
    int i;

    /*if (cfg.location) {
      Lookahead x = P.tail().head().L.tail().head();

      System.err.println(x.t.syntax.file + ":" + x.t.syntax.line);
      }*/
    
    if (true /*cfg.subparsers*/) {
      String delim;
      
      System.err.println("START VECTOR");
      i = 0;
      delim = "";
      currentP = P;
      while (currentP.tail() != Pair.<Subparser>empty()) {
        currentP = currentP.tail();
        Subparser subparser = currentP.head();
        
        System.err.print(delim);
        System.err.print(i + ": " + subparser);
        
        i++;
        delim = "\n";
      }
      System.err.println("END VECTOR(" + i + ")\n");
    }
    /*else if (cfg.numsubparsers) {
      i = 0;
      currentP = P;
      while (currentP.tail() != Pair.<Subparser>empty()) {
        currentP = currentP.tail();
        i++;
      }
      System.err.println("numsubparsers = " + i);
      }*/

    /*if (cfg.numstates) {
      HashSet<Integer> hash;
      
      hash = new HashSet<Integer>();
      i = 0;
      currentP = P;
      while (currentP.tail() != Pair.<Subparser>empty()) {
        currentP = currentP.tail();
        Subparser subparser = currentP.head();
        
        if (! hash.contains(subparser.s.hashCode())) {
          i++;
          hash.add(subparser.s.hashCode());
        }
      }
      System.err.println("numstates = " + i);
      }*/
  }
  
  /** Get the parsing action for the given lookahead, setting the lookahead's
   * x.action and x.actiondata.
   */
  private void getAction(Lookahead x, StateStack s, CContext scope) {
    trit istypedef = x.isTypedef;
    
    if (x.t.syntax.isToken()) {
      int yyn;
      int yystate;
      
      yystate = s.state;
      
      yyn = ForkMergeParserTables.yypact.table[yystate];
      
      //try to reduce without using lookahead
      if (ForkMergeParserTables.YYPACT_NINF == yyn) {
        yyn = ForkMergeParserTables.yydefact.table[yystate];
        if (0 == yyn) {
          x.action = ParsingAction.ERROR;
          x.actiondata = NODEFAULT;
        } else {
          //reduce
          x.action = ParsingAction.REDUCE;
          x.actiondata = yyn;
        }
      } else {
        int yytoken;
  
        if (null != x.t.syntax && x.t.syntax.isToken()) {
          if (CLexer.isType(x.t.syntax, sym.EOF)) {
            yytoken = ForkMergeParserTables.YYEOF;
          } else {
            Token token;
            String str;
            sym tokentype;
            
            token = (Token) x.t.syntax;
            str = CLexer.getString(token);
            tokentype = token.type();

            assert null != istypedef && trit.TRUEFALSE != istypedef;

            if (null != istypedef && trit.TRUE == istypedef) {
              tokentype = sym.TYPEDEFname;
            }

            yytoken = ForkMergeParserTables.translate(tokentype);
          }
        } else {
          yytoken = -1;
        }
  
        yyn += yytoken;  //index into action table, state row + token column
        
        if (yyn < 0 || ForkMergeParserTables.YYLAST < yyn || ForkMergeParserTables.yycheck.table[yyn] != yytoken) {
          yyn = ForkMergeParserTables.yydefact.table[yystate];
          if (0 == yyn) {
            x.action = ParsingAction.ERROR;
            x.actiondata = NODEFAULT;
          } else {
            x.action = ParsingAction.REDUCE;
            x.actiondata = yyn;
          }
        } else {
          yyn = ForkMergeParserTables.yytable.table[yyn];
          
          if (yyn <= 0) {  //reduce or error entry
            if (0 == yyn || ForkMergeParserTables.YYTABLE_NINF == yyn) {
              x.action = ParsingAction.ERROR;
              x.actiondata = INVALID;
            } else {
              yyn = -yyn;
              x.action = ParsingAction.REDUCE;
              x.actiondata = yyn;
            }
          } else {  //shift entry
            yystate = yyn;
            
            if (ForkMergeParserTables.YYFINAL == yystate) {
              x.action = ParsingAction.ACCEPT;
              x.actiondata = -1;
            } else {
              x.action = ParsingAction.SHIFT;
              x.actiondata = yystate;
            }
          }
        }
      }
    } else if (x.t.syntax.isError()) {
      x.action = ParsingAction.ERROR;
      x.actiondata = ERRDIRECTIVE;
    } else {
      assert false;
    }
  }
  
  /** Move to the next token or conditional.  Need to store layout too. */
  private static OrderedSyntax moveToNext(OrderedSyntax ordered) {
    boolean done;
    
    do {
      do {
        ordered = ordered.getNext();
      } while ( !(ordered.syntax.isToken() || ordered.syntax.isConditional()
                  /*|| ordered.syntax.isError()*/ ));
      
      Pair<CSyntax> flayout = null;
      Pair<CSyntax> lastFlayout = null;
  
      // for roundtripping
      /*if (cfg.roundtrip) {
        flayout = new Pair<CSyntax>(null);
        lastFlayout = flayout;
        }*/
      
      //skip empty conditionals
      done = true;
      if (ordered.syntax.isIf()) {
        int nesting = 0;
        OrderedSyntax forward;
        
        forward = ordered;
        
        for (;;) {
          /*if (cfg.roundtrip) {
            lastFlayout.setTail(new Pair<CSyntax>(forward.syntax));
            lastFlayout = lastFlayout.tail();
            }*/
          forward = forward.getNext();
          
          if (forward.syntax.isToken() || forward.syntax.isError()) {
            break;
          }
          else if (0 == nesting && forward.syntax.isEndConditional()) {
            ordered = forward;
            done = false;
            break;
          }
          
          if (forward.syntax.isIf()) {
            nesting++;
          }
          else if (forward.syntax.isEndConditional()) {
            nesting--;
          }
        }
      }
    } while (! done);
    
    return ordered;
  }
  
  /**
   * Move from the end of a branch (signified by #elif) to the end of
   * the conditional (#endif).
   *
   * TODO One possible optimization is to set a pointer to #endif from all
   * #elifs during the lookaheads() function.
   */
  private static OrderedSyntax moveToEndif(OrderedSyntax a) {
    int nesting = 0;
    
    if (a.syntax.isEndConditional()) return a;
    
    a = moveToNext(a);
    
    while ( ! (0 == nesting && a.syntax.isEndConditional())) {
      
      switch (a.syntax.syntaxKind()) {
      case IF:
        nesting++;
        break;
        
      case END_CONDITIONAL:
        nesting--;
        break;
      }
      
      a = moveToNext(a);
    }
    
    return a;
  }
  
  /**
   * Get list of lookaheads for a given conditional.  All lookaheads from
   * every branch of a conditional is added to the list, accounting for
   * empty branches, nested conditionals, and implicit elses.
   * The returned list contains the lookaheads in the
   * order in which they appear in the input text.
   */
  private Pair<Lookahead> lookaheads(Lookahead start) {
    //first element always null.  makes removing first non-null element easy
    Pair<Lookahead> headL = new Pair<Lookahead>(null);
    Pair<Lookahead> L = headL;
    Stack<Context> cstack = new Stack<Context>();
    Stack<Context> bstack = new Stack<Context>();
    Stack<Context> estack = new Stack<Context>();
    Stack<Context> ustack = new Stack<Context>();
    Stack<Boolean> sstack = new Stack<Boolean>();
    boolean seen = false;
    OrderedSyntax a = start.t;
    Context c = contextManager.new Context(true);
    Context branch = null;
    Context empty = contextManager.new Context(false);
    Context union = contextManager.new Context(false);
    OrderedSyntax currentIf = start.conditional;
    Stack<OrderedSyntax> currentIfs = new Stack<OrderedSyntax>();
    
    while (true) {
      if (a.syntax.isToken()) {
        seen = true;
        
        L.setTail(
                  new Pair<Lookahead>(new Lookahead(a, c, null, -1,
                                                    currentIf, null)));
        L = L.tail();
        
        c.addRef();
        
        if (cstack.empty()) {
          break;
          
        } else {
          //move to next branch or endif
          int nesting = 0;
          while (true) {
            a = moveToNext(a);

            if ( nesting == 0
                 && (a.syntax.isElif() || a.syntax.isEndConditional())
                 ) {
              break;
            }

            if (a.syntax.isIf()) nesting++;
            else if (a.syntax.isEndConditional()) nesting--;
          }
        }
        
      } else if (a.syntax.isConditional()) {
        Conditional conditional = (Conditional) a.syntax;
        
        if (conditional.isIf()) {
          //enter the if branch's presence condition
          sstack.push(seen);
          seen = false;
          
          currentIfs.push(currentIf);
          currentIf = a;
          
          bstack.push(branch);
          branch = ((If) conditional).context;
          
          cstack.push(c);
          c = cstack.peek().and(branch);
          
          estack.push(empty);
          empty = contextManager.new Context(false);
          
          ustack.push(union);
          union = branch;
          branch.addRef();
          
          if (c.isFalse()) {
            //move to next branch or endif
            int nesting = 0;
            while (true) {
              a = moveToNext(a);
  
              if ( nesting == 0
                   && (a.syntax.isElif() || a.syntax.isEndConditional())
                   ) {
                break;
              }
  
              if (a.syntax.isIf()) nesting++;
              else if (a.syntax.isEndConditional()) nesting--;
            }
          }
          else {
            //move to next token or conditional directives
            a = moveToNext(a);
          }
          
        } else if (conditional.isElif()) {
          //save the presence conditions of an empty branch
          if (! seen) {
            Context newEmpty = empty.or(branch);
            
            empty.delRef();
            empty = newEmpty;
          }
          
          //enter new branch's presence condition
          seen = seen || sstack.pop();
          sstack.push(seen);
          seen = false;
          
          branch = ((Elif) conditional).context;
          
          c.delRef();
          c = cstack.peek().and(branch);
          
          Context newUnion = union.or(branch);
          union.delRef();
          union = newUnion;
          
          if (c.isFalse()) {
            //move to next branch or endif
            int nesting = 0;
            while (true) {
              a = moveToNext(a);
  
              if ( nesting == 0
                   && (a.syntax.isElif() || a.syntax.isEndConditional())
                   ) {
                break;
              }
  
              if (a.syntax.isIf()) nesting++;
              else if (a.syntax.isEndConditional()) nesting--;
            }
          }
          else {
            //move to next token or conditional directives
            a = moveToNext(a);
          }

        } else if (conditional.isEndConditional()) {
          //save the presence conditions of an empty branch
          if (! seen) {
            Context newEmpty = empty.or(branch);
            
            empty.delRef();
            empty = newEmpty;
          }
          seen = seen || sstack.pop();
          
          //check for implicit #else and add it to empty branches
          if (! union.is(cstack.peek())) {
            Context notUnion = union.not();
            Context newEmpty = empty.or(notUnion);
            
            notUnion.delRef();
            empty.delRef();
            empty = newEmpty;
          }
          union.delRef();
          union = ustack.pop();
          
          //exit the conditional block's presence condition
          c.delRef();
          c = cstack.pop();
          branch = bstack.pop();
          
          //continue finding lookaheads for empty branches and implicit else
          if (! empty.isFalse()) {
            Context oldEmpty = estack.pop();
            Context combinedEmpty = empty.or(oldEmpty);
            Context newC = c.and(combinedEmpty);
            
            empty.delRef();
            empty = contextManager.new Context(false);

            oldEmpty.delRef();
            combinedEmpty.delRef();
            
            //find the next token for the empty branches and implicit else
            c.delRef();
            c = newC;
            
            if (c.isFalse()) {
              if (cstack.empty()) {  //done!
                break;
                
              } else {
                //move to next branch or endif
                int nesting = 0;
                while (true) {
                  a = moveToNext(a);
      
                  if ( nesting == 0
                       && (a.syntax.isElif() || a.syntax.isEndConditional())
                       ) {
                    break;
                  }
      
                  if (a.syntax.isIf()) nesting++;
                  else if (a.syntax.isEndConditional()) nesting--;
                }
              }
              
            } else {
              //move to next token or conditional directive
              a = moveToNext(a);
  
              if (cstack.empty()) {
                //if at end of a branch, move out of the nested conditional(s)
                while (a.syntax.isElif() || a.syntax.isEndConditional()) {
                  if (a.syntax.isElif()) {
                    //move to endif
                    int nesting = 0;
                    while (true) {
                      a = moveToNext(a);
          
                      if (nesting == 0 && (a.syntax.isEndConditional())) {
                        break;
                      }
          
                      if (a.syntax.isIf()) nesting++;
                      else if (a.syntax.isEndConditional()) nesting--;
                    }
                  }
    
                  if (a.syntax.isEndConditional()) {
                    a = moveToNext(a);
                  }
                }
              }
            }
            
          } else {
            empty.delRef();
            empty = estack.pop();
            
            if (cstack.empty()) {  //done!
              break;
              
            } else {
              //move to next branch or endif
              int nesting = 0;
              while (true) {
                a = moveToNext(a);
    
                if ( nesting == 0
                     && (a.syntax.isElif() || a.syntax.isEndConditional())
                     ) {
                  break;
                }
    
                if (a.syntax.isIf()) nesting++;
                else if (a.syntax.isEndConditional()) nesting--;
              }
            }
          }
          
          currentIf = currentIfs.pop();
          
        }
        else {
          assert true;
        }
      } else {
        assert true;
      }
    }
    
    c.delRef();
    
    assert headL.tail() != Pair.<Lookahead>empty();
    
    return headL;
  }
   
  /** Collect state-space data */
  private void collectStateSpaceData(Pair<Subparser> Pfirst) {
    IdentityHashMap<Object, Object> hash
      = new IdentityHashMap<Object, Object>();
    int i = 0;
    Pair<Subparser> currentP = Pfirst;
    
    while (currentP.tail() != Pair.<Subparser>empty()) {
      currentP = currentP.tail();
      Subparser subparser = currentP.head();
      
      if (! hash.containsKey(subparser.s)) {
        i++;
        hash.put(subparser.s, null);
      }
    }

    currentP = P;
    while (currentP.tail() != Pair.<Subparser>empty()) {
      currentP = currentP.tail();
      Subparser subparser = currentP.head();
      
      if (! hash.containsKey(subparser.s)) {
        i++;
        hash.put(subparser.s, null);
      }
    }

    Statistics.states(i);
    
    hash.clear();
  }

  /** A lookahead token. */
  protected static class Lookahead {
    /** The token. */
    public OrderedSyntax t;
    
    /** The context. */
    public Context c;
    
    /** The parsing action. */
    public ParsingAction action;
    
    /** The parsing action data. */
    public int actiondata;
    
    /** The #if in which this lookahead is contained. */
    public OrderedSyntax conditional;
    
    /** Whether or not the type is a typedef. */
    public trit isTypedef;
    
    public Lookahead(OrderedSyntax t, Context c, ParsingAction action,
                     int actiondata, OrderedSyntax conditional, trit isTypedef
                     ) {
      this.t = t;
      this.c = c;
      this.action = action;
      this.actiondata = actiondata;
      this.conditional = conditional;
      this.isTypedef = isTypedef;
    }
    
    public String toString() {
      if (null == action) {
        return t.syntax.toString() /*+ (cfg.debugcontexts ? c : "")*/;
      }
      else {
        return "(" + t.syntax.toString() + ", " + action + ", " + actiondata + ")" /*+ (cfg.debugcontexts ? c : "")*/;
      }
    }
  }
  
  /** A list node. */
  protected static class ListNode extends Node {
    protected String name = null;
    protected Pair<Object> list = null;
    protected Pair<Object> last = null;
    
    public ListNode(String name) {
      this(name, null, null);
    }
    
    public ListNode(String name, Pair<Object> list) {
      this(name, list, null);
    }
    
    public ListNode(String name, Pair<Object> list, Pair<Object> last) {
      this.name = name;
      this.list = list;
      this.last = last;
    }
    
    public Node add(Object o) {
      if (null != list && null == last) {
        last = list;
        while (last.tail() != Pair.<Object>empty()) {
          last = last.tail();
        }
      }

      if (null == list) {
        list = new Pair<Object>(o);
        last = list;
      } else {
        last.setTail(new Pair<Object>(o));
        last = last.tail();
      }
      
      return this;
    }
    
    public Object get(int index) {
      return list.get(index);
    }
    
    public int size() {
      if (null == list) {
        return 0;
      } else {
        return list.size();
      }
    }
    
    public String getName() {
      return name;
    }
  }
    
  /** A subparser in the parsing vector. */
  protected static class Subparser {
    /** The lookahead symbol, either a token or a conditional. **/
    public Lookahead a;
    
    /** The lookahead tokens. */
    public Pair<Lookahead> L;
    
    /** The state stack. */
    public StateStack s;

    /** The value stack. */
    public ValueStack v;
    
    /** The C typedef/var symbol table. */
    public CContext scope;

    public static int counter = 0;

    public int sequence = counter++;
    
    /** Create a new subparser. */
    public Subparser(Lookahead a, StateStack s, ValueStack v, CContext scope) {
      this.a = a;
      this.v = v;
      this.L = null;
      this.s = s;
      this.scope = scope;
    }
    
    /** Context: union of all lookaheads. */
    private Context contextCache = null;
    
    /** Get the context by unioning all the lookaheads' contexts. */
    public Context getContext() {
      if (null == contextCache) {
        if (a.c != null) {
          contextCache = a.c.addRef();
        } else {
          Pair<Lookahead> L = this.L;
          Context union = null;
          
          while (L.tail() != Pair.<Lookahead>empty()) {
            L = L.tail();
            if (null == union) {
              union = L.head().c;
              L.head().c.addRef();
            } else {
              Context newUnion = union.or(L.head().c);
              
              union.delRef();
              union = newUnion;
            }
          }
          
          contextCache = union;
        }
      }
      
      return contextCache;
    }
    
    /** Clear the context cache.  For when a subparser forks. */
    public void clearContext() {
      if (null != contextCache) {
        contextCache.delRef();
        contextCache = null;
      }
      
      if (null != a.c) {
        a.c.delRef();
        a.c = null;
      }
    }
    
    /** Free all of the subparser's contexts */
    public void free() {
      this.clearContext();
      
      if (null != this.L) {
        Pair<Lookahead> L = this.L;
        while (L.tail() != Pair.<Lookahead>empty()) {
          L = L.tail();
          L.head().c.delRef();
        }
        this.L = null;
      }
      
      this.scope.delRefs();
      this.scope = null;
    }
    
    /** String representation */
    public String toString() {
      StringBuilder sb;
      ValueStack value;
      String delim;
      
      sb = new StringBuilder();
      sb.append("subparser: ");
      sb.append(this.a.t.syntax);
      sb.append(":");
      sb.append(this.a.t.order);
      /*if (cfg.debugcontexts) {
        sb.append(":");
        sb.append(this.getContext());
        }*/
      sb.append(" (");
      delim = "";
      Pair<Lookahead> L = this.L;
      while (L != null && L.tail() != Pair.<Lookahead>empty()) {
        L = L.tail();
        Lookahead x = L.head();
        
        sb.append(delim);
        sb.append(x.t.syntax);
        sb.append(":");
        sb.append(x.t.order);
        sb.append(":");
        sb.append(x.t.syntax.file);
        sb.append(":");
        sb.append(x.t.syntax.line);
        if (null != x.action) {
          sb.append(" ");
          if (ParsingAction.REDUCE == x.action) {
            sb.append(x.action.toString());
            sb.append(":");
            sb.append(x.actiondata);
          }
          else {
            sb.append(x.action.toString());
          }
        }
        delim = ", ";
      }
      sb.append(")");
      sb.append(" state=");
      sb.append(this.s.state);
      sb.append(" line=");
      /*if (null != this.b.t) {
        sb.append(this.b.t.syntax.line);
        }*/
      sb.append(" hashcode=");
      sb.append(this.hashCode());      
      sb.append("\n");      
      
      sb.append("stack" + this.s.hashCode() + ":");
      value = this.v;
      
      while (null != value) {
        sb.append(" " + 
                  (value.value instanceof Node ?
                   ((Node) value.value).getName()
                   + ((Node) value.value).hashCode()
                   : value.value));
        value = value.next;
      }
      sb.append("\n");
      
      return sb.toString();
    }
  }
    
  /** A syntax object and it's sequence number. */
  protected static class OrderedSyntax {
    /** The stream from which to pull syntax */
    public final Stream stream;
    
    /** The syntax */
    public final CSyntax syntax;
    
    /** The ordered sequence number */
    protected int order;
    
    /** The next ordered token */
    protected OrderedSyntax _next;
    
    public OrderedSyntax(Stream stream) {
      this(stream, null, 0);
    }
    
    private OrderedSyntax(Stream stream, CSyntax syntax, int order) {
      this.stream = stream;
      this.syntax = syntax;
      this.order = order;
    }
    
    public OrderedSyntax getNext() {
      if (null == this._next) {
        this._next = new OrderedSyntax(this.stream, this.stream.scan(),
                                       this.order + 1);
      }
      
      return _next;
    }
    
    /** Compare to another ordered syntax sequence number.
     * This is a partial ordering, so the algorithm sees all tokens
     * in sibling branches as having equal token numbers.
     * 1 for this is greater than
     * 0 for equal
     * -1 for this is less than
     */
    public int compare(OrderedSyntax orderedSyntax) {
      if (this.order < orderedSyntax.order) return -1;
      else if (this.order > orderedSyntax.order) return 1;
      else /* if (this.order == orderedSyntax.order) */ return 0;
    }
    
    /** Test whether two tokens are the same. */
    public boolean same(OrderedSyntax ordered) {
      return this.order == ordered.order;
    }
    
    public String toString() {
      StringBuilder sb;
      
      sb = new StringBuilder();
      
      sb.append(this.syntax);
      sb.append("\n");
      sb.append("Order: " + this.order);
      sb.append("\n");
      
      return sb.toString();
    }
  }
  
  /** A syntax object and it's sequence number.  The sequence numbers form
   * a partial ordering such that sequence number of tokens in sibling
   * branches are equal
   */
  protected static class PartiallyOrderedSyntax extends OrderedSyntax {
    /** The partially ordered sequence number */
    protected LinkedList<Integer> order;
    
    public PartiallyOrderedSyntax(Stream stream) {
      super(stream, null, -1);
      
      this.order = new LinkedList<Integer>();
      this.order.add(1); //top-level "branch" is the file itself
      this.order.add(0); //start token number
    }
    
    private PartiallyOrderedSyntax(Stream stream, CSyntax syntax,
                                   LinkedList<Integer> order) {
      super(stream, syntax, -1);
      
      this.order = order;
    }
    
    public OrderedSyntax getNext() {
      if (null == this._next) {
        LinkedList<Integer> newOrder;
        CSyntax newSyntax;
        
        newSyntax = this.stream.scan();
        newOrder = new LinkedList<Integer>();
        newOrder.addAll(this.order);
        
        //increment order number
        if (newSyntax.isIf()) {
          int num;
          
          num = newOrder.removeLast();
          newOrder.add(num + 1);
          newOrder.add(1);
          newOrder.add(0);
        }
        else if (newSyntax.isElif()) {
          int num;
          
          newOrder.removeLast();
          num = newOrder.removeLast();
          newOrder.add(num + 1);
          newOrder.add(0);
        }
        else if (newSyntax.isEndConditional()) {
          int num;
          
          newOrder.removeLast();
          newOrder.removeLast();
          num = newOrder.removeLast();
          
          newOrder.add(num + 1);
        }
        else {
          int num;
          
          num = newOrder.removeLast();
          newOrder.add(num + 1);
        }
        
        this._next = new PartiallyOrderedSyntax(this.stream, newSyntax,
                                                newOrder);
      }
      
      return _next;
    }
    
    /** Compare to another ordered syntax sequence number.
     * This is a partial ordering, so the algorithm sees all tokens
     * in sibling branches as having equal token numbers.
     * 1 for this is greater than
     * 0 for equal
     * -1 for this is less than
     */
    public int compare(OrderedSyntax in) {
      PartiallyOrderedSyntax orderedSyntax;
      
      orderedSyntax = (PartiallyOrderedSyntax) in;
      for (int i = 0; i < Math.min(order.size(), orderedSyntax.order.size());
           i = i +2) {
        int b1, b2;
        int t1, t2;
        
        b1 = order.get(i);
        b2 = orderedSyntax.order.get(i);
        
        t1 = order.get(i + 1);
        t2 = orderedSyntax.order.get(i + 1);
        
        if (b1 != b2) { //different branches of the same conditional
          return 0;
        }
        else if (t1 != t2) { //same branch different tokens
          return t1 > t2 ? 1 : -1;
        }
      }
      
      return 0;
    }
    
    /** Test whether two tokens are the same. */
    public boolean same(OrderedSyntax in) {
      PartiallyOrderedSyntax ordered;
      
      ordered = (PartiallyOrderedSyntax) in;
      if (order.size() != ordered.order.size()) {
        return false;
      }
      
      int i;
      
      i = 0;
      
      for (Integer num : order) {
        if (num != ordered.order.get(i)) {
          return false;
        }
        
        i++;
      }
      
      return true;
    }
    
    public String toString() {
      StringBuilder sb;
      
      sb = new StringBuilder();
      
      sb.append(this.syntax);
      sb.append("\n");
      sb.append("Order: ");
      for (Integer i : this.order) {
        sb.append(i);
        sb.append(".");
      }
      sb.append("\n");
      
      return sb.toString();
    }
  }
  
  /** A frame of the parsing state stack. */
  protected static class StateStack {
    /** The state number */
    public int state;
    
    /** The next state in the stack */
    public StateStack next;
    
    /** Make a new state */
    public StateStack(int state, StateStack next) {
      this.state = state;
      this.next = next;
    }
    
    /** Get the ith (base 1) state down the stack.  1 is the current state. */
    public StateStack get(int i) {
      StateStack state;
      
      state = this;
      
      while (i > 1) {
        state = state.next;
        i--;
      }
      
      return state;
    }
    
    /** String */
    public String toString() {
      return state + "";
    }
  }
  
  /** A frame of the parsing state stack. */
  protected static class ValueStack {
    /** The value. */
    public Object value;
    
    /** Whether it is complete. */
    public boolean isComplete;

    /** Whether it is a complete list. */
    public boolean isCompleteList;

    /** Whether this is a duplicate list. */
    public boolean duplicateList = false;
    
    /** The next state in the stack. */
    public ValueStack next;

    /** Make a new state */
    public ValueStack(Object value, ValueStack next) {
      this(value, false, false, next);
    }

    /** Make a new state */
    public ValueStack(Object value, boolean isComplete, boolean isCompleteList,
                      ValueStack next
                      ) {
      this.value = value;
      this.isComplete = isComplete;
      this.isCompleteList = isCompleteList;
      this.next = next;
    }
    
    /** Copy constructor.  Make a shallow copy of just the stack frame. */
    public ValueStack(ValueStack v) {
      this.value = v.value;
      this.isComplete = v.isComplete;
      this.isCompleteList = v.isCompleteList;
      this.next = v.next;
    }

    /** Get the ith (base 1) state down the stack.  1 is the current state. */
    public ValueStack get(int i) {
      ValueStack value;
      
      value = this;
      
      while (i > 1) {
        value = value.next;
        i--;
      }
      
      return value;
    }
    
    /** String */
    public String toString() {
      return value.toString();
    }
  }
  
  protected static class BranchNode extends Node {
    public Object value;
    public Context context;
    
    public BranchNode(Object value, Context context) {
      this.value = value;
      this.context = context;
    }
    
    public Object get(int index) {
      switch (index) {
      case 0:
        return value;
      case 1:
        return context;
      default:
        throw new IndexOutOfBoundsException("Index: "+index+", Size: 0");
      }
    }
    
    public int size() {
      return 2;
    }
  }

  /** Used for traversing abstract syntax DAG */
  private static IdentityHashMap<Object, Integer> seen
    = new IdentityHashMap<Object, Integer>();
  private static int number;

  /**
   * Print the AST.  It not print duplicate nodes, but instead
   * print the root of a shared subtree and its number.
   */
  public static void printAST(Object o) {
    number = 0;
    seen.clear();
    printAST(o, 0);
  }

  
  /**
   * Print one node and recurse over children, given a nesting depth.
   *
   */
  private static void printAST(Object o, int nesting) {
    if (null == o) return;
    
    if (o instanceof Token) {
      indent(nesting);
      System.out.println("\"" + CLexer.getString((Token) o) + "\"");
      
    } else if (! seen.containsKey(o)) {
      seen.put(o, ++number);
      
      if (o instanceof BranchNode) {
        if (((BranchNode) o).value != null) {
          indent(nesting);
          System.out.println(((BranchNode) o).context);
        
          printAST(((BranchNode) o).value, nesting);
        }

      } else if (o instanceof ListNode) {
        Pair<Object> p = (Pair<Object>) ((ListNode) o).list;

        if (null != p) {  //not an empty list
          indent(nesting);
          System.out.println(((ListNode) o).getName() + "<" + number + ">(");

          while (Pair.<Object>empty() != p) {
            printAST(p.head(), nesting + 2);
            p = p.tail();
          }

          indent(nesting);
          System.out.println(")");
          /*while (Pair.<Object>empty() != p) {
            printAST(p.head(), nesting);
            p = p.tail();
            }*/
        }

      } else {
        Node n = (Node) o;
        indent(nesting);
        System.out.println(n.getName() + "<" + number + ">(");

        for (int i = 0; i < n.size(); i++) {
          Object child = n.get(i);
          
          if (null != child) {
            printAST(child, nesting + 2);
          }
        }

        indent(nesting);
        System.out.println(")");
      }
      
    } else {
      indent(nesting);
      System.out.println("#ref<" + seen.get(o) + ">");
    }
  }

  /**
   * Print an indent.
   */
  private static void indent(int depth) {
    for (int i = 0; i < depth; i++) System.out.print(" ");
  }
  
}

