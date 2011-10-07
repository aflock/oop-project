
/* A Bison parser, made by GNU Bison 2.4.1.  */

/* Skeleton implementation for Bison's Yacc-like parsers in C
   
      Copyright (C) 1984, 1989, 1990, 2000, 2001, 2002, 2003, 2004, 2005, 2006
   Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* C LALR(1) parser skeleton written by Richard Stallman, by
   simplifying the original so-called "semantic" parser.  */

/* All symbols defined below should begin with yy or YY, to avoid
   infringing on user name space.  This should be done even for local
   variables, as they might otherwise be expanded by user macros.
   There are some unavoidable exceptions within include files to
   define necessary library symbols; they are noted "INFRINGES ON
   USER NAME SPACE" below.  */

/* Identify Bison output.  */
#define YYBISON 1

/* Bison version.  */
#define YYBISON_VERSION "2.4.1"

/* Skeleton name.  */
#define YYSKELETON_NAME "yacc.c"

/* Pure parsers.  */
#define YYPURE 0

/* Push parsers.  */
#define YYPUSH 0

/* Pull parsers.  */
#define YYPULL 1

/* Using locations.  */
#define YYLSP_NEEDED 0



/* Copy the first part of user declarations.  */


/* Line 189 of yacc.c  */
#line 73 "c.tab.c"

/* Enabling traces.  */
#ifndef YYDEBUG
# define YYDEBUG 0
#endif

/* Enabling verbose error messages.  */
#ifdef YYERROR_VERBOSE
# undef YYERROR_VERBOSE
# define YYERROR_VERBOSE 1
#else
# define YYERROR_VERBOSE 0
#endif

/* Enabling the token table.  */
#ifndef YYTOKEN_TABLE
# define YYTOKEN_TABLE 0
#endif


/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     AUTO = 258,
     DOUBLE = 259,
     INT = 260,
     STRUCT = 261,
     BREAK = 262,
     ELSE = 263,
     LONG = 264,
     SWITCH = 265,
     CASE = 266,
     ENUM = 267,
     REGISTER = 268,
     TYPEDEF = 269,
     CHAR = 270,
     EXTERN = 271,
     RETURN = 272,
     UNION = 273,
     CONST = 274,
     FLOAT = 275,
     SHORT = 276,
     UNSIGNED = 277,
     CONTINUE = 278,
     FOR = 279,
     SIGNED = 280,
     VOID = 281,
     DEFAULT = 282,
     GOTO = 283,
     SIZEOF = 284,
     VOLATILE = 285,
     DO = 286,
     IF = 287,
     STATIC = 288,
     WHILE = 289,
     IDENTIFIER = 290,
     STRINGliteral = 291,
     FLOATINGconstant = 292,
     INTEGERconstant = 293,
     CHARACTERconstant = 294,
     OCTALconstant = 295,
     HEXconstant = 296,
     TYPEDEFname = 297,
     ARROW = 298,
     ICR = 299,
     DECR = 300,
     LS = 301,
     RS = 302,
     LE = 303,
     GE = 304,
     EQ = 305,
     NE = 306,
     ANDAND = 307,
     OROR = 308,
     ELLIPSIS = 309,
     MULTassign = 310,
     DIVassign = 311,
     MODassign = 312,
     PLUSassign = 313,
     MINUSassign = 314,
     LSassign = 315,
     RSassign = 316,
     ANDassign = 317,
     ERassign = 318,
     ORassign = 319,
     LPAREN = 320,
     RPAREN = 321,
     COMMA = 322,
     HASH = 323,
     DHASH = 324,
     LBRACE = 325,
     RBRACE = 326,
     LBRACK = 327,
     RBRACK = 328,
     DOT = 329,
     AND = 330,
     STAR = 331,
     PLUS = 332,
     MINUS = 333,
     NEGATE = 334,
     NOT = 335,
     DIV = 336,
     MOD = 337,
     LT = 338,
     GT = 339,
     XOR = 340,
     PIPE = 341,
     QUESTION = 342,
     COLON = 343,
     SEMICOLON = 344,
     ASSIGN = 345,
     ASMSYM = 346,
     _BOOL = 347,
     _COMPLEX = 348,
     RESTRICT = 349,
     __ALIGNOF = 350,
     __ALIGNOF__ = 351,
     ASM = 352,
     __ASM = 353,
     __ASM__ = 354,
     __ATTRIBUTE = 355,
     __ATTRIBUTE__ = 356,
     __BUILTIN_OFFSETOF = 357,
     __BUILTIN_TYPES_COMPATIBLE_P = 358,
     __BUILTIN_VA_ARG = 359,
     __BUILTIN_VA_LIST = 360,
     __COMPLEX__ = 361,
     __CONST = 362,
     __CONST__ = 363,
     __EXTENSION__ = 364,
     INLINE = 365,
     __INLINE = 366,
     __INLINE__ = 367,
     __LABEL__ = 368,
     __RESTRICT = 369,
     __RESTRICT__ = 370,
     __SIGNED = 371,
     __SIGNED__ = 372,
     __THREAD = 373,
     TYPEOF = 374,
     __TYPEOF = 375,
     __TYPEOF__ = 376,
     __VOLATILE = 377,
     __VOLATILE__ = 378,
     PPNUM = 379
   };
#endif



#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
#endif


/* Copy the second part of user declarations.  */


/* Line 264 of yacc.c  */
#line 239 "c.tab.c"

#ifdef short
# undef short
#endif

#ifdef YYTYPE_UINT8
typedef YYTYPE_UINT8 yytype_uint8;
#else
typedef unsigned char yytype_uint8;
#endif

#ifdef YYTYPE_INT8
typedef YYTYPE_INT8 yytype_int8;
#elif (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
typedef signed char yytype_int8;
#else
typedef short int yytype_int8;
#endif

#ifdef YYTYPE_UINT16
typedef YYTYPE_UINT16 yytype_uint16;
#else
typedef unsigned short int yytype_uint16;
#endif

#ifdef YYTYPE_INT16
typedef YYTYPE_INT16 yytype_int16;
#else
typedef short int yytype_int16;
#endif

#ifndef YYSIZE_T
# ifdef __SIZE_TYPE__
#  define YYSIZE_T __SIZE_TYPE__
# elif defined size_t
#  define YYSIZE_T size_t
# elif ! defined YYSIZE_T && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
#  include <stddef.h> /* INFRINGES ON USER NAME SPACE */
#  define YYSIZE_T size_t
# else
#  define YYSIZE_T unsigned int
# endif
#endif

#define YYSIZE_MAXIMUM ((YYSIZE_T) -1)

#ifndef YY_
# if YYENABLE_NLS
#  if ENABLE_NLS
#   include <libintl.h> /* INFRINGES ON USER NAME SPACE */
#   define YY_(msgid) dgettext ("bison-runtime", msgid)
#  endif
# endif
# ifndef YY_
#  define YY_(msgid) msgid
# endif
#endif

/* Suppress unused-variable warnings by "using" E.  */
#if ! defined lint || defined __GNUC__
# define YYUSE(e) ((void) (e))
#else
# define YYUSE(e) /* empty */
#endif

/* Identity function, used to suppress warnings about constant conditions.  */
#ifndef lint
# define YYID(n) (n)
#else
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static int
YYID (int yyi)
#else
static int
YYID (yyi)
    int yyi;
#endif
{
  return yyi;
}
#endif

#if ! defined yyoverflow || YYERROR_VERBOSE

/* The parser invokes alloca or malloc; define the necessary symbols.  */

# ifdef YYSTACK_USE_ALLOCA
#  if YYSTACK_USE_ALLOCA
#   ifdef __GNUC__
#    define YYSTACK_ALLOC __builtin_alloca
#   elif defined __BUILTIN_VA_ARG_INCR
#    include <alloca.h> /* INFRINGES ON USER NAME SPACE */
#   elif defined _AIX
#    define YYSTACK_ALLOC __alloca
#   elif defined _MSC_VER
#    include <malloc.h> /* INFRINGES ON USER NAME SPACE */
#    define alloca _alloca
#   else
#    define YYSTACK_ALLOC alloca
#    if ! defined _ALLOCA_H && ! defined _STDLIB_H && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
#     include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
#     ifndef _STDLIB_H
#      define _STDLIB_H 1
#     endif
#    endif
#   endif
#  endif
# endif

# ifdef YYSTACK_ALLOC
   /* Pacify GCC's `empty if-body' warning.  */
#  define YYSTACK_FREE(Ptr) do { /* empty */; } while (YYID (0))
#  ifndef YYSTACK_ALLOC_MAXIMUM
    /* The OS might guarantee only one guard page at the bottom of the stack,
       and a page size can be as small as 4096 bytes.  So we cannot safely
       invoke alloca (N) if N exceeds 4096.  Use a slightly smaller number
       to allow for a few compiler-allocated temporary stack slots.  */
#   define YYSTACK_ALLOC_MAXIMUM 4032 /* reasonable circa 2006 */
#  endif
# else
#  define YYSTACK_ALLOC YYMALLOC
#  define YYSTACK_FREE YYFREE
#  ifndef YYSTACK_ALLOC_MAXIMUM
#   define YYSTACK_ALLOC_MAXIMUM YYSIZE_MAXIMUM
#  endif
#  if (defined __cplusplus && ! defined _STDLIB_H \
       && ! ((defined YYMALLOC || defined malloc) \
	     && (defined YYFREE || defined free)))
#   include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
#   ifndef _STDLIB_H
#    define _STDLIB_H 1
#   endif
#  endif
#  ifndef YYMALLOC
#   define YYMALLOC malloc
#   if ! defined malloc && ! defined _STDLIB_H && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
void *malloc (YYSIZE_T); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
#  ifndef YYFREE
#   define YYFREE free
#   if ! defined free && ! defined _STDLIB_H && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
void free (void *); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
# endif
#endif /* ! defined yyoverflow || YYERROR_VERBOSE */


#if (! defined yyoverflow \
     && (! defined __cplusplus \
	 || (defined YYSTYPE_IS_TRIVIAL && YYSTYPE_IS_TRIVIAL)))

/* A type that is properly aligned for any stack member.  */
union yyalloc
{
  yytype_int16 yyss_alloc;
  YYSTYPE yyvs_alloc;
};

/* The size of the maximum gap between one aligned stack and the next.  */
# define YYSTACK_GAP_MAXIMUM (sizeof (union yyalloc) - 1)

/* The size of an array large to enough to hold all stacks, each with
   N elements.  */
# define YYSTACK_BYTES(N) \
     ((N) * (sizeof (yytype_int16) + sizeof (YYSTYPE)) \
      + YYSTACK_GAP_MAXIMUM)

/* Copy COUNT objects from FROM to TO.  The source and destination do
   not overlap.  */
# ifndef YYCOPY
#  if defined __GNUC__ && 1 < __GNUC__
#   define YYCOPY(To, From, Count) \
      __builtin_memcpy (To, From, (Count) * sizeof (*(From)))
#  else
#   define YYCOPY(To, From, Count)		\
      do					\
	{					\
	  YYSIZE_T yyi;				\
	  for (yyi = 0; yyi < (Count); yyi++)	\
	    (To)[yyi] = (From)[yyi];		\
	}					\
      while (YYID (0))
#  endif
# endif

/* Relocate STACK from its old location to the new one.  The
   local variables YYSIZE and YYSTACKSIZE give the old and new number of
   elements in the stack, and YYPTR gives the new location of the
   stack.  Advance YYPTR to a properly aligned location for the next
   stack.  */
# define YYSTACK_RELOCATE(Stack_alloc, Stack)				\
    do									\
      {									\
	YYSIZE_T yynewbytes;						\
	YYCOPY (&yyptr->Stack_alloc, Stack, yysize);			\
	Stack = &yyptr->Stack_alloc;					\
	yynewbytes = yystacksize * sizeof (*Stack) + YYSTACK_GAP_MAXIMUM; \
	yyptr += yynewbytes / sizeof (*yyptr);				\
      }									\
    while (YYID (0))

#endif

/* YYFINAL -- State number of the termination state.  */
#define YYFINAL  3
/* YYLAST -- Last index in YYTABLE.  */
#define YYLAST   4516

/* YYNTOKENS -- Number of terminals.  */
#define YYNTOKENS  125
/* YYNNTS -- Number of nonterminals.  */
#define YYNNTS  170
/* YYNRULES -- Number of rules.  */
#define YYNRULES  541
/* YYNRULES -- Number of states.  */
#define YYNSTATES  887

/* YYTRANSLATE(YYLEX) -- Bison symbol number corresponding to YYLEX.  */
#define YYUNDEFTOK  2
#define YYMAXUTOK   379

#define YYTRANSLATE(YYX)						\
  ((unsigned int) (YYX) <= YYMAXUTOK ? yytranslate[YYX] : YYUNDEFTOK)

/* YYTRANSLATE[YYLEX] -- Bison symbol number corresponding to YYLEX.  */
static const yytype_uint8 yytranslate[] =
{
       0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    38,    39,    40,    41,    42,    43,    44,
      45,    46,    47,    48,    49,    50,    51,    52,    53,    54,
      55,    56,    57,    58,    59,    60,    61,    62,    63,    64,
      65,    66,    67,    68,    69,    70,    71,    72,    73,    74,
      75,    76,    77,    78,    79,    80,    81,    82,    83,    84,
      85,    86,    87,    88,    89,    90,    91,    92,    93,    94,
      95,    96,    97,    98,    99,   100,   101,   102,   103,   104,
     105,   106,   107,   108,   109,   110,   111,   112,   113,   114,
     115,   116,   117,   118,   119,   120,   121,   122,   123,   124
};

#if YYDEBUG
/* YYPRHS[YYN] -- Index of the first RHS symbol of rule number YYN in
   YYRHS.  */
static const yytype_uint16 yyprhs[] =
{
       0,     0,     3,     5,     6,     9,    11,    13,    15,    17,
      19,    21,    24,    32,    41,    44,    48,    52,    56,    60,
      63,    67,    71,    75,    79,    82,    86,    90,    94,    98,
     100,   103,   107,   111,   115,   119,   126,   133,   142,   149,
     156,   165,   167,   169,   171,   173,   175,   177,   179,   181,
     183,   185,   187,   190,   193,   195,   198,   200,   202,   204,
     206,   208,   210,   212,   214,   216,   218,   220,   222,   224,
     226,   228,   230,   232,   234,   236,   239,   242,   245,   248,
     250,   253,   256,   259,   262,   265,   268,   270,   273,   276,
     279,   282,   285,   287,   290,   293,   296,   299,   302,   305,
     307,   310,   313,   316,   321,   326,   328,   330,   332,   335,
     338,   341,   344,   346,   349,   352,   355,   357,   359,   361,
     363,   365,   367,   369,   371,   373,   375,   377,   379,   381,
     383,   385,   387,   389,   391,   393,   395,   397,   399,   401,
     403,   410,   418,   421,   429,   438,   442,   444,   446,   447,
     450,   453,   456,   459,   462,   464,   468,   473,   477,   482,
     485,   487,   490,   492,   493,   495,   498,   503,   509,   512,
     518,   525,   531,   538,   542,   549,   557,   559,   563,   567,
     571,   572,   575,   577,   581,   583,   587,   589,   592,   597,
     602,   604,   607,   612,   614,   617,   622,   627,   629,   632,
     637,   639,   643,   646,   648,   650,   652,   655,   657,   660,
     661,   664,   666,   669,   673,   678,   680,   682,   685,   686,
     690,   693,   695,   697,   699,   702,   706,   712,   715,   718,
     722,   728,   731,   733,   735,   737,   739,   741,   743,   746,
     748,   750,   753,   757,   761,   766,   768,   773,   779,   782,
     786,   790,   795,   800,   802,   806,   808,   810,   812,   814,
     817,   821,   824,   828,   833,   835,   839,   841,   843,   846,
     850,   857,   861,   866,   868,   870,   872,   874,   880,   881,
     883,   886,   890,   895,   897,   900,   903,   907,   911,   915,
     919,   924,   926,   928,   930,   932,   934,   936,   938,   943,
     948,   955,   959,   964,   965,   967,   969,   972,   976,   978,
     982,   983,   986,   988,   990,   992,   995,   998,  1004,  1012,
    1018,  1024,  1032,  1042,  1046,  1051,  1054,  1057,  1061,  1063,
    1065,  1067,  1069,  1071,  1073,  1076,  1078,  1080,  1082,  1086,
    1088,  1090,  1092,  1099,  1105,  1107,  1112,  1116,  1121,  1125,
    1129,  1132,  1135,  1137,  1144,  1146,  1150,  1152,  1155,  1158,
    1161,  1164,  1169,  1171,  1173,  1175,  1177,  1179,  1186,  1193,
    1196,  1201,  1204,  1206,  1208,  1211,  1213,  1215,  1217,  1219,
    1221,  1223,  1225,  1230,  1232,  1236,  1240,  1244,  1246,  1250,
    1254,  1256,  1260,  1264,  1266,  1270,  1274,  1278,  1282,  1284,
    1288,  1292,  1294,  1298,  1300,  1304,  1306,  1310,  1312,  1316,
    1318,  1322,  1324,  1330,  1335,  1337,  1341,  1343,  1345,  1347,
    1349,  1351,  1353,  1355,  1357,  1359,  1361,  1363,  1365,  1369,
    1371,  1372,  1374,  1375,  1377,  1379,  1382,  1389,  1391,  1393,
    1394,  1396,  1399,  1404,  1405,  1408,  1412,  1414,  1416,  1418,
    1420,  1422,  1424,  1426,  1428,  1430,  1432,  1434,  1436,  1438,
    1440,  1442,  1444,  1446,  1448,  1450,  1452,  1454,  1456,  1458,
    1460,  1462,  1464,  1466,  1468,  1470,  1472,  1474,  1476,  1478,
    1480,  1482,  1484,  1486,  1488,  1490,  1492,  1494,  1496,  1498,
    1500,  1502,  1504,  1506,  1508,  1510,  1512,  1514,  1516,  1518,
    1520,  1522,  1524,  1526,  1528,  1530,  1532,  1534,  1536,  1538,
    1540,  1542,  1544,  1547,  1552,  1553,  1555,  1561,  1568,  1576,
    1582,  1586,  1588,  1589,  1591,  1593,  1597,  1602,  1610,  1612,
    1616,  1618,  1620,  1622,  1623,  1624,  1625,  1626,  1627,  1628,
    1629,  1630
};

