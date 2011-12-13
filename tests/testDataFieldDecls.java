public class testDataFieldDecls{

    public static int a;
    private final int b = 2;
    private int c = 3;
    int d = four();
    int[] e;
    int[][] f = new int[9][2];
    String g = "7";
    char h = '8';
    boolean i = false;
    long j = ((8-9)*2);
    double k = 8.8;
    //test for rest of primitive types

    public testDataFieldDecls()
    {
        a=1;
        e = new int[1];
        e[0] = 5;
        f[0][1] = 6;
    }
    
    public static void main(String[] args)
    {
        testDataFieldDecls cal = new testDataFieldDecls();
        cal.go();
    }
    
    public void go()
    {
        System.out.println("a    :    " + a);
        System.out.println("b    :    " + b);
        System.out.println("c    :    " + c);
        System.out.println("d    :    " + d);
        System.out.println("e    :    " + e[0]);
        System.out.println("f    :    " + f[0][1]);
        System.out.println("g    :    " + g);
        System.out.println("h    :    " + h); 
        System.out.println("i    :    " + i); 
        System.out.println("j    :    " + j); 
        System.out.println("k    :    " + k);     
    }
    
    public int four()
    {
        return 4;
    }
}
