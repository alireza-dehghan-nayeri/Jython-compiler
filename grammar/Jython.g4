grammar Jython;

program : importclass* (classDef)+ ;

importclass : IMPORT CLASSNAME ;

classDef : CLASS CLASSNAME (LPAREN CLASSNAME (COMMA CLASSNAME)* RPAREN)? LBRACE class_body* RBRACE;

class_body
    : varDec
    | methodDec
    | constructor
    | arrayDec ;

varDec : (TYPE | CLASSNAME) ID ;

arrayDec : (TYPE | CLASSNAME) LBRACK INTEGER RBRACK ID ;

methodDec:DEF ((TYPE | CLASSNAME) | VOID) ID LPAREN parameter* RPAREN LBRACE (statement)* RBRACE ;

constructor : DEF (TYPE | CLASSNAME) LPAREN parameter* RPAREN LBRACE ( statement)* RBRACE ;

parameter : varDec (COMMA varDec)* ;

statement
    : varDec
    | assignment
    | print_statment
    | method_call
    | return_statment
    | if_statment
    | while_statment
    | if_else_statment
    | for_statment;

return_statment : RETURN exp ;

condition_list : condition ((OR|AND) condition)* ;

condition
    : BOOL
    | prefixexp
    | (exp) relational_operators (exp);

if_statment : IF LPAREN condition_list RPAREN LBRACE statement* RBRACE;

while_statment : WHILE LPAREN condition_list RPAREN LBRACE statement* RBRACE ;

if_else_statment :IF LPAREN condition_list RPAREN LBRACE statement* RBRACE (ELIF LPAREN condition_list RPAREN LBRACE statement* RBRACE)* ELSE LBRACE statement* RBRACE ;

print_statment : PRINT LPAREN (prefixexp | TYPE args | INTEGER |STRING | BOOL) RPAREN ;

for_statment
    : FOR ID IN ID LBRACE statement* RBRACE
    | FOR ID IN RANGE LPAREN INTEGER (COMMA INTEGER)? (COMMA INTEGER)? RPAREN LBRACE statement* RBRACE ;

method_call : ID DOT? args ;

assignment
    : prefixexp assignment_operators exp
    | varDec assignment_operators exp
    | arrayDec EQUAL (TYPE | CLASSNAME) args (LBRACK INTEGER RBRACK) ;

exp
    : NONE
    | BOOL
    | INTEGER
    | STRING
    | FLOAT
    | prefixexp
    | exp arithmetic_operator exp
    | (TYPE | CLASSNAME) args
    | LPAREN exp RPAREN
    | ID args;

prefixexp
    : ID
    | prefixexp LBRACK INTEGER RBRACK
    | prefixexp DOT ID
    | prefixexp DOT ID args;

args : LPAREN (explist)? RPAREN ;

explist : exp (COMMA exp)*;

arithmetic_operator
    : ADD
    | SUB
    | MUL
    | DIV
    | MOD
    ;

relational_operators
    : LT
    | GT
    | LE
    | GE
    | EQUAL
    | NOTEQUAL
    ;

assignment_operators
    : ASSIGN
    | ADD_ASSIGN
    | SUB_ASSIGN
    | MUL_ASSIGN
    | DIV_ASSIGN
    ;

TYPE: INT | FLOATING_POINT | STRING | BOOL;
INT:                'int';
FLOATING_POINT:     'float';
STRING:             'string';
BOOL:               'bool';
CLASS:              'class';
IMPORT:             'import';
DEF:                'def';
VOID:               'void';
RETURN:             'return';
AND:                'and';
OR:                 'or';
IF:                 'if';
WHILE:              'while';
ELIF:               'elif';
ELSE:               'else';
PRINT:              'print';
FOR:                'for';
IN:                 'in';
RANGE:              'range';
NONE:               'none';

// Separators
LPAREN:             '(';
RPAREN:             ')';
LBRACE:             '{';
RBRACE:             '}';
LBRACK:             '[';
RBRACK:             ']';
SEMI:               ';';
COMMA:              ',';
DOT:                '.';

// Operators
ASSIGN:             '=';
GT:                 '>';
LT:                 '<';
BANG:               '!';
TILDE:              '~';
QUESTION:           '?';
COLON:              ':';
EQUAL:              '==';
LE:                 '<=';
GE:                 '>=';
NOTEQUAL:           '!=';
INC:                '++';
DEC:                '--';
ADD:                '+';
SUB:                '-';
MUL:                '*';
DIV:                '/';
BITAND:             '&';
BITOR:              '|';
CARET:              '^';
MOD:                '%';
ADD_ASSIGN:         '+=';
SUB_ASSIGN:         '-=';
MUL_ASSIGN:         '*=';
DIV_ASSIGN:         '/=';
AND_ASSIGN:         '&=';
OR_ASSIGN:          '|=';
XOR_ASSIGN:         '^=';
MOD_ASSIGN:         '%=';
LSHIFT_ASSIGN:      '<<=';
RSHIFT_ASSIGN:      '>>=';
URSHIFT_ASSIGN:     '>>>=';

CLASSNAME: [A-Z] (LETTER|DIGIT)*;
ID: [a-z]([A-Za-z_])*;
LETTER: [A-Za-z];
INTEGER: DIGIT+;
FLOAT: (DIGIT)+ '.' (DIGIT)+;
DIGIT: [0-9];

OPEN_COMMENT :      '#*' ;
CLOSE_COMMENT :     '*#' ;
COMMENT : OPEN_COMMENT (COMMENT | .)*? CLOSE_COMMENT -> skip ;
ONE_LINE_COMMENT :  '#' (~ '\n')* '\n'? -> skip ;

WHITESPACE : [ \t\r\n\f]+ -> skip ;





