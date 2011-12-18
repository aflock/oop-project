#include "testPrivateMethods.h"
#include <iostream>

_testPrivateMethods::_testPrivateMethods(): __vptr(&__vtable){} 

void _testPrivateMethods::__delete(_testPrivateMethods* __this){
delete __this;
}



void _testPrivateMethods::test(testPrivateMethods __this){
std::cout << ({__rt::literal("success");}) << std::endl;

}

int main(void) {
testPrivateMethods priv = new _testPrivateMethods();
_testPrivateMethods::test(priv);
return 0;
}

_testPrivateMethods_VT _testPrivateMethods::__vtable;

Class _testPrivateMethods::__class() { 
 static Class k = new java::lang::__Class(__rt::literal("testPrivateMethods"), java::lang::__Object::__class());
return k;
}

