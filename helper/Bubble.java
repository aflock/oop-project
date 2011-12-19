package xtc.oop.helper;
import java.util.ArrayList;
import xtc.util.SymbolTable;
//import java.util.Arrays;
//import java.util.Collections;

public class Bubble{
    ArrayList<Bubble> bubbles; //ArrayList of the children of this Bubble
    ArrayList<Mubble> mubbles; //ArrayList of Mubbles in this class
    ArrayList<Field> dataFields; // Field is a new structure created to hold both variable name, type, and modifiers
    String name; //This class' name
    Bubble parentBubble; //This Bubble's parent Bubble
    Pubble parentPubble; //This class' package (reference)
    boolean isBuilt = false; //determine whether a bubble has been completely filled in (excluding code)
    boolean isFilled = false; //determine whether all the code portions of this bubble's mubbles has been filled in
    ArrayList<String> staticData; //

    SymbolTable varTable;
    SymbolTable funcTable;
    SymbolTable table;
    SymbolTable dynamicTypeTable;


    /* DO WE NEED THIS?:
       String visibility; //The visibility for this class
       *a

    ////////////////////
    /* CONSTRUCTOR(S) */
    ////////////////////

    public Bubble(String name) {
        //TODO: Make this constructor, what are the params?
        //What's the context it's called from?

        this.name            = name;
        this.bubbles         = new ArrayList<Bubble>();
        this.dataFields      = new ArrayList<Field>();
        this.mubbles         = new ArrayList<Mubble>();
        this.varTable        = new SymbolTable();
        this.funcTable       = new SymbolTable();
        this.table           = new SymbolTable();
        this.dynamicTypeTable = new SymbolTable();
        this.staticData = new ArrayList<String>();
    }

    public Bubble(){
        this.bubbles         = new ArrayList<Bubble>();
        this.dataFields      = new ArrayList<Field>();
        this.mubbles         = new ArrayList<Mubble>();
        this.varTable        = new SymbolTable();
        this.funcTable       = new SymbolTable();
        this.table           = new SymbolTable();
        this.dynamicTypeTable = new SymbolTable();
        this.staticData = new ArrayList<String>();
    }


    ////////////////
    /* Other Meth */
    ////////////////
    public boolean hasName(String name)
    {
        if (this.name.equals(name))
            return true;
        else
            return false;
    }

    /////////////
    /* SETTERS */
    /////////////


    //Add a Bubble to the Bubble list
    public void addBubble(Bubble b) {
        this.bubbles.add(b);
    }

    //Add a data field
    public void addField(Field f) {
        this.dataFields.add(f);
    }

    //Add a Mubble to this Bubble
    public void addMubble(Mubble m) {
        m.setBubble(this);
        this.mubbles.add(m);
    }

    //Set the name of this Bubble
    public void setName(String name) {
        this.name = name;
    }

    /*
     * C has a method m(A)
     *
     * C c;
     * B b;
     * c.m(b)
     *
     */

    public String getStaticData(){
        String ret = "";
        for(String s : this.staticData){
            ret += s;
        }
        
        return ret;
    }
    
    public void addStaticData(String s){
    
        this.staticData.add(s);
    }
    public Mubble findMethod(ArrayList<Bubble> bubbles, String methodName, ArrayList<String> para) {

        Mubble mub = null;
        int best = 1000000;
        //System.out.println("Calling find method : " + methodName + " :: ");

        //System.out.println("mubblesList size is :: " + mubbles.size());
        for (Mubble m : mubbles) {
            //System.out.println("Mubble name is || "+ m.getName());
            //System.out.println("group name is || "+ m.getGroup());
            //System.out.println("_____");
            if (m.belongToGroup(methodName)) {
                //System.out.println(methodName + "::"+  m.getName());
                //System.out.println(m.getFlag());
                int min = 0;
                ArrayList<String> p = m.getParameterTypes();

                //System.out.println("p.size(): " + p.size());
                //System.out.println("para.size(): " + para.size());
                if (para.size() == p.size()) {
                    for (int i = 0; i < p.size(); i++) {
                        //System.out.println("V_V_V_V_V_V_V_VV_V_V_V_V_V_VV_V_V_V_V_V_V_V");
                        //System.out.println(methodName);
                        //System.out.println("calling rank on " + para.get(i) + "||| and " + p.get(i));
                        min += rank(bubbles, para.get(i), p.get(i));
                        //System.out.println(i + " " + para.get(i) + ":" + p.get(i) + " " + min);
                    }
                    if (min < best) {
                        best = min;
                        mub = m;
                    }
                }
            }
        }
        return mub;
    }

