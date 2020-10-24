%{
	#include <stdio.h>
	#include "P1.tab.h"
	#include <string.h>
%}

digit [0-9]
defin "#define"
clas "class"
lengt "length"
ou "out"
syste "System"
tru "true"
in "int"
thi "this"
ne "new"
fals "false"
integer (0|([1-9]{digit}*))
space "\t"|"\n"|"\r"|"\f"|" "
equals "="
/*******have to also include digits in identifier************/
identifier [a-zA-Z_$][a-zA-Z0-9_$]*
%%
"//".* {;}
"+" {return Plus;}
"(" {return Opbrac;}
")" {return Cpbrac;}
"." {return Dot;}
{defin} {return Define;}
{clas} {return Class;}
{syste} {return System;}
"public" {return Public;}
"static" {return Static;}
"void" {return Void;}
{lengt} {return Length;}
"main" {return MAIN;}
{ou} {return Out;}
"-" {return Minus;}
"<" {return Less;}
";" {return End;}
{equals} {return Equal;}
"*" {return Mult;}
"&&" {return AndAnd;}
"||" {return Or;}
"println" {return Println;}
"{" {return Ocbrac;}
"}" {return Ccbrac;}
"if" {return If;}
"else" {return Else;}
"while" {return While;}
"[" {return Orbrac;}
"]" {return Crbrac;}
"," {return Comma;}
"extends" {return Extends;}
"boolean" {return Boo;}
"String" {return String;}
"return" {return Return;}
"!=" {return NotEq;}
"/" {return Div;}
{tru} {return True;}
{in} {return Wint;}
{thi} {return This;}
{ne} {return New;}
"!" {return Excl;}
{fals} {return False;}
{space} {;}
{integer} {yylval.str = strdup(yytext);return Integer ;}
{identifier} {yylval.str = strdup(yytext) ;return Identifier ;}
%%
/************** change the strdup ********************/
/* main()
{
  printf("Enter the input:\n");
  yylex();
	bison -d dum.y
	flex dum.l
	gcc dum.tab.c lex.yy.c -lfl -o dum
	./dum
}*/
