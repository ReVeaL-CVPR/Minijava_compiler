package MiniJava.typecheck.symboltable;

import java.util.Comparator;

public class _Type{
	
	public static final int Var = 0;
	public static final int Func = 1;
	public static final int Class = 2;

	public static final int _array = 0;
	public static final int _boolean = 1;
	public static final int _int = 2;
	public static final int _class = 3;
	
    public int t;
    public String c;
    
    public _Type(int t){
    	this.t = t;
    	this.c = null;
    }
    
    public _Type(int t, String c){
	   this.t = t;
	   this.c = c;
    }
    
    public boolean compareTo(_Type type){
    	if (type == null)
    		return false;
    	if (t == type.t && c == type.c)
    		return true;
    	return false;
    }
    
}