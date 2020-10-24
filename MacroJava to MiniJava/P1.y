%{
	#include <stdio.h>
	#include <string.h>
	#include<stdlib.h>
	#include <stdbool.h>
	int yyerror ();
	int yylex(void);

	struct mapel
	{
			char* a;
			char* b;
			int num_args;
			char** args;
	};

	int num_toks(char* argvals)
	{
		char* dup = (char*)calloc((strlen(argvals)+10),sizeof(char));
		strcpy(dup,argvals);
		char *p;
	  p = strtok (dup,",");
		int i = 0;
	  while (p!= NULL)
	  {
	    p = strtok (NULL, ",");
			i++;
	  }
		/* printf("%d\n",i); */
		return i;
	}

	char** arg_toks(char* argvals,int n)
	{
		char* dup = (char*)calloc((strlen(argvals)+10),sizeof(char));
		strcpy(dup,argvals);
		char** res=(char**)malloc((n+2)*sizeof(char*));

		char *p;
	  p = strtok(dup,",");
		int i = 0;
	  while (p!= NULL)
	  {
	    res[i] = p;
			/* printf("%s\n",p); */
	    p = strtok (NULL, ",");
			i++;
	  }
		return res;
	}

	int num_elem = 0;
	struct mapel map[5000];

	void addElem(char* macro,char* val,char* argvals)
	{
			int i = 0;
			while(i < num_elem)
			{
				if(strcmp(macro,map[i].a) == 0)
				{
					break;
				}
				i++;
			}
			map[i].a = macro;
			map[i].b = val;
			if(argvals == NULL)
			{
				map[i].num_args = 0;
				map[i].args = NULL;
			}
			else
			{
				map[i].num_args = num_toks(argvals);
				map[i].args = arg_toks(argvals,map[i].num_args);
			}
			/* printf("%s\n %s\n",map[0].args[0],map[0].args[1]); */

			num_elem++;
	}

	int check(char c)
	{
		if((c < 'a' || c >'z') && (c < 'A' || c > 'Z') && (c < '0' || c > '9') && (c != '_' && c != '$'))
			return 1;
		else
			return 0;
	}

	char* retElem(char* macro,char* argvals)
	{
		int i = 0;

		while(i < num_elem)
		{
			if(strcmp(macro,map[i].a) == 0)
			{
				/* printf("%s\n",map[i].a); */

				if(argvals == NULL)
				{
					if(map[i].num_args != 0)
					{
						yyerror();
						exit(0);
					}
					else
					{
						/* char* res = (char*)calloc(strlen(map[i].b),sizeof(char*));
						strcpy(res,map[i].b); */
						return map[i].b;
					}
				}
				else
				{

					int n = num_toks(argvals);
					char** rep_args = arg_toks(argvals,n);
					if(n == map[i].num_args)
					{
						int x;
						int y;

						char* res = (char*)calloc(50000,sizeof(char*));
						res[0] = '\0';
						int p = 0;

						char* c;
						int z = 0;


						char* org = map[i].b;
						c = (char*)calloc(strlen(map[i].b)+15,sizeof(char*));
						c[0] = '\0';

						for(x = 0 ;x < strlen(map[i].b);x++)
						{
							if(check(org[x]) == 0)
							{
								c[z] = org[x];
								z++;
							}
							else
							{
								c[z] = '\0';
								/* printf("%s\n",c); */

								for(y = 0; y < n;y++)
								{
									if(strcmp(map[i].args[y],c) == 0)
									{
											strcat(res,rep_args[y]);
											p += strlen(rep_args[y]);
											c[0] = '\0';
											z = 0;
											break;
									}
								}
								c[z]='\0';
								z = 0;
								strcat(res,c);
								p += strlen(c);
								res[p] = org[x];
								p++;
								res[p] = '\0';
							}
						}
						c[z] = '\0';
						/* printf("%s\n",c); */

						for(y = 0; y < n;y++)
						{
							if(strcmp(map[i].args[y],c) == 0)
							{
									strcat(res,rep_args[y]);
									p += strlen(rep_args[y]);
									c[0] = '\0';
									z = 0;
									break;
							}
						}
						c[z]='\0';
						z = 0;
						strcat(res,c);
						p += strlen(c);
						res[p] = org[x];
						p++;
						res[p] = '\0';

						return res;
					}
					else
					{
						yyerror();
						exit(0);
					}
				}

				}

				i++;
			}

			yyerror();
			exit(0);
		}
%}
%union
{
	char* str;
}

