public class testShortplusShort{
    public static void main (String [] args){
        int a = 2;
        int b = 2;

        short c = 4;
        short d = 4;

        System.out.println(checker(a + b));
        System.out.println(checker(c + d));
        System.out.println(checker(a));
        System.out.println(checker(d));

    }
    public static String checker(short in){
        return "short param";
    }
    public static String checker(int in){
        return "int param";
    }

}
