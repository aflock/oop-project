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

/** This class is generated and provides node type support.
  */
public class CActions {
  public enum NodeType {
    DEFAULT, LIST, ACTION, LAYOUT, PASS_THROUGH
  }

  public static NodeType[] nodeType = new NodeType[] {
    NodeType.DEFAULT, /* $end */
    NodeType.DEFAULT, /* error */
    NodeType.DEFAULT, /* $undefined */
    NodeType.DEFAULT, /* AUTO */
    NodeType.DEFAULT, /* DOUBLE */
    NodeType.DEFAULT, /* INT */
    NodeType.DEFAULT, /* STRUCT */
    NodeType.DEFAULT, /* BREAK */
    NodeType.DEFAULT, /* ELSE */
    NodeType.DEFAULT, /* LONG */
    NodeType.DEFAULT, /* SWITCH */
    NodeType.DEFAULT, /* CASE */
    NodeType.DEFAULT, /* ENUM */
    NodeType.DEFAULT, /* REGISTER */
    NodeType.DEFAULT, /* TYPEDEF */
    NodeType.DEFAULT, /* CHAR */
    NodeType.DEFAULT, /* EXTERN */
    NodeType.DEFAULT, /* RETURN */
    NodeType.DEFAULT, /* UNION */
    NodeType.DEFAULT, /* CONST */
    NodeType.DEFAULT, /* FLOAT */
    NodeType.DEFAULT, /* SHORT */
    NodeType.DEFAULT, /* UNSIGNED */
    NodeType.DEFAULT, /* CONTINUE */
    NodeType.DEFAULT, /* FOR */
    NodeType.DEFAULT, /* SIGNED */
    NodeType.DEFAULT, /* VOID */
    NodeType.DEFAULT, /* DEFAULT */
    NodeType.DEFAULT, /* GOTO */
    NodeType.DEFAULT, /* SIZEOF */
    NodeType.DEFAULT, /* VOLATILE */
    NodeType.DEFAULT, /* DO */
    NodeType.DEFAULT, /* IF */
    NodeType.DEFAULT, /* STATIC */
    NodeType.DEFAULT, /* WHILE */
    NodeType.DEFAULT, /* IDENTIFIER */
    NodeType.DEFAULT, /* STRINGliteral */
    NodeType.DEFAULT, /* FLOATINGconstant */
    NodeType.DEFAULT, /* INTEGERconstant */
    NodeType.DEFAULT, /* CHARACTERconstant */
    NodeType.DEFAULT, /* OCTALconstant */
    NodeType.DEFAULT, /* HEXconstant */
    NodeType.DEFAULT, /* TYPEDEFname */
    NodeType.DEFAULT, /* ARROW */
    NodeType.DEFAULT, /* ICR */
    NodeType.DEFAULT, /* DECR */
    NodeType.DEFAULT, /* LS */
    NodeType.DEFAULT, /* RS */
    NodeType.DEFAULT, /* LE */
    NodeType.DEFAULT, /* GE */
    NodeType.DEFAULT, /* EQ */
    NodeType.DEFAULT, /* NE */
    NodeType.DEFAULT, /* ANDAND */
    NodeType.DEFAULT, /* OROR */
    NodeType.DEFAULT, /* ELLIPSIS */
    NodeType.DEFAULT, /* MULTassign */
    NodeType.DEFAULT, /* DIVassign */
    NodeType.DEFAULT, /* MODassign */
    NodeType.DEFAULT, /* PLUSassign */
    NodeType.DEFAULT, /* MINUSassign */
    NodeType.DEFAULT, /* LSassign */
    NodeType.DEFAULT, /* RSassign */
    NodeType.DEFAULT, /* ANDassign */
    NodeType.DEFAULT, /* ERassign */
    NodeType.DEFAULT, /* ORassign */
    NodeType.LAYOUT, /* LPAREN */
    NodeType.LAYOUT, /* RPAREN */
    NodeType.LAYOUT, /* COMMA */
    NodeType.DEFAULT, /* HASH */
    NodeType.DEFAULT, /* DHASH */
    NodeType.LAYOUT, /* LBRACE */
    NodeType.LAYOUT, /* RBRACE */
    NodeType.LAYOUT, /* LBRACK */
    NodeType.LAYOUT, /* RBRACK */
    NodeType.LAYOUT, /* DOT */
    NodeType.DEFAULT, /* AND */
    NodeType.DEFAULT, /* STAR */
    NodeType.DEFAULT, /* PLUS */
    NodeType.DEFAULT, /* MINUS */
    NodeType.DEFAULT, /* NEGATE */
    NodeType.DEFAULT, /* NOT */
    NodeType.DEFAULT, /* DIV */
    NodeType.DEFAULT, /* MOD */
    NodeType.DEFAULT, /* LT */
    NodeType.DEFAULT, /* GT */
    NodeType.DEFAULT, /* XOR */
    NodeType.DEFAULT, /* PIPE */
    NodeType.LAYOUT, /* QUESTION */
    NodeType.LAYOUT, /* COLON */
    NodeType.LAYOUT, /* SEMICOLON */
    NodeType.LAYOUT, /* ASSIGN */
    NodeType.DEFAULT, /* ASMSYM */
    NodeType.DEFAULT, /* _BOOL */
    NodeType.DEFAULT, /* _COMPLEX */
    NodeType.DEFAULT, /* RESTRICT */
    NodeType.DEFAULT, /* __ALIGNOF */
    NodeType.DEFAULT, /* __ALIGNOF__ */
    NodeType.DEFAULT, /* ASM */
    NodeType.DEFAULT, /* __ASM */
    NodeType.DEFAULT, /* __ASM__ */
    NodeType.DEFAULT, /* __ATTRIBUTE */
    NodeType.DEFAULT, /* __ATTRIBUTE__ */
    NodeType.DEFAULT, /* __BUILTIN_OFFSETOF */
    NodeType.DEFAULT, /* __BUILTIN_TYPES_COMPATIBLE_P */
    NodeType.DEFAULT, /* __BUILTIN_VA_ARG */
    NodeType.DEFAULT, /* __BUILTIN_VA_LIST */
    NodeType.DEFAULT, /* __COMPLEX__ */
    NodeType.DEFAULT, /* __CONST */
    NodeType.DEFAULT, /* __CONST__ */
    NodeType.DEFAULT, /* __EXTENSION__ */
    NodeType.DEFAULT, /* INLINE */
    NodeType.DEFAULT, /* __INLINE */
    NodeType.DEFAULT, /* __INLINE__ */
    NodeType.DEFAULT, /* __LABEL__ */
    NodeType.DEFAULT, /* __RESTRICT */
    NodeType.DEFAULT, /* __RESTRICT__ */
    NodeType.DEFAULT, /* __SIGNED */
    NodeType.DEFAULT, /* __SIGNED__ */
    NodeType.DEFAULT, /* __THREAD */
    NodeType.DEFAULT, /* TYPEOF */
    NodeType.DEFAULT, /* __TYPEOF */
    NodeType.DEFAULT, /* __TYPEOF__ */
    NodeType.DEFAULT, /* __VOLATILE */
    NodeType.DEFAULT, /* __VOLATILE__ */
    NodeType.DEFAULT, /* PPNUM */
    NodeType.DEFAULT, /* $accept */
    NodeType.PASS_THROUGH, /* TranslationUnit */
    NodeType.LIST, /* ExternalDeclarationList */
    NodeType.PASS_THROUGH, /* ExternalDeclaration */
    NodeType.DEFAULT, /* EmptyDefinition */
    NodeType.PASS_THROUGH, /* FunctionDefinitionExtension */
    NodeType.DEFAULT, /* FunctionDefinition */
    NodeType.DEFAULT, /* FunctionDeclarator */
    NodeType.DEFAULT, /* FunctionOldPrototype */
    NodeType.PASS_THROUGH, /* DeclarationExtension */
    NodeType.DEFAULT, /* Declaration */
    NodeType.DEFAULT, /* DefaultDeclaringList */
    NodeType.DEFAULT, /* DeclaringList */
    NodeType.PASS_THROUGH, /* DeclarationSpecifier */
    NodeType.PASS_THROUGH, /* TypeSpecifier */
    NodeType.LIST, /* DeclarationQualifierList */
    NodeType.LIST, /* TypeQualifierList */
    NodeType.DEFAULT, /* DeclarationQualifier */
    NodeType.DEFAULT, /* TypeQualifier */
    NodeType.DEFAULT, /* ConstQualifier */
    NodeType.DEFAULT, /* VolatileQualifier */
    NodeType.DEFAULT, /* RestrictQualifier */
    NodeType.DEFAULT, /* FunctionSpecifier */
    NodeType.DEFAULT, /* BasicDeclarationSpecifier */
    NodeType.DEFAULT, /* BasicTypeSpecifier */
    NodeType.DEFAULT, /* SUEDeclarationSpecifier */
    NodeType.DEFAULT, /* SUETypeSpecifier */
    NodeType.DEFAULT, /* TypedefDeclarationSpecifier */
    NodeType.DEFAULT, /* TypedefTypeSpecifier */
    NodeType.DEFAULT, /* TypeofDeclarationSpecifier */
    NodeType.DEFAULT, /* TypeofTypeSpecifier */
    NodeType.DEFAULT, /* Typeofspecifier */
    NodeType.DEFAULT, /* Typeofkeyword */
    NodeType.DEFAULT, /* VarArgDeclarationSpecifier */
    NodeType.DEFAULT, /* VarArgTypeSpecifier */
    NodeType.DEFAULT, /* VarArgTypeName */
    NodeType.DEFAULT, /* StorageClass */
    NodeType.DEFAULT, /* BasicTypeName */
    NodeType.DEFAULT, /* SignedKeyword */
    NodeType.DEFAULT, /* ComplexKeyword */
    NodeType.DEFAULT, /* ElaboratedTypeName */
    NodeType.DEFAULT, /* StructOrUnionSpecifier */
    NodeType.DEFAULT, /* StructOrUnion */
    NodeType.LIST, /* StructDeclarationList */
    NodeType.DEFAULT, /* StructDeclaration */
    NodeType.LIST, /* StructDefaultDeclaringList */
    NodeType.LIST, /* StructDeclaringList */
    NodeType.DEFAULT, /* StructDeclarator */
    NodeType.DEFAULT, /* StructIdentifierDeclarator */
    NodeType.DEFAULT, /* BitFieldSizeOpt */
    NodeType.DEFAULT, /* BitFieldSize */
    NodeType.DEFAULT, /* EnumSpecifier */
    NodeType.LIST, /* EnumeratorList */
    NodeType.DEFAULT, /* Enumerator */
    NodeType.DEFAULT, /* EnumeratorValueOpt */
    NodeType.DEFAULT, /* ParameterTypeList */
    NodeType.LIST, /* ParameterList */
    NodeType.DEFAULT, /* ParameterDeclaration */
    NodeType.LIST, /* IdentifierList */
    NodeType.DEFAULT, /* Identifier */
    NodeType.DEFAULT, /* IdentifierOrTypedefName */
    NodeType.DEFAULT, /* TypeName */
    NodeType.DEFAULT, /* InitializerOpt */
    NodeType.PASS_THROUGH, /* DesignatedInitializer */
    NodeType.DEFAULT, /* Initializer */
    NodeType.DEFAULT, /* InitializerList */
    NodeType.LIST, /* MatchedInitializerList */
    NodeType.DEFAULT, /* Designation */
    NodeType.LIST, /* DesignatorList */
    NodeType.DEFAULT, /* Designator */
    NodeType.DEFAULT, /* ObsoleteArrayDesignation */
    NodeType.DEFAULT, /* ObsoleteFieldDesignation */
    NodeType.PASS_THROUGH, /* Declarator */
    NodeType.PASS_THROUGH, /* TypedefDeclarator */
    NodeType.PASS_THROUGH, /* TypedefDeclaratorMain */
    NodeType.DEFAULT, /* ParameterTypedefDeclarator */
    NodeType.DEFAULT, /* CleanTypedefDeclarator */
    NodeType.DEFAULT, /* CleanPostfixTypedefDeclarator */
    NodeType.PASS_THROUGH, /* ParenTypedefDeclarator */
    NodeType.DEFAULT, /* ParenPostfixTypedefDeclarator */
    NodeType.DEFAULT, /* SimpleParenTypedefDeclarator */
    NodeType.PASS_THROUGH, /* IdentifierDeclarator */
    NodeType.PASS_THROUGH, /* IdentifierDeclaratorMain */
    NodeType.PASS_THROUGH, /* UnaryIdentifierDeclarator */
    NodeType.DEFAULT, /* PostfixIdentifierDeclarator */
    NodeType.PASS_THROUGH, /* ParenIdentifierDeclarator */
    NodeType.DEFAULT, /* SimpleDeclarator */
    NodeType.DEFAULT, /* OldFunctionDeclarator */
    NodeType.DEFAULT, /* PostfixOldFunctionDeclarator */
    NodeType.DEFAULT, /* AbstractDeclarator */
    NodeType.DEFAULT, /* PostfixingAbstractDeclarator */
    NodeType.DEFAULT, /* ParameterTypeListOpt */
    NodeType.DEFAULT, /* ArrayAbstractDeclarator */
    NodeType.DEFAULT, /* UnaryAbstractDeclarator */
    NodeType.DEFAULT, /* PostfixAbstractDeclarator */
    NodeType.PASS_THROUGH, /* Statement */
    NodeType.DEFAULT, /* LabeledStatement */
    NodeType.DEFAULT, /* CompoundStatement */
    NodeType.DEFAULT, /* LocalLabelDeclarationListOpt */
    NodeType.LIST, /* LocalLabelDeclarationList */
    NodeType.DEFAULT, /* LocalLabelDeclaration */
    NodeType.LIST, /* LocalLabelList */
    NodeType.LIST, /* DeclarationOrStatementList */
    NodeType.PASS_THROUGH, /* DeclarationOrStatement */
    NodeType.LIST, /* DeclarationList */
    NodeType.DEFAULT, /* ExpressionStatement */
    NodeType.DEFAULT, /* SelectionStatement */
    NodeType.DEFAULT, /* IterationStatement */
    NodeType.DEFAULT, /* JumpStatement */
    NodeType.DEFAULT, /* Constant */
    NodeType.LIST, /* StringLiteralList */
    NodeType.PASS_THROUGH, /* PrimaryExpression */
    NodeType.DEFAULT, /* PrimaryIdentifier */
    NodeType.DEFAULT, /* VariableArgumentAccess */
    NodeType.DEFAULT, /* StatementAsExpression */
    NodeType.PASS_THROUGH, /* PostfixExpression */
    NodeType.DEFAULT, /* CompoundLiteral */
    NodeType.LIST, /* ArgumentExpressionList */
    NodeType.PASS_THROUGH, /* UnaryExpression */
    NodeType.DEFAULT, /* TypeCompatibilityExpression */
    NodeType.DEFAULT, /* OffsetofExpression */
    NodeType.DEFAULT, /* ExtensionExpression */
    NodeType.DEFAULT, /* AlignofExpression */
    NodeType.DEFAULT, /* Alignofkeyword */
    NodeType.DEFAULT, /* LabelAddressExpression */
    NodeType.DEFAULT, /* Unaryoperator */
    NodeType.PASS_THROUGH, /* CastExpression */
    NodeType.PASS_THROUGH, /* MultiplicativeExpression */
    NodeType.PASS_THROUGH, /* AdditiveExpression */
    NodeType.PASS_THROUGH, /* ShiftExpression */
    NodeType.PASS_THROUGH, /* RelationalExpression */
    NodeType.PASS_THROUGH, /* EqualityExpression */
    NodeType.PASS_THROUGH, /* AndExpression */
    NodeType.PASS_THROUGH, /* ExclusiveOrExpression */
    NodeType.PASS_THROUGH, /* InclusiveOrExpression */
    NodeType.PASS_THROUGH, /* LogicalAndExpression */
    NodeType.PASS_THROUGH, /* LogicalORExpression */
    NodeType.PASS_THROUGH, /* ConditionalExpression */
    NodeType.PASS_THROUGH, /* AssignmentExpression */
    NodeType.DEFAULT, /* AssignmentOperator */
    NodeType.PASS_THROUGH, /* Expression */
    NodeType.PASS_THROUGH, /* ConstantExpression */
    NodeType.PASS_THROUGH, /* ExpressionOpt */
    NodeType.DEFAULT, /* AttributeSpecifierListOpt */
    NodeType.LIST, /* AttributeSpecifierList */
    NodeType.DEFAULT, /* AttributeSpecifier */
    NodeType.DEFAULT, /* AttributeKeyword */
    NodeType.DEFAULT, /* AttributeListOpt */
    NodeType.LIST, /* AttributeList */
    NodeType.DEFAULT, /* AttributeExpressionOpt */
    NodeType.DEFAULT, /* Word */
    NodeType.DEFAULT, /* AssemblyDefinition */
    NodeType.DEFAULT, /* AssemblyExpression */
    NodeType.DEFAULT, /* AssemblyExpressionOpt */
    NodeType.DEFAULT, /* AssemblyStatement */
    NodeType.DEFAULT, /* Assemblyargument */
    NodeType.DEFAULT, /* AssemblyoperandsOpt */
    NodeType.LIST, /* Assemblyoperands */
    NodeType.DEFAULT, /* Assemblyoperand */
    NodeType.DEFAULT, /* Assemblyclobbers */
    NodeType.DEFAULT, /* AsmKeyword */
    NodeType.ACTION, /* BindIdentifier */
    NodeType.ACTION, /* BindIdentifierInList */
    NodeType.ACTION, /* BindVar */
    NodeType.ACTION, /* BindEnum */
    NodeType.ACTION, /* EnterScope */
    NodeType.ACTION, /* ExitScope */
    NodeType.ACTION, /* ExitReentrantScope */
    NodeType.ACTION, /* ReenterScope */
    NodeType.ACTION /* KillReentrantScope */
    };

