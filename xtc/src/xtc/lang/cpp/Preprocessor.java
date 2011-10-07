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

import java.io.StringReader;

import java.lang.StringBuilder;

import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;
import java.util.ListIterator;

import xtc.util.Pair;

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

import xtc.lang.cpp.MacroTable;
import xtc.lang.cpp.MacroTable.Macro;
import xtc.lang.cpp.MacroTable.Macro.Object;
import xtc.lang.cpp.MacroTable.Macro.Function;
import xtc.lang.cpp.MacroTable.Entry;

import xtc.lang.cpp.ContextManager.Context;

import xtc.lang.cpp.ForkMergeParserTables.sym;

import net.sf.javabdd.BDD;

/**
 * This class expands macros and processes header files
 *
 * @author Paul Gazzillo
 * @version $Revision: 1.66 $
 */
public class Preprocessor implements Stream {
  /** Show errors. */
  public static boolean SHOW_ERRORS = false;

  /** Whether to gather statistics. */
  public static boolean STATISTICS = false;

  /**
   * The stream from which the Preprocessor gets tokens and
   * directives
   */
  protected Stream stream;
  
  /**
   * The stack of streams.  Used for expanding macro definitions
   * and include files
   */
  protected Stack<Stream> expansions;
  
  /**
   * The queue of syntactic units waiting to be polled.  This
   * is necessary because a macro expansion will produce multiple
   * syntactic units (e.g. annotations for original directive tokens
   * and macro expansion delimiters), but the stream interface only
   * returns one unit at a time.  This provides a buffer.
   */
  protected Queue<CSyntax> queue;
  
  /** The file manager for main file and header streams */
  protected HeaderFileManager fileManager;
  
  /** The macro table */
  protected MacroTable macroTable;
  
  /** The global context */
  protected ContextManager contextManager;

  /** The expression evaluator */
  protected ConditionEvaluator evaluator;
  
  /** The stack of macro contexts.  Used to keep track of
   * nested macro expansions.
   */
  protected Stack<TokenContext> tcontexts;
  
  /**
   * A flag indicating whether Preprocessor should preprocess tokens
   * or not.  Used to find the open-paren in funlike invocations.
   */
  protected int expanding;
  
  /** Whether the preprocessor is currently hoisting a function. */
  protected boolean isHoistingFunction;
  
  /** The current list of layout syntax before the next token. */
  Pair<CSyntax> layout;

  /** A pointer to the last element in the list of layout syntax. */
  Pair<CSyntax> lastLayout;

  /** Create a new macro preprocessor */
  public Preprocessor(HeaderFileManager fileManager, MacroTable macroTable,
                      ContextManager contextManager) {
    this.fileManager = fileManager;
    this.expansions = new Stack<Stream>();
    this.queue = new LinkedList<CSyntax>();
    this.macroTable = macroTable;
    this.contextManager = contextManager;
    this.evaluator = new ConditionEvaluator(contextManager, macroTable);
    this.tcontexts = new Stack<TokenContext>();
    this.expanding = 0;
    this.layout = new Pair<CSyntax>(null);
    this.lastLayout = layout;
  }
  
  Stack<Integer> ss = new Stack<Integer>();
  
  /**
   * This class scans the input tokens expanding macros and returns
   * either a Yytoken, whitespace, a directive, or a conditional.
   */
  public CSyntax scan() {
    // Finish emptying the queue before pulling more from the stream.
    if (queue.size() == 0) {
      CSyntax syntax;
      
      // Get the next token either from the file (base token context)
      // or from a pending macro expansion.
      if (tcontexts.empty()) {
        // Base token context.  The preprocess is pulling tokens from
        // the stream, not from other macro expansions.
        
        syntax = fileManager.scan();
        if (syntax.isEndInclude()) {
          // Test to make sure that files contained matched
          // conditional directives.
          if (true) {
            int xxx = ss.pop();
            
            if (contextManager.stack.size() != xxx) {
              System.err.println("JFDKSJAFLKDSA");
              System.err.println(((EndInclude) syntax).pfile.getName());
              System.err.println(contextManager.stack.size());
              System.err.println(xxx);
              System.err.println(queue);
              System.exit(-1);
            }
          }
        }

        /*if (fileManager.includes.size() == 0) {
          if (cfg.roundtrip) {
            this.lastLayout.setTail(new Pair<CSyntax>(syntax));
            this.lastLayout = this.lastLayout.tail();
          }
          }*/

      } else {
        // We are inside a macro expansion and preprocessing the
        // tokens of the definition.
        
        if (tcontexts.peek().end()) {
          // The token context is over.  That is, we reached the end
          // of a macro expansion or parameter prescan.
          syntax = popTokenContext();
          
        } else {
          syntax = tcontexts.peek().scan();
          if (syntax.isStartConditional()) {
            ((StartConditional) syntax).context.addRef();
          }
        }
        
        // Handle token-pasting.  Use a while loop because there may
        // be multiple pastes in a row.
        while (syntax.isFlaggedSyntax()
               && ((FlaggedSyntax) syntax).hasFlag(flag.PASTE_LEFT)) {
          CSyntax next;
          
          // There must be a next token, because we don't allow ## at
          // the beginning or end.
          do {
            next = tcontexts.peek().scan();
          } while (! (next.isToken()
                   || next.isConditionalBlock()
                   || Layout.AVOID_PASTE == next));
          
          if (syntax.isToken() && next.isToken()) {
            // Paste a token with a token.
            String pasted;
            CSyntax s1, s2;
            CLexer lexer;
            StringReader sr;
            
            pasted = CLexer.getString(syntax) + CLexer.getString(next);
            sr = new StringReader(pasted);
            lexer = new CLexer(sr);
            
            try {
              s1 = lexer.yylex();
              s2 = lexer.yylex();
              
              if (CLexer.isType(s2, sym.EOF)) {
                // The paste was successful.
                if (((Token) syntax).hasFlag(flag.PREV_WHITE)) {
                  ((Token) s1).setFlag(flag.PREV_WHITE);
                }
                
                syntax = s1;
                
                if (((Token) next).hasFlag(flag.PASTE_LEFT)) {
                  ((Token) syntax).setFlag(flag.PASTE_LEFT);
                }
                
                if (STATISTICS) {
                  Statistics.inc(Statistics.row.PASTE);
                }

              } else {
                // The paste was unsuccessful.  Add a space between
                // the tokens.
                if (SHOW_ERRORS) {
                  System.err.println("error: pasting "
                                     + CLexer.getString(syntax) + " and "
                                     + CLexer.getString(next)
                                     + " does not give a valid preprocessing"
                                     + "token");
                }
                queue.offer(s1);
                queue.offer(Layout.SPACE);
                queue.offer(s2);
                syntax = Layout.EMPTY;
              }
              
            } catch (Exception e) {
              e.printStackTrace();
              System.exit(1);
            }

          } else if (syntax.isConditionalBlock()
                     || next.isConditionalBlock()) {
            // One or both token-paste arguments is a conditional.
            // Need to hoist token pasting around it.
            CSyntax hoisted;
            
            hoisted = hoistPasting(syntax, next);
            
            if (null != hoisted) {
              syntax = hoisted;
              if (STATISTICS) {
                Statistics.inc(Statistics.row.PASTE);
                Statistics.inc(Statistics.row.HOISTED_PASTE);
              }
            } else {
              queue.offer(syntax);
              queue.offer(Layout.SPACE);
              queue.offer(next);
              syntax = Layout.EMPTY;
            }
          } else {
            queue.offer(syntax);
            queue.offer(next);
            syntax = Layout.EMPTY;
          }
        }
      }
      
      // Got the next token either from the file or a pending macro
      // expansion.  Now we can expand the token or evaluate the
      // directive.  The method "isExpanding" indicates whether the
      // preprocessor is doing preprocessing or just returning raw
      // tokens from the input (as when collecting function-like macro
      // expansions.)
      
      if (CLexer.isType(syntax, sym.EOF)) {
        // Just return EOF.
        queue.offer(syntax);
        
      } else if (syntax.isToken() && isExpanding()
                 && ! contextManager.isFalse()) {
        // A regular token.  Check whether it is a macro and expand
        // it.
        token((Token) syntax);
        
      } else if (syntax.isDirective() && isExpanding()
                 && ! contextManager.isFalse()) {
        // A compound token.  Preprocess it as normal.
        evaluateDirective((Directive) syntax);
        
      } else if (syntax.isDirective()) {
        // A compound token in a function-like macro invocation. Only
        // conditionals are evaluated.  Otherwise the directive gets
        // collected with the other tokens of the function-like macro
        // invocation.
        Directive directive;
        
        directive = (Directive) syntax;
        
        switch(directive.kind()) {
        case IF:
        case IFDEF:
        case IFNDEF:
        case ELIF:
        case ELSE:
        case ENDIF:
          evaluateDirective(directive);
          break;
        default:
          queue.offer(directive);
          break;
        }
        
      } else if (syntax.isConditional()
                 && (isExpanding() || isHoistingFunction)) {
        // A conditional (i.e. the conditional directives generated by
        // and used internally in the preprocessor.  It acts just like
        // a conditional directive.
        if (syntax.isIf()) {
          Context context;
          
          contextManager.push();
          context = ((If) syntax).context;
          contextManager.enter(context.bdd().id());
          
        } else if (syntax.isElif()) {
          Context context;
          
          context = ((Elif) syntax).context;
          contextManager.enter(context.bdd().id());
          
        } else if (syntax.isEndConditional()) {
          contextManager.pop();
        }
        
        queue.offer(syntax);

      } else if (syntax.isConditional()) {
        // A conditional in a function-like macro invocation.
        queue.offer(syntax);

      } else if (syntax.isConditionalBlock()) {
        // A conditional block.  Serialize it into conditionals and
        // regular tokens.
        ConditionalBlock block;
        List<CSyntax> serial;
        SyntaxContext stream;
        boolean first;
        
        block = (ConditionalBlock) syntax;
        serial = new LinkedList<CSyntax>();
        first = true;
        for (int i = 0; i < block.branches.size(); i++) {
          List<CSyntax> branch;
          
          if (first) {
            serial.add(new If(block.contexts.get(i)));
            first = false;
          }
          else {
            serial.add(new Elif(block.contexts.get(i)));
          }
          block.contexts.get(i).addRef();
          
          if (null != block.branches.get(i)) {
            // Non-empty branch.
            for (CSyntax s : block.branches.get(i)) {
              serial.add(s);
            }
          }
        }
        
        if (! first) {
          serial.add(new EndConditional());
        }

        stream = new SyntaxContext(serial);

        queue.offer(Layout.EMPTY);
        pushTokenContext(stream);
        
      } else {
        // Any other tokens are just returned.
        queue.offer(syntax);
      }
    }

    return queue.poll();
  }
  
  public boolean end() {
    if (tcontexts.empty() && fileManager.end()) {
      return true;
    }
    else {
      return false;
    }
  }
  
  /**
   * Hoist conditionals around token-paste operation.
   *
   * @param left A regular or compound token for the left-hand-side of
   * the token-paste operation.
   * @param right A regular or compound token for the right-hand-side
   * of the token-paste operation.
   * @return the pasted token or null if the paste was invalid.
   */
  protected CSyntax hoistPasting(CSyntax left, CSyntax right) {
    boolean didPaste;
    ConditionalBlock pastedBlock;
    
    if (left.isConditionalBlock()) {
      left = flattenConditionalBlock((ConditionalBlock) left);
    }
    
    if (right.isConditionalBlock()) {
      right = flattenConditionalBlock((ConditionalBlock) right);
    }
    
    didPaste = false;
    pastedBlock = null;
    if (left.isToken()) {
      ConditionalBlock block;
      
      block = (ConditionalBlock) right;
      
      for (int i = 0; i < block.branches.size(); i++) {
        List<CSyntax> branch;
        
        branch = (List<CSyntax>) block.branches.get(i);
        if (branch.size() > 0) {
          CSyntax first;
          String pasted;
          CSyntax s1, s2;
          CLexer lexer;
          StringReader sr;
          
          first = branch.get(0);
          pasted = CLexer.getString(left) + CLexer.getString(first);
          sr = new StringReader(pasted);
          lexer = new CLexer(sr);
          
          try {
            s1 = lexer.yylex();
            s2 = lexer.yylex();
            
            if (CLexer.isType(s2, sym.EOF)) {
              // A successful paste.
              branch.remove(0);
              branch.add(0, s1);
              didPaste = true;
            }
            else {
              // Don't expand tokens of the invalid paste.
              if (SHOW_ERRORS) {
                System.err.println("error: pasting " + CLexer.getString(left)
                                   + " and " + CLexer.getString(first)
                                   + " does not give a valid preprocessing "
                                   + "token");
              }
            }
          }
          catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
          }
        }
      }
      
      pastedBlock = block;
      
    } else if (right.isToken()) {
      ConditionalBlock block;
      
      block = (ConditionalBlock) left;
      
      for (int i = 0; i < block.branches.size(); i++) {
        List<CSyntax> branch;
        
        branch = (List<CSyntax>) block.branches.get(i);
        if (branch.size() > 0) {
          CSyntax last;
          String pasted;
          CSyntax s1, s2;
          CLexer lexer;
          StringReader sr;
          
          last = branch.get(branch.size() - 1);
          pasted = CLexer.getString(last) + CLexer.getString(right);
          sr = new StringReader(pasted);
          lexer = new CLexer(sr);
          
          try {
            s1 = lexer.yylex();
            s2 = lexer.yylex();
            
            if (CLexer.isType(s2, sym.EOF)) {
              // Paste was successful.
              branch.remove(branch.size() - 1);
              branch.add(s1);
              didPaste = true;
            }
            else {
              // Don't expand tokens of the invalid paste.
              if (SHOW_ERRORS) {
                System.err.println("error: pasting " + CLexer.getString(last)
                                   + " and " + CLexer.getString(right)
                                   + " does not give a valid preprocessing "
                                   + "token");
              }
            }
          }
          catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
          }
        }
      }
      
