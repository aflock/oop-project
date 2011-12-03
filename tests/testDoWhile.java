//SHOULD OUTPUT 1-10, each on a new line

public class testDoWhile{

    public testDoWhile(){
    }
 
    public static void main (String [] args)
    {
        testDoWhile cal = new testDoWhile();
        cal.doloop();
    }
    
    public void doloop()
    {
        int i = 1;
        do
        {
            System.out.println("i = " + i);
            i++;
        } while(i <= 10);
    }
}
