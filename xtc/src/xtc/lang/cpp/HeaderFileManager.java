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

import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.FileNotFoundException;

import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import xtc.lang.cpp.CSyntax.Token;
import xtc.lang.cpp.CSyntax.ValuedToken;
import xtc.lang.cpp.CSyntax.Keyword;
import xtc.lang.cpp.CSyntax.Identifier;
import xtc.lang.cpp.CSyntax.Literal;
import xtc.lang.cpp.CSyntax.Punctuation;
import xtc.lang.cpp.CSyntax.Layout;
import xtc.lang.cpp.CSyntax.Directive;
import xtc.lang.cpp.CSyntax.Directive.Kind;
import xtc.lang.cpp.CSyntax.If;
import xtc.lang.cpp.CSyntax.Elif;
import xtc.lang.cpp.CSyntax.EndConditional;
import xtc.lang.cpp.CSyntax.StartInclude;
import xtc.lang.cpp.CSyntax.EndInclude;
import xtc.lang.cpp.CSyntax.StartComputedInclude;
import xtc.lang.cpp.CSyntax.EndComputedInclude;

import xtc.lang.cpp.ContextManager.Context;

import xtc.lang.cpp.MacroTable.Entry;
import xtc.lang.cpp.MacroTable.Macro;
import xtc.lang.cpp.MacroTable.Macro.State;

import xtc.lang.cpp.ForkMergeParserTables.sym;

/**
 * This class manages the lexing of input files as well as finding
 * headers.
 *
 * @author Paul Gazzillo
 * @version $Revision: 1.55 $
 */
public class HeaderFileManager implements Stream {
  /** Trace includes. */
  public static boolean TRACE_INCLUDES = false;

  /** Show errors. */
  public static boolean SHOW_ERRORS = false;

  /** Gather statistics. */
  public static boolean STATISTICS = false;

  /** Quote include directories from the CPP tool */
  protected List<String> iquote;
  
  /** I directories from the CPP tool */
  protected List<String> I;
  
  /** System directories from the CPP tool */
  protected List<String> sysdirs;

  /** The current file, main or a header */
  protected Include include;
  
  /** The stack of includes */
  protected Stack<Include> includes;
  
  /** The map of file names to their guard macros */
  protected Map<String, String> guards;
  
  /**
   * The names of headers that don't have guards.  This avoids having
   * to recheck a header for a guard when we know it doesn't have one
   */
  protected HashSet<String> unguarded;
  
  /** The current line number.  Used by the __LINE__ macro. */
  protected int line;
  
  /**
   * Create a new file manager, given the main file reader, the main
   * file object, and the header search paths.
   */
  public HeaderFileManager(Reader in, File file, List<String> iquote,
                           List<String> I, List<String> sysdirs) {
    this.iquote = iquote;
    this.I = I;
    this.sysdirs = sysdirs;
    this.include = new PFile(file.toString(), file, false);
    ((PFile) this.include).open(in);
    this.includes = new Stack<Include>();
    this.guards = new HashMap<String, String>();
    this.unguarded = new HashSet<String>();
    this.line = -1;
  }
  
  /**
   * Get the next token from the base or header file.  If we are in
   * a header a see an EOF, we suppress the EOF and instead pop the
   * header stack and commence reading the last file.
   *
   * @return the next token.
   */
  public CSyntax scan() {
    if (include.isPFile()) {
      CSyntax syntax;
      PFile pfile;
      
      pfile = (PFile) include;
      
      try {
        syntax = pfile.scan();
      }
      catch (Error e) {
        System.err.println(pfile.file);
        e.printStackTrace();
        System.exit(-1);
        syntax = null;
      }
  
      if (CLexer.isType(syntax, sym.EOF) && (! includes.empty())) {
        if (pfile.checkGuard()) {
          if (! guards.containsKey(pfile.file.toString())) {
            guards.put(pfile.file.toString(), pfile.getGuard());
          }
        }
        pfile.close();
        syntax = new EndInclude(pfile);
        include = includes.pop();
        if (TRACE_INCLUDES) {
          System.err.println("back to " + includes.size() + " "
                             + include.getName());
        }
      }
      else {
        pfile.processGuard(syntax);
      }
      
      line = syntax.line;
      
      syntax.file = getFilename();
      
      return syntax;
    }
    else if (include.isComputed()) {
      Computed computed;
      CSyntax syntax;
      
      computed = (Computed) include;
      
      if (! computed.end()) {
        syntax = computed.scan();
      }
      else {
        // TODO store computed include pointer
        syntax = new EndComputedInclude();
        include = includes.pop();
        if (TRACE_INCLUDES) {
          System.err.println("back from computed to " + includes.size()
                             + " " + include.getName());
        }
      }
      
      line = syntax.line;

      syntax.file = getFilename();
      
      return syntax;
    }
    
    return null;
  }
  