    //finds the appropriate constructor for this mubble based off the parametres
    public Mubble findConstructor(ArrayList<String> para){
        Mubble mub = null;
        int best = 1000000;

        for (Mubble m : mubbles) {
            if (m.isConstructor()) {
                int min = 0;
                ArrayList<String> p = m.getParameterTypes();
                if (para.size() == p.size()) {
                    for (int i = 0; i < p.size(); i++) {
                        min += rank(bubbles, para.get(i), p.get(i));
                    }
                    if (min < best) {
                        best = min;
                        mub = m;
                    }
                }
            }
        }
        return mub;
    }

    /*
    public Mubble findMethod(ArrayList<Bubble> bubbles, String methodName, ArrayList<String> para) {
        Mubble mub = null;
        int matchNum = 100000000;
        System.out.println("findMethod being called SON");
        System.out.println("Method name is :: " + methodName);
        System.out.println("Bubble :: " + getName());
        for (Mubble m : mubbles) {
            //System.out.println("\tMethod:: " + m.getName());
            if (m.belongToGroup(methodName)) { //if there is a match in method names
                int min = 0;
                ArrayList<String> p = m.getParameterTypes(); //todo: fix, param types is size 0 for objects??
                ArrayList<Field> fields = m.getParameters();
                //System.out.println("fields.size(): " + fields.size());
                System.out.println("p.size(): " + p.size());
                System.out.println("para.size(): " + para.size());
                for(String s : para)
                    System.out.println("\t" + s);
                if (p.size() == para.size()) { //checking to see if there in an applicable method, strictly based on the amount of params
                    //System.out.println("Parameters size matches!");
                    boolean match = false;
                    for (int i = 0; i < p.size(); i++) {
                        //System.out.println("p.get(i): " + p.get(i));
                        //System.out.println("para.get(i): " + para.get(i));
                        if(p.get(i).equals(para.get(i))) {//if there is an exact match for a method
                            match = true;
                            return m;
                        }
                        else
                            match = false;
                        if (!match) { //there isnt an exact method match, let's resolve this overloading
                            // needs fixing
                            min += rank(bubbles, p.get(i), para.get(i));

                            primitive types, objects
                                e.g. methods  function calls
                                m(int)       m(long) -> 1
                                m(long)       m(Long) -> 2??
                                m(long)       m(Shape) -> INF
    m(A)          m(B) -> 1
    m(A)          m(C) -> 2
    m(A)          m(AAAA) -> INF

}
}


}
if (min < matchNum) {
//System.out.println("setting mub");
mub = m;
}
} else { continue;} //belongstogroup == false

       } //for end
       return mub; //do i want to return string?
       } //method end
       */
//Set the parent Bubble of this Bubble
public void setParentBubble(Bubble b) {
    this.parentBubble = b;
}

public boolean hasField(String fieldName){
	for(Field f : dataFields){
        if(f.getName().equals(fieldName))
            return true;
    }
    return false;
}

//Set the parent Pubble of this Bubble
public void setParentPubble(Pubble p) {
    this.parentPubble = p;
}

public Bubble setIsBuilt(boolean bool)
{
    isBuilt = bool;
    return this;
}

public Bubble setIsFilled(boolean bool)
{
    isFilled = bool;
    return this;
}


/* Setter for visibility
//Set the visibility of this Bubble
public void getVisibility(String visibility) {
this.visibility = visibility;
}
*/

/////////////
/* GETTERS */
/////////////

public SymbolTable getVarTable(){
    return this.varTable;
}

public SymbolTable getFuncTable() {
    return this.funcTable;
}

public SymbolTable getDynamicTypeTable() {
    return this.dynamicTypeTable;
}

public SymbolTable getTable() {
    return this.table;
}

public boolean isBuilt(){
    return isBuilt;
}

public boolean isFilled(){
    return isFilled;
}

public boolean hasField(Field nField){
    for(Field f : getDataFields()){
        if(f.getName().equals(nField.getName()))
            return true;
        else
            {}//System.out.println(f.getName() + " != " + nField.getName());
    }
    return false;
}
//Returns ArrayList of child Bubbles
public ArrayList<Bubble> getBubbles() {
    return this.bubbles;
    //return this.bubbles.toArray();
}

//Returns ArrayList of dataFields
public ArrayList<Field> getDataFields() {
    return this.dataFields;
}

//Returns ArrayList of Mubbles
public ArrayList<Mubble> getMubbles() {
    return this.mubbles;
    //return this.mubbles.toArray();
}

//Returns the name of this class
public String getName() {
    return this.name;
}

//Returns this class' parent class
public Bubble getParentBubble() {
    return this.parentBubble;
}

//Returns this class' package
public Pubble getParentPubble() {
    return this.parentPubble;
}

public String getPackageName(){
    return this.parentPubble.getName();
}


/* Getter for visibility
//Returns the visibility of this class (public, private, etc.)
public String getVisibility() {
return this.visibility;//{{{
}
*/

///////////////////
/* MISC. METHODS */
///////////////////

/*
   public boolean isPrim(String a){
   if(a.equals("int32_t") || a.equals("int64_t")
   ||a.equals("int16_t") ||a.equals("byte") ||a.equals("boolean"))
   return true;
   return false;
   }
   */

public static int rank(ArrayList<Bubble> bubbles, String a, String b) {
    // a is a passed argument
    // b is a parameter defined in a method

    if (isPrim(a) && isPrim(b)) { // a and b are primitive types
        //System.out.println("bbbbbbbbbbbbbbbbbbbbbbb");
        //System.out.println(a + ":" + b);
        //System.out.println(distPrim(a,b));
        //System.out.println("bbbbbbbbbbbbbbbbbbbbbbb");
        return distPrim(a,b);
    }
    else if (isPrim(a) || isPrim(b)) { // one of a and b is a primitive type
        // not working yet
        return 10000;
    }
    else { // both of a and b are objects
        Bubble descedent = findBubble(bubbles, a);
        int count = 0;
        while (!descedent.getName().equals(b)) {
            if (descedent.getName().equals("Object") || descedent.getName().equals("String")) {
                return 100000;
            }
            descedent = descedent.getParentBubble();
            count++;
        }
        return count;
    }
}
/*
   public int rank(ArrayList<Bubble> bubbles, String a, String b){
//a is the actual argument, b is the formal argument
//a moves. b stays
//TODO deal with primitive types
/* add to bubbles ? nah- deal with here- don't want to special case everything
 * int32_t
 * long (int64_t)
 * short(int16_t)
 * char
 * double
 * float
 * boolean
 * byte (int8_t)
 *
// byte -> char -> short -> int -> long
// float -> double
//System.out.println("A: " + a);
//System.out.println("B: " + b);

//System.out.println(isPrim(a) + " " + isPrim(b));

if (a.equals(b))
return 0;

if (isPrim(a) && isPrim(b)) {
return distPrim(a, b);
}
else if ((isPrim(a) && !isPrim(b)) || (!isPrim(a) && isPrim(b))) {
return 100000;
}

Bubble aBub = findBubble(bubbles, a);
//walk a's inheritance
int count = 0;
while (!(aBub.getName().equals(b))) {
if (aBub.getName().equals("Object") || aBub.getName().equals("String"))
break;
aBub = aBub.getParentBubble();

count++;
}
if (!aBub.getName().equals("Object")){
return 10000;
}
else{
return count;
}
   }
   */

public static int distPrim(String a, String b) {
    if (a.equals("byte")) {
        if (b.equals("byte")) {
            return 0;
        }
        else if (b.equals("char")) {
            return 10000;
        }
        else if (b.equals("bool") || b.equals("boolean")) {
            return 10000;
        }
        else if (b.equals("int16_t") || b.equals("short")) {
            return 1;
        }
        else if (b.equals("int32_t") || b.equals("int")) {
            return 2;
        }
        else if (b.equals("int64_t") || b.equals("long")) {
            return 3;
        }
        else if (b.equals("float")) {
            return 4;
        }
        else if (b.equals("double")) {
            return 5;
        }
        else {
            System.out.println("SHIT IS BROKEN: distPrim Bubble.java");
            return -1;
        }
    }
    else if (a.equals("char")) {
        if (b.equals("byte")) {
            return 10000;
        }
        else if (b.equals("char")) {
            return 0;
        }
        else if (b.equals("bool") || b.equals("boolean")) {
            return 10000;
        }
        else if (b.equals("int16_t") || b.equals("short")) {
            return 1;
        }
        else if (b.equals("int32_t") || b.equals("int")) {
            return 2;
        }
        else if (b.equals("int64_t") || b.equals("long")) {
            return 3;
        }
        else if (b.equals("float")) {
            return 4;
        }
        else if (b.equals("double")) {
            return 5;
        }
        else {
            System.out.println("SHIT IS BROKEN: distPrim Bubble.java");
            return -1;
        }
    }
    else if (a.equals("bool") || a.equals("boolean")) {
        if (b.equals("byte")) {
            return 10000;
        }
        else if (b.equals("char")) {
            return 10000;
        }
        else if (b.equals("bool") || b.equals("boolean")) {
            return 0;
        }
        else if (b.equals("int16_t") || b.equals("short")) {
            return 10000;
        }
        else if (b.equals("int32_t") || b.equals("int")) {
            return 10000;
        }
        else if (b.equals("int64_t") || b.equals("long")) {
            return 10000;
        }
        else if (b.equals("float")) {
            return 10000;
        }
        else if (b.equals("double")) {
            return 10000;
        }
        else {
            System.out.println("SHIT IS BROKEN: distPrim Bubble.java");
            return -1;
        }
    }
    else if (a.equals("int16_t") || a.equals("short")) {
        if (b.equals("byte")) {
            return 10000;
        }
        else if (b.equals("char")) {
            return 10000;
        }
        else if (b.equals("bool") || b.equals("boolean")) {
            return 10000;
        }
        else if (b.equals("int16_t") || b.equals("short")) {
            return 0;
        }
        else if (b.equals("int32_t") || b.equals("int")) {
            return 1;
        }
        else if (b.equals("int64_t") || b.equals("long")) {
            return 2;
        }
        else if (b.equals("float")) {
            return 3;
        }
        else if (b.equals("double")) {
            return 4;
        }
        else {
            System.out.println("SHIT IS BROKEN: distPrim Bubble.java");
            return -1;
        }
    }
    else if (a.equals("int32_t") || a.equals("int")) {
        if (b.equals("byte")) {
            return 10000;
        }
        else if (b.equals("char")) {
            return 10000;
        }
        else if (b.equals("bool") || b.equals("boolean")) {
            return 10000;
        }
        else if (b.equals("int16_t") || b.equals("short")) {
            return 10000;
        }
        else if (b.equals("int32_t") || b.equals("int")) {
            return 0;
        }
        else if (b.equals("int64_t") || b.equals("long")) {
            return 1;
        }
        else if (b.equals("float")) {
            return 2;
        }
        else if (b.equals("double")) {
            return 3;
        }
        else {
            System.out.println("SHIT IS BROKEN: distPrim Bubble.java");
            return -1;
        }
    }
    else if (a.equals("int64_t") || a.equals("long")) {
        if (b.equals("byte")) {
            return 10000;
        }
        else if (b.equals("char")) {
            return 10000;
        }
        else if (b.equals("bool") || b.equals("boolean")) {
            return 10000;
        }
        else if (b.equals("int16_t") || b.equals("short")) {
            return 10000;
        }
        else if (b.equals("int32_t") || b.equals("int")) {
            return 10000;
        }
        else if (b.equals("int64_t") || b.equals("long")) {
            return 1;
        }
        else if (b.equals("float")) {
            return 2;
        }
        else if (b.equals("double")) {
            return 3;
        }
        else {
            System.out.println("SHIT IS BROKEN: distPrim Bubble.java");
            return -1;
        }
    }
    else if (a.equals("float")) {
        if (b.equals("byte")) {
            return 10000;
        }
        else if (b.equals("char")) {
            return 10000;
        }
        else if (b.equals("bool") || b.equals("boolean")) {
            return 10000;
        }
        else if (b.equals("int16_t") || b.equals("short")) {
            return 10000;
        }
        else if (b.equals("int32_t") || b.equals("int")) {
            return 10000;
        }
        else if (b.equals("int64_t") || b.equals("long")) {
            return 10000;
        }
        else if (b.equals("float")) {
            return 1;
        }
        else if (b.equals("double")) {
            return 0;
        }
        else {
            System.out.println("SHIT IS BROKEN: distPrim Bubble.java");
            return -1;
        }
    }
    else if (a.equals("double")) {
        if (b.equals("byte")) {
            return 10000;
        }
        else if (b.equals("char")) {
            return 10000;
        }
        else if (b.equals("bool") || b.equals("boolean")) {
            return 10000;
        }
        else if (b.equals("int16_t") || b.equals("short")) {
            return 10000;
        }
        else if (b.equals("int32_t") || b.equals("int")) {
            return 10000;
        }
        else if (b.equals("int64_t") || b.equals("long")) {
            return 10000;
        }
        else if (b.equals("float")) {
            return 10000;
        }
        else if (b.equals("double")) {
            return 0;
        }
        else {
            System.out.println("SHIT IS BROKEN: distPrim Bubble.java");
            return -1;
        }
    }
    else {
        System.out.println("SHIT IS BROKEN: distPrim Bubble.java");
        return -1;
    }
}

public static boolean isPrim(String a) {
    if (a.equals("byte"))
        return true;
    if (a.equals("char"))
        return true;
    if (a.equals("bool") || a.equals("boolean"))
        return true;
    if (a.equals("int16_t") || a.equals("short"))
        return true;
    if (a.equals("int32_t") || a.equals("int"))
        return true;
    if (a.equals("int64_t") || a.equals("long"))
        return true;
    if (a.equals("float"))
        return true;
    if (a.equals("double"))
        return true;
    return false;
}

//todo: Should we search through parent and children bubbles??
public static Bubble findBubble(ArrayList<Bubble> bubbles, String name){
    //System.out.println("trying to find bubble with name :: " + name);
    for(Bubble b : bubbles){
        if (b.getName().equals(name))
            return b;
    }
    System.out.println("HOLY SHIT SOMETHINGS WRONG NO BUBBLE FOUND");
    return new Bubble(); //should never happen
}

public String getTypeDef() {
    String pkgpath = "";
    Pubble p = this.parentPubble;
    //e.g. java::lang::Object
    while(p != null) {
        pkgpath = p.getName() + "::" + pkgpath;
        p = p.getParent();
    }

    return "typedef _" + this.name + "* " + this.name + ";\n";
}


public void inheritAndResolveDataFields(ArrayList<Bubble> bubbleList){

    /* inherit data fields then resolve them */
    ArrayList<Field> inheritedFields = new ArrayList<Field>();
    if(this.getParentBubble() != null && !this.getParentBubble().getName().equals("Object")){

        //inherit fields
        for(Field f : this.getParentBubble().getDataFields()) {
            Field copy = f.deepCopy();
            if(!(copy.getName().charAt(0) == '$'))
                inheritedFields.add(copy);
        }

        //check for "overwritten" fields and mangle the names of inherited fields
        for(Field f :this.getDataFields()){
            for(Field g : inheritedFields){
                if(f.getName().equals(g.getName())){
                    //add a "$" to the old field
                    System.out.println("over writing " + g.getName() + " with a $");
                    g.setName( "$" + g.getName());
                }
            }
        }
    }

    EvalCall e = new EvalCall(this, bubbleList, this.table);
    SymbolTable.Scope dynCurrent = dynamicTypeTable.current();
    SymbolTable.Scope current = table.current();

    //then resolve fields that are not inherited
    for(Field f :this.getDataFields()){
        if(f.hasAssignment()){
            String type = (String)e.dispatch(f.getAssignmentNode());
            f.setDynamicType(type);
            //add this entry to the dynamic type table
            dynCurrent.define(f.getName(), type);
        }
    }

    //add my inherited fields to both static and dynamic tables
    for(Field f: inheritedFields){
        current.define(f.getName(), f.getType());
        dynCurrent.define(f.getName(), f.getDynamicType());
        //finally add inherited fields to my field list
        this.addField(f);
    }

    //now call the process on all my children so it bubbles (pun!) down
    for(Bubble b: bubbles)
        b.inheritAndResolveDataFields(bubbleList);
}


public void mangleBetweenClasses(){
    //will start with root Object bubble and do this recursively
    /* first inherit method if not noame == Object
     * then check if name conflict occurs between mubbles (real name not group name)
     *          for each mubble with name conflict:
     *              if mubble is inherited or over written
     *                  ignore
     *              else
     *                  mangle the name (until not recognized? how do we know when mangling is done?)
     *                                  (mangle until the method is not defined within the class anymore)
     *              for each child in bubble children
     *                  child.mangleBetweenClasses()
     */

    //System.out.println(this.getName() + " is inheriting and mangling (properly?) :)");
    this.inheritMethods();
    //check if any names are duplicates
    //keeping track of names so we know which to mangle
    ArrayList<String> noConflictNames = new ArrayList<String>();
    //System.out.println("All mubbles for " + this.getName());
    for(Mubble m : mubbles){
        char flag = m.getFlag();
        //System.out.println(m.getName() + " || " + flag);
        if(flag == 'i' || flag == 'w')//add to list
            noConflictNames.add(m.getName());
    }
    //now check for conflict
    for(Mubble m : mubbles){
        char flag = m.getFlag();
        if(!(flag == 'i' || flag == 'w')){
            if(noConflictNames.contains(m.getName())){//need to mangle
                String name = m.getName();
                while(noConflictNames.contains(name))
                    name = "_" +name;
                m.mangleName(name);//set name of mubble
            }
            noConflictNames.add(m.getName());
        }
    }

    //System.out.println("After mangling ::::::::::");
    for(Mubble m : mubbles){
        //System.out.println(m.getName());
    }
    for(Bubble b: this.getBubbles()){
        b.mangleBetweenClasses();
    }
}


public void inheritMethods(){
    //takes parents methods for vtable.

    ArrayList<Mubble> newMethodsList = new ArrayList<Mubble>();
    for(Mubble m : parentBubble.getMubbles())
    {
        newMethodsList.add(m.copy());
    }

    for(int i = 0; i < newMethodsList.size(); i++){
        Mubble m = newMethodsList.get(i);
        if(m.isMain() || m.isStatic() || m.isConstructor() || (m.getVisibility() != null && m.getVisibility().equals("private")))
            newMethodsList.remove(i--);
    }
    for(Mubble m : newMethodsList){
        m.setFlag('i');
        m.setBubble(this);
    }
    //kick out overwritten old methods
    for(int i = 0; i < newMethodsList.size(); i ++){
        Mubble m = newMethodsList.get(i);
        for(Mubble n : mubbles){
            if (m.getName().equals(n.getName()) && paramMatch(m, n)) {
                newMethodsList.remove(i);
                n.setFlag('w');
                newMethodsList.add(i, n);
            }
        }
    }

    //add new methods
    for(Mubble m : mubbles){
        if(!(m.getFlag() == 'w')){
            newMethodsList.add(m);
        }
    }
    this.mubbles = newMethodsList;

    for(Mubble m : mubbles)
    {
        //System.out.println(m.getName() + "'s parent bubble is " + m.getClassName());
    }
}

public boolean paramMatch(Mubble one, Mubble two){
    //checks if two mubbles have identical parameter types
    ArrayList<String> o = one.getParameterTypes();
    ArrayList<String> t = two.getParameterTypes();
    if(o.size() != t.size())
        return false;
    for(int i = 0; i < o.size(); i ++){
        if(!o.get(i).equals(t.get(i)))
            return false;
    }
    return true;
}

public String getFDeclStruct() {
    return "struct _"+this.name+";\n"+
        "struct _"+this.name+"_VT;\n";
}

public String getStruct() {
    String ret = "struct _" + this.name + " {\n";
    //indent
    ret += "//Data fields\n";
    //add the VT vptr
    ret += "_"+this.name+"_VT* __vptr;\n";
    //iterate through datafields, print them
    for(Field f : dataFields) {
        //DO I NEED MORE THAN TYPE AND NAME??? NO!
        for(String mod : f.getModifiers())
        {
            if(mod.equals("public") || mod.equals("private")){
            }   //ignore, were not worried about visibility in .h
            else if(mod.equals("final")){
            }//ignore, good java code shouldnt need this
            //ret += "const "; //IS THIS CORRECT? This just replaces final with const"
            else //its another modifier that is c++ compatible, so just print
                ret += mod + " ";
        }

        if(f.isArray) //fix to work with multidimension arrays
            ret += "__rt::Array<" + f.type + ">* " + f.name + ";\n";
        else
            ret += f.type + " " + f.name + ";\n";

    }
    ret+="\n//constructors\n";
    //loop through methods once to see if there are any constructors
    //if not create a default one
    boolean encounteredconstructor = false;
    for(Mubble m: mubbles){
        if(m.isConstructor()){
            encounteredconstructor = true;
        }
    }
    if(!encounteredconstructor) //if there was no constructor in the java file, create default one
    {
        /*
        Mubble construct = new Mubble(name);
        construct.setBubble(this);
        construct.setConstructor(true);
        this.addMubble(construct);
        */

        ret += "_" + name + "(); \n\n";
    }
    else
    {
        for(Mubble constructor : mubbles) {
            if(constructor.isConstructor()){
                ret += "_"+constructor.getName()+"(";
                boolean isFirst = true;
                for(Field f : constructor.getParameters()){
                    if(!isFirst)
                        ret+= ", ";
                    if(isFirst)
                        isFirst = false;
                    ret += f.type + " " + f.name;
                }
                ret += ");\n";
            }
        }
    }
    //static void __delete(__Object*);
    ret += "\n//The destructor\n";
    ret += "static void __delete(_" + this.name + "*);\n";

    ret+="\n//Forward declaration of methods\n";
    //Hardcoding the vt and class
    ret += "static Class __class();\n" +
        "static _"+this.name+"_VT __vtable;\n";
    for(Mubble m : mubbles) {
        //HARDCODING STATIC, MAY NEED TO CHANGE
        if(!m.isConstructor() && m.getFlag() != 'i' && !m.isDelete()) //if its a constructor, inherited method, or delete, don't print it
            ret += m.forward() + "\n";
    }
    //unindent
    ret += "};\n";
    return ret;
}

public String getStructVT() {
    String ret = "\n// The vtable layout for "+this.name+".\n";
    ret += "struct _"+this.name+"_VT {\n";
    //indent
    //Hardcoding class
    ret += "Class __isa;\n";
    //ret +=  "void (*__delete)(_" + this.name + "*);\n";
    for(Mubble m : mubbles) {
        //if its a constructor, main, static, or private, it doesnt belong in the vtable
        if(!m.isConstructor() && !m.isMain() && !m.isStatic() && !m.isPrivate())
            ret += m.vTable1()+"\n";
    }
    //Make VT constructor in-line, hardcoding class
    ret+="\n_"+this.name+"_VT()\n: __isa(_"+this.name+"::__class())";
    //ret += "__delete(&_" + this.name + "::__delete)";
    for(Mubble m : mubbles) {
        if(!m.isConstructor() && !m.isMain() && !m.isStatic() && !m.isPrivate())
            ret += ",\n"+m.vTable2();
    }
    ret+=" {\n";
    //unindent
    ret+="}\n";
    //unindent
    ret+="};\n";
    return ret;
}


public String getCC() {
    //returns a complete .cc entry for this class
    String ret = "";

    //loop through methods once to see if there are any constructors
    //if not create a default one
    boolean encounteredConstructor = false;
    for(Mubble m: mubbles){
        if(m.isConstructor())
            encounteredConstructor = true;
    }
    //=========BEFORE I PRINT CONSTRUCTORS, PUT IN STATIC DATAFIELDS=============//
    ret += this.getStaticData();
    
    if(!encounteredConstructor) //if there was no constructor in the java file, create default one
    {
        ret += "_" + name + "::_" + name + "(): __vptr(&__vtable){} \n\n";
    }
    for(Mubble m: mubbles){
        //if the method is not inherited
        if(m.getFlag() != 'i')
            ret += m.getCC() + "\n\n";
    }


    return ret;
    //return "todo: getC method in Bubble";
}
}//}}}
