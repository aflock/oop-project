public class testMultiParamType{

    final int dataField1 = 2;
    
    public static void main(String[] args)
    {
        testMultiParamType cal = new testMultiParamType();
        cal.go();
    }
    
    public void go()
    {
        this.testCase1(4,69);
    }
    
    
    public void testCase1(final int b, final int c)
    {
        System.out.println("b = " + b);
        System.out.println("c = " + c);
    }

}
