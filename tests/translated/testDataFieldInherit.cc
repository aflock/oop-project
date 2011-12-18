#include "testDataFieldInherit.h"
#include <iostream>

void _testDataFieldInherit::__delete(_testDataFieldInherit* __this){
delete __this;
}



_testDataFieldInherit::_testDataFieldInherit(): __vptr(&__vtable){

}

void _testI1::__delete(_testI1* __this){
delete __this;
}

void _testI1::test(testI1 __this){
std::cout << ({__rt::literal("test1.test(): Should print original name:");}) << std::endl;
std::cout << ({(__rt::literal("\t") + __this->name);}) << std::endl;

}

void _testI1::test2(testI1 __this){
std::cout << ({__rt::literal("test1.test2(): Should print original name:");}) << std::endl;
std::cout << ({(__rt::literal("\t") + __this->name);}) << std::endl;

}

_testI1::_testI1(): __vptr(&__vtable){
	this
}

void _testI2::__delete(_testI2* __this){
delete __this;
}

void _testI2::test(testI2 __this){
std::cout << ({__rt::literal("test2.test(): Should print 69:");}) << std::endl;
std::cout << ({(__rt::literal("\t") + __this->name);}) << std::endl;

}

void _testI2::test2(testI2 __this){
std::cout << ({__rt::literal("test2.test2(): Should print original name:");}) << std::endl;
std::cout << ({(__rt::literal("\t") + __this->$name);}) << std::endl;

}

_testI2::_testI2(): __vptr(&__vtable){

}

void _testI3::__delete(_testI3* __this){
delete __this;
}

void _testI3::test(testI3 __this){
std::cout << ({(__rt::literal("Should print 69: ") + __this->$name);}) << std::endl;
std::cout << ({(__rt::literal("Shoudld print c: ") + __this->name);}) << std::endl;

}

_testI3::_testI3(): __vptr(&__vtable){

}

void _testI4::__delete(_testI4* __this){
delete __this;
}

void _testI4::test(testI4 __this){
std::cout << ({(__rt::literal("Should print c: ") + __this->name);}) << std::endl;
std::cout << ({(__rt::literal("Shoudld print c: ") + __this->name);}) << std::endl;

}

_testI4::_testI4(): __vptr(&__vtable){

}

int main(int argc, const char* argv[]) {
/*
__rt::Ptr<__rt::Array<String> > args = new __rt::Array<String>(argc-1);
for(int i = 1; i < argc; i++){
(*args)[i-1] = argv[i];
}
*/
testI1 t1 = new _testI1();
t1->__vptr->test(t1);
t1->__vptr->test2(t1);
testI2 t2 = new _testI2();
t2->__vptr->test(t2);
t2->__vptr->test2(t2);
testI3 t3 = new _testI3();
t3->__vptr->test(t3);
testI4 t4 = new _testI4();
t4->__vptr->test(t4);
return 0;
}

_testDataFieldInherit_VT _testDataFieldInherit::__vtable;

Class _testDataFieldInherit::__class() {
 static Class k = new java::lang::__Class(__rt::literal("testDataFieldInherit"), java::lang::__Object::__class());
return k;
}

_testI1_VT _testI1::__vtable;

Class _testI1::__class() {
 static Class k = new java::lang::__Class(__rt::literal("testI1"), java::lang::__Object::__class());
return k;
}

_testI2_VT _testI2::__vtable;

Class _testI2::__class() {
 static Class k = new java::lang::__Class(__rt::literal("testI2"), java::lang::__Object::__class());
return k;
}

_testI3_VT _testI3::__vtable;

Class _testI3::__class() {
 static Class k = new java::lang::__Class(__rt::literal("testI3"), java::lang::__Object::__class());
return k;
}

_testI4_VT _testI4::__vtable;

Class _testI4::__class() {
 static Class k = new java::lang::__Class(__rt::literal("testI4"), java::lang::__Object::__class());
return k;
}

