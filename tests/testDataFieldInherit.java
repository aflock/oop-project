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
        testI1 t1 = new testI1();
        t1.test();
        t1.test2();

        testI2 t2 = new testI2();
        t2.test();
        t2.test2();
		testI3 t3 = new testI3();
		t3.test();

		testI4 t4 = new testI4();
		t4.test();
    }

}

class testI1 extends testDataFieldInherit{
	//vtable:
	//string name;
    public void test(){
        System.out.println("test1.test(): Should print original name:");
        System.out.println("\t" + this.name);
    }

    public void test2(){
        System.out.println("test1.test2(): Should print original name:");
        System.out.println("\t" + super.name);
    }
}

class testI2 extends testDataFieldInherit{
	//vtable:
	//String $name;
	//int name
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

class testI3 extends testI2{
	//vtable
	//String $$name
	//int $name
	//char name
	char name = 'c';

	public void test(){
		System.out.println("Should print 69: " + super.name);
		System.out.println("Shoudld print c: " + name);
	}

}

class testI4 extends testI3{

	public void test(){
		System.out.println("Should print c: " + super.name);
		System.out.println("Shoudld print c: " + name);

	}
}
