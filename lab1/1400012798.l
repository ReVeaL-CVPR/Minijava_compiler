%{
    #include <stdlib.h>
    #include <stdio.h>
    #include "y.tab.h"
    extern double   yylval;
    extern int      ser;
%}

number              [0-9]
standard_real       ([1-9]{number}*|0)(\.{number}*)?
unstandard_real     0{number}+(\.{number}*)?
letter              [a-zA-Z]
multiletter         {letter}{letter}+
plus                "+"
minus               "-"
log 	            "ln"
exp	                "**"
times               "*"
divide              "/"
lp                  "("
rp 	                ")"
equ	                "="
delim               ([ \n\t\;])*
%%

{plus}              {return PLUS;}
{log}               {return LOG;}
{exp}               {return EXP;}
{minus}             {return MINUS;}
{times}             {return TIMES;}
{divide}            {return DIVIDE;}
{lp}                {return LP;}
{rp}                {return RP;}
{delim}             {return DELIM;}
{equ}               {return EQUAL;}
{standard_real}     {
                        yylval = (double)atof(yytext); 
                        return NUMBER;
                    }
{unstandard_real}   {
                        printf("WARNING: %s unstandard input\n", yytext);
                        yylval = (double)atof(yytext);
                        return NUMBER;
                    }
{letter}            {
                        char c = yytext[0];
                        if (c <= 'z')
                            ser = (int)(yytext[0] - 'a');
                        else
                            ser = (int)(yytext[0] - 'A' + 27);
                        return VAR;
                    }
{multiletter}       {printf("WARNING: $s Do not support multiletter input\n", yytext);}
.		            {printf("Cannot Recongnize It\n");}
