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

            //make sure we can change them
            test[0][0] = 9001;
            test[0][1] = 9001;
            test[0][2] = 9001;
            test[0][3] = 9001;

            //need to throw index out of bounds error but also retrieve data correctly
            //if you don't want the error, just remove that = sign ;)
            for (int i = 0; i <=test[0].length(); i ++){
                System.out.println(test[0][i]);
            }

        }
        System.out.println("=================================");
    }
}