%start goal;

%type <str> CommaExpressionStar MacroDefinition MacroDefStatement MethodDeclarationStar MacroDefExpression CommaIdentifierStar MacroDefinitionStar TypeDeclarationStar TypeDeclaration CommaTypeIdentifierStar TypeIdentifierStar MethodDeclaration MainClass Type Identifier Integer goal Expression PrimaryExpression Statement StatementStar

%token Return Comma Extends Boo While Else If Ocbrac Ccbrac Println Out System Length Class Public Static Void MAIN True False String This New Wint Excl Dot Less Or NotEq Orbrac Crbrac AndAnd Integer Plus Minus Opbrac Cpbrac Define End Div Mult Equal Identifier
%%

goal : MacroDefinitionStar MainClass TypeDeclarationStar
{
	$$ = (char*)calloc((strlen($2)+strlen($3)+10),sizeof(char));
	strcpy($$,$2);
	strcat($$,"\n");
	strcat($$,$3);
	printf("%s",$$);
};

MacroDefinitionStar : MacroDefinitionStar MacroDefinition
{
	$$  = "hi";
}
|
{
	$$ = "hi";
};
MacroDefinition : MacroDefExpression
{

}
| 	MacroDefStatement
{

};

MacroDefStatement : Define Identifier Opbrac Cpbrac Ocbrac StatementStar Ccbrac
{
	addElem($2,$6,NULL);
}
| Define Identifier Opbrac Identifier CommaIdentifierStar Cpbrac Ocbrac StatementStar Ccbrac
{
	strcat($4,$5);
	addElem($2,$8,$4);
}

MacroDefExpression : Define Identifier Opbrac Cpbrac Opbrac Expression Cpbrac
{
	addElem($2,$6,NULL);
}
| Define Identifier Opbrac Identifier CommaIdentifierStar Cpbrac Opbrac Expression Cpbrac
{
	strcat($4,$5);
	/* printf("%s\n",$4); */
	addElem($2,$8,$4);
};


TypeDeclarationStar : TypeDeclaration TypeDeclarationStar
{
	$$ = (char*)calloc((strlen($2)+strlen($1)+20),sizeof(char));
	strcpy($$,$1);
	strcat($$,"\n");
	strcat($$,$2);
}
|
{
	$$ = (char*)calloc(10,sizeof(char));
	strcpy($$,"");
};

TypeDeclaration : Class Identifier Ocbrac TypeIdentifierStar MethodDeclarationStar Ccbrac
{
	$$ = (char*)calloc((strlen($2)+strlen($5)+strlen($4) + 20),sizeof(char));
	strcpy($$,"class ");
	strcat($$,$2);
	strcat($$,"{\n");
	strcat($$,$4);
	strcat($$,$5);
	strcat($$,"\n}\n");
}
| Class Identifier Extends Identifier Ocbrac TypeIdentifierStar MethodDeclarationStar Ccbrac
{
	$$ = (char*)calloc((strlen($2)+strlen($4)+strlen($7)+strlen($6) + 35),sizeof(char));
	strcpy($$,"class ");
	strcat($$,$2);
	strcat($$," extends ");
	strcat($$,$4);
	strcat($$,"{\n");
	strcat($$,$6);
	strcat($$,$7);
	strcat($$,"}\n");
};

MethodDeclarationStar : MethodDeclaration MethodDeclarationStar
{
	$$ = (char*)calloc((strlen($2)+strlen($1)+20),sizeof(char));
	strcpy($$,$1);
	strcat($$,"\n");
	strcat($$,$2);
}
|
{
	$$ = (char*)calloc(10,sizeof(char));
	strcpy($$,"");
};

