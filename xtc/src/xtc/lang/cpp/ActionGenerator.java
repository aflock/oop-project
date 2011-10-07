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

import java.lang.*;
import java.io.*;
import java.util.*;

/**
 * This class generates <code>NodeType</code>.  It takes from stdin
 * a node name and its type, either "list" or "action", separated by
 * a space, one node name per line.
 *
 * @author Paul Gazzillo
 * @version $Revision: 1.5 $
 */
public class ActionGenerator {
  public static void main(String args[]) throws Exception {
    BufferedReader inputStream = null;
    PrintWriter outputStream = null;
    
    try {
      String l;
      HashSet<Integer> list;
      HashMap<Integer, String> action;
      HashSet<Integer> layout;
      HashSet<Integer> passthrough;
      HashSet<Integer> complete;

      outputStream = new PrintWriter(System.out);
      
      outputStream.print("" +
"/*\n" +
" * xtc - The eXTensible Compiler\n" +
" * Copyright (C) 2009-2011 New York University\n" +
" *\n" +
" * This library is free software; you can redistribute it and/or\n" +
" * modify it under the terms of the GNU Lesser General Public License\n" +
" * version 2.1 as published by the Free Software Foundation.\n" +
" *\n" +
" * This library is distributed in the hope that it will be useful,\n" +
" * but WITHOUT ANY WARRANTY; without even the implied warranty of\n" +
" * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU\n" +
" * Lesser General Public License for more details.\n" +
" *\n" +
" * You should have received a copy of the GNU Lesser General Public\n" +
" * License along with this library; if not, write to the Free Software\n" +
" * Foundation, 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301,\n" +
" * USA.\n" +
" */\n" +
"package xtc.lang.cpp;\n" + 
"\n" +  
"/** This class is generated and provides node type support.\n" +
"  */\n" +
"public class CActions {\n" +
"  public enum NodeType {\n" +
"    DEFAULT, LIST, ACTION, LAYOUT, PASS_THROUGH\n" +
"  }\n" +
"\n"
      );

      inputStream = new BufferedReader(new InputStreamReader(System.in));

      list = new HashSet<Integer>();
      layout = new HashSet<Integer>();
      action = new HashMap<Integer, String>();
      passthrough = new HashSet<Integer>();
      complete = new HashSet<Integer>();
      while ((l = inputStream.readLine()) != null) {
        String[] a = l.split(" ");
        String name = a[0];
        String type = a[1];
        int sym = -1;
        
        for (int i = 0; i < ForkMergeParserTables.yytname.table.length; i++) {
          if (ForkMergeParserTables.yytname.table[i].equals(name)) {
            sym = i;
            break;
          }
        }
        
        if (sym >= 0) {
          if (type.equals("list")) {
            list.add(sym);
          } else if (type.equals("layout")) {
            layout.add(sym);
          } else if (type.equals("action")) {
            action.put(sym, name);
          } else if (type.equals("passthrough")) {
            passthrough.add(sym);
          } else if (type.equals("complete")) {
            complete.add(sym);
          } else {
            System.err.println("error: node " + name + " has unknown " +
              "type " + type);
          }
        } else {
          System.err.println("error: there is no node " + name + " in the " +
            "grammar");
        }
      }

      outputStream.print("" + 
"  public static NodeType[] nodeType = new NodeType[] {\n");
      for (int i = 0; i < ForkMergeParserTables.yytname.table.length; i++) {
        String delim;
        
        if (i < ForkMergeParserTables.yytname.table.length - 1) {
          delim = ",";
        } else {
          delim = "";
        }
        
        if (list.contains(i)) {
          outputStream.print("" +
"    NodeType.LIST" + delim + " /* " + ForkMergeParserTables.yytname.table[i] + " */\n"); 
        } else if (layout.contains(i)) {
          outputStream.print("" +
"    NodeType.LAYOUT" + delim + " /* " + ForkMergeParserTables.yytname.table[i] + " */\n"); 
        } else if (action.containsKey(i)) {
          outputStream.print("" +
"    NodeType.ACTION" + delim + " /* " + ForkMergeParserTables.yytname.table[i] + " */\n"); 
        } else if (passthrough.contains(i)) {
          outputStream.print("" +
"    NodeType.PASS_THROUGH" + delim + " /* " + ForkMergeParserTables.yytname.table[i] + " */\n"); 
        } else {
          outputStream.print("" +
"    NodeType.DEFAULT" + delim + " /* " + ForkMergeParserTables.yytname.table[i] + " */\n"); 
        }
      }

      outputStream.print("" +
"    };\n" +
"\n");

      outputStream.print("" + 
"    public static boolean isComplete(int sym) {\n" +
"      switch(sym) {\n");
      for (Integer i : complete) {
        outputStream.print("" +
"      case " + i + ": " + "/* " + ForkMergeParserTables.yytname.table[i] + " */\n"); 
        outputStream.print("" +
"        return true;\n"); 
      }
      outputStream.print("" +
"      default:\n" +
"        return false;\n" +
"      }\n" +
"    }\n" +
"\n");

      outputStream.print("" + 
"    public static void dispatchAction(ForkMergeParser.Subparser point, int sym, ActionInterface actions) {\n" +
"      switch(sym) {\n");
      for (Integer i : action.keySet()) {
        String name = action.get(i);
        
        outputStream.print("" +
"      case " + i + ": " + "/* " + ForkMergeParserTables.yytname.table[i] + " */\n"); 
        outputStream.print("" +
"        actions." + name + "(point);\n" +
"        break;\n"); 
      }
      outputStream.print("" +
"      }\n" +
"    }\n" +
"\n");

      outputStream.print("" + 
"    public static interface ActionInterface {\n");
      for (Integer i : action.keySet()) {
        outputStream.print("" +
"      public void " + action.get(i) + "(ForkMergeParser.Subparser point);\n"); 
      }
      outputStream.print("" +
"    }\n" +
"\n");

      outputStream.print("" + 
"}\n");

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      if (inputStream != null) {
          inputStream.close();
      }
      if (outputStream != null) {
          outputStream.close();
      }
    }
    
  }
}
