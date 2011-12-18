#include "testMethodOverwriting.h"
#include <iostream>

void _testMethodOverwriting::__delete(_testMethodOverwriting* __this){
delete __this;
}

String _testMethodOverwriting::toString(testMethodOverwriting __this){
::java::lang::String toReturn = (__rt::literal("overwritten toString: ") + __this->name);
return toReturn;

}

_testMethodOverwriting::_testMethodOverwriting(): __vptr(&__vtable){


name = __rt::literal("AFLOCK OVERWRITE");

}



int main(int argc, const char* argv[]) {
/*
__rt::Ptr<__rt::Array<String> > args = new __rt::Array<String>(argc-1);
for(int i = 1; i < argc; i++){
(*args)[i-1] = argv[i];
}
*/
testMethodOverwriting ao = new _testMethodOverwriting();
std::cout << ({ao;}) << std::endl;
return 0;
}

_testMethodOverwriting_VT _testMethodOverwriting::__vtable;

Class _testMethodOverwriting::__class() { 
 static Class k = new java::lang::__Class(__rt::literal("testMethodOverwriting"), java::lang::__Object::__class());
return k;
}