  /**
   * Get the current line number.  The lexer returns 0-base number,
   * but __LINE__ needs 1-base.
   *
   * @return the current line number.
   */
  public int getLine() {
    return line;
  }
  
  /**
   * Return the current file name
   *
   * @return the filename.
   */
  public String getFilename() {
    return include.getName();
  }
  
  /**
   * Determine whether we are at the end of the stream.
   *
   * @return true if at end of stream.
   */
  public boolean end() {
    if (includes.empty() && include.end()) {
      return true;
    }
    else {
      return false;
    }
  }
  
  /**
   * Enter the given header file.  Use findHeader to get the qualified
   * header.  Also checks for guard macro.
   *
   * @return a start header delimiter if the header exists, otherwise
   * null
   */
  public CSyntax includeHeader(String headerName, boolean sysHeader,
                               boolean includeNext,
                               ContextManager contextManager,
                               MacroTable macroTable) {
    PFile header;
    
    header = findHeader(headerName, sysHeader, includeNext);
    
    if (null == header) {
      //error messages are reported by findHeader already
      return null;
    }
    else {
      boolean guarded;
      
      guarded = openHeader(header, contextManager, macroTable);
      
      if (! guarded) {
        includes.push(include);
        include = header;
        
        if (TRACE_INCLUDES) {
          System.err.println("include " + includes.size() + " " + header.file);
        }
        
        if (STATISTICS) {
          Statistics.include(header.file.toString());
        }
        
        if (STATISTICS) {
          Statistics.max(Statistics.row.MAX_INCLUDE_DEPTH, includes.size(),
                         headerName);
        }

        return new StartInclude(header);
      }
      else {
        return null;
      }
    }
  }

  /**
   * Open the header, finding and enforcing header guards.
   *
   * @param header the header descriptor.
   * @param contextManager the presence condition manager.
   * @param macroTable the macro symbol table.
   * @returns false if the header was guarded.
   */
  protected boolean openHeader(PFile header, ContextManager contextManager,
    MacroTable macroTable) {
    boolean guarded;
    boolean findGuard;
    
    if (unguarded.contains(header.file.toString())) {
      guarded = false;
      findGuard = false;
    } else if (guards.containsKey(header.file.toString())) {
      List<Entry> entries;
      String guard;
      
      guard = guards.get(header.file.toString());
      
      entries = macroTable.get(guard, contextManager);
      
      guarded = true;
      if (null != entries) {  //can be null in cppmode
        for (Entry entry : entries) {
          if (State.DEFINED != entry.macro.state) {
            guarded = false;
            break;
          }
        }
      }
      findGuard = false;
    } else {
      guarded = false;
      findGuard = true;
    }

    if (guarded) {
      if (TRACE_INCLUDES) {
        System.err.println("guarding " + header.file.toString() + " with " +
                           guards.get(header.file.toString()));
      }
      
      if (STATISTICS) {
        Statistics.guard(header.file.toString());
      }

      return true;
    }

    try {
      header.open();
    }
    catch (FileNotFoundException e) {
      // Should never be thrown since findHeader verifies the header's
      // existence
      e.printStackTrace();
      assert false;
    }
    
    if (findGuard) {
      CSyntax syntax;
      
      header.queue = new LinkedList<CSyntax>();
      for(;;) {
        syntax = header.stream.scan();
        header.queue.offer(syntax);
        if (CLexer.isType(syntax, sym.EOF)) {
          break;
        }
        
        header.processGuard(syntax);
      }
      header.close();

      if (header.checkGuard() && ! macroTable.contains(header.getGuard())) {
        if (! guards.containsKey(header.file.toString())) {
          guards.put(header.file.toString(), header.getGuard());
        }
        macroTable.rectifyGuard(header.guardMacro, contextManager);
      } else {
        unguarded.add(header.file.toString());
      }
    }
    
    return false;
  }