/* YYRHS -- A `-1'-separated list of the rules' RHS.  */
static const yytype_int16 yyrhs[] =
{
     126,     0,    -1,   127,    -1,    -1,   127,   128,    -1,   130,
      -1,   134,    -1,   276,    -1,   129,    -1,    89,    -1,   131,
      -1,   109,   131,    -1,   132,   293,    70,   223,   227,   291,
      71,    -1,   133,   293,   229,    70,   223,   227,   291,    71,
      -1,   206,   288,    -1,   138,   206,   286,    -1,   139,   206,
     286,    -1,   140,   206,   286,    -1,   141,   206,   286,    -1,
     212,   288,    -1,   138,   212,   286,    -1,   139,   212,   286,
      -1,   140,   212,   286,    -1,   141,   212,   286,    -1,   212,
     288,    -1,   138,   212,   286,    -1,   139,   212,   286,    -1,
     140,   212,   286,    -1,   141,   212,   286,    -1,   135,    -1,
     109,   135,    -1,   150,   294,    89,    -1,   151,   294,    89,
      -1,   137,   294,    89,    -1,   136,   294,    89,    -1,   140,
     206,   286,   278,   268,   187,    -1,   141,   206,   286,   278,
     268,   187,    -1,   136,    67,   268,   206,   287,   278,   268,
     187,    -1,   138,   197,   286,   278,   268,   187,    -1,   139,
     197,   286,   278,   268,   187,    -1,   137,    67,   268,   197,
     287,   278,   268,   187,    -1,   148,    -1,   150,    -1,   152,
      -1,   158,    -1,   154,    -1,   149,    -1,   151,    -1,   153,
      -1,   159,    -1,   155,    -1,   161,    -1,   141,   161,    -1,
     140,   142,    -1,   143,    -1,   141,   143,    -1,   143,    -1,
     161,    -1,   144,    -1,   145,    -1,   146,    -1,   270,    -1,
     147,    -1,    19,    -1,   107,    -1,   108,    -1,    30,    -1,
     122,    -1,   123,    -1,    94,    -1,   114,    -1,   115,    -1,
     110,    -1,   111,    -1,   112,    -1,   149,   161,    -1,   140,
     162,    -1,   148,   142,    -1,   148,   162,    -1,   162,    -1,
     141,   162,    -1,   149,   143,    -1,   149,   162,    -1,   151,
     161,    -1,   140,   165,    -1,   150,   142,    -1,   165,    -1,
     141,   165,    -1,   151,   143,    -1,   153,   161,    -1,   140,
      42,    -1,   152,   142,    -1,    42,    -1,   141,    42,    -1,
     153,   143,    -1,   155,   161,    -1,   140,   156,    -1,   154,
     142,    -1,   154,   156,    -1,   156,    -1,   141,   156,    -1,
     155,   143,    -1,   155,   156,    -1,   157,    65,   186,    66,
      -1,   157,    65,   265,    66,    -1,   119,    -1,   120,    -1,
     121,    -1,   159,   161,    -1,   140,   160,    -1,   158,   142,
      -1,   158,   160,    -1,   160,    -1,   141,   160,    -1,   159,
     143,    -1,   159,   160,    -1,   105,    -1,    14,    -1,    16,
      -1,    33,    -1,     3,    -1,    13,    -1,    26,    -1,    15,
      -1,    21,    -1,     5,    -1,     9,    -1,    20,    -1,     4,
      -1,   163,    -1,    22,    -1,    92,    -1,   164,    -1,    25,
      -1,   116,    -1,   117,    -1,    93,    -1,   106,    -1,   166,
      -1,   176,    -1,   167,   290,    70,   168,   291,    71,    -1,
     167,   185,   290,    70,   168,   291,    71,    -1,   167,   185,
      -1,   167,   269,   290,    70,   168,   291,    71,    -1,   167,
     269,   185,   290,    70,   168,   291,    71,    -1,   167,   269,
     185,    -1,     6,    -1,    18,    -1,    -1,   168,   169,    -1,
     171,    89,    -1,   170,    89,    -1,   141,    89,    -1,   139,
      89,    -1,    89,    -1,   141,   173,   268,    -1,   170,    67,
     173,   268,    -1,   139,   172,   268,    -1,   171,    67,   172,
     268,    -1,   197,   174,    -1,   175,    -1,   206,   174,    -1,
     175,    -1,    -1,   175,    -1,    88,   266,    -1,    12,    70,
     177,    71,    -1,    12,   185,    70,   177,    71,    -1,    12,
     185,    -1,    12,    70,   177,    67,    71,    -1,    12,   185,
      70,   177,    67,    71,    -1,    12,   269,    70,   177,    71,
      -1,    12,   269,   185,    70,   177,    71,    -1,    12,   269,
     185,    -1,    12,   269,    70,   177,    67,    71,    -1,    12,
     269,   185,    70,   177,    67,    71,    -1,   178,    -1,   177,
      67,   178,    -1,    35,   289,   179,    -1,    42,   289,   179,
      -1,    -1,    90,   266,    -1,   181,    -1,   181,    67,    54,
      -1,   182,    -1,   181,    67,   182,    -1,   138,    -1,   138,
     214,    -1,   138,   206,   286,   268,    -1,   138,   200,   286,
     268,    -1,   140,    -1,   140,   214,    -1,   140,   206,   286,
     268,    -1,   139,    -1,   139,   214,    -1,   139,   206,   286,
     268,    -1,   139,   200,   286,   268,    -1,   141,    -1,   141,
     214,    -1,   141,   206,   286,   268,    -1,   184,    -1,   183,
      67,   184,    -1,    35,   288,    -1,    35,    -1,    42,    -1,
     139,    -1,   139,   214,    -1,   141,    -1,   141,   214,    -1,
      -1,    90,   188,    -1,   189,    -1,   192,   189,    -1,    70,
     191,    71,    -1,    70,   191,   188,    71,    -1,   263,    -1,
     191,    -1,   191,   188,    -1,    -1,   191,   188,    67,    -1,
     193,    90,    -1,   195,    -1,   196,    -1,   194,    -1,   193,
     194,    -1,    72,   266,    73,    -1,    72,   266,    54,   266,
      73,    -1,    74,    35,    -1,    74,    42,    -1,    72,   266,
      73,    -1,    72,   266,    54,   266,    73,    -1,    35,    88,
      -1,   198,    -1,   206,    -1,   199,    -1,   203,    -1,   200,
      -1,    42,    -1,    42,   215,    -1,   201,    -1,   202,    -1,
      76,   200,    -1,    76,   141,   200,    -1,    65,   201,    66,
      -1,    65,   201,    66,   215,    -1,   204,    -1,    76,    65,
     205,    66,    -1,    76,   141,    65,   205,    66,    -1,    76,
     203,    -1,    76,   141,   203,    -1,    65,   203,    66,    -1,
      65,   205,   215,    66,    -1,    65,   203,    66,   215,    -1,
      42,    -1,    65,   205,    66,    -1,   207,    -1,   208,    -1,
     210,    -1,   209,    -1,    76,   206,    -1,    76,   141,   206,
      -1,   210,   215,    -1,    65,   208,    66,    -1,    65,   208,
      66,   215,    -1,   211,    -1,    65,   210,    66,    -1,    35,
      -1,   213,    -1,    76,   212,    -1,    76,   141,   212,    -1,
     210,    65,   290,   183,   292,    66,    -1,    65,   212,    66,
      -1,    65,   212,    66,   215,    -1,   218,    -1,   219,    -1,
     215,    -1,   217,    -1,    65,   290,   216,   292,    66,    -1,
      -1,   180,    -1,    72,    73,    -1,    72,   266,    73,    -1,
     217,    72,   266,    73,    -1,    76,    -1,    76,   141,    -1,
      76,   214,    -1,    76,   141,   214,    -1,    65,   218,    66,
      -1,    65,   219,    66,    -1,    65,   215,    66,    -1,    65,
     218,    66,   215,    -1,   221,    -1,   222,    -1,   230,    -1,
     231,    -1,   232,    -1,   233,    -1,   279,    -1,   185,    88,
     268,   220,    -1,    11,   266,    88,   220,    -1,    11,   266,
      54,   266,    88,   220,    -1,    27,    88,   220,    -1,    70,
     223,   227,    71,    -1,    -1,   224,    -1,   225,    -1,   224,
     225,    -1,   113,   226,    89,    -1,    35,    -1,   226,    67,
      35,    -1,    -1,   227,   228,    -1,   134,    -1,   220,    -1,
     134,    -1,   229,   134,    -1,   267,    89,    -1,    32,    65,
     265,    66,   220,    -1,    32,    65,   265,    66,   220,     8,
     220,    -1,    10,    65,   265,    66,   220,    -1,    34,    65,
     265,    66,   220,    -1,    31,   220,    34,    65,   265,    66,
      89,    -1,    24,    65,   267,    89,   267,    89,   267,    66,
     220,    -1,    28,   185,    89,    -1,    28,    76,   265,    89,
      -1,    23,    89,    -1,     7,    89,    -1,    17,   267,    89,
      -1,    37,    -1,    38,    -1,    40,    -1,    41,    -1,    39,
      -1,    36,    -1,   235,    36,    -1,   237,    -1,   234,    -1,
     235,    -1,    65,   265,    66,    -1,   239,    -1,   238,    -1,
      35,    -1,   104,    65,   263,    67,   186,    66,    -1,    65,
     290,   222,   291,    66,    -1,   236,    -1,   240,    72,   265,
      73,    -1,   240,    65,    66,    -1,   240,    65,   242,    66,
      -1,   240,    74,   185,    -1,   240,    43,   185,    -1,   240,
      44,    -1,   240,    45,    -1,   241,    -1,    65,   186,    66,
      70,   190,    71,    -1,   263,    -1,   242,    67,   263,    -1,
     240,    -1,    44,   243,    -1,    45,   243,    -1,   250,   251,
      -1,    29,   243,    -1,    29,    65,   186,    66,    -1,   249,
      -1,   247,    -1,   246,    -1,   245,    -1,   244,    -1,   103,
      65,   186,    67,   186,    66,    -1,   102,    65,   186,    67,
     240,    66,    -1,   109,   251,    -1,   248,    65,   186,    66,
      -1,   248,   243,    -1,    96,    -1,    95,    -1,    52,    35,
      -1,    75,    -1,    76,    -1,    77,    -1,    78,    -1,    79,
      -1,    80,    -1,   243,    -1,    65,   186,    66,   251,    -1,
     251,    -1,   252,    76,   251,    -1,   252,    81,   251,    -1,
     252,    82,   251,    -1,   252,    -1,   253,    77,   252,    -1,
     253,    78,   252,    -1,   253,    -1,   254,    46,   253,    -1,
     254,    47,   253,    -1,   254,    -1,   255,    83,   254,    -1,
     255,    84,   254,    -1,   255,    48,   254,    -1,   255,    49,
     254,    -1,   255,    -1,   256,    50,   255,    -1,   256,    51,
     255,    -1,   256,    -1,   257,    75,   256,    -1,   257,    -1,
     258,    85,   257,    -1,   258,    -1,   259,    86,   258,    -1,
     259,    -1,   260,    52,   259,    -1,   260,    -1,   261,    53,
     260,    -1,   261,    -1,   261,    87,   265,    88,   262,    -1,
     261,    87,    88,   262,    -1,   262,    -1,   243,   264,   263,
      -1,    90,    -1,    55,    -1,    56,    -1,    57,    -1,    58,
      -1,    59,    -1,    60,    -1,    61,    -1,    62,    -1,    63,
      -1,    64,    -1,   263,    -1,   265,    67,   263,    -1,   262,
      -1,    -1,   265,    -1,    -1,   269,    -1,   270,    -1,   269,
     270,    -1,   271,    65,    65,   272,    66,    66,    -1,   100,
      -1,   101,    -1,    -1,   273,    -1,   275,   274,    -1,   273,
      67,   275,   274,    -1,    -1,    65,    66,    -1,    65,   242,
      66,    -1,    35,    -1,     3,    -1,     4,    -1,     5,    -1,
       6,    -1,     7,    -1,     8,    -1,     9,    -1,    10,    -1,
      11,    -1,    12,    -1,    13,    -1,    14,    -1,    15,    -1,
      16,    -1,    17,    -1,    18,    -1,    19,    -1,    20,    -1,
      21,    -1,    22,    -1,    23,    -1,    24,    -1,    25,    -1,
      26,    -1,    27,    -1,    28,    -1,    29,    -1,    30,    -1,
      31,    -1,    32,    -1,    33,    -1,    34,    -1,    91,    -1,
      92,    -1,    93,    -1,    94,    -1,    95,    -1,    96,    -1,
      97,    -1,    98,    -1,    99,    -1,   100,    -1,   101,    -1,
     102,    -1,   103,    -1,   104,    -1,   105,    -1,   106,    -1,
     107,    -1,   108,    -1,   109,    -1,   110,    -1,   111,    -1,
     112,    -1,   113,    -1,   114,    -1,   115,    -1,   116,    -1,
     117,    -1,   118,    -1,   119,    -1,   120,    -1,   121,    -1,
     122,    -1,   123,    -1,   277,    89,    -1,   285,    65,   235,
      66,    -1,    -1,   277,    -1,   285,    65,   280,    66,    89,
      -1,   285,   143,    65,   280,    66,    89,    -1,   235,    88,
     281,    88,   281,    88,   284,    -1,   235,    88,   281,    88,
     281,    -1,   235,    88,   281,    -1,   235,    -1,    -1,   282,
      -1,   283,    -1,   282,    67,   283,    -1,   235,    65,   265,
      66,    -1,    72,   275,    73,   235,    65,   265,    66,    -1,
     235,    -1,   284,    67,   235,    -1,    97,    -1,    98,    -1,
      99,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1
};

/* YYRLINE[YYN] -- source line where rule number YYN was defined.  */
static const yytype_uint16 yyrline[] =
{
       0,   174,   174,   179,   181,   185,   186,   187,   188,   192,
     196,   197,   201,   202,   206,   207,   208,   209,   210,   212,
     213,   214,   215,   216,   220,   221,   222,   223,   224,   260,
     261,   265,   266,   267,   268,   275,   276,   277,   281,   282,
     283,   287,   288,   289,   290,   291,   295,   296,   297,   298,
     299,   303,   304,   305,   309,   310,   314,   315,   319,   320,
     321,   322,   323,   327,   328,   329,   333,   334,   335,   339,
     340,   341,   345,   346,   347,   351,   352,   353,   354,   358,
     359,   360,   361,   365,   366,   367,   371,   372,   373,   378,
     379,   380,   384,   385,   386,   390,   391,   392,   393,   397,
     398,   399,   400,   404,   405,   409,   410,   411,   415,   416,
     417,   418,   422,   423,   424,   425,   429,   433,   434,   435,
     436,   437,   441,   442,   443,   444,   445,   446,   447,   448,
     449,   450,   451,   455,   456,   457,   461,   462,   466,   467,
     471,   472,   474,   475,   476,   478,   482,   483,   486,   488,
     492,   493,   494,   495,   496,   500,   501,   505,   506,   511,
     512,   516,   517,   520,   522,   526,   530,   531,   532,   533,
     534,   535,   536,   537,   538,   539,   548,   549,   553,   554,
     557,   559,   563,   564,   568,   569,   573,   574,   575,   576,
     577,   578,   579,   580,   581,   582,   583,   584,   585,   586,
     594,   595,   599,   603,   604,   608,   609,   610,   611,   614,
     616,   620,   621,   630,   631,   632,   636,   637,   640,   641,
     645,   646,   647,   651,   652,   656,   657,   658,   659,   663,
     664,   668,   672,   673,   677,   681,   682,   686,   687,   688,
     695,   696,   697,   701,   702,   709,   710,   711,   713,   714,
     718,   719,   720,   724,   725,   729,   733,   734,   738,   739,
     740,   744,   745,   746,   750,   751,   755,   759,   760,   761,
     765,   766,   767,   771,   772,   773,   777,   778,   780,   782,
     786,   787,   788,   792,   793,   794,   795,   799,   800,   801,
     802,   808,   809,   810,   811,   812,   813,   814,   818,   819,
     820,   821,   832,   835,   837,   841,   842,   846,   850,   851,
     854,   855,   859,   860,   864,   865,   874,   878,   879,   880,
     884,   885,   886,   891,   892,   893,   894,   895,   902,   903,
     907,   908,   909,   914,   915,   921,   922,   923,   924,   925,
     926,   930,   934,   938,   941,   942,   943,   944,   945,   946,
     947,   948,   949,   953,   957,   958,   962,   963,   964,   965,
     966,   967,   968,   969,   970,   971,   972,   976,   980,   984,
     988,   989,   993,   994,   998,  1002,  1003,  1004,  1005,  1006,
    1007,  1011,  1012,  1016,  1017,  1018,  1019,  1023,  1024,  1025,
    1029,  1030,  1031,  1035,  1036,  1037,  1038,  1039,  1043,  1044,
    1045,  1049,  1050,  1054,  1055,  1059,  1060,  1064,  1065,  1069,
    1070,  1074,  1075,  1077,  1082,  1083,  1087,  1088,  1089,  1090,
    1091,  1092,  1093,  1094,  1095,  1096,  1097,  1101,  1102,  1106,
    1110,  1112,  1115,  1117,  1121,  1122,  1126,  1130,  1131,  1134,
    1136,  1140,  1141,  1144,  1146,  1147,  1151,  1152,  1153,  1154,
    1155,  1156,  1157,  1158,  1159,  1160,  1161,  1162,  1163,  1164,
    1165,  1166,  1167,  1168,  1169,  1170,  1171,  1172,  1173,  1174,
    1175,  1176,  1177,  1178,  1179,  1180,  1181,  1182,  1183,  1184,
    1185,  1186,  1187,  1188,  1189,  1190,  1191,  1192,  1193,  1194,
    1195,  1196,  1197,  1198,  1199,  1200,  1201,  1202,  1203,  1204,
    1205,  1206,  1207,  1208,  1209,  1210,  1211,  1212,  1213,  1214,
    1215,  1216,  1222,  1226,  1229,  1231,  1235,  1236,  1240,  1241,
    1242,  1243,  1246,  1248,  1252,  1253,  1257,  1258,  1262,  1263,
    1267,  1268,  1269,  1278,  1281,  1284,  1287,  1290,  1293,  1296,
    1299,  1302
};
#endif

#if YYDEBUG || YYERROR_VERBOSE || YYTOKEN_TABLE
/* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
   First, the terminals, then, starting at YYNTOKENS, nonterminals.  */
static const char *const yytname[] =
{
  "$end", "error", "$undefined", "AUTO", "DOUBLE", "INT", "STRUCT",
  "BREAK", "ELSE", "LONG", "SWITCH", "CASE", "ENUM", "REGISTER", "TYPEDEF",
  "CHAR", "EXTERN", "RETURN", "UNION", "CONST", "FLOAT", "SHORT",
  "UNSIGNED", "CONTINUE", "FOR", "SIGNED", "VOID", "DEFAULT", "GOTO",
  "SIZEOF", "VOLATILE", "DO", "IF", "STATIC", "WHILE", "IDENTIFIER",
  "STRINGliteral", "FLOATINGconstant", "INTEGERconstant",
  "CHARACTERconstant", "OCTALconstant", "HEXconstant", "TYPEDEFname",
  "ARROW", "ICR", "DECR", "LS", "RS", "LE", "GE", "EQ", "NE", "ANDAND",
  "OROR", "ELLIPSIS", "MULTassign", "DIVassign", "MODassign", "PLUSassign",
  "MINUSassign", "LSassign", "RSassign", "ANDassign", "ERassign",
  "ORassign", "LPAREN", "RPAREN", "COMMA", "HASH", "DHASH", "LBRACE",
  "RBRACE", "LBRACK", "RBRACK", "DOT", "AND", "STAR", "PLUS", "MINUS",
  "NEGATE", "NOT", "DIV", "MOD", "LT", "GT", "XOR", "PIPE", "QUESTION",
  "COLON", "SEMICOLON", "ASSIGN", "ASMSYM", "_BOOL", "_COMPLEX",
  "RESTRICT", "__ALIGNOF", "__ALIGNOF__", "ASM", "__ASM", "__ASM__",
  "__ATTRIBUTE", "__ATTRIBUTE__", "__BUILTIN_OFFSETOF",
  "__BUILTIN_TYPES_COMPATIBLE_P", "__BUILTIN_VA_ARG", "__BUILTIN_VA_LIST",
  "__COMPLEX__", "__CONST", "__CONST__", "__EXTENSION__", "INLINE",
  "__INLINE", "__INLINE__", "__LABEL__", "__RESTRICT", "__RESTRICT__",
  "__SIGNED", "__SIGNED__", "__THREAD", "TYPEOF", "__TYPEOF", "__TYPEOF__",
  "__VOLATILE", "__VOLATILE__", "PPNUM", "$accept", "TranslationUnit",
  "ExternalDeclarationList", "ExternalDeclaration", "EmptyDefinition",
  "FunctionDefinitionExtension", "FunctionDefinition",
  "FunctionDeclarator", "FunctionOldPrototype", "DeclarationExtension",
  "Declaration", "DefaultDeclaringList", "DeclaringList",
  "DeclarationSpecifier", "TypeSpecifier", "DeclarationQualifierList",
  "TypeQualifierList", "DeclarationQualifier", "TypeQualifier",
  "ConstQualifier", "VolatileQualifier", "RestrictQualifier",
  "FunctionSpecifier", "BasicDeclarationSpecifier", "BasicTypeSpecifier",
  "SUEDeclarationSpecifier", "SUETypeSpecifier",
  "TypedefDeclarationSpecifier", "TypedefTypeSpecifier",
  "TypeofDeclarationSpecifier", "TypeofTypeSpecifier", "Typeofspecifier",
  "Typeofkeyword", "VarArgDeclarationSpecifier", "VarArgTypeSpecifier",
  "VarArgTypeName", "StorageClass", "BasicTypeName", "SignedKeyword",
  "ComplexKeyword", "ElaboratedTypeName", "StructOrUnionSpecifier",
  "StructOrUnion", "StructDeclarationList", "StructDeclaration",
  "StructDefaultDeclaringList", "StructDeclaringList", "StructDeclarator",
  "StructIdentifierDeclarator", "BitFieldSizeOpt", "BitFieldSize",
  "EnumSpecifier", "EnumeratorList", "Enumerator", "EnumeratorValueOpt",
  "ParameterTypeList", "ParameterList", "ParameterDeclaration",
  "IdentifierList", "Identifier", "IdentifierOrTypedefName", "TypeName",
  "InitializerOpt", "DesignatedInitializer", "Initializer",
  "InitializerList", "MatchedInitializerList", "Designation",
  "DesignatorList", "Designator", "ObsoleteArrayDesignation",
  "ObsoleteFieldDesignation", "Declarator", "TypedefDeclarator",
  "TypedefDeclaratorMain", "ParameterTypedefDeclarator",
  "CleanTypedefDeclarator", "CleanPostfixTypedefDeclarator",
  "ParenTypedefDeclarator", "ParenPostfixTypedefDeclarator",
  "SimpleParenTypedefDeclarator", "IdentifierDeclarator",
  "IdentifierDeclaratorMain", "UnaryIdentifierDeclarator",
  "PostfixIdentifierDeclarator", "ParenIdentifierDeclarator",
  "SimpleDeclarator", "OldFunctionDeclarator",
  "PostfixOldFunctionDeclarator", "AbstractDeclarator",
  "PostfixingAbstractDeclarator", "ParameterTypeListOpt",
  "ArrayAbstractDeclarator", "UnaryAbstractDeclarator",
  "PostfixAbstractDeclarator", "Statement", "LabeledStatement",
  "CompoundStatement", "LocalLabelDeclarationListOpt",
  "LocalLabelDeclarationList", "LocalLabelDeclaration", "LocalLabelList",
  "DeclarationOrStatementList", "DeclarationOrStatement",
  "DeclarationList", "ExpressionStatement", "SelectionStatement",
  "IterationStatement", "JumpStatement", "Constant", "StringLiteralList",
  "PrimaryExpression", "PrimaryIdentifier", "VariableArgumentAccess",
  "StatementAsExpression", "PostfixExpression", "CompoundLiteral",
  "ArgumentExpressionList", "UnaryExpression",
  "TypeCompatibilityExpression", "OffsetofExpression",
  "ExtensionExpression", "AlignofExpression", "Alignofkeyword",
  "LabelAddressExpression", "Unaryoperator", "CastExpression",
  "MultiplicativeExpression", "AdditiveExpression", "ShiftExpression",
  "RelationalExpression", "EqualityExpression", "AndExpression",
  "ExclusiveOrExpression", "InclusiveOrExpression", "LogicalAndExpression",
  "LogicalORExpression", "ConditionalExpression", "AssignmentExpression",
  "AssignmentOperator", "Expression", "ConstantExpression",
  "ExpressionOpt", "AttributeSpecifierListOpt", "AttributeSpecifierList",
  "AttributeSpecifier", "AttributeKeyword", "AttributeListOpt",
  "AttributeList", "AttributeExpressionOpt", "Word", "AssemblyDefinition",
  "AssemblyExpression", "AssemblyExpressionOpt", "AssemblyStatement",
  "Assemblyargument", "AssemblyoperandsOpt", "Assemblyoperands",
  "Assemblyoperand", "Assemblyclobbers", "AsmKeyword", "BindIdentifier",
  "BindIdentifierInList", "BindVar", "BindEnum", "EnterScope", "ExitScope",
  "ExitReentrantScope", "ReenterScope", "KillReentrantScope", 0
};
#endif

