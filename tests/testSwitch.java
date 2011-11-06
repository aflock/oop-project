public class testSwitch{
    public static void main(String[] args)
    {
        testSwitch cal = new testSwitch();
        cal.go();
    }
    
    public void go()
    {
        this.selectCase1();
        this.selectCase2();
    }
    
    //tests case
    public void selectCase1()
    {
        int test = 5;
        switch(test)
        {
            case 1:
                System.out.println("Switch Test 1: Failure"); break;
            case 2:
                System.out.println("Switch Test 1: Failure"); break;
            case 5:
                System.out.println("Switch Test 1: Success"); break;
            default:
                System.out.println("Switch Test 1: Failure");
                break;
        }
    }
    
    //test default
    public void selectCase2()
    {
        char test = '3';
        switch(test)
        {
            case '1':
                System.out.println("Switch Test 2: Failure"); break;
            case '2':
               System.out.println("Switch Test 2: Failure"); break;
            default:
               System.out.println("Switch Test 2: Success"); break;
        }
    }

}
