#include "testFor.h"
#include <iostream>

using namespace std;

_testFor::_testFor(): __vptr(&__vtable){} 



int main(void) {
cout << "Running for loop" << endl;
for(int i = 0; i < 5; i++) {
cout << "for loop i: " << i << endl;
}
return 0;
}

_testFor_VT _testFor::__vtable;

Class _testFor::__class() { 
 static Class k = new java::lang::__Class(__rt::literal("testFor"), java::lang::__Object::__class());
return k;
}

