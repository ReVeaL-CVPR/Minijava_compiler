package translator;

import java.util.BitSet;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Vector;

public class Basic_block {
	String ref;
	Vector<String> params;
	Vector<String> suc;
	Vector<String> par;
	Vector<String> temp_def;
	Vector<String> temp_use;
	Vector<String> splits;
	HashMap<String, Vector<String>> interfere;
	TreeMap<Integer, Operation> opt_table;
	BitSet def;
	BitSet use;
	BitSet inp;
	BitSet out;
	public Basic_block(String ref) {
		this.ref = ref;
		suc = new Vector<>();
		par = new Vector<>();
		params = new Vector<>();
		temp_def = new Vector<>();
		temp_use = new Vector<>();
		interfere = new HashMap<>();
		opt_table = new TreeMap<>();
		splits = new Vector<>();
	}
	public void display_table(){
		for(Operation operation : opt_table.values())
			operation.display();
	}

}