MethodDeclaration : Public Type Identifier Opbrac Cpbrac Ocbrac TypeIdentifierStar StatementStar Return Expression End Ccbrac
{
	$$ = (char*)calloc((strlen($2)+strlen($3)+strlen($7)+strlen($8)+strlen($10)+150),sizeof(char));
	strcpy($$,"public ");
	strcat($$,$2);
	strcat($$," ");
	strcat($$,$3);
	strcat($$,"()\n{\n");
	strcat($$,$7);
	strcat($$,$8);
	strcat($$,"return ");
	strcat($$,$10);
	strcat($$,";\n}\n");
}
| Public Type Identifier Opbrac Type Identifier CommaTypeIdentifierStar Cpbrac Ocbrac TypeIdentifierStar StatementStar Return Expression End Ccbrac
{
	$$ = (char*)calloc((strlen($2)+strlen($3)+strlen($5)+strlen($6)+strlen($7)+strlen($10)+strlen($11)+strlen($13)+150),sizeof(char));
	strcpy($$,"public ");
	strcat($$,$2);
	strcat($$," ");
	strcat($$,$3);
	strcat($$,"(");
	strcat($$,$5);
	strcat($$," ");
	strcat($$,$6);
	strcat($$,$7);
	strcat($$,")\n{\n");
	strcat($$,$10);
	strcat($$,$11);
	strcat($$,"return ");
	strcat($$,$13);
	strcat($$,";\n}\n");
	};

CommaTypeIdentifierStar : Comma Type Identifier CommaTypeIdentifierStar
{
	$$ = (char*)calloc((strlen($2)+strlen($3)+strlen($4)+20),sizeof(char));
	strcpy($$,",");
	strcat($$,$2);
	strcat($$," ");
	strcat($$,$3);
	strcat($$,$4);
}
|
{
	$$ = (char*)calloc(10,sizeof(char));
	strcpy($$,"");
};

TypeIdentifierStar :   TypeIdentifierStar Type Identifier End
{
	$$ = (char*)calloc((strlen($1)+strlen($2)+strlen($3)+20),sizeof(char));
	strcpy($$,$1);
	strcat($$,$2);
	strcat($$," ");
	strcat($$,$3);
	strcat($$,";\n");
}
|
{
	$$ = (char*)calloc(10,sizeof(char));
	strcpy($$,"");
};

CommaIdentifierStar : Comma Identifier CommaIdentifierStar
{
	$$ = (char*)calloc((strlen($3)+strlen($2)),sizeof(char));
	strcpy($$,",");
	strcat($$,$2);
	strcat($$,$3);
}
|
{
	$$ = (char*)calloc(10,sizeof(char));
	strcpy($$,"");
};
/* Class Identifier Ocbrac Public Static Void MAIN Opbrac String Orbrac Crbrac Identifier Cpbrac Ocbrac System Dot Out Dot Println Opbrac Expression Cpbrac End Ccbrac Ccbrac */
MainClass : Class Identifier Ocbrac Public Static Void MAIN Opbrac String Orbrac Crbrac Identifier Cpbrac Ocbrac System Dot Out Dot Println Opbrac Expression Cpbrac End Ccbrac Ccbrac
{
	$$ = (char*)calloc((strlen($2)+strlen($12)+strlen($21)+100),sizeof(char));
	strcpy($$,"class ");
	strcat($$,$2);
	strcat($$,"{\n public static void main (String[]");
	strcat($$,$12);
	strcat($$,"){\n System.out.println(");
	strcat($$,$21);
	strcat($$,");\n}\n}");
};

Type : Wint Orbrac Crbrac
{
	$$ = (char*)calloc(10,sizeof(char));
	strcpy($$,"int[]");
}
| Boo
{
	$$ = (char*)calloc(15,sizeof(char));
	strcpy($$,"boolean");
}
| Wint
{
	$$ = (char*)calloc(15,sizeof(char));
	strcpy($$,"int");
}
| Identifier
{
	$$ = (char*)calloc(strlen($1),sizeof(char));
	strcpy($$,$1);
}

StatementStar : Statement StatementStar
{
	$$ = (char*)calloc((strlen($1)+strlen($2)+10),sizeof(char));
	strcpy($$,$1);
	strcat($$,"\n");
	strcat($$,$2);
}
|
{
	$$ = (char*)calloc(10,sizeof(char));
	strcpy($$,"");
};

