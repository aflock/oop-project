#include "testFor.h"
#include <iostream>

_testFor::_testFor(): __vptr(&__vtable){} 



void _testFor::__delete(_testFor* __this){
delete __this;
}

int main(void) {
std::cout << ({__rt::literal("Running for loop");}) << std::endl;
for(int i = 0; i < 5; i++) {
std::cout << ({(__rt::literal("for loop i: ") + i);}) << std::endl;
}
return 0;
}

_testFor_VT _testFor::__vtable;

Class _testFor::__class() { 
 static Class k = new java::lang::__Class(__rt::literal("testFor"), java::lang::__Object::__class());
return k;
}

