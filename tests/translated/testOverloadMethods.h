#include "java_lang.h"

typedef __rt::Ptr<java::lang::__Object> Object;
typedef __rt::Ptr<java::lang::__Class> Class;
typedef __rt::Ptr<java::lang::__String> String;

struct _testOverloadMethods;
struct _testOverloadMethods_VT;


//Absolute typedefs to make below code more readable
typedef __rt::Ptr< _testOverloadMethods> testOverloadMethods;

struct _testOverloadMethods {
//Data fields
_testOverloadMethods_VT* __vptr;

//Constructors
_testOverloadMethods(); 


//The destructor
static void __delete(_testOverloadMethods*);

//Forward declaration of methods
static Class __class();
static _testOverloadMethods_VT __vtable;

static void m(int32_t);
static void _m(String);
static void __m(Object);
static void ___m(char);
static void ____m();
};

// The vtable layout for testOverloadMethods.
struct _testOverloadMethods_VT {
Class __isa;
void (*__delete)(_testOverloadMethods*);
int32_t (*hashCode)(testOverloadMethods);
bool (*equals)(testOverloadMethods, Object);
Class (*getClass)(testOverloadMethods);
String (*toString)(testOverloadMethods);

_testOverloadMethods_VT()
: __isa(_testOverloadMethods::__class()),
__delete(&_testOverloadMethods::__delete),
hashCode((int32_t(*)(testOverloadMethods))&java::lang::__Object::hashCode),
equals((bool(*)(testOverloadMethods,Object))&java::lang::__Object::equals),
getClass((Class(*)(testOverloadMethods))&java::lang::__Object::getClass),
toString((String(*)(testOverloadMethods))&java::lang::__Object::toString) {
}
};
