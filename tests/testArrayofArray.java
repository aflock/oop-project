public class testArrayofArray
{
    public static void main(String[] args)
    {
        System.out.println("=========TESTING INT[][]=========");
        int[][] test = new int[3][4];
        for(int r=0; r < 3; r++)
        {
            for(int c=0; c<4; c++)
            {
                test[r][c] = r*c;
                System.out.println(r + " * " + c + " = " + test[r][c]);
            }
        }
        System.out.println("=================================");
    }
}
