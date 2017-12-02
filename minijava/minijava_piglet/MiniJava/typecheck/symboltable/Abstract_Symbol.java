package MiniJava.typecheck.symboltable;

import java.util.HashMap;
import java.util.Set;

import Error_handle.error;

public class Abstract_Symbol {
	 
	protected HashMap<String, Method> methods;
	protected HashMap<String, Variable> variables;
	protected HashMap<String, _Class> classes;

	
	protected String name;
	
	protected int define_line;
	protected _Type type;
	
	public Abstract_Symbol(){
		type = new _Type(-1);
	}
	
	
	public Set<String> get_methods(){
		return methods.keySet();
	}
	
	public Set<String> get_variables(){
		return variables.keySet();
	}
	
	public Set<String> get_classes(){
		return classes.keySet();
	}
	
	
	public Method get_method(String key){
		if (methods == null)
			return null;
		return methods.get(key);
	}
	public Variable get_variable(String key){
		if (variables == null)
			return null;
		return variables.get(key);
	}
	public _Class get_class(String key){
		if (classes == null)
			return null;
		return classes.get(key);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public _Type getType() {
		return type;
	}

	public void setType(int t) {
		this.type.t = t;
	}
	
	public void setType(String c) {
		this.type.c = c;
	}
	
	public void setType(_Type type) {
		this.type = type;
	}

	public void add_map(int cmd, String key, Object value){
		switch (cmd) {
			case _Type.Var:
				if (variables == null)
					System.out.println("no variables in this node");
				if (variables.containsKey(key))
					error.print_stacktrace("variable redefinition", ((Abstract_Symbol)value).getDefine_line());
				else
					variables.put(key, (Variable)value);
				break;
			case _Type.Func: 
				if (methods == null)
					System.out.println("no methods in this node");
				if (methods.containsKey(key)){
					error.print_stacktrace("function redefinition", ((Abstract_Symbol)value).getDefine_line());
				}
				else
					methods.put(key, (Method)value);
				break;
			case _Type.Class:
				if (classes == null)
					System.out.println("no classes in this node");
				if (classes.containsKey(key))
					error.print_stacktrace("class redefinition", ((Abstract_Symbol)value).getDefine_line());
				else
					classes.put(key, (_Class)value);
				break;
			default:
				System.out.println("no such cmd:" + cmd);
				break;
		}
	}
	public void print(int space){
		for (int i = 0; i < space; ++i){
			System.out.print(" ");
		}
		System.out.print(this.name + " " + this.type.t + " ");
	}
	
	public int getDefine_line() {
		return define_line;
	}

	public void setDefine_line(int define_line) {
		this.define_line = define_line;
	}
}
