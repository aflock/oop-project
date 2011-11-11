import java.io.File;

//ALSO SEE xtc.util.Tool's locate() method

public class Finder {

    public static void main(String [] args) {
	//Run from cmd line arg
	System.out.println(findFile(args[0]));
    }

    //Query is in the form: [pkg.whatever.]Class
    public static String findFile(String query) {
	String sep = System.getProperty("file.separator");
	String cp = System.getProperty("java.class.path");

	query = query.replace(".",sep).concat(".java");
	
	return findFile(cp, query);
    }

    //can return File if necessary
    public static String findFile(String cp, String query) {
	
	File f = new File(cp);
	File [] files = f.listFiles();
	for(int i = 0; i < files.length; i++) {
	    //System.out.println(files[i]);
	    if(files[i].isDirectory()) {
		String a = findFile(files[i].getAbsolutePath(), query);
		if(!a.equals("No"))
		    return a;
	    }
	    else if(files[i].getAbsolutePath().endsWith(query))
		return files[i].getAbsolutePath();
	}
	return "No";
    }

}