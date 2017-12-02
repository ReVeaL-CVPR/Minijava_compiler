package translator;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.Scrollable;

public class Flow_chart {
	HashMap<String, Integer>spill_list = new HashMap<>();
	public String get_col(String reg){
		int i = colors.get(reg);
		if (i >= 0)
			return table[colors.get(reg)];
		return null;
	}
	static String[] savet = {"a0","a1","a2","a3"};
	static String[] table = {"t0","t1","t2","t3","t4","t5","t6","t7","t8","t9",
			"s0","s1","s2","s3","s4","s5","s6","s7"};
	int cnt;
	int store;
	Basic_block exit_block;
	String name;
	Vector<String> params;
	int inp_num;
	HashMap<String, Basic_block> block_ref;
	HashMap<String, Vector<String>> interfere;
	HashMap<String, Integer> colors;
	Vector< Vector<String> > buff;
	HashMap<Integer, Operation> opt_table;
	int spill_number;
	int max_args;
	
	Vector<String> spills;
	String ret;
	
	public boolean in_buf(Vector<String> v){
		for (Vector<String> buf : buff){
			if (buf.containsAll(v))
				return true;
		}
		return false;
	}
	public int get_spill_number(int store){
		if (spill_number != -1)
			return spill_number;
		spill_number = spills.size();
		ArrayList<Integer> saved = new ArrayList<>();
		for (Integer i: colors.values()){
			if (!saved.contains(i)){
				++spill_number;
				saved.add(i);
			}
		}
		if (store > 4)
			spill_number += (store-4);
		return spill_number;
	}
	
	public Flow_chart(String name) {
		store = 0;
		spill_number = -1;
		max_args = 0;
		this.name = name;
		opt_table = new HashMap<>();
		block_ref = new HashMap<>();
		params = new Vector<>();
		interfere = new HashMap<>();
		buff = new Vector<>();
		spills = new Vector<>();
		colors = new HashMap<>();
	}
	public void add_map(String a, String b){
	   if (interfere.containsKey(a)){
		   if (!interfere.get(a).contains(b))
			   interfere.get(a).add(b);
	   }
	   else{
		   Vector<String> vector = new Vector<>();
		   vector.add(b);
		   interfere.put(a, vector);
	   }
	}

	public void delete_node(String node){
		interfere.remove(node);
		for (String string : interfere.keySet()){
			Vector<String> vector = interfere.get(string);
			if (vector.contains(node))
				vector.remove(node);
		}
	}

}
