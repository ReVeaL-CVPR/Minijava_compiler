package MiniJava.typecheck;

import MiniJava.typecheck.type_check;

public class Test 
{
	public static void main(String[] args)
    {
        A a;
		B b;
        C c;
        int res;
        a = new A();
        b = new B();
        c = new C();
        res = a.init(4);
        res = a.run();
        res = b.init(5);
        res = b.run();
        res = c.say(b);
        // c.say(b);
	}
}

class A {
    int val;
    public int init(int v){
        val = v+1;
        return 0;
    }
    public int gun() {
        System.out.println(val);
        System.out.println(1);
        return 0;
    }
	public int run() {
        System.out.println(val);
		System.out.println(1);
		return 0;
	}
}
class B extends A
{
    int val;
    public int init(int v){
        val = v+2;
        return 0;
    }
    public int run() 
    {
        System.out.println(val);
        System.out.println(2);
        return 0;
    }
}

class C{
    public int say(A a){
        int res;
        res = a.init(5);
        res = a.run();
        res = a.gun();
        return 0;
    }
}
