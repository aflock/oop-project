public class testMethodChaining
{
    public static void main(String[] args)
    {
        testMethodChaining cal = new testMethodChaining();
        System.out.println("===========Test Method Chain==============");
        System.out.println("Test 1: Chaining this");
        cal.test1().test2().test3(); //tests returning this
        System.out.println("Test 2: Chaining String/CharAt");
        System.out.println("\tCharAt(0): " + cal.getName().charAt(0));
        
        
    }
    
    public testMethodChaining test1()
    {
        System.out.println("\tTest1() Called");
        return this;
    }
    
    public testMethodChaining test2()
    {
        System.out.println("\tTest2() Called");
        return this;
    }
    
    public testMethodChaining test3()
    {
        System.out.println("\tTest3() Called");
        return this;
    }
    
    public String getName()
    {
        System.out.println("\tName: Calvin");
        return "Calvin";
    }
    
}