Statement : Ocbrac StatementStar Ccbrac
{
	$$ = (char*)calloc((strlen($2)+10),sizeof(char));
	strcpy($$,"{\n");
	strcat($$,$2);
	strcat($$,"\n}");

}
| System Dot Out Dot Println Opbrac Expression Cpbrac End
{
	$$ = (char*)calloc((strlen($7)+40),sizeof(char));
	strcpy($$,"System.out.println(");
	strcat($$,$7);
	strcat($$,");");
}
|		Identifier Equal Expression End
{
	$$ = (char*)calloc((strlen($1)+strlen($3)+10),sizeof(char));
	strcpy($$,$1);
	strcat($$," = ");
	strcat($$,$3);
	strcat($$,";");
}
| Identifier Orbrac Expression Crbrac Equal Expression End
{
	$$ = (char*)calloc((strlen($1)+strlen($3)+strlen($6)+10),sizeof(char));
	strcpy($$,$1);
	strcat($$,"[");
	strcat($$,$3);
	strcat($$,"] = ");
	strcat($$,$6);
	strcat($$,";");
}
| If Opbrac Expression Cpbrac Statement
{
	$$ = (char*)calloc((strlen($3)+strlen($5)+20),sizeof(char));
	strcpy($$,"if(");
	strcat($$,$3);
	strcat($$,")\n");
	strcat($$,$5);
}
| If Opbrac Expression Cpbrac Statement Else Statement
{
	$$ = (char*)calloc((strlen($3)+strlen($5)+strlen($7)+30),sizeof(char));
	strcpy($$,"if(");
	strcat($$,$3);
	strcat($$,")\n");
	strcat($$,$5);
	strcat($$,"\n");
	strcat($$,"else\n");
	strcat($$,$7);
	strcat($$,"\n");
}
| While Opbrac Expression Cpbrac Statement
{
	$$ = (char*)calloc((strlen($3)+strlen($5)+30),sizeof(char));
	strcpy($$,"while(");
	strcat($$,$3);
	strcat($$,")\n");
	strcat($$,$5);
}
//Macro
| Identifier Opbrac Expression CommaExpressionStar Cpbrac End
{
	strcat($3,$4);

	$$ = retElem($1,$3);

	strcat($$,"\n");

}
//Macro
| Identifier Opbrac Cpbrac End
{
	$$ = retElem($1,NULL);
	strcat($$,"\n");
};

Expression : PrimaryExpression
{
	$$ = (char*)calloc((strlen($1)+10),sizeof(char));
	strcpy($$,$1);
}

