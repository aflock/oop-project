#This file should be run from oop-project. Do not source xtc, it ruins CP

#Test for compilation
echo "Compiling test code."
cd tests
javac *.java
if [ "$?" -ne "0" ]; then
    echo "Test code is not compilable!"
    exit 1
else
    echo "Test code successfully compiles."
fi

#Run the java files
echo "Running test code."
for name in *.java; do
    name=`echo $name | sed 's/.java//g'`
    java $name > "javaoutput/"$name"_J.output"
    if [ "$?" -ne "0" ]; then
	echo "Runtime error on $name."
	exit 1
    fi
done
echo "Test code runs successfully."
rm *.class
cd ..

#Compile the translator
echo "Compiling NewTranslator.java using make."
cd ../xtc #pushd ../xtc > /dev/null
. setup.sh
cd src/xtc/oop/
make > /dev/null
if [ "$?" -ne "0" ]; then
    echo "Make failed!"
    exit 1
else
    echo "Compilation successful."
fi
cd ../../../ #popd > /dev/null

#Pass the files to our parser
for i in ../oop-project/tests/*.java; do
    name=`echo $i | sed 's/..\/oop-project\/tests\///g'`
    echo "Translating $name..."
    java xtc.oop.NewTranslator $i > /dev/null
    if [ "$?" -ne "0" ]; then
	echo "Translator fails on $name."
	exit 1
    else
	echo "Translation successful."
    fi

    name=`echo $name | sed 's/.java//g'`
    cd ../oop-project/tests/translated/
    g++ testFor.cc java_lang.cc testFor.h java_lang.h
    g++ testFor.cc java_lang.cc
    #g++ $name".cc" $name".h" java_lang.cc java_lang.h #shouldn't need .h files
    ./a.out > "../cppoutput/"$name"_C.output"
    cd ../../../xtc

    #pushd ../../xtc/src/xtc/oop > /dev/null
        #call the translator
	#java xtc.oop.NewTranslator $i
	#diff -y (or -q) the outputs
    #popd > /dev/null
done
echo "Successful testing! Let's diff the outputs!"

#then diff the outputs

#if you wanna indent with AF vimscript hack
#for i in ./translated/*.cc; do
    #vim -u -s indentme.scr $i
#done
#for i in ./translated/*.h; do
    #vim -u -s indentme.scr $i
#done
