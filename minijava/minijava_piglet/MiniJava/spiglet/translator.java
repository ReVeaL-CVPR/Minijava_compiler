package MiniJava.spiglet;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import MiniJava.utils.*;
import minijava_syntaxtree.Node;
import MiniJava.typecheck.*;
import MiniJava.typecheck.symboltable.*;
import MiniJava.typecheck.visitor.*;

class para{
	String belong;
	String name;
	int type;
	public para(String n, int t, String b){
		belong = b;
		name = n;
		type = t;
	}
	public para(para p) {
		belong = p.belong;
		name = p.name;
		type = p.type;
	}
	public String get_name(){
		if (type == translator.func)
			return belong + "_" + name;
		return name;
	}
}


class Dispatched_table extends LinkedHashMap<String, ArrayList<para>>{
	String cname;
	public void print(){
		for (String string : this.keySet()){
			System.out.println(" " + string);
			ArrayList<para> fields = this.get(string);
			for (para p : fields){
				System.out.println("    " + p.type + " " + p.get_name());
			}
		}
	}
	public int size(){
		int tot = 0;
		for (String string : this.keySet()){
			tot += this.get(string).size();
		}
		return tot;
	}
	private void set_tables(_Class c){
		cname = c.getName();
		ArrayList<para> table = new ArrayList<para>();
		for (String field : c.get_variables()){
			table.add(new para(field, translator.var, cname));
		}
		for (String field : c.get_methods()){
			boolean flag = false;
			for (String string : this.keySet()){
				ArrayList<para> pp = this.get(string);
				for (para p : pp){
					if (p.name.equals(field) && p.type == translator.func){
						p.belong = cname;
						flag = true;
						break;
					}
				}
				if(flag)
					break;
			}
			if (!flag)
				table.add(new para(field, translator.func, cname));
		}
		this.put(c.getName(), table);
	}
	
	public Dispatched_table(_Class c) {
		set_tables(c);
	}
	public Dispatched_table(Dispatched_table par, _Class c) {
		for (String string : par.keySet()){
			ArrayList<para> list = par.get(string);
			ArrayList<para> pp = new ArrayList<>();
			for (para p : list)
				pp.add(new para(p));
			this.put(string, pp);
		}
		set_tables(c);
	}
	public int get_loc(String cname, String name, int type){
		ArrayList<para> list = this.get(cname);
		if (list == null)
			return -1;
		int size = list.size();
		for (int i = 0; i < size; ++i) {
			para p = list.get(i);
			if (p.name.equals(name) && p.type == type)
				return i;
		}
		return -1;
	}
	
	public int get_loc(String name, int type){
		int res = 0;
		if (type == translator.var){
			int cnt = -1;
			for (String string : this.keySet()){
				ArrayList<para> list = this.get(string);
				int size = list.size();
				for (int i = 0; i < size; ++i) {
					para p = list.get(i);
					if (p.name.equals(name) && p.type == type)
						cnt = res + i;
				}
				res += size;
			}
			return cnt;
		}
		else{
			for (String string : this.keySet()){
				ArrayList<para> list = this.get(string);
				int size = list.size();
				for (int i = 0; i < size; ++i) {
					para p = list.get(i);
					if (p.name.equals(name) && p.type == type)
						return res + i;
				}
				res += size;
			}
		}
		return -1;
	}
}


public class translator{
	public static OutputStreamWriter writer;
	static final int var = 0;
	static final int func = 1;
	static Root symbol_table_root;
	static HashMap<String, Dispatched_table> classes = new HashMap<>();
	private static ArrayList<String> step;
	public static Dispatched_table build(_Class c){
		if (step.contains(c.getName()))
			return null;
		else
			step.add(c.getName());
		if (c == null)
			return null;
		String father = c.father;
		Dispatched_table aClass = null;
		if (father != null){
			if (classes.containsKey(father))
				aClass = new Dispatched_table(classes.get(father), c);
			else{
				_Class fClass = symbol_table_root.get_class(father);
				Dispatched_table father_tab = build(fClass);
				if (father_tab == null)
					aClass = new Dispatched_table(c);
				else
					aClass = new Dispatched_table(father_tab, c);
			}
		}
		else
			aClass = new Dispatched_table(c);
		classes.put(c.getName(), aClass);
		return aClass;
	}
	
	public static void generate_piglet(String file, String outfile) throws Exception{
		if (type_check.parser == null)
			type_check.parser = new MiniJavaParser(new FileInputStream(file));
		else
			type_check.parser.ReInit(new FileInputStream(file));
		symbol_table_root = new Root();
		Node tree_root;
		tree_root = MiniJavaParser.Goal();
		tree_root.accept(new construct_symboltable(), symbol_table_root);
		System.out.println(file);
//		symbol_table_root.print(1);
		step = new ArrayList<>();
		for (_Class c : symbol_table_root.get_class_list())
			build(c);
		for (String string : classes.keySet()){
			System.out.println(string + " " + classes.get(string).size());
			classes.get(string).print();
		}
		System.out.println("--------------------");
		writer = new OutputStreamWriter(new FileOutputStream(outfile));
		tree_root.accept(new translate_visitor());
		writer.close();
	}
	
	public static void generate_piglet(InputStream stream) throws Exception{
		if (type_check.parser == null)
			type_check.parser = new MiniJavaParser(stream);
		else
			type_check.parser.ReInit(stream);
		symbol_table_root = new Root();
		Node tree_root;
		tree_root = MiniJavaParser.Goal();
		tree_root.accept(new construct_symboltable(), symbol_table_root);
		step = new ArrayList<>();
		for (_Class c : symbol_table_root.get_class_list())
			build(c);
		writer = new OutputStreamWriter(System.out);
		tree_root.accept(new translate_visitor());
		writer.close();
	}
	
	
	public static void main(String[] args){
		try {
			generate_piglet(new FileInputStream("./benchmark/LinkedList.java"));
//			generate_piglet(System.in);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
