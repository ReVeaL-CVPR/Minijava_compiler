package MiniJava.piglet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import MiniJava.piglet.translator;

import MiniJava.typecheck.symboltable.*;
import minijava_syntaxtree.*;
import minijava_visitor.GJVoidDepthFirst;

public class translate_visitor<A> extends GJVoidDepthFirst<A>{

	Dispatched_table table = null;
	int temp_size = 20; 
	int label_size = 0;
	HashMap<String, Integer> var_table = null;
	int indent = 0;
	String func_scope = null;
	String class_handler = null;
	
	
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
	int ccq = 0;
   public void visit(final MainClass n, final A class1) {
	   var_table = new HashMap<>();
	   table = translator.classes.get(n.f1.get_name());
	   print_pigletln("MAIN", indent++);
	   print_pigletln("MOVE TEMP 0 HALLOCATE " + 4 * table.size(), indent); 
	   for (int i = 0; i < table.size(); ++i)
		   print_pigletln(String.format("HSTORE TEMP 0 %d 0", 4 * i), indent);
	   super.visit(n.f14, (A)class1);
	   super.visit(n.f15, (A)class1);
	   print_pigletln("END", --indent);
   }
   /**
    * AssignmentStatement
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
   public void visit(AssignmentStatement n, A argu) {
	   String name = n.f0.get_name();
	   int loc = table.get_loc(name, translator.var);
	   if (loc == -1){
		   if (!var_table.containsKey(func_scope + "_" + name)){
			   int tmp = temp_size++;
			   var_table.put(func_scope + "_" + name, tmp);
			   print_pigletln(String.format("MOVE TEMP %d ", tmp), indent++);
		   }
		   else{
			   loc = var_table.get(func_scope + "_" + name);
			   if(loc >= 0)
				   print_pigletln(String.format("MOVE TEMP %d ", loc), indent++);
			   else
				   print_pigletln(String.format("HSTORE TEMP 1 %d", - 4 * loc), indent++);
		   }
	   }
	   else
		   print_pigletln(String.format("HSTORE TEMP 0 %d ", 4 * loc), indent++);
       n.f2.accept(this, argu);
       --indent;
   }
   
   
   /**
    * ArrayAssignmentStatement
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> Expression()
    * f3 -> "]"
    * f4 -> "="
    * f5 -> Expression()
    * f6 -> ";"
    */
   public void visit(ArrayAssignmentStatement n, A argu) {
	   String name = n.f0.get_name();
	   int exp = temp_size ++;
	   print_pigletln(String.format("MOVE TEMP %d ", exp), indent++);
       n.f2.accept(this, argu); 
       --indent;
       int arr_addr = 0;
	   int loc = table.get_loc(n.f0.get_name(), translator.var);
	   if (loc == -1)
	   {
		   loc = var_table.get(func_scope + "_" + name);
		   if(loc >= 0)
			   // less than 20 variables
			   arr_addr = loc;
		   else{
			   // more than 20 variables
			   arr_addr = temp_size ++;
			   print_pigletln(String.format("HLOAD TEMP %d TEMP 1 %d", 
		    		   arr_addr, -4 * loc), indent); 
		   }
	   }
	   else{
		   // in-class arrays
		   arr_addr = temp_size ++;
		   print_pigletln(String.format("HLOAD TEMP %d TEMP 0 %d", 
	    		   arr_addr, 4 * loc), indent); 
	   }
       int arr_size = temp_size ++;
       print_pigletln(String.format("HLOAD TEMP %d TEMP %d 0", arr_size, arr_addr), indent);       
       print_pigletln(String.format("CJUMP MINUS 1 LT TEMP %d TEMP %d L%d ERROR",
    		   exp, arr_size, label_size), indent); 
       print_pigletln(String.format("L%d NOOP", label_size++), 0);  
	   print_pigletln(String.format("HSTORE PLUS TEMP %d TIMES 4 PLUS 1 TEMP %d 0",
			   arr_addr, exp), indent);
	   n.f5.accept(this, argu);
   }

