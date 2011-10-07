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

/**
 * A stream that filters out all tokens except for regular and
 * compound tokens.
 *
 * @author Paul Gazzillo
 */
public class TokenFilter implements Stream {
  /** The stream to read syntax from. */
  Stream stream;

  /**
   * Create a new token filter stream.
   *
   * @param stream The stream to filter.
   */
  public TokenFilter(Stream stream) {
    this.stream = stream;
  }

  public CSyntax scan() {
    CSyntax syntax = this.stream.scan();
      
    while (! syntax.isToken() && ! syntax.isConditional()) {
      syntax = this.stream.scan();
    }

    return syntax;
  }

  public boolean end() {
    return this.stream.end();
  }
}
