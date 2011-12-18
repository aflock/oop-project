#include "java_lang.h"

typedef __rt::Ptr<java::lang::__Object> Object;
typedef __rt::Ptr<java::lang::__Class> Class;
typedef __rt::Ptr<java::lang::__String> String;

struct _testPrivateMethods;
struct _testPrivateMethods_VT;


//Absolute typedefs to make below code more readable
typedef __rt::Ptr< _testPrivateMethods> testPrivateMethods;

struct _testPrivateMethods {
//Data fields
_testPrivateMethods_VT* __vptr;

//Constructors
_testPrivateMethods(); 


//The destructor
static void __delete(_testPrivateMethods*);

//Forward declaration of methods
static Class __class();
static _testPrivateMethods_VT __vtable;

static void test(testPrivateMethods);
};

// The vtable layout for testPrivateMethods.
struct _testPrivateMethods_VT {
Class __isa;
void (*__delete)(_testPrivateMethods*);
int32_t (*hashCode)(testPrivateMethods);
bool (*equals)(testPrivateMethods, Object);
Class (*getClass)(testPrivateMethods);
String (*toString)(testPrivateMethods);

_testPrivateMethods_VT()
: __isa(_testPrivateMethods::__class()),
__delete(&_testPrivateMethods::__delete),
hashCode((int32_t(*)(testPrivateMethods))&java::lang::__Object::hashCode),
equals((bool(*)(testPrivateMethods,Object))&java::lang::__Object::equals),
getClass((Class(*)(testPrivateMethods))&java::lang::__Object::getClass),
toString((String(*)(testPrivateMethods))&java::lang::__Object::toString) {
}
};
