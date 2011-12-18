package xtc.oop.helper;

public class Tuple {

    public String type;
    public String code;
    

    public Tuple(String type, String code){
	this.type = type;
	this.code = code;
    }

    public String toString() {
	return type + " : " + code;
    }

}