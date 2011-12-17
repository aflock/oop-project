/*Resolve Data Fields:
-After Structure Parser Completes
1. Pass assignment node to eval call to see what the dynamic type will be
2. Need to find the correct scope in the symbol table and add it

Then, inherit datafields
1. 
*/

public class testDataFieldInherit{

    String name = "original name";
    
    public static void main(String[] args)
    {
        test1 t1 = new test1();
        t1.test();
        t1.test2();
        
        test2 t2 = new test2();
        t2.test();
        t2.test2();
    }

}

class test1 extends testDataFieldInherit{

    public void test(){
        System.out.println("test1.test(): Should print original name:");
        System.out.println("\t" + name);
    }
    
    public void test2(){
        System.out.println("test1.test2(): Should print original name:");
        System.out.println("\t" + super.name);
    }
}

class test2 extends testDataFieldInherit{
    int name = 69;
    
    public void test(){
        System.out.println("test2.test(): Should print 69:");
        System.out.println("\t" + name);
    }
    
    public void test2(){
        System.out.println("test2.test2(): Should print original name:");
        System.out.println("\t" + super.name);
    }


}
