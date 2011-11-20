public class testSelfAssignment{
    public static void main (String [] args)
    {
        String[] theArray = new String[10];
        theArray[0] = theArray[0] = "Take LSD errday\n";
        theArray[4] = theArray[0];
        theArray[4] = theArray[4];

        for (int i=0; i<theArray.length; i++){
            System.out.println(theArray[i]);
        }
    }
}
