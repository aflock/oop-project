public class testBasicClass{
    String name;
    public testBasicClass(){
        this.name = "Ninja Assassin";
    }

    public static void main (String [] args)
    {
        testBasicClass na = new testBasicClass();
        System.out.println("my name is: " + na.name);
    }

}
