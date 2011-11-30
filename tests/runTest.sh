#Test for compilation
echo "Compiling test code."
javac *.java
if [ "$?" = "0" ]; then
    #rm *.class
    echo "Test code successfully compiles."
    echo "Compiling NewTranslator.java using make."
    #Compile the translator
    pushd ../../xtc > /dev/null
    . setup.sh
    cd src/xtc/oop/
    make > /dev/null
    if [ "$?"="0" ]; then #if not equal to 0
        echo "Make failed!"
        exit 1
    else
        echo "Compilation successful."
    fi

    popd > /dev/null

    #Pass the files to our parser
    for i in *.java; do
        java $i
        echo "Passing $i to NewTranslator.java"
        #stupid shit
        pushd ../../xtc/src/xtc/oop > /dev/null
        #call the translator
        #java xtc.oop.NewTranslator $i
        popd > /dev/null
    done
    echo "Successful testing! Let's diff the outputs!"
else
    echo "Test code is not compilable!!"
fi

#then diff the outputs

