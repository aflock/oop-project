public class testSimpleAssignment{


    public String name;
    
    public testSimpleAssignment()
    {
        this.name = "test";
    }
    
    public static void main(String[] args){
        testSimpleAssignment calvin = new testSimpleAssignment();
        
        System.out.println(calvin.name);
    }


}
