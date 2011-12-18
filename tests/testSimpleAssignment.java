public class testSimpleAssignment{


    public String name;
    
    public testSimpleAssignment()
    {
        this.name = "test";
        meth()
    }
    
    public static void main(String[] args){
        testSimpleAssignment calvin = new testSimpleAssignment();
        
        System.out.println(calvin.name);
    }
    
    public void meth(){}


}

class child{

   int i = 0;
   public child(){
       super();
       name = "test";
       meth(this)
   }
   
   public meth(int a){
   
   }
   
   



}
