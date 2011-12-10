public class testBasicClass{
    String name;
    public testBasicClass(){
        this.name = "Ninja Assassin";
    }

    public testBasicClass(Object f){
        this.name = Obj.hashCode()
    }

    public static void main (String [] args)
    {
        testBasicClass na = new testBasicClass();
        System.out.println("my name is: " + na.name);
    }

}
