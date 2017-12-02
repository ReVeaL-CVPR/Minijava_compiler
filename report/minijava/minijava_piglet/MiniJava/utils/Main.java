package MiniJava.utils;
import minijava_syntaxtree.Node;
import minijava_visitor.GJNoArguDepthFirst;

public class Main {
   public static void main(String [] args) {
      try {
         Node root = new MiniJavaParser(System.in).Goal();
         System.out.println("Program parsed successfully");
         root.accept(new GJNoArguDepthFirst());
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
}
