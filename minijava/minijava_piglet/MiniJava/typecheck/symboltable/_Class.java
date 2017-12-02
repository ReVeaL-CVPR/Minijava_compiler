package MiniJava.typecheck.symboltable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class _Class extends Abstract_Symbol{
	
	public String father;
	public _Class() {
		methods = new HashMap<>();
		variables = new HashMap<>();
		father = null;
	}
	public ArrayList<Method> get_method_list(){
		ArrayList<Method> list = new ArrayList<>();
		Collection<Method> _methods = methods.values();
		for (Method method : _methods){
			list.add(method);
		}
		return list;
	}
	public void print(int space){
		super.print(space);
		System.out.println("class");
		for (String string : methods.keySet()){
			methods.get(string).print(space+2);
		}
		for (String string : variables.keySet()){
			variables.get(string).print(space+2);
		}
	}
}