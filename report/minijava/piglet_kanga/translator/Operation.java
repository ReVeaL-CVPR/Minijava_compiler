package translator;

import java.util.Vector;

public class Operation {
	int line;
	boolean is_def;
	boolean is_use;
	String  def_var;
	Vector<String> use_var;
	Vector<String> alive;
	public Operation(int line) {
		this.line = line;
		use_var = new Vector<>();
		alive = new Vector<>();
	}
	public void display(){
		String out = line + " ";
		if (is_def)
			out += " def " + def_var;
		if (is_use) {
			out += " use ";
			for (String var : use_var)
				out += var + " ";
		}
		out += " !!!live!!! ";
		for (String var : alive)
			out += var + " ";
		System.out.println(out);
	}
}
