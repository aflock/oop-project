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
import java.util.ListIterator;
import java.util.EnumSet;
import java.util.HashMap;

import xtc.util.Pair;

import xtc.lang.cpp.ContextManager.Context;
import xtc.lang.cpp.MacroTable.Entry;
import xtc.lang.cpp.MacroTable.Macro.State;
import xtc.lang.cpp.HeaderFileManager.PFile;

import xtc.lang.cpp.ForkMergeParserTables.sym;

/**
 * A language unit, such as a token, a directive, or formatting.
 * This class is only meant to be subclassed.
 *
 * @author Paul Gazzillo
 * @version $Revision: 1.52 $
 */
public class CSyntax {
  public int line;
  public int charBegin;
  public int charEnd;
  public String file;
  public Pair<CSyntax> layout;
  
  public enum SyntaxKind {
    TOKEN, IF, ELIF, END_CONDITIONAL
  };
  
  /** No public constructor.  */
  protected CSyntax(int line, int charBegin, int charEnd) {
    //TODO use location object that has file name too
    this.line = line;
    this.charBegin = charBegin;
    this.charEnd = charEnd;
  }
  
  protected CSyntax(CSyntax s) {
    this(s.line, s.charBegin, s.charEnd);
  }
  
  public boolean isToken() {
    return false;
  }
  
  public boolean isKeyword() {
    return false;
  }
  
  public boolean isIdentifier() {
    return false;
  }
  
  public boolean isPunctuation() {
    return false;
  }
  
  public boolean isLayout() {
    return false;
  }
  
  public boolean isDirective() {
    return false;
  }
  
  public boolean isConditional() {
    return false;
  }
  
  public boolean isStartConditional() {
    return false;
  }
  
  public boolean isEndConditional() {
    return false;
  }
  
  public boolean isIf() {
    return false;
  }
  
  public boolean isElif() {
    return false;
  }
    
  public boolean isFlaggedSyntax() {
    return false;
  }
  
  public boolean isConditionalBlock() {
    return false;
  }
  
  public boolean isMacroDelimiter() {
    return false;
  }
  
  public boolean isStartMacro() {
    return false;
  }
  
  public boolean isObjectMacro() {
    return false;
  }
  
  public boolean isFunctionMacro() {
    return false;
  }
  
  public boolean isEndMacro() {
    return false;
  }
  
  public boolean isIncludeDelimiter() {
    return false;
  }
  
  public boolean isInclude() {
    return false;
  }

  public boolean isStartInclude() {
    return false;
  }
  
  public boolean isEndInclude() {
    return false;
  }
  
  public boolean isStartComputedInclude() {
    return false;
  }
  
  public boolean isEndComputedInclude() {
    return false;
  }
  
  public boolean isError() {
    return false;
  }
  
  public boolean hasValue() {
    return false;
  }
  
  public String getValue() {
    return null;
  }
  
  public CSyntax copy() {
    return new CSyntax(this);
  }
  
  public SyntaxKind syntaxKind() {
    assert true;
    
    return null;
  }
  
  //NESTED SUBCLASSES

  /** A syntax unit with a flag */
  public static class FlaggedSyntax extends CSyntax {
    public EnumSet<flag> flags;
      
    public FlaggedSyntax(int line, int charBegin, int charEnd) {
      super(line, charBegin, charEnd);
      this.flags = null;
    }
    
    public FlaggedSyntax(FlaggedSyntax s) {
      super(s);
      this.flags = (null == flags) ? null : flags.clone();
    }
    
    public static enum flag {
      NO_EXPAND,
      PASTE_LEFT,
      STRINGIFY_ARG,
      PREV_WHITE,
      HOISTED_FUNCTION,
      UNKNOWN_DEF
    }
    
    public void setFlag(flag f) {
      if (null == flags) {
        flags = EnumSet.of(f);
      }
      else {
        flags.add(f);
      }
    }
    
