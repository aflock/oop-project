public class testNameMashing
{
    public static void main(String[] args)
    {
        System.out.println("=========TESTING NAME MASHING=========");
        //Testing Single and Double UnderScore
        int _test = 1;         System.out.println("_test = " + _test);
        int __test = 1;        System.out.println("__test = " + __test);
        
        //Messing with some keywords
        int _String = 1;        System.out.println("_String = " + _String);
        int __String = 1;       System.out.println("__String = " + __String);
        int _Class = 1;         System.out.println("_Class = " + _Class);
        int __Class = 1;        System.out.println("__Class = " + __Class);
        int _Array = 1;         System.out.println("_Array = " + _Array);
        int __Array = 1;        System.out.println("__Array = " + __Array);
        int _this = 1;          System.out.println("_this = " + _this);
        int __this = 1;         System.out.println("__this = " + __this);
        
        //Weird Symbols
        int $test = 1;         System.out.println("$test= " + $test);
        
        System.out.println("=====================================");
    }
}