# ifdef YYPRINT
/* YYTOKNUM[YYLEX-NUM] -- Internal token number corresponding to
   token YYLEX-NUM.  */
static const yytype_uint16 yytoknum[] =
{
       0,   256,   257,   258,   259,   260,   261,   262,   263,   264,
     265,   266,   267,   268,   269,   270,   271,   272,   273,   274,
     275,   276,   277,   278,   279,   280,   281,   282,   283,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   314,
     315,   316,   317,   318,   319,   320,   321,   322,   323,   324,
     325,   326,   327,   328,   329,   330,   331,   332,   333,   334,
     335,   336,   337,   338,   339,   340,   341,   342,   343,   344,
     345,   346,   347,   348,   349,   350,   351,   352,   353,   354,
     355,   356,   357,   358,   359,   360,   361,   362,   363,   364,
     365,   366,   367,   368,   369,   370,   371,   372,   373,   374,
     375,   376,   377,   378,   379
};
# endif

/* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */
static const yytype_uint16 yyr1[] =
{
       0,   125,   126,   127,   127,   128,   128,   128,   128,   129,
     130,   130,   131,   131,   132,   132,   132,   132,   132,   132,
     132,   132,   132,   132,   133,   133,   133,   133,   133,   134,
     134,   135,   135,   135,   135,   136,   136,   136,   137,   137,
     137,   138,   138,   138,   138,   138,   139,   139,   139,   139,
     139,   140,   140,   140,   141,   141,   142,   142,   143,   143,
     143,   143,   143,   144,   144,   144,   145,   145,   145,   146,
     146,   146,   147,   147,   147,   148,   148,   148,   148,   149,
     149,   149,   149,   150,   150,   150,   151,   151,   151,   152,
     152,   152,   153,   153,   153,   154,   154,   154,   154,   155,
     155,   155,   155,   156,   156,   157,   157,   157,   158,   158,
     158,   158,   159,   159,   159,   159,   160,   161,   161,   161,
     161,   161,   162,   162,   162,   162,   162,   162,   162,   162,
     162,   162,   162,   163,   163,   163,   164,   164,   165,   165,
     166,   166,   166,   166,   166,   166,   167,   167,   168,   168,
     169,   169,   169,   169,   169,   170,   170,   171,   171,   172,
     172,   173,   173,   174,   174,   175,   176,   176,   176,   176,
     176,   176,   176,   176,   176,   176,   177,   177,   178,   178,
     179,   179,   180,   180,   181,   181,   182,   182,   182,   182,
     182,   182,   182,   182,   182,   182,   182,   182,   182,   182,
     183,   183,   184,   185,   185,   186,   186,   186,   186,   187,
     187,   188,   188,   189,   189,   189,   190,   190,   191,   191,
     192,   192,   192,   193,   193,   194,   194,   194,   194,   195,
     195,   196,   197,   197,   198,   199,   199,   200,   200,   200,
     201,   201,   201,   202,   202,   203,   203,   203,   203,   203,
     204,   204,   204,   205,   205,   206,   207,   207,   208,   208,
     208,   209,   209,   209,   210,   210,   211,   212,   212,   212,
     213,   213,   213,   214,   214,   214,   215,   215,   216,   216,
     217,   217,   217,   218,   218,   218,   218,   219,   219,   219,
     219,   220,   220,   220,   220,   220,   220,   220,   221,   221,
     221,   221,   222,   223,   223,   224,   224,   225,   226,   226,
     227,   227,   228,   228,   229,   229,   230,   231,   231,   231,
     232,   232,   232,   233,   233,   233,   233,   233,   234,   234,
     234,   234,   234,   235,   235,   236,   236,   236,   236,   236,
     236,   237,   238,   239,   240,   240,   240,   240,   240,   240,
     240,   240,   240,   241,   242,   242,   243,   243,   243,   243,
     243,   243,   243,   243,   243,   243,   243,   244,   245,   246,
     247,   247,   248,   248,   249,   250,   250,   250,   250,   250,
     250,   251,   251,   252,   252,   252,   252,   253,   253,   253,
     254,   254,   254,   255,   255,   255,   255,   255,   256,   256,
     256,   257,   257,   258,   258,   259,   259,   260,   260,   261,
     261,   262,   262,   262,   263,   263,   264,   264,   264,   264,
     264,   264,   264,   264,   264,   264,   264,   265,   265,   266,
     267,   267,   268,   268,   269,   269,   270,   271,   271,   272,
     272,   273,   273,   274,   274,   274,   275,   275,   275,   275,
     275,   275,   275,   275,   275,   275,   275,   275,   275,   275,
     275,   275,   275,   275,   275,   275,   275,   275,   275,   275,
     275,   275,   275,   275,   275,   275,   275,   275,   275,   275,
     275,   275,   275,   275,   275,   275,   275,   275,   275,   275,
     275,   275,   275,   275,   275,   275,   275,   275,   275,   275,
     275,   275,   275,   275,   275,   275,   275,   275,   275,   275,
     275,   275,   276,   277,   278,   278,   279,   279,   280,   280,
     280,   280,   281,   281,   282,   282,   283,   283,   284,   284,
     285,   285,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294
};

/* YYR2[YYN] -- Number of symbols composing right hand side of rule YYN.  */
static const yytype_uint8 yyr2[] =
{
       0,     2,     1,     0,     2,     1,     1,     1,     1,     1,
       1,     2,     7,     8,     2,     3,     3,     3,     3,     2,
       3,     3,     3,     3,     2,     3,     3,     3,     3,     1,
       2,     3,     3,     3,     3,     6,     6,     8,     6,     6,
       8,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     2,     2,     1,     2,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     2,     2,     2,     2,     1,
       2,     2,     2,     2,     2,     2,     1,     2,     2,     2,
       2,     2,     1,     2,     2,     2,     2,     2,     2,     1,
       2,     2,     2,     4,     4,     1,     1,     1,     2,     2,
       2,     2,     1,     2,     2,     2,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       6,     7,     2,     7,     8,     3,     1,     1,     0,     2,
       2,     2,     2,     2,     1,     3,     4,     3,     4,     2,
       1,     2,     1,     0,     1,     2,     4,     5,     2,     5,
       6,     5,     6,     3,     6,     7,     1,     3,     3,     3,
       0,     2,     1,     3,     1,     3,     1,     2,     4,     4,
       1,     2,     4,     1,     2,     4,     4,     1,     2,     4,
       1,     3,     2,     1,     1,     1,     2,     1,     2,     0,
       2,     1,     2,     3,     4,     1,     1,     2,     0,     3,
       2,     1,     1,     1,     2,     3,     5,     2,     2,     3,
       5,     2,     1,     1,     1,     1,     1,     1,     2,     1,
       1,     2,     3,     3,     4,     1,     4,     5,     2,     3,
       3,     4,     4,     1,     3,     1,     1,     1,     1,     2,
       3,     2,     3,     4,     1,     3,     1,     1,     2,     3,
       6,     3,     4,     1,     1,     1,     1,     5,     0,     1,
       2,     3,     4,     1,     2,     2,     3,     3,     3,     3,
       4,     1,     1,     1,     1,     1,     1,     1,     4,     4,
       6,     3,     4,     0,     1,     1,     2,     3,     1,     3,
       0,     2,     1,     1,     1,     2,     2,     5,     7,     5,
       5,     7,     9,     3,     4,     2,     2,     3,     1,     1,
       1,     1,     1,     1,     2,     1,     1,     1,     3,     1,
       1,     1,     6,     5,     1,     4,     3,     4,     3,     3,
       2,     2,     1,     6,     1,     3,     1,     2,     2,     2,
       2,     4,     1,     1,     1,     1,     1,     6,     6,     2,
       4,     2,     1,     1,     2,     1,     1,     1,     1,     1,
       1,     1,     4,     1,     3,     3,     3,     1,     3,     3,
       1,     3,     3,     1,     3,     3,     3,     3,     1,     3,
       3,     1,     3,     1,     3,     1,     3,     1,     3,     1,
       3,     1,     5,     4,     1,     3,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     3,     1,
       0,     1,     0,     1,     1,     2,     6,     1,     1,     0,
       1,     2,     4,     0,     2,     3,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     2,     4,     0,     1,     5,     6,     7,     5,
       3,     1,     0,     1,     1,     3,     4,     7,     1,     3,
       1,     1,     1,     0,     0,     0,     0,     0,     0,     0,
       0,     0
};

/* YYDEFACT[STATE-NAME] -- Default rule to reduce with in state
   STATE-NUM when YYTABLE doesn't specify something else to do.  Zero
   means the default is an error.  */
static const yytype_uint16 yydefact[] =
{
       3,     0,     2,     1,   120,   128,   125,   146,   126,     0,
     121,   117,   123,   118,   147,    63,   127,   124,   130,   133,
     122,    66,   119,   266,    92,     0,     0,     9,   131,   136,
      69,   530,   531,   532,   437,   438,   116,   137,    64,    65,
       0,    72,    73,    74,    70,    71,   134,   135,   105,   106,
     107,    67,    68,     4,     8,     5,    10,   540,   540,     6,
      29,   541,   541,     0,     0,     0,     0,    54,    58,    59,
      60,    62,    41,    46,    42,    47,    43,    48,    45,    50,
      99,     0,    44,    49,   112,    51,    79,   129,   132,    86,
     138,   537,   139,   535,   255,   256,   258,   257,   264,   535,
     267,    61,     0,     7,     0,     0,   203,   204,     0,   168,
       0,   434,     0,     0,     0,     0,   259,   268,    11,    30,
       0,     0,   432,     0,   432,     0,   237,     0,     0,   533,
     232,   234,   236,   239,   240,   235,   245,   233,   533,   533,
     233,   533,    90,    53,    56,    96,   109,    57,    76,    84,
     533,   533,    93,    55,   100,   113,    52,    80,    87,   533,
     533,    77,    78,    81,    75,    82,    85,     0,    88,    83,
       0,    91,    94,    89,    97,    98,   101,   102,    95,     0,
     110,   111,   114,   115,   108,   142,   537,     0,    14,   537,
       0,   261,   276,    24,     0,   512,     0,   536,   536,     0,
     176,     0,     0,   173,   435,   262,   265,   271,   260,   269,
     303,     0,   314,     0,     0,     0,     0,     0,     0,   433,
      34,     0,    33,   537,   238,   253,     0,     0,     0,     0,
       0,     0,   241,   248,   514,    15,    25,   514,    16,    26,
     514,    27,   514,    28,    31,    32,     0,   341,   333,   328,
     329,   332,   330,   331,     0,     0,     0,   537,   375,   376,
     377,   378,   379,   380,   373,   372,     0,     0,     0,     0,
     205,   207,    46,    47,    48,    50,    49,     0,   336,   337,
     344,   335,   340,   339,   356,   352,   381,   366,   365,   364,
     363,     0,   362,     0,   383,   387,   390,   393,   398,   401,
     403,   405,   407,   409,   411,   414,   427,     0,     0,   145,
       0,   148,   278,   280,   381,   429,     0,     0,   439,     0,
     180,   180,     0,   166,     0,     0,     0,   263,   272,     0,
     310,   304,   305,     0,     0,   233,   257,     0,     0,   533,
     533,   303,   315,   534,   534,   278,     0,   243,   250,     0,
       0,     0,   242,   249,   515,   432,   432,   432,   432,   537,
     360,   537,   357,   358,   374,     0,     0,     0,     0,     0,
       0,   369,   537,   283,   206,   275,   273,   274,   208,   103,
     334,     0,   350,   351,     0,     0,     0,   417,   418,   419,
     420,   421,   422,   423,   424,   425,   426,   416,     0,   537,
     371,   359,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   104,     0,   148,     0,   148,   538,   535,   186,   193,
     190,   197,    42,    47,   279,   182,   184,   539,   200,   539,
     281,     0,   447,   448,   449,   450,   451,   452,   453,   454,
     455,   456,   457,   458,   459,   460,   461,   462,   463,   464,
     465,   466,   467,   468,   469,   470,   471,   472,   473,   474,
     475,   476,   477,   478,   446,   479,   480,   481,   482,   483,
     484,   485,   486,   487,   488,   489,   490,   491,   492,   493,
     494,   495,   496,   497,   498,   499,   500,   501,   502,   503,
     504,   505,   506,   507,   508,   509,   510,   511,     0,   440,
     443,   513,     0,   178,   179,   169,   177,     0,   167,     0,
     171,     0,   308,     0,   430,   306,     0,     0,     0,     0,
       0,   514,   514,   310,   514,   514,   254,   244,   252,   251,
     246,     0,   209,   209,   209,   209,     0,     0,     0,   338,
     303,   538,     0,     0,     0,     0,     0,     0,   284,   285,
     349,   346,     0,   354,     0,   348,   415,     0,   384,   385,
     386,   388,   389,   391,   392,   396,   397,   394,   395,   399,
     400,   402,   404,   406,   408,   410,     0,     0,   428,   538,
     148,   538,   154,     0,     0,   149,     0,     0,     0,   202,
     537,   283,   533,   533,   187,   533,   533,   194,   537,   283,
     533,   191,   533,   198,     0,     0,     0,     0,   282,     0,
       0,     0,   441,   181,   170,   174,     0,   172,     0,   307,
       0,     0,     0,   430,     0,     0,     0,     0,   430,     0,
       0,   341,    92,     0,   312,     0,   313,   291,   292,   311,
     293,   294,   295,   296,   431,     0,   297,     0,     0,     0,
     430,   432,   432,   247,     0,    38,    39,    35,    36,   361,
       0,   218,   382,   310,     0,     0,     0,     0,   289,   287,
     288,   286,   347,     0,   345,   370,   413,     0,     0,   538,
       0,     0,   153,   432,   160,   163,   152,   432,   162,   163,
       0,   151,     0,   150,   140,   284,   432,   432,   432,   432,
     284,   432,   432,   183,   185,   201,   270,   277,   436,   443,
     444,     0,   175,   309,   326,     0,     0,     0,   325,   430,
     430,     0,     0,     0,     0,     0,   432,   316,     0,     0,
      12,     0,   209,   209,   341,   218,     0,     0,   210,   211,
       0,     0,   223,   221,   222,   215,     0,   216,   430,   343,
       0,     0,     0,   290,   355,   412,   141,     0,   143,   165,
     157,   159,   164,   155,   161,   432,   432,   189,   188,   196,
     195,   192,   199,   442,   445,     0,     0,   430,   327,     0,
     301,     0,   323,     0,     0,     0,   430,   521,     0,     0,
      13,    37,    40,   231,     0,     0,   227,   228,   212,     0,
     220,   224,   353,   217,   302,   368,   367,   342,   144,   156,
     158,   430,     0,   299,   430,   324,     0,   430,   430,   298,
     522,     0,     0,   213,     0,     0,   229,     0,   219,   319,
     430,     0,     0,   317,   320,     0,     0,   520,   523,   524,
     516,     0,   214,     0,     0,   225,   300,   430,     0,   430,
       0,     0,   522,     0,   517,   230,     0,     0,   321,   318,
       0,     0,   519,   525,   226,   430,     0,   526,     0,   322,
       0,   528,   518,     0,     0,   527,   529
};

/* YYDEFGOTO[NTERM-NUM].  */
static const yytype_int16 yydefgoto[] =
{
      -1,     1,     2,    53,    54,    55,    56,    57,    58,   644,
      60,    61,    62,   213,   270,   215,   271,   143,    67,    68,
      69,    70,    71,    72,   272,    74,   273,    76,   274,    78,
     275,    80,    81,    82,   276,    84,    85,    86,    87,    88,
      89,    90,    91,   426,   595,   596,   597,   693,   697,   771,
     694,    92,   199,   200,   513,   434,   435,   436,   437,   438,
     645,   277,   665,   748,   749,   756,   757,   750,   751,   752,
     753,   754,   129,   130,   131,   132,   133,   134,   228,   136,
     229,   116,    94,    95,    96,   336,    98,   114,   100,   559,
     375,   439,   192,   376,   377,   646,   647,   648,   330,   331,
     332,   523,   524,   649,   217,   650,   651,   652,   653,   278,
     279,   280,   281,   282,   283,   284,   285,   562,   286,   287,
     288,   289,   290,   291,   292,   293,   294,   295,   296,   297,
     298,   299,   300,   301,   302,   303,   304,   305,   306,   398,
     654,   316,   655,   218,   219,   101,   102,   508,   509,   622,
     510,   103,   354,   357,   656,   798,   847,   848,   849,   882,
     657,   234,   534,   188,   320,   345,   598,   616,   120,   123
};

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
#define YYPACT_NINF -724
static const yytype_int16 yypact[] =
{
    -724,   170,  1227,  -724,  -724,  -724,  -724,  -724,  -724,   106,
    -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,
    -724,  -724,  -724,  -724,  -724,   335,  3583,  -724,  -724,  -724,
    -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,
    1953,  -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,
    -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,
    -724,   -28,   185,   193,   193,  2074,  2122,  -724,  -724,  -724,
    -724,  -724,  2752,  2752,   927,   927,  2889,  2889,  1263,  1263,
    -724,    80,  2776,  2776,  -724,  -724,  -724,  -724,  -724,  -724,
    -724,    60,  -724,  -724,  -724,  -724,  -724,   140,  -724,  -724,
    -724,  -724,   206,  -724,   202,   229,  -724,  -724,   269,   240,
     336,  -724,   257,   437,   265,  3583,  -724,  -724,  -724,  -724,
     266,  2444,    93,   264,    93,   274,   290,   253,  3430,  -724,
    -724,  -724,  -724,  -724,  -724,  -724,  -724,   272,  -724,  -724,
     272,  -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,
    -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,
    -724,  -724,  -724,  -724,  -724,  -724,  -724,   297,  -724,  -724,
     300,  -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,  2930,
    -724,  -724,  -724,  -724,  -724,   359,    60,   364,  -724,  -724,
    3907,  -724,   329,   375,   390,  -724,   434,  -724,  -724,    37,
    -724,   269,   269,   403,  -724,   290,  -724,   290,  -724,  -724,
     368,  2720,  -724,   455,   455,  2243,  2291,  2412,   409,    93,
    -724,   455,  -724,  -724,  -724,  -724,   253,   418,   427,   290,
     253,  3467,  -724,  -724,   467,  -724,   428,   467,  -724,   453,
     180,   458,   349,   465,  -724,  -724,  4248,  -724,  -724,  -724,
    -724,  -724,  -724,  -724,  4301,  4301,   481,  2930,  -724,  -724,
    -724,  -724,  -724,  -724,  -724,  -724,   460,   478,   491,  4354,
     233,  3170,   855,  3400,  3400,  1099,  1730,   492,  -724,   525,
    -724,  -724,  -724,  -724,   797,  -724,   950,  -724,  -724,  -724,
    -724,  4407,  -724,  4354,  -724,   564,   136,   328,    36,   365,
     511,   510,   512,   550,    11,  -724,  -724,   366,   535,   359,
     541,  -724,  2565,  -724,  -724,  -724,   540,  4354,  1542,   130,
     528,   528,   225,  -724,   305,   401,   269,  -724,  -724,   580,
    -724,   368,  -724,   484,  3486,  -724,   290,   409,  3616,  -724,
    -724,   368,  -724,  -724,  -724,  2720,   445,   290,   290,   554,
     449,   253,  -724,  -724,  -724,    93,    93,    93,    93,  2930,
    -724,  2930,  -724,  -724,  -724,   566,   446,   568,  3331,  3331,
    4354,  -724,   233,  1293,  -724,  -724,  -724,  -724,  -724,  -724,
    -724,   352,  -724,  -724,  3960,  4354,   352,  -724,  -724,  -724,
    -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,  4354,  2930,
    -724,  -724,  4354,  4354,  4354,  4354,  4354,  4354,  4354,  4354,
    4354,  4354,  4354,  4354,  4354,  4354,  4354,  4354,  4354,  4354,
    4013,  -724,  4354,  -724,   574,  -724,  3211,  -724,    38,    38,
    1784,  1905,  2889,  2889,  -724,   587,  -724,   594,  -724,  -724,
    -724,   590,  -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,
    -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,
    -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,
    -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,
    -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,
    -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,
    -724,  -724,  -724,  -724,  -724,  -724,  -724,  -724,   601,   613,
     621,  -724,  4354,  -724,  -724,  -724,  -724,   251,  -724,   295,
    -724,   451,  -724,   -23,  1075,  -724,   484,   538,   484,  3530,
    3616,   467,   467,  -724,   467,   467,  -724,  -724,  -724,  -724,
    -724,   647,   611,   611,   611,   611,   654,   658,  4089,  -724,
     368,  -724,   662,   668,   670,   676,   685,   688,  1293,  -724,
    -724,  -724,   575,  -724,    82,  -724,  -724,   690,  -724,  -724,
    -724,   564,   564,   136,   136,   328,   328,   328,   328,    36,
      36,   365,   511,   510,   512,   550,  4354,   -40,  -724,  3211,
    -724,  3211,  -724,   909,  3050,  -724,    76,   153,   686,  -724,
      35,  3368,  -724,  -724,  -724,  -724,  -724,  -724,    97,  3294,
    -724,  -724,  -724,  -724,  2599,   724,   694,   696,  -724,   698,
    1542,  4142,  -724,  -724,  -724,  -724,   357,  -724,   733,  -724,
     687,   710,  4354,  4354,   692,   717,   699,   139,  3725,   730,
     731,   713,   714,  1663,  -724,   719,  -724,  -724,  -724,  -724,
    -724,  -724,  -724,  -724,   738,   721,  -724,  2983,   740,   484,
    1075,    93,    93,  -724,  3854,  -724,  -724,  -724,  -724,   744,
     744,  -724,  -724,  -724,   750,   864,  3331,  3331,  -724,   290,
    -724,  -724,  -724,  4354,  -724,   744,  -724,  4354,   746,  3211,
     747,  4354,  -724,    93,  -724,   732,  -724,    93,  -724,   732,
     362,  -724,   342,  -724,  -724,  3368,    93,    93,    93,    93,
    3294,    93,    93,  -724,  -724,  -724,  -724,  -724,  -724,   621,
    -724,   616,  -724,  -724,  -724,  4354,    39,   735,  -724,  4354,
    3725,  4354,   736,   787,  4354,  4354,    93,  -724,   434,   757,
    -724,   752,   611,   611,   739,  -724,  4354,   464,  -724,  -724,
    4195,    78,  -724,  -724,  -724,  -724,   755,  3854,  1421,  -724,
     734,   766,   769,  -724,  -724,  -724,  -724,   765,  -724,  -724,
    -724,  -724,  -724,  -724,  -724,    93,    93,  -724,  -724,  -724,
    -724,  -724,  -724,  -724,  -724,   626,  4354,  3725,  -724,   754,
    -724,   173,  -724,   774,   629,   638,  3725,    29,   786,   434,
    -724,  -724,  -724,  -724,  3801,    45,  -724,  -724,  -724,  4354,
    -724,  -724,  -724,   789,  -724,  -724,  -724,  -724,  -724,  -724,
    -724,  3725,   773,  -724,  4354,  -724,  4354,  3725,  3725,  -724,
      70,   778,   802,  -724,   475,  4354,   386,    51,  -724,  -724,
    3725,   784,   642,   870,  -724,  1542,    47,   775,   812,  -724,
    -724,   799,  -724,   816,  4354,  -724,  -724,  4354,   801,  3725,
     818,  4354,    70,    70,  -724,   392,   819,   827,  -724,  -724,
     434,   650,   806,  -724,  -724,  3725,   123,  -724,   434,  -724,
    4354,   525,   828,   665,   434,  -724,   525
};

