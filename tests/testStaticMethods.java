public class testStaticMethods{
    public static int getNum(){
        return (4 + (4*12));
    }

    public static void main (String [] args)
    {
        Andrew a = new Andrew("lolz");
        System.out.println(a.getCount());
        System.out.println(a.getAge());
        System.out.println(getNum());
    }
}

class Andrew{
    String name;
    int age;
    static int count = 4;
    public Andrew(String n){
        this.name = n;
        this.age = count + 1;
    }

    public static int getCount(){
        return count;
    }

    public int getAge(){
        return this.age;
    }
}
