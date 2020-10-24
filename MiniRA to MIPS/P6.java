import syntaxtree.*;
import visitor.*;

public class P6 {
   public static void main(String [] args) {
      try {
         Node root = new MiniRAParser(System.in).Goal();
         GJNoArguDepthFirst node = new GJNoArguDepthFirst();
         root.accept(node);

        System.out.println("           .text");
     	  System.out.println("           .globl _halloc");
     	  System.out.println("_halloc:");
     	  System.out.println("           li $v0, 9");
     	  System.out.println("           syscall");
     	  System.out.println("           j $ra");

        System.out.println("           .text");
     	  System.out.println("           .globl _print");
     	  System.out.println("_print:");
     	  System.out.println("           li $v0, 1");
     	  System.out.println("           syscall");
     	  System.out.println("           la $a0, newl");
     	  System.out.println("           li $v0, 4");
     	  System.out.println("           syscall");
     	  System.out.println("           j $ra");

     	  System.out.println("           .data");
     	  System.out.println("           .align 0");
     	  System.out.println("newl:");
     	  System.out.println("           .asciiz \"\\n\"");
     	  System.out.println("           .data");
     	  System.out.println("           .align 0");

     	  System.out.println("str_er:");
     	  System.out.println("           .asciiz \" ERROR: abnormal termination\\n\"");

      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
}