   /**
    * IfStatement
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    * f5 -> "else"
    * f6 -> Statement()
    */
   public void visit(IfStatement n, A argu) {
       print_pigletln("CJUMP ", indent++); 
       n.f2.accept(this, argu);
       int l = label_size++, q = label_size++; 
       print_pigletln("L" + l, indent--); 
       n.f4.accept(this, argu);
       print_pigletln("JUMP L" + q, indent);
       print_pigletln("L" + l, 0); 
       n.f6.accept(this, argu);
       print_pigletln("L" + q + " NOOP", 0);
   }

   /**
    * WhileStatement
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
   public void visit(WhileStatement n, A argu) {
       print_pigletln("CJUMP ", indent++); 
       n.f2.accept(this, argu);
       int l1 = label_size++;
       print_pigletln("L" + l1 + "", --indent);
       int l2 = label_size++;
       print_pigletln("L" + l2, 0); 
       n.f4.accept(this, argu);
       print_pigletln("CJUMP ", indent++); 
       n.f2.accept(this, argu);
       print_pigletln("L" + l1,--indent);
       print_pigletln("JUMP L" + l2, indent);
       print_pigletln("L" + l1 + " NOOP", 0); 
   }

   /**
    * PrintStatement
    * f0 -> "print_pigletln"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
    */
   public void visit(PrintStatement n, A argu) {
	   print_pigletln("PRINT", indent++); 
	   n.f2.accept(this, argu); 
	   --indent;
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
   public void visit(final ClassDeclaration n, A argu) {
	   table = translator.classes.get(n.f1.get_name());
	   n.f4.accept(this, argu);
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
   public void visit(final ClassExtendsDeclaration n, A argu) {
	   table = translator.classes.get(n.f1.get_name());
	   n.f6.accept(this, argu);
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
   public void visit(MethodDeclaration n, A argu) {
	   
	   int size = 0;
	   Node node = n.f4.node;
	   ArrayList<String> params = new ArrayList<>();
	   if(node != null){
		   params.add(((FormalParameterList)node).f0.f1.get_name());
		   for(Node parameter : ((FormalParameterList)node).f1.nodes)
			   params.add(((FormalParameterRest)parameter).f1.f1.get_name());
	   }
	   size = params.size();
       temp_size = 20;
	   func_scope = n.f2.get_name();
	   if(size >= 20){
		   for (int i = 0; i < size; ++i)
			   var_table.put(func_scope+"_"+params.get(i), -i);
	   }
	   else{
		   for (int i = 0; i < size; ++i)
			   var_table.put(func_scope+"_"+params.get(i), i+1);
	   }
	   print_pigletln(String.format("\n\n%s_%s [%d]", 
			   table.cname, n.f2.get_name(), size + 1), indent++);
	   print_pigletln("BEGIN", indent++);
	   n.f8.accept(this, argu);
	   --indent;
	   print_pigletln("RETURN", indent++);
	   n.f10.accept(this, argu);
	   --indent;
	   print_pigletln("END", --indent);
	   
   }
   
   
   /**
    * f0 -> PrimaryExpression()
    * f1 -> "&&"
    * f2 -> PrimaryExpression()
    */
   public void visit(AndExpression n, A argu) {
	   int l = label_size++;
       int q = label_size++;
	   print_pigletln("BEGIN", indent++); 
	   print_pigletln("CJUMP LT 0", indent++); 
       n.f0.accept(this, argu);
       print_pigletln("L" + l, --indent);
	   print_pigletln("CJUMP LT 0", indent++); 
       n.f2.accept(this, argu);
       print_pigletln("L" + l, --indent);
       int tmp = temp_size++;
       print_pigletln("MOVE TEMP " + tmp + " 1", indent);
       print_pigletln("JUMP L" + q, indent);
       print_pigletln("L" + l, 0);
       print_pigletln("MOVE TEMP " + tmp + " 0", indent);
       print_pigletln("L" + q + " NOOP", 0);
       print_pigletln("RETURN TEMP " + tmp, indent);
       print_pigletln("END", --indent);
   }
   
   /**
    * f0 -> PrimaryExpression()
    * f1 -> "<"
    * f2 -> PrimaryExpression()
    */
   public void visit(CompareExpression n, A argu) {
	   print_pigletln("LT ", indent ++);
       n.f0.accept(this, argu);
       n.f2.accept(this, argu);
       --indent;
   }
   
   /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
   public void visit(PlusExpression n, A argu) {
	   print_pigletln("PLUS ", indent ++);
       n.f0.accept(this, argu);
       n.f2.accept(this, argu);
       --indent;
   }
   
   /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
   public void visit(MinusExpression n, A argu) {
	   print_pigletln("MINUS", indent ++);
       n.f0.accept(this, argu);
       n.f2.accept(this, argu);
       --indent;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
   public void visit(TimesExpression n, A argu) {
	   print_pigletln("TIMES", indent ++);
       n.f0.accept(this, argu);
       n.f2.accept(this, argu);
       --indent;
   }
   
   /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
   public void visit(ArrayLookup n, A argu) {
	   print_pigletln("BEGIN", indent ++);  
	   int arr_addr = temp_size++;
       print_pigletln("MOVE TEMP " + arr_addr, indent++); 	   
	   n.f0.accept(this, argu);
       int arr_size = temp_size ++;
       print_pigletln(String.format("HLOAD TEMP %d TEMP %d 0", 
    		   arr_size, arr_addr), --indent);
       int exp = temp_size++;
       print_pigletln("MOVE TEMP " + exp, indent++);
       n.f2.accept(this, argu);
       print_pigletln(String.format("CJUMP MINUS 1 LT TEMP %d TEMP %d L%d ERROR",
    		   exp, arr_size, label_size), --indent); 
       int ret = temp_size++;
       print_pigletln(String.format("L%d NOOP", label_size++), 0); 
       print_pigletln(String.format("HLOAD TEMP %d PLUS TEMP %d TIMES 4 PLUS 1 TEMP %d 0",
    		   ret, arr_addr, exp), indent);
       print_pigletln("RETURN TEMP " + ret, indent);
       print_pigletln("END", --indent);
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
   public void visit(ArrayLength n, A argu) {
	   print_pigletln("BEGIN", indent);  
	   int arr_addr = temp_size++;
       print_pigletln("MOVE TEMP " + arr_addr, indent); 	   
	   n.f0.accept(this, argu);
       int arr_size = temp_size ++;
       print_pigletln(String.format("HLOAD TEMP %d TEMP %d 0", arr_size, arr_addr), --indent);
       print_pigletln("RETURN TEMP " + arr_size, indent);
       print_pigletln("END", --indent);
   }
   
   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( ExpressionList() )?
    * f5 -> ")"
    */
   public void visit(MessageSend n, A argu) {

	   int lei = temp_size++;
       print_pigletln("CALL", indent++);
	   print_pigletln("BEGIN MOVE TEMP " + lei, indent++);
	   n.f0.accept(this, argu);
       String name = n.f2.get_name();
       Method method = translator.symbol_table_root.get_class(class_handler).get_method(name);
       Set<String> params = method.parameter_map.keySet();
       String[] args = new String[params.size()];
       int cnt = 0;
       for (String param : params)
    	   args[cnt++] = param;
       int loc = translator.classes.get(class_handler).get_loc(name, translator.func);
       int l1 = temp_size++;
	   print_pigletln(String.format("HLOAD TEMP %d TEMP %d %d", l1, lei, 4 * loc), indent);
	   print_pigletln("RETURN TEMP " + l1, indent);
	   print_pigletln("END", --indent);
	   print_pigletln("(", indent);
	   print_pigletln("TEMP " + lei, ++indent);
       ExpressionList list = (ExpressionList)n.f4.node;
       if (list != null){
    	   ArrayList<Expression> arrayList = new ArrayList<>();
    	   arrayList.add(list.f0);
    	   for (Node node: list.f1.nodes)
    		   arrayList.add(((ExpressionRest)node).f1);
    	   int size = arrayList.size();
    	   if (size < 20){
    		   for (int i = 0; i < size; ++i){
    			   int l = temp_size ++;
    			   arrayList.get(i).accept(this, argu);
    		   }
    	   }
    	   else{
    		   int pnt = temp_size++;
    		   print_pigletln(String.format("BEGIN MOVE TEMP %d HALLOCATE %d", pnt, 4 * size), indent++);
    		   for (int i = 0; i < size; ++i){
    			   print_pigletln(String.format("HSTORE TEMP %d %d", pnt, 4 * i), indent);
    			   arrayList.get(i).accept(this, argu);
    		   }
    		   print_pigletln(String.format("RETURN TEMP %d", pnt), indent);
    		   print_pigletln("END", --indent);
    	   }
       }
       print_pigletln(")", --indent);
       --indent;
       class_handler = method.getType().c;
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
   public void visit(PrimaryExpression n, A argu) {
	  Node node = n.f0.choice;
	  if (node instanceof IntegerLiteral)
		  print_pigletln(((IntegerLiteral) node).f0.tokenImage, indent);
	  else if (node instanceof TrueLiteral)
		  print_pigletln("1", indent);
	  else if (node instanceof FalseLiteral)
		  print_pigletln("0", indent);
	  else if (node instanceof Identifier)
	  {
		  String name = ((Identifier) node).get_name();
		  int loc = table.get_loc(name, translator.var);
		  if (loc == -1){
			   loc = var_table.get(func_scope+"_"+name);
			   if(loc >= 0)
				   print_pigletln(String.format("TEMP %d ", loc), indent);
			   else{
				   int tmp = temp_size++;
				   print_pigletln("BEGIN", indent++);
				   print_pigletln(String.format("HLOAD TEMP %d TEMP 1 %d RETURN TEMP %d",
						   tmp, - 4 * loc, tmp), indent);
				   print_pigletln("END", --indent);
			   }
		   }
		   else{
			   int tmp = temp_size++;
			   print_pigletln("BEGIN", indent++);
			   print_pigletln(String.format("HLOAD TEMP %d TEMP 0 %d RETURN TEMP %d",
					   tmp, 4 * loc, tmp), indent);
			   print_pigletln("END", --indent);
		   }
		  Variable variable = null;
		  _Class cl_Class = translator.symbol_table_root.get_class(table.cname);
		  Method method = cl_Class.get_method(func_scope);
		  ArrayList<Abstract_Symbol> parents = new ArrayList<>();
		  parents.add(cl_Class);
		  while(true){
			  cl_Class = translator.symbol_table_root.get_class(cl_Class.father);
		      if (cl_Class == null || parents.contains(cl_Class))
		    	  break;
			  parents.add(cl_Class);
		  }
		  for (Abstract_Symbol pClass : parents){
			  variable = pClass.get_variable(name);
			  if (variable != null)
				  break;
		  }
		  if (variable == null){
			  variable = method.get_variable(name);
			  if (variable == null){
				  variable = method.parameter_map.get(name);
			  }
		  }
		  class_handler = variable.getType().c;
	  }
	  else if (node instanceof ThisExpression){
		  class_handler = table.cname;
		  print_pigletln("TEMP 0", indent);
	  }
	  else if (node instanceof ArrayAllocationExpression){
		  /**
		   * ArrayAllocationExpression
		   * Grammar production:
		   * f0 -> "new"
		   * f1 -> "int"
		   * f2 -> "["
		   * f3 -> Expression()
		   * f4 -> "]"
		   */
		  int exp = temp_size ++;
		  print_pigletln("BEGIN", indent++);
		  print_pigletln("MOVE TEMP " + exp, indent);
		  ((ArrayAllocationExpression) node).f3.accept(this, argu);
		  int arr_addr = temp_size++;
		  print_pigletln(String.format("MOVE TEMP %d HALLOCATE TIMES 4 PLUS 1 TEMP %d", arr_addr, exp), indent);
		  print_pigletln(String.format("HSTORE TEMP %d 0 TEMP %d", arr_addr, exp), indent);
		  int tmp = temp_size ++;
		  int jmp = label_size++;
		  int jmp2 = label_size++;
		  print_pigletln(String.format("MOVE TEMP %d 1", tmp), indent);
		  print_pigletln(String.format("CJUMP LT TEMP %d TEMP %d L%d", tmp, exp, jmp), indent);
		  print_pigletln(String.format("L%d", jmp2), 0);
		  print_pigletln(String.format("HSTORE PLUS TEMP %d TIMES 4 TEMP %d 0 0", arr_addr, tmp), indent);
		  print_pigletln(String.format("MOVE TEMP %d PLUS TEMP %d 1", tmp, tmp), indent);
		  print_pigletln(String.format("CJUMP LT TEMP %d TEMP %d L%d", tmp, exp, jmp), indent);
		  print_pigletln(String.format("JUMP L%d", jmp2), indent);
		  print_pigletln(String.format("L%d NOOP", jmp), 0);
		  print_pigletln("RETURN TEMP " + arr_addr, indent);
		  print_pigletln("END", --indent);
	  }
	  else if (node instanceof AllocationExpression){
		  /**
		   * AllocationExpression
		   * Grammar production:
		   * f0 -> "new"
		   * f1 -> Identifier()
		   * f2 -> "("
		   * f3 -> ")"
		   */
		  print_pigletln("BEGIN", indent++);
		  String name = ((AllocationExpression) node).f1.get_name();
		  Dispatched_table tab = translator.classes.get(name);
		  int size = tab.size();
		  int init = temp_size++;
		  print_pigletln(String.format("MOVE TEMP %d HALLOCATE %d", init, 4 * size), indent);
		  int loc = 0;
		  for (String _s : tab.keySet()){
			  ArrayList<para> list = tab.get(_s);
			  for (para p : list)
			  {
				  if (p.type == translator.func)
					  print_pigletln(String.format("HSTORE TEMP %d %d %s", 
							  init, 4 * loc, p.get_name()), indent);
				  else
					  print_pigletln(String.format("HSTORE TEMP %d %d 0", init, 4 * loc), indent);
				  ++loc; 
			  }
		  }
		  print_pigletln(String.format("RETURN TEMP %d", init), indent);
		  print_pigletln("END", --indent);
		  class_handler = name;
	  }
	  else if (node instanceof NotExpression){
		  /**
		   * Grammar production:
		   * f0 -> "!"
		   * f1 -> Expression()
		   */
		  print_pigletln("LT ", indent++);
		  ((NotExpression) node).f1.accept(this, argu);
		  print_pigletln("1", indent--);
	  }
	  else if (node instanceof BracketExpression)
		  ((BracketExpression)node).f1.accept(this, argu);
   }
   
   public static void print_piglet(String s, int indent){
	   String res = "";
	   for (int i = 0; i < indent; ++i)
		   res += "    ";
	   res += s;
	   try {
		translator.writer.write(res);
		translator.writer.flush();
	   } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	   }
   }
   public static void print_pigletln(String s, int indent){
	   String res = "";
	   for (int i = 0; i < indent; ++i)
		   res += "   ";
	   res += s;
	   try {
		translator.writer.write(res + '\n');
		translator.writer.flush();
	   } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	   }
   }
}
