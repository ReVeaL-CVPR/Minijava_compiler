package kanga_translator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import kanga_utils.KangaParser;

public class MinijavaTranslator {
	public static void main(String args[]){
		try {
			InputStream inputstream = System.in;
			File file = new File("out.a");
			FileOutputStream outputStream = new FileOutputStream(file);
			byte[] bytes = new byte[1024*1024];
			int len = inputstream.read(bytes);
			inputstream.close();
			outputStream.write(bytes, 0, len);
			outputStream.close();
			Runtime cmd = Runtime.getRuntime();
			cmd.exec("cmd /c start java -jar minijava2spiglet.jar < out.a");
			cmd.exec("cmd /c start java -jar spiglet2kanga.jar < out.a > temp");
			cmd.exec("cmd /c start java -jar kanga2mips.jar < temp > out.s");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
