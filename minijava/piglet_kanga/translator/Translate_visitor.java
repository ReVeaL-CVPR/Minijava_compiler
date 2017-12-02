package translator;

import java.awt.SplashScreen;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import spiglet_syntaxtree.*;
import spiglet_visitor.GJNoArguDepthFirst;
import translator.Translator;

public class Translate_visitor<R> extends GJNoArguDepthFirst<R>{

	Flow_chart chart;
	Basic_block temp;
	Basic_block current;
	Operation opt;
	boolean v1_used = false;

	
	  /**
	    * f0 -> "MAIN"
	    * f1 -> StmtList()
	    * f2 -> "END"
	    * f3 -> ( Procedure() )*
	    * f4 -> <EOF>
	    */
	   public R visit(Goal n) {
		   chart = Translator.find_chart("MAIN");
		   System.out.println(String.format("MAIN [%d] [%d] [%d]",
				   0, chart.spills.size(), chart.max_args));
	       n.f1.accept(this);
	       System.out.println("END");
	       n.f3.accept(this);
	       return null;
	   }
	   

	   /**
	    * f0 -> Label()
	    * f1 -> "["
	    * f2 -> IntegerLiteral()
	    * f3 -> "]"
	    * f4 -> StmtExp()
	    */
	   public R visit(Procedure n) {
		   String name = n.f0.f0.toString();
		   chart = Translator.find_chart(name);
		   
		   int store = Integer.parseInt(n.f2.f0.toString());
		   if (store > 4)
			   chart.store = store - 4;
		   System.out.println(String.format("%s [%s] [%d] [%d]", name,
				   n.f2.f0.toString(), chart.get_spill_number(store), chart.max_args));
		   chart.inp_num = Integer.parseInt(n.f2.f0.toString());
	       n.f4.accept(this);
	       return null;
	   }

	   /**
	    * f0 -> "NOOP"
	    */
	   public R visit(NoOpStmt n) {
	       System.out.println("NOOP");
	       return null;
	   }

	   /**
	    * f0 -> "ERROR"
	    */
	   public R visit(ErrorStmt n) {
		   System.out.println("ERROR");
		   return null;
	   }
	   
	   public void recover(String param, String reg){
		   if (chart.spill_list.containsKey(param)) {
			   int loc = chart.spill_list.get(param);
			   System.out.println("ASTORE SPILLEDARG " + loc + " " + reg);
		   }
		   else{
			   System.out.println("ASTORE SPILLEDARG " + chart.store + " " + reg);
			   chart.spill_list.put(param, chart.store++);
		   }
	   }
	   
	   public String get_reg(String param){
		   if (chart.spills.contains(param)){
			   System.out.println("ALOAD v1 SPILLEDARG " + chart.spill_list.get(param));
			   if(v1_used)
				   return "v0";
			   v1_used = true;
			   return "v1";
		   }
		   else
			   return chart.get_col(param);
	   }
	  


	   /**
	    * f0 -> "CJUMP"
	    * f1 -> Temp()
	    * f2 -> Label()
	    */
	   public R visit(CJumpStmt n) {
		   Operation operation = chart.opt_table.get(n.f0.beginLine);
		   String param = operation.use_var.get(0);
		   String reg = get_reg(param);
		   System.out.print("CJUMP " + reg + " ");
	       n.f2.accept(this); 
	       v1_used = false;
	       return null;
	   }

	   /**
	    * f0 -> "JUMP"
	    * f1 -> Label()
	    */
	   public R visit(JumpStmt n) {
		   System.out.print("JUMP ");
	       n.f1.accept(this);
	       return null;
	   }

