public class testOverloadMethods{
	public static void main (String [] args)
	{
		m(5);
		m("SDA");
		m(new Object());
		m('n');
		m();

	}

	public static void m(int a){
		System.out.println("m int");
	}
	public static void m(String a){
		System.out.println("string");
	}
	public static void m(Object a){
		System.out.println("ob");
	}
	public static void m(char a){
		System.out.println("char");
	}
	public static void m(){
		System.out.println("nothin");
	}

}
