package spiglet_utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import spiglet_syntaxtree.*;
import spiglet_visitor.*;
import translator.Block_visitor;
import translator.Flow_chart;
import translator.Translator;



public class test {
	public static OutputStreamWriter writer;
	static final int var = 0;
	static final int func = 1;


	public static void generate_piglet(InputStream in) throws Exception{
		new SpigletParser(in);
		Node tree_root;
		tree_root = SpigletParser.Goal();
		tree_root.accept(new Block_visitor());
	}
	
	
	public static void main(String[] args){
		try {
			Translator _translator = new Translator();
			generate_piglet(new FileInputStream(new File("test")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
