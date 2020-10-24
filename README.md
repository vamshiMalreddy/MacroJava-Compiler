#**MacroJava-Compiler**

Through six phases, we designed a compiler for subset of Java called **MacroJava** using Flex, Bison and JavaCC in C and Java :
> 1. **Macro Expansion** : Converting MacroJava to MiniJava
> 2. **Syntactic and Semantic Analysis** : Type Checking for MiniJava
> 3. **Intermediate Code Generation** : Converting MiniJava to MiniIR
> 4. **Simplified Intermediate Code Generation** : Converting MiniIR to MicroIR
> 5. **Register Allocation** : Converting MicroIR to MiniRA
> 6. **Machine Level Code Generation** : Converting MiniRA to MIPS

The first part uses Flex and Bison
The remaining parts use javacc and jtb

> For details refer: http://www.cse.iitm.ac.in/~krishna/cs3300

###**Instructions for Execution:** 

Install javacc on Ubuntu

	On Ubuntu
	apt-get install javacc

Find the required jj file in the "jj files" folder

Generate javacc's input grammar and the visitors and jtb.out.jj file using jtb

	java -jar jtb132.jar <.jj file>

Generate the syntax tree and visitors using javacc

	javacc jtb.out.jj

For the Flex and Bison assignments:
	
	The main flex and bison files are called PY.l and PY.y, respectively.
	compiling (bison PY.y; flex PY.l; gcc PY.tab.c lex.yy.c -lfl -o PY) produces a binary named PY 

For Java coding assignments:
	
	The main file is named PY.java, where Y is the assignment number.
	Compiling command :  javac PY.java
	Execution command : java PY < <input sample program>

Find the required jar file in the "jar files" folder and check the execution of intermediate codes using

	java -jar <jar file> < <input sample program>
