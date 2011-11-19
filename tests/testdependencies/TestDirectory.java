public class TestDirectory {
    public void m() {
	System.out.println("method m in class TestDirectory");
	n();
    }

    public void n() {
	new B().m();
	System.out.println("method n in class TestDirectory");
    }
}