    public void unsetFlag(flag f) {
      if (null != flags) {
        flags.remove(f);
        if (flags.size() == 0) {
          flags = null;
        }
      }
    }
    
    public boolean hasFlag(flag f) {
      return null != flags && flags.contains(f);
    }
    
    public boolean isFlaggedSyntax() {
      return true;
    }
    
    public CSyntax copy() {
      return new FlaggedSyntax(this);
    }
  }
  
  /** A token */
  public static class Token extends FlaggedSyntax {
    public static Token EOF = new Token(sym.EOF, -1, -1, -1);
    
    protected sym type;
    
    public Token(sym type, int line, int charBegin, int charEnd) {
      super(line, charBegin, charEnd);
      this.type = type;
    }
    
    public Token(Token t) {
      super(t);
      this.type = t.type;
    }

    public sym type() {
      return type;
    }
    
    public boolean isType(sym type) {
      return this.type == type;
    }
    
    public SyntaxKind syntaxKind() {
      return SyntaxKind.TOKEN;
    }
    
    public boolean isToken() {
      return true;
    }

    /** Produce a string representation for debugging */
    public String toString() {
      return CLexer.getString(this);
    }
    
    public CSyntax copy() {
      return new Token(this);
    }
  }

  /** A valued token */
  public static class ValuedToken extends Token {
    protected String value;
    
    /** Make a new Token */
    public ValuedToken(sym type, String value, int line, int charBegin, int charEnd) {
      super(type, line, charBegin, charEnd);
      this.value = value;
    }
    
    public ValuedToken(ValuedToken vt) {
      super(vt);
      this.value = vt.value;
    }
  
    /** Produce a string representation for debugging */
    public String toString() {
      return value;
    }
  
    public boolean hasValue() {
      return true;
    }
    
    public String getValue() {
      return value;
    }
    
    public CSyntax copy() {
      return new ValuedToken(this);
    }
  }

  /** A keyword */
  public static class Keyword extends Token {
    public Keyword(sym type, int line, int charBegin, int charEnd) {
      super(type, line, charBegin, charEnd);
    }
    
    public Keyword(Keyword k) {
      super(k);
    }
    
    public boolean isKeyword() {
      return true;
    }

    public CSyntax copy() {
      return new Keyword(this);
    }
  }

  /** An identifier */
  public static class Identifier extends ValuedToken {
    /** Make a new Token */
    public Identifier(sym type, String value, int line, int charBegin, int charEnd) {
      super(type, value, line, charBegin, charEnd);
    }
    
    public Identifier(Identifier i) {
      super(i);
    }

    public boolean isIdentifier() {
      return true;
    }
  
    public CSyntax copy() {
      return new Identifier(this);
    }
  }

  /** A literal */
  public static class Literal extends ValuedToken {
    /** Make a new Token */
    public Literal(sym type, String value, int line, int charBegin, int charEnd) {
      super(type, value, line, charBegin, charEnd);
    }
    
    public Literal(Literal l) {
      super(l);
    }

    public boolean isLiteral() {
      return true;
    }
  
    public CSyntax copy() {
      return new Literal(this);
    }
  }

  /** Punctuation */
  public static class Punctuation extends Token {
    public Punctuation(sym type, int line, int charBegin, int charEnd) {
      super(type, line, charBegin, charEnd);
    }
    
    public Punctuation(Punctuation p) {
      super(p);
    }
    
    public boolean isPunctuation() {
      return true;
    }

    public CSyntax copy() {
      return new Punctuation(this);
    }
  }

  /** A layout object, containing comments, newlines, and whitespace */
  public static class Layout extends CSyntax {
    /** An empty layout token used when an empty syntax unit is needed */
    public static final Layout EMPTY = new Layout("", false, -1, -1, -1);
    
    /** An empty layout token used when an empty syntax unit is needed */
    public static final Layout SPACE = new Layout(" ", false, -1, -1, -1);
    
    /** An empty layout token used when an empty syntax unit is needed */
    public static final Layout AVOID_PASTE = new Layout("", false, -1, -1, -1);
    