/* YYPGOTO[NTERM-NUM].  */
static const yytype_int16 yypgoto[] =
{
    -724,  -724,  -724,  -724,  -724,  -724,   856,  -724,  -724,    26,
     -36,  -724,  -724,     7,     5,     9,     0,   -41,   772,  -724,
    -724,  -724,  -724,  -724,    12,  -307,    13,  -724,    14,  -724,
      15,    -7,  -724,  -724,    16,   117,   841,   435,  -724,  -724,
     124,  -724,  -724,  -356,  -724,  -724,  -724,   207,   210,   212,
    -570,  -724,  -140,  -230,   600,  -724,  -724,   308,  -724,   310,
      -1,  -215,  -467,  -723,   176,  -724,   182,  -724,  -724,   177,
    -724,  -724,   -63,  -724,  -724,  -115,   -59,  -724,   -34,  -724,
    -220,    23,  -724,    71,  -724,   138,  -724,   377,  -724,  -148,
     536,  -724,  -724,  -353,  -351,  -106,  -724,   565,  -329,  -724,
     602,  -724,  -501,  -724,  -724,  -724,  -724,  -724,  -724,  -724,
    -193,  -724,  -724,  -724,  -724,   256,  -724,   313,   166,  -724,
    -724,  -724,  -724,  -724,  -724,  -724,   -21,   145,   332,   338,
     331,   520,   521,   519,   524,   526,  -724,   -99,  -348,  -724,
    -100,  -242,  -573,   122,    48,    -9,  -724,  -724,  -724,   219,
    -600,  -724,   937,  -191,  -724,   151,    91,  -724,    95,  -724,
     254,   653,   610,   -76,   761,     6,  -126,   534,   906,   228
};

/* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
   positive, shift that token.  If negative, reduce the rule which
   number is the opposite.  If zero, do what YYDEFACT says.
   If YYTABLE_NINF, syntax error.  */
