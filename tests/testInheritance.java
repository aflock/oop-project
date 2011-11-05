public class testInheritance{
    String name;
    int age;
    public testInheritance(String name){
        this.name = name;
        this.age = 42;
    }
    public String getStats(){
        return ("Name: " + this.name + "\nAge: " + this.age);
    }
    public int getAge(){
        return this.age;
    }
    public static void main (String [] args)
    {
       Inherited i = new Inherited("DK SMASH");
       System.out.println(i.getStats());
       System.out.println(i.getAge());
    }

}
class Inherited extends testInheritance{

    int killCount;
    public Inherited(String name){
        super(name);
        this.killCount = 9001;
    }
    public String getStats(){
        return ("Name: " + this.name + "\nAge: " + this.age + "\nkills: " + this.killCount);
    }

}
