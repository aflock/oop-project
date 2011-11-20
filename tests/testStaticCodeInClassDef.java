public class testStaticCodeInClassDef{
    public static void main (String [] args)
    {
        IDoShit n = new IDoShit("hahahah");
        System.out.println(n);

    }

}

class IDoShit{
    String name;
    String favoriteBackStreetBoy = "Nick.AJ.Howie.Brian.Kevin".split("\\.")[0].toLowerCase();
    static int count = "thisissomerandoshit".length();

    public IDoShit(String name){
        this.name = name;
        System.out.println("constructing :" + this.favoriteBackStreetBoy);
    }
    public String toString(){
        return "My fave BSB is " + this.favoriteBackStreetBoy + ";;;" + count;
    }
}