    public String value;
    protected boolean newline;
    
    /** Make a new Token */
    public Layout(String value, boolean newline, int line, int charBegin, int charEnd) {
      super(line, charBegin, charEnd);
      this.value = value;
      this.newline = newline;
    }
    
    public Layout(Layout l) {
      super(l);
      this.value = l.value;
      this.newline = l.newline;
    }
  
    /** Produce a string representation for debugging */
    public String toString() {
      return getValue();
    }
  
    public boolean isLayout() {
      return true;
    }
    
    public boolean hasNewline() {
      return newline;
    }
    
    public boolean hasValue() {
      return true;
    }
    
    public String getValue() {
      return value;
    }

    public CSyntax copy() {
      return new Layout(this);
    }
  }
  
  /** Stores the tokens and layout of a directive. */
  public static class Directive extends CSyntax {
    protected List<CSyntax> list;
    protected int offset;
    protected Kind kind;
    
    public enum Kind {
      IF, IFDEF, IFNDEF, ELIF, ELSE, ENDIF, INCLUDE, INCLUDE_NEXT, DEFINE,
      UNDEF, LINE, ERROR, WARNING, PRAGMA, LINEMARKER, INVALID
    }
    
    private static final HashMap<String, Kind> kindMap
      = new HashMap<String, Kind>();
    static {
      kindMap.put("if", Kind.IF);
      kindMap.put("ifdef", Kind.IFDEF);
      kindMap.put("ifndef", Kind.IFNDEF);
      kindMap.put("elif", Kind.ELIF);
      kindMap.put("else", Kind.ELSE);
      kindMap.put("endif", Kind.ENDIF);
      kindMap.put("include", Kind.INCLUDE);
      kindMap.put("include_next", Kind.INCLUDE_NEXT);
      kindMap.put("define", Kind.DEFINE);
      kindMap.put("undef", Kind.UNDEF);
      kindMap.put("line", Kind.LINE);
      kindMap.put("error", Kind.ERROR);
      kindMap.put("warning", Kind.WARNING);
      kindMap.put("pragma", Kind.PRAGMA);
    }
    
    public Directive(List<CSyntax> list, int line,
      int charBegin, int charEnd
    ) {
      super(line, charBegin, charEnd);
      this.list = list;
      
      String name;  //name of directive
      int s;  //index into the list of tokens of the directive

      //find the directive in the list.name
      name = null;
      s = 1;  //since list.get(0) is HASH
      while (s < list.size()) {
        CSyntax syntax;
        
        syntax = list.get(s);
        
        if (syntax.isToken() && syntax.hasValue()) {
          name = syntax.getValue();
          break;
        }
        else if (syntax.isToken() && ((Token) syntax).isType(sym.IF)) {
          name = "if";
          break;
        }
        else if (syntax.isToken() && ((Token) syntax).isType(sym.ELSE)) {
          name = "else";
          break;
        }
        s++;
      }
      
      s++; //move past the directive name
      
      if (name != null) {
        if (kindMap.containsKey(name)) {
          kind = kindMap.get(name);
          
        } else {
          for (;;) {
            
            if (name.length() > 0) {
              boolean isnumeric;
              
              isnumeric = true;
              for (int i = 0; i < name.length(); i++) {
                if (! Character.isDigit(name.charAt(i))) {
                  isnumeric = false;
                }
              }
              
              if (isnumeric) {
                kind = Kind.LINEMARKER;
                break;
              }
            }
            
            kind = Kind.INVALID;
            break;
          }
        }
      }
      else {
        kind = Kind.INVALID;
      }
      
      this.offset = s;
    }
    
    public Directive(Directive d) {
      super(d);
      this.list = d.list;
      this.kind = kind;
    }
    
    public List<CSyntax> getList() {
      return list;
    }
    
    public int getOffset() {
      return offset;
    }
    
    public Kind kind() {
      return kind;
    }
    
    public boolean isDirective() {
      return true;
    }
    
