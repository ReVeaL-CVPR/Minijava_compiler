package MiniJava.typecheck.visitor;
import java.util.ArrayList;
import java.util.Set;

import Error_handle.error;
import MiniJava.typecheck.symboltable.*;
import MiniJava.typecheck.symboltable._Class;
import minijava_syntaxtree.ClassDeclaration;
import minijava_syntaxtree.ClassExtendsDeclaration;
import minijava_syntaxtree.FormalParameter;
import minijava_syntaxtree.FormalParameterList;
import minijava_syntaxtree.FormalParameterRest;
import minijava_syntaxtree.Identifier;
import minijava_syntaxtree.MainClass;
import minijava_syntaxtree.MethodDeclaration;
import minijava_syntaxtree.Node;
import minijava_syntaxtree.VarDeclaration;
import minijava_visitor.GJVoidDepthFirst;

public class construct_symboltable<A> extends GJVoidDepthFirst<A>{

   /**
    * MainClass
    * Grammar production:
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
    * f14 -> ( VarDeclaration() )*
    * f15 -> ( Statement() )*
    * f16 -> "}"
    * f17 -> "}"
    */   
   public void visit(final MainClass n, final A root) {
	   try{
		   ((Root)root).setDefine_line(n.f1.f0.beginLine);
		   ((Root)root).setName(n.f1.get_name());
		   ((Root)root).add_map(_Type.Class, n.f1.get_name(), root);
	       for (final Node node : n.f14.nodes){
	    	 //handle all the variables in main class
	    	   final Variable variable = new Variable();
	    	   node.accept(this, (A) variable);
	    	   ((Root)root).add_map(_Type.Var, variable.getName(), variable);
	       }
	   }catch(final Exception e){
		   e.printStackTrace();
	   }
   } 
   
   
   /**
    * VarDeclaration
    * Grammar production:
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
   public void visit(final VarDeclaration node, final A variable) {
	   try{
    	   if (((VarDeclaration) node).f0.f0.which == 3){
    		   final Node id = ((VarDeclaration) node).f0.f0.choice;
    		   ((Variable) variable).setType(((Identifier) id).get_name());
    	   }
    	   ((Variable) variable).setDefine_line(((VarDeclaration) node).f2.beginLine);
    	   ((Variable) variable).setType(((VarDeclaration) node).f0.f0.which);
    	   ((Variable) variable).setName(((VarDeclaration) node).f1.get_name());
	   }catch(final Exception e){
		   e.printStackTrace();
	   }
   }
   
   
   
   /**
    * ClassDeclaration
    * Grammar production:
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
    */
   public void visit(final ClassDeclaration n, final A root) {
	   try{
	       final String class_name = n.f1.get_name();
	       final _Class class1 = new _Class();
	       class1.setName(class_name);
	       class1.setDefine_line(n.f0.beginLine);
	       for (final Node node : n.f3.nodes){
	    	   //handle all the variables in this class
	    	   final Variable variable = new Variable();
	    	   node.accept(this, (A) variable);
	    	   class1.add_map(_Type.Var, variable.getName(), variable);
	       } 
	       for (final Node node : n.f4.nodes){
	    	 //handle all the methods in this class
	    	   final Method method = new Method();
	    	   node.accept(this, (A) method);
	    	   class1.add_map(_Type.Func, method.getName(), method);
	       } 
	       ((Abstract_Symbol)root).add_map(_Type.Class, class1.getName(), class1);
	   }catch(final Exception e){
		   e.printStackTrace();
	   }
   }
   
   
   

	/**
	 * Grammar production:
	 * f0 -> "class"
	 * f1 -> Identifier()
	 * f2 -> "extends"
	 * f3 -> Identifier()
	 * f4 -> "{"
	 * f5 -> ( VarDeclaration() )*
	 * f6 -> ( MethodDeclaration() )*
	 * f7 -> "}"
	 */
   
   public void visit(final ClassExtendsDeclaration n, final A root) {
	   try{
	       final String class_name = n.f1.get_name();
	       final _Class class1 = new _Class();
	       class1.setName(class_name);
	       class1.setDefine_line(n.f0.beginLine);
	       class1.father = n.f3.get_name();
	       for (final Node node : n.f5.nodes){
	    	   //handle all the variables in this class
	    	   final Variable variable = new Variable();
	    	   node.accept(this, (A) variable);
	    	   class1.add_map(_Type.Var, variable.getName(), variable);
	       } 
	       for (final Node node : n.f6.nodes){
	    	 //handle all the methods in this class
	    	   final Method method = new Method();
	    	   node.accept(this, (A) method);
	    	   class1.add_map(_Type.Func, method.getName(), method);
	       } 
	       ((Abstract_Symbol)root).add_map(_Type.Class, class1.getName(), class1);
	   }catch(final Exception e){
		   e.printStackTrace();
	   }
   }
   
   
   /**
    * MethodDeclaration
    * Grammar production:
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
   public void visit(final MethodDeclaration n, final A method) {
	   if (((MethodDeclaration) n).f1.f0.which == 3){
		   final Node id = ((MethodDeclaration) n).f1.f0.choice;
		   ((Method)method).setType(((Identifier) id).get_name());
	   }
	   ((Method)method).setType(((MethodDeclaration) n).f1.f0.which);
	   ((Method)method).setName(((MethodDeclaration) n).f2.get_name());
	   ((Method)method).setDefine_line(((MethodDeclaration) n).f0.beginLine);
	   final FormalParameterList list = (FormalParameterList)n.f4.node;
	   if (list != null) {
		   //handle all the parameters
		   final ArrayList<FormalParameter> fList = new ArrayList<>();
		   fList.add(list.f0);
		   if (list.f1 != null){
			   for (final Node rest : list.f1.nodes){
				   fList.add(((FormalParameterRest)rest).f1);
			   }
		   }
		   for (final FormalParameter p : fList){
			   final Variable parameter = new Variable();
			   if (p.f0.f0.which == 3){
	    		   final Node id = p.f0.f0.choice;
	    		   parameter.setType(((Identifier)id).get_name());
	    	   }
			   parameter.setType(p.f0.f0.which);
			   parameter.setName(p.f1.get_name());
			   ((Method)method).parameter_map.put(parameter.getName(), parameter);
		   }
	   }
	   Set<String> list1 = ((Method)method).parameter_map.keySet();
	   for (final Node node : n.f7.nodes){
		   //handle all the variables in this function
    	   final Variable variable = new Variable();
    	   node.accept(this, (A) variable);
    	   ((Method)method).add_map(_Type.Var, variable.getName(), variable);
    	   if (list1.contains(variable.getName()))
    		   error.print_stacktrace("redefinition", variable.getDefine_line());
	   }
	}	
}
