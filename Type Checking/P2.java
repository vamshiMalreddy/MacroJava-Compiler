import syntaxtree.*;
import visitor.*;

public class P2 {
   public static void main(String [] args) {
      try {
         Node root = new MiniJavaParser(System.in).Goal();
         // System.out.println("Program parsed successfully");
         GJNoArguDepthFirst node = new GJNoArguDepthFirst();
         root.accept(node); // Your assignment part is invoked here.
         root.accept(node);
         System.out.println("Program type checked successfully");
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
}
