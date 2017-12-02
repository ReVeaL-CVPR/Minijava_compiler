package translator;

import spiglet_syntaxtree.*;
import spiglet_visitor.DepthFirstVisitor;
import translator.Translator;

public class Block_visitor extends DepthFirstVisitor{

	Flow_chart chart;
	Basic_block temp;
	Basic_block current;
	Operation opt;
	int loc = 0;
	
	  /**
	    * f0 -> "MAIN"
	    * f1 -> StmtList()
	    * f2 -> "END"
	    * f3 -> ( Procedure() )*
	    * f4 -> <EOF>
	    */
	   public void visit(Goal n) {
		   chart = new Flow_chart("MAIN");
		   current = new Basic_block("_" + chart.cnt++);
		   chart.block_ref.put(current.ref, current);
	       n.f1.accept(this);
	       add_block("951228_return");
	       chart.exit_block = current;
	       Translator.charts.add(chart);
	       current = null;
	       n.f3.accept(this);
	   }
	   boolean from_exp = false;
	   
	   /**
	    * f0 -> Temp()
	    *       | IntegerLiteral()
	    *       | Label()
	    */
	   public void visit(SimpleExp n) {
		   from_exp = true;
	       n.f0.accept(this);
	       from_exp = false;
	   }
	   
	   /**
	    * f0 -> Label
	    */
	   public void visit(Label n) {
		   if (!from_exp)
			   add_block(n.f0.toString());
	   }

	   
	   /**
	    * f0 -> "CJUMP"
	    * f1 -> Temp()
	    * f2 -> Label()
	    */
	   public void visit(CJumpStmt n) {
		   loc = n.f0.beginLine;
		   String lab = n.f2.f0.toString();
		   n.f1.accept(this);
		   current.suc.add(lab);
		   if (chart.block_ref.containsKey(lab))
			   chart.block_ref.get(lab).par.add(current.ref);
		   else{
			   Basic_block nb = new Basic_block(lab);
			   nb.par.add(current.ref);
			   chart.block_ref.put(lab, nb);
		   }
		   add_block("_" + chart.cnt++);
	   }

	   /**
	    * f0 -> "JUMP"
	    * f1 -> Label()
	    */
	   public void visit(JumpStmt n) {
		   String lab = n.f1.f0.toString();
		   current.suc.add(lab);
		   if (chart.block_ref.containsKey(lab))
			   chart.block_ref.get(lab).par.add(current.ref);
		   else{
			   Basic_block nb = new Basic_block(lab);
			   nb.par.add(current.ref);
			   chart.block_ref.put(lab, nb);
		   }
		   add_block("_" + chart.cnt++);
	   }
	   
	   /**
	    * f0 -> Label()
	    * f1 -> "["
	    * f2 -> IntegerLiteral()
	    * f3 -> "]"
	    * f4 -> StmtExp()
	    */
	   public void visit(Procedure n) {
		   chart = new Flow_chart(n.f0.f0.toString());
		   current = new Basic_block("_" + chart.cnt++);
		   for (int i = 0; i < Integer.parseInt(n.f2.f0.toString()); ++i){
			   String param = "" + i;
			   current.params.add(param);
			   chart.params.add(param);
//			   current.temp_def.add(param);
		   }
		   chart.block_ref.put(current.ref, current);
	       n.f4.accept(this);
	   }
	   
