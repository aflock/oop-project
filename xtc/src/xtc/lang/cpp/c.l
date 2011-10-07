package xtc.lang.cpp;
import xtc.lang.cpp.CSyntax.Token;
import xtc.lang.cpp.CSyntax.ValuedToken;
import xtc.lang.cpp.CSyntax.Keyword;
import xtc.lang.cpp.CSyntax.Identifier;
import xtc.lang.cpp.CSyntax.Literal;
import xtc.lang.cpp.CSyntax.Punctuation;
import xtc.lang.cpp.CSyntax.Layout;

import xtc.lang.cpp.ForkMergeParserTables.sym;

%%

%{

  private StringBuilder comment = null;
  private boolean commentnl = false;

  private static final String errorMsg[] = {
    "Error: Unmatched end-of-comment punctuation.",
    "Error: Unmatched start-of-comment punctuation.",
    "Error: Unclosed string.",
    "Error: Illegal character."
    };
  
  public static final int E_ENDCOMMENT = 0; 
  public static final int E_STARTCOMMENT = 1; 
  public static final int E_UNCLOSEDSTR = 2; 
  public static final int E_UNMATCHED = 3; 
  
  public static void error(int code) {
    System.out.println(errorMsg[code]);
  }

  /** Convert a syntactic unit to a string. */
  public static String getString(CSyntax syntax) {
    if (syntax.hasValue()) {
      return syntax.getValue();
    }
    else if (syntax.isToken()) {
      switch(((Token) syntax).type()) {
        case AUTO:
          return "auto";
        case BREAK:
          return "break";
        case CASE:
          return "case";
        case CHAR:
          return "char";
        case CONST:
          return "const";
        case CONTINUE:
          return "continue";
        case DEFAULT:
          return "default";
        case DO:
          return "do";
        case DOUBLE:
          return "double";
        case ELSE:
          return "else";
        case ENUM:
          return "enum";
        case EXTERN:
          return "extern";
        case FLOAT:
          return "float";
        case FOR:
          return "for";
        case GOTO:
          return "goto";
        case IF:
          return "if";
        case INT:
          return "int";
        case LONG:
          return "long";
        case REGISTER:
          return "register";
        case RETURN:
          return "return";
        case SHORT:
          return "short";
        case SIGNED:
          return "signed";
        case SIZEOF:
          return "sizeof";
        case STATIC:
          return "static";
        case STRUCT:
          return "struct";
        case SWITCH:
          return "switch";
        case TYPEDEF:
          return "typedef";
        case UNION:
          return "union";
        case UNSIGNED:
          return "unsigned";
        case VOID:
          return "void";
        case VOLATILE:
          return "volatile";
        case WHILE:
          return "while";
        case _BOOL:
          return "_Bool";
        case _COMPLEX:
          return "_Complex";
        case INLINE:
          return "inline";
        case RESTRICT:
          return "restrict";
        case __ALIGNOF:
          return "__alignof";
        case __ALIGNOF__:
          return "__alignof__";
        case ASM:
          return "asm";
        case __ASM:
          return "__asm";
        case __ASM__:
          return "__asm__";
        case __ATTRIBUTE:
          return "__attribute";
        case __ATTRIBUTE__:
          return "__attribute__";
        case __BUILTIN_OFFSETOF:
          return "__builtin_offsetof";
        case __BUILTIN_TYPES_COMPATIBLE_P:
          return "__builtin_types_compatible_p";
        case __BUILTIN_VA_ARG:
          return "__builtin_va_arg";
        case __BUILTIN_VA_LIST:
          return "__builtin_va_list";
        case __COMPLEX__:
          return "__complex__";
        case __CONST:
          return "__const";
        case __CONST__:
          return "__const__";
        case __EXTENSION__:
          return "__extension__";
        case __INLINE:
          return "__inline";
        case __INLINE__:
          return "__inline__";
        case __LABEL__:
          return "__label__";
        case __RESTRICT:
          return "__restrict";
        case __RESTRICT__:
          return "__restrict__";
        case __SIGNED:
          return "__signed";
        case __SIGNED__:
          return "__signed__";
        case __THREAD:
          return "__thread";
        case TYPEOF:
          return "typeof";
        case __TYPEOF:
          return "__typeof";
        case __TYPEOF__:
          return "__typeof__";
        case __VOLATILE:
          return "__volatile";
        case __VOLATILE__:
          return "__volatile__";
        case LPAREN:
          return "(";
        case RPAREN:
          return ")";
        case COMMA:
          return ",";
        case HASH:
          return "#";
        case DHASH:
          return "##";
        case LBRACE:
          return "{";
        case RBRACE:
          return "}";
        case LBRACK:
          return "[";
        case RBRACK:
          return "]";
        case DOT:
          return ".";
        case AND:
          return "&";
        case STAR:
          return "*";
        case PLUS:
          return "+";
        case MINUS:
          return "-";
        case NEGATE:
          return "~";
        case NOT:
          return "!";
        case DIV:
          return "/";
        case MOD:
          return "%";
        case LT:
          return "<";
        case GT:
          return ">";
        case XOR:
          return "^";
        case PIPE:
          return "|";
        case QUESTION:
          return "?";
        case COLON:
          return ":";
        case SEMICOLON:
          return ";";
        case ASSIGN:
          return "=";
        case ARROW:
          return "->";
        case ICR:
          return "++";
        case DECR:
          return "--";
        case LS:
          return "<<";
        case RS:
          return ">>";
        case LE:
          return "<=";
        case GE:
          return ">=";
        case EQ:
          return "==";
        case NE:
          return "!=";
        case ANDAND:
          return "&&";
        case OROR:
          return "||";
        case PLUSassign:
          return "+=";
        case MINUSassign:
          return "-=";
        case MULTassign:
          return "*=";
        case DIVassign:
          return "/=";
        case MODassign:
          return "%=";
        case LSassign:
          return "<<=";
        case RSassign:
          return ">>=";
        case ANDassign:
          return "&=";
        case ERassign:
          return "^=";
        case ORassign:
          return "|=";
        case ELLIPSIS:
          return "...";
        case EOF:
          return "";
      }
    }
    
    return null;
  }
  
  public static boolean isType(CSyntax syntax, sym type) {
    return syntax.isToken() && ((Token) syntax).isType(type);
  }
  
%} 

