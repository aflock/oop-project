#include "testStaticMethods.h"
#include <iostream>

_testStaticMethods::_testStaticMethods(): __vptr(&__vtable){} 

int _testStaticMethods::getNum(){
return ((4 + 4) * 12);

}

int _testStaticMethods::getAge(){
return 5;

}

int _testStaticMethods::getCount(){
return 40000;

}

int _testStaticMethods::getNumber(testStaticMethods __this){
return 6;

}



void _testStaticMethods::__delete(_testStaticMethods* __this){
delete __this;
}

int main(void) {
std::cout << ({_testStaticMethods::getNum();}) << std::endl;
std::cout << ({_testStaticMethods::getAge();}) << std::endl;
std::cout << ({_testStaticMethods::getCount();}) << std::endl;
return 0;
}

_testStaticMethods_VT _testStaticMethods::__vtable;

Class _testStaticMethods::__class() { 
 static Class k = new java::lang::__Class(__rt::literal("testStaticMethods"), java::lang::__Object::__class());
return k;
}

