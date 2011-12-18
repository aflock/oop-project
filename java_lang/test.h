#include "java_lang.h"

typedef java::lang::Class Class;
typedef java::lang::Object Object;
typedef java::lang::String String;

namespace  tests {
struct _test;
struct _test_VT;
}

//Absolute typedefs to make below code more readable
typedef ::tests::_test* test;

namespace  tests {
struct _test {
//Data fields
_test_VT* __vptr;

//Constructors
_test();


//Forward declaration of methods
static Class __class();
static _test_VT __vtable;

};
// The vtable layout for test.
struct _test_VT {
Class __isa;


_test_VT()
: __isa(_test::__class()) {
}
};
}