      pastedBlock = block;
    }
    else {
      // TODO hoist token-pasting when both arguments are conditional.
      System.err.println("TODO hoist both token-paste args");
      System.exit(1);
    }
    
    // Preserve the PASTE_LEFT flag on the pasted token.  This is
    // necessary since the pasted token may be the argument of another
    // token-paste operation.
    if (((FlaggedSyntax) right).hasFlag(flag.PASTE_LEFT)) {
      ((FlaggedSyntax) left).setFlag(flag.PASTE_LEFT);
      
    } else {
      ((FlaggedSyntax) left).unsetFlag(flag.PASTE_LEFT);
    }
    
    if (didPaste) {
      return pastedBlock;
    }
    else {
      return null;
    }
  }
  
  /**
   * Flatten nested conditional blocks.  A conditional block without
   * any nested conditional blocks is returned.
   *
   * @param The conditional block to flatten.
   * @return The flatten conditional block without any nested
   * conditional blocks.
   */
  protected ConditionalBlock flattenConditionalBlock(ConditionalBlock block) {
    ConditionalBlock newBlock;
    List<List<? extends CSyntax>> newBranches;
    List<Context> newContexts;
    
    newBranches = new LinkedList<List<? extends CSyntax>>();
    newContexts = new LinkedList<Context>();

    for (int i = 0; i < block.branches.size(); i++) {
      List<? extends CSyntax> branch;
      Context context;
      List<List<? extends CSyntax>> branches;
      List<Context> contexts;
      
      branch = block.branches.get(i);
      context = block.contexts.get(i);

      branches = new LinkedList<List<? extends CSyntax>>();
      contexts = new LinkedList<Context>();

      branches.add(new LinkedList<CSyntax>());
      contexts.add(context);
      
      flattenBranch(branch, branches, contexts);
      
      newBranches.addAll(branches);
      newContexts.addAll(contexts);
    }
    
    // Trim infeasible branches when presence condition is false.
    // This reduces the number of branches in the flattened
    // conditional.
    for (int i = 0; i < newContexts.size(); i++) {
      Context context;
      
      context = newContexts.get(i);
      
      if (context.isFalse()) {
        context.delRef();
        newContexts.remove(i);
        newBranches.remove(i);
        i--;
      }
    }
    
    newBlock = new ConditionalBlock(newBranches, newContexts);
    
    if (null != block.flags) {
      newBlock.flags = block.flags.clone();
    }
    
    return newBlock;
  }
  
  /**
   * Takes a list of tokens and conditional blocks and flattens and
   * conditional blocks contained in the branch.
   *
   * @param list The list of tokens.
   * @param branches The flattened branches.
   * @param contexts The presence conditions of the flattened branches
   */
  protected void flattenBranch(List<? extends CSyntax> list,
                               List<List<? extends CSyntax>> branches,
                               List<Context> contexts) {
    for (CSyntax syntax : list) {
      if (syntax.isToken() || syntax.isLayout()) {
        for (List<? extends CSyntax> branch : branches) {
          ((List<CSyntax>) branch).add(syntax);
        }
      }
      else if (syntax.isConditionalBlock()) {
        ConditionalBlock block;
        List<List<? extends CSyntax>> newBranches;
        List<Context> newContexts;
        
        block = flattenConditionalBlock((ConditionalBlock) syntax);
        
        newBranches = new LinkedList<List<? extends CSyntax>>();
        newContexts = new LinkedList<Context>();

        for (int i = 0; i < branches.size(); i++) {
          for (int j = 0; j < block.branches.size(); j++) {
            List<CSyntax> newBranch;
            Context newContext;
            
            newBranch = new LinkedList<CSyntax>();
            newBranch.addAll(branches.get(i));
            newBranch.addAll(block.branches.get(j));
            
            newContext = contexts.get(i).and(block.contexts.get(j));
            block.contexts.get(j).delRef();
            
            newBranches.add(newBranch);
            newContexts.add(newContext);
          }
          contexts.get(i).delRef();
        }
        
        branches.clear();
        branches.addAll(newBranches);
        
        contexts.clear();
        contexts.addAll(newContexts);
      }
    }
  }
  
  /**
   * Evaluate the directive.  The directives are dispatched to handler
   * functions per type of directive.
   *
   * @param directive The directive to evaluate.
   */
  protected void evaluateDirective(Directive directive) {
    List<CSyntax> list;
    int s;
    boolean invalid;

    list = directive.getList();
    s = directive.getOffset();
    
    invalid = false;
    switch (directive.kind()) {
    case IF:
      ifDirective(list, s);
      if (STATISTICS) {
        Statistics.inc(Statistics.row.IF);
        Statistics.inc(Statistics.row.CONDITIONAL);
        Statistics.conditionalNesting().push(1);
        Statistics.max(Statistics.row.MAX_CONDITIONAL_DEPTH,
                       Statistics.conditionalNesting().size(),
                       directive.file + ":" + directive.line);
        if (Statistics.conditionalNesting().size() > 0) {
          Statistics.inc(Statistics.row.BRANCH_CONDITIONAL);
        }
      }
      break;
    case IFDEF:
      ifdefDirective(list, s);
      if (STATISTICS) {
        Statistics.inc(Statistics.row.IFDEF);
        Statistics.inc(Statistics.row.CONDITIONAL);
        Statistics.conditionalNesting().push(1);
        Statistics.max(Statistics.row.MAX_CONDITIONAL_DEPTH,
                       Statistics.conditionalNesting().size(),
                       directive.file + ":" + directive.line);
        if (Statistics.conditionalNesting().size() > 0) {
          Statistics.inc(Statistics.row.BRANCH_CONDITIONAL);
        }
      }
      break;
    case IFNDEF:
      ifndefDirective(list, s);
      if (STATISTICS) {
        Statistics.inc(Statistics.row.IFNDEF);
        Statistics.inc(Statistics.row.CONDITIONAL);
        Statistics.conditionalNesting().push(1);
        Statistics.max(Statistics.row.MAX_CONDITIONAL_DEPTH,
                       Statistics.conditionalNesting().size(),
                       directive.file + ":" + directive.line);
        if (Statistics.conditionalNesting().size() > 0) {
          Statistics.inc(Statistics.row.BRANCH_CONDITIONAL);
        }
      }
      break;
    case ELIF:
      elifDirective(list, s);

      if (STATISTICS) {
        Statistics.inc(Statistics.row.ELIF);
        Statistics.conditionalNesting()
          .push(Statistics.conditionalNesting().pop() + 1);
      }
      break;
    case ELSE:
      elseDirective(list, s);
      if (STATISTICS) {
        Statistics.inc(Statistics.row.ELSE);
        Statistics.conditionalNesting()
          .push(Statistics.conditionalNesting().pop() + 1);
      }
      break;
    case ENDIF:
      endifDirective(list, s);
      if (STATISTICS) {
        Statistics.inc(Statistics.row.ENDIF);
        Statistics.max(Statistics.row.MAX_CONDITIONAL_BREADTH,
                       Statistics.conditionalNesting().pop(),
                       directive.file + ":" + directive.line);
      }
      break;
    case INCLUDE:
      includeDirective(list, s, false);
      if (STATISTICS) {
        Statistics.inc(Statistics.row.INCLUDE);
        if (Statistics.conditionalNesting().size() > 0) {
          Statistics.inc(Statistics.row.BRANCH_INCLUDE);
        }
      }
      break;
    case INCLUDE_NEXT:
      includeDirective(list, s, true);
      if (STATISTICS) {
        Statistics.inc(Statistics.row.INCLUDE_NEXT);
        if (Statistics.conditionalNesting().size() > 0) {
          Statistics.inc(Statistics.row.BRANCH_INCLUDE);
        }
      }
      break;
    case DEFINE:
      defineDirective(list, s);
      if (STATISTICS) {
        Statistics.inc(Statistics.row.DEFINE);
        if (Statistics.conditionalNesting().size() > 0) {
          Statistics.inc(Statistics.row.BRANCH_DEFINE);
        }
      }
      break;
    case UNDEF:
      undefDirective(list, s);
      if (STATISTICS) {
        Statistics.inc(Statistics.row.UNDEF);
      }
      break;
    case LINE:
      lineDirective(list, s);
      if (STATISTICS) {
        Statistics.inc(Statistics.row.LINE);
      }
      break;
    case ERROR:
      errorDirective(list, s);
      if (STATISTICS) {
        Statistics.inc(Statistics.row.ERROR);
      }
      break;
    case WARNING:
      warningDirective(list, s);
      if (STATISTICS) {
        Statistics.inc(Statistics.row.WARNING);
      }
      break;
    case PRAGMA:
      pragmaDirective(list, s);
      if (STATISTICS) {
        Statistics.inc(Statistics.row.PRAGMA);
      }
      break;
    case LINEMARKER:
      // Ignore linemarkers for now.  TODO update the internal line
      // counter.
      break;
    default:
      invalid = true;
      break;
    }

    if (invalid && SHOW_ERRORS) {
      System.err.println("error: invalid preprocessor directive");
    }

    queue.offer(directive);
  }
  
  /**
   * Process if directive.  This takes the list of syntactic units
   * and the position of the first syntax after the directive name.
   * It passes the conditional expression to a function that
   * evaluates the expression.
   *
   * @param list The tokens of the directive.
   * @param s The number of tokens after the directive name.
   */
  protected void ifDirective(List<CSyntax> list, int s) {
    // Move past the whitespace after the directive name.
    while (s < list.size() && list.get(s).isLayout()) s++;
    
    if (s >= list.size()) {
      if (SHOW_ERRORS) {
        System.err.println("error: empty if directive");
      }
      return;
    }
    else {
      List<CSyntax> tokens;
      BDD bdd;

      tokens = new LinkedList<CSyntax>();
      while (s < list.size()) {
        CSyntax syntax;
        
        syntax = list.get(s);
        if (syntax.isToken()) {
          tokens.add(syntax);
        }
        
        s++;
      }

      bdd = expression(tokens);
      
      contextManager.push();
      contextManager.enter(bdd);
      
      queue.offer(new If(contextManager.reference()));
    }
  }
  
  /**
   * Take expression tokens and return an expanded, completed, parsed,
   * and evaluated expression as a BDD.
   *
   * @param tokens The tokens of the expression to evaluate.
   */
  protected BDD expression(List<CSyntax> tokens) {
    SyntaxContext scontext;
    List<CSyntax> expanded;
    List<LinkedList<Token>> completed;
    List<Context> contexts;
    List<BDD> terms;
    BDD newBdd;
    int saveExpanding;
    Context global;
    String detail;
    int expansionsStart;
    
    detail = null;
    expansionsStart = 0;
    if (SHOW_ERRORS) {
      detail = tokens.get(0).file + ":" + tokens.get(0).line;
      if (null != Statistics.table
          && Statistics.table.containsKey(Statistics.row.EXPANSION)) {
        expansionsStart = Statistics.table.get(Statistics.row.EXPANSION);
      }
    }
    
    tokens.add(Token.EOF);
    
    // Push a new token context to expand macros in the expression.
    scontext = new SyntaxContext(tokens);
    
    saveExpanding = expanding;
    expanding = 0;
    pushTokenContext(scontext);
    
    // Expand conditional expression macros.
    expanded = new LinkedList<CSyntax>();
    for (;;) {
      CSyntax syntax;
      
      syntax = scan();
      
      if (Token.EOF == syntax) {
        break;
      }
      
      expanded.add(syntax);
      
      if (syntax.isToken() && CLexer.getString(syntax).equals("defined")) {
        CSyntax s;
        // The number of tokens left to collect.
        int collect;
        List<CSyntax> defined;
        
        defined = new LinkedList<CSyntax>();
        s = null;
        collect = 1;
        for (;;) {
          // TODO fix fonda/macros/defined1.c
          s = tcontexts.peek().scan();
          
          if (Token.EOF == s) {
            break;
          }
          else if (CLexer.isType(s, sym.LPAREN)) {
            // Collect two more tokens, the macro and the rparen.
            collect = 2;
          }
          else if (CLexer.isType(s, sym.RPAREN)) {
            collect--;
          }
          else if (s.isConditional()) {
            System.err.println("NEED DEFINED OPERATOR HOISTING");
            System.exit(-1);
            collect--;
          }
          else if (s.isToken()) {
            collect--;
          }
          else if (s.isConditionalBlock()) {
            System.err.println("CONDITIONAL BLOCK IN DEFINED");
            System.exit(-1);
            collect--;
          }
          
          defined.add(s);
          
          if (collect == 0 || CLexer.isType(s, sym.RPAREN)) {
            break;
          }
        }
        
        expanded.addAll(defined);

        if (Token.EOF == s) {
          break;
        }
      }
    }
    
    tokens.clear();
    
    popTokenContext();
    expanding = saveExpanding;
    
    // Trim leading whitespace.
    while (expanded.size() > 0 && expanded.get(0).isLayout()) {
      expanded.remove(0);
    }
    
    // Collect conditionals into conditional blocks.
    global = contextManager.reference();
    expanded = buildBlocks(expanded, global);
    global.delRef();
    
    completed = new LinkedList<LinkedList<Token>>();
    contexts = new LinkedList<Context>();
    
    completed.add(new LinkedList<Token>());
    contexts.add(contextManager.reference());
    
    // Complete conditional expressions.
    hoistExpression(expanded, completed, contexts);
    
    // TODO dedup expressions (put on hold pending evaluation of need
    //for the optimization).
    
    expanded.clear();
    
    // Union of all terms, where Term = Context && CompletedExpression.
    terms = new LinkedList<BDD>();
    for (int i = 0; i < completed.size(); i++) {
      LinkedList<Token> tokenlist = completed.get(i);
      Context context = contexts.get(i);
      
      if (! context.isFalse()) {
        boolean unknown = false;
        StringBuilder string = new StringBuilder();
        BDD bdd;
        
        for (Token token : tokenlist) {
          if (! (token.hasFlag(flag.UNKNOWN_DEF))) {
            string.append(CLexer.getString(token));
            string.append(" ");
          }
          else {
            // Mark presence conditions containing unknown macro defs.
            unknown = true;
            break;
          }
        }

        if (! unknown) {
          bdd = evaluator.evaluate(string.toString());
          if (! bdd.isZero()) {
            terms.add(bdd.and(context.bdd()));
          }
          
          bdd.free();
        }
        else {
          // Assume expression is true if it contains an unknown defition.
          terms.add(context.bdd().id());
        }
      }

      context.delRef();
    }
    
    // Take union of each subexpression term.
    newBdd = contextManager.B.zero();
    for (BDD term : terms) {
      BDD bdd;
      
      bdd = newBdd.or(term);
      term.free();
      newBdd.free();
      newBdd = bdd;
    }
    
    if (STATISTICS) {
      Statistics.inc(Statistics.row.EXPRESSION);
      if (null != Statistics.table
          && Statistics.table.containsKey(Statistics.row.EXPANSION)) {
        Statistics.inc(Statistics.row.EXPRESSION_EXPANSION,
                       Statistics.table.get(Statistics.row.EXPANSION)
                       - expansionsStart);
      }
      if (terms.size() > 1) {
        Statistics.inc(Statistics.row.HOISTED_EXPRESSION);
        Statistics.max(Statistics.row.MAX_HOISTED_EXPRESSION,
                       terms.size(), detail);
      }
    }
    
    return newBdd;
  }
  
  /**
   * Complete an expression that contains syntax-breaking
   * conditionals.  The hoisted expressions are put in tokenlists
   * and their presence conditions in contexts.
   *
   * @param list The list of tokens of the expression.
   * @param tokenlists The hoisted expressions.
   * @param contexts The hoisted expressions' presence conditions.
   */
  protected void hoistExpression(List<? extends CSyntax> list,
                                 List<LinkedList<Token>> tokenlists,
                                 List<Context> contexts) {
    for (CSyntax s : list) {
      if (s.isToken()) {
        for (LinkedList<Token> tokenlist : tokenlists) {
          tokenlist.add((Token) s);
        }
      }
      else if (s.isConditionalBlock()) {
        ConditionalBlock block;
        List<LinkedList<Token>> newTokenlists;
        List<Context> newContexts;
        
        block = (ConditionalBlock) s;
        newTokenlists = new LinkedList<LinkedList<Token>>();
        newContexts = new LinkedList<Context>();
        for (int i = 0; i < block.contexts.size(); i++) {
          List<? extends CSyntax> branch;
          Context context;
          List<LinkedList<Token>> branchTokenlists;
          List<Context> branchContexts;
          
          branch = block.branches.get(i);
          context = block.contexts.get(i);

          branchTokenlists = new LinkedList<LinkedList<Token>>();
          branchContexts = new LinkedList<Context>();
          branchTokenlists.add(new LinkedList<Token>());
          branchContexts.add(context);
          context.addRef();
          
          hoistExpression(branch, branchTokenlists, branchContexts);
          
          // Combine strings and bdds with newStrings and newBdds.
          for (int a = 0; a < tokenlists.size(); a++) {
            for (int b = 0; b < branchTokenlists.size(); b++) {
              LinkedList<Token> tokenlist;
              Context newContext;
              
              tokenlist = new LinkedList<Token>();
              tokenlist.addAll(tokenlists.get(a));
              tokenlist.addAll(branchTokenlists.get(b));
              newContext = contexts.get(a).and(branchContexts.get(b));
              if (! newContext.isFalse()) {
                newTokenlists.add(tokenlist);
                newContexts.add(newContext);
              }
              else {
                newContext.delRef();
              }
            }
          }
          
          for (Context c : branchContexts) {
            c.delRef();
          }
        }
        
        tokenlists.clear();
        tokenlists.addAll(newTokenlists);

        for (Context c : contexts) {
          c.delRef();
        }
        contexts.clear();
        contexts.addAll(newContexts);
        
        block.free();
      }
    }
  }
  
  /**
   * Process ifdef directive.  This takes the list of syntactic units
   * and the position of the first syntax after the directive name.
   *
   * @param list The tokens of the directive.
   * @param s The number of tokens after the directive name.
   */
  protected void ifdefDirective(List<CSyntax> list, int s) {
    // Move past the whitespace after the directive name.
    while (s < list.size() && list.get(s).isLayout()) s++;
    
    if (s >= list.size()) {
      if (SHOW_ERRORS) {
        System.err.println("error: empty ifdef directive");
      }
      return;
    }
    else {
      String str;
      BDD bdd;
      
      if (list.get(s).isIdentifier() || list.get(s).isKeyword()) {
        // Valid macro name.
      }
      else {
        if (SHOW_ERRORS) {
          System.err.println("error: invalid macro name in ifdef");
        }
        return;
      }
      
      str = contextManager.vars().defVar(CLexer.getString(list.get(s)));
      
      bdd = evaluator.evaluate(str);
      
      contextManager.push();
      contextManager.enter(bdd);
      
      queue.offer(new If(contextManager.reference()));
    }
  }
  
  /**
   * Process ifndef directive.  This takes the list of syntactic units
   * and the position of the first syntax after the directive name.
   *
   * @param list The tokens of the directive.
   * @param s The number of tokens after the directive name.
   */
  protected void ifndefDirective(List<CSyntax> list, int s) {
    // Move past the whitespace after the directive name.
    while (s < list.size() && list.get(s).isLayout()) s++;
    
    if (s >= list.size()) {
      if (SHOW_ERRORS) {
        System.err.println("error: empty ifndef directive");
      }
      return;
    }
    else {
      String str;
      BDD bdd;
      
      if (list.get(s).isIdentifier() || list.get(s).isKeyword()) {
        // Valid macro name.
      }
      else {
        if (SHOW_ERRORS) {
          System.err.println("error: invalid macro name in ifdef");
        }
        return;
      }

      str = "! " + contextManager.vars().defVar(CLexer.getString(list.get(s)));
      
      bdd = evaluator.evaluate(str);

      contextManager.push();
      contextManager.enter(bdd);

      queue.offer(new If(contextManager.reference()));
    }
  }
  
  /**
   * Process elif directive.  This takes the list of syntactic units
   * and the position of the first syntax after the directive name.
   *
   * @param list The tokens of the directive.
   * @param s The number of tokens after the directive name.
   */
  protected void elifDirective(List<CSyntax> list, int s) {
    // Move past the whitespace after the directive name.
    while (s < list.size() && list.get(s).isLayout()) s++;
    
    if (s >= list.size()) {
      if (SHOW_ERRORS) {
        System.err.println("error: empty if directive");
      }
      return;
    }
    else {
      List<CSyntax> tokens;
      BDD bdd;

      tokens = new LinkedList<CSyntax>();
      while (s < list.size()) {
        CSyntax syntax;
        
        syntax = list.get(s);
        if (syntax.isToken()) {
          tokens.add(syntax);
        }
        
        s++;
      }
      
      contextManager.enterElse();
      
      bdd = expression(tokens);
      
      contextManager.enterElif(bdd);

      queue.offer(new Elif(contextManager.reference()));
    }
  }
  
  /**
   * Process else directive.  This takes the list of syntactic units
   * and the position of the first syntax after the directive name.
   *
   * @param list The tokens of the directive.
   * @param s The number of tokens after the directive name.
   */
  protected void elseDirective(List<CSyntax> list, int s) {
    contextManager.enterElse();
    queue.offer(new Elif(contextManager.reference()));
  }
  
  /**
   * Process endif directive.  This takes the list of syntactic units
   * and the position of the first syntax after the directive name.
   *
   * @param list The tokens of the directive.
   * @param s The number of tokens after the directive name.
   */
  protected void endifDirective(List<CSyntax> list, int s) {
    try {
      contextManager.pop();
    }
    catch (Exception e) {
      System.err.println("EXTRA ENDIF");
      System.err.println(fileManager.include.getName());
      System.err.println(list.get(0));
      e.printStackTrace();
      System.exit(-1);
    }
    queue.offer(new EndConditional());
  }
  
  /**
   * Process include directive.  This takes the list of syntactic units
   * and the position of the first syntax after the directive name.
   * Computed macros are evaluated.  If the macro is multiply-defined,
   * we generate multiple includes that are wrapped in conditional
   * objects.  The preprocessor needs 
   *
   * @param list The tokens of the directive.
   * @param s The number of tokens after the directive name.
   * @param includeNext Whether the include directive was an
   * #include_next directive.
   */
  protected void includeDirective(List<CSyntax> list, int s,
                                  boolean includeNext) {
    StringBuilder sb;
    String str;
    LinkedList<CSyntax> tokens;
    
    // Move past the whitespace after the directive name.
    while (s < list.size() && list.get(s).isLayout()) s++;
    
    sb = new StringBuilder();
    
    // Combine all tokens before next whitespace.
    tokens = new LinkedList<CSyntax>();
    while (s < list.size() && (! list.get(s).isLayout())) {
      sb.append(CLexer.getString(list.get(s)));
      tokens.add(list.get(s));
      s++;
    }
    
    while (s < list.size()) {
      tokens.add(list.get(s));
      s++;
    }
    
    while (tokens.getLast().isLayout()) {
      tokens.removeLast();
    }
    
    str = sb.toString();
    
    if (str.length() == 0) {
      if (SHOW_ERRORS) {
        System.err.println("error: empty include directive");
      }
    }
    else {
      char first, last;
      String headerName;
      boolean sysHeader;
      CSyntax delimiter;
      
      first = str.charAt(0);
      last = str.charAt(str.length() - 1);
      
      for (;;) {
        sysHeader = false;
        if ('<' == first && '>' == last) {
          // System header.
          sysHeader = true;
        }
        else if ('"' == first && '"' == last) {
          // User header.
        }
        else {
          // Computed header.
          List<CSyntax> computed, blocks;
          SyntaxContext sc;
          List<LinkedList<Token>> completed;
          List<String> completedStrings;
          List<Context> contexts;
          Context global;
          
          tokens.add(Token.EOF);
          sc = new SyntaxContext(tokens);
          pushTokenContext(sc);
          
          computed = new LinkedList<CSyntax>();
          for (;;) {
            CSyntax syntax;
            
            syntax = scan();
            
            if (CLexer.isType(syntax, sym.EOF)) {
              break;
            }
            
            computed.add(syntax);
          }
          
          popTokenContext();
          
          global = contextManager.reference();
          // Build conditional blocks.
          blocks = buildBlocks(computed, global);
          global.delRef();
          
          completed = new LinkedList<LinkedList<Token>>();
          contexts = new LinkedList<Context>();
          
          completed.add(new LinkedList<Token>());
          contexts.add(contextManager.reference());
          
          // Make all combinations.
          hoistExpression(blocks, completed, contexts);
          
          // Build strings and trim those using macros with unknown
          // definitions.
          completedStrings = new LinkedList<String>();
          for (int i = 0; i < completed.size(); i++) {
            LinkedList<Token> tokenlist = completed.get(i);
            Context context = contexts.get(i);
            StringBuilder string = new StringBuilder();
            boolean unknown = false;
            
            for (Token token : tokenlist) {
              if (! (token.hasFlag(flag.UNKNOWN_DEF))) {
                string.append(CLexer.getString(token));
              }
              else {
                // Mark those containing unknown definitions.
                unknown = true;
                string.delete(0, string.length());
                string.append(CLexer.getString(token));
                break;
              }
            }
            
            if (! unknown) {
              completedStrings.add(string.toString());
            }
            else {
              if (SHOW_ERRORS) {
                System.err.println("warning: computed header used unknown " +
                                   "definition(s): " + string.toString());
              }
              completed.remove(i);
              context.delRef();
              contexts.remove(i);
              i--;
            }
          }
          
          delimiter =
            fileManager.includeComputedHeader(completedStrings, contexts,
                                              includeNext, contextManager,
                                              macroTable);
  
          queue.offer(delimiter);
          
          break;
        }
        
        headerName = str.substring(1, str.length() - 1);
        delimiter
          = fileManager.includeHeader(headerName, sysHeader, includeNext,
                                      contextManager, macroTable);
        
        if (null != delimiter) {
          queue.offer(delimiter);
          ss.push(contextManager.stack.size());
        }
        
        break;
      }
    }
  }
  
  /**
   * Process define directive.  This takes the list of syntactic units
   * and the position of the first syntax after the directive name.
   * This function parses the macro, determining whether its function-
   * or object-like and adds a new table entry given the current context.
   *
   * @param list The tokens of the directive.
   * @param s The number of tokens after the directive name.
   */
  protected void defineDirective(List<CSyntax> list, int s) {
    // Move past the whitespace after the directive name.
    while (s < list.size() && list.get(s).isLayout()) s++;

    if (s >= list.size()) {
      if (SHOW_ERRORS) {
        System.err.println("error: empty define directive");
      }
      return;
    }
    else if (! (list.get(s).isIdentifier() || list.get(s).isKeyword())) {
      if (SHOW_ERRORS) {
        System.err.println("error: defining a non-identifier token");
      }
    }
    else {
      String name;
      List<String> formals;
      LinkedList<Token> definition;
      boolean isFunctionlike;
      Macro macro;
      Context context;
      String variadic;
      
      name = CLexer.getString(list.get(s));
      formals = null;
      definition = null;
      variadic = null;
      
      // Move past the macro name.
      s++;
      isFunctionlike = false;
      if (s < list.size()) {
        
        // Check if macro is function-like.  If so, we need to parse
        // the macros formal arguments.
        
        if (CLexer.isType(list.get(s), sym.LPAREN)) {
          // Move past paren.
          s++;
          
          do {
            // Move past whitespace.
            while (s < list.size() && list.get(s).isLayout()) s++;
            
            if (list.get(s).isIdentifier() || list.get(s).isKeyword()) {
              // We are on a formal argument name.

              if (formals == null) {
                formals = new LinkedList<String>();
              }
              
              // Check for named variadic.
              if (s < (list.size() - 1)
                  && CLexer.isType(list.get(s + 1), sym.ELLIPSIS)) {
                if (null != variadic) {
                  if (SHOW_ERRORS) {
                    System.err.println("error: no args allowed after " +
                                       "variadic");
                  }
                  return;
                }
                variadic = CLexer.getString(list.get(s));
                s++;
              }
              else {
                formals.add(CLexer.getString(list.get(s)));
              }

            } else if (CLexer.isType(list.get(s), sym.ELLIPSIS)) {
              // The formal argument is variadic.
              if (null != variadic) {
                if (SHOW_ERRORS) {
                  System.err.println("error: no args allowed after variadic");
                }
                return;
              }

              // The default name of the variadic argument.
              variadic = "__VA_ARGS__";

            } else if (CLexer.isType(list.get(s), sym.RPAREN)
                     && null == formals) {
              // Function-like macro with no arguments.  Done looking
              // for formals.
              s++;
              break;
            } else {
              if (SHOW_ERRORS) {
                System.err.println("error: parameter name missing");
              }
              return;
            }
            
            s++;
            
            //move past whitespace
            while (s < list.size() && list.get(s).isLayout()) s++;

            if (s >= list.size()) {
              if (SHOW_ERRORS) {
                System.err.println("error: missing end parenthesis");
              }
              return;
            }
            
            if (CLexer.isType(list.get(s), sym.COMMA)) {
              // Comma-separated formal arguments.
              s++;

            } else if (CLexer.isType(list.get(s), sym.RPAREN)) {
              // Done looking for formals.
              s++;
              break;

            } else {
              if (SHOW_ERRORS) {
                System.err.println("error: missing end parenthesis or comma");
              }
              return;
            }
          } while (true);

          isFunctionlike = true;
        }
        
        // Move past the whitespace after the macro name.
        while (s < list.size() && list.get(s).isLayout()) s++;
        
        if (s >= list.size() ) {
          // Empty macro.
        }
        else {
          // Read in the macro definition, checking token-paste and
          // stringify operations.  The operators are removed and
          // instead the operands of pasting and stringification are
          // flagged as such.
          boolean followingPasteOp = false;
          boolean followingStringify = false;
          boolean prevWhite = false;
          final String pasteError
            = "'##' cannot appear at either end of a macro expansion";
          
          do {
            CSyntax syntax;
            
            syntax = list.get(s);
            
            if (isFunctionlike && CLexer.isType(syntax, sym.HASH)) {
              // Stringifification operator.
              CSyntax next;
              int ss;
              boolean valid;
              
              // Stringification can only be done on macro arguments.
              // The following code checks for that.
              ss = s + 1;
              valid = false;
              while (ss < list.size()) {
                next = list.get(ss);
                if (next.isToken()) {
                  if (null != formals
                      && formals.contains(CLexer.getString(next))) {
                    valid = true;
                  }
                  else if (null != variadic
                           && CLexer.getString(next).equals(variadic)) {
                    valid = true;
                  }
                  break;
                }
                ss++;
              }
              
              if (! valid) {
                if (SHOW_ERRORS) {
                  System.err.println("'#' is not followed by a macro " +
                                     "parameter");
                }
              }

            } else if (CLexer.isType(syntax, sym.DHASH)) {
              // Token-paste operator.

              // The token-paste operator is binary, so it can't
              // be the first token of the definition.
              if (null == definition) {
                if (SHOW_ERRORS) {
                  System.err.println(pasteError);
                }
                return;
              }

              // Flag the previous token as the left operand of a
              // token-pasting.
              definition.getLast().setFlag(flag.PASTE_LEFT);
              
            } else if (syntax.isToken()) {
              // A regular token.
              Token token;
              
              token = (Token) syntax;
              
              if (null == definition) {
                definition = new LinkedList<Token>();
              }
              
              if (prevWhite) {
                // Flat the token as having whitespace before it.
                // Whitespace tokens are removed from macro
                // definitions.
                token.setFlag(flag.PREV_WHITE);
              }
              
              if (followingStringify) {
                // Flag the token as a stringification argument.
                token.setFlag(flag.STRINGIFY_ARG);
              }
              
              definition.add(token);
              
            } else if (syntax.isLayout() && (! followingStringify)) {
              // Whitespace.
              if (null != definition) {
                prevWhite = true;
              }
            }
            
            if (syntax.isToken()) {
              followingPasteOp = CLexer.isType(syntax, sym.DHASH);
              followingStringify = CLexer.isType(syntax, sym.HASH);
              
              if (! followingStringify) {
                // If the stringification operator has whitespace
                // before it, flag the stringification argument
                // instead, since we remove the operator.
                prevWhite = false;
              }
            }
            
            s++;
          } while (s < list.size());
          
          if (followingPasteOp) {
            // The token-pasting operator can't appear at the end of a
            // definition since it's a binary operator.
            if (SHOW_ERRORS) {
              System.err.println(pasteError);
            }
            return;
          }
        }
      }
      
      // Create and store the macro definitions in the macro symbol
      // table.

      if (isFunctionlike) {
        macro = new Macro.Function(formals, definition, variadic);
      }
      else {
        macro = new Macro.Object(definition);
      }
      
      macroTable.define(name, macro, contextManager);
    }
  }
  
  /**
   * Process undef directive.  This takes the list of syntactic units
   * and the position of the first syntax after the directive name.
   *
   * @param list The tokens of the directive.
   * @param s The number of tokens after the directive name.
   */
  protected void undefDirective(List<CSyntax> list, int s) {
    // Move past the whitespace after the directive name.
    while (s < list.size() && list.get(s).isLayout()) s++;
    
    if (s >= list.size()) {
      if (SHOW_ERRORS) {
        System.err.println("error: empty undef directive");
      }
      return;

    } else {
      String name;
      Context context;
      
      name = CLexer.getString(list.get(s));

      // TODO should probably check that name is an identifier or
      // keyword.
      
      macroTable.undefine(name, contextManager);
    }
  }
  
  /**
   * Process line directive.  This takes the list of syntactic units
   * and the position of the first syntax after the directive name.
   *
   * @param list The tokens of the directive.
   * @param s The number of tokens after the directive name.
   */
  protected void lineDirective(List<CSyntax> list, int s) {
    // TODO implement the line directive so it updates the internal
    // line counter and filename.  This could also interact with
    // conditionals, so we would need an interal filename and line
    // counter for each differeing presence condition.
  }
  
  /**
   * Process error directive.  This takes the list of syntactic units
   * and the position of the first syntax after the directive name.
   *
   * @param list The tokens of the directive.
   * @param s The number of tokens after the directive name.
   */
  protected void errorDirective(List<CSyntax> list, int s) {
    // Create a new internal error object.  TODO indicate the source
    // of the error (preprocessor/parser) in the Error object.
    queue.offer(new CSyntax.Error());
  }
  
  /**
   * Process warning directive.  This takes the list of syntactic units
   * and the position of the first syntax after the directive name.
   *
   * @param list The tokens of the directive.
   * @param s The number of tokens after the directive name.
   */
  protected void warningDirective(List<CSyntax> list, int s) {
    // TODO output the warning message when SHOW_ERRORS is on.
  }
  
  /**
   * Process pragma directive.  This takes the list of syntactic units
   * and the position of the first syntax after the directive name.
   *
   * @param list The tokens of the directive.
   * @param s The number of tokens after the directive name.
   */
  protected void pragmaDirective(List<CSyntax> list, int s) {
    // Pragma directives are compiler dependent.  TODO implement gcc's
    // pragma once by exploiting the existing guard macro
    // implementation.
  }
  
  /**
   * Check a token to see if it's a defined macro and expand if necessary.
   * Multiply-defined macros are expanded to all definitions, but
   * wrapped with conditionals.  The Preprocessor must check for conditional
   * objects to update the context and also to normalize token-pasting
   * and stringification that involve conditionals
   *
   * @param token The token to try to expand.
   */
  protected void token(Token token) {
    for (;;) {
      String name;
      
      if (! (token.isIdentifier() || token.isKeyword())) {
        // Macro names can only be identifiers.  C keywords are
        // special identifiers.
        break;
      }
      
      name = CLexer.getString(token);
      
      if (name.equals("__FILE__")) {
        // Emit the current filename.
        List<CSyntax> list;
        
        list = new LinkedList<CSyntax>();
        list.add(new Literal(sym.STRINGliteral, "\"" +
                             fileManager.getFilename() + "\"", -1, -1, -1));
        
        pushTokenContext(new SingleMacro(name, list, new ObjectMacro(token)));
        
        return;
      } else if (name.equals("__LINE__")) {
        // Emit the current line number.
        List<CSyntax> list;
        
        list = new LinkedList<CSyntax>();
        list.add(new Literal(sym.INTEGERconstant,
                             ((Integer) fileManager.getLine()).toString(),
                             -1, -1, -1));
        
        pushTokenContext(new SingleMacro(name, list, new ObjectMacro(token)));
        
        return;
      }

      if (STATISTICS && name.startsWith("CONFIG_")) {
        Statistics.configInText(name);
      }
      
      if ((! macroTable.contains(name)) || token.hasFlag(flag.NO_EXPAND)) {
        // Don't expand macros flagged as such.
        break;
      }
    
      if (macroTable.isEnabled(name)) {
        List<Entry> entries;
        boolean hasDefinition;
        boolean hasFunction;
        
        entries = macroTable.get(name, contextManager);
        
        hasDefinition = false;
        hasFunction = false;
        for (Entry e : entries) {
          switch (e.macro.state) {
            case DEFINED:
              hasDefinition = true;
              break;
          }
          
          if (e.macro.isFunction()) {
            hasFunction = true;
          }
        }
  
        if (hasDefinition) {
          // Expand the macro to it's definition and push a new token
          // context for the preprocessor to pull tokens from.  This
          // is to preprocess the macro expansion.
          MacroContext macroContext;
          
          if (hasFunction) {
            // Function-like macro.  At least one definition of the
            // macro is function-like.  First we hoist the
            // function-like macro invocation around conditionals that
            // interfere with the syntax.  Then we expand each hoisted
            // invocation.
            
            if (token.hasFlag(flag.HOISTED_FUNCTION)) {
              macroContext = funlikeInvocation(token, entries);
            } else {
              hoistFunction(token);
              macroContext = null;
            }
            
            if (null == macroContext) {  //not a funlike invocation, no paren
              return;
            }
            
            if (STATISTICS) {
              Statistics.inc(Statistics.row.FUNCTION);
            }

          } else {
            // Object-like macro.
            boolean needConditional;
            
            needConditional = true;
            if (entries.size() == 1) {
              Context context;
              Context and;
              
              context = contextManager.reference();
              and = context.and(entries.get(0).context);
              context.delRef();
        
              needConditional = ! contextManager.is(and);
              and.delRef();
            }
            
            if (entries.size() == 1
                && contextManager.is(entries.get(0).context)) {
              // Don't bother output conditionals when there is only
              // one possible macro definition in the current presence
              // condition.

              if (token.hasFlag(flag.PREV_WHITE)) {
                queue.offer(Layout.SPACE);
              }

              macroContext
                = new SingleMacro(name,
                                  entries.get(0).macro.definition,
                                  new ObjectMacro(token));
              macroTable.free(entries);
              
            } else {
              // Expand all possible macro definitions in the current
              // presence condition.
              List<CSyntax> original;
              List<List<? extends CSyntax>> lists;
              List<Context> contexts;
              
              original = new LinkedList<CSyntax>();
              original.add(token);

              lists = new LinkedList<List<? extends CSyntax>>();
              contexts = new LinkedList<Context>();
              for (Entry e : entries) {
                contexts.add(e.context);
                
                switch (e.macro.state) {
                  case DEFINED:
                    lists.add(e.macro.definition);
                    break;
                  case UNDEFINED:
                  case FREE:
                    List<CSyntax> replacement;
                    
                    replacement = new LinkedList<CSyntax>();
                    replacement.add(token);
                    lists.add(replacement);
                  break;
                }
              }
              
              macroContext = new MultipleMacro(name, lists, contexts,
                                               new ObjectMacro(token));
            }
            
            if (STATISTICS) {
              Statistics.inc(Statistics.row.OBJECT);
            }
          }
          
          if (STATISTICS) {
            int defs;
            int alldefs;
            int nested;
            String detail;
            
            detail = CLexer.getString(token) + ":" + token.file + ":"
              + token.line;

            Statistics.inc(Statistics.row.EXPANSION);
            if (Statistics.conditionalNesting().size() > 0) {
              Statistics.inc(Statistics.row.BRANCH_EXPANSION);
            }
            defs = 0;
            for (Entry e : entries) {
              switch (e.macro.state) {
                case DEFINED:
                  defs++;
                  break;
              }
            }
            
            Statistics.max(Statistics.row.MAX_EXPANDED_DEFINITIONS, defs,
                           detail);
            
            alldefs = 0;
            for (Entry e : macroTable.table.get(name)) {
              switch (e.macro.state) {
                case DEFINED:
                  alldefs++;
                  break;
              }
            }
            
            Statistics.inc(Statistics.row.TRIMMED_DEFINITIONS, alldefs - defs);
            
            nested = 0;
            for (TokenContext tc : tcontexts) {
              if (tc.isMacroContext()) {
                nested++;
              }
            }
            
            if (nested > 0) {
              Statistics.inc(Statistics.row.EXPANSION_NESTED);
              Statistics.max(Statistics.row.MAX_EXPANSION_NESTED_DEPTH,
                             nested, detail);
            }
          }

          pushTokenContext(macroContext);
          
          return;
        }
        
      } else {
        // The token is not a macro.
        token = (Token) token.copy();
        token.setFlag(flag.NO_EXPAND);
      }
      
      break;
    }
    
    queue.offer(token);
  }

  /**
   * This class recognizes a function-like macro invocation in a given
   * configuration and reports when the invocation needs to fork.
   */
  protected static class Invocation {
    Context startContext;

    /**
     * The state of the parser.
     * -3 means it hasn't start parsing the argument list
     * -2 means invalid invocation (no starting paren)
     * -1 means finished parsing the invocation (seen last paren)
     * 0 means it has read the right paren of the arg list,
     *   but there are no nested parens
     * >0 is the nesting depth of parentheses
     */
    int state;
    
    public Invocation(Context startContext) {
      this.startContext = startContext;
      this.state = -3;
    }
    
    public void parse(ConditionalSyntax csyntax) {
      CSyntax syntax;
      Context context;
      Context and;
      
      if (done()) return;
      
      syntax = csyntax.syntax;
      context = csyntax.context;
      
      if ((syntax.isDirective() && ((Directive) syntax).kind()
           == Directive.Kind.WARNING) || syntax.isInclude()) {
        state = -2;
        return;
      }
      
      if (! syntax.isToken()) {
        return;
      }
      
      and = startContext.and(context);
      
      if (and.isFalse()) {
        and.delRef();
        return;
      }
      else {
        and.delRef();
      }
      
      if (-3 == state) {
        // Haven't started parsing the parameter list.

        if (syntax.isToken()) {
          if (CLexer.isType(syntax, sym.LPAREN)) {
            // Start parsing the argument list.
            state = 0;
          }
          else {
            // The first token was not an open parenthesis, so it's
            // not a valid function-like macro invocation.  (It may
            // still be an object-like invocation though.)
            state = -2;
          }
        }

      } else {
        // We are in the middle of parsing an argument list.

        // Track the nesting depth of parentheses.
        if (syntax.isToken()) {
          switch (((Token) syntax).type) {
            case LPAREN:
              state++;
              break;
            case RPAREN:
              state--;
              break;
          }
        }
      }
    }
    
    /**
     * Done when we have finished parsing or seen an invalid
     * function-like macro invocation invocation.
     */
    public boolean done() {
      return -1 == state || -2 == state;
    }
    
    public boolean invalid() {
      return -2 == state;
    }
    
    public boolean started() {
      return state > -2;
    }
    
    /**
     * Indicates that we need to hoist conditionals around the
     * function-like macro invocation.  Hoisting is necessary when a
     * parenthesis or comma is inside a condition.  Specifically,
     * there are three situations when hoisting is necessary: (1) the
     * starting paren has a different presence condition; (2) a comma
     * in state 0 has a different presence condition; and (3) the
     * ending paren has a different presence condition.
     *
     * @param csyntax The (possibly compound) token and it's presence
     * condition.
     * @return true when the preprocessor needs to hoist conditionals
     * around the function-like macro invocation.
     */
    public boolean needFork(ConditionalSyntax csyntax) {
      CSyntax syntax;
      Context context;
      Context and;
      
      //need to fork in the following cases
      
      syntax = csyntax.syntax;
      context = csyntax.context;
      
      if (syntax.isConditional()) return false;
      
      if (! syntax.isToken()) return false;

      if (startContext.is(context)) return false;

      and = startContext.and(context);
      
      if (and.isFalse()) {
        and.delRef();
        return false;
      }
      else {
        and.delRef();
      }
      
      if (null != syntax) {
        if (syntax.isToken()) {
          switch (((Token) syntax).type) {
            case LPAREN:
            case COMMA:
              return 0 == state;
            case RPAREN:
              return -1 == state;
          }
        }
      }
      
      return false;
    }
  }
  
  /** An object containing a token and it's presence condition. */
  protected static class ConditionalSyntax {
    public final CSyntax syntax;
    public final Context context;
    
    public ConditionalSyntax(CSyntax syntax, Context context) {
      this.syntax = syntax;
      this.context = context;
    }

    public String toString() {
      return syntax.toString();
    }
  }
  
  /**
   * Parse a function-like macro invocation and hoist conditionals
   * around it if necessary.  This function pulls the tokens of the
   * invocation directly from the preprocessor's token-stream via the
   * scan() function.
   *
   * @param token The token containing the macro name.
   */
  protected void hoistFunction(Token token) {
    CSyntax syntax;
    Token flagged;
    List<Invocation> invocations;
    List<ConditionalSyntax> buffer;
    Context union;
    List<CSyntax> hoisted;
    ContextManager savedManager;
    boolean needConditional;
    CSyntax eofOrInclude;
    
    invocations = new LinkedList<Invocation>();
    invocations.add(new Invocation(contextManager.reference()));

    buffer = new LinkedList<ConditionalSyntax>();

    // Copy the contextManager.  This is necessary to back out of the
    // changes in presence condition due to conditionals inside of the
    // function-like macro invocation.
    savedManager = contextManager;
    contextManager = new ContextManager(contextManager);
    
    // Turn of macro expansion.  This is done because the preprocessor
    // needs to first read and parse the raw, unexpanded tokens of the
    // function-like macro invocation.
    expansionOff();
    isHoistingFunction = true;

    eofOrInclude = null;
    for (;;) {
      // This is the main loop that parsing the function-like macro
      // invocation and checks for conditionals.
      ConditionalSyntax csyntax;
      Context context;
      boolean done;

      syntax = scan();

      if (STATISTICS) {
        if (syntax.isDirective()) {
          Directive directive;
          
          directive = (Directive) syntax;
          switch (directive.kind()) {
            case IF:
              if (STATISTICS) {
                Statistics.inc(Statistics.row.PRESCAN_CONDITIONAL);
              }
              break;
            case IFDEF:
              if (STATISTICS) {
                Statistics.inc(Statistics.row.PRESCAN_CONDITIONAL);
              }
              break;
            case IFNDEF:
              if (STATISTICS) {
                Statistics.inc(Statistics.row.PRESCAN_CONDITIONAL);
              }
              break;
            case ELIF:
              if (STATISTICS) {
                Statistics.inc(Statistics.row.PRESCAN_CONDITIONAL);
              }
              break;
            case ELSE:
              if (STATISTICS) {
                Statistics.inc(Statistics.row.PRESCAN_CONDITIONAL);
              }
              break;
            case ENDIF:
              if (STATISTICS) {
                Statistics.inc(Statistics.row.PRESCAN_CONDITIONAL);
              }
              break;
            case INCLUDE:
              // Not allowed in function-like macro invocations.
              break;
            case INCLUDE_NEXT:
              // Not allowed in function-like macro invocations.
              break;
            case DEFINE:
              if (STATISTICS) {
                Statistics.inc(Statistics.row.PRESCAN_DEFINE);
              }
              break;
            case UNDEF:
              if (STATISTICS) {
                Statistics.inc(Statistics.row.PRESCAN_UNDEF);
              }
              break;
            case LINE:
              if (STATISTICS) {
                Statistics.inc(Statistics.row.PRESCAN_DIRECTIVE);
              }
              break;
            case ERROR:
              if (STATISTICS) {
                Statistics.inc(Statistics.row.PRESCAN_DIRECTIVE);
              }
              break;
            case WARNING:
              if (STATISTICS) {
                Statistics.inc(Statistics.row.PRESCAN_DIRECTIVE);
              }
              break;
            case PRAGMA:
              if (STATISTICS) {
                Statistics.inc(Statistics.row.PRESCAN_DIRECTIVE);
              }
              break;
            case LINEMARKER:
              if (STATISTICS) {
                Statistics.inc(Statistics.row.PRESCAN_DIRECTIVE);
              }
              break;
            default:
              break;
          }
        }
      }

      context = contextManager.reference();

      // Only certain tokens are allowed in function-like macro
      // invocations.  The following are allowed: regular tokens,
      // layout, defines, undefines, errors, warnings, and
      // conditionals.  The following tests reflects this, except for
      // conditionals.  The main preprocessor "scan()" method updates
      // the presence condition given conditionals as usual, even
      // though expansion is turned off with expansionOff().
      if (syntax.isToken() || syntax.isLayout()
          || (syntax.isDirective()
              && (((Directive) syntax).kind() == Directive.Kind.DEFINE
                  || ((Directive) syntax).kind() == Directive.Kind.UNDEF
                  || ((Directive) syntax).kind() == Directive.Kind.INCLUDE
                  || ((Directive) syntax).kind() == Directive.Kind.INCLUDE_NEXT
                  || ((Directive) syntax).kind() == Directive.Kind.ERROR
                  || ((Directive) syntax).kind() == Directive.Kind.WARNING))) {

        // Parse the regular tokens of the function-like macro
        // invocation.

        if (! context.isFalse()) {
          csyntax = new ConditionalSyntax(syntax, context);
          
          buffer.add(csyntax);
          
          for (Invocation inv : invocations) {
            inv.parse(csyntax);
          }
          
          for (int i = 0; i < invocations.size(); i++) {
            Invocation inv;
            
            inv = invocations.get(i);
            
            if (inv.needFork(csyntax)) {
              List<Invocation> forked;
              
              forked = forkInvocation(inv, csyntax.context, buffer);
              invocations.addAll(i + 1, forked);
              i = i + forked.size();
            }
          }

        } else {
          context.delRef();
        }

      } else {
        // Pass through any tokens that aren't regular language
        // tokens.
        buffer.add(new ConditionalSyntax(syntax, null));
      }
      
      done = true;
      for (Invocation inv : invocations) {
        if (! inv.done()) {
          done = false;
          break;
        }
      }
      
      if (done) {
        break;
      }
      
      if (CLexer.isType(syntax, sym.EOF)) {
        // Include directives are not permitted in function-like macro
        // invocations.  Stop parsing once we see one or an EOF.  Both
        // signal an incomplete invocation.  The token is saved so it
        // can be processed by the preprocessor. (Right now expansion
        // is off.)
        eofOrInclude = syntax;
        break;
      }
    }

    isHoistingFunction = false;
    expansionOn();
    
    // TODO optimization: detect invalid function invocations
    // (e.g. due to invalid number of arguments) and remove them from
    // the list of invocations so that the preprocessor doesn't have
    // to bother processing them and throwing an error.
    
    hoisted = new LinkedList<CSyntax>();
    
    flagged = (Token) token.copy();
    flagged.setFlag(flag.HOISTED_FUNCTION);
    
    if (invocations.size() == 1
        && savedManager.is(invocations.get(0).startContext)) {
      // If there was only one invocation, no need to hoist
      // conditionals.
      needConditional = false;

    } else {
      // Need to hoist conditionals around the invocation.
      needConditional = true;
    }

    // Hoist conditionals around the function-like macro invocations.
    for (Invocation inv : invocations) {
      Context last;
      
      if (needConditional) {
        if (invocations.get(0) == inv) {
          hoisted.add(new If(inv.startContext));
          
        } else {
          hoisted.add(new Elif(inv.startContext));
        }
      }

      hoisted.add(flagged);
      last = null;
      for (ConditionalSyntax cs : buffer) {
        CSyntax s;
        Context c;
        
        s = cs.syntax;
        c = cs.context;
        
        if (s.isToken() || s.isLayout()
            || (s.isDirective()
                && (((Directive) s).kind() == Directive.Kind.DEFINE
                    || ((Directive) s).kind() == Directive.Kind.UNDEF
                    || ((Directive) s).kind() == Directive.Kind.INCLUDE
                    || ((Directive) s).kind() == Directive.Kind.INCLUDE_NEXT
                    || ((Directive) s).kind() == Directive.Kind.ERROR
                    || ((Directive) s).kind() == Directive.Kind.WARNING))
            ) {
          Context and;

          and = inv.startContext.and(c);
          if (! and.isFalse()) {
            if (null != last && ! last.is(c)) {
              hoisted.add(new EndConditional());
              last = null;
            }
            
            if (! inv.startContext.is(and)) {
              if (null == last) {
                hoisted.add(new If(c));
                last = c;
              }
            }
            hoisted.add(s);
          }
          and.delRef();
        }
      }

      if (null != last) {
        hoisted.add(new EndConditional());
      }
    }
    
    if (needConditional && invocations.size() > 0) {
      hoisted.add(new EndConditional());
    }

    // Save the tokens that weren't read in, but weren't part of a
    // function-like macro invocation.
    union = contextManager.new Context(false);
    for (Invocation inv : invocations) {
      Context tmp;
      
      tmp = union.or(inv.startContext);
      union.delRef();
      union = tmp;
    }

    Context current;
    
    current = null;
    for (ConditionalSyntax cs : buffer) {
      CSyntax s;
      Context c;
      
      s = cs.syntax;
      c = cs.context;
      
      if (s.isToken() || s.isLayout()
          || (s.isDirective()
              && (((Directive) s).kind() == Directive.Kind.DEFINE
                  || ((Directive) s).kind() == Directive.Kind.UNDEF
                  || ((Directive) s).kind() == Directive.Kind.INCLUDE
                  || ((Directive) s).kind() == Directive.Kind.INCLUDE_NEXT
                  || ((Directive) s).kind() == Directive.Kind.ERROR
                  || ((Directive) s).kind() == Directive.Kind.WARNING))) {
        Context tmp;
        
        tmp = c.andNot(union);
        
        if (! tmp.isFalse()) {
          if (null == current) {
            hoisted.add(new If(tmp));
            current = tmp;
            
          } else if (! current.is(tmp)) {
            hoisted.add(new EndConditional());
            hoisted.add(new If(tmp));
            current = tmp;
          }
          
          hoisted.add(s);
          
        } else {
          tmp.delRef();
        }
        
        c.delRef();
        
      } else {
        if (null != current) {
          hoisted.add(new EndConditional());
          current = null;
        }
        hoisted.add(s);
      }
    }
    
    union.delRef();
    
    if (null != current) {
      hoisted.add(new EndConditional());
    }
    
    if (null != eofOrInclude) {
      hoisted.add(eofOrInclude);
    }
    
    queue.offer(Layout.EMPTY);
    
    // Restore the contextManager.
    contextManager.free();
    contextManager = savedManager;

    // Make sure the hoisting produces matching conditionals.
    /*int nesting = 0;

    for (CSyntax cs : hoisted) {
      if (cs.isIf()) {
        nesting++;

      } else if (cs.isEndConditional()) {
        nesting--;
      }
    }

    if (nesting != 0) {
      System.err.println("error: hoisting produced mismatched conditionals.");
      System.exit(1);
      }*/

    pushTokenContext(new SyntaxContext(hoisted));

    if (STATISTICS) {
      int numhoisted;
      String detail;
      
      detail = CLexer.getString(token) + ":" + token.file + ":" + token.line;
      
      numhoisted = 0;
      for (Invocation inv : invocations) {
        if (inv.done()) {
          numhoisted++;
        }
      }
      
      if (numhoisted > 1) {
        Statistics.inc(Statistics.row.HOISTED_FUNCTION);
        Statistics.max(Statistics.row.MAX_HOISTED_FUNCTION,
                       numhoisted, detail);
      }
    }
  }

  /**
   * Fork a function-like macro invocation.
   *
   * @param inv the current invocation.
   * @param context The presence condition of the invocation.
   * @param list The tokens of the invocations so far.
   * @return the resulting forked invocations.
   */
  protected static List<Invocation> forkInvocation(Invocation inv,
                                                   Context context,
                                                   List<ConditionalSyntax>
                                                   list) {
    Context new1, new2;
    List<Invocation> invocations;
    
    new1 = inv.startContext.and(context);
    new2 = inv.startContext.andNot(context);
    
    inv.startContext.delRef();
    inv.startContext = new1;
    
    invocations = new LinkedList<Invocation>();
    
    if (! new2.isFalse()) {
      Invocation newinv;
      
      newinv = new Invocation(new2);
      invocations.add(newinv);
      
      for (ConditionalSyntax s : list) {
        if (newinv.done()) break;
        
        newinv.parse(s);
        
        if (newinv.needFork(s)) {
          List<Invocation> forked;
          
          forked = forkInvocation(newinv, s.context, list);
          invocations.addAll(forked);
        }
      }
    }
    else {
      new2.delRef();
    }
    
    return invocations;
  }
  
  /**
   * Expand a function-like macro invocation.  This method assumes
   * that any conditionals that break the invocation have already been
   * expanded by hoistFunction.  This parses the parameters, expands
   * them, and replaces the formal parameters with the actuals in the
   * macro definition.
   * 
   * This method follows the gcc preprocessor implementation.  The
   * pseudo-code for this algorithm is the following:<br>
   * <pre>
   * if (funlike)
   *  fun funlike_invocation_p
   *    check for paren
   *    fun collect_args
   *      for each argument
   *        call cpp_get_token
   *        while tracking parens and commas
   *          commas must be nesting == 0
   *          don't forget to capture variadics!
   *
   *  fun replace_args
   *    loop through tokens in func macro def
   *      if we encounter an arg
   *        stringify the arg
   *        expand the arg
   *          fun expand_arg
   *            call push_ptoken_context of the args tokens
   *            call cpp_get_token
   *              buffer resulting tokens and store for arg replacement
   *            call _cpp_pop_context
   *    now we replace the args in the func macro def, loop through
   *      replace expanded, stringified, and do pasting
   *      swallow the comma on variadic arg
   *
   * call _cpp_push_token_context of the macro's definition (w/args replaced)
   * </pre>
   *
   * @param token The macro name token.
   * @param entries The definitions of the macro from the macro table.
   * @return a new token context containing the macro expansion.
   */
  protected MacroContext funlikeInvocation(CSyntax token,
                                           List<Entry> entries) {
    CSyntax syntax;
    LinkedList<CSyntax> buffer;
    MacroContext mcontext;
    boolean hasVariadic;
    
    //System.err.println("funlike " + CLexer.getString(token));
    
    buffer = new LinkedList<CSyntax>();
    
    // Skip the whitespace before the open parenthesis.
    expansionOff();
    
    do {
      syntax = scan();
      
      buffer.add(syntax);
    } while ((syntax.isLayout() || syntax.isMacroDelimiter())
             && ! (CLexer.isType(syntax, sym.EOF)));
    
    expansionOn();

    // Check whether any definitions have a variadic argument.
    hasVariadic = false;
    for (Entry e : entries) {
      if (e.macro.isFunction()) {
        if (((Function) e.macro).isVariadic()) {
          hasVariadic = true;
          break;
        }
      }
    }
    
    if (CLexer.isType(syntax, sym.LPAREN)) {
      // The next token is an open parenthesis.  Start parsing the
      // arguments.
      int argc;
      LinkedList<LinkedList<CSyntax>> args;
      LinkedList<LinkedList<CSyntax>> rawargs = null;
      
      // Add the function name to the buffer to preserve original
      // source in the macro delimiter.
      buffer.addFirst(token);
      
      // Initialize the list of parsed arguments.
      argc = 0;
      args = new LinkedList<LinkedList<CSyntax>>();
      
      if (hasVariadic) {
        // Also save arguments with their whitespace and commas, so
        // that the variadic argument can be stringified.
        rawargs = new LinkedList<LinkedList<CSyntax>>();
      }
      
      // Don't expand macros while collect arguments.  Instead,
      // arguments are prescanned (expanded by the preprocessor)
      // before substituting them for their formal arguments in the
      // definition.
      expansionOff();
      
      // Collect macros arguments until we see the closing parenthesis
      // or EOF.
      do {
        int parenDepth = 0;
        
        // Initialize the next argument.
        argc++;
        args.add(null);
        if (hasVariadic) {
          rawargs.add(new LinkedList<CSyntax>());
        }
        
        for (;;) {
          boolean conditionalDirective;
          
          syntax = scan();
          
          buffer.add(syntax);

          // Check for conditional directives in the invocation.
          conditionalDirective = false;
          if (syntax.isDirective()) {
            switch(((Directive) syntax).kind()) {
              case IF:
              case IFDEF:
              case IFNDEF:
              case ELIF:
              case ELSE:
              case ENDIF:
                conditionalDirective = true;
                break;
            }
          }
          
          // Drop leading whitespace.
          if ( (syntax.isLayout() /* TODO || syntax.isDelimiter() */)
            && args.getLast() == null) {
            
            if (hasVariadic && argc > 1) {
              rawargs.get(rawargs.size() - 2).add(syntax);
            }
            
            continue;
            
          } else if (conditionalDirective) {
            // This should never happen, since conditional directives
            // are hoisted around invocations.
            System.err.println("NEED FUNCTION-LIKE MACRO INVOCATION " +
                               "COMPLETION");
            System.err.println(fileManager.include.getName());
            System.err.println(syntax);
            System.exit(-1);
            continue;

          } else if (CLexer.isType(syntax, sym.LPAREN)) {
            // Track nesting depth of parentheses.
            parenDepth++;
            
          } else if (CLexer.isType(syntax, sym.RPAREN)) {
            // Track nesting depth of parentheses.
            if (parenDepth-- == 0) {
              break;
            }

          } else if (CLexer.isType(syntax, sym.COMMA)) {
            if (0 == parenDepth) {
              // Only the commas outside of parenthesis separate
              // arguments.

              if (hasVariadic) {
                rawargs.getLast().add(syntax);
              }

              // Saw one complete argument.
              break;
            }

          } else if (CLexer.isType(syntax, sym.EOF)) {
            // Just in case we encounter EOF while collecting
            // arguments.  This means there was no closing parenthesis
            // in the invocation.
            break;
          }
          
          // Function-like macro invocations can have regular tokens,
          // internal conditional object instances, and other
          // directives: define, undef, error, and warning.
          if (syntax.isToken() || syntax.isLayout()
              || syntax.isConditional()
              || (syntax.isDirective()
                  && (((Directive) syntax).kind() == Directive.Kind.DEFINE
                      || ((Directive) syntax).kind() == Directive.Kind.UNDEF
                      || ((Directive) syntax).kind() == Directive.Kind.ERROR
                      || ((Directive) syntax).kind() == Directive.Kind.WARNING)
                  )) {
            // Arguments are made of regular tokens and conditionals.
            // Also save layout for stringifying the variadic
            // argument.

            if (args.getLast() == null) {
              args.removeLast();
              args.add(new LinkedList<CSyntax>());
            }

            if (syntax.isToken() && args.getLast().size() == 0
                && ((Token) syntax).hasFlag(flag.PREV_WHITE)) {
              // Remove the leading whitespace caused by an argument
              // that contains a nested function-like macro expansion.
              // For example: #define _ASM_ALIGN __ASM_SEL(.balign 4,
              // .balign 8) from
              // linux-2.6.38/arch/x86/include/asm/asm.h:22.  The
              // token ".balign" should not have leading whitespace
              // when "__ASM_SEL" is expanded.

              syntax = (Token) syntax.copy();

              // TODO I changed this because it seemed like an error.
              // It needs to be tested.
              /* ((Token) syntax).unsetFlag(flag.PASTE_LEFT); */
              ((Token) syntax).unsetFlag(flag.PREV_WHITE);
            }
            
            // Save the token in the current argument.
            args.getLast().add(syntax);
            if (hasVariadic) {
              rawargs.getLast().add(syntax);
            }
          }
        }
        
        // Drop trailing padding.
        if (args.getLast() != null) {
          while (args.getLast().getLast().isLayout()) {
            args.getLast().removeLast();
          }
          
          if (args.getLast().size() == 0) {
            args.removeLast();
            args.add(null);
          }
        }
        
      } while (! (CLexer.isType(syntax, sym.RPAREN)
                  || CLexer.isType(syntax, sym.EOF)));

      expansionOn();

      if (CLexer.isType(syntax, sym.EOF)) {
        if (SHOW_ERRORS) {
          System.err.println("error: unterminated argument list " +
                             "invoking macro " + token.getValue());
        }
        
        buffer.removeLast(); //remove EOF from saved syntax
        queue.offer(syntax); //backup to EOF

        // TODO preserve original source of the invalid macro
        // invocation.
        
        mcontext = null;
      } else {
        // We have seen the closing parenthesis of the complete
        // invocation.

        mcontext = replaceArgs(CLexer.getString(token), args, rawargs,
                               entries, buffer);
      }
      
    } else {
      // This is not a function-like macro invocation since we did not
      // see an opening parenthesis.
      
      // TODO if there any any definitions the macro that are
      // object-like, need to expand and push a context for these

      // Emit the macro name.
      queue.offer(token);

      // TODO when "buffer" contains an #include, it is not processed
      // until the rest of the "buffer"'s tokens are processed.  So in
      // $CPPTEST/cpp/function_parenthesis.c, the fourth invocation,
      // the ")" is emitted after the contents of
      // $CPPTEST/cpp/function_parenthesis.h.

      // Back out of the invocation and reprocess the whitespace and
      // token we read while searching for the open parenthesis.
      pushTokenContext(new SyntaxContext(buffer));
      
      mcontext = null;
    }

    //System.err.println("end collect funlike " + CLexer.getString(token));

    return mcontext;
  }
  
  /**
   * Substitute the formal parameters in a macro definition with the
   * actual parameters from the macro invocation.
   *
   * @param name The name of the function-like macro.
   * @param args The actual parameters.
   * @param rawargs The actual parameters including commas and
   * whitespace.
   * @param entries The macro definitions from the macro symbol table.
   * @param buffer The raw tokens of the macro invocation.
   */
  protected MacroContext replaceArgs(String name,
                                     LinkedList<LinkedList<CSyntax>> args,
                                     LinkedList<LinkedList<CSyntax>> rawargs,
                                     List<Entry> entries,
                                     LinkedList<CSyntax> buffer) {
    List<CSyntax>[] stringified;
    List<CSyntax>[] blockArgs;
    LinkedList[] expanded;
    
    expanded = null;
    blockArgs = null;
    stringified = null;
    
    // Remove tokens whose presence conditions are false under the
    // current presence condition.
    for (List<CSyntax> arg : args) {
      Context c;
      
      c = contextManager.reference();
      trimInfeasible(arg, c);
      c.delRef();
    }

    // Prescan (expand) and stringify arguments for each function
    // definition.  Each definition may have a different number and
    // different names of formal arguments.  Arguments are expanded
    // and stringified only if they are actually used in a definition.
    for (Entry e : entries) {
      if (e.macro.isFunction()) {
        Function f;
        
        // Skip empty definitions.
        if (null == e.macro.definition) continue;
        
        // TODO verify number of arguments which can be different for
        // each definition.  Because of variadics, one invocation can
        // be valid for definitions with differing numbers of
        // arguments.
        
        f = (Function) e.macro;

        // Skip definitions without arguments.
        if (null == f.formals) continue;

        // Expand and stringify arguments.  Delay expanding and
        // stringifying variadics, since the number of arguments in
        // the variadic depends on the definition.
        for (int i = 0; i < f.definition.size(); i++ ) {
          Token t;
          int indexOfFormal;
          
          t = f.definition.get(i);
          
          if (! (t.isIdentifier() || t.isKeyword())) continue;
          
          indexOfFormal = f.formals.indexOf(t.getValue());
          
          // TODO can remove this after verifying correct number of
          // actual arguments.
          if (indexOfFormal >= args.size()) {
            indexOfFormal = -1;
          }
          
          if (indexOfFormal >= 0) {
            // The token is a formal argument for the current
            // definition.
            
            if (t.hasFlag(flag.STRINGIFY_ARG)) {
              // Stringify the argument.

              if (null == stringified) {
                stringified = new List[args.size()];
              }

              if (null == stringified[indexOfFormal]) {
                Context global;
                
                global = contextManager.reference();
                if (null == blockArgs) {
                  blockArgs = new List[args.size()];
                }
                if (null == blockArgs[indexOfFormal]) {
                  blockArgs[indexOfFormal]
                    = buildBlocks(args.get(indexOfFormal), global);
                }
                stringified[indexOfFormal]
                  = stringifyArg(blockArgs[indexOfFormal], global);
                global.delRef();
              }

            } else if (t.hasFlag(flag.PASTE_LEFT)
                       || (i > 0 && f.definition.get(i - 1)
                           .hasFlag(flag.PASTE_LEFT))) {
              // Operands to the token-paste operator are _not_
              // expanded.

              if (null == blockArgs) {
                blockArgs = new List[args.size()];
              }

              if (null == blockArgs[indexOfFormal]) {
                Context global;
                
                global = contextManager.reference();
                blockArgs[indexOfFormal]
                  = buildBlocks(args.get(indexOfFormal), global);
                global.delRef();
              }

            } else {
              // Expand the argument (i.e. prescan.)

              if (null == expanded) {
                expanded = new LinkedList[args.size()];
              }

              if (null == expanded[indexOfFormal]) {
                expanded[indexOfFormal] = expandArg(args.get(indexOfFormal));
                
                Context c;
                
                c = contextManager.reference();
                trimInfeasible(expanded[indexOfFormal], c);
                c.delRef();
              }
            }
          }
        } // For each token in the definition.
      }
    } // For each definition.
    
    
    List<List<? extends CSyntax>> lists
      = new LinkedList<List<? extends CSyntax>>();;
    List<Context> contexts = new LinkedList<Context>();;

    // Subsitute the arguments for each definition.  The result is a
    // list of tokens for each definition containing.
    for (Entry e : entries) {
      contexts.add(e.context);
      
      if (e.macro.isFunction()) {
        List<CSyntax> replaced;
        Function f;
        boolean argsCheck;
        
        f = (Function) e.macro;
        
        // Check that the number of formal arguments matches the
        // number of actuals.  Because of variadics, the number of
        // actuals may legally be greater than the number of formals.

        argsCheck = false;

        if (null == f.formals || f.formals.size() == 0) {
          if (! f.isVariadic()) {
            argsCheck = null == args
              || args.size() == 0
              || args.size() == 1
              && (null == args.get(0) || args.get(0).size() == 0);

            // Determine whether the invocation has no
            // arguments.  It has no arguments if there is only one
            // argument and it contains no regular tokens.
            if (args.size() == 1 && null != args.get(0)
                && args.get(0).size() > 0) {
              argsCheck = true;

              for (CSyntax s : args.get(0)) {
                if (s.isToken()) {
                  argsCheck = false;
                  break;
                }
              }
            }
            
          } else {
            argsCheck = true;
          }

        } else {
          if (null != args && args.size() == f.formals.size()) {
            argsCheck = true;

          } else if (f.isVariadic() && args.size() >= (f.formals.size() + 1)) {
            argsCheck = true;
          }
        }

        if (! argsCheck) {
          // The number of arguments does not match the number of
          // formal arguments.

          // TODO report the error.
          replaced = null;
          
        } else if (null == f.definition) {
          // The definition was empty, so it expands to nothing.
          replaced = null;

        } else {
          // Substitute the formal arguments with the actual
          // arguments for each definition.

          LinkedList<CSyntax> varArg = null;
          List<CSyntax> varStr = null;
          List<CSyntax> varBlock = null;
          LinkedList<CSyntax> varExp = null;
          
          replaced = new LinkedList<CSyntax>();
          
          //Expand and stringify the variadic argument if the current
          //definition has one.
          for (int i = 0; i < f.definition.size(); i++ ) {
            Token t;
            int indexOfFormal;
            
            t = f.definition.get(i);
            
            if (! (t.isIdentifier() || t.isKeyword())) continue;

            if (f.isVariadic() && CLexer.getString(t).equals(f.variadic)) {
              if (null == varArg) {
                // Construct the variadic argument out of several actual
                // arguments.

                varArg = new LinkedList<CSyntax>();
                
                for (int argi = null == f.formals ? 0 : f.formals.size();
                     argi < rawargs.size(); argi++) {
                  if (null != rawargs.get(argi)) {
                    for (CSyntax s : rawargs.get(argi)) {
                      varArg.add(s);
                    }
                  }
                }
                
                // Remove trailing padding.
                while (varArg.size() > 0 && varArg.getLast().isLayout()) {
                  varArg.removeLast();
                }
              }
              
              if (t.hasFlag(flag.STRINGIFY_ARG)) {
                // Stringify the variadic argument.

                if (null == varStr) {
                  Context global;
                  
                  global = contextManager.reference();
                  if (null == varBlock) {
                    varBlock = buildBlocks(varArg, global);
                  }
                  varStr = stringifyArg(varBlock, global);
                  global.delRef();
                }

              } else if (t.hasFlag(flag.PASTE_LEFT)
                         || (i > 0 && f.definition.get(i - 1)
                             .hasFlag(flag.PASTE_LEFT))) {
                // Operands to token-pasting are _not_ expanded.  Save
                // the unexpanded variadic argument.

                if (null == varBlock) {
                  Context global;
                  
                  global = contextManager.reference();
                  varBlock = buildBlocks(varArg, global);
                  global.delRef();
                }

              } else {
                // Expand the variadic argument.
                
                if (null == varExp) {
                  varExp = expandArg(varArg);
                }
              }
            }
          }

          // Finally, substitute the formals with the actuals.
          for (int i = 0; i < f.definition.size(); i++) {
            Token t;
            boolean variadic;
            int indexOfFormal;
            
            t = f.definition.get(i);
            variadic = false;
            
            if (null != f.formals) {
              indexOfFormal = f.formals.indexOf(CLexer.getString(t));
            }
            else {
              indexOfFormal = -1;
            }
            
            if (f.isVariadic() && CLexer.getString(t).equals(f.variadic)) {
              variadic = true;
            }

            if (indexOfFormal < 0 && ! variadic) {
              if ( (i < f.definition.size() - 1)
                && null != f.variadic
                && f.variadic.equals(CLexer.getString(f.definition.get(i + 1)))
                && t.isType(sym.COMMA)
                && ((Token) t).hasFlag(flag.PASTE_LEFT)
              ) {
                // The following implements a GCC preprocessor
                // feature.  When an empty variaidic argument is
                // pasted with a comma, the comma is removed.  If the
                // variadic is not empty, no pasting occurs.

                if (args.size() == f.formals.size()) {
                  // Swallow the comma (don't add it to the expanded
                  // definition.)  Then skip the variadic argument
                  // since we know it's empty.
                  i++;

                } else {
                  // Don't attempt to paste the comma with the
                  // variadic.  Even though there are no tokens that
                  // can be pasted with a comma, this avoids the error
                  // message that would be emitted.
                  Token newcomma;
                  
                  // TODO check cpp/function_variadic_paste.c, need to
                  // get rid of space between "," and the empty variadic.
                  
                  newcomma = (Token) t.copy();
                  newcomma.unsetFlag(flag.PASTE_LEFT);
                  replaced.add(newcomma);
                }
                
              } else {
                // It's not a formal argument, so just pass it
                // through.
                replaced.add(t);
              }

            } else {
              
              // We found a formal argument.  Substitute it with the
              // actual argument.

              LinkedList<CSyntax> argArg = null;
              List<CSyntax> argStr = null;
              List<CSyntax> argBlock = null;
              LinkedList<CSyntax> argExp = null;

              if (variadic) {
                argArg = varArg;
                argStr = varStr;
                argBlock = varBlock;
                argExp = varExp;

              } else {
                argArg = args.get(indexOfFormal);
                if (null != stringified) {
                  argStr = stringified[indexOfFormal];
                }
                if (null != blockArgs) {
                  argBlock = blockArgs[indexOfFormal];
                }
                if (null != expanded) {
                  argExp = expanded[indexOfFormal];
                }
              }
              
              // TODO check whether adding space for tokens with
              // PREV_WHITE is even necessary. Shouldn't stringifyArg,
              // etc, be checking for PREV_WHITE and adding space
              // instead?
              if (t.hasFlag(flag.STRINGIFY_ARG)) {
                if (t.hasFlag(flag.PREV_WHITE)
                    && argStr != null && argStr.size() > 0) {
                  replaced.add(Layout.SPACE);
                }
                replaced.addAll(argStr);

              } else if (t.hasFlag(flag.PASTE_LEFT)) {
                // Expand a macro argument that is the left operand of
                // a token-paste operation.  Only the _last_ token of
                // the actual argument gets pasted, so add the
                // PASTE_LEFT flag to it.
                List<CSyntax> arg;
                FlaggedSyntax last;
                
                arg = argBlock;
                if (arg.size() > 0) {
                  last = (FlaggedSyntax) arg.get(arg.size() - 1);
    
                  if (null != arg) {
                    if (t.hasFlag(flag.PREV_WHITE)) {
                      replaced.add(Layout.SPACE);
                    }

                    for (CSyntax a : arg) {
                      if (a != last) {
                        replaced.add(a);
                      }
                    }
                  
                    // Copy the token that will received the
                    // PASTE_LEFT flag.  This is necessary since the
                    // actual argument may be substituted elsewhere,
                    // i.e. not as the left operand of a
                    // token-pasting.
                    last = (FlaggedSyntax) last.copy();
                    last.setFlag(flag.PASTE_LEFT);
                    replaced.add(last);
                  }
                }

              } else if ( i > 0 && f.definition.get(i - 1)
                          .hasFlag(flag.PASTE_LEFT)) {
                // Expand a macro argument that is the right operand
                // of a token-paste operation.  The only thing we need
                // to do special here is check for an empty argument.
                List<CSyntax> arg;
                
                arg = argBlock;
                if (null != arg && arg.size() > 0) {
                  if (t.hasFlag(flag.PREV_WHITE)) {
                    replaced.add(Layout.SPACE);
                  }

                  for (CSyntax a : arg) {
                    replaced.add(a);
                  }
                  
                } else {
                  // The argument is empty, so add a special token to
                  // avoid the token-paste.
                  replaced.add(Layout.AVOID_PASTE);
                }

              } else if (null != argExp) {
                // Substitute a formal argument with an actual.

                if (t.hasFlag(flag.PREV_WHITE) && argExp != null
                    && argExp.size() > 0) {
                  replaced.add(Layout.SPACE);
                }

                for (CSyntax a : (LinkedList<CSyntax>) argExp) {
                  replaced.add(a);
                }

              }
            }
          }
        }
        
        lists.add(replaced);

      } else {
        // The entry is an object-like definition, free, or undefined.
        List<CSyntax> replaced;

        replaced = new LinkedList<CSyntax>();
        
        if (Macro.State.DEFINED == e.macro.state) {
          if (null != e.macro.definition) {
            replaced.addAll(e.macro.definition);
          }

          for (CSyntax s : buffer) {
            if (buffer.getFirst() != s) {
              replaced.add(s);
            }
          }

        } else {
          replaced.addAll(buffer);
        }
        
        lists.add(replaced);
      }
    }
    
    macroTable.free(entries);
    
    // Check whether we need a conditional for the expansion.  When
    // there is only one definition and it is from the same presence
    // condition, we don't need a conditional around the expanded
    // definition(s).

    boolean needConditional;
    
    needConditional = true;
    if (lists.size() == 1) {
      Context context;
      Context and;
      
      context = contextManager.reference();
      and = context.and(contexts.get(0));
      context.delRef();

      needConditional = ! contextManager.is(and);
      and.delRef();
    }
    
    // Return the expanded macros so they can be preprocessed.

    if (needConditional) {
      return new MultipleMacro(name, lists, contexts,
                               new FunctionMacro(buffer));
    }
    else {
      return new SingleMacro(name, lists.get(0), new FunctionMacro(buffer));
    }
  }
  
  /**
   * Trim tokens whose presence condition is infeasible under a given
   * presence condition.  The passed list of tokens is modified.
   *
   * @param list The list of tokens.
   * @param context The presence condition to test for feasibility
   * under.
   */
  protected static void trimInfeasible(List<? extends CSyntax> list,
                                       Context context) {
    int nesting;
    boolean feasible;
    
    if (null == list) return;
    
    nesting = 0;
    feasible = true;
    for (int i = 0; i < list.size(); i++) {
      CSyntax s;
      
      s = list.get(i);
  
      if (! feasible && nesting == 0) {
        if (s.isElif() || s.isEndConditional()) {
          feasible = true;
          continue;
        }
      }
      
      if (! feasible) {
        if (s.isIf()) {
          nesting++;
        }
        if (s.isEndConditional()) {
          nesting--;
        }
      }
      
      if (feasible) {
        // Leave the token alone.

      } else {
        // Remove infeasible tokens.
        list.remove(i);
        i--;
        continue;
      }
      
      if (feasible) {
        if (s.isIf() || s.isElif()) {
          Context and;
          
          and = context.and(((StartConditional) s).context);
          if (and.isFalse()) {
            feasible = false;
            nesting = 0;
          }
          and.delRef();
        }
      }
    }
  }

  /**
   * Stringify a macro argument.  Because of conditionals, there may a
   * different stringified argument by presence condition.  This
   * method hoists the conditional around stringification and returns
   * a list of string literals.
   *
   * @param arg The list of tokens of the macro argument.
   * @param global The current presence condition.  Used to trim
   * infeasible tokens
   * @return The list of string literals.
   */
  protected List<CSyntax> stringifyArg(List<CSyntax> arg, Context global) {
    if (null == arg) {
      // An empty argument just becomes "".
      List<CSyntax> list;
      
      list = new LinkedList<CSyntax>();
      list.add(new Literal(sym.STRINGliteral, "\"\"", -1, -1, -1));
      
      return list;
      
    } else {
      boolean hoist;
      
      // Check whether we need to hoist a conditional around the
      // stringification.  Hoisting is necessary when the argument
      // contains conditionals.
      hoist = false;
      for (CSyntax s : arg) {
        if (s.isConditionalBlock()) {
          hoist = true;
          break;
        }
      }
      
      if (STATISTICS) {
        Statistics.inc(Statistics.row.STRINGIFY);
      }
      
      if (hoist) {
        // Hoist conditionals around stringification.
        List<StringBuilder> strings;
        List<Context> contexts;
        List<CSyntax> list;
        boolean first;
        
        strings = new LinkedList<StringBuilder>();
        contexts = new LinkedList<Context>();

        strings.add(new StringBuilder());
        contexts.add(global);
        global.addRef();
        hoistStringification(arg, strings, contexts, global);
        
        list = new LinkedList<CSyntax>();
        first = true;
        for (int i = 0; i < strings.size(); i++) {
          String str;
          BDD bdd;
          
          if (! contexts.get(i).isFalse()) {
            str = escapeString(strings.get(i).toString());
            
            if (first) {
              list.add(new If(contexts.get(i)));
              first = false;
            }
            else {
              list.add(new Elif(contexts.get(i)));
            }
            
            list.add(new Literal(sym.STRINGliteral, str, -1, -1, -1));
          }
          else {
            contexts.get(i).delRef();
          }
        }
        
        if (! first) {
          list.add(new EndConditional());
        }

        if (STATISTICS) {
          Statistics.inc(Statistics.row.HOISTED_STRINGIFY);
        }

        return list;
        
      } else {
        // Stringify the argument.
        StringBuilder sb;
        String str;
        Literal stringified;
        List<CSyntax> list;
        
        sb = new StringBuilder();
        
        for (CSyntax s : arg) {
          if (s.isToken()) {
            if (((Token) s).hasFlag(flag.PREV_WHITE)) {
              sb.append(' ');
            }
            sb.append(CLexer.getString(s));
          }
          else if (s.isLayout()) {
            sb.append(' ');
          }
        }
        
        str = escapeString(sb.toString());
        
        stringified = new Literal(sym.STRINGliteral, str, -1, -1, -1);
        
        list = new LinkedList<CSyntax>();
        list.add(stringified);
        
        return list;
      }
    }
  }
  
  /**
   * Take a list of tokens and conditionals and group the conditionals
   * together into a structured conditional block object.
   *
   * @param list The tokens to build the conditional blocks from.
   * @param global The current presence condition, used to trim
   * infeasible conditional branches.
   */
  protected static List<CSyntax> buildBlocks(List<CSyntax> list,
                                             Context global) {
    List<CSyntax> newList;
    SyntaxContext tcontext;
    CSyntax syntax;
    
    newList = new LinkedList<CSyntax>();
    tcontext = new SyntaxContext(list);
    
    syntax = tcontext.scan();
    while (null != syntax) {
      if (syntax.isConditional()) {
        newList.add(buildConditionalBlock((If) syntax, tcontext, global));
      }
      else {
        newList.add(syntax);
      }
      
      syntax = tcontext.scan();
    }
    
    return newList;
  }
  
  /**
   * Build one conditional block out of a list of tokens.
   *
   * @param start The starting "if" compound token of the conditional.
   * @param streamin The remaining tokens of the conditional.
   * @param global The current presence conditional used to trim
   * infeasible conditional branches.
   * @return The conditional block.
   */
  protected static ConditionalBlock
    buildConditionalBlock(If start, Stream streamin, Context global) {
    List<CSyntax> branch;
    List<List<? extends CSyntax>> branches;
    List<Context> contexts;
    ConditionalBlock block;
    CSyntax syntax;
    
    branches = new LinkedList<List<? extends CSyntax>>();
    contexts = new LinkedList<Context>();

    branch = new LinkedList<CSyntax>();
    branches.add(branch);
    
    contexts.add(start.context);
    start.context.addRef();
    
    syntax = streamin.scan();
    
    while (null != syntax) {
      
      if (syntax.isIf()) {
        branch.add(buildConditionalBlock((If) syntax, streamin, global));
      }
      else if (syntax.isElif()) {
        branch = new LinkedList<CSyntax>();
        branches.add(branch);
        contexts.add(((Elif) syntax).context);
        ((Elif) syntax).context.addRef();
      }
      else if (syntax.isEndConditional()) {
        break;
      }
      else {
        branch.add(syntax);
      }
      
      syntax = streamin.scan();
    }
    
    // Trim infeasible branches.
    for (int i = 0; i < branches.size(); i++) {
      List<? extends CSyntax> list;
      Context local;
      
      list = branches.get(i);

      local = global.and(contexts.get(i));
      
      if (local.isFalse()) {
        branches.remove(i);
        contexts.remove(i);
      }
      
      local.delRef();
    }
    
    // Add implicit else to the block if necessary.
    Context union, notUnion, implicitElse;
    
    union = contexts.get(0);
    union.addRef();
    
    for (int i = 1; i < contexts.size(); i++) {
      Context tmp;
      
      tmp = union.or(contexts.get(i));
      union.delRef();
      union = tmp;
    }
    
    notUnion = union.not();
    union.delRef();
    
    implicitElse = global.and(notUnion);
    
    notUnion.delRef();

    if (! implicitElse.isFalse()) {
      contexts.add(implicitElse);
      branches.add(new LinkedList<CSyntax>());
    }
    else {
      implicitElse.delRef();
    }
    
    block = new ConditionalBlock(branches, contexts);
    
    if (null != start.flags) {
      block.flags = start.flags.clone();
    }
    
    return block;
  }
  
  /**
   * Escape quotes in the string and add quotes.
   *
   * @param str The string to escape.
   * @return The escaped string.
   */
  protected static String escapeString(String str) {
    str = str.replace("\\", "\\\\");
    str = str.replace("\"", "\\\"");
    str = "\"" + str + "\"";
    
    return str;
  }
  
  /**
   * Hoist stringification around all conditionals in a list of
   * tokens.  This completes the conditionals by taking all
   * combinations of their branches, resulting in multiplicative
   * explosion in the number of strings.
   *
   * @param list The list of tokens containing conditionals to hoist.
   * @param strings The stringified strings.
   * @param contexts The presence conditions of the hoisted
   * conditionals around each string.
   */
  protected static void hoistStringification(List<? extends CSyntax> list,
                                             List<StringBuilder> strings,
                                             List<Context> contexts,
                                             Context global) {
    // TODO unit test this by having nested conditionals passed as
    // stringify args, verify optimal bdd freeing.

    for (CSyntax s : list) {
      if (s.isToken()) {
        for (StringBuilder sb : strings) {
          sb.append(CLexer.getString(s));
        }

      } else if (s.isLayout()) {
        if (CLexer.getString(s).length() > 0) {
          for (StringBuilder sb : strings) {
            if (sb.charAt(sb.length() - 1) != ' ') {
              sb.append(' ');
            }
          }
        }

      } else if (s.isConditionalBlock()) {
        ConditionalBlock block;
        List<StringBuilder> newStrings;
        List<Context> newContexts;
        
        block = (ConditionalBlock) s;
        newStrings = new LinkedList<StringBuilder>();
        newContexts = new LinkedList<Context>();
        for (int i = 0; i < block.contexts.size(); i++) {
          List<? extends CSyntax> branch;
          Context context;
          List<StringBuilder> branchStrings;
          List<Context> branchContexts;
          
          branch = block.branches.get(i);
          context = block.contexts.get(i);

          branchStrings = new LinkedList<StringBuilder>();
          branchContexts = new LinkedList<Context>();
          branchStrings.add(new StringBuilder());
          branchContexts.add(context);
          context.addRef();
          
          hoistStringification(branch, branchStrings, branchContexts, global);
          
          // Combine strings and contexts with newStrings and newContexts.
          for (int a = 0; a < strings.size(); a++) {
            for (int b = 0; b < branchStrings.size(); b++) {
              StringBuilder sb;
              Context tmp, newContext;
              
              tmp = contexts.get(a).and(branchContexts.get(b));
              newContext = global.and(tmp);
              tmp.delRef();

              if (newContext.isFalse()) {
                newContext.delRef();
              }
              else {
                sb = new StringBuilder();
                sb.append(strings.get(a));
                sb.append(branchStrings.get(b));
                newStrings.add(sb);
                newContexts.add(newContext);
              }
            }
          }
          
          for (Context c : branchContexts) {
            c.delRef();
          }
        }
        
        strings.clear();
        strings.addAll(newStrings);

        for (Context c : contexts) {
          c.delRef();
        }
        contexts.clear();
        contexts.addAll(newContexts);
      }
    }
  }
  
  /**
   * Expand an argument by pushing a token context and using
   * Preprocessor.
   *
   * @param arg The argument to expand.
   * @return The expanded argument.
   */
  protected LinkedList<CSyntax> expandArg(LinkedList<CSyntax> arg) {
    SyntaxContext scontext;
    LinkedList<CSyntax> expanded;
    LinkedList<CSyntax> argEOF;
    
    // Empty argument.
    if (null == arg) return null;
    
    argEOF = new LinkedList<CSyntax>();
    argEOF.addAll(arg);
    argEOF.add(Token.EOF);

    scontext = new SyntaxContext(argEOF);
    
    pushTokenContext(scontext);
    
    expanded = new LinkedList<CSyntax>();
    for (;;) {
      CSyntax syntax;
      
      syntax = scan();
      
      if (Token.EOF == syntax) {
        break;

      } else if (syntax.isEndInclude()) {
        //break;

      } else if (CLexer.isType(syntax, sym.EOF)) {
        if (SHOW_ERRORS) {
          System.err.println("real EOF in argument expansion");
        }
        break;
      }

      expanded.add(syntax);
    }
    
    popTokenContext();
    
    return expanded;
  }
  
  /**
   * Turn expansion off.  The preprocessor will not preprocess any
   * tokens in this state.
   */
  protected void expansionOff() {
    expanding++;
  }

  /**
   * Turn expansion on.  The preprocessor will preprocess tokens in
   * this state.
   */
  protected void expansionOn() {
    expanding--;
  }
  
  /**
   * Check if preprocessor should preprocess tokens or not.
   *
   * @return true if the preprocessor should be expanding macros.
   */
  protected boolean isExpanding() {
    return expanding == 0;
  }

  //process different directives, i.e. definitions
  //update conditional context
  //expand macros in program text and in conditional expressions
  //parse function-like macros
  //complete function-like macros
  
  /**
   * Push a token context.  A token context is a list of tokens that
   * are pending preprocessing.  They are used for preprocessing macro
   * definitions after expansion.  Since macros can be nested, the
   * token contexts are stored in a stack.
   *
   * @param tcontext The token context.
   */
  protected void pushTokenContext(TokenContext tcontext) {
    tcontexts.push(tcontext);

    if (tcontext.isMacroContext()) {
      MacroContext mcontext;

      mcontext = (MacroContext) tcontext;
      macroTable.disable(mcontext.name);
      queue.offer(mcontext.startDelimiter);
    }
  }
  
  /**
   * Pop a token context.  Returns a macro expansion delimiter.
   *
   * @return A delimiter that signifies the end of a macro expansion.
   */
  protected CSyntax popTokenContext() {
    TokenContext tcontext;
    
    tcontext = tcontexts.pop();
    
    if (tcontext.isMacroContext()) {
      MacroContext mcontext;
      
      mcontext = (MacroContext) tcontext;

      macroTable.enable(mcontext.name);
      
      if (mcontext.isMultipleMacro()) {
        ((MultipleMacro) mcontext).freeContext();
      }
      
      return mcontext.endDelimiter;

    } else {
      // When the token context is not from a macro expansion, just
      // return an empty token.
      return Layout.EMPTY;
    }
  }
  
  /**
   * A token context class.  A token context is a stream the
   * preprocessor reads tokens from instead of directly from input.
   * It is used, among other things, for macro expansion.
   */
  protected abstract static class TokenContext implements Stream {
    public boolean isMacroContext() {
      return false;
    }
    
    public boolean isSyntaxContext() {
      return false;
    }

    public boolean isSingleMacro() {
      return false;
    }
    
    public boolean isMultipleMacro() {
      return false;
    }
  }
  
  /**
   * A token context that produces a stream of tokens from a buffer.
   */
  protected static class SyntaxContext extends TokenContext {
    /** The list iterator. */
    protected ListIterator<CSyntax> iterator;
    
    /** Create a new token context from a list of syntax objects */
    public SyntaxContext(List<CSyntax> list) {
      if (null != list) {
        this.iterator = list.listIterator();
      }
      else {
        this.iterator = null;
      }
    }
                                          
    public CSyntax scan() {
      if (null == iterator) {
        return null;
      }
      
      if (iterator.hasNext()) {
        return iterator.next();
      }
      else {
        return null;
      }
    }
    
    public boolean end() {
      return ! iterator.hasNext();
    }
    
    public boolean isSyntaxContext() {
      return true;
    }
  }
  
  /**
   * A token context that contains the tokens from a macro expansion.
   */
  protected abstract static class MacroContext extends TokenContext {
    public final String name;
    public final StartMacro startDelimiter;
    public final EndMacro endDelimiter;
    
    protected MacroContext(String name, StartMacro startMacro,
                           EndMacro endMacro) {
      this.name = name;
      this.startDelimiter = startMacro;
      this.endDelimiter = endMacro;
    }
    
    public boolean isMacroContext() {
      return true;
    }
  }
  
  /**
   * The token context for singly-defined macro.
   */
  protected static class SingleMacro extends MacroContext {
    /** The macro definition iterator */
    protected ListIterator<? extends CSyntax> iterator;
    
    /** Create a new macro context, supporting empty definitions too */
    public SingleMacro(String name, List<? extends CSyntax> def,
                       StartMacro startMacro) {
      super(name, startMacro, new EndMacro(startMacro));
      
      if (null == def) {
        this.iterator = null;

      } else {
        this.iterator = def.listIterator();
      }
    }
    
    public CSyntax scan() {
      if (null == iterator) {
        return null;
      }
      
      if (iterator.hasNext()) {
        return iterator.next();

      } else {
        return null;
      }
    }
    
    public boolean end() {
      return iterator == null || ! iterator.hasNext();
    }

    public boolean isSingleMacro() {
      return true;
    }
  }
  
  /**
   * The macro context of a multiply-defined macro.
   */
  protected static class MultipleMacro extends MacroContext {
    protected List<List<? extends CSyntax>> lists;
    
    protected List<Context> contexts;
    
    /** The current list */
    protected int list;
    
    /** The position within a list */
    protected int i;
    
    public MultipleMacro(String name, List<List<? extends CSyntax>> lists,
                         List<Context> contexts, StartMacro startMacro) {
      super(name, startMacro, new EndMacro(startMacro));
      
      this.lists = lists;
      this.contexts = contexts;
      this.list = -1;
      this.i = 0;
      
      for (int listi = 0; listi < lists.size(); listi++) {
        trimInfeasible(lists.get(listi), contexts.get(listi));
      }
    }

    public boolean isMultipleMacro() {
      return true;
    }
    
    public CSyntax scan() {
      if (-1 == list) {
        list = 0;
        i = 0;
        contexts.get(list).addRef();
        
        return new If(contexts.get(list));

      } else if (list < lists.size()) {
        if (null != lists.get(list) && i < lists.get(list).size()) {
          return lists.get(list).get(i++);

        } else {
          list++;
          i = 0;
          if (list < lists.size()) {
            contexts.get(list).addRef();
            
            return new Elif(contexts.get(list));

          } else {
            return new EndConditional();
          }
        }

      } else {
        return null;
      }
    }

    public boolean end() {
      return list >= lists.size();
    }

    public void freeContext() {
      for (Context context : contexts) {
        context.delRef();
      }
    }
  }
}