    public static boolean isComplete(int sym) {
      switch(sym) {
      case 137: /* DeclaringList */
        return true;
      case 136: /* DefaultDeclaringList */
        return true;
      case 279: /* AssemblyStatement */
        return true;
      case 278: /* AssemblyExpressionOpt */
        return true;
      case 277: /* AssemblyExpression */
        return true;
      case 276: /* AssemblyDefinition */
        return true;
      case 283: /* Assemblyoperand */
        return true;
      case 129: /* EmptyDefinition */
        return true;
      case 282: /* Assemblyoperands */
        return true;
      case 128: /* ExternalDeclaration */
        return true;
      case 281: /* AssemblyoperandsOpt */
        return true;
      case 131: /* FunctionDefinition */
        return true;
      case 280: /* Assemblyargument */
        return true;
      case 130: /* FunctionDefinitionExtension */
        return true;
      case 287: /* BindIdentifierInList */
        return true;
      case 133: /* FunctionOldPrototype */
        return true;
      case 286: /* BindIdentifier */
        return true;
      case 132: /* FunctionDeclarator */
        return true;
      case 135: /* Declaration */
        return true;
      case 284: /* Assemblyclobbers */
        return true;
      case 134: /* DeclarationExtension */
        return true;
      case 258: /* ExclusiveOrExpression */
        return true;
      case 259: /* InclusiveOrExpression */
        return true;
      case 256: /* EqualityExpression */
        return true;
      case 257: /* AndExpression */
        return true;
      case 262: /* ConditionalExpression */
        return true;
      case 263: /* AssignmentExpression */
        return true;
      case 260: /* LogicalAndExpression */
        return true;
      case 261: /* LogicalORExpression */
        return true;
      case 266: /* ConstantExpression */
        return true;
      case 267: /* ExpressionOpt */
        return true;
      case 265: /* Expression */
        return true;
      case 288: /* BindVar */
        return true;
      case 289: /* BindEnum */
        return true;
      case 290: /* EnterScope */
        return true;
      case 184: /* Identifier */
        return true;
      case 291: /* ExitScope */
        return true;
      case 292: /* ExitReentrantScope */
        return true;
      case 190: /* InitializerList */
        return true;
      case 293: /* ReenterScope */
        return true;
      case 191: /* MatchedInitializerList */
        return true;
      case 294: /* KillReentrantScope */
        return true;
      case 188: /* DesignatedInitializer */
        return true;
      case 182: /* ParameterDeclaration */
        return true;
      case 183: /* IdentifierList */
        return true;
      case 180: /* ParameterTypeList */
        return true;
      case 181: /* ParameterList */
        return true;
      case 197: /* Declarator */
        return true;
      case 220: /* Statement */
        return true;
      case 221: /* LabeledStatement */
        return true;
      case 222: /* CompoundStatement */
        return true;
      case 239: /* StatementAsExpression */
        return true;
      case 238: /* VariableArgumentAccess */
        return true;
      case 236: /* PrimaryExpression */
        return true;
      case 235: /* StringLiteralList */
        return true;
      case 233: /* JumpStatement */
        return true;
      case 232: /* IterationStatement */
        return true;
      case 231: /* SelectionStatement */
        return true;
      case 230: /* ExpressionStatement */
        return true;
      case 229: /* DeclarationList */
        return true;
      case 228: /* DeclarationOrStatement */
        return true;
      case 227: /* DeclarationOrStatementList */
        return true;
      case 254: /* ShiftExpression */
        return true;
      case 255: /* RelationalExpression */
        return true;
      case 252: /* MultiplicativeExpression */
        return true;
      case 253: /* AdditiveExpression */
        return true;
      case 251: /* CastExpression */
        return true;
      case 249: /* LabelAddressExpression */
        return true;
      case 127: /* ExternalDeclarationList */
        return true;
      case 246: /* ExtensionExpression */
        return true;
      case 126: /* TranslationUnit */
        return true;
      case 247: /* AlignofExpression */
        return true;
      case 244: /* TypeCompatibilityExpression */
        return true;
      case 245: /* OffsetofExpression */
        return true;
      case 242: /* ArgumentExpressionList */
        return true;
      case 243: /* UnaryExpression */
        return true;
      case 240: /* PostfixExpression */
        return true;
      case 241: /* CompoundLiteral */
        return true;
      default:
        return false;
      }
    }

