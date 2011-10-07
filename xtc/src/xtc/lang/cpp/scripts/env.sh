#!/bin/bash

# Sets the environment SuperC needs for operation

# These are user-specific
JAVA_DEV_ROOT=~/work/java
CPPDEV=~/work/superC
LINUXVER=linux-2.6.39.2
LINUXDIR=$CPPDEV/$LINUXVER
GCCINCLUDEDIR=$JAVA_DEV_ROOT/data/cpp/gcc/x86_64-linux-gnu/4.4.5/include

# Set up the rest of the environment
JAVA_ARGS="-Xms2048m -Xmx2048m -Xss128m"
MAINCLASS=xtc.lang.cpp.SuperC  # the name of the main java class
CPPDIR=$JAVA_DEV_ROOT/src/xtc/lang/cpp
CPPDATA=$JAVA_DEV_ROOT/data/cpp
FONDAROOT=$JAVA_DEV_ROOT/fonda/cpp_testsuite
PATH_SEP=:
CLASSPATH=$CLASSPATH:$JAVA_DEV_ROOT/classes:$JAVA_DEV_ROOT/bin/junit.jar:$JAVA_DEV_ROOT/bin/antlr.jar:$JAVA_DEV_ROOT/bin/javabdd.jar
PATH=$PATH:$CPPDIR:$CPPDIR/scripts

#Export environment vars
export CPPDEV JAVA_DEV_ROOT LINUXVER GCCINCLUDEDIR TIMEOUT MAINCLASS LINUXDIR CPPDIR CPPDATA FONDAROOT PATH_SEP CLASSPATH PATH
