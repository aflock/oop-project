/*
 * xtc - The eXTensible Compiler
 * Copyright (C) 2011 Robert Grimm, New York University
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

import xtc.lang.cpp.ContextManager.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Support for syntax objects.  This class is not yet used.
 *
 * Each syntax object supports a number of flags, efficiently
 * implemented through a bit mask.  Their main usage is by the
 * preprocessor to control macro expansion.
 *
 * @author Robert Grimm, Paul Gazzillo
 * @version $Revision: 1.2 $
 */
public abstract class Syntax extends xtc.tree.Token {

  /** The kinds of syntax objects. */
  public static enum Kind {
    /** Layout. */
    LAYOUT,
    /** A language token. */
    LANGUAGE,
    /** A preprocessor directive. */
    DIRECTIVE,
    /** The internal representation of a conditional. */
    CONDITIONAL
  }

  // --------------------------------------------------------------------------

  /**
   * The maximum flag number.  Flags are numbered from 0 to this
   * number.
   */
  public static final int MAX_FLAGS = 31;

  /** The flags. */
  private int flags;

  /** Create a new syntax object. */
  public Syntax() {
    // Nothing to do.
  }

  /**
   * Create a copy of the specified syntax object, including its
   * flags.
   *
   * @param other The syntax object to copy.
   */
  protected Syntax(Syntax other) {
    this.flags = other.flags;
  }

  /**
   * Copy this syntax object, including its flags.
   *
   * @return A copy.
   */
  public abstract Syntax copy();

  /**
   * Get this syntax object's kind.
   *
   * @return The kind.
   */
  public abstract Kind kind();

  /**
   * Convert this syntax object to layout.
   *
   * @return This syntax object as layout.
   * @throws ClassCastException Signals that this syntax object is not
   *   layout.
   */
  public Layout toLayout() {
    throw new ClassCastException("Not layout: " + this);
  }

  /**
   * Convert this syntax object to a language token.
   *
   * @return This syntax object as a language token.
   * @throws ClassCastException Signals that this syntax object is not
   *   a language token.
   */
  public Language<?> toLanguage() {
    throw new ClassCastException("Not a language token: " + this);
  }
  
  /**
   * Convert this syntax object to a preprocessor directive.
   *
   * @return This syntax object as a directive.
   * @throws ClassCastException Signals that this syntax object is not
   *   a directive.
   */
  public Directive toDirective() {
    throw new ClassCastException("Not a directive: " + this);
  }

  /**
   * Convert this syntax object to a conditional.
   *
   * @return This syntax object as a conditional.
   * @throws ClassCastException Signals that this syntax object is not
   *   a conditional.
   */
  public Conditional toConditional() {
    throw new ClassCastException("Not a conditional: " + this);
  }

  private int toMask(int num) {
    if (0 > num || MAX_FLAGS < num) {
      throw new IllegalArgumentException("Invalid flag number: " + num);
    }
    return 1 << num;
  }

  /** Clear all flags. */
  public void clearFlags() {
    flags = 0;
  }

  /**
   * Clear the specified flag.
   *
   * @param num The flag number.
   * @throws IllegalArgumentException Signals an invalid flag number.
   */
  public void clearFlag(int num) {
    flags |= toMask(num);
  }

  /**
   * Set the specified flag.
   *
   * @param num The flag number.
   * @throws IllegalArgumentException Signals an invalid flag number.
   */
  public void setFlag(int num) {
    flags &= ~toMask(num);
  }

  /**
   * Test the specified flag.
   *
   * @param num The flag number.
   * @return true if the flag is set.
   * @throws IllegalArgumentException Signals an invalid flag number.
   */
  public boolean testFlag(int num) {
    final int mask = toMask(num);
    return (flags & mask) == mask;
  }

  // --------------------------------------------------------------------------

  /** Layout. */
  public static class Layout extends Syntax {
    
    private final String text;

    /**
     * Create a new layout object.
     *
     * @param text The text.
     */
    public Layout(String text) {
      this.text = text;
    }

    private Layout(Layout other) {
      super(other);
      this.text = other.text;
    }

    public Layout copy() {
      return new Layout(this);
    }

    public Kind kind() {
      return Kind.LAYOUT;
    }

    public Layout toLayout() {
      return this;
    }

    public String getTokenText() {
      return text;
    }

  }

  // --------------------------------------------------------------------------

  /** The interface for language tags. */
  public static interface LanguageTag {

    /**
     * Determine whether the tag has a name.  Keywords and identifiers
     * have names and thus can be replaced by macro expansion in the
     * preprocessor.  Punctuation does not have a name.
     *
     * @return true if the tag has a name.
     */
    public boolean hasName();

    /**
     * Get the tag's ID.  The returned number is used by the LR parser
     * to identify the language token.
     *
     * @return The ID.
     */
    public int getID();

    /**
     * Get the tag's text.  For language tokens with a fixed text,
     * this method returns that text.  Otherwise, it returns null.
     *
     * @return The text.
     */
    public String getText();

  }