	   /**
	    * f0 -> "HSTORE"
	    * f1 -> Temp()
	    * f2 -> IntegerLiteral()
	    * f3 -> Temp()
	    */
	   public R visit(HStoreStmt n) {
		   Operation operation = chart.opt_table.get(n.f0.beginLine);
		   String tmp1 = operation.use_var.get(0);
		   String tmp2 = operation.use_var.get(1);
		   String reg1 = get_reg(tmp1);
		   String reg2 = get_reg(tmp2);
	       System.out.println("HSTORE " + reg1 + " " + n.f2.f0.toString() + " " + reg2);
	       
	       v1_used = false;
	       return null;
	   }

	   
	   /**
	    * f0 -> "HLOAD"
	    * f1 -> Temp()
	    * f2 -> Temp()
	    * f3 -> IntegerLiteral()
	    */
	   public R visit(HLoadStmt n) {
		   Operation operation = chart.opt_table.get(n.f0.beginLine);
		   String tmp1 = operation.def_var;
		   if(!chart.colors.containsKey(tmp1) && !chart.spills.contains(tmp1))
			   return null;
		   String tmp2 = operation.use_var.get(0);
		   String reg2 = get_reg(tmp2);
		   if (chart.spills.contains(tmp1)) {
			   System.out.println("HLOAD v1 " + reg2 + " " + n.f3.f0.toString());
			   recover(tmp1, "v1");
		   }
		   else{
			   String reg1 = chart.get_col(tmp1);
			  // System.out.println("color: " + tmp1 + " " + reg1);
			   System.out.println("HLOAD " + reg1 + " "+ reg2 + " " + n.f3.f0.toString());
		   }
		   v1_used = false;
	       return null;
	   }

	   /**
	    * f0 -> "MOVE"
	    * f1 -> Temp()
	    * f2 -> Exp()
	    */
	   public R visit(MoveStmt n) {
		   Operation operation = chart.opt_table.get(n.f0.beginLine);
		   String tmp1 = operation.def_var;
		   Node choice = n.f2.f0.choice;
		   if (choice instanceof Call) {
			   /**
			    * f0 -> "CALL"
			    * f1 -> SimpleExp()
			    * f2 -> "("
			    * f3 -> ( Temp() )*
			    * f4 -> ")"
			    */
		       int size = ((Call)choice).f3.size();
		       String reg = null;
		       Vector<Node> nodes = ((Call)choice).f3.nodes;
		       if (size <= 4){
		    	   for (int i = 0; i < size; ++i){
		    		   reg = (String)nodes.get(i).accept(this);
		    		   System.out.println("MOVE " + chart.savet[i] + " " + reg);
		    	   }
		       }
		       else{
		    	   for (int i = 0; i < 4; ++i){
		    		   reg = (String)nodes.get(i).accept(this);
		    		   System.out.println("MOVE " + chart.savet[i] + " " + reg);
		    	   }
		    	   for (int i = 4; i < size; ++i){
		    		   String name = ((Temp)nodes.get(i)).f1.f0.toString();
		    		   reg = get_reg(name);
		    		   System.out.println("PASSARG " + (i-3) + " " + reg);
		    	   }
		       }
		       reg = (String)((Call)choice).f1.accept(this);
		       System.out.println("CALL " + reg);
		       if(!operation.alive.contains(tmp1))
				   return null;
		       if (chart.spills.contains(tmp1))
		    	   recover(tmp1, "v0");
		       else{
				   String reg1 = chart.get_col(tmp1);
				   System.out.println("MOVE " + reg1 + " v0");
			   }
		       
		   }
		   else if (choice instanceof HAllocate) {
			   if(!operation.alive.contains(tmp1))
				   return null;
			   /**
			    * f0 -> "HALLOCATE"
			    * f1 -> SimpleExp()
			    */
			   String reg = (String)(((HAllocate)choice).f1.accept(this));
			   if (chart.spills.contains(tmp1)){
				   System.out.println("MOVE v1 HALLOCATE " + reg);
		    	   recover(tmp1, "v1");
			   }
		       else{
				   String reg1 = chart.get_col(tmp1);
				   System.out.println("MOVE " + reg1 + " HALLOCATE " + reg);
			   }
		   }
		   else if (choice instanceof BinOp) {
			   if(!operation.alive.contains(tmp1))
				   return null;
			   /**
			    * f0 -> Operator()
			    * f1 -> Temp()
			    * f2 -> SimpleExp()
			    */
		       String reg1 = (String)((BinOp)choice).f1.accept(this);
		       String reg2 = (String)((BinOp)choice).f2.accept(this);
		       if (chart.spills.contains(tmp1)){
		    	   System.out.print("MOVE v1 ");
		    	   ((BinOp)choice).f0.accept(this);
			       System.out.println(" " + reg1 + " " + reg2);
		    	   recover(tmp1, "v1");
		       }
		       else{
				   String reg = chart.get_col(tmp1);
				   System.out.print("MOVE " + reg + " ");
				   ((BinOp)choice).f0.accept(this);
			       System.out.println(" " + reg1 + " " + reg2);
			   }
		   }
		   else if (choice instanceof SimpleExp) {
			   if(!operation.alive.contains(tmp1))
				   return null;
			   if (chart.spills.contains(tmp1)){
				   String ret = (String)((SimpleExp)choice).accept(this);
		    	   System.out.println("MOVE v1 " + ret);
		    	   recover(tmp1, "v1");
		       }
		       else{
		    	   String ret = (String)((SimpleExp)choice).accept(this);
				   String reg = chart.get_col(tmp1);
				   System.out.println("MOVE " + reg + " " + ret);
			   }
		   }
		   v1_used = false;
		   return null;
	   }

