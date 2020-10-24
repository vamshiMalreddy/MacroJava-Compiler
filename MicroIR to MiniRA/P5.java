import syntaxtree.*;
import visitor.*;
import java.util.*;

public class P5 {
   public static void main(String [] args) {
      try {
         Node root = new microIRParser(System.in).Goal();
         HashMap<String,Integer> labstmtind = (HashMap<String,Integer>)root.accept(new GJNoArguDepthFirst());
         Maps map = new Maps(); 
         root.accept(new GJDepthFirst(labstmtind,map),null);
         root.accept(new GJDepthFirst1(map),null);
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
} 

