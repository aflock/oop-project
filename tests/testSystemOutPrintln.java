public class testSystemOutPrintln
{
    public static void main(String[] args)
    {
        String var = "variable";
        System.out.println("test" + " whoa plus " + var);
        System.out.println(replaceSys("System.out.println(\"test\" + \" whoa plus \" + \"variable\");"));
    }
    
    public static String replaceSystemPrintln(String s)
    {
        String ret = "";

        if (s.contains("System.out.println"))
        {
            //get everything to be printed
            String toPrint = getStringBetween(s, "println(", ");");
            toPrint = toPrint.replace("+", "<<");
            
            ret += "std::cout << " + toPrint + " << std::endl;";
        }
        
        return ret;
            
    }
    
    public static String getStringBetween(String src, String start, String end)
    {
        int lnStart;
        int lnEnd;
        String ret = "";
        lnStart = src.indexOf(start);
        lnEnd = src.indexOf(end);
        if(lnStart != -1 && lnEnd != -1)
            ret = src.substring(lnStart + start.length(), lnEnd);
	
	    return ret;
    }
}
