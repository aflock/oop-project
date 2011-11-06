

public class testMultiMain
{
    public static void main(String[] args)
    {
        System.out.println("Multiple Main Method Test: Success");
        main2 cal = new main2();
    }
    
}

class main2
{
    public static void main(String[] args)
    {
         System.out.println("Multiple Main Method Test: Failed on main2");
    }
}
