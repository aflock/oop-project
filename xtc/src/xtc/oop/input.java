/**
 This file is for testing OOP Homework 1. It demonstrates various versions of
 the scope-introducing language constructs in Java (e.g. it demonstrates 
 for-loops both with and without curly braces).

 Each construct section is labeled with a comment.
**/

//Class Declaration
public class input{

    //Constructor
    public input() {
	//Nothing to do
    }
    
    //Method
    public static void main(String [] args){
	
	//For loops, with and without curly braces
	for(int i = 0; i < 10; i++) {
	    for(int j = 0; j < 10; j++)
		System.out.println("For loops");
	}
	
	int x = 0;
	int y = 0;

	//While and do-while loops, with and without curly braces
	while(x < 10) {
	    y = 0;
	    do 
		System.out.println("While loops " + y++);
	    while(y < 10);
	    x++;
	}

	//Conditional statements, with and without curly braces
	if(x < y) {
	    System.out.println("If");
	}
	else
	    System.out.println("Else");
	
	//Block
	{
	    String s = "This is a block.";
	    System.out.println(s);
	}
	
	char c = 'a';

	//Switch statement
	switch(c) {
	case 'a':
	    System.out.println("Switch a");
	    break;
	case 'b':
	    System.out.println("Switch b");
	    break;
	case 'c':
	    System.out.println("Switch c");
	    break;
	default:
	    System.out.println("Default switch");
	}

	//Try/Catch/Finally statement
	try {
	    System.out.println("Try");
	}catch(Exception e) {
	    System.out.println("Catch");
	}finally {
	    System.out.println("Finally");
	}
    }
}