public class testStaticMethods{
    public static int getNum(){
        return (4 + 4) * 12;
    }
    public static int getAge(){
        return 5;
    }
    public static int getCount(){
        return 40000;
    }
    public int getNumber(){
        return 6;
    }

    public static void main (String [] args)
    {
        System.out.println(Andrew.getCount());
        Andrew a = new Andrew("lolz");
        System.out.println(a.getCount());
        System.out.println(a.getAge());
        System.out.println(a.getLink());
        System.out.println(getNum());
        System.out.println(getAge());
        System.out.println(getCount());

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

    public int getLink(){
        return getAge() + getCount();
    }
}
