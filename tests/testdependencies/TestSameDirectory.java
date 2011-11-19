public class TestSameDirectory {
    public static void main(String[] args) {
	TestDirectory s = new TestDirectory();
	s.m();
	System.out.println("method main in class TestSameDirectory");
    }
}

class B {
    public void m() {
	System.out.println("method m in class B in file TestSameDirectory.java");
    }
}