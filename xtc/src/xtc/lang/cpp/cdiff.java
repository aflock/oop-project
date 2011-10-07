/*
 * xtc - The eXTensible Compiler
 * Copyright (C) 2009-2011 New York University
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 2 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301,
 * USA.
 */
package xtc.lang.cpp;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

import xtc.lang.cpp.HeaderFileManager.CLexerStream;
import xtc.lang.cpp.HeaderFileManager.DirectiveStream;

import xtc.lang.cpp.CSyntax.Token;

import xtc.lang.cpp.ForkMergeParserTables.sym;

/**
 * Token-based diff.
 *
 * @author Paul Gazzillo
 * @version $Revision: 1.12 $
 */
public class cdiff {
  /**
   * Determine whether two files' tokens differ.  Each file is lexed
   * into C tokens and compared token-by-token, ignoring whitespace.
   * If the files are the same, the exit code is 0.  If they differ,
   * the location in each file of the first difference is emitted and
   * the return code is 1.
   */
  public static void main(String[] args) {
    if (args.length < 2) {
      System.err.println("cdiff: compares two C files token-by-token");
      System.err.println("USAGE: java xtc.lang.cpp.cdiff file1 file2");
      System.exit(-1);
    }
    
    try {
      File file1 = new File(args[0]);
      File file2 = new File(args[1]);
      BufferedReader reader1 = new BufferedReader(new FileReader(file1));
      BufferedReader reader2 = new BufferedReader(new FileReader(file2));
      DirectiveStream stream1
        = new DirectiveStream(new CLexerStream(reader1), file1.toString());
      DirectiveStream stream2
        = new DirectiveStream(new CLexerStream(reader2), file2.toString());
      boolean end1 = false;
      boolean end2 = false;
      CSyntax syntax1 = null;
      CSyntax syntax2 = null;
      
      while (! (end1 || end2)) {
        do {
          syntax1 = stream1.scan();
        } while (null == syntax1 || ! syntax1.isToken());
        
        do {
          syntax2 = stream2.scan();
        } while (null == syntax2 || ! syntax2.isToken());
        
        if (! CLexer.getString(syntax1).equals(CLexer.getString(syntax2))) {
          System.out.println(file1);
          System.out.println(syntax1);
          System.out.println(syntax1.line);
          System.out.println();
          System.out.println(file2);
          System.out.println(syntax2);
          System.out.println(syntax2.line);
          System.out.println();
          System.out.println("different");
          System.exit(1);
        }
        
        end1 = CLexer.isType(syntax1, sym.EOF);
        end2 = CLexer.isType(syntax2, sym.EOF);
      }
      
      System.exit(0);

    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}
