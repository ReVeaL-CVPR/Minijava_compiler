package kanga_translator;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Vector;

import kanga_utils.KangaParser;
import kanga_translator.Translate_visitor;

public class Translator {
	public static void main(String args[]){
		try {
//			InputStream inputstream = System.in;
			FileInputStream inputstream;
			inputstream = new FileInputStream(new File("benchmark/test"));
			new KangaParser(inputstream);
			KangaParser.Goal().accept(new Translate_visitor<String>());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
