package kanga_translator;

import java.util.*;
import kanga_syntaxtree.*;
import kanga_visitor.GJNoArguDepthFirst;

public class Translate_visitor<R> extends GJNoArguDepthFirst<R>{
	int argn,args,stkn;
	String[] reg_table = {"a0", "a1", "a2", "a3", "t0", "t1", "t2", 
			  "t3", "t4", "t5", "t6", "t7", "s0", "s1", "s2", "s3",
			  "s4", "s5", "s6", "s7", "t8", "t9", "v0", "v1"};
	  /**
	    * f0 -> "MAIN"
	    * f1 -> "["
	    * f2 -> IntegerLiteral()
	    * f3 -> "]"
	    * f4 -> "["
	    * f5 -> IntegerLiteral()
	    * f6 -> "]"
	    * f7 -> "["
	    * f8 -> IntegerLiteral()
	    * f9 -> "]"
	    * f10 -> StmtList()
	    * f11 -> "END"
	    * f12 -> ( Procedure() )*
	    * f13 -> <EOF>
	    */
	   public R visit(Goal n) {
		   argn = Integer.parseInt(n.f2.f0.toString());
		   stkn = Integer.parseInt(n.f5.f0.toString());
		   args = Integer.parseInt(n.f8.f0.toString());
		   if (args > 4)
			   args -= 4;
		   else
			   args = 0;
		   System.out.println("	.text");
		   System.out.println("	.globl main");
		   System.out.println("main:");
		   System.out.println("move $fp, $sp");
		   System.out.println("subu $sp, $sp, " + 4 * (1+stkn+args));
		   System.out.println("sw $ra, -4($fp)");
	       n.f10.accept(this);
	       System.out.println("lw $ra, -4($fp)");
	       System.out.println("addu $sp, $sp, " + 4 * (1+stkn+args));
	       System.out.println("j $ra");
	       n.f12.accept(this);
	       String tail = ".text\n.globl _halloc\n_halloc:\nli $v0, 9\nsyscall\nj $ra\n";
	       tail += ".text\n.globl _print\n_print:\nli $v0, 1\nsyscall\nla $a0, newl\n"
	       		+ "li $v0, 4\nsyscall\nj $ra\n";
	       tail += ".data\n.align   0\nnewl:    .asciiz \"\\n\"\n.data\n.align   0\n"+
	       		"str_er:  .asciiz \" ERROR: abnormal termination\\n\"";
	       System.out.println(tail);
	       return null;
	   }

	   /**
	    * f0 -> Label()
	    * f1 -> "["
	    * f2 -> IntegerLiteral()
	    * f3 -> "]"
	    * f4 -> "["
	    * f5 -> IntegerLiteral()
	    * f6 -> "]"
	    * f7 -> "["
	    * f8 -> IntegerLiteral()
	    * f9 -> "]"
	    * f10 -> StmtList()
	    * f11 -> "END"
	    */
	   public R visit(Procedure n) {
		   argn = Integer.parseInt(n.f2.f0.toString());
		   stkn = Integer.parseInt(n.f5.f0.toString());
		   if (argn > 4)
			   stkn -= (argn - 4);
		   args = Integer.parseInt(n.f8.f0.toString());
		   if (args > 4)
			   args -= 4;
		   else
			   args = 0;
		   String lab = (String)n.f0.f0.toString();
		   System.out.println("	.text");
		   System.out.println("	.globl " + lab);
		   System.out.println(lab + ":");
		   System.out.println("sw $fp, -8($sp)");
		   System.out.println("move $fp, $sp");
		   System.out.println("subu $sp, $sp, " + 4 * (2+stkn+args));
		   System.out.println("sw $ra, -4($fp)");
	       n.f10.accept(this);
	       System.out.println("lw $ra, -4($fp)");
	       System.out.println("lw $fp, " + 4 * (stkn+args) + "($sp) ");
	       System.out.println("addu $sp, $sp, " + 4 * (2+stkn+args));
	       System.out.println("j $ra");
	       return null;
	   }

	   /**
	    * f0 -> "NOOP"
	    */
	   public R visit(NoOpStmt n) {
		  System.out.println("nop");
	      return null;
	   }


	   
	   /**
	    * f0 -> "ERROR"
	    */
	   public R visit(ErrorStmt n) {
	      R _ret=null;
	      System.out.println("la $a0, str_er");
	      System.out.println("syscall");
	      n.f0.accept(this);
	      return _ret;
	   }

	   /**
	    * f0 -> "CJUMP"
	    * f1 -> Reg()
	    * f2 -> Label()
	    */
	   public R visit(CJumpStmt n) {
		   System.out.println("beqz " + n.f1.accept(this)+ " " + n.f2.f0.toString());
	       return null;
	   }

	   /**
	    * f0 -> "JUMP"
	    * f1 -> Label()
	    */
	   public R visit(JumpStmt n) {
		   System.out.println("b " + n.f1.f0.toString());
	       return null;
	   }

	   /**
	    * f0 -> "HSTORE"
	    * f1 -> Reg()
	    * f2 -> IntegerLiteral()
	    * f3 -> Reg()
	    */
	   public R visit(HStoreStmt n) {
	      System.out.println(String.format("sw %s, %s(%s)", 
	    		  n.f3.accept(this), n.f2.f0.toString(), 
	    		  n.f1.accept(this)));
	      return null;
	   }

	   /**
	    * f0 -> "HLOAD"
	    * f1 -> Reg()
	    * f2 -> Reg()
	    * f3 -> IntegerLiteral()
	    */
	   public R visit(HLoadStmt n) {
	      System.out.println(String.format("lw %s %s(%s)", 
	    		  n.f1.accept(this), n.f3.f0.toString(), 
	    		  n.f2.accept(this)));
	      return null;
	   }

