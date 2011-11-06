public class testDoWhile{
    String name;
    public testMethodOverwriting(){
        this.name = "AFLOCK OVERWRITE" ;
    }
    public String toString(){
        String toReturn = "overwritten toString: " + this.name;
        return toReturn;
    }
    public static void main (String [] args)
    {
        testMethodOverwriting ao = new testMethodOverwriting();
        //should be able to call toString from within the print statement
        System.out.println(ao);
    }
}
