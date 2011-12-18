public class testConstructorInherit{
	String name;
	public testConstructorInherit(){
		this.name = "yayaya";
	}

	public static void main (String [] args)
	{
		System.out.println("super constructor");
		Inherit test = new Inherit();
	}

}
class Inherit extends testConstructorInherit{

	int name;

	public Inherit(){
		super();
		System.out.println("inherit constructor");
		this.name = 42;
	}

}
