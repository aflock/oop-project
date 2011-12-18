#include "testBasicClass.h"
#include <iostream>

_testBasicClass::_testBasicClass(): __vptr(&__vtable){


name = __rt::literal("Ninja Assassin");

}



void _testBasicClass::__delete(_testBasicClass* __this){
delete __this;
}

int main(void) {
testBasicClass na = new _testBasicClass();
std::cout << ({(__rt::literal("my name is: ") + na->name);}) << std::endl;
return 0;
}

_testBasicClass_VT _testBasicClass::__vtable;

Class _testBasicClass::__class() { 
 static Class k = new java::lang::__Class(__rt::literal("testBasicClass"), java::lang::__Object::__class());
return k;
}