#define YYTABLE_NINF -542
static const yytype_int16 yytable[] =
{
     111,   139,    66,   319,   119,   432,   346,    64,   109,    63,
     350,    65,   533,   232,    73,    75,    77,    79,    83,   556,
     719,   557,   554,   193,   698,    93,   115,   422,    59,   135,
     135,   161,   660,   166,   813,   171,   563,   174,   432,   122,
      66,   180,   365,   355,   628,    64,   356,    63,   687,    65,
     566,   358,    73,    75,    77,    79,    83,   110,   145,   154,
     727,   324,   325,    93,   419,   380,   629,   589,   227,   591,
      23,   175,   177,    23,   588,   441,   666,   667,   668,   307,
     126,   834,   111,   380,   409,   410,   137,   140,   150,   159,
     185,   315,   516,   786,   233,   106,   112,   187,   420,   835,
     600,   204,   107,   600,   322,   854,   248,   190,   323,   203,
     190,   601,   861,   111,   601,   111,   352,   830,   836,   411,
     412,   216,   374,   378,   855,   772,   214,   787,   231,   772,
     698,   541,    23,    73,    75,    77,    79,    83,   208,   186,
      97,   106,   845,   700,   546,   179,   547,   212,   107,   422,
     809,   139,   747,   552,   553,   684,   789,   366,   344,   380,
      34,    35,   608,   113,    97,   701,   380,   227,   810,   190,
       3,   227,   758,   609,   106,   119,   108,   204,    97,   135,
     135,   107,   146,   155,   567,   309,   521,   135,   880,   149,
     158,   308,   310,    34,    35,   312,   511,   353,   112,   181,
     183,    97,    97,    97,    97,   189,    34,    35,   145,   154,
     204,   216,   190,   405,   406,   731,   214,   216,   315,   232,
     702,   673,   214,    73,    75,    77,    79,    83,    23,    73,
      75,    77,    79,    83,   689,   126,   335,   335,   339,   340,
     422,   343,   703,   342,   335,   860,   221,   556,   371,   557,
     -17,   841,   124,    97,   208,   556,   105,   557,   127,   366,
     197,   366,   825,   367,   154,   113,    97,   198,   177,   128,
     623,   194,   401,   563,   227,   801,   802,    31,    32,    33,
     604,   607,   611,   613,   867,   564,   197,   516,    23,   516,
     125,   195,   227,   198,   196,   225,   515,   112,   372,   366,
     233,   112,   167,   170,   197,   190,   346,   432,   350,   373,
     201,   198,   431,   602,   605,   424,   755,   429,   226,   428,
     587,   430,   624,   205,    73,   433,    77,    79,    83,   128,
     197,   207,   146,   155,   529,   764,   210,   198,   530,   149,
     158,   358,  -533,   661,   662,   431,   111,   111,   111,   111,
     429,   599,   428,   220,   430,   223,   314,    73,   433,    77,
      79,    83,   190,   222,   113,   367,   625,   367,   113,    97,
      23,   106,   517,   558,   407,   408,   518,    23,   107,    99,
     560,   568,   569,   570,   126,   565,   244,   106,   155,   245,
     726,   166,   197,   183,   107,   158,   516,    23,   658,   198,
      25,   317,   755,   117,   112,   367,   202,   333,   112,   755,
     681,    26,   360,   315,   352,   413,   414,    99,   334,   -18,
     362,   363,   112,   145,   154,   674,   594,   337,   722,  -537,
     691,   593,   421,   422,   311,   314,    34,    35,   338,   541,
     138,   141,   151,   160,    23,   -19,    31,    32,    33,   769,
     691,   603,   606,   610,   612,   318,   755,   400,  -225,   314,
    -225,   761,   762,   688,  -226,   690,  -226,   227,   519,   227,
     248,   527,   520,   326,   337,   527,  -225,   542,   543,   544,
     545,   329,  -226,   314,   347,   338,   232,   686,   105,   113,
      23,   105,   209,   348,   105,   353,   105,   126,   -20,   806,
     148,   157,   189,   206,   805,   117,   807,   162,   165,   190,
     223,   536,   549,   422,   223,   540,   364,   190,   626,    23,
     333,   190,   627,   -21,   216,   368,   225,   672,   -22,   214,
     695,   334,   733,   315,   741,   -23,    73,    75,    77,    79,
      83,   227,   838,   369,   822,   797,   852,   146,   155,   526,
     571,   572,   208,   208,   149,   158,   370,   681,   379,   135,
     334,   380,   681,   767,    31,    32,    33,   837,   314,   314,
     314,   314,   314,   314,   314,   314,   314,   314,   314,   314,
     314,   314,   314,   314,   314,   314,   415,   154,   765,   594,
     352,   594,   315,   853,   593,   416,   593,   112,   417,   112,
     227,   705,   418,   223,   206,   423,   797,   119,   209,   710,
     190,   425,   866,   440,   431,   522,   335,   699,   512,   429,
     539,   428,   371,   430,   790,   785,    73,   433,    77,    79,
      83,   791,   548,   191,   794,   795,   732,   846,   550,   695,
     402,   682,   683,   216,   590,   403,   404,   315,   214,   191,
     148,   157,   111,   111,   614,    73,    75,    77,    79,    83,
     216,   615,   224,   618,   527,   214,   527,   619,   135,   846,
     846,   112,    73,    75,    77,    79,    83,   876,   314,   112,
     620,   823,   784,   683,   111,   881,   621,   315,   111,   594,
     829,   886,   821,   422,   593,   827,   422,   111,   111,   111,
     111,   664,   111,   111,   828,   422,   157,   165,   858,   422,
     315,   155,   223,   663,   314,   839,   877,   422,   158,   190,
     669,   843,   844,   699,   670,   335,   842,   111,   208,   675,
     112,   885,   422,   208,   856,   676,   315,   677,   527,   573,
     574,   327,   678,   328,   579,   580,   527,   575,   576,   577,
     578,   679,   314,   869,   680,   315,   685,   704,   216,   427,
     716,   871,   717,   214,   718,   349,   111,   111,   723,   879,
      73,    75,    77,    79,    83,   725,   724,   381,   382,   383,
     883,   728,   729,   742,   743,   105,   105,   730,   105,   105,
     235,   236,   237,   238,   239,   734,   735,   527,   314,   384,
     815,  -203,  -204,   240,   241,   422,   385,   736,   386,   314,
     737,   740,   242,   243,   671,   770,   759,   766,   768,   773,
     691,   793,   799,   800,   788,   792,   812,   803,   777,   778,
     779,   780,   816,   781,   782,   817,   818,   144,   153,   826,
     381,   382,   383,   824,   144,   163,   144,   168,   144,   172,
     144,   176,   831,   314,   144,   182,   838,   314,   796,     5,
       6,   840,   384,   862,     8,   148,   157,   850,   851,   385,
      12,   386,   191,   857,    15,    16,    17,    18,   859,   863,
      19,    20,   349,   537,   538,    21,   349,   153,   864,   865,
     868,   870,   874,   875,   878,   884,   118,   819,   820,   247,
     248,   249,   250,   251,   252,   253,   147,   156,   555,   776,
     775,   774,   314,   147,   164,   147,   169,   147,   173,   147,
     178,   514,   714,   147,   184,   715,   808,   804,   811,   361,
       4,   760,   551,   525,   721,   581,   583,   582,   783,   104,
      10,    11,   584,    13,    23,   585,    15,    28,    29,    30,
     832,   126,   314,   872,   535,    34,    35,    21,   873,   321,
      22,    37,    38,    39,   121,    41,    42,    43,   268,    44,
      45,    46,    47,   617,   333,   314,     0,    51,    52,     0,
       0,     0,     0,     0,     0,   334,     0,   144,   153,     0,
       0,     0,   531,   532,     0,     0,     0,   691,   692,     0,
       0,   314,     0,   153,     0,   387,   388,   389,   390,   391,
     392,   393,   394,   395,   396,     0,  -541,     0,     0,     0,
     314,    30,     0,     0,     0,     0,     0,    34,    35,   157,
       0,     0,     0,     0,    38,    39,     0,    41,    42,    43,
     397,    44,    45,   153,   163,   168,   172,   176,   182,    51,
      52,     0,     0,     0,     0,     0,   147,   156,     0,     0,
       0,     0,     0,   191,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   349,     4,     5,
       6,     7,   630,     0,     8,   631,   632,     9,    10,    11,
      12,    13,   633,    14,    15,    16,    17,    18,   634,   635,
      19,    20,   636,   637,   246,    21,   638,   639,    22,   640,
     641,   248,   249,   250,   251,   252,   253,   642,    15,   254,
     255,     0,     0,     0,     0,     0,     0,   256,     0,    21,
       0,     0,     0,     0,     0,     0,   555,     0,     0,     0,
     257,     0,     0,     0,   555,   550,  -538,     0,     0,     0,
     258,   259,   260,   261,   262,   263,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,    28,    29,    30,
     264,   265,    31,    32,    33,    34,    35,   266,   267,   268,
      36,    37,    38,    39,   643,    41,    42,    43,     0,    44,
      45,    46,    47,    30,    48,    49,    50,    51,    52,    34,
      35,     0,   144,   153,   144,   168,    38,    39,     0,    41,
      42,    43,     0,    44,    45,   763,     0,     0,    48,    49,
      50,    51,    52,     0,     0,     0,     0,     0,     0,     0,
       4,     5,     6,     7,     0,     0,     8,     0,     0,     9,
      10,    11,    12,    13,     0,    14,    15,    16,    17,    18,
       0,     0,    19,    20,     0,   706,   707,    21,   708,   709,
      22,     0,    23,   711,     0,   712,     4,     0,     0,    24,
       0,   147,   156,   147,   169,     0,    10,    11,     0,    13,
       0,     0,    15,     0,     0,     0,     0,     0,     0,     0,
       0,     0,    25,    21,     0,     0,    22,     0,     0,     0,
       0,   153,   153,    26,     0,     0,     0,     0,     0,     0,
       0,     0,    15,     0,     0,     0,    27,     0,     0,    28,
      29,    30,     0,    21,    31,    32,    33,    34,    35,     0,
     153,     0,    36,    37,    38,    39,    40,    41,    42,    43,
       0,    44,    45,    46,    47,     0,    48,    49,    50,    51,
      52,     0,     0,     0,     0,     0,     0,    30,   372,     0,
       0,     0,     0,    34,    35,   190,   153,     0,     0,   373,
      38,    39,     0,    41,    42,    43,     0,    44,    45,     0,
       0,     0,    48,    49,    50,    51,    52,    30,     0,     0,
       0,     0,     0,    34,    35,     0,     0,     0,     0,     0,
      38,    39,     0,    41,    42,    43,     0,    44,    45,     0,
       0,     0,     0,     0,     0,    51,    52,     0,     0,     0,
       0,     0,     0,     0,     4,     5,     6,     7,   630,   739,
       8,   631,   632,     9,    10,    11,    12,    13,   633,    14,
      15,    16,    17,    18,   634,   635,    19,    20,   636,   637,
     246,    21,   638,   639,    22,   640,   641,   248,   249,   250,
     251,   252,   253,   642,     0,   254,   255,     0,     0,     0,
       0,     0,     0,   256,     0,     0,     0,   153,     0,     0,
       0,     0,   153,     0,     0,     0,   257,     0,     0,     0,
       0,   550,   814,     0,     0,     0,   258,   259,   260,   261,
     262,   263,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,    28,    29,    30,   264,   265,    31,    32,
      33,    34,    35,   266,   267,   268,    36,    37,    38,    39,
     643,    41,    42,    43,     0,    44,    45,    46,    47,     0,
      48,    49,    50,    51,    52,   442,   443,   444,   445,   446,
     447,   448,   449,   450,   451,   452,   453,   454,   455,   456,
     457,   458,   459,   460,   461,   462,   463,   464,   465,   466,
     467,   468,   469,   470,   471,   472,   473,   474,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   475,   476,   477,   478,   479,   480,   481,
     482,   483,   484,   485,   486,   487,   488,   489,   490,   491,
     492,   493,   494,   495,   496,   497,   498,   499,   500,   501,
     502,   503,   504,   505,   506,   507,     4,     5,     6,     7,
       0,     0,     8,     0,     0,     9,    10,    11,    12,    13,
       0,    14,    15,    16,    17,    18,     0,     0,    19,    20,
       0,     0,   246,    21,     0,     0,    22,     0,   247,   248,
     249,   250,   251,   252,   253,    24,     0,   254,   255,     0,
       0,     0,     0,     0,     0,   256,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   257,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   258,   259,
     260,   261,   262,   263,     0,     0,     0,     0,     0,    15,
       0,     0,     0,     0,     0,    28,    29,    30,   264,   265,
      21,     0,     0,    34,    35,   266,   267,   268,    36,    37,
      38,    39,   269,    41,    42,    43,     0,    44,    45,    46,
      47,     0,    48,    49,    50,    51,    52,     4,     5,     6,
       7,     0,     0,     8,     0,     0,     9,    10,    11,    12,
      13,     0,    14,    15,    16,    17,    18,     0,     0,    19,
      20,     0,     0,     0,    21,     0,     0,    22,     0,    23,
       0,     0,     0,     0,    30,     0,   142,     0,     0,     0,
      34,    35,     0,     0,     0,    36,     0,    38,    39,     0,
      41,    42,    43,     0,    44,    45,     0,     0,     0,   608,
       0,     0,    51,    52,     0,     0,   190,     0,     0,     0,
     609,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,    28,    29,    30,     0,
       0,     0,     0,     0,    34,    35,     0,     0,     0,    36,
      37,    38,    39,     0,    41,    42,    43,     0,    44,    45,
      46,    47,     0,    48,    49,    50,    51,    52,     4,     5,
       6,     7,     0,     0,     8,     0,     0,     9,    10,    11,
      12,    13,     0,    14,    15,    16,    17,    18,     0,     0,
      19,    20,     0,     0,     0,    21,     0,     0,    22,     0,
      23,     0,     0,     0,     0,     0,     0,   152,     0,     0,
       0,     0,     0,     0,     0,     0,     4,     5,     6,     7,
       0,     0,     8,     0,     0,     9,    10,    11,    12,    13,
     608,    14,    15,    16,    17,    18,     0,   190,    19,    20,
       0,   609,     0,    21,     0,     0,    22,     0,    23,     0,
       0,     0,     0,     0,     0,    24,     0,    28,    29,    30,
       0,     0,     0,     0,     0,    34,    35,     0,     0,     0,
      36,    37,    38,    39,     0,    41,    42,    43,    25,    44,
      45,    46,    47,     0,    48,    49,    50,    51,    52,    26,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,    28,    29,    30,     0,     0,
       0,     0,     0,    34,    35,     0,     0,     0,    36,    37,
      38,    39,     0,    41,    42,    43,     0,    44,    45,    46,
      47,     0,    48,    49,    50,    51,    52,     4,     5,     6,
       7,     0,     0,     8,     0,     0,     9,    10,    11,    12,
      13,     0,    14,    15,    16,    17,    18,     0,     0,    19,
      20,     0,     0,     0,    21,     0,     0,    22,     0,    23,
       0,     0,     0,     0,     0,     0,   142,     0,     0,     0,
       0,     0,     0,     0,     0,     4,     5,     6,     7,     0,
       0,     8,     0,     0,     9,    10,    11,    12,    13,    25,
      14,    15,    16,    17,    18,     0,     0,    19,    20,     0,
      26,     0,    21,     0,     0,    22,     0,    23,     0,     0,
       0,     0,     0,     0,   152,     0,    28,    29,    30,     0,
       0,     0,     0,     0,    34,    35,     0,     0,     0,    36,
      37,    38,    39,     0,    41,    42,    43,    25,    44,    45,
      46,    47,     0,    48,    49,    50,    51,    52,    26,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,    28,    29,    30,     0,     0,     0,
       0,     0,    34,    35,     0,     0,     0,    36,    37,    38,
      39,     0,    41,    42,    43,     0,    44,    45,    46,    47,
       0,    48,    49,    50,    51,    52,     4,     5,     6,     7,
       0,     0,     8,     0,     0,     9,    10,    11,    12,    13,
       0,    14,    15,    16,    17,    18,     0,     0,    19,    20,
       0,     0,     0,    21,     0,     0,    22,     0,    23,     0,
       0,     0,     0,     0,     0,   142,     0,     0,     0,     0,
       0,     0,     0,     0,     4,     5,     6,     7,     0,     0,
       8,     0,     0,     9,    10,    11,    12,    13,   337,    14,
      15,    16,    17,    18,     0,     0,    19,    20,     0,   338,
       0,    21,     0,     0,    22,     0,    23,     0,     0,     0,
       0,     0,     0,   152,     0,    28,    29,    30,     0,     0,
       0,     0,     0,    34,    35,     0,     0,     0,    36,    37,
      38,    39,     0,    41,    42,    43,   337,    44,    45,    46,
      47,     0,    48,    49,    50,    51,    52,   338,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,    28,    29,    30,     0,     0,     0,     0,
       0,    34,    35,     0,     0,     0,    36,    37,    38,    39,
       0,    41,    42,    43,     0,    44,    45,    46,    47,     0,
      48,    49,    50,    51,    52,     4,     5,     6,     7,     0,
       0,     8,     0,     0,     9,    10,    11,    12,    13,     0,
      14,    15,    16,    17,    18,     0,     0,    19,    20,     0,
       0,     0,    21,     0,     0,    22,     0,     4,     5,     6,
       7,     0,     0,     8,    24,     0,     9,    10,    11,    12,
      13,     0,    14,    15,    16,    17,    18,     0,     0,    19,
      20,     0,     0,     0,    21,     0,     0,    22,     0,     0,
       0,     0,   341,     0,     0,     0,    24,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,    28,    29,    30,     0,     0,     0,
       0,     0,    34,    35,     0,     0,     0,    36,    37,    38,
      39,   211,    41,    42,    43,     0,    44,    45,    46,    47,
       0,    48,    49,    50,    51,    52,    28,    29,    30,     0,
       0,     0,     0,     0,    34,    35,     0,     0,     0,    36,
      37,    38,    39,   211,    41,    42,    43,     0,    44,    45,
      46,    47,     0,    48,    49,    50,    51,    52,     4,     5,
       6,     7,     0,     0,     8,     0,     0,     9,    10,    11,
      12,    13,     0,    14,    15,    16,    17,    18,     0,     0,
      19,    20,     0,     0,     0,    21,     0,     0,    22,     0,
     427,     0,     4,     5,     6,     7,     0,    24,     8,     0,
       0,     9,    10,    11,    12,    13,     0,    14,    15,    16,
      17,    18,     0,     0,    19,    20,     0,     0,     0,    21,
       0,     0,    22,     0,     0,     0,     0,     0,     0,     0,
       0,    24,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   713,     0,     0,     0,    28,    29,    30,
       0,     0,     0,     0,     0,    34,    35,     0,     0,     0,
      36,    37,    38,    39,     0,    41,    42,    43,     0,    44,
      45,    46,    47,     0,    48,    49,    50,    51,    52,     0,
       0,    28,    29,    30,     0,     0,     0,     0,     0,    34,
      35,     0,     0,     0,    36,    37,    38,    39,     0,    41,
      42,    43,     0,    44,    45,    46,    47,     0,    48,    49,
      50,    51,    52,     4,     5,     6,     7,     0,     0,     8,
       0,     0,     9,    10,    11,    12,    13,     0,    14,    15,
      16,    17,    18,     0,     0,    19,    20,     0,     0,     0,
      21,     0,     0,    22,     0,     4,     5,     6,     0,     0,
       0,     8,    24,     0,     0,    10,    11,    12,    13,     0,
       0,    15,    16,    17,    18,     0,     0,    19,    20,     4,
       0,     0,    21,     0,     0,    22,     0,     0,     0,    10,
      11,     0,    13,     0,     0,    15,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,    21,     0,     0,    22,
       0,     0,    28,    29,    30,     0,     0,     0,     0,     0,
      34,    35,     0,     0,     0,    36,    37,    38,    39,     0,
      41,    42,    43,     0,    44,    45,    46,    47,     0,    48,
      49,    50,    51,    52,    28,    29,    30,     0,     0,     0,
       0,     0,    34,    35,     0,     0,     0,     0,    37,    38,
      39,     0,    41,    42,    43,     0,    44,    45,    46,    47,
      30,     0,     0,     0,    51,    52,    34,    35,     0,     0,
       0,    36,     0,    38,    39,     0,    41,    42,    43,     0,
      44,    45,     4,     0,     0,     0,     0,     0,    51,    52,
       0,     0,    10,    11,     0,    13,     0,     0,    15,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,    21,
       0,     0,    22,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     5,     6,     7,     0,     0,     8,
       0,     0,     9,     0,     0,    12,     0,     0,    14,    15,
      16,    17,    18,     0,     0,    19,    20,     0,     0,   246,
      21,     0,     0,     0,     0,   247,   248,   249,   250,   251,
     252,   253,    24,     0,   254,   255,     0,     0,     0,     0,
       0,     0,   256,    30,     0,     0,     0,     0,     0,    34,
      35,     0,     0,     0,     0,   257,    38,    39,     0,    41,
      42,    43,    15,    44,    45,   258,   259,   260,   261,   262,
     263,    51,    52,    21,     0,     0,     0,     0,     0,     0,
       0,     0,    28,    29,    30,   264,   265,     0,     0,     0,
      34,    35,   266,   267,   268,    36,    37,    38,    39,   269,
      41,    42,    43,     0,    44,    45,    46,    47,   738,    48,
      49,    50,    51,    52,     5,     6,     7,     0,     0,     8,
       0,     0,     9,     0,     0,    12,     0,     0,    14,    15,
      16,    17,    18,     0,     0,    19,    20,    30,     0,     0,
      21,     0,     0,    34,    35,    23,     0,     0,     0,     0,
      38,    39,   152,    41,    42,    43,     0,    44,    45,     0,
       0,     0,     0,     0,     0,    51,    52,     0,     0,     0,
       0,     0,     0,     0,     0,   337,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   338,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   691,   696,
       0,     0,    28,    29,    30,     0,     0,     0,     0,     0,
      34,    35,     0,     0,     0,    36,    37,    38,    39,     0,
      41,    42,    43,     0,    44,    45,    46,    47,     0,    48,
      49,    50,    51,    52,     5,     6,     7,     0,     0,     8,
       0,     0,     9,     0,     0,    12,     0,     0,    14,    15,
      16,    17,    18,     0,     0,    19,    20,     0,     0,     0,
      21,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   152,     0,     0,     5,     6,     7,     0,     0,
       8,     0,     0,     9,     0,     0,    12,     0,     0,    14,
      15,    16,    17,    18,     0,   372,    19,    20,     0,     0,
       0,    21,   190,     0,     0,     0,   373,     0,     0,     0,
       0,     0,     0,    24,     0,     0,     0,     0,     0,     0,
       0,     0,    28,    29,    30,     0,     0,     0,     0,     0,
      34,    35,     0,     0,     0,    36,    37,    38,    39,     0,
      41,    42,    43,     0,    44,    45,    46,    47,     0,    48,
      49,    50,    51,    52,     0,     0,     0,     0,     0,     0,
     592,     0,     0,    28,    29,    30,     0,     0,     0,     0,
       0,    34,    35,    15,     0,     0,    36,    37,    38,    39,
       0,    41,    42,    43,    21,    44,    45,    46,    47,    23,
      48,    49,    50,    51,    52,     5,     6,     7,     0,     0,
       8,     0,     0,     9,     0,     0,    12,     0,     0,    14,
      15,    16,    17,    18,     0,     0,    19,    20,     0,   608,
       0,    21,     0,     0,     0,     0,   190,     0,     0,     0,
     609,     0,     0,    24,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,    15,    30,     0,
       0,     0,     0,     0,    34,    35,     0,     0,    21,     0,
       0,    38,    39,    23,    41,    42,    43,     0,    44,    45,
     126,     0,     0,     0,     0,     0,    51,    52,     0,    15,
       0,     0,     0,    28,    29,    30,     0,     0,     0,     0,
      21,    34,    35,   600,     0,     0,    36,    37,    38,    39,
     190,    41,    42,    43,   601,    44,    45,    46,    47,    15,
      48,    49,    50,    51,    52,     0,     0,     0,     0,     0,
      21,     0,    30,     0,     0,    23,     0,     0,    34,    35,
       0,     0,   126,     0,     0,    38,    39,     0,    41,    42,
      43,     0,    44,    45,     0,     0,    15,     0,     0,     0,
      51,    52,     0,     0,    30,   230,     0,    21,     0,     0,
      34,    35,    23,     0,     0,    15,   128,    38,    39,   126,
      41,    42,    43,     0,    44,    45,    21,     0,     0,     0,
       0,    23,    51,    52,    30,     0,     0,     0,   126,     0,
      34,    35,   351,     0,     0,     0,     0,    38,    39,     0,
      41,    42,    43,   128,    44,    45,     0,     0,     0,    15,
       0,   528,    51,    52,     0,     0,     0,     0,     0,     0,
      21,    30,   334,     0,     0,    23,     0,    34,    35,     0,
       0,     0,   126,     0,    38,    39,     0,    41,    42,    43,
      30,    44,    45,     0,     0,     0,    34,    35,     0,    51,
      52,     0,     0,    38,    39,   659,    41,    42,    43,     0,
      44,    45,    15,     0,     0,     0,   334,     0,    51,    52,
       0,     0,     0,    21,     0,     0,     0,     0,    23,     0,
       0,     0,     0,     0,    30,     0,     0,     0,     0,     0,
      34,    35,     0,     0,     0,    15,     0,    38,    39,     0,
      41,    42,    43,     0,    44,    45,    21,     0,    25,     0,
       0,    23,    51,    52,     0,     0,     0,     0,     0,    26,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,    30,     0,     0,
       0,   337,     0,    34,    35,     0,     0,     0,     0,     0,
      38,    39,   338,    41,    42,    43,     0,    44,    45,     0,
       0,     0,     0,     0,     0,    51,    52,     0,     0,     0,
      30,     0,     0,     0,     0,     0,    34,    35,     0,     0,
       0,     0,     0,    38,    39,     0,    41,    42,    43,     0,
      44,    45,   630,     0,     0,   631,   632,     0,    51,    52,
       0,     0,   633,     0,     0,     0,     0,     0,   634,   635,
       0,     0,   636,   637,   246,     0,   638,   639,     0,   640,
     641,   248,   249,   250,   251,   252,   253,   107,     0,   254,
     255,     0,     0,     0,     0,     0,     0,   256,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     257,     0,     0,     0,     0,   550,     0,     0,     0,     0,
     258,   259,   260,   261,   262,   263,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     264,   265,    31,    32,    33,     0,     0,   266,   267,   268,
     246,     0,     0,     0,   269,     0,   744,   248,   249,   250,
     251,   252,   253,     0,     0,   254,   255,     0,     0,     0,
       0,     0,     0,   256,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   257,     0,     0,     0,
       0,   745,   833,   746,     0,   747,   258,   259,   260,   261,
     262,   263,     0,   246,     0,     0,     0,     0,     0,   744,
     248,   249,   250,   251,   252,   253,   264,   265,   254,   255,
       0,     0,     0,   266,   267,   268,   256,     0,     0,     0,
     269,     0,     0,     0,     0,     0,     0,     0,     0,   257,
       0,     0,     0,     0,   745,     0,   746,     0,   747,   258,
     259,   260,   261,   262,   263,     0,   246,     0,     0,     0,
       0,     0,   247,   248,   249,   250,   251,   252,   253,   264,
     265,   254,   255,     0,     0,     0,   266,   267,   268,   256,
       0,     0,     0,   269,     0,     0,     0,     0,     0,     0,
       0,     0,   257,     0,     0,     0,     0,     0,     0,     0,
     313,     0,   258,   259,   260,   261,   262,   263,     0,   246,
       0,     0,     0,     0,     0,   247,   248,   249,   250,   251,
     252,   253,   264,   265,   254,   255,     0,     0,     0,   266,
     267,   268,   256,     0,     0,     0,   269,     0,     0,     0,
       0,     0,     0,     0,     0,   257,   561,     0,     0,     0,
       0,     0,     0,     0,     0,   258,   259,   260,   261,   262,
     263,     0,   246,     0,     0,     0,     0,     0,   247,   248,
     249,   250,   251,   252,   253,   264,   265,   254,   255,     0,
       0,     0,   266,   267,   268,   256,     0,     0,     0,   269,
       0,     0,     0,     0,     0,     0,     0,     0,   257,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   258,   259,
     260,   261,   262,   263,     0,     0,     0,     0,     0,     0,
       0,   586,     0,     0,     0,     0,     0,     0,   264,   265,
       0,     0,     0,     0,     0,   266,   267,   268,   246,     0,
       0,     0,   269,     0,   247,   248,   249,   250,   251,   252,
     253,     0,     0,   254,   255,     0,     0,     0,     0,     0,
       0,   256,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,   257,     0,     0,     0,     0,   671,
       0,     0,     0,     0,   258,   259,   260,   261,   262,   263,
       0,   246,     0,     0,     0,     0,     0,   247,   248,   249,
     250,   251,   252,   253,   264,   265,   254,   255,     0,     0,
       0,   266,   267,   268,   256,     0,     0,     0,   269,     0,
       0,     0,     0,     0,     0,     0,     0,   257,   720,     0,
       0,     0,     0,     0,     0,     0,     0,   258,   259,   260,
     261,   262,   263,     0,   246,     0,     0,     0,     0,     0,
     247,   248,   249,   250,   251,   252,   253,   264,   265,   254,
     255,     0,     0,     0,   266,   267,   268,   256,     0,     0,
       0,   269,     0,     0,     0,     0,     0,     0,     0,     0,
     257,     0,     0,     0,     0,   745,     0,     0,     0,     0,
     258,   259,   260,   261,   262,   263,     0,   246,     0,     0,
       0,     0,     0,   247,   248,   249,   250,   251,   252,   253,
     264,   265,   254,   255,     0,     0,     0,   266,   267,   268,
     256,     0,     0,     0,   269,     0,     0,     0,     0,     0,
       0,     0,     0,   359,     0,     0,     0,     0,     0,     0,
       0,     0,     0,   258,   259,   260,   261,   262,   263,     0,
     246,     0,     0,     0,     0,     0,   247,   248,   249,   250,
     251,   252,   253,   264,   265,   254,   255,     0,     0,     0,
     266,   267,   268,   256,     0,     0,     0,   269,     0,     0,
       0,     0,     0,     0,     0,     0,   361,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   258,   259,   260,   261,
     262,   263,     0,   246,     0,     0,     0,     0,     0,   247,
     248,   249,   250,   251,   252,   253,   264,   265,   254,   255,
       0,     0,     0,   266,   267,   268,   256,     0,     0,     0,
     269,     0,     0,     0,     0,     0,     0,     0,     0,   257,
       0,     0,     0,     0,     0,     0,     0,     0,     0,   258,
     259,   260,   261,   262,   263,     0,   246,     0,     0,     0,
       0,     0,   247,   248,   249,   250,   251,   252,   253,   264,
     265,   254,   255,     0,     0,     0,   266,   267,   268,   256,
       0,     0,     0,   269,     0,     0,     0,     0,     0,     0,
       0,     0,   399,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   258,   259,   260,   261,   262,   263,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   264,   265,     0,     0,     0,     0,     0,   266,
     267,   268,     0,     0,     0,     0,   269
};

