package MiniJava.typecheck.symboltable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Root extends _Class{
	public Root() {
		classes = new HashMap<>();
	}
	public ArrayList<_Class> get_class_list(){
		ArrayList<_Class> list = new ArrayList<>();
		Collection<_Class> cLasses = classes.values();
		for (_Class class1 : cLasses){
			list.add(class1);
		}
		return list;
	}
	public void print(int space){
		System.out.println("root");
		for (String kString : classes.keySet()){
			if (classes.get(kString) == this)
				continue;
			classes.get(kString).print(space + 2);
		}
	}
}
