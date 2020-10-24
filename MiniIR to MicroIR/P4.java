import syntaxtree.*;
import visitor.*;

public class P4 {
   public static void main(String [] args) {
      try {
//      	System.out.println("fe");
         Node root = new MiniIRParser(System.in).Goal();
         root.accept(new GJNoArguDepthFirst()); // Your assignment part is invoked here.
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
}
