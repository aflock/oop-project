public class testInheritandOverload{
    String name;
    public testInheritandOverload(){
        this.name = "older";
    }
    public void m(){
         System.out.println("m");
    }
    public static void main (String [] args)
    {
        Inheriter i = new Inheriter();
        i.m(3);
        i.m();

    }
}
class Inheriter extends testInheritandOverload{
    public void m(int a){
         System.out.println(a);
    }
}