| PrimaryExpression AndAnd PrimaryExpression
{
	$$ = (char*)calloc((strlen($1)+ strlen($3)+12),sizeof(char));
	strcpy($$,$1);
	strcat($$,"&&");
	strcat($$,$3);
}
| PrimaryExpression NotEq PrimaryExpression
{
	$$ = (char*)calloc((strlen($1)+ strlen($3)+12),sizeof(char));
	strcpy($$,$1);
	strcat($$,"!=");
	strcat($$,$3);
}
| PrimaryExpression Or PrimaryExpression
{
	$$ = (char*)calloc((strlen($1)+ strlen($3)+12),sizeof(char));
	strcpy($$,$1);
	strcat($$,"||");
	strcat($$,$3);
}
| PrimaryExpression Less Equal PrimaryExpression
{
	$$ = (char*)calloc((strlen($1)+ strlen($4)+12),sizeof(char));
	strcpy($$,$1);
	strcat($$,"<=");
	strcat($$,$4);
}
| PrimaryExpression Plus PrimaryExpression
{
	$$ = (char*)calloc((strlen($1)+ strlen($3)+12),sizeof(char));
	strcpy($$,$1);
	strcat($$,"+");
	strcat($$,$3);
}
| PrimaryExpression Minus PrimaryExpression
{
	$$ = (char*)calloc((strlen($1)+ strlen($3)+12),sizeof(char));
	strcpy($$,$1);
	strcat($$,"-");
	strcat($$,$3);
}
| PrimaryExpression Mult PrimaryExpression
{
	$$ = (char*)calloc((strlen($1)+ strlen($3)+12),sizeof(char));
	strcpy($$,$1);
	strcat($$,"*");
	strcat($$,$3);
}
| PrimaryExpression Div PrimaryExpression
{
	$$ = (char*)calloc((strlen($1)+ strlen($3)+12),sizeof(char));
	strcpy($$,$1);
	strcat($$,"/");
	strcat($$,$3);
}
| PrimaryExpression Orbrac PrimaryExpression Crbrac
{
	$$ = (char*)calloc((strlen($1)+ strlen($3)+12),sizeof(char));
	strcpy($$,$1);
	strcat($$," [");
	strcat($$,$3);
	strcat($$,"]");
}
| PrimaryExpression Dot Length
{
	$$ = (char*)calloc((strlen($1)+20),sizeof(char));
	strcpy($$,$1);
	strcat($$,".");
	strcat($$,"length");
}
//Macro
| Identifier Opbrac Expression CommaExpressionStar Cpbrac
{
	strcat($3,$4);
	$$ = retElem($1,$3);
	/* printf("%s\n",$3); */

}
//Macro
| Identifier Opbrac Cpbrac
{
	$$ = retElem($1,NULL);
}
| PrimaryExpression Dot Identifier Opbrac Expression CommaExpressionStar Cpbrac
{
	$$ = (char*)calloc((strlen($3)+strlen($1)+strlen($5)+strlen($6)+20),sizeof(char));
	strcpy($$,$1);
	strcat($$,".");
	strcat($$,$3);
	strcat($$,"(");
	strcat($$,$5);
	strcat($$,$6);
	strcat($$,")");
}
| PrimaryExpression Dot Identifier Opbrac Cpbrac
{
	$$ = (char*)calloc((strlen($3)+strlen($1)+20),sizeof(char));
	strcpy($$,$1);
	strcat($$,".");
	strcat($$,$3);
	strcat($$,"()");
};

CommaExpressionStar : Comma Expression CommaExpressionStar
{
	$$ = (char*)calloc((strlen($3)+strlen($2)+20),sizeof(char));
	strcpy($$,",");
	strcat($$,$2);
	strcat($$,$3);
}
|
{
	$$ = (char*)calloc(10,sizeof(char));
	strcpy($$,"");
};

/* Negative Number Please */

PrimaryExpression : Integer
{
	$$ = (char*)calloc((strlen($1)+10),sizeof(char));
	strcpy($$,$1);
}

| Identifier
{
	$$ = (char*)calloc((strlen($1)+10),sizeof(char));
	strcpy($$,$1);
}

| True
{
	$$ = (char*)calloc((10),sizeof(char));
	strcpy($$,"true");
}
| False
{
	$$ = (char*)calloc((10),sizeof(char));
	strcpy($$,"false");
}
| This
{
	$$ = (char*)calloc((10),sizeof(char));
	strcpy($$,"this");
}
| New Wint Orbrac Expression Crbrac
{
	$$ = (char*)calloc((strlen($4) + 25),sizeof(char));
	strcpy($$,"new");
	strcat($$," int");
	strcat($$,"[");
	strcat($$,$4);
	strcat($$,"]");
}
| New Identifier Opbrac Cpbrac
{
	$$ = (char*)calloc((strlen($2) + 25),sizeof(char));
	strcpy($$,"new ");
	strcat($$,$2);
	strcat($$,"(");
	strcat($$,")");
	free($2);
}
| Excl Expression
{
	$$ = (char*)calloc((strlen($2)+ 5),sizeof(char));
	strcpy($$,"!");
	strcat($$,$2);
}
| Opbrac Expression Cpbrac
{
	$$ = (char*)calloc((strlen($2)+ 25),sizeof(char));
	strcpy($$,"(");
	strcat($$,$2);
	strcat($$,")");
};
%%

int yyerror()
{
	printf ("// Failed to parse macrojava code.");
	return 0;
}
int main ()
{
	yyparse();
	return 0;
}
