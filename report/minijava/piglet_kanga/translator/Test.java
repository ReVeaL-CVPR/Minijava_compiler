package translator;

import java.util.LinkedHashMap;

public class Test {
	public static void main(String args[]){
		LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
		map.put(1, "123");
		map.put(5, "456");
		map.put(3, "789");
		map.put(3, "789");
		for (Integer integer : map.keySet()){
			System.out.println(integer);
		}
	}
}