  /**
   * Get a header descriptor.  If it's from an #include_next directive
   * find the next header after the current one of the same name.
   * This function builds the chain of search paths in the same way as
   * GNU cpp.
   *
   * @param headerName the absolute or relative path of the header.
   * @param sysHeader true if the header is a system header.
   * @param includeNext true if the directive was an #include_next
   * directive, a gcc extension.
   * @return a header descriptor.
   */
  protected PFile findHeader(String headerName, boolean sysHeader,
                             boolean includeNext) {
    if (includeNext && includes.empty()) {
      if (SHOW_ERRORS) {
        System.err.println("error: include_next can only be used in "
                           + "header files");
      }
      
      return null;
    }
    else if (headerName.charAt(0) == '/') {
      File path;
      
      path = new File(headerName);
      
      if (path.exists()) {
        // It's already an absolute path.
        return new PFile(headerName, new File(headerName), false);
      }
      else {
        return null;
      }
    }
    else {
      // Ordered list of search paths.
      List<String> chain;
      // Used with include_next to flag current header path.
      boolean foundCurrentHeader;
      
      chain = new LinkedList<String>();
      
      // User header paths.
      if (! sysHeader) {
        chain.add(include.getPath());
        
        for (String s : iquote) {
          chain.add(s);
        }
      }
      
      // -I paths.
      for (String s : I) {
        chain.add(s);
      }
      
      foundCurrentHeader = false;
      
      // Check user header paths.
      for (String s : chain) {
        File path;
        
        path = new File(s, headerName);

        if (includeNext && (! foundCurrentHeader)) {
          if (path.getParent().equals(include.getPath())) {
            // Skip current header if include_next.
            foundCurrentHeader = true;
          }
        }
        else if (path.exists()) {
          return new PFile(headerName, path, false);
        }
      }
      
      // Check system directories.
      for (String s : sysdirs) {
        File path;
        
        path = new File(s, headerName);
        
        if (includeNext && (! foundCurrentHeader)) {
          if (path.getParent().equals(include.getPath())) {
            foundCurrentHeader = true;
          }
        }
        else if (path.exists()) {
          return new PFile(headerName, path, true);
        }
      }
      
      if (SHOW_ERRORS) {
        System.err.println("error: header " + headerName + " not found");
      }
      
      return null;
    }
  }
  
  /**
   * Include computed header(s).  Take a list of header names and a
   * list of presence conditions.  Note that because macros can be
   * multiply-defined, a single computed include directive may expand
   * to several possible header filenames.
   *
   * @param complete the list of computed includes.
   * @param contexts the list of presence conditions in which the
   * computed includes are valid.
   * @param includeNext whether the computed include was an
   * #include_next directive.
   * @param contextManager the presence condition manager.
   * @param macroTable the macro symbol table.
   * @return the start include delimiter if the header exists, null
   * otherwise.
   */
  public CSyntax includeComputedHeader(List<String> completed,
                                       List<Context> contexts,
                                       boolean includeNext,
                                       ContextManager contextManager,
                                       MacroTable macroTable) {
    includes.push(include);
    
    include = new Computed(completed, contexts, includeNext, contextManager,
                           macroTable);

    if (TRACE_INCLUDES) {
      System.err.println("computed include " + includes.size());
    }
    
    if (STATISTICS) {
      Statistics.max(Statistics.row.MAX_INCLUDE_DEPTH, includes.size(),
                     "COMPUTED");
      Statistics.inc(Statistics.row.COMPUTED);
      if (completed.size() > 1) {
        Statistics.inc(Statistics.row.HOISTED_INCLUDE);
        Statistics.max(Statistics.row.MAX_HOISTED_INCLUDE, completed.size(),
                       "COMPUTED");
      }
    }

    // TODO reference computed object
    return new StartComputedInclude();
  }
  