  /** A language token. */
  public static class Language<Tag extends Enum<Tag> & LanguageTag>
    extends Syntax {

    protected final Tag tag;

    /**
     * Create a new language token.
     *
     * @param tag The tag.
     */
    public Language(Tag tag) {
      this.tag = tag;
    }

    /**
     * Create a copy of another language token.
     *
     * @param other The other language token.
     */
    protected Language(Language<Tag> other) {
      super(other);
      this.tag = other.tag;
    }

    public Language<Tag> copy() {
      return new Language<Tag>(this);
    }

    public Kind kind() {
      return Kind.LANGUAGE;
    }

    public Language<Tag> toLanguage() {
      return this;
    }

    /**
     * Get the tag.
     *
     * @return The tag.
     */
    public Tag tag() {
      return tag;
    }

    public String getTokenText() {
      return tag.getText();
    }

  }

  /**
   * A language token with variable text, such as identifiers and
   * literals.
   */
  public static class Text<Tag extends Enum<Tag> & LanguageTag>
    extends Language<Tag> {

    private final String text;

    /**
     * Create a new text token.
     *
     * @param The tag.
     * @param The text.
     */
    public Text(Tag tag, String text) {
      super(tag);
      this.text = text;
    }

    private Text(Text<Tag> other) {
      super(other);
      this.text = other.text;
    }

    public Text<Tag> copy() {
      return new Text<Tag>(this);
    }

    public String getTokenText() {
      return text;
    }

  }

  // --------------------------------------------------------------------------

  /** The directive tag. */
  public static enum DirectiveTag {
    IF("#if"),
    IFDEF("#ifdef"),
    IFNDEF("#ifndef"),
    ELIF("#elif"),
    ELSE("#else"),
    ENDIF("#endif"),
    INCLUDE("#include"),
    DEFINE("#define"),
    UNDEFINE("#undefine"),
    LINE("#line"),
    LINEMARKER("#"),
    ERROR("#error"),
    WARNING("#warning"),
    PRAGMA("#pragma");

    private String text;

    private DirectiveTag(String text) {
      this.text = text;
    }

    public String getText() {
      return text;
    }

  }

  /** A preprocessor directive. */
  public static class Directive extends Syntax {

    private final DirectiveTag tag;
    private final List<Language<?>> tokens;

    public Directive(DirectiveTag tag, List<Language<?>> tokens) {
      this.tag = tag;
      this.tokens = tokens;
    }

    private Directive(Directive other) {
      super(other);
      this.tag = other.tag;
      this.tokens = new ArrayList<Language<?>>(other.tokens);
    }

    public Directive copy() {
      return new Directive(this);
    }

    public Kind kind() {
      return Kind.DIRECTIVE;
    }

    public Directive toDirective() {
      return this;
    }

    public DirectiveTag tag() {
      return tag;
    }

    public int size() {
      return 1 + tokens.size();
    }

    public Object get(int index) {
      if (0 == index) {
        return tag.getText();
      } else if (1 <= index && index < tokens.size()) {
        return tokens.get(index);
      } else {
        throw new IndexOutOfBoundsException("Index: " + index + 
                                            ", Size: " + tokens.size());
      }
    }

    public String getTokenText() {
      StringBuffer buf = new StringBuffer();
      buf.append(tag.getText());

      Iterator<Language<?>> iter = tokens.iterator();
      while (iter.hasNext()) {
        buf.append(' '); // FIXME: Should we have a space here?
        buf.append(iter.next().getTokenText());
      }

      return buf.toString();
    }

  }

  // --------------------------------------------------------------------------

  /** The conditional tag. */
  public static enum ConditionalTag {
    /** The start of a conditional. */
    START,
    /** The next branch of a conditional. */
    NEXT,
    /** The end of a conditional. */
    END
  }

  /**
   * A conditional.  This class provides the internal, normalized
   * representation of preprocessor conditional directives.
   */
  public static class Conditional extends Syntax {

    protected final ConditionalTag tag;
    protected final Context context;

    /**
     * Create a new conditional token.
     *
     * @param tag The tag.
     * @param context The context.
     * @throws IllegalArgumentException Signals that the context is
     *   not null for an end conditional.
     */
    public Conditional(ConditionalTag tag, Context context) {
      if (ConditionalTag.END == tag && null != context) {
        throw new IllegalArgumentException("End conditional with context");
      }
      this.tag = tag;
      this.context = context;
    }

    private Conditional(Conditional other) {
      super(other);
      this.tag = other.tag;
      this.context = other.context; // FIXME check for correctness.
    }

    public Conditional copy() {
      return new Conditional(this.tag, this.context);
    }

    public Kind kind() {
      return Kind.CONDITIONAL;
    }

    public Conditional toConditional() {
      return this;
    }

    /**
     * Get the tag.
     *
     * @return The tag.
     */
    public ConditionalTag tag() {
      return tag;
    }

    /**
     * Get the context.
     *
     * @return The context.
     * @throws IllegalStateException Signals that this conditional
     *   token terminates a conditional.
     */
    public Context context() {
      if (ConditionalTag.END == tag) {
        throw new IllegalStateException("End conditional has no context");
      }
      return context;
    }

    public String getTokenText() {
      return context.toString(); // FIXME
    }

  }

}
