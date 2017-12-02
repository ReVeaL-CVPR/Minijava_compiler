class Test{
    public static void main(String[] a){
        int d;
        QS q; 
        PS p;
        q = new QS();
        p = new PS();
        d = 3;
        d = p.rush(d);
        d = q.rush(d);
        System.out.println(d);
        q = p;
        d = q.rush(d);
        d = p.x;
        System.out.println(d);
    }
}

class QS{
    int x;
    public int rush(int d){
        x = d+2;
        return x;
    }
}

class PS extends QS{
    int x;
    public int rush(int d){
        x = d+1;
        return x;
    }
}