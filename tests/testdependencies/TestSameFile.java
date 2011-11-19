public class TestSameFile {
    public static void main(String[] args) {
	System.out.println("method main in class TestSameFile");
	new A().m();	
    }
}

class A {
    public void m() {
	System.out.println("method m in class A");
    }
}