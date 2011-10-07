#!/bin/bash

dirs=`find . -maxdepth 1 -mindepth 1 -type d | grep -v arch`
dirs="$dirs ./arch/x86"

echo "$dirs"

echo "The number of .h files."
find $dirs -name "*.h" | wc -l

echo "The number of .c files."
find $dirs -name "*.c" | wc -l

echo "The number of configuration variables."
find $dirs -name Kconfig | xargs cat | grep "^config" | awk '{print }' | sort | uniq | wc -l

echo "The number of defines."
alldefines=`find $dirs -name "*.[c|h]" | xargs cat | grep "^# *define" | wc -l`
echo $alldefines
defines=`find $dirs -name "*.[c]" | xargs cat | grep "^# *define" | wc -l`
echo $defines
defines=`find $dirs -name "*.[h]" | xargs cat | grep "^# *define" | wc -l`
echo $defines

echo "The number of macros."
find $dirs -name "*.[c|h]" | xargs cat | grep "^# *define" | awk '{print $2}' | awk -F'(' '{print $1}' | sort | uniq > uniquemacros.txt
macros=`cat uniquemacros.txt | wc -l`
echo $macros
find $dirs -name "*.[c]" | xargs cat | grep "^# *define" | awk '{print $2}' | awk -F'(' '{print $1}' | sort | uniq | wc -l
find $dirs -name "*.[h]" | xargs cat | grep "^# *define" | awk '{print $2}' | awk -F'(' '{print $1}' | sort | uniq | wc -l

echo "Average definitions per macro."
echo `echo "$alldefines / $macros" | bc -lq`

echo "The number of #if{def|ndef}?s."
defines=`find $dirs -name "*.[c|h]" | xargs cat | grep "^# *if" | wc -l`
echo $defines
defines=`find $dirs -name "*.[c]" | xargs cat | grep "^# *if" | wc -l`
echo $defines
defines=`find $dirs -name "*.[h]" | xargs cat | grep "^# *if" | wc -l`
echo $defines


echo "The number of #includes."
defines=`find $dirs -name "*.[c|h]" | xargs cat | grep "^# *include" | wc -l`
echo $defines
defines=`find $dirs -name "*.[c]" | xargs cat | grep "^# *include" | wc -l`
echo $defines
defines=`find $dirs -name "*.[h]" | xargs cat | grep "^# *include" | wc -l`
echo $defines

echo "The number of directives."
defines=`find $dirs -name "*.[c|h]" | xargs cat | egrep "^# *(if|elif|endif|include|define|undef|error|line|warning|pragma)" | wc -l`
echo $defines
defines=`find $dirs -name "*.[c]" | xargs cat | egrep "^# *(if|elif|endif|include|define|undef|error|line|warning|pragma)" | wc -l`
echo $defines
defines=`find $dirs -name "*.[h]" | xargs cat | egrep "^# *(if|elif|endif|include|define|undef|error|line|warning|pragma)" | wc -l`
echo $defines

cloc $dirs