static const yytype_int16 yycheck[] =
{
       9,    64,     2,   196,    40,   312,   226,     2,     9,     2,
     230,     2,   341,   128,     2,     2,     2,     2,     2,   372,
     620,   372,   370,    99,   594,     2,    26,    67,     2,    63,
      64,    72,   533,    74,   757,    76,   384,    78,   345,    67,
      40,    82,   257,   234,    67,    40,   237,    40,    88,    40,
     398,   242,    40,    40,    40,    40,    40,     9,    65,    66,
     633,   201,   202,    40,    53,    36,    89,   423,   127,   425,
      35,    78,    79,    35,   422,   317,   543,   544,   545,   179,
      42,   804,    91,    36,    48,    49,    63,    64,    65,    66,
      91,   190,   322,    54,   128,    35,    25,    91,    87,    54,
      65,   110,    42,    65,    67,    54,    36,    72,    71,   110,
      72,    76,    65,   122,    76,   124,   231,    88,    73,    83,
      84,   121,   270,   271,    73,   695,   121,    88,   128,   699,
     700,   351,    35,   121,   121,   121,   121,   121,   115,    91,
       2,    35,    72,    67,   359,    65,   361,   121,    42,    67,
      72,   214,    74,   368,   369,    73,   729,   257,   221,    36,
     100,   101,    65,    25,    26,    89,    36,   226,    90,    72,
       0,   230,   673,    76,    35,   211,    70,   186,    40,   213,
     214,    42,    65,    66,   399,   186,   326,   221,    65,    65,
      66,   185,   186,   100,   101,   189,    66,   231,   127,    82,
      83,    63,    64,    65,    66,    65,   100,   101,   215,   216,
     219,   211,    72,    77,    78,    76,   211,   217,   317,   334,
      67,   550,   217,   211,   211,   211,   211,   211,    35,   217,
     217,   217,   217,   217,   590,    42,   213,   214,   215,   216,
      67,   218,    89,   217,   221,   845,   124,   600,   269,   600,
      70,   824,    67,   115,   231,   608,     2,   608,    65,   359,
      35,   361,    89,   257,   271,   127,   128,    42,   275,    76,
     512,    65,   293,   621,   333,   742,   743,    97,    98,    99,
     428,   429,   430,   431,   857,   385,    35,   517,    35,   519,
      62,    89,   351,    42,    65,    42,    71,   226,    65,   399,
     334,   230,    74,    75,    35,    72,   526,   614,   528,    76,
      70,    42,   312,   428,   429,   309,   664,   312,    65,   312,
     420,   312,    71,    66,   312,   312,   312,   312,   312,    76,
      35,    66,   215,   216,   334,   683,    70,    42,   338,   215,
     216,   532,    70,   534,   535,   345,   355,   356,   357,   358,
     345,   427,   345,    89,   345,    65,   190,   345,   345,   345,
     345,   345,    72,    89,   226,   359,    71,   361,   230,   231,
      35,    35,    67,   373,    46,    47,    71,    35,    42,     2,
     381,   402,   403,   404,    42,   386,    89,    35,   271,    89,
     632,   432,    35,   276,    42,   271,   626,    35,   524,    42,
      65,    72,   750,    26,   333,   399,    70,    65,   337,   757,
     558,    76,   246,   512,   529,    50,    51,    40,    76,    70,
     254,   255,   351,   430,   431,   551,   426,    65,    71,    70,
      88,   426,    66,    67,    70,   269,   100,   101,    76,   659,
      63,    64,    65,    66,    35,    70,    97,    98,    99,   691,
      88,   428,   429,   430,   431,    65,   804,   291,    72,   293,
      74,   676,   677,   589,    72,   591,    74,   526,    67,   528,
      36,   333,    71,    70,    65,   337,    90,   355,   356,   357,
     358,   113,    90,   317,    66,    76,   601,   586,   234,   351,
      35,   237,   115,    66,   240,   529,   242,    42,    70,    35,
      65,    66,    65,    66,   746,   128,    42,    72,    73,    72,
      65,    66,    66,    67,    65,    66,    35,    72,    67,    35,
      65,    72,    71,    70,   524,    65,    42,   548,    70,   524,
     593,    76,   638,   632,   660,    70,   524,   524,   524,   524,
     524,   600,    67,    65,   786,   738,    71,   430,   431,    65,
     405,   406,   529,   530,   430,   431,    65,   705,    66,   593,
      76,    36,   710,   689,    97,    98,    99,   809,   402,   403,
     404,   405,   406,   407,   408,   409,   410,   411,   412,   413,
     414,   415,   416,   417,   418,   419,    75,   594,   687,   589,
     705,   591,   691,   835,   589,    85,   591,   526,    86,   528,
     659,   601,    52,    65,    66,    70,   799,   643,   231,   609,
      72,    70,   854,    73,   614,    35,   593,   594,    90,   614,
      66,   614,   643,   614,   730,   725,   614,   614,   614,   614,
     614,   731,    66,    97,   734,   735,   637,   830,    70,   702,
      76,    66,    67,   643,    70,    81,    82,   746,   643,   113,
     215,   216,   661,   662,    67,   643,   643,   643,   643,   643,
     660,    67,   126,    73,   526,   660,   528,    66,   702,   862,
     863,   600,   660,   660,   660,   660,   660,   870,   512,   608,
      67,   787,    66,    67,   693,   878,    65,   786,   697,   689,
     796,   884,    66,    67,   689,    66,    67,   706,   707,   708,
     709,    90,   711,   712,    66,    67,   271,   272,    66,    67,
     809,   594,    65,    66,   548,   821,    66,    67,   594,    72,
      66,   827,   828,   700,    66,   702,   826,   736,   705,    67,
     659,    66,    67,   710,   840,    67,   835,    67,   600,   407,
     408,   205,    66,   207,   413,   414,   608,   409,   410,   411,
     412,    66,   586,   859,    66,   854,    66,    71,   758,    35,
      66,   861,    66,   758,    66,   229,   775,   776,    35,   875,
     758,   758,   758,   758,   758,    65,    89,    43,    44,    45,
     880,    89,    65,   661,   662,   531,   532,    88,   534,   535,
     137,   138,   139,   140,   141,    65,    65,   659,   632,    65,
      66,    88,    88,   150,   151,    67,    72,    88,    74,   643,
      89,    71,   159,   160,    70,   693,    66,    71,    71,   697,
      88,    34,    65,    71,    89,    89,    71,    88,   706,   707,
     708,   709,    66,   711,   712,    66,    71,    65,    66,    65,
      43,    44,    45,    89,    72,    73,    74,    75,    76,    77,
      78,    79,    66,   687,    82,    83,    67,   691,   736,     4,
       5,    88,    65,    88,     9,   430,   431,    89,    66,    72,
      15,    74,   336,    89,    19,    20,    21,    22,     8,    67,
      25,    26,   346,   347,   348,    30,   350,   115,    89,    73,
      89,    73,    73,    66,    88,    67,    40,   775,   776,    35,
      36,    37,    38,    39,    40,    41,    65,    66,   372,   702,
     700,   699,   746,    72,    73,    74,    75,    76,    77,    78,
      79,   321,   614,    82,    83,   615,   750,   745,   751,    65,
       3,   675,   367,   331,   621,   415,   417,   416,   719,     2,
      13,    14,   418,    16,    35,   419,    19,    92,    93,    94,
     799,    42,   786,   862,   344,   100,   101,    30,   863,   198,
      33,   106,   107,   108,    58,   110,   111,   112,   104,   114,
     115,   116,   117,   439,    65,   809,    -1,   122,   123,    -1,
      -1,    -1,    -1,    -1,    -1,    76,    -1,   215,   216,    -1,
      -1,    -1,   339,   340,    -1,    -1,    -1,    88,    89,    -1,
      -1,   835,    -1,   231,    -1,    55,    56,    57,    58,    59,
      60,    61,    62,    63,    64,    -1,    89,    -1,    -1,    -1,
     854,    94,    -1,    -1,    -1,    -1,    -1,   100,   101,   594,
      -1,    -1,    -1,    -1,   107,   108,    -1,   110,   111,   112,
      90,   114,   115,   271,   272,   273,   274,   275,   276,   122,
     123,    -1,    -1,    -1,    -1,    -1,   215,   216,    -1,    -1,
      -1,    -1,    -1,   527,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,   541,     3,     4,
       5,     6,     7,    -1,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    38,    39,    40,    41,    42,    19,    44,
      45,    -1,    -1,    -1,    -1,    -1,    -1,    52,    -1,    30,
      -1,    -1,    -1,    -1,    -1,    -1,   600,    -1,    -1,    -1,
      65,    -1,    -1,    -1,   608,    70,    71,    -1,    -1,    -1,
      75,    76,    77,    78,    79,    80,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    92,    93,    94,
      95,    96,    97,    98,    99,   100,   101,   102,   103,   104,
     105,   106,   107,   108,   109,   110,   111,   112,    -1,   114,
     115,   116,   117,    94,   119,   120,   121,   122,   123,   100,
     101,    -1,   430,   431,   432,   433,   107,   108,    -1,   110,
     111,   112,    -1,   114,   115,   679,    -1,    -1,   119,   120,
     121,   122,   123,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
       3,     4,     5,     6,    -1,    -1,     9,    -1,    -1,    12,
      13,    14,    15,    16,    -1,    18,    19,    20,    21,    22,
      -1,    -1,    25,    26,    -1,   602,   603,    30,   605,   606,
      33,    -1,    35,   610,    -1,   612,     3,    -1,    -1,    42,
      -1,   430,   431,   432,   433,    -1,    13,    14,    -1,    16,
      -1,    -1,    19,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    65,    30,    -1,    -1,    33,    -1,    -1,    -1,
      -1,   529,   530,    76,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    19,    -1,    -1,    -1,    89,    -1,    -1,    92,
      93,    94,    -1,    30,    97,    98,    99,   100,   101,    -1,
     558,    -1,   105,   106,   107,   108,   109,   110,   111,   112,
      -1,   114,   115,   116,   117,    -1,   119,   120,   121,   122,
     123,    -1,    -1,    -1,    -1,    -1,    -1,    94,    65,    -1,
      -1,    -1,    -1,   100,   101,    72,   594,    -1,    -1,    76,
     107,   108,    -1,   110,   111,   112,    -1,   114,   115,    -1,
      -1,    -1,   119,   120,   121,   122,   123,    94,    -1,    -1,
      -1,    -1,    -1,   100,   101,    -1,    -1,    -1,    -1,    -1,
     107,   108,    -1,   110,   111,   112,    -1,   114,   115,    -1,
      -1,    -1,    -1,    -1,    -1,   122,   123,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,     3,     4,     5,     6,     7,   657,
       9,    10,    11,    12,    13,    14,    15,    16,    17,    18,
      19,    20,    21,    22,    23,    24,    25,    26,    27,    28,
      29,    30,    31,    32,    33,    34,    35,    36,    37,    38,
      39,    40,    41,    42,    -1,    44,    45,    -1,    -1,    -1,
      -1,    -1,    -1,    52,    -1,    -1,    -1,   705,    -1,    -1,
      -1,    -1,   710,    -1,    -1,    -1,    65,    -1,    -1,    -1,
      -1,    70,    71,    -1,    -1,    -1,    75,    76,    77,    78,
      79,    80,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    92,    93,    94,    95,    96,    97,    98,
      99,   100,   101,   102,   103,   104,   105,   106,   107,   108,
     109,   110,   111,   112,    -1,   114,   115,   116,   117,    -1,
     119,   120,   121,   122,   123,     3,     4,     5,     6,     7,
       8,     9,    10,    11,    12,    13,    14,    15,    16,    17,
      18,    19,    20,    21,    22,    23,    24,    25,    26,    27,
      28,    29,    30,    31,    32,    33,    34,    35,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    91,    92,    93,    94,    95,    96,    97,
      98,    99,   100,   101,   102,   103,   104,   105,   106,   107,
     108,   109,   110,   111,   112,   113,   114,   115,   116,   117,
     118,   119,   120,   121,   122,   123,     3,     4,     5,     6,
      -1,    -1,     9,    -1,    -1,    12,    13,    14,    15,    16,
      -1,    18,    19,    20,    21,    22,    -1,    -1,    25,    26,
      -1,    -1,    29,    30,    -1,    -1,    33,    -1,    35,    36,
      37,    38,    39,    40,    41,    42,    -1,    44,    45,    -1,
      -1,    -1,    -1,    -1,    -1,    52,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    65,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    75,    76,
      77,    78,    79,    80,    -1,    -1,    -1,    -1,    -1,    19,
      -1,    -1,    -1,    -1,    -1,    92,    93,    94,    95,    96,
      30,    -1,    -1,   100,   101,   102,   103,   104,   105,   106,
     107,   108,   109,   110,   111,   112,    -1,   114,   115,   116,
     117,    -1,   119,   120,   121,   122,   123,     3,     4,     5,
       6,    -1,    -1,     9,    -1,    -1,    12,    13,    14,    15,
      16,    -1,    18,    19,    20,    21,    22,    -1,    -1,    25,
      26,    -1,    -1,    -1,    30,    -1,    -1,    33,    -1,    35,
      -1,    -1,    -1,    -1,    94,    -1,    42,    -1,    -1,    -1,
     100,   101,    -1,    -1,    -1,   105,    -1,   107,   108,    -1,
     110,   111,   112,    -1,   114,   115,    -1,    -1,    -1,    65,
      -1,    -1,   122,   123,    -1,    -1,    72,    -1,    -1,    -1,
      76,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    92,    93,    94,    -1,
      -1,    -1,    -1,    -1,   100,   101,    -1,    -1,    -1,   105,
     106,   107,   108,    -1,   110,   111,   112,    -1,   114,   115,
     116,   117,    -1,   119,   120,   121,   122,   123,     3,     4,
       5,     6,    -1,    -1,     9,    -1,    -1,    12,    13,    14,
      15,    16,    -1,    18,    19,    20,    21,    22,    -1,    -1,
      25,    26,    -1,    -1,    -1,    30,    -1,    -1,    33,    -1,
      35,    -1,    -1,    -1,    -1,    -1,    -1,    42,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,     3,     4,     5,     6,
      -1,    -1,     9,    -1,    -1,    12,    13,    14,    15,    16,
      65,    18,    19,    20,    21,    22,    -1,    72,    25,    26,
      -1,    76,    -1,    30,    -1,    -1,    33,    -1,    35,    -1,
      -1,    -1,    -1,    -1,    -1,    42,    -1,    92,    93,    94,
      -1,    -1,    -1,    -1,    -1,   100,   101,    -1,    -1,    -1,
     105,   106,   107,   108,    -1,   110,   111,   112,    65,   114,
     115,   116,   117,    -1,   119,   120,   121,   122,   123,    76,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    92,    93,    94,    -1,    -1,
      -1,    -1,    -1,   100,   101,    -1,    -1,    -1,   105,   106,
     107,   108,    -1,   110,   111,   112,    -1,   114,   115,   116,
     117,    -1,   119,   120,   121,   122,   123,     3,     4,     5,
       6,    -1,    -1,     9,    -1,    -1,    12,    13,    14,    15,
      16,    -1,    18,    19,    20,    21,    22,    -1,    -1,    25,
      26,    -1,    -1,    -1,    30,    -1,    -1,    33,    -1,    35,
      -1,    -1,    -1,    -1,    -1,    -1,    42,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,     3,     4,     5,     6,    -1,
      -1,     9,    -1,    -1,    12,    13,    14,    15,    16,    65,
      18,    19,    20,    21,    22,    -1,    -1,    25,    26,    -1,
      76,    -1,    30,    -1,    -1,    33,    -1,    35,    -1,    -1,
      -1,    -1,    -1,    -1,    42,    -1,    92,    93,    94,    -1,
      -1,    -1,    -1,    -1,   100,   101,    -1,    -1,    -1,   105,
     106,   107,   108,    -1,   110,   111,   112,    65,   114,   115,
     116,   117,    -1,   119,   120,   121,   122,   123,    76,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    92,    93,    94,    -1,    -1,    -1,
      -1,    -1,   100,   101,    -1,    -1,    -1,   105,   106,   107,
     108,    -1,   110,   111,   112,    -1,   114,   115,   116,   117,
      -1,   119,   120,   121,   122,   123,     3,     4,     5,     6,
      -1,    -1,     9,    -1,    -1,    12,    13,    14,    15,    16,
      -1,    18,    19,    20,    21,    22,    -1,    -1,    25,    26,
      -1,    -1,    -1,    30,    -1,    -1,    33,    -1,    35,    -1,
      -1,    -1,    -1,    -1,    -1,    42,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,     3,     4,     5,     6,    -1,    -1,
       9,    -1,    -1,    12,    13,    14,    15,    16,    65,    18,
      19,    20,    21,    22,    -1,    -1,    25,    26,    -1,    76,
      -1,    30,    -1,    -1,    33,    -1,    35,    -1,    -1,    -1,
      -1,    -1,    -1,    42,    -1,    92,    93,    94,    -1,    -1,
      -1,    -1,    -1,   100,   101,    -1,    -1,    -1,   105,   106,
     107,   108,    -1,   110,   111,   112,    65,   114,   115,   116,
     117,    -1,   119,   120,   121,   122,   123,    76,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    92,    93,    94,    -1,    -1,    -1,    -1,
      -1,   100,   101,    -1,    -1,    -1,   105,   106,   107,   108,
      -1,   110,   111,   112,    -1,   114,   115,   116,   117,    -1,
     119,   120,   121,   122,   123,     3,     4,     5,     6,    -1,
      -1,     9,    -1,    -1,    12,    13,    14,    15,    16,    -1,
      18,    19,    20,    21,    22,    -1,    -1,    25,    26,    -1,
      -1,    -1,    30,    -1,    -1,    33,    -1,     3,     4,     5,
       6,    -1,    -1,     9,    42,    -1,    12,    13,    14,    15,
      16,    -1,    18,    19,    20,    21,    22,    -1,    -1,    25,
      26,    -1,    -1,    -1,    30,    -1,    -1,    33,    -1,    -1,
      -1,    -1,    70,    -1,    -1,    -1,    42,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    92,    93,    94,    -1,    -1,    -1,
      -1,    -1,   100,   101,    -1,    -1,    -1,   105,   106,   107,
     108,   109,   110,   111,   112,    -1,   114,   115,   116,   117,
      -1,   119,   120,   121,   122,   123,    92,    93,    94,    -1,
      -1,    -1,    -1,    -1,   100,   101,    -1,    -1,    -1,   105,
     106,   107,   108,   109,   110,   111,   112,    -1,   114,   115,
     116,   117,    -1,   119,   120,   121,   122,   123,     3,     4,
       5,     6,    -1,    -1,     9,    -1,    -1,    12,    13,    14,
      15,    16,    -1,    18,    19,    20,    21,    22,    -1,    -1,
      25,    26,    -1,    -1,    -1,    30,    -1,    -1,    33,    -1,
      35,    -1,     3,     4,     5,     6,    -1,    42,     9,    -1,
      -1,    12,    13,    14,    15,    16,    -1,    18,    19,    20,
      21,    22,    -1,    -1,    25,    26,    -1,    -1,    -1,    30,
      -1,    -1,    33,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    42,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    54,    -1,    -1,    -1,    92,    93,    94,
      -1,    -1,    -1,    -1,    -1,   100,   101,    -1,    -1,    -1,
     105,   106,   107,   108,    -1,   110,   111,   112,    -1,   114,
     115,   116,   117,    -1,   119,   120,   121,   122,   123,    -1,
      -1,    92,    93,    94,    -1,    -1,    -1,    -1,    -1,   100,
     101,    -1,    -1,    -1,   105,   106,   107,   108,    -1,   110,
     111,   112,    -1,   114,   115,   116,   117,    -1,   119,   120,
     121,   122,   123,     3,     4,     5,     6,    -1,    -1,     9,
      -1,    -1,    12,    13,    14,    15,    16,    -1,    18,    19,
      20,    21,    22,    -1,    -1,    25,    26,    -1,    -1,    -1,
      30,    -1,    -1,    33,    -1,     3,     4,     5,    -1,    -1,
      -1,     9,    42,    -1,    -1,    13,    14,    15,    16,    -1,
      -1,    19,    20,    21,    22,    -1,    -1,    25,    26,     3,
      -1,    -1,    30,    -1,    -1,    33,    -1,    -1,    -1,    13,
      14,    -1,    16,    -1,    -1,    19,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    30,    -1,    -1,    33,
      -1,    -1,    92,    93,    94,    -1,    -1,    -1,    -1,    -1,
     100,   101,    -1,    -1,    -1,   105,   106,   107,   108,    -1,
     110,   111,   112,    -1,   114,   115,   116,   117,    -1,   119,
     120,   121,   122,   123,    92,    93,    94,    -1,    -1,    -1,
      -1,    -1,   100,   101,    -1,    -1,    -1,    -1,   106,   107,
     108,    -1,   110,   111,   112,    -1,   114,   115,   116,   117,
      94,    -1,    -1,    -1,   122,   123,   100,   101,    -1,    -1,
      -1,   105,    -1,   107,   108,    -1,   110,   111,   112,    -1,
     114,   115,     3,    -1,    -1,    -1,    -1,    -1,   122,   123,
      -1,    -1,    13,    14,    -1,    16,    -1,    -1,    19,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    30,
      -1,    -1,    33,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,     4,     5,     6,    -1,    -1,     9,
      -1,    -1,    12,    -1,    -1,    15,    -1,    -1,    18,    19,
      20,    21,    22,    -1,    -1,    25,    26,    -1,    -1,    29,
      30,    -1,    -1,    -1,    -1,    35,    36,    37,    38,    39,
      40,    41,    42,    -1,    44,    45,    -1,    -1,    -1,    -1,
      -1,    -1,    52,    94,    -1,    -1,    -1,    -1,    -1,   100,
     101,    -1,    -1,    -1,    -1,    65,   107,   108,    -1,   110,
     111,   112,    19,   114,   115,    75,    76,    77,    78,    79,
      80,   122,   123,    30,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    92,    93,    94,    95,    96,    -1,    -1,    -1,
     100,   101,   102,   103,   104,   105,   106,   107,   108,   109,
     110,   111,   112,    -1,   114,   115,   116,   117,    65,   119,
     120,   121,   122,   123,     4,     5,     6,    -1,    -1,     9,
      -1,    -1,    12,    -1,    -1,    15,    -1,    -1,    18,    19,
      20,    21,    22,    -1,    -1,    25,    26,    94,    -1,    -1,
      30,    -1,    -1,   100,   101,    35,    -1,    -1,    -1,    -1,
     107,   108,    42,   110,   111,   112,    -1,   114,   115,    -1,
      -1,    -1,    -1,    -1,    -1,   122,   123,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    65,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    76,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    88,    89,
      -1,    -1,    92,    93,    94,    -1,    -1,    -1,    -1,    -1,
     100,   101,    -1,    -1,    -1,   105,   106,   107,   108,    -1,
     110,   111,   112,    -1,   114,   115,   116,   117,    -1,   119,
     120,   121,   122,   123,     4,     5,     6,    -1,    -1,     9,
      -1,    -1,    12,    -1,    -1,    15,    -1,    -1,    18,    19,
      20,    21,    22,    -1,    -1,    25,    26,    -1,    -1,    -1,
      30,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    42,    -1,    -1,     4,     5,     6,    -1,    -1,
       9,    -1,    -1,    12,    -1,    -1,    15,    -1,    -1,    18,
      19,    20,    21,    22,    -1,    65,    25,    26,    -1,    -1,
      -1,    30,    72,    -1,    -1,    -1,    76,    -1,    -1,    -1,
      -1,    -1,    -1,    42,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    92,    93,    94,    -1,    -1,    -1,    -1,    -1,
     100,   101,    -1,    -1,    -1,   105,   106,   107,   108,    -1,
     110,   111,   112,    -1,   114,   115,   116,   117,    -1,   119,
     120,   121,   122,   123,    -1,    -1,    -1,    -1,    -1,    -1,
      89,    -1,    -1,    92,    93,    94,    -1,    -1,    -1,    -1,
      -1,   100,   101,    19,    -1,    -1,   105,   106,   107,   108,
      -1,   110,   111,   112,    30,   114,   115,   116,   117,    35,
     119,   120,   121,   122,   123,     4,     5,     6,    -1,    -1,
       9,    -1,    -1,    12,    -1,    -1,    15,    -1,    -1,    18,
      19,    20,    21,    22,    -1,    -1,    25,    26,    -1,    65,
      -1,    30,    -1,    -1,    -1,    -1,    72,    -1,    -1,    -1,
      76,    -1,    -1,    42,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    19,    94,    -1,
      -1,    -1,    -1,    -1,   100,   101,    -1,    -1,    30,    -1,
      -1,   107,   108,    35,   110,   111,   112,    -1,   114,   115,
      42,    -1,    -1,    -1,    -1,    -1,   122,   123,    -1,    19,
      -1,    -1,    -1,    92,    93,    94,    -1,    -1,    -1,    -1,
      30,   100,   101,    65,    -1,    -1,   105,   106,   107,   108,
      72,   110,   111,   112,    76,   114,   115,   116,   117,    19,
     119,   120,   121,   122,   123,    -1,    -1,    -1,    -1,    -1,
      30,    -1,    94,    -1,    -1,    35,    -1,    -1,   100,   101,
      -1,    -1,    42,    -1,    -1,   107,   108,    -1,   110,   111,
     112,    -1,   114,   115,    -1,    -1,    19,    -1,    -1,    -1,
     122,   123,    -1,    -1,    94,    65,    -1,    30,    -1,    -1,
     100,   101,    35,    -1,    -1,    19,    76,   107,   108,    42,
     110,   111,   112,    -1,   114,   115,    30,    -1,    -1,    -1,
      -1,    35,   122,   123,    94,    -1,    -1,    -1,    42,    -1,
     100,   101,    65,    -1,    -1,    -1,    -1,   107,   108,    -1,
     110,   111,   112,    76,   114,   115,    -1,    -1,    -1,    19,
      -1,    65,   122,   123,    -1,    -1,    -1,    -1,    -1,    -1,
      30,    94,    76,    -1,    -1,    35,    -1,   100,   101,    -1,
      -1,    -1,    42,    -1,   107,   108,    -1,   110,   111,   112,
      94,   114,   115,    -1,    -1,    -1,   100,   101,    -1,   122,
     123,    -1,    -1,   107,   108,    65,   110,   111,   112,    -1,
     114,   115,    19,    -1,    -1,    -1,    76,    -1,   122,   123,
      -1,    -1,    -1,    30,    -1,    -1,    -1,    -1,    35,    -1,
      -1,    -1,    -1,    -1,    94,    -1,    -1,    -1,    -1,    -1,
     100,   101,    -1,    -1,    -1,    19,    -1,   107,   108,    -1,
     110,   111,   112,    -1,   114,   115,    30,    -1,    65,    -1,
      -1,    35,   122,   123,    -1,    -1,    -1,    -1,    -1,    76,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    94,    -1,    -1,
      -1,    65,    -1,   100,   101,    -1,    -1,    -1,    -1,    -1,
     107,   108,    76,   110,   111,   112,    -1,   114,   115,    -1,
      -1,    -1,    -1,    -1,    -1,   122,   123,    -1,    -1,    -1,
      94,    -1,    -1,    -1,    -1,    -1,   100,   101,    -1,    -1,
      -1,    -1,    -1,   107,   108,    -1,   110,   111,   112,    -1,
     114,   115,     7,    -1,    -1,    10,    11,    -1,   122,   123,
      -1,    -1,    17,    -1,    -1,    -1,    -1,    -1,    23,    24,
      -1,    -1,    27,    28,    29,    -1,    31,    32,    -1,    34,
      35,    36,    37,    38,    39,    40,    41,    42,    -1,    44,
      45,    -1,    -1,    -1,    -1,    -1,    -1,    52,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      65,    -1,    -1,    -1,    -1,    70,    -1,    -1,    -1,    -1,
      75,    76,    77,    78,    79,    80,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      95,    96,    97,    98,    99,    -1,    -1,   102,   103,   104,
      29,    -1,    -1,    -1,   109,    -1,    35,    36,    37,    38,
      39,    40,    41,    -1,    -1,    44,    45,    -1,    -1,    -1,
      -1,    -1,    -1,    52,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    65,    -1,    -1,    -1,
      -1,    70,    71,    72,    -1,    74,    75,    76,    77,    78,
      79,    80,    -1,    29,    -1,    -1,    -1,    -1,    -1,    35,
      36,    37,    38,    39,    40,    41,    95,    96,    44,    45,
      -1,    -1,    -1,   102,   103,   104,    52,    -1,    -1,    -1,
     109,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    65,
      -1,    -1,    -1,    -1,    70,    -1,    72,    -1,    74,    75,
      76,    77,    78,    79,    80,    -1,    29,    -1,    -1,    -1,
      -1,    -1,    35,    36,    37,    38,    39,    40,    41,    95,
      96,    44,    45,    -1,    -1,    -1,   102,   103,   104,    52,
      -1,    -1,    -1,   109,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    65,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      73,    -1,    75,    76,    77,    78,    79,    80,    -1,    29,
      -1,    -1,    -1,    -1,    -1,    35,    36,    37,    38,    39,
      40,    41,    95,    96,    44,    45,    -1,    -1,    -1,   102,
     103,   104,    52,    -1,    -1,    -1,   109,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    65,    66,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    75,    76,    77,    78,    79,
      80,    -1,    29,    -1,    -1,    -1,    -1,    -1,    35,    36,
      37,    38,    39,    40,    41,    95,    96,    44,    45,    -1,
      -1,    -1,   102,   103,   104,    52,    -1,    -1,    -1,   109,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    65,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    75,    76,
      77,    78,    79,    80,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    88,    -1,    -1,    -1,    -1,    -1,    -1,    95,    96,
      -1,    -1,    -1,    -1,    -1,   102,   103,   104,    29,    -1,
      -1,    -1,   109,    -1,    35,    36,    37,    38,    39,    40,
      41,    -1,    -1,    44,    45,    -1,    -1,    -1,    -1,    -1,
      -1,    52,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    65,    -1,    -1,    -1,    -1,    70,
      -1,    -1,    -1,    -1,    75,    76,    77,    78,    79,    80,
      -1,    29,    -1,    -1,    -1,    -1,    -1,    35,    36,    37,
      38,    39,    40,    41,    95,    96,    44,    45,    -1,    -1,
      -1,   102,   103,   104,    52,    -1,    -1,    -1,   109,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    65,    66,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    75,    76,    77,
      78,    79,    80,    -1,    29,    -1,    -1,    -1,    -1,    -1,
      35,    36,    37,    38,    39,    40,    41,    95,    96,    44,
      45,    -1,    -1,    -1,   102,   103,   104,    52,    -1,    -1,
      -1,   109,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      65,    -1,    -1,    -1,    -1,    70,    -1,    -1,    -1,    -1,
      75,    76,    77,    78,    79,    80,    -1,    29,    -1,    -1,
      -1,    -1,    -1,    35,    36,    37,    38,    39,    40,    41,
      95,    96,    44,    45,    -1,    -1,    -1,   102,   103,   104,
      52,    -1,    -1,    -1,   109,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    65,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    75,    76,    77,    78,    79,    80,    -1,
      29,    -1,    -1,    -1,    -1,    -1,    35,    36,    37,    38,
      39,    40,    41,    95,    96,    44,    45,    -1,    -1,    -1,
     102,   103,   104,    52,    -1,    -1,    -1,   109,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    65,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    75,    76,    77,    78,
      79,    80,    -1,    29,    -1,    -1,    -1,    -1,    -1,    35,
      36,    37,    38,    39,    40,    41,    95,    96,    44,    45,
      -1,    -1,    -1,   102,   103,   104,    52,    -1,    -1,    -1,
     109,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    65,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    75,
      76,    77,    78,    79,    80,    -1,    29,    -1,    -1,    -1,
      -1,    -1,    35,    36,    37,    38,    39,    40,    41,    95,
      96,    44,    45,    -1,    -1,    -1,   102,   103,   104,    52,
      -1,    -1,    -1,   109,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    65,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    75,    76,    77,    78,    79,    80,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    95,    96,    -1,    -1,    -1,    -1,    -1,   102,
     103,   104,    -1,    -1,    -1,    -1,   109
};

