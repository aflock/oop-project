import java.util.ArrayList;
public class testPassingLists{
    public static ArrayList<String> stringList = new ArrayList<String>();

    public static void main (String [] args)
    {
        stringList.add("main1");
        stringList.add("main2");
        Foo foo = new Foo(stringList);
        foo.mess();
        for(String s : stringList){
            System.out.println(s);
        }
    }
}

class Foo{
    ArrayList<String> passedList;

    public Foo(ArrayList<String> pl){
        this.passedList = pl;
    }

    public void mess(){
        passedList.add("mess1");
        passedList.add("mess2");
    }
}
