public class testOverloadOperator
{
    public static void main(String[] args)
    {
        String testString = "string + ";


        byte testByte = 1;
        short testShort = 2;
        int testInt = 3;
        long testLong = -4;
        float testFloat = -5;
        double testDouble = 6.0;
        boolean testBoolean = true;
        char testChar = 'q';
        
        System.out.println(testString + testString);
        System.out.println(testString + testByte);
        System.out.println(testString + testShort);
        System.out.println(testString + testInt);
        System.out.println(testString + testLong);
        System.out.println(testString + testFloat);
        System.out.println(testString + testDouble);
        System.out.println(testString + testBoolean);
        System.out.println(testString + testChar);
        //System.out.println(testChar+ testChar);
        //System.out.println(testByte + testChar);
        //System.out.println(testByte + testBoolean);
    }
}