    public static void dispatchAction(ForkMergeParser.Subparser point, int sym, ActionInterface actions) {
      switch(sym) {
      case 288: /* BindVar */
        actions.BindVar(point);
        break;
      case 289: /* BindEnum */
        actions.BindEnum(point);
        break;
      case 290: /* EnterScope */
        actions.EnterScope(point);
        break;
      case 291: /* ExitScope */
        actions.ExitScope(point);
        break;
      case 292: /* ExitReentrantScope */
        actions.ExitReentrantScope(point);
        break;
      case 293: /* ReenterScope */
        actions.ReenterScope(point);
        break;
      case 294: /* KillReentrantScope */
        actions.KillReentrantScope(point);
        break;
      case 287: /* BindIdentifierInList */
        actions.BindIdentifierInList(point);
        break;
      case 286: /* BindIdentifier */
        actions.BindIdentifier(point);
        break;
      }
    }

    public static interface ActionInterface {
      public void BindVar(ForkMergeParser.Subparser point);
      public void BindEnum(ForkMergeParser.Subparser point);
      public void EnterScope(ForkMergeParser.Subparser point);
      public void ExitScope(ForkMergeParser.Subparser point);
      public void ExitReentrantScope(ForkMergeParser.Subparser point);
      public void ReenterScope(ForkMergeParser.Subparser point);
      public void KillReentrantScope(ForkMergeParser.Subparser point);
      public void BindIdentifierInList(ForkMergeParser.Subparser point);
      public void BindIdentifier(ForkMergeParser.Subparser point);
    }

}
