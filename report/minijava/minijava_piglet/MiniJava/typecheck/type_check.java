package MiniJava.typecheck;

import java.awt.Window.Type;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import Error_handle.error;
import MiniJava.*;
import MiniJava.utils.*;
import minijava_syntaxtree.Node;
import MiniJava.typecheck.symboltable.*;
import MiniJava.typecheck.visitor.*;

public class type_check {
	public static void check_circle(ArrayList<_Class> class_list){
		HashMap<String, ArrayList<Method>> func_map = new HashMap<>();
		int size = class_list.size();
		boolean[] steps = new boolean[size];
		int qt = 0;
		_Class class1 = class_list.get(0);
		while(qt < size){
			if (steps[qt] == true){
				qt++;
				continue;
			}
			class1 = class_list.get(qt);
			if (class1.father == null)
			{
				qt++;
				continue;
			}
			_Class next = symbol_table_root.get_class(class1.father);
			if (class1.father != null && next == null){
				error.print_stacktrace("extension class not defined", class1.getDefine_line());
				steps[qt++] = true;
				continue;
			}
			ArrayList<Method> extend_func_list = null;
			if (func_map.containsKey(next.getName())){
				extend_func_list = func_map.get(next.getName());
			}
			else{
				ArrayList<_Class> stack = new ArrayList<>();
				stack.add(class1);
				while(next != null)
				{
					if (next.getName().equals(class1.getName())){
						error.print_stacktrace("inheritance cycle", class1.getDefine_line());
						break;
					}
					if (steps[class_list.indexOf(next)]){
						extend_func_list = func_map.get(next.getName());
						break;
					}
					stack.add(next);
					steps[class_list.indexOf(next)] = true;
					next = symbol_table_root.get_class(next.father);
				}
				if (extend_func_list == null)
					extend_func_list = stack.remove(stack.size()-1).get_method_list();
				int len = stack.size();
				for(int i = len-1; i >= 0; --i){
					_Class class2 = stack.get(i);
					ArrayList<Method> current_list = class2.get_method_list();
					boolean flag = false;
					ArrayList<Method> candidate = new ArrayList<>();
					for (Method method : current_list){
						for (Method mm : extend_func_list){
							if (method.getName().equals(mm.getName())){
								if (!method.getType().compareTo(mm.getType())) {
									flag = true;
									break;
								}
								_Type[] tl = method.get_type_list();
								_Type[] tr = mm.get_type_list();
								for (int j = 0; j < tl.length; ++j){
									if (!tl[j].compareTo(tr[j])){
										flag = true;
										break;
									}
								}
							}
						}
						if(flag == true){
							error.print_stacktrace("minijava doesn't support overload", method.getDefine_line());
							break;
						}
						else
							candidate.add(method);
					}
					extend_func_list.addAll(candidate);
				}
				func_map.put(class1.getName(), extend_func_list);
			}
			steps[qt++] = true;
		}
	}
	public static MiniJavaParser parser = null;
	public static Root symbol_table_root;
	public static boolean check(InputStream inputStream){
		try {
			StringReader reader = error.pre_process(inputStream);
			if (parser == null)
				parser = new MiniJavaParser(reader);
			else
				parser.ReInit(reader);
			Node tree_root = MiniJavaParser.Goal();
			symbol_table_root = new Root();
			tree_root.accept(new construct_symboltable(), symbol_table_root);
			check_circle(symbol_table_root.get_class_list());
			tree_root.accept(new re_check_visitor(), symbol_table_root);
			if (error.error){
				System.out.println("Type error");
				return false;
			}
			else
				System.out.println("Program type checked successfully");
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static void check(String file){
		try {
			StringReader reader = error.pre_process(file);
//			if (error.flag)
//				return;
//			System.out.println(file);
			if (parser == null)
				parser = new MiniJavaParser(reader);
			else
				parser.ReInit(reader);
			Node tree_root = MiniJavaParser.Goal();
//	        System.out.println("Program parsed done");
			symbol_table_root = new Root();
			tree_root.accept(new construct_symboltable(), symbol_table_root);
//			System.out.println("symbol table built done");
			check_circle(symbol_table_root.get_class_list());
//			symbol_table_root.print(0);
			tree_root.accept(new re_check_visitor(), symbol_table_root);
//			System.out.println("recheck done");
			if (error.error)
				System.out.println("Type error");
			else
				System.out.println("Program type checked successfully");
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
