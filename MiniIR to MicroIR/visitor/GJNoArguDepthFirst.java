//
// Generated by JTB 1.3.2
//

/*
print_args = false
print_exp = false
print_lab = false
*/

package visitor;
import syntaxtree.*;
import java.util.*;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class GJNoArguDepthFirst<R> implements GJNoArguVisitor<R> {
   //
   // Auto class visitors--probably don't need to be overridden.
   //

   int curr_ind = 1;
   int num_args = -1;
   boolean print_exp = true;
   Stack<Integer> index_stack = new Stack<Integer>();
   Map<R,R> TempTab = new HashMap<>();
   boolean print_args = false;
   boolean print_lab = true;
   // ArrayList<R> ArgList = new ArrayList<R>();
   Stack<ArrayList<R>> arg_stack = new Stack<ArrayList<R>>();

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

   public R visit(NodeToken n) {  return (R)n.tokenImage; }

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> "MAIN"
    * f1 -> StmtList()
    * f2 -> "END"
    * f3 -> ( Procedure() )*
    * f4 -> <EOF>
    */
   public R visit(Goal n) {
      R _ret=null;
      // System.out.println("fkas ");

      n.f0.accept(this);
      System.out.print("MAIN\n");
      n.f1.accept(this);
      n.f2.accept(this);
      System.out.print("END\n");
      n.f3.accept(this);
      n.f4.accept(this);
      return _ret;
   }

   /**
    * f0 -> ( ( Label() )? Stmt() )*
    */
   public R visit(StmtList n) {

      R _ret=null;
      // System.out.println("fkas ");
      boolean prev = print_lab;
      print_lab = true;
      n.f0.accept(this);
      print_lab = prev;
      return _ret;
   }

   /**
    * f0 -> Label()
    * f1 -> "["
    * f2 -> IntegerLiteral()
    * f3 -> "]"
    * f4 -> StmtExp()
    */
   public R visit(Procedure n) {
      R _ret=null;
      System.out.println("");

      boolean prev = print_lab;
      print_lab = true;
      n.f0.accept(this);
      print_lab = prev;

      n.f1.accept(this);

      prev = print_exp;
      print_exp = false;
      R i = n.f2.accept(this);
      print_exp = prev;

      num_args = Integer.valueOf(String.valueOf(i));
      curr_ind = num_args+1;
      TempTab = new HashMap<>();
      n.f3.accept(this);
      System.out.println("[" + i + "]");
      System.out.println("BEGIN");
      R f_ret = n.f4.accept(this);
      System.out.println("RETURN " + f_ret);
      System.out.println("END ");
      return _ret;
   }

   /**
    * f0 -> NoOpStmt()
    *       | ErrorStmt()
    *       | CJumpStmt()
    *       | JumpStmt()
    *       | HStoreStmt()
    *       | HLoadStmt()
    *       | MoveStmt()
    *       | PrintStmt()
    */
   public R visit(Stmt n) {
      R _ret=null;
      // System.out.println("fkas ");
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "NOOP"
    */
   public R visit(NoOpStmt n) {
      R _ret=null;
      n.f0.accept(this);
      System.out.println("NOOP ");
      return _ret;
   }

   /**
    * f0 -> "ERROR"
    */
   public R visit(ErrorStmt n) {
      R _ret=null;
      n.f0.accept(this);
      System.out.println("ERROR ");
      return _ret;
   }

   /**
    * f0 -> "CJUMP"
    * f1 -> Exp()
    * f2 -> Label()
    */
   public R visit(CJumpStmt n) {
      R _ret=null;
      n.f0.accept(this);

      boolean prev = print_args;
      print_args = false;
      R exp = n.f1.accept(this);
      print_args = prev;

      prev = print_lab;
      print_lab = true;
      System.out.print("CJUMP "+ exp +" ");
      n.f2.accept(this);

      print_lab = prev;

      return _ret;
   }

   /**
    * f0 -> "JUMP"
    * f1 -> Label()
    */
   public R visit(JumpStmt n) {
      R _ret=null;

      n.f0.accept(this);

      boolean prev = print_lab;
      print_lab = true;
      System.out.print("JUMP ");
      n.f1.accept(this);
      print_lab = prev;

      return _ret;
   }

   /**
    * f0 -> "HSTORE"
    * f1 -> Exp()
    * f2 -> IntegerLiteral()
    * f3 -> Exp()
    */
   public R visit(HStoreStmt n) {
      R _ret=null;


      n.f0.accept(this);

      boolean prev = print_args;
      print_args = false;
      R exp = n.f1.accept(this);
      print_args = prev;

       prev = print_exp;
      print_exp = false;
      R i = n.f2.accept(this);
      print_exp = prev;

      prev = print_lab;
      boolean prev1 = print_args;
      print_lab = false;
      print_args = false;
      R exp2 = n.f3.accept(this);
      print_args = prev1;
      print_lab = prev;

      System.out.println("HSTORE "+ exp +" "+ i + " "+ exp2);

      return _ret;
   }

   /**
    * f0 -> "HLOAD"
    * f1 -> Temp()
    * f2 -> Exp()
    * f3 -> IntegerLiteral()
    */
   public R visit(HLoadStmt n) {
      R _ret=null;
      n.f0.accept(this);
      R temp = n.f1.accept(this);

      boolean prev = print_args;
      print_args = false;
      R exp = n.f2.accept(this);
      print_args = prev;

      prev = print_exp;
      print_exp = false;
      R i = n.f3.accept(this);
      print_exp = prev;

      System.out.println("HLOAD "+ temp +" "+ exp + " "+ i);
      return _ret;
   }

   /**
    * f0 -> "MOVE"
    * f1 -> Temp()
    * f2 -> Exp()
    */
   public R visit(MoveStmt n) {
      R _ret=null;
      n.f0.accept(this);
      R temp = n.f1.accept(this);

      boolean prev = print_args;
      print_args = false;
      R exp = n.f2.accept(this);
      print_args = prev;

      System.out.println("MOVE "+ temp +" "+exp);
      return _ret;
   }

   /**
    * f0 -> "PRINT"
    * f1 -> Exp()
    */
   public R visit(PrintStmt n) {
      R _ret=null;
      n.f0.accept(this);

      boolean prev = print_args;
      print_args = false;
      R exp = n.f1.accept(this);
      print_args = prev;

      System.out.println("PRINT "+exp);
      return _ret;
   }

   /**
    * f0 -> StmtExp()
    *       | Call()
    *       | HAllocate()
    *       | BinOp()
    *       | Temp()
    *       | IntegerLiteral()
    *       | Label()
    */
   public R visit(Exp n) {
      R _ret=null;
      R exp = n.f0.accept(this);

      if(print_args)
      {
        arg_stack.peek().add(exp);
      }

      return exp;
   }

   /**
    * f0 -> "BEGIN"
    * f1 -> StmtList()
    * f2 -> "RETURN"
    * f3 -> Exp()
    * f4 -> "END"
    */
   public R visit(StmtExp n) {
      R _ret=null;

      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);

      boolean prev = print_args;
      print_args = false;
      R exp = n.f3.accept(this);
      print_args = prev;
      n.f4.accept(this);
      return exp;
   }

   /**
    * f0 -> "CALL"
    * f1 -> Exp()
    * f2 -> "("
    * f3 -> ( Exp() )*
    * f4 -> ")"
    */
   public R visit(Call n) {
      R _ret=null;
      n.f0.accept(this);
      // <ArrayList<R>> list =??
      arg_stack.add( new ArrayList<R>());
      boolean prev = print_args;
      print_args = false;
      R f = n.f1.accept(this);
      print_args = prev;

      n.f2.accept(this);

      prev = print_args;
      print_args = true;
      n.f3.accept(this);
      print_args = prev;
      n.f4.accept(this);


      index_stack.push(curr_ind);
      curr_ind++;
      System.out.print("MOVE TEMP "+index_stack.peek()+" ");
      System.out.print("CALL "+ f+" "+"(");
      ListIterator<R> it1 = arg_stack.peek().listIterator();
      while (it1.hasNext())
      {
          R tem  = it1.next();
          System.out.print(tem + " ");
      }
      System.out.println(")");
      arg_stack.pop();
      String s = "TEMP "+String.valueOf(index_stack.peek());
      index_stack.pop();
      return (R)s;
   }

   /**
    * f0 -> "HALLOCATE"
    * f1 -> Exp()
    */
   public R visit(HAllocate n) {
      R _ret=null;

      n.f0.accept(this);

      boolean prev = print_args;
      print_args = false;
      R exp = n.f1.accept(this);
      print_args = prev;

      index_stack.push(curr_ind);
      curr_ind++;
      System.out.println("MOVE TEMP "+index_stack.peek()+" HALLOCATE "+ exp);
      String s = "TEMP "+String.valueOf(index_stack.peek());
      index_stack.pop();
      return (R)s;
   }

   /**
    * f0 -> Operator()
    * f1 -> Exp()
    * f2 -> Exp()
    */
   public R visit(BinOp n) {
      R _ret=null;
      R op = n.f0.accept(this);

      boolean prev = print_args;
      print_args = false;
      R id1 = n.f1.accept(this);
      R id2 = n.f2.accept(this);
      print_args = prev;

      index_stack.push(curr_ind);
      curr_ind++;
      System.out.println("MOVE TEMP "+index_stack.peek()+" "+ op + " "+id1+" "+id2);
      String s = "TEMP "+String.valueOf(index_stack.peek());
      index_stack.pop();
      return (R)s;

   }

   /**
    * f0 -> "LE"
    *       | "NE"
    *       | "PLUS"
    *       | "MINUS"
    *       | "TIMES"
    *       | "DIV"
    */
   public R visit(Operator n) {
      R _ret=n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "TEMP"
    * f1 -> IntegerLiteral()
    */
   public R visit(Temp n) {
      R _ret=null;

      boolean prev = print_exp;
      print_exp = false;
      n.f0.accept(this);
      R i = n.f1.accept(this);
      int int_i = Integer.valueOf(String.valueOf(i));
      print_exp = prev;

      if(!TempTab.containsKey(i))
      {
        if(int_i <= num_args)
        {
          TempTab.put(i,i);
        }
        else
        {
          index_stack.push(curr_ind);
          curr_ind++;
          TempTab.put(i,(R)index_stack.peek());
          index_stack.pop();
        }
      }
      String s = "TEMP "+String.valueOf(TempTab.get(i));
      return (R)s;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public R visit(IntegerLiteral n) {
      R _ret=null;
      R i = n.f0.accept(this);
      if(print_exp)
      {
        index_stack.push(curr_ind);
        curr_ind++;
        System.out.println("MOVE TEMP "+ (R)index_stack.peek()+" "+i);
        _ret = (R)index_stack.peek();
        index_stack.pop();
        String s = "TEMP "+String.valueOf(_ret);
        _ret = (R)s;
      }
      else
        _ret = i;
      return _ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public R visit(Label n) {
      R _ret=n.f0.accept(this);

      if(print_lab)
        System.out.print(_ret + "\n");
      else
      {
        index_stack.push(curr_ind);
        curr_ind++;
        System.out.println("MOVE TEMP "+ (R)index_stack.peek()+" "+_ret);
        _ret = (R)index_stack.peek();
        index_stack.pop();
        String s = "TEMP "+String.valueOf(_ret);
        _ret = (R)s;
      }

      return _ret;
   }

}