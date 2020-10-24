//
// Generated by JTB 1.3.2
//


/*

2)NOOP was there in AndExpression
3)NOOP was there in WhileStatement
4)Removed SendMsgFlag totally
5)Didn't use expType
6)used prime_ex only in few cases
7)Didn't use newLineFlag
8)Didn't use objToken
*/
package visitor;
import syntaxtree.*;
import java.util.*;
import java.util.ArrayList;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class GJNoArguDepthFirst<R> implements GJNoArguVisitor<R> {

   class FuncItem
   {
     R func;
     R clas;
   }

   class Vartype
   {
     R name;
     R type;
   }
   /*iteration*/
   int itr = 0;
   int var_ind = 21;
   int l_ind = 0;

   /*doubts*/
   R prime_ex;
   int counter = 0;
   boolean Flag1;
   boolean Flag2;

   /*symbol tables*/
   Map<R,ArrayList<R>> VarSymTab = new HashMap<>();
   Map<R,Integer> LocVarTab;
   ArrayList<R> LocParList;

   Map<R,ArrayList<Vartype>> VarSymType = new HashMap<>();
   Map<R,R> LocVarType;
   Map<R,R> LocParType;

   Map<R,ArrayList<FuncItem>> FuncSymTab = new HashMap<>();
   Map<R,ArrayList<R>> ClassGraph = new HashMap<>();
   Map<R,R> ParentClass = new HashMap<>();
   Map<R,Boolean> visited = new HashMap<>();
   Stack<R> stack = new Stack<R>();
   Stack<Integer> index_stack = new Stack<Integer>();
   Stack<Integer> l_stack = new Stack<Integer>();
   R curr_class = null;
   R curr_func = null;

   public boolean has_func(ArrayList<FuncItem> templist,R fName)
   {
     ListIterator<FuncItem> iterator = templist.listIterator();
     while (iterator.hasNext())
     {
         if(iterator.next().func == fName)
         {
           return true;
         }
     }
     return false;
   }

   public int findInd(ArrayList<FuncItem> templist,R fName)
   {
     ListIterator<FuncItem> iterator = templist.listIterator();
     int i = 0;
     while (iterator.hasNext())
     {
         if(iterator.next().func == fName)
         {
           return i;
         }
         i++;
     }
     return -1;
   }

   public int IndexOf(R var,ArrayList<R> Listtemp)
   {
     ListIterator<R> iterator = Listtemp.listIterator();
     int i = 0;
     while (iterator.hasNext())
     {
         if(iterator.next() == var)
         {
           return i;
         }
         i++;
     }
     return -1;
   }

   public void update_table(R child,R parent)
   {
     ListIterator<R> iterator = VarSymTab.get(parent).listIterator();
     R i;
     FuncItem j;
     while (iterator.hasNext())
     {
         i = iterator.next();
         if(!(VarSymTab.get(child).contains(i)))
         {
           VarSymTab.get(child).add(i);
         }
     }

     ListIterator<FuncItem> iterator1 = FuncSymTab.get(parent).listIterator();
     while (iterator1.hasNext())
     {
         j = iterator1.next();

         if(!has_func(FuncSymTab.get(child),j.func))
         {
           FuncSymTab.get(child).add(j);
         }
     }

     Vartype vty;
     ListIterator<Vartype> iterator2 = VarSymType.get(parent).listIterator();
     while (iterator2.hasNext())
     {
         vty = iterator2.next();

         if(!VarSymType.get(child).contains(vty))
         {
           VarSymType.get(child).add(vty);
         }
     }
   }

   public void topologicalSortUtil(R class_name)
    {
        visited.replace(class_name,true);
        R i;

        ListIterator<R> iterator = ClassGraph.get(class_name).listIterator();
        while (iterator.hasNext())
        {
            i = iterator.next();
            if (!visited.get(i))
                topologicalSortUtil(i);
        }
        stack.push(class_name);
    }

    // The function to do Topological Sort. It uses
    // recursive topologicalSortUtil()
    public void topologicalSort()
    {
        Iterator<Map.Entry<R,Boolean>> it = visited.entrySet().iterator();

        while(it.hasNext())
        {
             Map.Entry<R,Boolean> entry = it.next();
             if (entry.getValue() == false)
                 topologicalSortUtil(entry.getKey());
        }
    }

    public String getTempString(R var)
    {
      if(curr_func != null)
       {
         Flag2 = false;
   		   if( LocVarTab.containsKey(var))
   			   return "TEMP " + LocVarTab.get(var);
   		   else if( LocParList.contains(var))
   			   return "TEMP " + (1+IndexOf(var,LocParList));
       }
      if( curr_class != null && VarSymTab.get(curr_class).contains(var)){
      Flag2 = true;
 		   if(counter == 0)
 			   return "TEMP 0 " + 4*(1+ IndexOf(var,VarSymTab.get(curr_class)));
 		   else {
 			   var_ind++;
 			   return "\nBEGIN\n HLOAD TEMP " + (var_ind-1) + " TEMP 0 " + 4*(1+ IndexOf(var,VarSymTab.get(curr_class))) + "\n RETURN\n TEMP " + (var_ind-1) + "\nEND";
 		   }
 	   }

	   return "";
    }

    public R FindType(ArrayList<Vartype> al,R var)
    {
      Vartype vty;
      ListIterator<Vartype> iterator2 = al.listIterator();
      while (iterator2.hasNext())
      {
          vty = iterator2.next();

          if(vty.name == var)
          {
            return vty.type;
          }
      }
      return (R)"";
    }

    public R get_type(R var)
    {
      if(LocVarType.containsKey(var))
        return LocVarType.get(var);
      if(LocParType.containsKey(var))
        return LocParType.get(var);

      return FindType(VarSymType.get(curr_class),var);
    }
   public R visit(NodeList n) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeListOptional n) {
      if ( n.present() ) {
         R _ret=null;
         int _count=0;
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this);
            _count++;
         }
         return _ret;
      }
      else
         return null;
   }

   public R visit(NodeOptional n) {
      if ( n.present() )
         return n.node.accept(this);
      else
         return null;
   }

   public R visit(NodeSequence n) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeToken n) { return (R)n.tokenImage; }

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> <EOF>
    */
   public R visit(Goal n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      if(itr == 0)
      {
        Iterator<Map.Entry<R,R>> it = ParentClass.entrySet().iterator();

        while(it.hasNext())
        {
             Map.Entry<R,R> entry = it.next();
             if (entry.getValue() != null)
             {
               ClassGraph.get(entry.getValue()).add(entry.getKey());
             }
        }

        topologicalSort();

        while(!stack.empty())
        {
          R cl = stack.peek();
          if(ParentClass.containsKey(cl) )
          {
            update_table(cl,ParentClass.get(cl));
          }
          stack.pop();
        }

        itr++;
      }

      // ListIterator<FuncItem> it1 = FuncSymTab.get((R)"A").listIterator();
      // while (it1.hasNext())
      // {
      //     FuncItem tem  = it1.next();
      //     System.out.print (tem.clas + "_" + tem.func + "\n ");
      // }


      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> "public"
    * f4 -> "static"
    * f5 -> "void"
    * f6 -> "main"
    * f7 -> "("
    * f8 -> "String"
    * f9 -> "["
    * f10 -> "]"
    * f11 -> Identifier()
    * f12 -> ")"
    * f13 -> "{"
    * f14 -> PrintStatement()
    * f15 -> "}"
    * f16 -> "}"
    */
   public R visit(MainClass n) {
      R _ret=null;
      if(itr == 1)
      {
        System.out.print("MAIN\n ");
      }

      n.f0.accept(this);
      R class_name = n.f1.accept(this);
      if(itr == 1)
      {
        VarSymTab.put(class_name,new ArrayList<R>());
        FuncSymTab.put(class_name,new ArrayList<FuncItem>());
      }
      n.f2.accept(this);
      n.f3.accept(this);
      n.f4.accept(this);
      n.f5.accept(this);
      n.f6.accept(this);
      n.f7.accept(this);
      n.f8.accept(this);
      n.f9.accept(this);
      n.f10.accept(this);
      n.f11.accept(this);
      n.f12.accept(this);
      n.f13.accept(this);
      n.f14.accept(this);
      n.f15.accept(this);
      n.f16.accept(this);
      if(itr == 1)
        System.out.print("END\n ");
      return _ret;
   }

   /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
   public R visit(TypeDeclaration n) {
      R _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
    */
   public R visit(ClassDeclaration n) {
      R _ret=null;
      n.f0.accept(this);
      R class_name = n.f1.accept(this);
      curr_class = class_name;
      if(itr == 0)
      {
        VarSymTab.put(class_name,new ArrayList<R>());
        FuncSymTab.put(class_name,new ArrayList<FuncItem>());
        ClassGraph.put(class_name,new ArrayList<R>());
        VarSymType.put(class_name,new ArrayList<Vartype>());
        visited.put(class_name,false);
      }
      n.f2.accept(this);
      n.f3.accept(this);
      n.f4.accept(this);
      n.f5.accept(this);
      curr_class = null;
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "extends"
    * f3 -> Identifier()
    * f4 -> "{"Type
    * f5 -> ( VarDeclaration() )*
    * f6 -> ( MethodDeclaration() )*
    * f7 -> "}"ClassGraph.put(class_name,null);
        visited.put(class_name,false);
    */
   public R visit(ClassExtendsDeclaration n) {
      R _ret=null;
      n.f0.accept(this);
      R class_name = n.f1.accept(this);
      curr_class = class_name;
      if(itr == 0)
      {
        VarSymTab.put(class_name,new ArrayList<R>());
        FuncSymTab.put(class_name,new ArrayList<FuncItem>());
        ClassGraph.put(class_name,new ArrayList<R>());
        visited.put(class_name,false);
        VarSymType.put(class_name,new ArrayList<Vartype>());
      }
      n.f2.accept(this);
      R parent_class = n.f3.accept(this);
      if(itr == 0)
      {
        ParentClass.put(curr_class,parent_class);
      }
      n.f4.accept(this);
      n.f5.accept(this);
      n.f6.accept(this);
      n.f7.accept(this);
      curr_class = null;
      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
   public R visit(VarDeclaration n) {
      R _ret=null;
      R typ = n.f0.accept(this);
      R var_name = n.f1.accept(this);
      if(itr == 0)
      {
        if(curr_func == null)
        {
          VarSymTab.get(curr_class).add(var_name);
          if(typ != null)
          {
            Vartype vt = new Vartype();
            vt.name = var_name;
            vt.type = typ;
            VarSymType.get(curr_class).add(vt);
          }
        }
      }
      if(itr == 1)
      {
        if(curr_func != null )
        {
          LocVarTab.put(var_name,var_ind);
          var_ind++;
          if(typ != null)
            LocVarType.put(var_name,typ);
        }
      }

      n.f2.accept(this);
      return _ret;
   }

   /**
    * f0 -> "public"
    * f1 -> Type()
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( FormalParameterList() )?
    * f5 -> ")"
    * f6 -> "{"
    * f7 -> ( VarDeclaration() )*
    * f8 -> ( Statement() )*
    * f9 -> "return"
    * f10 -> Expression()
    * f11 -> ";"
    * f12 -> "}"
    */
   public R visit(MethodDeclaration n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      R func_name = n.f2.accept(this);
      curr_func = func_name;
      if(itr == 0)
      {
        FuncItem fi = new FuncItem();
        fi.clas = curr_class;
        fi.func = func_name;
        FuncSymTab.get(curr_class).add(fi);
      }

      if(itr == 1)
      {
        LocParList = new ArrayList<R>();
        LocVarTab = new HashMap<>();
        LocVarType = new HashMap<>();
        LocParType = new HashMap<>();
      }
      n.f3.accept(this);
      n.f4.accept(this);
      n.f5.accept(this);
      n.f6.accept(this);
      if(itr == 1)
      {
        System.out.println("\n\n"+ curr_class + "_" + curr_func+ " [" + (LocParList.size()+1) + "]\n");
        System.out.print("BEGIN\n ");
      }

      n.f7.accept(this);
      n.f8.accept(this);
      n.f9.accept(this);
      if(itr == 1)
      {
        Flag1 = true;
        System.out.print("RETURN\n ");
      }
      n.f10.accept(this);
      if(itr == 1)
      {
        Flag1 = false;
        System.out.print("\nEND\n\n ");
      }
      n.f11.accept(this);
      n.f12.accept(this);
      curr_func = null;
      return _ret;
   }

   /**
    * f0 -> FormalParameter()
    * f1 -> ( FormalParameterRest() )*
    */
   public R visit(FormalParameterList n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
   public R visit(FormalParameter n) {
      R _ret=null;
      R ty = n.f0.accept(this);
      R par = n.f1.accept(this);
      if(itr == 1)
      {
        LocParList.add(par);
        LocParType.put(par,ty);
      }
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
   public R visit(FormalParameterRest n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      return _ret;
   }

   /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
   public R visit(Type n) {
      R _ret = n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
   public R visit(ArrayType n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      return _ret;
   }

   /**
    * f0 -> "boolean"
    */
   public R visit(BooleanType n) {
      R _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "int"
    */
   public R visit(IntegerType n) {
      R _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | PrintStatement()
    */
   public R visit(Statement n) {
      R _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
   public R visit(Block n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
   public R visit(AssignmentStatement n) {
      R _ret=null;
      if(itr == 0)
      {
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
      }
      else
      {
        Flag1 = true;

        String x = getTempString((R)n.f0.f0.tokenImage);

        if(Flag2)
          System.out.print("HSTORE ");
        else
          System.out.print("MOVE ");

        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        Flag1 = false;
        n.f3.accept(this);
        System.out.print("\n ");

      }

      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> Expression()
    * f3 -> "]"
    * f4 -> "="
    * f5 -> Expression()
    * f6 -> ";"
    */
   public R visit(ArrayAssignmentStatement n) {
      R _ret=null;
      if(itr == 0)
      {
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
        n.f6.accept(this);
      }
      else
      {
        Flag1 = true;
        System.out.print("HSTORE PLUS ");
        counter++;
        n.f0.accept(this);
        n.f1.accept(this);
        counter--;

        System.out.print("TIMES 4 ");
        n.f2.accept(this);
        n.f3.accept(this);
        System.out.println(" 4");
        n.f4.accept(this);
        n.f5.accept(this);
          Flag1 = false;
        n.f6.accept(this);

        System.out.print("\n");
      }

      return _ret;
   }

   /**
    * f0 -> IfthenElseStatement()
    *       | IfthenStatement()
    */
   public R visit(IfStatement n) {
      R _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
   public R visit(IfthenStatement n) {
      R _ret=null;
      if(itr == 0)
      {
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
      }
      else
      {
        Flag1 = true;
        l_stack.push(l_ind);
        l_ind += 1;
        n.f0.accept(this);
        n.f1.accept(this);
        System.out.print("CJUMP ");
        n.f2.accept(this);
        n.f3.accept(this);
        System.out.print("L" + (l_stack.peek()) + "\n ");
        n.f4.accept(this);
        System.out.print("L" + (l_stack.peek()) + " NOOP\n ");
        l_stack.pop();
      }

      return _ret;
   }

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    * f5 -> "else"
    * f6 -> Statement()
    */
   public R visit(IfthenElseStatement n) {
      R _ret=null;
      if(itr == 0)
      {
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
        n.f6.accept(this);
      }
      if(itr == 1)
      {

         l_stack.push(l_ind);
         l_ind += 2;
         Flag1 = true;
         n.f0.accept(this);
         n.f1.accept(this);
         System.out.print("CJUMP ");
         n.f2.accept(this);
         n.f3.accept(this);
         System.out.print("L" + (l_stack.peek()+1) + "\n ");
         n.f4.accept(this);
         System.out.print("JUMP L" + l_stack.peek() + "\n ");
         n.f5.accept(this);
         System.out.print("L" + (l_stack.peek()+1) + " NOOP\n ");
         n.f6.accept(this);
         Flag1 = false;
         System.out.print("L" + l_stack.peek() + " NOOP\n ");
         l_stack.pop();

      }
      return _ret;
   }

   /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
   public R visit(WhileStatement n) {
      R _ret=null;
      if(itr == 0)
      {
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
      }
      else
      {
        l_stack.push(l_ind);
        l_ind += 2;
        Flag1 = true;
        n.f0.accept(this);
        n.f1.accept(this);
        System.out.print("L" + (l_stack.peek()) + " NOOP\n CJUMP ");
        n.f2.accept(this);
        System.out.print("L" + (l_stack.peek()+1) + "\n ");//NOOP was there
        n.f3.accept(this);
        n.f4.accept(this);
          Flag1 = false;
        System.out.print("JUMP L" + (l_stack.peek()) +"\n ");
        System.out.print("L" + (l_stack.peek()+1) + " NOOP\n ");
        l_stack.pop();

      }

      return _ret;
   }

   /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
    */
   public R visit(PrintStatement n) {
      R _ret=null;
      Flag1 = true;
      n.f0.accept(this);
      n.f1.accept(this);
      if(itr == 1)
        System.out.print("PRINT ");
      n.f2.accept(this);
      n.f3.accept(this);
      n.f4.accept(this);
      if(itr == 1)
        System.out.print("\n");
      Flag1 = false;
      return _ret;
   }

   /**
    * f0 -> OrExpression()
    *       | AndExpression()
    *       | CompareExpression()
    *       | neqExpression()
    *       | PlusExpression()
    *       | MinusExpression()
    *       | TimesExpression()
    *       | DivExpression()
    *       | ArrayLookup()
    *       | ArrayLength()
    *       | MessageSend()
    *       | PrimaryExpression()
    */
   public R visit(Expression n) {
     R _ret=null;
     if(itr == 0)
     {
       n.f0.accept(this);
     }
      if(itr == 1)
      {
         counter++;
         n.f0.accept(this);
         counter--;
      }


      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "&&"
    * f2 -> PrimaryExpression()
    */
   public R visit(AndExpression n) {
      R _ret=null;
      if(itr == 0)
      {
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
      }
      else
      {
        l_stack.push(l_ind);
        index_stack.push(var_ind);
        var_ind++;
        l_ind += 2;
        System.out.print("\nBEGIN \nCJUMP NE PLUS ");
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        System.out.print("2 L" + l_stack.peek() + " \n ");//NOOP was there
        System.out.print("MOVE TEMP " + index_stack.peek() + " 0\n JUMP L" + (l_stack.peek()+1) + "\n ");
        System.out.print("L" + l_stack.peek() + "\n MOVE TEMP " + index_stack.peek() + " 1\n");
        System.out.print("L" + (l_stack.peek()+1) + "\n NOOP\n");
        System.out.print(" RETURN\n TEMP " + index_stack.peek() + "\nEND\n");

        l_stack.pop();
        index_stack.pop();
      }


      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "||"
    * f2 -> PrimaryExpression()
    */
   public R visit(OrExpression n) {
      R _ret=null;
      if(itr == 0)
      {
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
      }
      else
      {
        l_stack.push(l_ind);
        index_stack.push(var_ind);
        var_ind++;
        l_ind += 2;
        System.out.print("\nBEGIN \nCJUMP NE PLUS ");
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        System.out.print("0 L" + l_stack.peek() + " \n ");//NOOP was there
        System.out.print("MOVE TEMP " + index_stack.peek() + " 1\n JUMP L" + (l_stack.peek()+1) + "\n ");
        System.out.print("L" + l_stack.peek() + "\n MOVE TEMP " + index_stack.peek() + " 0\n");
        System.out.print("L" + (l_stack.peek()+1) + "\n NOOP\n");
        System.out.print(" RETURN\n TEMP " + index_stack.peek() + "\nEND\n");
        l_stack.pop();
        index_stack.pop();
      }
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "<="
    * f2 -> PrimaryExpression()
    */
   public R visit(CompareExpression n) {
      R _ret=null;
      if(itr == 1)
        System.out.print("LE ");
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "!="
    * f2 -> PrimaryExpression()
    */
   public R visit(neqExpression n) {
      R _ret=null;
      if(itr == 1)
        System.out.print("NE ");
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
   public R visit(PlusExpression n) {
      R _ret=null;
      if(itr == 1)
        System.out.print("PLUS ");
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
   public R visit(MinusExpression n) {
     R _ret=null;
     if(itr == 1)
       System.out.print("MINUS ");
     n.f0.accept(this);
     n.f1.accept(this);
     n.f2.accept(this);
     return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
   public R visit(TimesExpression n) {
     R _ret=null;
     if(itr == 1)
       System.out.print("TIMES ");
     n.f0.accept(this);
     n.f1.accept(this);
     n.f2.accept(this);
     return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "/"
    * f2 -> PrimaryExpression()
    */
   public R visit(DivExpression n) {
     R _ret=null;
     if(itr == 1)
       System.out.print("DIV ");
     n.f0.accept(this);
     n.f1.accept(this);
     n.f2.accept(this);
     return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
   public R visit(ArrayLookup n) {
     R _ret=null;
     if(itr == 0)
     {

       n.f0.accept(this);
       n.f1.accept(this);
       n.f2.accept(this);
       n.f3.accept(this);

     }
     else
     {
        index_stack.push(var_ind);
        var_ind+=2;
        System.out.print("\nBEGIN\n HLOAD TEMP " + (index_stack.peek()) + " PLUS\n");
        n.f0.accept(this);
        n.f1.accept(this);
        System.out.print("\nBEGIN\n MOVE TEMP " + (index_stack.peek()+1) + " TIMES 4 ");
        n.f2.accept(this);
        n.f3.accept(this);
        System.out.print("\n RETURN\n TEMP " + (index_stack.peek()+1) + "\nEND\n 4\n RETURN\n TEMP " + (index_stack.peek()) + "\nEND\n ");
        index_stack.pop();
     }
     return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
   public R visit(ArrayLength n) {
     R _ret=null;
     if(itr == 0)
     {
       n.f0.accept(this);
       n.f1.accept(this);
       n.f2.accept(this);
     }
     else
     {
       index_stack.push(var_ind);
       var_ind+=1;
       System.out.print("\nBEGIN\n");
       System.out.print("\nHLOAD TEMP " + (index_stack.peek())+" ");
       n.f0.accept(this);
       System.out.print(" 0");
       System.out.print("\n RETURN \n TEMP " + (index_stack.peek())+" \nEND");
       n.f1.accept(this);
       n.f2.accept(this);
       index_stack.pop();
     }


      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( ExpressionList() )?
    * f5 -> ")"
    */
   public R visit(MessageSend n) {
      R _ret=null;
      if(itr == 0)
      {
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
      }
      if(itr == 1)
      {
        index_stack.push(var_ind);
        var_ind+= 3;
        System.out.print("CALL \nBEGIN \n MOVE TEMP " + (index_stack.peek()+1) + " ");
        R pexp = n.f0.accept(this);
        if(pexp != null)
          prime_ex = get_type(pexp);
        System.out.print("\n HLOAD TEMP " + (index_stack.peek()) + " TEMP " + (index_stack.peek()+1) + " 0\n ");
        n.f1.accept(this);
        R f_name = n.f2.accept(this);
        System.out.print("HLOAD TEMP " + (index_stack.peek()+2) + " TEMP " + (index_stack.peek()) + " " + 4*findInd(FuncSymTab.get(prime_ex),f_name)+ "\n ");
        System.out.print("RETURN\n " + "TEMP " + (index_stack.peek()+2) + "\n " + "END\n ");

        n.f3.accept(this);
        System.out.print("( TEMP " + (index_stack.peek()+1) + " ");
        n.f4.accept(this);
        n.f5.accept(this);
        System.out.print(")\n ");
        index_stack.pop();
      }
      return _ret;
   }

   /**
    * f0 -> Expression()
    * f1 -> ( ExpressionRest() )*
    */
   public R visit(ExpressionList n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> Expression()
    */
   public R visit(ExpressionRest n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      return _ret;
   }

   /**
    * f0 -> IntegerLiteral()
    *       | TrueLiteral()
    *       | FalseLiteral()
    *       | Identifier()
    *       | ThisExpression()
    *       | ArrayAllocationExpression()
    *       | AllocationExpression()
    *       | NotExpression()
    *       | BracketExpression()
    */
   public R visit(PrimaryExpression n) {
      R _ret= n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public R visit(IntegerLiteral n) {
      R _ret = n.f0.accept(this);
      if(itr == 1)
      {
        System.out.print(_ret + " ");
        // pexpType = "int";
      }
      return _ret;
   }

   /**
    * f0 -> "true"
    */
   public R visit(TrueLiteral n) {
      R _ret=null;
      n.f0.accept(this);
      if(itr == 1)
      {
        System.out.print("1 ");
        // pexpType = "boolean";
      }
      return _ret;
   }

   /**
    * f0 -> "false"
    */
   public R visit(FalseLiteral n) {
      R _ret=null;
      n.f0.accept(this);
      if(itr == 1)
      {
        System.out.print("0 ");
        // pexpType = "boolean";
      }
      return _ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public R visit(Identifier n) {
      R id =n.f0.accept(this);
      if(itr == 1)
      {
        if(Flag1)
      	  System.out.print(getTempString(id) + " ");
      }
      return id;
   }

   /**
    * f0 -> "this"
    */
   public R visit(ThisExpression n) {
      R _ret=null;
      n.f0.accept(this);
      if(itr == 1)
      {
        System.out.print("TEMP 0" + "\n ");
        prime_ex = curr_class;
      }
      return _ret;
   }

   /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Expression()
    * f4 -> "]"
    */
   public R visit(ArrayAllocationExpression n) {
      R _ret=null;
      if(itr == 0)
      {
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
      }
      if(itr == 1)
      {
        index_stack.push(var_ind);
        l_stack.push(l_ind);
        var_ind+=2;
        l_ind += 2;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        System.out.print("\nBEGIN\n MOVE TEMP " + (index_stack.peek()) + " ");
        n.f3.accept(this);
        n.f4.accept(this);
        System.out.print("\n MOVE TEMP " + (index_stack.peek()+1) + " HALLOCATE TIMES PLUS TEMP " + (index_stack.peek()) + " 1" + " 4");
        System.out.print("\n HSTORE TEMP " + (index_stack.peek()+1) + " 0 TEMP " + (index_stack.peek()));
        System.out.print("\n L" + l_stack.peek() + "\n CJUMP NE 0 TEMP " + index_stack.peek() + " L" + (l_stack.peek()+1) + " \n");
        System.out.print("\n HSTORE PLUS TEMP " + (index_stack.peek()+1) + " TIMES 4 TEMP " + index_stack.peek() + " 0 0");
        System.out.print("\n MOVE TEMP " + index_stack.peek() + " MINUS TEMP " + index_stack.peek() + " 1");
        System.out.print("\n JUMP L" + l_stack.peek());
        System.out.print("\n L" + (l_stack.peek()+1) + "\n NOOP\n RETURN\n TEMP " + (index_stack.peek()+1) + "\nEND\n");

        l_stack.pop();
        index_stack.pop();
      }


      return _ret;
   }

   /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
   public R visit(AllocationExpression n) {
      R _ret=null;
      n.f0.accept(this);
      R id_class = n.f1.accept(this);
      if(itr == 1)
      {
        index_stack.push(var_ind);
        l_stack.push(l_ind);
        var_ind += 3;
        l_ind += 2;
        System.out.print("\nBEGIN\n");
        System.out.print ("MOVE TEMP " + (index_stack.peek()+2) + " " + (VarSymTab.get(id_class).size()) + "\n ");
        System.out.print ("MOVE TEMP " + (index_stack.peek()) + " HALLOCATE " + 4*FuncSymTab.get(id_class).size() + "\n ");
        System.out.print ("MOVE TEMP " + (index_stack.peek()+1) + " HALLOCATE " + 4*(VarSymTab.get(id_class).size()+1) + "\n ");

        int i = 0;
        ListIterator<FuncItem> it1 = FuncSymTab.get(id_class).listIterator();
        while (it1.hasNext())
        {
            FuncItem tem  = it1.next();
            System.out.print ("HSTORE TEMP " + index_stack.peek() + " " + 4*i + " " + tem.clas + "_" + tem.func + "\n ");
        	  ++i;
        }
        System.out.print("\n L" + l_stack.peek() + "\n CJUMP NE 0 TEMP " + (index_stack.peek()+2 )+ " L" + (l_stack.peek()+1) + " \n");
        System.out.print("\n HSTORE PLUS TEMP " + (index_stack.peek()+1) + " TIMES 4 TEMP " + (index_stack.peek()+2) + " 0 0");
        System.out.print("\n MOVE TEMP " + (index_stack.peek()+2) + " MINUS TEMP " + (index_stack.peek()+2) + " 1");
        System.out.print("\n JUMP L" + l_stack.peek());
        System.out.print("\n L" + (l_stack.peek()+1) + "\n NOOP\n");
        System.out.print ("HSTORE TEMP " + (index_stack.peek()+1) + " 0 TEMP " + index_stack.peek() + "\n ");
        System.out.print ("RETURN\n  TEMP " + (index_stack.peek()+1) + "\nEND\n \n ");
        // System.out.print(p_token + "\n ");
        index_stack.pop();
        l_stack.pop();
        prime_ex = id_class;
      }

      n.f2.accept(this);
      n.f3.accept(this);
      return _ret;
   }

   /**
    * f0 -> "!"
    * f1 -> Expression()
    */
   public R visit(NotExpression n) {
      R _ret=null;
      n.f0.accept(this);
      if(itr == 1)
        System.out.print("MINUS 1 ");
      n.f1.accept(this);
      // if(itr == 1)
      //   pexpType = "boolean";
      return _ret;
   }

   /**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
    */
   public R visit(BracketExpression n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> ( IdentifierRest() )*
    */
   public R visit(IdentifierList n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> Identifier()
    */
   public R visit(IdentifierRest n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      return _ret;
   }

}
