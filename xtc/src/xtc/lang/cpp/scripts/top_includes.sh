#!/bin/bash

dirs=`find . -maxdepth 1 -mindepth 1 -type d | grep -v arch`
dirs="$dirs ./arch/x86"

echo "top includes in .c and .h"
find $dirs -name "*.[c|h]" | grep -v "^\./arch" | xargs cat | grep "^#include" | awk '{print $2}' | sort | awk 'BEGIN{count=0; file="";}{if ($0 != file) { print count, file; file = $0; count=1; } else { count++ }}' | sort -nr | head
echo

echo "top includes in .c"
find $dirs -name "*.c" | grep -v "^\./arch" | xargs cat | grep "^#include" | awk '{print $2}' | sort | awk 'BEGIN{count=0; file="";}{if ($0 != file) { print count, file; file = $0; count=1; } else { count++ }}' | sort -nr | head
echo

echo "top includes in .h"
find $dirs -name "*.h" | grep -v "^\./arch" | xargs cat | grep "^#include" | awk '{print $2}' | sort | awk 'BEGIN{count=0; file="";}{if ($0 != file) { print count, file; file = $0; count=1; } else { count++ }}' | sort -nr | head

