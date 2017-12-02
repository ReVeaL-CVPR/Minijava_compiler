package translator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import javax.print.DocFlavor.STRING;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import spiglet_syntaxtree.Node;
import spiglet_utils.SpigletParser;

public class Translator {
	public static Flow_chart find_chart(String name){
		for (Flow_chart chart : charts)
			if (chart.name.equals(name))
				return chart;
		return null;
	}
	public static Vector<Flow_chart> charts;
	public static int max_register = Flow_chart.table.length;
	
	public static void display_symbol(String cmd, Flow_chart chart, BitSet set){
		System.out.print(cmd + " : ");
		for (int i = 0; i < chart.params.size(); ++i){
			if (set.get(i))
				System.out.print(chart.params.get(i) + " ");
		}
		System.out.println("");
	}
	
	public static void tranlate(InputStream inputstream){
		try {
			//System.out.println("hello");
			charts = new Vector<>();
			new SpigletParser(inputstream);
			Node tree_root;
			tree_root = SpigletParser.Goal();
			tree_root.accept(new Block_visitor());
			for (Flow_chart chart : charts)
			{
				int size = chart.params.size();
				//System.out.println(chart.name + " " + size + " " + chart.params.toString());
				BitSet tmp;
				for (Basic_block block : chart.block_ref.values()){
					block.def = new BitSet(size);
					block.use = new BitSet(size);
					block.inp = new BitSet(size);
					block.out = new BitSet(size);
					for (String string : block.temp_use)
						block.use.set(chart.params.indexOf(string));
					for (String string : block.temp_def)
						block.def.set(chart.params.indexOf(string));
				}
				ArrayList<Basic_block> changed = new ArrayList<>(chart.block_ref.values());
				changed.remove(chart.exit_block);
				chart.exit_block.inp.or(chart.exit_block.use);
				while(!changed.isEmpty()){
					boolean flag = false;
					Basic_block block = changed.remove(0);
					block.out.clear();
					for (String suc: block.suc)
						block.out.or(chart.block_ref.get(suc).inp);
					tmp = (BitSet) block.inp.clone();
					block.inp = (BitSet) block.out.clone();
					block.inp.andNot(block.def);
					block.inp.or(block.use);
					if (!tmp.equals(block.inp))
						flag = true;
					if (flag){
						for (String par : block.par)
							changed.add(chart.block_ref.get(par));
					}
				}
				
				for (Basic_block block : chart.block_ref.values())
				{
					
//					System.out.println("def" + " " + block.def);
					
					int cnt = 0;
					for (int i = 0; i < size; ++i){
						if (block.inp.get(i)){
							Operation operation = new Operation(cnt--);
							operation.is_def = true;
							operation.def_var = chart.params.get(i);
							block.opt_table.put(operation.line, operation);
						}
					}
					Operation operation = new Operation(100000);
					operation.is_use = true;
					for (int i = 0; i < size; ++i){
						if (block.out.get(i)){
							operation.use_var.add(chart.params.get(i));
						}
					}
					block.opt_table.put(operation.line, operation);
					ArrayList<Operation> opt_list = new ArrayList<>();
					opt_list.addAll(block.opt_table.values());
					int tot = opt_list.size();
					Vector<String> liVector = new Vector<>();
					for (int i = tot-1; i >= 0; --i){
						operation = opt_list.get(i);
						operation.alive = (Vector<String>) liVector.clone();
						if (operation.is_def)
							if (liVector.contains(operation.def_var))
								liVector.remove(operation.def_var);
						if (operation.is_use) 
							for (String string : operation.use_var)
								if (!liVector.contains(string)){
									liVector.add(string);
									operation.alive.add(string);
								}
						if (!chart.in_buf(operation.alive)){
							chart.buff.add(operation.alive);
						}
					}
					//System.out.println("hello");
//					System.out.println(block.ref + block.suc);
//					display_symbol("use", chart, block.use);
//					display_symbol("def",chart, block.def);
//					display_symbol("inp", chart, block.inp);
//					display_symbol("out", chart, block.out);
//					block.display_table();
				}
//				for(Operation operation : chart.opt_table.values())
//					operation.display();
				
				for (Vector<String> buf : chart.buff){
					for (String a : buf){
						chart.add_map(a, a);
						for (String b: buf){
							if (!a.equals(b)){
								chart.add_map(a, b);
								chart.add_map(b, a);
							}
						}
					}
				}
				
				//display_map(chart.interfere);
				HashMap<String, Vector<String>> store = map_clone(chart.interfere);
				ArrayList<String> stack = new ArrayList<>();
				while(!chart.interfere.isEmpty()){
					boolean flag = true;
					for (String string : chart.interfere.keySet()){
						int degree = chart.interfere.get(string).size();
						if (degree < max_register){
							chart.delete_node(string);
							//display_map(store);
							stack.add(string);
							flag = false;
							break;
						}
					}
					if (flag) {
						String spill = (String)chart.interfere.keySet().toArray()[0];
						chart.interfere = (HashMap<String, Vector<String>>) map_clone(store);
						chart.spills.add(spill);
						chart.delete_node(spill);
						store = (HashMap<String, Vector<String>>) map_clone(chart.interfere);
						stack.clear();
					}
				}
				//display_map(store);
				
				Vector<String> pops = new Vector<>();
				while(!stack.isEmpty()){
					String pop = stack.remove(0);
					Vector<String> relations = store.get(pop);
					Vector<Integer> color = new Vector<>();
					for(String alr : pops){
						if (relations.contains(alr))
							color.add(chart.colors.get(alr));
					}
					for (int i = 0; i < max_register; ++i){
						if (!color.contains(i)){
							chart.colors.put(pop, i);
							break;
						}
					}
					pops.add(pop);
				}
//				for (String string : chart.colors.keySet()){
//					System.out.println(string + " color:" + chart.colors.get(string));
//				}
//				for (String string : chart.spills){
//					System.out.println(string + " spill");
//				}
				
			}
			//System.out.println("-----------------");
			tree_root.accept(new Translate_visitor<String>());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String args[]){
//		tranlate(System.in);
		try {
			tranlate(new FileInputStream(new File("./benchmark/test")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void display_map(HashMap<String, Vector<String>> map){
		System.out.println("display_map!!!" + map.keySet());
		for (String key : map.keySet())
		{
			for (String val : map.get(key)){
				System.out.println(key + "---" + val);
			}
		}
		System.out.println("display_map~~~");
	}
	public static HashMap<String, Vector<String>> map_clone(HashMap<String, Vector<String>> map){
		HashMap<String, Vector<String>> cl = new HashMap<String, Vector<String>>();
		for (String string : map.keySet()){
			cl.put(string, (Vector<String>)map.get(string).clone());
		}
		return cl;
	}
}