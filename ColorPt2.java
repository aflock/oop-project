import xtc.oop.helper.Bubble;

class Pt{

    public Pt(){
    }
}

class ColorPt extends Pt{
    /*
    public static void main(String[] args){
        ColorPt c = new ColorPt("HAHAHAHA");
        System.out.println(c.getColor());
    }
    */

    String color;

    public ColorPt(String color){
        this.color = color;
    }
    public String getColor(){
        return color;
    }
    public void setColor(String color){
        this.color = color;
    }

    public int fuckyou() {
	return 1;
    }
    public char doalltheshits(int num) {
	boolean ishigh = false;
	int b;

	b = (ishigh ? 4 : 3);

	if (ishigh && true) {	    
	    num++;
	    return 0;
	}
	else {
	    ishigh = true;
	}

	if (num == 123)
	    ;

	if (num == 234) {

	}

	if (num == 4) 
	    ;
	else if (num == 5) 
	    ;
	else if (num == 6) {
	    
	}
	/*
	else
	    System.out.println("It is not high");
	*/

	for (int i = 0; i < num; i++) {
	    System.out.println("Hello World");
	}

	for ( ; b < num; --num) {
	    fuckyou();
	}

	while (ishigh) 
	    System.out.println("Infinity while loop");

	while (afunction()) {
	    System.out.println("shit");
	}

	while (b <= 5 || b >= -1 && b == 2) {
	    //dont();
	}

	switch(b=fuckyou()) {
	case 1:   
	case 2: num++;
	    num--; 
	    break;
	case 4: break;
	case 5: num--; break;
	default:
	    fuckyou();
	}
		
	return 0;
	
    }    

    public boolean afunction() {
	return true;
    }

    public static void main(String[] args) {

    }
}
