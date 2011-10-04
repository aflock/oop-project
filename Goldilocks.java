import java.util.Scanner;
import java.util.*;

public class Goldilocks{
    public static void main (String [] args)
    {
        System.out.println("hello");
        Newbie Andrew = new Newbie();
        Newbie John = new Newbie();
        System.out.println(Andrew.info());
        for (int i = 0; i<2; i++){
            if(i==0){
                String theString = Andrew.info() + i;
                System.out.println(i + theString);
            }
            else{
                String theString = John.info() + i;
                System.out.println(i + theString);
            }
        }

        int j = 1;
        while(j==1){
            j++;
            int theInt = 5;
            System.out.println(theInt);
        }
       try{
           Scanner sc = new Scanner(System.in);
           System.out.println(sc.toString());
       } catch(Exception e){
           System.out.println(e);
       }



       if(true)
           System.out.println("yay true!");
       if(true){
            System.out.println("in a block");
       }
       else
           System.out.println("yay false!");

       {
           int tryThis = 15;
           System.out.println(tryThis);
       }

       do
           j++;
               while(j<4);
       System.out.println(j);
    }
}

class Newbie{
    private static int count = 0;
    public int age;

    public Newbie(){
        count++;
        age = count + 1;
    }

    public String info(){
        return ("This Newb's age is" + this.age );
    }
}














