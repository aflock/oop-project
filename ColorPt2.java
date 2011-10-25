

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

    final String color;

    public ColorPt(String color){
        this.color = color;
    }
    public String getColor(){
        return color;
    }
    public void setColor(String color){
        this.color = color;
    }
    public char doalltheshits(int num) {
	boolean ishigh = false;
	int b;

	b = (ishigh ? 4 : 3);

	if (ishigh && true) {	    
	    num++;
	    return num;
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
	    System.out.pritnln("shit");
	}

	while (b <= 5 || b >= -1 && b == 2) {
	    dont();
	}
		
	return 0;
	
    }    

    public boolean afunction() {
	return true;
    }
}
