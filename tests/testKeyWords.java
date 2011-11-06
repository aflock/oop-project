//tests if we handle c++ keywords correctly in translation
public class testKeyWords{

    public static void main(String[] args)
    {
        testKeyWords cal = new testKeyWords();
        cal.testBool();
        cal.testInt32_t();
        cal.testShort();
    }

    public void testBool()
    {
        boolean bool = true;
        if(bool)
            System.out.println("Boolean Named Bool: Success");
        else
            System.out.println("Boolean Named Bool: Failed");
    }

    public void testInt32_t()
    {
        int int32_t = 0;
        if(int32_t == 0)
            System.out.println("Int Named Int32_t: Success");
        else
            System.out.println("Int Named Int32_t: Failed");
    }
    
    public void testShort()
    {
        short int16_t = 0;
        if(int16_t == 0)
            System.out.println("Short Named Int16_t: Success");
        else
            System.out.println("Short Named Int16_t: Failed");
    }
}
