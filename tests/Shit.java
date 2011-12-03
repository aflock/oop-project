public class Shit {
    public Shit() {

    }

    public static void main(String[] args) {
	Shit c = new Shit();

	int a, b;
	System.out.println(c.n().m(a=b=1));

    }

    Shit n() {
	return this;
    }

    int m(int a) {
	return a;
    }
}

