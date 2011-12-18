#include "testWhile.h"
#include <iostream>

_testWhile::_testWhile(): __vptr(&__vtable){} 



void _testWhile::__delete(_testWhile* __this){
delete __this;
}

int main(void) {
std::cout << ({__rt::literal("Running while loop");}) << std::endl;
int32_t i = 0;
while(i < 5) {
std::cout << ({(__rt::literal("while loop i: ") + i++);}) << std::endl;
}
return 0;
}

_testWhile_VT _testWhile::__vtable;

Class _testWhile::__class() { 
 static Class k = new java::lang::__Class(__rt::literal("testWhile"), java::lang::__Object::__class());
return k;
}

