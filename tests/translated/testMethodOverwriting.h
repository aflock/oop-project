#include "java_lang.h"

typedef __rt::Ptr<java::lang::__Object> Object;
typedef __rt::Ptr<java::lang::__Class> Class;
typedef __rt::Ptr<java::lang::__String> String;

struct _testMethodOverwriting;
struct _testMethodOverwriting_VT;


//Absolute typedefs to make below code more readable
typedef __rt::Ptr< _testMethodOverwriting> testMethodOverwriting;

struct _testMethodOverwriting {
//Data fields
_testMethodOverwriting_VT* __vptr;
String name;

//Constructors
_testMethodOverwriting();

//The destructor
static void __delete(_testMethodOverwriting*);

//Forward declaration of methods
static Class __class();
static _testMethodOverwriting_VT __vtable;
static String toString(testMethodOverwriting);

};

// The vtable layout for testMethodOverwriting.
struct _testMethodOverwriting_VT {
Class __isa;
void (*__delete)(_testMethodOverwriting*);
int32_t (*hashCode)(testMethodOverwriting);
bool (*equals)(testMethodOverwriting, Object);
Class (*getClass)(testMethodOverwriting);
String (*toString)(testMethodOverwriting);

_testMethodOverwriting_VT()
: __isa(_testMethodOverwriting::__class()),
__delete(&_testMethodOverwriting::__delete),
hashCode((int32_t(*)(testMethodOverwriting))&java::lang::__Object::hashCode),
equals((bool(*)(testMethodOverwriting,Object))&java::lang::__Object::equals),
getClass((Class(*)(testMethodOverwriting))&java::lang::__Object::getClass),
toString(&_testMethodOverwriting::toString) {
}
};
