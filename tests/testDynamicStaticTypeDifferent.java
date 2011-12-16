public class testDynamicStaticTypeDifferent{
    public static void m(String a){
        System.out.println("String");
    }
    public static void m(Object c){
        System.out.println("Obj");
    }
    public static void m(B b){
         System.out.println("BBB");
    }
    public static void m(A b){
         System.out.println("AAB");
    }
    public static void main (String [] args)
    {
        Object a = "Ahhhh";
        m(a);
        A example  = new B();
        B example2 = new B();
        A example3 = example2;
        example.method();
        example2.method();
        example3.method();
        example.dynMeth();
        example2.dynMeth();
        example3.dynMeth();
        m(example);
        m(example2);
        m(example3);
        example.dynMeth(example);
        example.dynMeth(example2);
        example.dynMeth(example3);
    }
}

class A{
    public void dynMeth(B a){
        System.out.println(" argument bb");
    }
    public void dynMeth(A a){
        System.out.println(" argument aa");
    }
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
/* How do we solve this problem?
 * Any time a new object is created with NewClassExpression, wanf
 */
