public class testDynamicStaticTypeDifferent{
    public static void m(String a){
        System.out.println("String");
    }
    public static void m(Object c){
        System.out.println("Obj");
    }
    public static void main (String [] args)
    {
        Object a = "Ahhhh";
        m(a);
        A example = new B();
        example.method();
        example.dynMeth();

    }
}

class A{
    public void dynMeth(){
        System.out.println("Dynamix a");
    }
    public static void method(){
         System.out.println("A method");
    }
}
class B extends A{
    public void dynMeth(){
        System.out.println("Dynamix b");
    }
    public static void method(){
        System.out.println("B method");
    }
}
