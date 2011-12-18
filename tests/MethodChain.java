public class MethodChain {
    public static void main(String[] args) {
	MethodChain a;
	MethodChain b;
	a = new MethodChain();
	b = new MethodChain();

	//a.m().m(b);
	a.m(b);
    }
    /*
    public MethodChain() {

    }
    */
    public MethodChain m() {
	return this;
    }

    public MethodChain m(MethodChain a) {
	return a;
    }
}

