%{
    #include <math.h>
    #include <stdio.h>
    #define YYSTYPE double
    #include "lex.yy.c"
    double variable[52];
    int    ser; 
%}  

%token VAR NUMBER EQUAL PLUS MINUS TIMES DIVIDE LP RP DELIM EXP LOG
%%
cmd: result | result DELIM cmd
    ;
result: L1 EQUAL	 {printf("result: %lf\n",$1);}
    |L1 DELIM EQUAL	 {printf("result: %lf\n",$1);}
    |VAR EQUAL L1    {
                        variable[ser] = $3;
                        if (ser <= 'z')
                            printf("define: %c = %lf\n",(char)(ser+'a'),$3);
                        else
                            printf("define: %c = %lf\n",(char)(ser-27+'A'),$3);
                     }
    ;
L1:  L1 PLUS  L2 	 {$$ = $1 + $3;}
    |L1 MINUS L2	 {$$ = $1 - $3;}
    |L2 		     {$$ = $1;}
    ;
L2:  L2 TIMES  L3  	 {$$ = $1 * $3;}
    |L2 DIVIDE L3 	 {$$ = $1 / $3;}
    |L3 		     {$$ = $1;}
    ;
L3:  L3 EXP L4	  	 {$$ = pow($1, $3);}
    |L4		 	     {$$ = $1;}
    ;
L4:  NUMBER 	 	 {$$ = $1;}
    |LP L1 RP 	 	 {$$ = $2;}
    |LOG LP L1 RP	 {$$ = log($3);}
    |VAR             {$$ = variable[ser];}
    ;
%%
int main()
{
    for (int i = 0; i < 26; ++i)
        variable[i] = 0;
    return yyparse();
}
int yyerror(char* s)
{
    fprintf(stderr,"%s",s);
    return 1;
}
int yywrap()
{
    return 1;
}
