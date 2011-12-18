#include "testOverloadMethods.h"
#include <iostream>

_testOverloadMethods::_testOverloadMethods(): __vptr(&__vtable){} 

void _testOverloadMethods::__delete(_testOverloadMethods* __this){
delete __this;
}



void _testOverloadMethods::m(int32_t a){
std::cout << ({__rt::literal("m int");}) << std::endl;

}

void _testOverloadMethods::_m(String a){
std::cout << ({__rt::literal("string");}) << std::endl;

}

void _testOverloadMethods::__m(Object a){
std::cout << ({__rt::literal("ob");}) << std::endl;

}

void _testOverloadMethods::___m(char a){
std::cout << ({__rt::literal("char");}) << std::endl;

}

void _testOverloadMethods::____m(){
std::cout << ({__rt::literal("nothin");}) << std::endl;

}

int main(int argc, const char* argv[]) {
/*
__rt::Ptr<__rt::Array<String> > args = new __rt::Array<String>(argc-1);
for(int i = 1; i < argc; i++){
(*args)[i-1] = argv[i];
}
*/
_testOverloadMethods::m(5);
_testOverloadMethods::_m(__rt::literal("SDA"));
_testOverloadMethods::__m(new java::lang::__Object());
_testOverloadMethods::___m('n');
_testOverloadMethods::____m();
return 0;
}

_testOverloadMethods_VT _testOverloadMethods::__vtable;

Class _testOverloadMethods::__class() { 
 static Class k = new java::lang::__Class(__rt::literal("testOverloadMethods"), java::lang::__Object::__class());
return k;
}

