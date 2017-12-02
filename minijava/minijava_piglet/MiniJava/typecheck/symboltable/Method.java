package MiniJava.typecheck.symboltable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class Method extends Abstract_Symbol{
	public LinkedHashMap<String, Variable> parameter_map;
	public Method() {
		variables = new HashMap<>();
		parameter_map = new LinkedHashMap<>();
	}
	public Variable get_variable(String key){
		Variable variable = parameter_map.get(key);
		if (variable == null)
			return variables.get(key);
		return variable;
	}
	public _Type[] get_type_list(){
		Collection<Variable> list = parameter_map.values();
		_Type[] type_list = new _Type[list.size()];
		int cnt = 0;
		for (Variable variable : list){
			type_list[cnt++] = variable.type;
		}
		return type_list;
	}
	public void print(int space){
		super.print(space);
		System.out.println("method");
		for (String string : parameter_map.keySet()){
			parameter_map.get(string).print(space+2);
		}
		for (String string : variables.keySet()){
			variables.get(string).print(space+2);
		}
	}
}
