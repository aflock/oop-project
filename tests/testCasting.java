public class testCasting{

    public static void m(int    a )  { System.out.println("int");    }
    public static void m(char   a )  { System.out.println("char");   }
    public static void m(long   a )  { System.out.println("long");   }
    public static void m(String a )  { System.out.println("String"); }
    public static void m(Object a )  { System.out.println("Object"); }
    public static void m(Inh    a )  { System.out.println("Inh");    }

    public static void main (String [] args)
    {
        int a = 8;
        char b = 'v';
        String c = "hi";
        Object d = new Object();
        Inh e = new Inh();

        m(a);
        m(b);
        m(d);
        m(c);
        m((int)b);
        m((char)a);
        m((long)a);
        m((Object)c);
        m((Object)e);
    }
}

class Inh{
    public Inh(){}
}