%eofval{
  return new Token(sym.EOF, yyline + 1, yychar, yychar + yylength());
%eofval}

%class CLexer
%unicode
%type CSyntax
%line
%char

%state COMMENT

//%debug

identifier=[a-zA-Z_][0-9a-zA-Z_]*

exponent_part=([eE]|[pP])[-+]?[0-9]+
fractional_constant=([0-9]*"."[0-9]+)|([0-9]+".")
floating_constant=(({fractional_constant}{exponent_part}?)|([0-9]+{exponent_part}))[FfLl]?
preprocessing_number="."?[0-9]+([0-9a-zA-Z._]|{exponent_part})*

integer_suffix_opt=([uU]?[lL]?)|([lL][uU])|([uU]("ll"|"LL"))|(("ll"|"LL")[uU]|"LL"|"ll"|"UU"|"uu")
integer_constant=[1-9][0-9]*{integer_suffix_opt}
octal_constant="0"[0-7]*{integer_suffix_opt}
hex_constant="0"[xX][0-9a-fA-F]+{integer_suffix_opt}

simple_escape=[abfnrtv\'\"?\\]
octal_escape=[0-7]{1,3}
hex_escape="x"[0-9a-fA-F]+

escape_sequence=[\\]({simple_escape}|{octal_escape}|{hex_escape})
c_char=[^\'\\\n]|{escape_sequence}
s_char=[^\"\\\n]|{escape_sequence}

//TODO need to lexer \n without quote as in ~/Work/java/fonda/cpp_testsuite/cpp/stringification_strings.c

//whitespace

h_tab=[\011]
form_feed=[\014]
v_tab=[\013]
c_return=[\015]

horizontal_white=[ ]|{h_tab}

newline=\r|\n|\r\n

//comment=[/][*]([^*/]|[^*][/]|[*][^/])*[*][/]

linecomment="//"[^\r\n]*{newline}

continuation="\\\n"

whitespace=({horizontal_white})+|({v_tab}|{c_return}|{form_feed})+|{continuation}

//whitespace=({horizontal_white})+|({v_tab}|{c_return}|{form_feed})+|{continuation}|{comment}

//whitespace=({horizontal_white})+|({v_tab}|{c_return}|{form_feed})+|({horizontal_white}|{v_tab}|{c_return}|{form_feed})*"\n"|{continuation}|{comment}

%%

<YYINITIAL> {

{whitespace}+
{ return new Layout(yytext(), false, yyline + 1, yychar, yychar + yylength()); }

{whitespace}*({newline}|{linecomment})+{whitespace}*
{ return new Layout(yytext(), true, yyline + 1, yychar, yychar + yylength()); }

auto      { return new Keyword(sym.AUTO, yyline + 1, yychar, yychar + yylength()); }
break     { return new Keyword(sym.BREAK, yyline + 1, yychar, yychar + yylength()); }
case      { return new Keyword(sym.CASE, yyline + 1, yychar, yychar + yylength()); }
char      { return new Keyword(sym.CHAR, yyline + 1, yychar, yychar + yylength()); }
const     { return new Keyword(sym.CONST, yyline + 1, yychar, yychar + yylength()); }
continue  { return new Keyword(sym.CONTINUE, yyline + 1, yychar, yychar + yylength()); }
default   { return new Keyword(sym.DEFAULT, yyline + 1, yychar, yychar + yylength()); }
do        { return new Keyword(sym.DO, yyline + 1, yychar, yychar + yylength()); }
double    { return new Keyword(sym.DOUBLE, yyline + 1, yychar, yychar + yylength()); }
else      { return new Keyword(sym.ELSE, yyline + 1, yychar, yychar + yylength()); }
enum      { return new Keyword(sym.ENUM, yyline + 1, yychar, yychar + yylength()); }
extern    { return new Keyword(sym.EXTERN, yyline + 1, yychar, yychar + yylength()); }
float     { return new Keyword(sym.FLOAT, yyline + 1, yychar, yychar + yylength()); }
for       { return new Keyword(sym.FOR, yyline + 1, yychar, yychar + yylength()); }
goto      { return new Keyword(sym.GOTO, yyline + 1, yychar, yychar + yylength()); }
if        { return new Keyword(sym.IF, yyline + 1, yychar, yychar + yylength()); }
int       { return new Keyword(sym.INT, yyline + 1, yychar, yychar + yylength()); }
long      { return new Keyword(sym.LONG, yyline + 1, yychar, yychar + yylength()); }
register  { return new Keyword(sym.REGISTER, yyline + 1, yychar, yychar + yylength()); }
return    { return new Keyword(sym.RETURN, yyline + 1, yychar, yychar + yylength()); }
short     { return new Keyword(sym.SHORT, yyline + 1, yychar, yychar + yylength()); }
signed    { return new Keyword(sym.SIGNED, yyline + 1, yychar, yychar + yylength()); }
sizeof    { return new Keyword(sym.SIZEOF, yyline + 1, yychar, yychar + yylength()); }
static    { return new Keyword(sym.STATIC, yyline + 1, yychar, yychar + yylength()); }
struct    { return new Keyword(sym.STRUCT, yyline + 1, yychar, yychar + yylength()); }
switch    { return new Keyword(sym.SWITCH, yyline + 1, yychar, yychar + yylength()); }
typedef   { return new Keyword(sym.TYPEDEF, yyline + 1, yychar, yychar + yylength()); }
union     { return new Keyword(sym.UNION, yyline + 1, yychar, yychar + yylength()); }
unsigned  { return new Keyword(sym.UNSIGNED, yyline + 1, yychar, yychar + yylength()); }
void      { return new Keyword(sym.VOID, yyline + 1, yychar, yychar + yylength()); }
volatile  { return new Keyword(sym.VOLATILE, yyline + 1, yychar, yychar + yylength()); }
while     { return new Keyword(sym.WHILE, yyline + 1, yychar, yychar + yylength()); }

//C99
_Bool     { return new Keyword(sym._BOOL, yyline + 1, yychar, yychar + yylength()); }
_Complex  { return new Keyword(sym._COMPLEX, yyline + 1, yychar, yychar + yylength()); }
inline    { return new Keyword(sym.INLINE, yyline + 1, yychar, yychar + yylength()); }
restrict  { return new Keyword(sym.RESTRICT, yyline + 1, yychar, yychar + yylength()); }

//GCC 
"__alignof"                     { return new Keyword(sym.__ALIGNOF, yyline + 1, yychar, yychar + yylength()); }
"__alignof__"                   { return new Keyword(sym.__ALIGNOF__, yyline + 1, yychar, yychar + yylength()); }
"asm"                           { return new Keyword(sym.ASM, yyline + 1, yychar, yychar + yylength()); }
"__asm"                         { return new Keyword(sym.__ASM, yyline + 1, yychar, yychar + yylength()); }
"__asm__"                       { return new Keyword(sym.__ASM__, yyline + 1, yychar, yychar + yylength()); }
"__attribute"                   { return new Keyword(sym.__ATTRIBUTE, yyline + 1, yychar, yychar + yylength()); }
"__attribute__"                 { return new Keyword(sym.__ATTRIBUTE__, yyline + 1, yychar, yychar + yylength()); }
"__builtin_offsetof"            { return new Keyword(sym.__BUILTIN_OFFSETOF, yyline + 1, yychar, yychar + yylength()); }
"__builtin_types_compatible_p"  { return new Keyword(sym.__BUILTIN_TYPES_COMPATIBLE_P, yyline + 1, yychar, yychar + yylength()); }
"__builtin_va_arg"              { return new Keyword(sym.__BUILTIN_VA_ARG, yyline + 1, yychar, yychar + yylength()); }
"__builtin_va_list"             { return new Keyword(sym.__BUILTIN_VA_LIST, yyline + 1, yychar, yychar + yylength()); }
"__complex__"                   { return new Keyword(sym.__COMPLEX__, yyline + 1, yychar, yychar + yylength()); }
"__const"                       { return new Keyword(sym.__CONST, yyline + 1, yychar, yychar + yylength()); }
"__const__"                     { return new Keyword(sym.__CONST__, yyline + 1, yychar, yychar + yylength()); }
"__extension__"                 { return new Keyword(sym.__EXTENSION__, yyline + 1, yychar, yychar + yylength()); }
"__inline"                      { return new Keyword(sym.__INLINE, yyline + 1, yychar, yychar + yylength()); }
"__inline__"                    { return new Keyword(sym.__INLINE__, yyline + 1, yychar, yychar + yylength()); }
"__label__"                     { return new Keyword(sym.__LABEL__, yyline + 1, yychar, yychar + yylength()); }
"__restrict"                    { return new Keyword(sym.__RESTRICT, yyline + 1, yychar, yychar + yylength()); }
"__restrict__"                  { return new Keyword(sym.__RESTRICT__, yyline + 1, yychar, yychar + yylength()); }
"__signed"                      { return new Keyword(sym.__SIGNED, yyline + 1, yychar, yychar + yylength()); }
"__signed__"                    { return new Keyword(sym.__SIGNED__, yyline + 1, yychar, yychar + yylength()); }
"__thread"                      { return new Keyword(sym.__THREAD, yyline + 1, yychar, yychar + yylength()); }
"typeof"                        { return new Keyword(sym.TYPEOF, yyline + 1, yychar, yychar + yylength()); }
"__typeof"                      { return new Keyword(sym.__TYPEOF, yyline + 1, yychar, yychar + yylength()); }
"__typeof__"                    { return new Keyword(sym.__TYPEOF__, yyline + 1, yychar, yychar + yylength()); }
"__volatile"                    { return new Keyword(sym.__VOLATILE, yyline + 1, yychar, yychar + yylength()); }
"__volatile__"                  { return new Keyword(sym.__VOLATILE__, yyline + 1, yychar, yychar + yylength()); }

{identifier}            { return new Identifier(sym.IDENTIFIER, yytext(), yyline + 1, yychar, yychar + yylength()); }

{integer_constant}      { return new Literal(sym.INTEGERconstant, yytext(), yyline + 1, yychar, yychar + yylength()); }
{octal_constant}        { return new Literal(sym.OCTALconstant, yytext(), yyline + 1, yychar, yychar + yylength()); }
{hex_constant}          { return new Literal(sym.HEXconstant, yytext(), yyline + 1, yychar, yychar + yylength()); }
{floating_constant}     { return new Literal(sym.FLOATINGconstant, yytext(), yyline + 1, yychar, yychar + yylength()); }
{preprocessing_number}  { return new Literal(sym.PPNUM, yytext(), yyline + 1, yychar, yychar + yylength()); }

"L"?\'{c_char}+\'       { return new Literal(sym.CHARACTERconstant, yytext(), yyline + 1, yychar, yychar + yylength()); }
"L"?\"{s_char}*\"       { return new Literal(sym.STRINGliteral, yytext(), yyline + 1, yychar, yychar + yylength()); }


"->"  { return new Punctuation(sym.ARROW, yyline + 1, yychar, yychar + yylength()); }
"++"  { return new Punctuation(sym.ICR, yyline + 1, yychar, yychar + yylength()); }
"--"  { return new Punctuation(sym.DECR, yyline + 1, yychar, yychar + yylength()); }
"<<"  { return new Punctuation(sym.LS, yyline + 1, yychar, yychar + yylength()); }
">>"  { return new Punctuation(sym.RS, yyline + 1, yychar, yychar + yylength()); }
"<="  { return new Punctuation(sym.LE, yyline + 1, yychar, yychar + yylength()); }
">="  { return new Punctuation(sym.GE, yyline + 1, yychar, yychar + yylength()); }
"=="  { return new Punctuation(sym.EQ, yyline + 1, yychar, yychar + yylength()); }
"!="  { return new Punctuation(sym.NE, yyline + 1, yychar, yychar + yylength()); }
"&&"  { return new Punctuation(sym.ANDAND, yyline + 1, yychar, yychar + yylength()); }
"||"  { return new Punctuation(sym.OROR, yyline + 1, yychar, yychar + yylength()); }
"+="  { return new Punctuation(sym.PLUSassign, yyline + 1, yychar, yychar + yylength()); }
"-="  { return new Punctuation(sym.MINUSassign, yyline + 1, yychar, yychar + yylength()); }
"*="  { return new Punctuation(sym.MULTassign, yyline + 1, yychar, yychar + yylength()); }
"/="  { return new Punctuation(sym.DIVassign, yyline + 1, yychar, yychar + yylength()); }
"%="  { return new Punctuation(sym.MODassign, yyline + 1, yychar, yychar + yylength()); }
"<<=" { return new Punctuation(sym.LSassign, yyline + 1, yychar, yychar + yylength()); }
">>=" { return new Punctuation(sym.RSassign, yyline + 1, yychar, yychar + yylength()); }
"&="  { return new Punctuation(sym.ANDassign, yyline + 1, yychar, yychar + yylength()); }
"^="  { return new Punctuation(sym.ERassign, yyline + 1, yychar, yychar + yylength()); }
"|="  { return new Punctuation(sym.ORassign, yyline + 1, yychar, yychar + yylength()); }
"..." { return new Punctuation(sym.ELLIPSIS, yyline + 1, yychar, yychar + yylength()); }

"("   { return new Punctuation(sym.LPAREN, yyline + 1, yychar, yychar + yylength()); }
")"   { return new Punctuation(sym.RPAREN, yyline + 1, yychar, yychar + yylength()); }
","   { return new Punctuation(sym.COMMA, yyline + 1, yychar, yychar + yylength()); }
"#"   { return new Punctuation(sym.HASH, yyline + 1, yychar, yychar + yylength()); }
"##"  { return new Punctuation(sym.DHASH, yyline + 1, yychar, yychar + yylength()); }

"{"   { return new Punctuation(sym.LBRACE, yyline + 1, yychar, yychar + yylength()); }
"}"   { return new Punctuation(sym.RBRACE, yyline + 1, yychar, yychar + yylength()); }
"["   { return new Punctuation(sym.LBRACK, yyline + 1, yychar, yychar + yylength()); }
"]"   { return new Punctuation(sym.RBRACK, yyline + 1, yychar, yychar + yylength()); }
"."   { return new Punctuation(sym.DOT, yyline + 1, yychar, yychar + yylength()); }
"&"   { return new Punctuation(sym.AND, yyline + 1, yychar, yychar + yylength()); }
"*"   { return new Punctuation(sym.STAR, yyline + 1, yychar, yychar + yylength()); }
"+"   { return new Punctuation(sym.PLUS, yyline + 1, yychar, yychar + yylength()); }
"-"   { return new Punctuation(sym.MINUS, yyline + 1, yychar, yychar + yylength()); }
"~"   { return new Punctuation(sym.NEGATE, yyline + 1, yychar, yychar + yylength()); }
"!"   { return new Punctuation(sym.NOT, yyline + 1, yychar, yychar + yylength()); }
"/"   { return new Punctuation(sym.DIV, yyline + 1, yychar, yychar + yylength()); }
"%"   { return new Punctuation(sym.MOD, yyline + 1, yychar, yychar + yylength()); }
"<"   { return new Punctuation(sym.LT, yyline + 1, yychar, yychar + yylength()); }
">"   { return new Punctuation(sym.GT, yyline + 1, yychar, yychar + yylength()); }
"^"   { return new Punctuation(sym.XOR, yyline + 1, yychar, yychar + yylength()); }
"|"   { return new Punctuation(sym.PIPE, yyline + 1, yychar, yychar + yylength()); }
"?"   { return new Punctuation(sym.QUESTION, yyline + 1, yychar, yychar + yylength()); }
":"   { return new Punctuation(sym.COLON, yyline + 1, yychar, yychar + yylength()); }
";"   { return new Punctuation(sym.SEMICOLON, yyline + 1, yychar, yychar + yylength()); }
"="   { return new Punctuation(sym.ASSIGN, yyline + 1, yychar, yychar + yylength()); }

//TODO find out what @ and @function are, something to do with assembly, e.g. in linux-2.6.36/include/linux/linkage.h
"@"   { return new Punctuation(sym.ASM, yyline + 1, yychar, yychar + yylength()); }
"$"   { return new Punctuation(sym.ASM, yyline + 1, yychar, yychar + yylength()); }

//for c++
//".*"  { return new Punctuation(sym.DOTSTAR, yyline + 1, yychar, yychar + yylength()); }
//"::"  { return new Punctuation(sym.DCOLON, yyline + 1, yychar, yychar + yylength()); }
//"->*" { return new Punctuation(sym.ARROWSTAR, yyline + 1, yychar, yychar + yylength()); }

"/*" {
yybegin(COMMENT);
comment = new StringBuilder();
comment.append(yytext());
commentnl = false;
}
}

<COMMENT> {
"*/" {
comment.append(yytext()); yybegin(YYINITIAL);
return new Layout(comment.toString(), false, yyline + 1, yychar, yychar + yylength());
}
[^*\n]+ {comment.append(yytext());}
"*" {comment.append(yytext());}
\n {comment.append(yytext()); commentnl = true;}
}

