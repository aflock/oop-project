#include "testInheritandOverload.h"
#include <iostream>

void _testInheritandOverload::__delete(_testInheritandOverload* __this){
	delete __this;
}

_testInheritandOverload::_testInheritandOverload(): __vptr(&__vtable){


	name = __rt::literal("older");

}

void _testInheritandOverload::m(testInheritandOverload __this){
	std::cout << ({__rt::literal("m");}) << std::endl;

}



_Inheriter::_Inheriter(): __vptr(&__vtable){}

void _Inheriter::__delete(_Inheriter* __this){
	delete __this;
}

void _Inheriter::_m(Inheriter __this, int32_t a){
	std::cout << ({a;}) << std::endl;

}

int main(void) {
	Inheriter i = new _Inheriter();
	i->__vptr->_m(i,3);
	i->__vptr->m(i);
	return 0;
}

_testInheritandOverload_VT _testInheritandOverload::__vtable;

Class _testInheritandOverload::__class() {
	static Class k = new java::lang::__Class(__rt::literal("testInheritandOverload"), java::lang::__Object::__class());
	return k;
}

_Inheriter_VT _Inheriter::__vtable;

Class _Inheriter::__class() {
	static Class k = new java::lang::__Class(__rt::literal("Inheriter"), java::lang::__Object::__class());
	return k;
}

