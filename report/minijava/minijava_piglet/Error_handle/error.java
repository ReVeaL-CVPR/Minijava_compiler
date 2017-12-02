package Error_handle;

import java.awt.im.InputContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.FormatFlagsConversionMismatchException;

public class error {
	public static boolean flag = false;
	public static boolean error = false;
	public static final boolean is_print = false;
	public static String [] code_list;
	
	public static StringReader pre_process(InputStream inputStream) throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		flag = false;
		error = false;
	   	String code = "";
	   	String buf;
	   	while ((buf = reader.readLine()) != null){
	   		code += (buf + '\n');
	   		if (buf.contains("TE")){
	   			flag = true;
	   		}
	   	}
	   	code_list = code.split("\n");
		return new StringReader(code);
	}
	
	public static StringReader pre_process(String file) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
		flag = false;
		error = false;
	   	String code = "";
	   	String buf;
	   	while ((buf = reader.readLine()) != null){
	   		code += (buf + '\n');
	   		if (buf.contains("TE")){
	   			flag = true;
	   		}
	   	}
	   	code_list = code.split("\n");
		return new StringReader(code);
	}
	public static void print_stacktrace(String msg, int line){
		error = true;
		if (is_print){
			System.err.println("\nerror: " + msg);
			System.err.println(line + ":" + code_list[line-1]);
			System.err.flush();
		}
	}
	
}
