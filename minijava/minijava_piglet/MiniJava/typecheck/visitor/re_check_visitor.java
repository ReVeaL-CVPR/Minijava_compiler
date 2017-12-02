package MiniJava.typecheck.visitor;

import java.util.ArrayList;
import Error_handle.error;
import MiniJava.typecheck.symboltable.*;
import minijava_syntaxtree.*;
import minijava_visitor.GJVoidDepthFirst;

public class re_check_visitor<A> extends GJVoidDepthFirst<A>{
	
	
	
	   public static _Type type_int     = new _Type(_Type._int);
	   public static _Type type_class   = new _Type(_Type._class);
	   public static _Type type_boolean = new _Type(_Type._boolean);
	   public static _Type type_array   = new _Type(_Type._array);
	   
	   private static Root _root;

	   
	   public boolean is_child(String a, String b){
		   boolean flag = false;
		   while(a != null){
			   if (a.equals(b)){
				   flag = true;
				   break;
			   }
			   a = _root.get_class(a).father;
		   }
		   return flag;
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
		       String class_name = n.f1.get_name();
		       _Class class1 = ((Abstract_Symbol)root).get_class(class_name);
			   super.visit(n, (A)class1);
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
		       String class_name = n.f1.get_name();
		       _Class class1 = ((Abstract_Symbol)root).get_class(class_name);
			   super.visit(n, (A)class1);
		   }catch(final Exception e){
			   e.printStackTrace();
		   }
	   }
	   

	   
	   public _Type PrimaryExpression_type(PrimaryExpression expression, ArrayList<Abstract_Symbol> parents){
		   /**
		    * Grammar production:
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
		   Node node = expression.f0.choice;
		   if (node instanceof IntegerLiteral) 
			   return new _Type(_Type._int);
		   else if (node instanceof TrueLiteral || node instanceof FalseLiteral){
			   return type_boolean;
		   }
		   else if (node instanceof NotExpression){
			   _Type type = Expression_type(((NotExpression)node).f1, parents);
			   if (type.t != _Type._boolean)
				   error.print_stacktrace("not expression must be boolean", ((NotExpression)node).f0.beginLine);
			   return type_boolean;
		   }
		   else if (node instanceof Identifier){
			   int line = ((Identifier)node).f0.beginLine;
			   String var_name = ((Identifier)node).get_name();
			   Variable variable = get_variable(var_name, line, parents);
			   if (variable == null){
				   error.print_stacktrace("undifined variable",  line);
				   return null;
			   }
			   else if (!variable.Is_init())
				   error.print_stacktrace("variable used before initilization",  line);
			   return variable.getType();
		   }
		   else if (node instanceof ThisExpression){
			  return new _Type(_Type._class, parents.get(0).getName());
		   }
		   else if (node instanceof ArrayAllocationExpression){
			   _Type type = Expression_type(((ArrayAllocationExpression)node).f3, parents);
			   if (type == null)
				   return null;
			   if (type.t != _Type._int)
				   error.print_stacktrace("array size must be integer", ((ArrayAllocationExpression)node).f1.beginLine);
			   return type_array;
		   }
		   else if (node instanceof AllocationExpression){
			   int line = ((AllocationExpression)node).f1.f0.beginLine;
			   String var_name = (((AllocationExpression)node).f1).get_name();
			   _Class class1 = _root.get_class(var_name);
			   if (class1 == null){
				   error.print_stacktrace("undifined class",  line);
				   return null;
			   }
			   return new _Type(_Type._class, var_name);
		   } 
		   else if (node instanceof BracketExpression){
			  return Expression_type(((BracketExpression)node).f1, parents);
		   }
		   return null;
	   }
	   
	   public _Type Expression_type(Expression expression, ArrayList<Abstract_Symbol> parents){
		   /**
		    * Grammar production:
		    * f0 -> AndExpression()
		    *       | CompareExpression()
		    *       | PlusExpression()
		    *       | MinusExpression()
		    *       | TimesExpression()
		    *       | ArrayLookup()
		    *       | ArrayLength()
		    *       | MessageSend()
		    *       | PrimaryExpression()
		    */
		   Node node = expression.f0.choice;
		   if (node instanceof CalculateExpression)
		   {
			   _Type tl = PrimaryExpression_type(((CalculateExpression)node).f0, parents);
			   _Type tr = PrimaryExpression_type(((CalculateExpression)node).f2, parents);
			   if (tl != null && tr != null && (tl.t != _Type._int || tr.t != _Type._int))
				   error.print_stacktrace("calculation type must be integer", ((CalculateExpression)node).f1.beginLine);
		   }
		   if (node instanceof AndExpression || node instanceof CompareExpression){
			   return type_boolean;
		   }
		   else if (node instanceof PlusExpression  || node instanceof MinusExpression
				 || node instanceof TimesExpression || node instanceof ArrayLength){
			   return new _Type(_Type._int);
		   }
		   else if (node instanceof ArrayLookup)
		   {
			   _Type type = PrimaryExpression_type(((ArrayLookup)node).f0, parents);
			   if (type == null)
				   return null;
			   if (type.t != _Type._array)
				   error.print_stacktrace("array type error", ((ArrayLookup)node).f1.beginLine);
			   type = PrimaryExpression_type(((ArrayLookup)node).f2, parents);
			   if (type.t != _Type._int)
				   error.print_stacktrace("array index must be integer", ((ArrayLookup)node).f1.beginLine);
			   return type_int;
		   }
		   else if (node instanceof MessageSend)
		   {
			   int line = ((MessageSend)node).f1.beginLine;
			   _Type caller = PrimaryExpression_type(((MessageSend)node).f0, parents);
			   if (caller == null){
				   return null;
			   }
			   if (caller.t != _Type._class){
				   error.print_stacktrace("caller must be a class",  line);
				   return null;
			   }
			   String func = ((MessageSend)node).f2.get_name();
			   Method method = null;
			   _Class p_Class = _root.get_class(caller.c);
			   if (p_Class == null){
				   error.print_stacktrace("variable not defined",  line);
				   return null;
			   }
			   ArrayList<_Class> stack = new ArrayList<>();
			   stack.add(p_Class);
			   while(true){
				   p_Class = _root.get_class(p_Class.father);
				   if (p_Class == null || stack.contains(p_Class))
					   break;
				   stack.add(p_Class);
			   }
			   for (_Class cl : stack){
				   method = cl.get_method(func);
				   if (method != null){
					   break;
				   }
			   }
			   if (method == null){
				   error.print_stacktrace("method not define",  ((MessageSend)node).f1.beginLine);
				   return null;
			   }
			   ExpressionList params = (ExpressionList)((MessageSend)node).f4.node;
			   ArrayList<Expression> param_list = new ArrayList<>();
			   if (params != null){
				   param_list.add(params.f0);
				   NodeListOptional optional = params.f1;
				   for (Node node2 : optional.nodes){
					   param_list.add(((ExpressionRest)node2).f1);
				   }
			   }
			   _Type[] type_list = method.get_type_list();
			   if (param_list.size() != type_list.length) {
				   error.print_stacktrace("param list length error " + param_list.size() + " " + type_list.length,  ((MessageSend)node).f1.beginLine);
				   return null;
			   }
			   int cnt = 0;
			   for (Expression exp : param_list){
				   _Type tp = Expression_type(exp, parents);
				   if (tp == null){
					   error.print_stacktrace("undifined variable",  line);
					   return null;
				   }
				   if (!tp.compareTo(type_list[cnt++])){
					   error.print_stacktrace("param list type error",  ((MessageSend)node).f1.beginLine);
					   return null;
				   }
			   }
			   return method.getType();
		   }
		   else if (node instanceof PrimaryExpression)
		   {
			   return PrimaryExpression_type((PrimaryExpression)node, parents);
		   }
		   return null;
	   }
	   
	   public void type_check(_Type type, Expression expression, int line, ArrayList<Abstract_Symbol> parents, String msg){
		   _Type ret = Expression_type(expression, parents);
		   if (ret == null)
			   return;
		   if (type.t == _Type._class && ret.t == _Type._class && is_child(ret.c, type.c))
			   return;
		   if (!type.compareTo(ret))
			   error.print_stacktrace(msg, line);
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
	   
	   
	   public Variable get_variable(String var_name, int line, ArrayList<Abstract_Symbol> parents){
		   Variable variable = null;
		   for (Abstract_Symbol parent : parents){
			   variable = parent.get_variable(var_name);
			   if (variable != null)
				   break;
		   }
		   return variable;
	   }
	   
	   private void statement_check(ArrayList<Statement> arrayList, ArrayList<Abstract_Symbol> parents){
		   /**
		    * Grammar production:
		    * f0 -> Block()
		    *       | AssignmentStatement()
		    *       | ArrayAssignmentStatement()
		    *       | IfStatement()
		    *       | WhileStatement()
		    *       | PrintStatement()
		    */
		   while (!arrayList.isEmpty()){
			   Statement statement = arrayList.remove(0);
			   Node choice = statement.f0.choice;
	    	   if (choice instanceof Block){
	    		   for (Node node : ((Block)choice).f1.nodes)
	    			   arrayList.add((Statement)node);
	    	   }
	    	   else if (choice instanceof AssignmentStatement){
	    		   int line = ((AssignmentStatement) choice).f1.beginLine;
	    		   String var_name = ((AssignmentStatement)choice).f0.get_name();
	    		   Variable variable = get_variable(var_name, line, parents);
	    		   if (variable == null) {
	    			   error.print_stacktrace("undifined variable", line);
	    			   continue;
	    		   }
	    		   type_check(variable.getType(), ((AssignmentStatement)choice).f2, line, parents, "assign type error");
	    		   variable.set_init(true);
	    	   }
	    	   else if (choice instanceof ArrayAssignmentStatement){
	    		   int line = ((ArrayAssignmentStatement) choice).f1.beginLine;
	    		   String var_name = ((ArrayAssignmentStatement)choice).f0.get_name();
	    		   Variable variable = get_variable(var_name, line, parents);
	    		   if (variable == null) {
	    			   error.print_stacktrace("undifined variable", line);
	    			   continue;
	    		   }
	    		   if (variable.getType().t != _Type._array)
	    			   error.print_stacktrace("array type error", line);
	    		   type_check(new _Type(_Type._int), ((ArrayAssignmentStatement)choice).f2, line, parents, "array index must be interger");
	    		   type_check(new _Type(_Type._int), ((ArrayAssignmentStatement)choice).f5, line, parents, "arrayassign type error");
	    		   variable.set_init(true);
	    	   }
	    	   else if (choice instanceof IfStatement){
	    		   int line = ((IfStatement)choice).f0.beginLine;
	    		   type_check(new _Type(_Type._boolean), ((IfStatement)choice).f2, line, parents, "if expression must be boolean");
	    		   arrayList.add(((IfStatement)choice).f4);
	    		   arrayList.add(((IfStatement)choice).f6);
	    	   }
	    	   else if (choice instanceof WhileStatement){
	    		   int line = ((WhileStatement)choice).f0.beginLine;
	    		   type_check(new _Type(_Type._boolean), ((WhileStatement)choice).f2, line, parents, "while expression must be boolean");
	    		   arrayList.add(((WhileStatement)choice).f4);
	    	   }
	    	   else if (choice instanceof PrintStatement){
	    		   int line = ((PrintStatement)choice).f0.beginLine;
	    		   _Type type = Expression_type(((PrintStatement)choice).f2, parents);
	    		   if (type == null)
	    			   break;
	    		   if (type.t != _Type._array && type.t != _Type._int)
	    			   error.print_stacktrace("print expression error " + type.t, line);
	    	   }
		   }
	   }
	   
	   

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
	   public void visit(final MainClass n, final A class1) {
		   _root = (Root)class1;
		   ArrayList <Statement> arrayList = new ArrayList<>();		   
		   for (final Node node : n.f15.nodes)
			   arrayList.add((Statement)node);
		   ArrayList<Abstract_Symbol> parents = new ArrayList<>();
		   parents.add(_root);
		   statement_check(arrayList, parents);
		   super.visit(n, class1);
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
	   public void visit(final MethodDeclaration n, final A class1) {
		   String method_name = ((MethodDeclaration) n).f2.get_name();
		   Method method = ((_Class)class1).get_method(method_name);
		   ArrayList <Statement> arrayList = new ArrayList<>();		   
		   for (final Node node : n.f8.nodes)
			   arrayList.add((Statement)node);
		   ArrayList<Abstract_Symbol> parents = new ArrayList<>();
		   parents.add((_Class)class1);
		   parents.add(method);
		   _Class p_Class = (_Class)class1;
		   while(true){
			   p_Class = _root.get_class(p_Class.father);
			   if (p_Class == null || parents.contains(p_Class))
				   break;
			   parents.add(p_Class);
		   }
		   statement_check(arrayList, parents);
		   _Type type = Expression_type(((MethodDeclaration) n).f10, parents);
		   Boolean flag = true;
		   _Type mt = method.getType();
		   if (type == null){
			   if (mt != null)
				   flag = false;
		   }
		   else{
			   if (type.t != _Type._class || mt.t != _Type._class || !is_child(type.c, mt.c)){
				   if(!type.compareTo(method.getType()))
					   flag = false;
			   }  
		   }
		   if (!flag)
			   error.print_stacktrace("method return type error", ((MethodDeclaration) n).f9.beginLine);
		   super.visit(n, class1);
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
	    		   _Class class1 = _root.get_class(((Identifier) id).get_name());
	    		   if (class1 == null)
	    			   error.print_stacktrace("undifined class", ((VarDeclaration) node).f2.beginLine);
	    	   }
		   }catch(final Exception e){
			   e.printStackTrace();
		   }
	   }
}
