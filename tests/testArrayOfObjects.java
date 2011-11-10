public class testArrayOfObjects{

    public static void main (String [] args)
    {
        Object[] arrOfObject = new Object[10];

        arrOfObject[0] = new Object();

        System.out.println(arrOfObject[0]);

        //what if the spot hasn't been initialized?
        System.out.println(arrOfObject[3]);
    }

}