    public String toString() {
      StringBuilder sb = new StringBuilder();

      for (CSyntax s : list) {
        sb.append(s.toString());
      }

      return sb.toString();
    }
    
    public CSyntax copy() {
      return new Directive(this);
    }
  }
  
  /** An expanded and normalized conditional */
  public static class Conditional extends FlaggedSyntax {
    /** Should be instantiated by subclasses */
    protected Conditional() {
      super(-1, -1, -1);
    }
    
    protected Conditional(Conditional c) {
      super(c);
    }
    
    public boolean isConditional() {
      return true;
    }
    
    public CSyntax copy() {
      return new Conditional(this);
    }
  }
  
  /** Base class for If and Elif markers */
  public static class StartConditional extends Conditional {
    Context context;
    
    protected StartConditional(Context context) {
      this.context = context;
      context.addRef();
    }
    
    protected StartConditional(StartConditional s) {
      super(s);
      this.context = s.context;
      s.context.addRef();
    }
    
    public boolean isStartConditional() {
      return true;
    }
    
    public CSyntax copy() {
      return new StartConditional(this);
    }
  }
  
  /** Marks the beginning of a conditional block */
  public static class If extends StartConditional {
    public If(Context context) {
      super(context);
    }
    
    public If(If i) {
      super(i);
    }
    
    public boolean isIf() {
      return true;
    }
    
    public SyntaxKind syntaxKind() {
      return SyntaxKind.IF;
    }
    
    public CSyntax copy() {
      return new If(this);
    }

    public String toString() {
      return "#if " + context;
    }
  }
  
  /** Marks a branch after the first in a conditional block */
  public static class Elif extends StartConditional {
    public Elif(Context context) {
      super(context);
    }
    
    public Elif(Elif e) {
      super(e);
    }
    
    public boolean isElif() {
      return true;
    }
    
    public SyntaxKind syntaxKind() {
      return SyntaxKind.ELIF;
    }
    
    public CSyntax copy() {
      return new Elif(this);
    }

    public String toString() {
      return "#elif " + context;
    }
  }
  
  /** Marks the end of a conditional block */
  public static class EndConditional extends Conditional {
    public EndConditional() {
    }
    
    public EndConditional(EndConditional e) {
      super(e);
    }
    
    public boolean isEndConditional() {
      return true;
    }
    
    public SyntaxKind syntaxKind() {
      return SyntaxKind.END_CONDITIONAL;
    }
    
    public CSyntax copy() {
      return new EndConditional(this);
    }

    public String toString() {
      return "#endif";
    }
  }
  
  /** A conditional block.  Used within the expander to structure conditionals */
  public static class ConditionalBlock extends FlaggedSyntax {
    List<List<? extends CSyntax>> branches;
    List<Context> contexts;
    
    public ConditionalBlock(List<List<? extends CSyntax>> branches, List<Context> contexts) {
      super(-1, -1, -1);
      this.branches = branches;
      this.contexts = contexts;
      for (Context context : contexts) {
        context.addRef();
      }
    }
    
    public ConditionalBlock(ConditionalBlock c) {
      super(c);
      this.branches = c.branches;
      this.contexts = c.contexts;
      for (Context context : contexts) {
        context.addRef();
      }
    }
    
    public boolean isConditionalBlock() {
      return true;
    }
    
    public void free() {
      branches.clear();
      for (Context c : contexts) {
        c.delRef();
      }
    }
    
    public CSyntax copy() {
      return new ConditionalBlock(this);
    }
    
