package MiniJava.typecheck.symboltable;

public class Variable extends Abstract_Symbol{
	boolean init;
	public boolean Is_init() {
		return init;
	}
	public void set_init(boolean is_init) {
		this.init = is_init;
	}
	public Variable() {
		type = new _Type(0);
		init = false;
	}
	public void print(int space){
		super.print(space);
		if (type.c != null)
			System.out.print(type.c);
		System.out.print('\n');
	}
}