/* YYSTOS[STATE-NUM] -- The (internal number of the) accessing
   symbol of state STATE-NUM.  */
static const yytype_uint16 yystos[] =
{
       0,   126,   127,     0,     3,     4,     5,     6,     9,    12,
      13,    14,    15,    16,    18,    19,    20,    21,    22,    25,
      26,    30,    33,    35,    42,    65,    76,    89,    92,    93,
      94,    97,    98,    99,   100,   101,   105,   106,   107,   108,
     109,   110,   111,   112,   114,   115,   116,   117,   119,   120,
     121,   122,   123,   128,   129,   130,   131,   132,   133,   134,
     135,   136,   137,   138,   139,   140,   141,   143,   144,   145,
     146,   147,   148,   149,   150,   151,   152,   153,   154,   155,
     156,   157,   158,   159,   160,   161,   162,   163,   164,   165,
     166,   167,   176,   206,   207,   208,   209,   210,   211,   212,
     213,   270,   271,   276,   277,   285,    35,    42,    70,   185,
     269,   270,   208,   210,   212,   141,   206,   212,   131,   135,
     293,   293,    67,   294,    67,   294,    42,    65,    76,   197,
     198,   199,   200,   201,   202,   203,   204,   206,   212,   197,
     206,   212,    42,   142,   143,   156,   160,   161,   162,   165,
     206,   212,    42,   143,   156,   160,   161,   162,   165,   206,
     212,   142,   162,   143,   161,   162,   142,   294,   143,   161,
     294,   142,   143,   161,   142,   156,   143,   156,   161,    65,
     142,   160,   143,   160,   161,   185,   269,   290,   288,    65,
      72,   215,   217,   288,    65,    89,    65,    35,    42,   177,
     178,    70,    70,   185,   270,    66,    66,    66,   206,   212,
      70,   109,   134,   138,   139,   140,   141,   229,   268,   269,
      89,   268,    89,    65,   215,    42,    65,   201,   203,   205,
      65,   141,   200,   203,   286,   286,   286,   286,   286,   286,
     286,   286,   286,   286,    89,    89,    29,    35,    36,    37,
      38,    39,    40,    41,    44,    45,    52,    65,    75,    76,
      77,    78,    79,    80,    95,    96,   102,   103,   104,   109,
     139,   141,   149,   151,   153,   155,   159,   186,   234,   235,
     236,   237,   238,   239,   240,   241,   243,   244,   245,   246,
     247,   248,   249,   250,   251,   252,   253,   254,   255,   256,
     257,   258,   259,   260,   261,   262,   263,   265,   290,   185,
     290,    70,   290,    73,   243,   262,   266,    72,    65,   235,
     289,   289,    67,    71,   177,   177,    70,   215,   215,   113,
     223,   224,   225,    65,    76,   206,   210,    65,    76,   206,
     206,    70,   134,   206,   197,   290,   205,    66,    66,   215,
     205,    65,   200,   203,   277,   278,   278,   278,   278,    65,
     243,    65,   243,   243,    35,   186,   265,   290,    65,    65,
      65,   251,    65,    76,   214,   215,   218,   219,   214,    66,
      36,    43,    44,    45,    65,    72,    74,    55,    56,    57,
      58,    59,    60,    61,    62,    63,    64,    90,   264,    65,
     243,   251,    76,    81,    82,    77,    78,    46,    47,    48,
      49,    83,    84,    50,    51,    75,    85,    86,    52,    53,
      87,    66,    67,    70,   290,    70,   168,    35,   138,   139,
     140,   141,   150,   151,   180,   181,   182,   183,   184,   216,
      73,   266,     3,     4,     5,     6,     7,     8,     9,    10,
      11,    12,    13,    14,    15,    16,    17,    18,    19,    20,
      21,    22,    23,    24,    25,    26,    27,    28,    29,    30,
      31,    32,    33,    34,    35,    91,    92,    93,    94,    95,
      96,    97,    98,    99,   100,   101,   102,   103,   104,   105,
     106,   107,   108,   109,   110,   111,   112,   113,   114,   115,
     116,   117,   118,   119,   120,   121,   122,   123,   272,   273,
     275,    66,    90,   179,   179,    71,   178,    67,    71,    67,
      71,   177,    35,   226,   227,   225,    65,   210,    65,   141,
     141,   286,   286,   223,   287,   287,    66,   215,   215,    66,
      66,   205,   268,   268,   268,   268,   186,   186,    66,    66,
      70,   222,   186,   186,   263,   215,   218,   219,   141,   214,
     185,    66,   242,   263,   265,   185,   263,   186,   251,   251,
     251,   252,   252,   253,   253,   254,   254,   254,   254,   255,
     255,   256,   257,   258,   259,   260,    88,   265,   263,   168,
      70,   168,    89,   139,   141,   169,   170,   171,   291,   288,
      65,    76,   200,   206,   214,   200,   206,   214,    65,    76,
     206,   214,   206,   214,    67,    67,   292,   292,    73,    66,
      67,    65,   274,   266,    71,    71,    67,    71,    67,    89,
       7,    10,    11,    17,    23,    24,    27,    28,    31,    32,
      34,    35,    42,   109,   134,   185,   220,   221,   222,   228,
     230,   231,   232,   233,   265,   267,   279,   285,   291,    65,
     227,   278,   278,    66,    90,   187,   187,   187,   187,    66,
      66,    70,   251,   223,   291,    67,    67,    67,    66,    66,
      66,   214,    66,    67,    73,    66,   262,    88,   291,   168,
     291,    88,    89,   172,   175,   197,    89,   173,   175,   206,
      67,    89,    67,    89,    71,   141,   286,   286,   286,   286,
     141,   286,   286,    54,   182,   184,    66,    66,    66,   275,
      66,   242,    71,    35,    89,    65,   266,   267,    89,    65,
      88,    76,   185,   220,    65,    65,    88,    89,    65,   143,
      71,   291,   268,   268,    35,    70,    72,    74,   188,   189,
     192,   193,   194,   195,   196,   263,   190,   191,   227,    66,
     240,   186,   186,   215,   263,   262,    71,   291,    71,   266,
     268,   174,   175,   268,   174,   173,   172,   268,   268,   268,
     268,   268,   268,   274,    66,   265,    54,    88,    89,   267,
     220,   265,    89,    34,   265,   265,   268,   235,   280,    65,
      71,   187,   187,    88,   191,   266,    35,    42,   189,    72,
      90,   194,    71,   188,    71,    66,    66,    66,    71,   268,
     268,    66,   266,   220,    89,    89,    65,    66,    66,   220,
      88,    66,   280,    71,   188,    54,    73,   266,    67,   220,
      88,   267,   265,   220,   220,    72,   235,   281,   282,   283,
      89,    66,    71,   266,    54,    73,   220,    89,    66,     8,
     275,    65,    88,    67,    89,    73,   266,   267,    89,   220,
      73,   265,   281,   283,    73,    66,   235,    66,    88,   220,
      65,   235,   284,   265,    67,    66,   235
};

#define yyerrok		(yyerrstatus = 0)
#define yyclearin	(yychar = YYEMPTY)
#define YYEMPTY		(-2)
#define YYEOF		0

#define YYACCEPT	goto yyacceptlab
#define YYABORT		goto yyabortlab
#define YYERROR		goto yyerrorlab


/* Like YYERROR except do call yyerror.  This remains here temporarily
   to ease the transition to the new meaning of YYERROR, for GCC.
   Once GCC version 2 has supplanted version 1, this can go.  */

#define YYFAIL		goto yyerrlab

#define YYRECOVERING()  (!!yyerrstatus)

#define YYBACKUP(Token, Value)					\
do								\
  if (yychar == YYEMPTY && yylen == 1)				\
    {								\
      yychar = (Token);						\
      yylval = (Value);						\
      yytoken = YYTRANSLATE (yychar);				\
      YYPOPSTACK (1);						\
      goto yybackup;						\
    }								\
  else								\
    {								\
      yyerror (YY_("syntax error: cannot back up")); \
      YYERROR;							\
    }								\
while (YYID (0))


#define YYTERROR	1
#define YYERRCODE	256


/* YYLLOC_DEFAULT -- Set CURRENT to span from RHS[1] to RHS[N].
   If N is 0, then set CURRENT to the empty location which ends
   the previous symbol: RHS[0] (always defined).  */

#define YYRHSLOC(Rhs, K) ((Rhs)[K])
#ifndef YYLLOC_DEFAULT
# define YYLLOC_DEFAULT(Current, Rhs, N)				\
    do									\
      if (YYID (N))                                                    \
	{								\
	  (Current).first_line   = YYRHSLOC (Rhs, 1).first_line;	\
	  (Current).first_column = YYRHSLOC (Rhs, 1).first_column;	\
	  (Current).last_line    = YYRHSLOC (Rhs, N).last_line;		\
	  (Current).last_column  = YYRHSLOC (Rhs, N).last_column;	\
	}								\
      else								\
	{								\
	  (Current).first_line   = (Current).last_line   =		\
	    YYRHSLOC (Rhs, 0).last_line;				\
	  (Current).first_column = (Current).last_column =		\
	    YYRHSLOC (Rhs, 0).last_column;				\
	}								\
    while (YYID (0))
#endif


/* YY_LOCATION_PRINT -- Print the location on the stream.
   This macro was not mandated originally: define only if we know
   we won't break user code: when these are the locations we know.  */

#ifndef YY_LOCATION_PRINT
# if YYLTYPE_IS_TRIVIAL
#  define YY_LOCATION_PRINT(File, Loc)			\
     fprintf (File, "%d.%d-%d.%d",			\
	      (Loc).first_line, (Loc).first_column,	\
	      (Loc).last_line,  (Loc).last_column)
# else
#  define YY_LOCATION_PRINT(File, Loc) ((void) 0)
# endif
#endif


/* YYLEX -- calling `yylex' with the right arguments.  */

#ifdef YYLEX_PARAM
# define YYLEX yylex (YYLEX_PARAM)
#else
# define YYLEX yylex ()
#endif

/* Enable debugging if requested.  */
#if YYDEBUG

# ifndef YYFPRINTF
#  include <stdio.h> /* INFRINGES ON USER NAME SPACE */
#  define YYFPRINTF fprintf
# endif

# define YYDPRINTF(Args)			\
do {						\
  if (yydebug)					\
    YYFPRINTF Args;				\
} while (YYID (0))

# define YY_SYMBOL_PRINT(Title, Type, Value, Location)			  \
do {									  \
  if (yydebug)								  \
    {									  \
      YYFPRINTF (stderr, "%s ", Title);					  \
      yy_symbol_print (stderr,						  \
		  Type, Value); \
      YYFPRINTF (stderr, "\n");						  \
    }									  \
} while (YYID (0))


/*--------------------------------.
| Print this symbol on YYOUTPUT.  |
`--------------------------------*/

/*ARGSUSED*/
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_symbol_value_print (FILE *yyoutput, int yytype, YYSTYPE const * const yyvaluep)
#else
static void
yy_symbol_value_print (yyoutput, yytype, yyvaluep)
    FILE *yyoutput;
    int yytype;
    YYSTYPE const * const yyvaluep;
#endif
{
  if (!yyvaluep)
    return;
# ifdef YYPRINT
  if (yytype < YYNTOKENS)
    YYPRINT (yyoutput, yytoknum[yytype], *yyvaluep);
# else
  YYUSE (yyoutput);
# endif
  switch (yytype)
    {
      default:
	break;
    }
}


/*--------------------------------.
| Print this symbol on YYOUTPUT.  |
`--------------------------------*/

#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_symbol_print (FILE *yyoutput, int yytype, YYSTYPE const * const yyvaluep)
#else
static void
yy_symbol_print (yyoutput, yytype, yyvaluep)
    FILE *yyoutput;
    int yytype;
    YYSTYPE const * const yyvaluep;
#endif
{
  if (yytype < YYNTOKENS)
    YYFPRINTF (yyoutput, "token %s (", yytname[yytype]);
  else
    YYFPRINTF (yyoutput, "nterm %s (", yytname[yytype]);

  yy_symbol_value_print (yyoutput, yytype, yyvaluep);
  YYFPRINTF (yyoutput, ")");
}

/*------------------------------------------------------------------.
| yy_stack_print -- Print the state stack from its BOTTOM up to its |
| TOP (included).                                                   |
`------------------------------------------------------------------*/

#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_stack_print (yytype_int16 *yybottom, yytype_int16 *yytop)
#else
static void
yy_stack_print (yybottom, yytop)
    yytype_int16 *yybottom;
    yytype_int16 *yytop;
#endif
{
  YYFPRINTF (stderr, "Stack now");
  for (; yybottom <= yytop; yybottom++)
    {
      int yybot = *yybottom;
      YYFPRINTF (stderr, " %d", yybot);
    }
  YYFPRINTF (stderr, "\n");
}

# define YY_STACK_PRINT(Bottom, Top)				\
do {								\
  if (yydebug)							\
    yy_stack_print ((Bottom), (Top));				\
} while (YYID (0))


/*------------------------------------------------.
| Report that the YYRULE is going to be reduced.  |
`------------------------------------------------*/

#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_reduce_print (YYSTYPE *yyvsp, int yyrule)
#else
static void
yy_reduce_print (yyvsp, yyrule)
    YYSTYPE *yyvsp;
    int yyrule;
#endif
{
  int yynrhs = yyr2[yyrule];
  int yyi;
  unsigned long int yylno = yyrline[yyrule];
  YYFPRINTF (stderr, "Reducing stack by rule %d (line %lu):\n",
	     yyrule - 1, yylno);
  /* The symbols being reduced.  */
  for (yyi = 0; yyi < yynrhs; yyi++)
    {
      YYFPRINTF (stderr, "   $%d = ", yyi + 1);
      yy_symbol_print (stderr, yyrhs[yyprhs[yyrule] + yyi],
		       &(yyvsp[(yyi + 1) - (yynrhs)])
		       		       );
      YYFPRINTF (stderr, "\n");
    }
}

# define YY_REDUCE_PRINT(Rule)		\
do {					\
  if (yydebug)				\
    yy_reduce_print (yyvsp, Rule); \
} while (YYID (0))

/* Nonzero means print parse trace.  It is left uninitialized so that
   multiple parsers can coexist.  */
int yydebug;
#else /* !YYDEBUG */
# define YYDPRINTF(Args)
# define YY_SYMBOL_PRINT(Title, Type, Value, Location)
# define YY_STACK_PRINT(Bottom, Top)
# define YY_REDUCE_PRINT(Rule)
#endif /* !YYDEBUG */


/* YYINITDEPTH -- initial size of the parser's stacks.  */
#ifndef	YYINITDEPTH
# define YYINITDEPTH 200
#endif

/* YYMAXDEPTH -- maximum size the stacks can grow to (effective only
   if the built-in stack extension method is used).

   Do not make this value too large; the results are undefined if
   YYSTACK_ALLOC_MAXIMUM < YYSTACK_BYTES (YYMAXDEPTH)
   evaluated with infinite-precision integer arithmetic.  */

#ifndef YYMAXDEPTH
# define YYMAXDEPTH 10000
#endif



#if YYERROR_VERBOSE