  /**
   * This class abstracts away both a single header and computed
   * headers that have to include several headers in different
   * context.
   */
  protected abstract static class Include {
    /** Construct a new instance. */
    private Include() { /* Nothing to do. */ }
    
    /**
     * Determine whether the include is a regular one.
     *
     * @return true if it's a regular include.
     */
    public boolean isPFile() {
      return false;
    }
    
    /**
     * Determine whether the include is a computed one.
     *
     * @return true if it's a computed include.
     */
    public boolean isComputed() {
      return false;
    }
    
    /**
     * Determine whether the stream of tokens of the header is done.
     *
     * @return true when it's done.
     */
    abstract public boolean end();
    
    /**
     * Get the full path of the header.
     *
     * @return the name of the header.
     */
    abstract public String getName();
    
    /**
     * Get the parent directory of the header.
     *
     * @return the parent directory of the header.
     */
    abstract public String getPath();
  }
  
  /**
   * A struct to encapsulate the data associated with a regular
   * header.
   */
  public class PFile extends Include {
    /** The name of the header file. */
    public final String name;
    
    /** The file object of the header. */
    public final File file;

    /** Whether it's a system header. */
    public final boolean system;

    /** The stream of tokens from the header. */
    protected Stream stream;

    /**
     * The macro that guards the header.  null if there is no guard
     * macro.
     */
    protected String guardMacro;

    /** The file reader. */
    protected BufferedReader fileReader;

    /**
     * The queue is used to stored tokens when looking for a guard
     * macro.
     */
    protected Queue<CSyntax> queue;
    
    /**
     * A list containing the first and second syntax of the file.
     * Used for checking for a guard macro
     */
    protected List<CSyntax> guard;
    
    /**
     * Hang onto the previous syntax to find last syntax in the file.
     * Used for checking for a guard macro
     */
    protected CSyntax buffer;
    
    /** Construct a new header descriptor. */
    public PFile(String name, File file, boolean system) {
      this.name = name;
      this.file = file;
      this.system = system;
      this.guard = new LinkedList<CSyntax>();
      this.buffer = null;
      this.guardMacro = null;
    }
    
    /** Open the file reader. */
    public void open() throws FileNotFoundException {
      fileReader = new BufferedReader(new FileReader(file));
      stream = new DirectiveStream(new CLexerStream(fileReader), getName());
    }
    
    /**
     * Open the file reader.
     *
     * @param in an already open reader.
     */
    public void open(Reader in) {
      stream = new DirectiveStream(new CLexerStream(in), getName());
    }
    
    /** Close the reader and streams. */
    public void close() {
      if (null != stream) {
        try {
          fileReader.close();
        }
        catch (Exception e) {
          e.printStackTrace();
        }
        ((DirectiveStream) stream).stream = null;
        stream = null;
      }
    }
    
    /**
     * Get the stream of tokens.
     *
     * @return the stream of tokens.
     */
    public Stream stream() {
      return stream;
    }
    
    /**
     * Get the next token from the stream.
     *
     * @return the next token.
     */
    public CSyntax scan() {
      if (null == queue) {
        return stream.scan();
      }
      else {
        return queue.poll();
      }
    }
    
    /**
     * This routine collects the first, second, and last syntax of the
     * file to check for a guard macro
     *
     * @param the current token.
     */
    public void processGuard(CSyntax syntax) {
      if (syntax.isToken() || syntax.isDirective()) {
        //save first two syntax
        if (guard.size() == 0) {
          guard.add(syntax);
        }
        else if (guard.size() == 1) {
          guard.add(syntax);
        }

        //save last syntax
        buffer = syntax;
      }
    }
    