	   /**
	    * f0 -> "PRINT"
	    * f1 -> SimpleExp()
	    */
	   public R visit(PrintStmt n) {
		   String ret = (String)n.f1.accept(this);
	       System.out.println("PRINT " + ret);
	       v1_used = false;
	       return null;
	   }
	   
	   /**
	    * f0 -> Temp()
	    *       | IntegerLiteral()
	    *       | Label()
	    */
	   public R visit(SimpleExp n) {
		   if (n.f0.choice instanceof Label) {
			   return (R)((Label)n.f0.choice).f0.toString();
		   }
	       return n.f0.accept(this);
	   }


	   /**
	    * f0 -> "BEGIN"
	    * f1 -> StmtList()
	    * f2 -> "RETURN"
	    * f3 -> SimpleExp()
	    * f4 -> "END"
	    */
	   public R visit(StmtExp n) {
		   ArrayList<Integer> saved = new ArrayList<>();
	       for (Integer i: chart.colors.values()){
	    	   if (!saved.contains(i)){
	    		   System.out.println("ASTORE SPILLEDARG " + chart.store + " " + chart.table[i]);
	    		   chart.spill_list.put("store_" + i, chart.store++);
	    		   saved.add(i);
	    	   }
	       }
	       int inp_num = chart.inp_num;
	       if (inp_num <= 4){
	    	   for (int i = 0; i < inp_num; ++i){
	    		   String temp = ""+i;
	    		   if (chart.colors.containsKey(temp))
	    			   System.out.println("MOVE " + chart.get_col(temp) + " " + chart.savet[i]);
	    	   }
	       }
	       else{
	    	   for (int i = 0; i < 4; ++i){
	    		   String temp = ""+i;
	    		   if (chart.colors.containsKey(temp))
	    			   System.out.println("MOVE " + chart.get_col(temp) + " " + chart.savet[i]);
	    	   }
	    	   for (int i = 4; i < inp_num; ++i){
	    		   String temp = ""+i;
	    		   if (chart.colors.containsKey(temp))
	    			   System.out.println("ALOAD " + chart.get_col(temp) + " SPILLEDARG " + (i-4));
	    	   }
	       }
	       n.f1.accept(this);
	       String reg = (String)n.f3.accept(this);
	       System.out.println("MOVE v0 " + reg);
	       for (Integer i: chart.colors.values()){
	    	   if (saved.contains(i)) {
	    		   System.out.println("ALOAD " + chart.table[i] + " SPILLEDARG " + chart.spill_list.get("store_" + i));
	    		   chart.spill_list.remove("store_" + i);
	    		   saved.remove(i);
	    	   }   
	       }
	       System.out.println("END");
	       return null;
	   }


	   /**
	    * f0 -> "TEMP"
	    * f1 -> IntegerLiteral()
	    */
	   public R visit(Temp n) {
		   String temp = n.f1.f0.toString();
		   //System.out.println("use: " + temp + " " + get_reg(temp));
	       return (R)get_reg(temp);
	   }

	   /**
	    * f0 -> <INTEGER_LITERAL>
	    */
	   public R visit(IntegerLiteral n) {
		   return (R) n.f0.toString();
	   }

	   /**
	    * f0 -> <IDENTIFIER>
	    */
	   public R visit(Label n) {
	      System.out.println(n.f0.toString());
	      return null;
	   }
	   
	   /**
	    * f0 -> "LT"
	    *       | "PLUS"
	    *       | "MINUS"
	    *       | "TIMES"
	    */
	   public R visit(Operator n) {
		   n.f0.accept(this);
		   int which = n.f0.which;
		   if (which == 0)
			   System.out.print(" LT ");
		   else if (which == 1)
			   System.out.print(" PLUS ");
		   else if (which == 2)
			   System.out.print(" MINUS ");
		   else
			   System.out.print(" TIMES ");
		   return null;
	   }
}