	   /**
	    * f0 -> "BEGIN"
	    * f1 -> StmtList()
	    * f2 -> "RETURN"
	    * f3 -> SimpleExp()
	    * f4 -> "END"
	    */
	   public void visit(StmtExp n) {
	       n.f1.accept(this);
	       add_block("951228_return");
	       loc = n.f2.beginLine;
	       n.f3.accept(this);
	       chart.exit_block = current;
	       Translator.charts.add(chart);
	       current = null;
	   }
	   public void add_block(String ref){
		   if (current == null)
			   return;
		   temp = current;		   
		   if (chart.block_ref.containsKey(ref))
			   current = chart.block_ref.get(ref);
		   else{
			   current = new Basic_block(ref);
			   chart.block_ref.put(ref, current);
		   }
		   temp.suc.add(current.ref);
		   current.par.add(temp.ref);
	   }
	   boolean move_tmp = false;
	   /**
	    * f0 -> "MOVE"
	    * f1 -> Temp()
	    * f2 -> Exp()
	    */
	   public void visit(MoveStmt n) {
		  loc = n.f0.beginLine;
	      String param = n.f1.f1.f0.toString();
	      move_tmp = true;
	      opt = new Operation(n.f0.beginLine);
	      opt.is_def = true;
	      opt.def_var = param;
	      current.opt_table.put(opt.line, opt);
	      chart.opt_table.put(opt.line, opt);
	      loc = n.f0.beginLine;
	      n.f2.accept(this);
	      if (!chart.params.contains(param))
	    	  chart.params.add(param);
	      if (!current.params.contains(param))
	    	  current.params.add(param);
	      if (!current.temp_use.contains(param)) 
	    	  current.temp_def.add(param);
	   }
	   
	   /**
	    * f0 -> "HLOAD"
	    * f1 -> Temp()
	    * f2 -> Temp()
	    * f3 -> IntegerLiteral()
	    */
	   public void visit(HLoadStmt n) {
		  String param = n.f1.f1.f0.toString();
	      if (!chart.params.contains(param))
	    	  chart.params.add(param);
	      if (!current.params.contains(param))
	    	  current.params.add(param);
	      if (!current.temp_use.contains(param)) 
	    	  current.temp_def.add(param);
	      opt = new Operation(n.f0.beginLine);
	      opt.is_def = true;
	      opt.def_var = param;
	      current.opt_table.put(opt.line, opt);
	      chart.opt_table.put(opt.line, opt);
	      loc = n.f0.beginLine;
	      n.f2.accept(this);
	   }
	   
	   /**
	    * f0 -> "TEMP"
	    * f1 -> IntegerLiteral()
	    */
	   public void visit(Temp n) {
		   String param = n.f1.f0.toString();
		   if (!current.temp_def.contains(param)) 
			   current.temp_use.add(param);
		   if (current.opt_table.containsKey(loc))
			   opt = current.opt_table.get(loc);
		   else{
			   opt = new Operation(loc);
			   current.opt_table.put(opt.line, opt);
			   chart.opt_table.put(opt.line, opt);
		   }
		   opt.is_use = true;
		   opt.use_var.add(param);
	   }
	   
	   /**
	    * f0 -> "CALL"
	    * f1 -> SimpleExp()
	    * f2 -> "("
	    * f3 -> ( Temp() )*
	    * f4 -> ")"
	    */
	   public void visit(Call n) {
		   int narg = n.f3.size();
		   if (narg > chart.max_args)
			   chart.max_args = narg;
		   loc = n.f0.beginLine;
	       n.f1.accept(this);
	       loc = n.f0.beginLine;
	       n.f3.accept(this);
	   }
	   
	   /**
	    * f0 -> "PRINT"
	    * f1 -> SimpleExp()
	    */
	   public void visit(PrintStmt n) {
	      loc = n.f0.beginLine;
	      n.f1.accept(this);
	   }
	   
	   /**
	    * f0 -> "HALLOCATE"
	    * f1 -> SimpleExp()
	    */
	   public void visit(HAllocate n) {
		  loc = n.f0.beginLine;
	      n.f1.accept(this);
	   }
	   
	   /**
	    * f0 -> "HSTORE"
	    * f1 -> Temp()
	    * f2 -> IntegerLiteral()
	    * f3 -> Temp()
	    */
	   public void visit(HStoreStmt n) {
		   loc = n.f0.beginLine;
		   n.f1.accept(this);
		   n.f3.accept(this);
	   }
	   
	   /**
	    * f0 -> "LT"
	    *       | "PLUS"
	    *       | "MINUS"
	    *       | "TIMES"
	    */
	   public void visit(Operator n) {
		   loc = ((NodeToken)n.f0.choice).beginLine;
	   }
}
