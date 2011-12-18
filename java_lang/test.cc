#include "test.h"
#include <iostream>

using namespace std;

namespace  tests {
    _test::_test(): __vptr(&__vtable){}
}

int main(void) {
    cout << "Ha yes win" << endl;
    return 0;
}

_test_VT _test::__vtable;

Class tests::_test::__class() {
    static Class k = new java::lang::__Class(__rt::literal("test"), java::lang::__Object::__class());
    return k;
}