    /**
     * Check for guard macro.  Get the guard macro name with
     * getGuard().
     *
     * @return true if the header has a guard.
     */
    public boolean checkGuard() {
      for (;;) {
        if (guard.size() == 2 && null != buffer) {
          Directive first, second, last;
          String ifndef, define;
          List<CSyntax> list;
          int s;
          
          if (! guard.get(0).isDirective() || ! guard.get(1).isDirective()
              || ! buffer.isDirective()) {
            break;
          }
          
          first = (Directive) guard.get(0);
          second = (Directive) guard.get(1);
          last = (Directive) buffer;
          
          if (first.kind() != Kind.IFNDEF || second.kind() != Kind.DEFINE
              || last.kind() != Kind.ENDIF) {
            break;
          }
          
          list = first.getList();
          s = first.getOffset();
          
          // Move past the whitespace after the directive name.
          while (s < list.size() && list.get(s).isLayout()) s++;
          
          if (s >= list.size()) {
            break;
          }

          if (! (list.get(s).isIdentifier() || list.get(s).isKeyword())) {
            break;
          }
          
          ifndef = CLexer.getString(list.get(s));
          
          list = second.getList();
          s = second.getOffset();
          
          // Move past the whitespace after the directive name.
          while (s < list.size() && list.get(s).isLayout()) s++;

          if (s >= list.size()) {
            break;
          }

          if (! (list.get(s).isIdentifier() || list.get(s).isKeyword())) {
            break;
          }
          
          define = CLexer.getString(list.get(s));

          if (! ifndef.equals(define)) {
            break;
          }
          
          this.guardMacro = define;
          
          return true;
        }
        
        break;
      }
      
      return false;
    }
    
    /**
     * Return the guard macro name if it has one.  Must be called after
     * checkGuard.
     *
     * @return the guard macro name, null otherwise.
     */
    public String getGuard() {
      return guardMacro;
    }

    public boolean isPFile() {
      return true;
    }
    
    public boolean end() {
      return stream.end();
    }
    
    public String getName() {
      return file.toString();
    }
    
    public String getPath() {
      return file.getParent();
    }
  }
  
  /**
   * The descriptor for a computed header.  Because macros can be
   * implicitly conditional, a computed header may expand to multiple
   * header file names.
   */
  protected class Computed extends Include {
    /** The list of header file names. */
    protected List<String> completed;

    /** The presence condition of each header file name. */
    protected List<Context> contexts;

    /**
     * Whether the computed header was from an #include_next
     * directive.
     */
    protected boolean includeNext;

    /** A pointer to the presence condition manager. */
    protected ContextManager contextManager;
    
    /** A pointer to the macro symbol table. */
    protected MacroTable macroTable;

    /** Whether we are at the end of all headers. */
    protected boolean end;

    /** The current header descriptor. */
    protected PFile pfile;

    /** The index of the current header file name. */
    protected int i;
    
    /** The current stream of tokens. */
    public Stream stream;
    
    /** Construct a new computed header. */
    public Computed(List<String> completed, List<Context> contexts,
                    boolean includeNext, ContextManager contextManager,
                    MacroTable macroTable) {
      this.completed = completed;
      this.contexts = contexts;
      this.includeNext = includeNext;
      this.contextManager = contextManager;
      this.macroTable = macroTable;
      this.end = false;
      this.pfile = null;
      this.stream = null;
      this.i = -1; // We increment before checking the filename.
    }
    
    /**
     * Get the next token from the stream.
     *
     * @return the next token.
     */
    public CSyntax scan() {
      if (null == pfile) {
        pfile = null;
        
        // Keep trying until we get a valid header.
        while (null == pfile && i < (completed.size() - 1)) {
          char first, last;
          String headerName;
          boolean sysHeader;
          String str;
          
          // Get the next header file.
          i++;
          
          str = completed.get(i);
          
          first = str.charAt(0);
          last = str.charAt(str.length() - 1);
          
          for (;;) {
            sysHeader = false;
            if ('<' == first && '>' == last) {
              sysHeader = true;
            }
            else if ('"' == first && '"' == last) {
              // A user header.
            }
            else {
              // Error.  Each header file name either needs quotes or
              // brackets.
              break;
            }
            
            headerName = str.substring(1, str.length() - 1);
            pfile = findHeader(headerName, sysHeader, includeNext);
            
            break;
          }
          
          if (null == pfile) {
            if (SHOW_ERRORS) {
              System.err.println("error: invalid header from computed "
                                 + "include, " + str);
            }
            // File does not exist or invalid header string.  Try the
            // next header file name.
            continue;
          }
          else {
            // Open the file or guard it.
            boolean guarded;
            
            guarded = openHeader(pfile, contextManager, macroTable);
            
            if (guarded) {
              pfile = null;
              
              // Guarded, so move to next one.
              continue;
            }
            else {
              return new If(contexts.get(i));
            }
          }
        }  // While invalid pfile.
        
        // When we get here, there are no more valid headers.
        end = true;
        
        return Layout.EMPTY;
      }
      else {
        CSyntax syntax;
      
        try {
          syntax = pfile.scan();
        }
        catch (Error e) {
          System.err.println(pfile.file);
          e.printStackTrace();
          System.exit(-1);
          syntax = null;
        }
    
        if (CLexer.isType(syntax, sym.EOF) && (! includes.empty())) {
          if (pfile.checkGuard()) {
            if (! guards.containsKey(pfile.file.toString())) {
              guards.put(pfile.file.toString(), pfile.getGuard());
            }
          }
          pfile.close();
          // Make it null so we can move on in the next scan.
          pfile = null;
          
          syntax = new EndConditional();

          if (TRACE_INCLUDES) {
            System.err.println("back to computed " + includes.size());
          }
        }
        else {
          pfile.processGuard(syntax);
        }
        
        return syntax;
      }
    }
    