	   /**
	    * f0 -> "MOVE"
	    * f1 -> Reg()
	    * f2 -> Exp()
	    */
	   public R visit(MoveStmt n) {
	      Node exp = n.f2.f0.choice;
	      String reg = (String)n.f1.accept(this);
	      if (exp instanceof HAllocate){
	    	  if ( ((HAllocate)exp).f1.f0.choice instanceof IntegerLiteral )
	    		  System.out.println("li $a0 " + ((HAllocate)exp).f1.f0.accept(this));
	    	  else
	    		  System.out.println("move $a0 " + ((HAllocate)exp).f1.f0.accept(this));
	    	  System.out.println("jal _halloc");
	    	  System.out.println("move " + reg + " $v0");
	      }
	      else if (exp instanceof BinOp)
	    	  System.out.println(String.format("%s %s, %s, %s", ((BinOp)exp).accept(this),
	    			  reg, ((BinOp)exp).f1.accept(this), ((BinOp)exp).f2.accept(this)));
	      else if (exp instanceof SimpleExp){
	    	  int which = ((SimpleExp)exp).f0.which;
	    	  if (which == 0)
	    		  System.out.println("move " + reg + " " + exp.accept(this));
	    	  else if (which == 1)
	    		  System.out.println("li " + reg + " " + exp.accept(this));
	    	  else
	    		  System.out.println("la " + reg + " " + ((Label)((SimpleExp)exp).f0.choice).f0.toString());
	      }
	      return null;
	   }

	   /**
	    * f0 -> "PRINT"
	    * f1 -> SimpleExp()
	    */
	   public R visit(PrintStmt n) {
	      System.out.println("move $a0 " + n.f1.accept(this));
	      System.out.println("jal _print");
	      return null;
	   }

	   /**
	    * f0 -> Reg()
	    *       | IntegerLiteral()
	    *       | Label()
	    */
	   public R visit(SimpleExp n) {
		   Node node = n.f0.choice;
		   if(node instanceof IntegerLiteral)
			   return (R)((IntegerLiteral)node).f0.toString();
		   else return node.accept(this);
	   }
	   
	   /**
	    * f0 -> "ALOAD"
	    * f1 -> Reg()
	    * f2 -> SpilledArg()
	    */
	   public R visit(ALoadStmt n) {
		   int res = Integer.parseInt(n.f2.f1.f0.toString());
		   if (res >= argn - 4){
			   System.out.println("lw " + n.f1.accept(this) + ", " + 
				4 * (res + args) + "($sp)");
		   }
		   else{
			   System.out.println("lw " + n.f1.accept(this) + ", " 
				+ 4 * res + "($fp)");
		   }
	       return null;
	   }

	   /**
	    * f0 -> "ASTORE"
	    * f1 -> SpilledArg()
	    * f2 -> Reg()
	    */
	   public R visit(AStoreStmt n) {
		   int res = Integer.parseInt(n.f1.f1.f0.toString());
		   if (argn > 4) {
			   if (res >= argn - 4){
				   System.out.println("sw " + n.f2.accept(this) + ", " + 
					4 * (res + args - argn + 4) + "($sp)");
			   }
			   else{
				   System.out.println("sw " + n.f2.accept(this) + ", " + 
							4 * (res) + "($fp)");
			   }
		   }
		   else{
			   System.out.println("sw " + n.f2.accept(this) + ", " + 
						4 * (res + args) + "($sp)");
		   }
	       return null;
	   }

	   /**
	    * f0 -> "PASSARG"
	    * f1 -> IntegerLiteral()
	    * f2 -> Reg()
	    */
	   public R visit(PassArgStmt n) {
		   System.out.println("sw " + n.f2.accept(this) + ", "
		   + 4 * (Integer.parseInt(n.f1.f0.toString())-1) + "($sp)");
	       return null;
	   }

	   /**
	    * f0 -> "CALL"
	    * f1 -> SimpleExp()
	    */
	   public R visit(CallStmt n) {
	      System.out.println("jalr " + n.f1.accept(this));
	      return null;
	   }

	   /**
	    * f0 -> Operator()
	    * f1 -> Reg()
	    * f2 -> SimpleExp()
	    */
	   public R visit(BinOp n) {
		   int opt = n.f0.f0.which;
		   switch (opt) 
		   {
				case 0:
					return (R)"slt";
				case 1:
					if (n.f2.f0.choice instanceof Reg)
						return (R)"add";
					else
						return (R)"addu";
				case 2:
					if (n.f2.f0.choice instanceof Reg)
						return (R)"sub";
					else
						return (R)"subu";
				case 3:
					return (R)"mul";
		   }
		   return null;
	   }

	   /**
	    * f0 -> "a0"
	    *       | "a1"
	    *       | "a2"
	    *       | "a3"
	    *       | "t0"
	    *       | "t1"
	    *       | "t2"
	    *       | "t3"
	    *       | "t4"
	    *       | "t5"
	    *       | "t6"
	    *       | "t7"
	    *       | "s0"
	    *       | "s1"
	    *       | "s2"
	    *       | "s3"
	    *       | "s4"
	    *       | "s5"
	    *       | "s6"
	    *       | "s7"
	    *       | "t8"
	    *       | "t9"
	    *       | "v0"
	    *       | "v1"
	    */
	   public R visit(Reg n) {
	      return (R)("$" + reg_table[n.f0.which]);
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
		   System.out.println(n.f0.toString() + ":");
		   return null;
	   }
}
