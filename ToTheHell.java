public class ToTheHell {
    public static void main(String[] args) {
	String s = null;
	char b = 'a';
	double fl = .0;
	
	Object o = new String("A"); 
	if (o instanceof String) {
	    
	}
	int c = (char)b;
	s = (String)o;
    }

    boolean hell;   
    int num;
    public ToTheHell(boolean hell) {
	this.hell = hell;
	
    }

    public ToTheHell(boolean hell, int num) {
	this(hell);
	this.num = num;
    }
    /*
    public ToTheHell() {
	this();
    }
    */
    public void setHell(boolean hell) {
	this.hell = hell;
    }
}