    public boolean isComputed() {
      return true;
    }
    
    public boolean end() {
      return end;
    }
    
    public String getName() {
      if (null != pfile) {
        return pfile.getName();
      }
      else {
        return "";
      }
    }
    
    public String getPath() {
      if (null == pfile) {
        // The subheaders use the current directory in which the
        // computed include lives.
        return includes.peek().getPath();
      }
      else {
        return pfile.file.getParent();
      }
    }
  }
  
  
  /** This class wraps the lexer in a Stream */
  public static class CLexerStream implements Stream {
    /** The lexer. */
    CLexer scanner;

    /** Whether the stream is over. */
    boolean end;
    
    /** Create a new stream. */
    public CLexerStream(Reader in) {
      this.scanner = new CLexer(in);
      this.end = false;
    }
    
    public CSyntax scan() {
      if (! end()) {
        try {
          CSyntax syntax;
          
          syntax = scanner.yylex();
          end = CLexer.isType(syntax, sym.EOF);
          
          return syntax;
        }
        catch (IOException e) {
          e.printStackTrace();
          System.exit(-1);
        }
      }
      
      return null;
    }
    
    public boolean end() {
      return end;
    }
  }
  
  
  /** This class collects the tokens of preprocessor directives */
  public static class DirectiveStream implements Stream {
    /** The input stream of tokens. */
    Stream stream;
    
    // TODO need to handle saving the filename better.
    /** The filename. */
    String filename;

    /** We are at the beginning of a newline. */
    protected boolean newline;
   
    /** Create a new directive parser stream. */
    public DirectiveStream(Stream stream, String filename) {
      this.stream = stream;
      this.filename = filename;
      this.newline = true;
    }
    
    /**
     * This function parses preprocessor directives.  The has must
     * occur at the beginning of the new line, which is why we must
     * keep a flag indicating whether this is so.
     *
     * The function returns either the next Yytoken
     * from the lexer or a Directive which the function has parsed.
     *
     * @return the next token or compound token.
     */
    public CSyntax scan() {
      CSyntax syntax = stream.scan();
      
      // Parse the directive.
      if (newline && syntax.isToken() && ((Token) syntax).isType(sym.HASH)) {
        List<CSyntax> list;
        Directive directive;
        
        list = new ArrayList<CSyntax>();
        
        list.add(syntax);
        do {
          syntax = stream.scan();
          syntax.file = filename;
          list.add(syntax);
          if (syntax.isLayout() && ((Layout) syntax).hasNewline()) {
            break;
          }
          else if (syntax.isToken() && ((Token) syntax).isType(sym.EOF)) {
            break;
          }
        } while (true);
        
        directive = new Directive(
          list,
          list.get(0).line,
          list.get(0).charBegin,
          list.get(list.size() - 1).charEnd
        );
        directive.file = filename;
        newline = true;
  
        return directive;
      }
      else {
        // Check if we go to a newline.
        newline = syntax.isLayout() && ((Layout) syntax).hasNewline();
        
        return syntax;
      }
    }
    
    public boolean end() {
      return stream.end();
    }
  }
}
