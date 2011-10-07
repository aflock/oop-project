#!/bin/bash

if [ -z "$JAVA_DEV_ROOT" ]
then
    "please run env.sh first"
fi

LINUXVER=linux-2.6.39.2
LINUXDIR=$CPPDEV/linux-2.6.39.2

if [ ! `basename $PWD` = $LINUXVER ]
then
    echo "Please run from the linux directory $LINUXVER"
    exit
fi

type=$1

if [[ $ARGV -lt 0 || ! ( $type = "preprocessor" || $type = "parser" || $type = "all" || $type = "csus") ]]
then
    echo "Invalid state type argument."
    echo "Please specify preprocessor, parser, or all."
    exit 1
fi 

flags="-cfg includestats -cfg macrostats -cfg expressionvars -cfg bddvars"

case $type in
    preprocessor ) flags="$flags -preprocess -cfg stats -cfg preprocessorstats";;
    parser ) flags="$flags -cfg stats -cfg parserstats";;
    all ) flags="$flags -cfg stats -cfg preprocessorstats -cfg parserstats";;
    csus ) flags="-cfg stats -cfg csus";;
    * ) echo "Invalid type argument tool.  Please check usage and try again."; exit 1;;
esac

infile="$2"

if [ ! -z $infile ]
then

    echo "Using supplied list of files from $infile."

    files=`cat $infile`

    if [ $? -ne 0 ]
    then
        echo "Error reading $infile. Exiting."
        exit
    fi

    echo "Gathering stats for $LINUXVER from $infile.  `cat $infile | wc -l` files."
else
    dirs=`find . -maxdepth 1 -mindepth 1 -type d | grep -v arch`
    dirs="$dirs ./arch/x86"

    echo "$dirs"

    files=`find $dirs -name "*.c"`

    echo "Gathering stats for $LINUXVER.  `find $dirs -name "*.c" | wc -l` .c files."
fi

for i in $files
do
    statfile=$i.$type.stats
    oldstatfile=$i.both.stats
    if [[ -f $oldstatfile ]]
    then
        echo "Processing old $i"
        timekill.pl $TIMEOUT $MAINCLASS /usr/bin/time java -ea \
            $JAVA_ARGS \
            $MAINCLASS \
            -preprocess \
            -nobuiltins -include $CPPDATA/builtins.h -include $CPPDATA/nonbooleans.h \
            -nostdinc -isystem $GCCINCLUDEDIR -I $LINUXDIR/include/ \
            -I $LINUXDIR/arch/x86/include/ \
            -silent $flags $i > /dev/null 2> $oldstatfile.addendum
    else if [ -f $statfile ]
    then
        echo "Skipping $i"
    else
        echo "Processing $i"
#        superC.sh -silent $flags $i 1> /dev/null 2> $statfile
       timekill.pl $TIMEOUT $MAINCLASS /usr/bin/time java -ea \
            $JAVA_ARGS \
            $MAINCLASS \
            -nobuiltins -include $CPPDATA/builtins.h -include $CPPDATA/nonbooleans.h \
            -nostdinc -isystem $GCCINCLUDEDIR -I $LINUXDIR/include/ \
            -I $LINUXDIR/arch/x86/include/ \
            -silent $flags $i > /dev/null 2> $statfile
    fi
    fi
done