# ifndef yystrlen
#  if defined __GLIBC__ && defined _STRING_H
#   define yystrlen strlen
#  else
/* Return the length of YYSTR.  */
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static YYSIZE_T
yystrlen (const char *yystr)
#else
static YYSIZE_T
yystrlen (yystr)
    const char *yystr;
#endif
{
  YYSIZE_T yylen;
  for (yylen = 0; yystr[yylen]; yylen++)
    continue;
  return yylen;
}
#  endif
# endif

# ifndef yystpcpy
#  if defined __GLIBC__ && defined _STRING_H && defined _GNU_SOURCE
#   define yystpcpy stpcpy
#  else
/* Copy YYSRC to YYDEST, returning the address of the terminating '\0' in
   YYDEST.  */
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static char *
yystpcpy (char *yydest, const char *yysrc)
#else
static char *
yystpcpy (yydest, yysrc)
    char *yydest;
    const char *yysrc;
#endif
{
  char *yyd = yydest;
  const char *yys = yysrc;

  while ((*yyd++ = *yys++) != '\0')
    continue;

  return yyd - 1;
}
#  endif
# endif

# ifndef yytnamerr
/* Copy to YYRES the contents of YYSTR after stripping away unnecessary
   quotes and backslashes, so that it's suitable for yyerror.  The
   heuristic is that double-quoting is unnecessary unless the string
   contains an apostrophe, a comma, or backslash (other than
   backslash-backslash).  YYSTR is taken from yytname.  If YYRES is
   null, do not copy; instead, return the length of what the result
   would have been.  */
static YYSIZE_T
yytnamerr (char *yyres, const char *yystr)
{
  if (*yystr == '"')
    {
      YYSIZE_T yyn = 0;
      char const *yyp = yystr;

      for (;;)
	switch (*++yyp)
	  {
	  case '\'':
	  case ',':
	    goto do_not_strip_quotes;

	  case '\\':
	    if (*++yyp != '\\')
	      goto do_not_strip_quotes;
	    /* Fall through.  */
	  default:
	    if (yyres)
	      yyres[yyn] = *yyp;
	    yyn++;
	    break;

	  case '"':
	    if (yyres)
	      yyres[yyn] = '\0';
	    return yyn;
	  }
    do_not_strip_quotes: ;
    }

  if (! yyres)
    return yystrlen (yystr);

  return yystpcpy (yyres, yystr) - yyres;
}
# endif

/* Copy into YYRESULT an error message about the unexpected token
   YYCHAR while in state YYSTATE.  Return the number of bytes copied,
   including the terminating null byte.  If YYRESULT is null, do not
   copy anything; just return the number of bytes that would be
   copied.  As a special case, return 0 if an ordinary "syntax error"
   message will do.  Return YYSIZE_MAXIMUM if overflow occurs during
   size calculation.  */
static YYSIZE_T
yysyntax_error (char *yyresult, int yystate, int yychar)
{
  int yyn = yypact[yystate];

  if (! (YYPACT_NINF < yyn && yyn <= YYLAST))
    return 0;
  else
    {
      int yytype = YYTRANSLATE (yychar);
      YYSIZE_T yysize0 = yytnamerr (0, yytname[yytype]);
      YYSIZE_T yysize = yysize0;
      YYSIZE_T yysize1;
      int yysize_overflow = 0;
      enum { YYERROR_VERBOSE_ARGS_MAXIMUM = 5 };
      char const *yyarg[YYERROR_VERBOSE_ARGS_MAXIMUM];
      int yyx;

# if 0
      /* This is so xgettext sees the translatable formats that are
	 constructed on the fly.  */
      YY_("syntax error, unexpected %s");
      YY_("syntax error, unexpected %s, expecting %s");
      YY_("syntax error, unexpected %s, expecting %s or %s");
      YY_("syntax error, unexpected %s, expecting %s or %s or %s");
      YY_("syntax error, unexpected %s, expecting %s or %s or %s or %s");
# endif
      char *yyfmt;
      char const *yyf;
      static char const yyunexpected[] = "syntax error, unexpected %s";
      static char const yyexpecting[] = ", expecting %s";
      static char const yyor[] = " or %s";
      char yyformat[sizeof yyunexpected
		    + sizeof yyexpecting - 1
		    + ((YYERROR_VERBOSE_ARGS_MAXIMUM - 2)
		       * (sizeof yyor - 1))];
      char const *yyprefix = yyexpecting;

      /* Start YYX at -YYN if negative to avoid negative indexes in
	 YYCHECK.  */
      int yyxbegin = yyn < 0 ? -yyn : 0;

      /* Stay within bounds of both yycheck and yytname.  */
      int yychecklim = YYLAST - yyn + 1;
      int yyxend = yychecklim < YYNTOKENS ? yychecklim : YYNTOKENS;
      int yycount = 1;

      yyarg[0] = yytname[yytype];
      yyfmt = yystpcpy (yyformat, yyunexpected);

      for (yyx = yyxbegin; yyx < yyxend; ++yyx)
	if (yycheck[yyx + yyn] == yyx && yyx != YYTERROR)
	  {
	    if (yycount == YYERROR_VERBOSE_ARGS_MAXIMUM)
	      {
		yycount = 1;
		yysize = yysize0;
		yyformat[sizeof yyunexpected - 1] = '\0';
		break;
	      }
	    yyarg[yycount++] = yytname[yyx];
	    yysize1 = yysize + yytnamerr (0, yytname[yyx]);
	    yysize_overflow |= (yysize1 < yysize);
	    yysize = yysize1;
	    yyfmt = yystpcpy (yyfmt, yyprefix);
	    yyprefix = yyor;
	  }

      yyf = YY_(yyformat);
      yysize1 = yysize + yystrlen (yyf);
      yysize_overflow |= (yysize1 < yysize);
      yysize = yysize1;

      if (yysize_overflow)
	return YYSIZE_MAXIMUM;

      if (yyresult)
	{
	  /* Avoid sprintf, as that infringes on the user's name space.
	     Don't have undefined behavior even if the translation
	     produced a string with the wrong number of "%s"s.  */
	  char *yyp = yyresult;
	  int yyi = 0;
	  while ((*yyp = *yyf) != '\0')
	    {
	      if (*yyp == '%' && yyf[1] == 's' && yyi < yycount)
		{
		  yyp += yytnamerr (yyp, yyarg[yyi++]);
		  yyf += 2;
		}
	      else
		{
		  yyp++;
		  yyf++;
		}
	    }
	}
      return yysize;
    }
}
#endif /* YYERROR_VERBOSE */


/*-----------------------------------------------.
| Release the memory associated to this symbol.  |
`-----------------------------------------------*/

/*ARGSUSED*/
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yydestruct (const char *yymsg, int yytype, YYSTYPE *yyvaluep)
#else
static void
yydestruct (yymsg, yytype, yyvaluep)
    const char *yymsg;
    int yytype;
    YYSTYPE *yyvaluep;
#endif
{
  YYUSE (yyvaluep);

  if (!yymsg)
    yymsg = "Deleting";
  YY_SYMBOL_PRINT (yymsg, yytype, yyvaluep, yylocationp);

  switch (yytype)
    {

      default:
	break;
    }
}

/* Prevent warnings from -Wmissing-prototypes.  */
#ifdef YYPARSE_PARAM
#if defined __STDC__ || defined __cplusplus
int yyparse (void *YYPARSE_PARAM);
#else
int yyparse ();
#endif
#else /* ! YYPARSE_PARAM */
#if defined __STDC__ || defined __cplusplus
int yyparse (void);
#else
int yyparse ();
#endif
#endif /* ! YYPARSE_PARAM */


/* The lookahead symbol.  */
int yychar;

/* The semantic value of the lookahead symbol.  */
YYSTYPE yylval;

/* Number of syntax errors so far.  */
int yynerrs;



/*-------------------------.
| yyparse or yypush_parse.  |
`-------------------------*/

#ifdef YYPARSE_PARAM
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
int
yyparse (void *YYPARSE_PARAM)
#else
int
yyparse (YYPARSE_PARAM)
    void *YYPARSE_PARAM;
#endif
#else /* ! YYPARSE_PARAM */
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
int
yyparse (void)
#else
int
yyparse ()

#endif
#endif
{


    int yystate;
    /* Number of tokens to shift before error messages enabled.  */
    int yyerrstatus;

    /* The stacks and their tools:
       `yyss': related to states.
       `yyvs': related to semantic values.

       Refer to the stacks thru separate pointers, to allow yyoverflow
       to reallocate them elsewhere.  */

    /* The state stack.  */
    yytype_int16 yyssa[YYINITDEPTH];
    yytype_int16 *yyss;
    yytype_int16 *yyssp;

    /* The semantic value stack.  */
    YYSTYPE yyvsa[YYINITDEPTH];
    YYSTYPE *yyvs;
    YYSTYPE *yyvsp;

    YYSIZE_T yystacksize;

  int yyn;
  int yyresult;
  /* Lookahead token as an internal (translated) token number.  */
  int yytoken;
  /* The variables used to return semantic value and location from the
     action routines.  */
  YYSTYPE yyval;

#if YYERROR_VERBOSE
  /* Buffer for error messages, and its allocated size.  */
  char yymsgbuf[128];
  char *yymsg = yymsgbuf;
  YYSIZE_T yymsg_alloc = sizeof yymsgbuf;
#endif

#define YYPOPSTACK(N)   (yyvsp -= (N), yyssp -= (N))

  /* The number of symbols on the RHS of the reduced rule.
     Keep to zero when no symbol should be popped.  */
  int yylen = 0;

  yytoken = 0;
  yyss = yyssa;
  yyvs = yyvsa;
  yystacksize = YYINITDEPTH;

  YYDPRINTF ((stderr, "Starting parse\n"));

  yystate = 0;
  yyerrstatus = 0;
  yynerrs = 0;
  yychar = YYEMPTY; /* Cause a token to be read.  */

  /* Initialize stack pointers.
     Waste one element of value and location stack
     so that they stay on the same level as the state stack.
     The wasted elements are never initialized.  */
  yyssp = yyss;
  yyvsp = yyvs;

  goto yysetstate;

/*------------------------------------------------------------.
| yynewstate -- Push a new state, which is found in yystate.  |
`------------------------------------------------------------*/
 yynewstate:
  /* In all cases, when you get here, the value and location stacks
     have just been pushed.  So pushing a state here evens the stacks.  */
  yyssp++;

 yysetstate:
  *yyssp = yystate;

  if (yyss + yystacksize - 1 <= yyssp)
    {
      /* Get the current used size of the three stacks, in elements.  */
      YYSIZE_T yysize = yyssp - yyss + 1;

#ifdef yyoverflow
      {
	/* Give user a chance to reallocate the stack.  Use copies of
	   these so that the &'s don't force the real ones into
	   memory.  */
	YYSTYPE *yyvs1 = yyvs;
	yytype_int16 *yyss1 = yyss;

	/* Each stack pointer address is followed by the size of the
	   data in use in that stack, in bytes.  This used to be a
	   conditional around just the two extra args, but that might
	   be undefined if yyoverflow is a macro.  */
	yyoverflow (YY_("memory exhausted"),
		    &yyss1, yysize * sizeof (*yyssp),
		    &yyvs1, yysize * sizeof (*yyvsp),
		    &yystacksize);

	yyss = yyss1;
	yyvs = yyvs1;
      }
#else /* no yyoverflow */
# ifndef YYSTACK_RELOCATE
      goto yyexhaustedlab;
# else
      /* Extend the stack our own way.  */
      if (YYMAXDEPTH <= yystacksize)
	goto yyexhaustedlab;
      yystacksize *= 2;
      if (YYMAXDEPTH < yystacksize)
	yystacksize = YYMAXDEPTH;

      {
	yytype_int16 *yyss1 = yyss;
	union yyalloc *yyptr =
	  (union yyalloc *) YYSTACK_ALLOC (YYSTACK_BYTES (yystacksize));
	if (! yyptr)
	  goto yyexhaustedlab;
	YYSTACK_RELOCATE (yyss_alloc, yyss);
	YYSTACK_RELOCATE (yyvs_alloc, yyvs);
#  undef YYSTACK_RELOCATE
	if (yyss1 != yyssa)
	  YYSTACK_FREE (yyss1);
      }
# endif
#endif /* no yyoverflow */

      yyssp = yyss + yysize - 1;
      yyvsp = yyvs + yysize - 1;

      YYDPRINTF ((stderr, "Stack size increased to %lu\n",
		  (unsigned long int) yystacksize));

      if (yyss + yystacksize - 1 <= yyssp)
	YYABORT;
    }

  YYDPRINTF ((stderr, "Entering state %d\n", yystate));

  if (yystate == YYFINAL)
    YYACCEPT;

  goto yybackup;

/*-----------.
| yybackup.  |
`-----------*/
yybackup:

  /* Do appropriate processing given the current state.  Read a
     lookahead token if we need one and don't already have one.  */

  /* First try to decide what to do without reference to lookahead token.  */
  yyn = yypact[yystate];
  if (yyn == YYPACT_NINF)
    goto yydefault;

  /* Not known => get a lookahead token if don't already have one.  */

  /* YYCHAR is either YYEMPTY or YYEOF or a valid lookahead symbol.  */
  if (yychar == YYEMPTY)
    {
      YYDPRINTF ((stderr, "Reading a token: "));
      yychar = YYLEX;
    }

  if (yychar <= YYEOF)
    {
      yychar = yytoken = YYEOF;
      YYDPRINTF ((stderr, "Now at end of input.\n"));
    }
  else
    {
      yytoken = YYTRANSLATE (yychar);
      YY_SYMBOL_PRINT ("Next token is", yytoken, &yylval, &yylloc);
    }

  /* If the proper action on seeing token YYTOKEN is to reduce or to
     detect an error, take that action.  */
  yyn += yytoken;
  if (yyn < 0 || YYLAST < yyn || yycheck[yyn] != yytoken)
    goto yydefault;
  yyn = yytable[yyn];
  if (yyn <= 0)
    {
      if (yyn == 0 || yyn == YYTABLE_NINF)
	goto yyerrlab;
      yyn = -yyn;
      goto yyreduce;
    }

  /* Count tokens shifted since error; after three, turn off error
     status.  */
  if (yyerrstatus)
    yyerrstatus--;

  /* Shift the lookahead token.  */
  YY_SYMBOL_PRINT ("Shifting", yytoken, &yylval, &yylloc);

  /* Discard the shifted token.  */
  yychar = YYEMPTY;

  yystate = yyn;
  *++yyvsp = yylval;

  goto yynewstate;


/*-----------------------------------------------------------.
| yydefault -- do the default action for the current state.  |
`-----------------------------------------------------------*/
yydefault:
  yyn = yydefact[yystate];
  if (yyn == 0)
    goto yyerrlab;
  goto yyreduce;


/*-----------------------------.
| yyreduce -- Do a reduction.  |
`-----------------------------*/
yyreduce:
  /* yyn is the number of a rule to reduce with.  */
  yylen = yyr2[yyn];

  /* If YYLEN is nonzero, implement the default value of the action:
     `$$ = $1'.

     Otherwise, the following line sets YYVAL to garbage.
     This behavior is undocumented and Bison
     users should not rely upon it.  Assigning to YYVAL
     unconditionally makes the parser a bit smaller, and it avoids a
     GCC warning that YYVAL may be used uninitialized.  */
  yyval = yyvsp[1-yylen];


  YY_REDUCE_PRINT (yyn);
  switch (yyn)
    {
      

/* Line 1455 of yacc.c  */
#line 3095 "c.tab.c"
      default: break;
    }
  YY_SYMBOL_PRINT ("-> $$ =", yyr1[yyn], &yyval, &yyloc);

  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);

  *++yyvsp = yyval;

  /* Now `shift' the result of the reduction.  Determine what state
     that goes to, based on the state we popped back to and the rule
     number reduced by.  */

  yyn = yyr1[yyn];

  yystate = yypgoto[yyn - YYNTOKENS] + *yyssp;
  if (0 <= yystate && yystate <= YYLAST && yycheck[yystate] == *yyssp)
    yystate = yytable[yystate];
  else
    yystate = yydefgoto[yyn - YYNTOKENS];

  goto yynewstate;


/*------------------------------------.
| yyerrlab -- here on detecting error |
`------------------------------------*/
yyerrlab:
  /* If not already recovering from an error, report this error.  */
  if (!yyerrstatus)
    {
      ++yynerrs;
#if ! YYERROR_VERBOSE
      yyerror (YY_("syntax error"));
#else
      {
	YYSIZE_T yysize = yysyntax_error (0, yystate, yychar);
	if (yymsg_alloc < yysize && yymsg_alloc < YYSTACK_ALLOC_MAXIMUM)
	  {
	    YYSIZE_T yyalloc = 2 * yysize;
	    if (! (yysize <= yyalloc && yyalloc <= YYSTACK_ALLOC_MAXIMUM))
	      yyalloc = YYSTACK_ALLOC_MAXIMUM;
	    if (yymsg != yymsgbuf)
	      YYSTACK_FREE (yymsg);
	    yymsg = (char *) YYSTACK_ALLOC (yyalloc);
	    if (yymsg)
	      yymsg_alloc = yyalloc;
	    else
	      {
		yymsg = yymsgbuf;
		yymsg_alloc = sizeof yymsgbuf;
	      }
	  }

	if (0 < yysize && yysize <= yymsg_alloc)
	  {
	    (void) yysyntax_error (yymsg, yystate, yychar);
	    yyerror (yymsg);
	  }
	else
	  {
	    yyerror (YY_("syntax error"));
	    if (yysize != 0)
	      goto yyexhaustedlab;
	  }
      }
#endif
    }



  if (yyerrstatus == 3)
    {
      /* If just tried and failed to reuse lookahead token after an
	 error, discard it.  */

      if (yychar <= YYEOF)
	{
	  /* Return failure if at end of input.  */
	  if (yychar == YYEOF)
	    YYABORT;
	}
      else
	{
	  yydestruct ("Error: discarding",
		      yytoken, &yylval);
	  yychar = YYEMPTY;
	}
    }

  /* Else will try to reuse lookahead token after shifting the error
     token.  */
  goto yyerrlab1;


/*---------------------------------------------------.
| yyerrorlab -- error raised explicitly by YYERROR.  |
`---------------------------------------------------*/
yyerrorlab:

  /* Pacify compilers like GCC when the user code never invokes
     YYERROR and the label yyerrorlab therefore never appears in user
     code.  */
  if (/*CONSTCOND*/ 0)
     goto yyerrorlab;

  /* Do not reclaim the symbols of the rule which action triggered
     this YYERROR.  */
  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);
  yystate = *yyssp;
  goto yyerrlab1;


/*-------------------------------------------------------------.
| yyerrlab1 -- common code for both syntax error and YYERROR.  |
`-------------------------------------------------------------*/
yyerrlab1:
  yyerrstatus = 3;	/* Each real token shifted decrements this.  */

  for (;;)
    {
      yyn = yypact[yystate];
      if (yyn != YYPACT_NINF)
	{
	  yyn += YYTERROR;
	  if (0 <= yyn && yyn <= YYLAST && yycheck[yyn] == YYTERROR)
	    {
	      yyn = yytable[yyn];
	      if (0 < yyn)
		break;
	    }
	}

      /* Pop the current state because it cannot handle the error token.  */
      if (yyssp == yyss)
	YYABORT;


      yydestruct ("Error: popping",
		  yystos[yystate], yyvsp);
      YYPOPSTACK (1);
      yystate = *yyssp;
      YY_STACK_PRINT (yyss, yyssp);
    }

  *++yyvsp = yylval;


  /* Shift the error token.  */
  YY_SYMBOL_PRINT ("Shifting", yystos[yyn], yyvsp, yylsp);

  yystate = yyn;
  goto yynewstate;


/*-------------------------------------.
| yyacceptlab -- YYACCEPT comes here.  |
`-------------------------------------*/
yyacceptlab:
  yyresult = 0;
  goto yyreturn;

/*-----------------------------------.
| yyabortlab -- YYABORT comes here.  |
`-----------------------------------*/
yyabortlab:
  yyresult = 1;
  goto yyreturn;

#if !defined(yyoverflow) || YYERROR_VERBOSE
/*-------------------------------------------------.
| yyexhaustedlab -- memory exhaustion comes here.  |
`-------------------------------------------------*/
yyexhaustedlab:
  yyerror (YY_("memory exhausted"));
  yyresult = 2;
  /* Fall through.  */
#endif

yyreturn:
  if (yychar != YYEMPTY)
     yydestruct ("Cleanup: discarding lookahead",
		 yytoken, &yylval);
  /* Do not reclaim the symbols of the rule which action triggered
     this YYABORT or YYACCEPT.  */
  YYPOPSTACK (yylen);
  YY_STACK_PRINT (yyss, yyssp);
  while (yyssp != yyss)
    {
      yydestruct ("Cleanup: popping",
		  yystos[*yyssp], yyvsp);
      YYPOPSTACK (1);
    }
#ifndef yyoverflow
  if (yyss != yyssa)
    YYSTACK_FREE (yyss);
#endif
#if YYERROR_VERBOSE
  if (yymsg != yymsgbuf)
    YYSTACK_FREE (yymsg);
#endif
  /* Make sure YYID is used.  */
  return YYID (yyresult);
}