    /** This creates a conditional block out of a list of macro table entires.
      * It handles adding context references for the ConditionalBlock and
      * freeing the table entry context references.
      *
      * Do not use the table entires after sending them to this function.
      *
      * This takes the original set of tokens, i.e. the macro invocation,
      * and a list of entries, then builds and returns a conditional block.
      */
    public static ConditionalBlock build(List<CSyntax> original, List<Entry> entries) {
      List<List<? extends CSyntax>> branches;
      List<Context> contexts;
      ConditionalBlock block;

      branches = new LinkedList<List<? extends CSyntax>>();
      contexts = new LinkedList<Context>();
      for (Entry e : entries) {
        switch (e.macro.state) {
          case DEFINED:
            branches.add(e.macro.definition);
            break;
          case FREE:
          case UNDEFINED:
            branches.add(original);
            break;
        }
        contexts.add(e.context);  //plus one reference
      }
      entries.clear();  //minus one reference to all contexts
      
      block = new ConditionalBlock(branches, contexts);
      if (((Token) original.get(0)).hasFlag(flag.PREV_WHITE)) {
        block.setFlag(flag.PREV_WHITE);
      }
      
      return block;
    }
  }
  
  public static class MacroDelimiter extends CSyntax {
    protected MacroDelimiter() {
      super(-1, -1, -1);
    }
    
    public boolean isMacroDelimiter() {
      return true;
    }
  }
  
  public static class StartMacro extends MacroDelimiter {
    protected StartMacro() {
    }

    public boolean isStartMacro() {
      return true;
    }
  }
  
  public static class ObjectMacro extends StartMacro {
    Token macro;
    
    public ObjectMacro(Token macro) {
      this.macro = macro;
    }

    public boolean isObjectMacro() {
      return true;
    }

    public String toString() {
      return "ObjectMacro(" + CLexer.getString(macro) + ")";
    }
  }
  
  public static class FunctionMacro extends StartMacro {
    List<CSyntax> invocation;
    
    public FunctionMacro(List<CSyntax> invocation) {
      this.invocation = invocation;
    }

    public boolean isFunctionMacro() {
      return true;
    }

    public String toString() {
      return "FunctionMacro(" + CLexer.getString(invocation.get(0)) + ")";
    }
  }
  
  public static class EndMacro extends MacroDelimiter {
    StartMacro startMacro;
    
    public EndMacro(StartMacro startMacro) {
      this.startMacro = startMacro;
    }

    public boolean isEndMacro() {
      return true;
    }

    public String toString() {
      return "EndMacro(" + startMacro + ")";
    }
  }

  public static class StartInclude extends CSyntax {
    //TODO save original include directive here and perhaps make common superclass for this and computedincludes
    PFile pfile;
    
    public StartInclude(PFile pfile) {
      super(-1, -1, -1);
      
      this.pfile = pfile;
    }
  
    public boolean isInclude() {
      return true;
    }

    public boolean isStartInclude() {
      return true;
    }

    public String toString() {
      return "StartInclude(" + pfile.getName() + ")";
    }
  }
  
  public static class EndInclude extends CSyntax {
    PFile pfile;
    
    public EndInclude(PFile pfile) {
      super(-1, -1, -1);
      
      this.pfile = pfile;
    }
    
    public boolean isInclude() {
      return true;
    }

    public boolean isEndInclude() {
      return true;
    }

    public String toString() {
      return "EndInclude(" + pfile.getName() + ")";
    }
  }
  
  /** Start delimiter for computed include */
  public static class StartComputedInclude extends CSyntax {
    //TODO save original include directive here
    
    public StartComputedInclude() {
      super(-1, -1, -1);
    }
  
    public boolean isInclude() {
      return true;
    }

    public boolean isStartComputedInclude() {
      return true;
    }

    public String toString() {
      return "StartComputedInclude";
    }
  }
  
  /** End delimiter for computed include */
  public static class EndComputedInclude extends CSyntax {
    public EndComputedInclude() {
      super(-1, -1, -1);
    }
    
    public boolean isInclude() {
      return true;
    }

    public boolean isEndComputedInclude() {
      return true;
    }

    public String toString() {
      return "EndComputedInclude";
    }
  }
  
  /** Preprocessor error */
  public static class Error extends CSyntax {
    public Error() {
      super(-1, -1, -1);
    }
    
    public boolean isError() {
      return true;
    }
  }
}

