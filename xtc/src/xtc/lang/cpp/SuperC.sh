#!/bin/bash

if [[ -z $MAINCLASS || -z $GCCINCLUDEDIR || -z $LINUXDIR ]]
then
  echo "Please run \"scripts/env.sh\" to setup the environment first."
  exit 1
fi

/usr/bin/time java -ea \
  $JAVA_ARGS \
  $MAINCLASS \
  -include $CPPDATA/nonbooleans.h \
  -nostdinc -isystem $GCCINCLUDEDIR -I $LINUXDIR/include/ \
  -I $LINUXDIR/arch/x86/include/ \
  $@
